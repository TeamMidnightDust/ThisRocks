#!/usr/bin/env python3
"""Rewrite Yarn-mapped Java sources to Mojang ("official") names, class-level only.

Builds a Yarn -> Mojang class dictionary for one Minecraft version by joining
three published artifacts on the obfuscated name:

    intermediary v2 tiny : official     -> intermediary
    yarn v2 tiny         : intermediary -> yarn
    Mojang ProGuard txt  : mojang       -> official

Only class names are rewritten. Method and field names are deliberately left
alone: a wrong class name fails to compile, whereas a wrong method name can
bind to a same-shape overload and fail silently at runtime. Those are resolved
against the target-version sources by the compiler in the next phase.

Usage:
    yarn_to_mojmap.py fetch   --version 1.21.11 --yarn-build 2 --cache .mapcache
    yarn_to_mojmap.py report  --cache .mapcache --src src/main/java
    yarn_to_mojmap.py apply   --cache .mapcache --src src/main/java
"""

from __future__ import annotations

import argparse
import json
import re
import sys
import urllib.request
import zipfile
from pathlib import Path

VERSION_MANIFEST = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json"
FABRIC_MAVEN = "https://maven.fabricmc.net/net/fabricmc"


# --------------------------------------------------------------------------- fetch


def _download(url: str, dest: Path) -> Path:
    if dest.exists():
        return dest
    dest.parent.mkdir(parents=True, exist_ok=True)
    with urllib.request.urlopen(url) as response, dest.open("wb") as handle:
        handle.write(response.read())
    return dest


def fetch(version: str, yarn_build: int, cache: Path) -> None:
    _download(
        f"{FABRIC_MAVEN}/intermediary/{version}/intermediary-{version}-v2.jar",
        cache / "intermediary.jar",
    )
    _download(
        f"{FABRIC_MAVEN}/yarn/{version}+build.{yarn_build}"
        f"/yarn-{version}+build.{yarn_build}-v2.jar",
        cache / "yarn.jar",
    )

    with urllib.request.urlopen(VERSION_MANIFEST) as response:
        manifest = json.load(response)
    entry = next(v for v in manifest["versions"] if v["id"] == version)
    with urllib.request.urlopen(entry["url"]) as response:
        meta = json.load(response)
    _download(meta["downloads"]["client_mappings"]["url"], cache / "client.txt")

    print(f"fetched mappings for {version} (yarn build.{yarn_build}) into {cache}")


# --------------------------------------------------------------------- dictionary


def _tiny_classes(jar: Path) -> dict[str, str]:
    """Read `c <from> <to>` lines from a tiny v2 mappings jar."""
    with zipfile.ZipFile(jar) as archive:
        text = archive.read("mappings/mappings.tiny").decode("utf-8")
    out: dict[str, str] = {}
    for line in text.splitlines():
        if not line.startswith("c\t"):
            continue
        parts = line.split("\t")
        if len(parts) >= 3 and parts[2]:
            out.setdefault(parts[1], parts[2])
    return out


def build_dictionary(cache: Path) -> dict[str, str]:
    """Yarn dotted class name -> Mojang dotted class name (nested use `$`)."""
    obf_to_intermediary = _tiny_classes(cache / "intermediary.jar")
    intermediary_to_yarn = _tiny_classes(cache / "yarn.jar")

    mojang_to_obf: dict[str, str] = {}
    with (cache / "client.txt").open(encoding="utf-8") as handle:
        for line in handle:
            if line.startswith((" ", "\t", "#")) or " -> " not in line:
                continue
            left, right = line.rstrip("\n").split(" -> ")
            mojang_to_obf[left.strip()] = right.rstrip(":").strip()
    obf_to_mojang = {v: k for k, v in mojang_to_obf.items()}

    dictionary: dict[str, str] = {}
    for obf, intermediary in obf_to_intermediary.items():
        yarn = intermediary_to_yarn.get(intermediary)
        mojang = obf_to_mojang.get(obf.replace("/", "."))
        if yarn and mojang:
            dictionary[yarn.replace("/", ".")] = mojang
    return dictionary


def already_mojang(dictionary: dict[str, str], name: str) -> bool:
    """True if the name is already a Mojang name, making rewriting a no-op.

    Keeps `apply` idempotent: re-running over migrated sources reports success
    instead of flagging every already-correct import as unresolved.
    """
    targets = _mojang_targets(dictionary)
    owner, _, _member = name.rpartition(".")
    return name in targets or owner in targets


_TARGET_CACHE: dict[int, frozenset[str]] = {}


def _mojang_targets(dictionary: dict[str, str]) -> frozenset[str]:
    key = id(dictionary)
    if key not in _TARGET_CACHE:
        _TARGET_CACHE[key] = frozenset(v.replace("$", ".") for v in dictionary.values())
    return _TARGET_CACHE[key]


def resolve(dictionary: dict[str, str], name: str) -> str | None:
    """Resolve a dotted name, tolerating a trailing static member."""
    if name in dictionary:
        return dictionary[name]
    owner, _, member = name.rpartition(".")
    if owner in dictionary:
        # Either a static member import, or a nested class written with a dot.
        nested = f"{owner}${member}"
        if nested in dictionary:
            return dictionary[nested]
        return f"{dictionary[owner]}.{member}"
    return None


# ------------------------------------------------------------------------ rewrite

IMPORT_RE = re.compile(r"^import\s+(static\s+)?([A-Za-z0-9_.]+)\s*;", re.MULTILINE)


def _simple(name: str) -> str:
    return name.replace("$", ".").rsplit(".", 1)[-1]


def rewrite_file(text: str, dictionary: dict[str, str]) -> tuple[str, list[str]]:
    """Rewrite imports and the simple names they bind. Returns (text, unresolved)."""
    unresolved: list[str] = []
    simple_renames: dict[str, str] = {}
    nested_renames: dict[str, str] = {}

    for match in IMPORT_RE.finditer(text):
        is_static, name = match.group(1), match.group(2)
        if not name.startswith("net.minecraft."):
            continue
        target = resolve(dictionary, name)
        if target is None:
            if not already_mojang(dictionary, name):
                unresolved.append(name)
            continue
        if is_static:
            continue
        yarn_simple, mojang_simple = _simple(name), _simple(target)
        if yarn_simple != mojang_simple:
            simple_renames[yarn_simple] = mojang_simple
        # nested classes of an imported outer class, e.g. Item.Settings
        prefix = name + "$"
        for yarn_fqn, mojang_fqn in dictionary.items():
            if yarn_fqn.startswith(prefix) and yarn_fqn.count("$") == 1:
                yarn_ref = yarn_simple + "." + yarn_fqn.split("$", 1)[1]
                mojang_ref = mojang_simple + "." + mojang_fqn.split("$", 1)[1]
                if yarn_ref != mojang_ref:
                    nested_renames[yarn_ref] = mojang_ref

    def rewrite_import(match: re.Match[str]) -> str:
        is_static, name = match.group(1), match.group(2)
        if not name.startswith("net.minecraft."):
            return match.group(0)
        target = resolve(dictionary, name)
        if target is None:
            return match.group(0)
        return f"import {'static ' if is_static else ''}{target.replace('$', '.')};"

    text = IMPORT_RE.sub(rewrite_import, text)

    # One combined single-pass substitution. Sequential per-name passes are
    # unsound: yarn `RegistryKeys` -> `Registries` and yarn `Registries` ->
    # `BuiltInRegistries` both apply in RocksMain, and running them in sequence
    # lets the second pass rewrite the first pass's output, silently producing
    # `BuiltInRegistries.ITEM_GROUP` where `Registries.CREATIVE_MODE_TAB` was
    # meant. A single pass never re-examines what it just emitted.
    renames = {**simple_renames, **nested_renames}
    if renames:
        # Longest-first so `Item.Settings` wins over bare `Item`.
        alternation = "|".join(
            re.escape(k) for k in sorted(renames, key=lambda k: -len(k))
        )
        pattern = re.compile(rf"(?<![.\w])({alternation})\b")
        text = pattern.sub(lambda m: renames[m.group(1)], text)

    return text, unresolved


# --------------------------------------------------------------------------- cli


def _java_files(src: Path) -> list[Path]:
    return sorted(src.rglob("*.java"))


def report(cache: Path, src: Path) -> int:
    dictionary = build_dictionary(cache)
    names: set[str] = set()
    for path in _java_files(src):
        for match in IMPORT_RE.finditer(path.read_text(encoding="utf-8")):
            if match.group(2).startswith("net.minecraft."):
                names.add(match.group(2))
    unresolved = sorted(
        n for n in names
        if resolve(dictionary, n) is None and not already_mojang(dictionary, n)
    )
    print(f"dictionary entries : {len(dictionary)}")
    print(f"vanilla imports    : {len(names)}")
    print(f"resolved           : {len(names) - len(unresolved)}/{len(names)}")
    if unresolved:
        print("UNRESOLVED:")
        for name in unresolved:
            print("  ", name)
        return 1
    print("all vanilla imports resolved")
    return 0


def apply(cache: Path, src: Path) -> int:
    dictionary = build_dictionary(cache)
    changed, unresolved_total = 0, []
    for path in _java_files(src):
        original = path.read_text(encoding="utf-8")
        rewritten, unresolved = rewrite_file(original, dictionary)
        unresolved_total.extend(unresolved)
        if rewritten != original:
            path.write_text(rewritten, encoding="utf-8")
            changed += 1
    print(f"rewrote {changed} of {len(_java_files(src))} files")
    if unresolved_total:
        print("UNRESOLVED (left untouched):")
        for name in sorted(set(unresolved_total)):
            print("  ", name)
        return 1
    return 0


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="command", required=True)

    p_fetch = sub.add_parser("fetch")
    p_fetch.add_argument("--version", required=True)
    p_fetch.add_argument("--yarn-build", required=True, type=int)
    p_fetch.add_argument("--cache", required=True, type=Path)

    for name in ("report", "apply"):
        p = sub.add_parser(name)
        p.add_argument("--cache", required=True, type=Path)
        p.add_argument("--src", required=True, type=Path)

    args = parser.parse_args(argv)
    if args.command == "fetch":
        fetch(args.version, args.yarn_build, args.cache)
        return 0
    if args.command == "report":
        return report(args.cache, args.src)
    return apply(args.cache, args.src)


if __name__ == "__main__":
    sys.exit(main())
