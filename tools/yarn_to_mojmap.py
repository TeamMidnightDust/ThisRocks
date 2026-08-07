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
    targets = frozenset(v.replace("$", ".") for v in dictionary.values())
    owner, _, _member = name.rpartition(".")
    return name in targets or owner in targets


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

IMPORT_RE = re.compile(r"^import\s+(static\s+)?([A-Za-z0-9_.*]+)\s*;", re.MULTILINE)


def _simple(name: str) -> str:
    return name.replace("$", ".").rsplit(".", 1)[-1]


def _scan_nested_classes(
    dictionary: dict[str, str],
    yarn_fqn: str,
    yarn_simple: str,
    mojang_simple: str,
    nested_renames: dict[str, str],
) -> None:
    """Scan for nested classes of an outer class and populate nested_renames.

    Called for both explicit imports and wildcard-expanded classes to ensure
    consistent handling. E.g., for net.minecraft.block.AbstractBlock mapping to
    BlockBehaviour, finds AbstractBlock$Settings -> BlockBehaviour$Properties
    and records "AbstractBlock.Settings" -> "BlockBehaviour.Properties".
    """
    prefix = yarn_fqn + "$"
    for yarn_nested_fqn, mojang_nested_fqn in dictionary.items():
        if yarn_nested_fqn.startswith(prefix) and yarn_nested_fqn.count("$") == 1:
            yarn_ref = yarn_simple + "." + yarn_nested_fqn.split("$", 1)[1]
            mojang_ref = mojang_simple + "." + mojang_nested_fqn.split("$", 1)[1]
            if yarn_ref != mojang_ref:
                nested_renames[yarn_ref] = mojang_ref


def _package_top_level_classes(dictionary: dict[str, str], pkg: str) -> dict[str, str]:
    """Get {simple_name: mojang_fqn} for all top-level classes in a package."""
    result = {}
    prefix = pkg + "."
    for yarn_fqn, mojang_fqn in dictionary.items():
        if not yarn_fqn.startswith(prefix):
            continue
        remainder = yarn_fqn[len(prefix) :]
        if "." in remainder or "$" in remainder:
            continue
        simple = remainder
        if simple not in result:
            result[simple] = mojang_fqn
    return result


def rewrite_file(text: str, dictionary: dict[str, str]) -> tuple[str, list[str]]:
    """Rewrite imports and the simple names they bind. Returns (text, unresolved)."""
    unresolved: list[str] = []
    simple_renames: dict[str, str] = {}
    nested_renames: dict[str, str] = {}
    wildcard_expansions: dict[str, list[str]] = {}  # pkg -> sorted list of mojang_fqn
    wildcard_classes: dict[str, list[tuple[str, str]]] = {}  # pkg -> [(yarn_fqn, mojang_fqn), ...]

    # Phase 1: Analyze wildcard imports
    all_imports = list(IMPORT_RE.finditer(text))
    simple_name_sources: dict[str, list[str]] = {}  # simple_name -> list of packages
    text_without_imports = IMPORT_RE.sub("", text)

    for match in all_imports:
        is_static, name = match.group(1), match.group(2)
        if not name.startswith("net.minecraft."):
            continue

        if name.endswith(".*"):
            pkg = name[:-2]
            classes = _package_top_level_classes(dictionary, pkg)

            if not classes:
                unresolved.append(name)
                continue

            # Find which classes from this package are used in the body
            used_mojang = []  # Sorted list of Mojang FQNs for import rewriting
            used_pairs = []   # List of (yarn_fqn, mojang_fqn) for Phase 2 processing
            for simple, mojang_fqn in classes.items():
                if re.search(rf"\b{re.escape(simple)}\b", text_without_imports):
                    yarn_fqn = f"{pkg}.{simple}"  # Construct Yarn FQN directly
                    used_mojang.append(mojang_fqn)
                    used_pairs.append((yarn_fqn, mojang_fqn))
                    if simple not in simple_name_sources:
                        simple_name_sources[simple] = []
                    simple_name_sources[simple].append(pkg)

            if used_mojang:
                wildcard_expansions[pkg] = sorted(used_mojang)
                wildcard_classes[pkg] = used_pairs

    # Check for conflicts: same simple name from multiple wildcards
    conflicted_packages: set[str] = set()
    for simple, packages in simple_name_sources.items():
        if len(set(packages)) > 1:
            conflicted_packages.update(packages)

    if conflicted_packages:
        for pkg in conflicted_packages:
            unresolved.append(f"{pkg}.*")
        return text, unresolved

    # Phase 2: Process explicit imports and expanded wildcards
    # Both branches populate simple_renames and nested_renames identically
    for match in all_imports:
        is_static, name = match.group(1), match.group(2)
        if not name.startswith("net.minecraft."):
            continue

        if name.endswith(".*"):
            pkg = name[:-2]
            if pkg in wildcard_classes:
                # Process each expanded class with the same logic as explicit imports
                for yarn_fqn, mojang_fqn in wildcard_classes[pkg]:
                    yarn_simple = _simple(yarn_fqn)
                    mojang_simple = _simple(mojang_fqn)
                    if yarn_simple != mojang_simple:
                        simple_renames[yarn_simple] = mojang_simple
                    # Scan for nested classes (e.g., AbstractBlock -> AbstractBlock$Settings)
                    _scan_nested_classes(dictionary, yarn_fqn, yarn_simple, mojang_simple, nested_renames)
        else:
            # Explicit import
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
            # Scan for nested classes (e.g., Item -> Item$Settings)
            _scan_nested_classes(dictionary, name, yarn_simple, mojang_simple, nested_renames)

    def rewrite_import(match: re.Match[str]) -> str:
        is_static, name = match.group(1), match.group(2)
        if not name.startswith("net.minecraft."):
            return match.group(0)

        if name.endswith(".*"):
            pkg = name[:-2]
            if pkg in wildcard_expansions:
                return "\n".join(
                    f"import {f.replace('$', '.')};" for f in wildcard_expansions[pkg]
                )
            else:
                return match.group(0)
        else:
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


def _report_impl(dictionary: dict[str, str], src: Path) -> tuple[set[str], set[str]]:
    """Compute report metrics using rewrite_file for consistency with apply.

    Returns (all_imports, unresolved_set) where both are sets of import names.
    Uses rewrite_file() so wildcards are expanded the same way in report and apply.
    """
    names: set[str] = set()
    unresolved_total: set[str] = set()

    for path in _java_files(src):
        text = path.read_text(encoding="utf-8")
        # Collect all import names (both explicit and wildcard)
        for match in IMPORT_RE.finditer(text):
            if match.group(2).startswith("net.minecraft."):
                names.add(match.group(2))
        # Use rewrite_file to determine actual unresolved items
        # This ensures wildcards are expanded consistently
        _, unresolved = rewrite_file(text, dictionary)
        unresolved_total.update(unresolved)

    return names, unresolved_total


def report(cache: Path, src: Path) -> int:
    dictionary = build_dictionary(cache)
    names, unresolved_total = _report_impl(dictionary, src)

    print(f"dictionary entries : {len(dictionary)}")
    print(f"vanilla imports    : {len(names)}")
    print(f"resolved           : {len(names) - len(unresolved_total)}/{len(names)}")
    if unresolved_total:
        print("UNRESOLVED:")
        for name in sorted(unresolved_total):
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
