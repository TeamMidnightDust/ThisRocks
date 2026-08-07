#!/usr/bin/env python3
"""Structural checks for the built This Rocks! 26.2 jar."""

from __future__ import annotations

import fnmatch
import json
import sys
import zipfile
from pathlib import Path

MOD_ID = "rocks"
EXPECTED_DEPENDS = {"fabricloader", "minecraft", "fabric-api", "midnightlib"}
# Package roots that only ever appear in Yarn-mapped code. Their presence means
# a class reference survived the rename and the jar is mismapped. MC 26.2
# ships unobfuscated with no Yarn release, and this build declares no
# mappings (see PORTING-NOTES.md §4), so the jar's classes use Mojang's own
# package layout directly, e.g. official net/minecraft/resources/Identifier
# vs Yarn net/minecraft/util/Identifier, and official
# net/minecraft/server/level/ServerPlayer vs Yarn
# net/minecraft/server/network/ServerPlayerEntity, so these byte strings do
# not collide with legitimate official-mapped references.
YARN_ONLY_PREFIXES = (
    b"net/minecraft/util/Identifier",
    b"net/minecraft/text/Text",
    b"net/minecraft/registry/Registries",
    b"net/minecraft/server/network/ServerPlayerEntity",
)
# geyser and nether_geyser intentionally ship without a loot table (their
# drops are handled elsewhere); every other block must have one. Keep this
# list explicit so a future block silently missing a loot table still fails.
KNOWN_LOOTLESS_BLOCKS = {"geyser.json", "nether_geyser.json"}


def fail(message: str) -> None:
    print(f"FAIL: {message}")
    sys.exit(1)


def main(jar_path: str) -> int:
    jar = Path(jar_path)
    if not jar.exists():
        fail(f"jar not found: {jar}")

    with zipfile.ZipFile(jar) as archive:
        names = set(archive.namelist())

        if "fabric.mod.json" not in names:
            fail("fabric.mod.json missing")
        meta = json.loads(archive.read("fabric.mod.json"))

        if meta["id"] != MOD_ID:
            fail(f"mod id is {meta['id']}, expected {MOD_ID}")
        if "${version}" in meta["version"]:
            fail("version placeholder was not expanded by processResources")
        missing = EXPECTED_DEPENDS - set(meta.get("depends", {}))
        if missing:
            fail(f"depends missing: {sorted(missing)}")
        if "accessWidener" in meta:
            fail("accessWidener key still present")
        if "mixins" in meta:
            fail("mixins key still present")

        for group, entries in meta["entrypoints"].items():
            for entry in entries:
                cls = entry.replace(".", "/") + ".class"
                if cls not in names:
                    fail(f"{group} entrypoint class not in jar: {entry}")

        # MidnightLib is included via jar-in-jar (build.gradle:
        # implementation include("maven.modrinth:midnightlib:...")). Confirm
        # it actually landed nested in the jar rather than only on the
        # compile classpath. If this is absent, fabric.mod.json's hard
        # `depends` on midnightlib means the mod will simply fail to load
        # with a clear "missing dependency" message rather than misbehave,
        # but users would need to install MidnightLib separately.
        midnightlib_jars = [
            n for n in names
            if fnmatch.fnmatch(n, "META-INF/jars/midnightlib-*.jar")
        ]
        if not midnightlib_jars:
            fail("MidnightLib is not nested: no META-INF/jars/midnightlib-*.jar "
                 "entry found. jar-in-jar include() did not nest it — either "
                 "fix the build or document that MidnightLib must be "
                 "installed separately.")

        blockstates = {n.rsplit("/", 1)[-1] for n in names
                       if n.startswith(f"assets/{MOD_ID}/blockstates/")
                       and n.endswith(".json")}
        item_defs = {n.rsplit("/", 1)[-1] for n in names
                     if n.startswith(f"assets/{MOD_ID}/items/")
                     and n.endswith(".json")}
        if not item_defs:
            fail("no assets/rocks/items/ model definitions — items will render "
                 "as the missing-texture model")
        without_def = sorted(blockstates - item_defs)
        if without_def:
            fail(f"blocks with no item model definition: {without_def}")

        loot = {n.rsplit("/", 1)[-1] for n in names
                if n.startswith(f"data/{MOD_ID}/loot_table/blocks/")
                and n.endswith(".json")}
        missing_loot = sorted(blockstates - loot - KNOWN_LOOTLESS_BLOCKS)
        if missing_loot:
            fail(f"blocks with no loot table (excluding known exceptions "
                 f"{sorted(KNOWN_LOOTLESS_BLOCKS)}): {missing_loot}")

        for name in names:
            if not name.endswith(".class"):
                continue
            body = archive.read(name)
            for prefix in YARN_ONLY_PREFIXES:
                if prefix in body:
                    fail(f"Yarn class reference {prefix.decode()} survived in {name}")

    print(f"PASS: {jar.name}")
    print(f"  version         : {meta['version']}")
    print(f"  blockstates     : {len(blockstates)}")
    print(f"  item defs       : {len(item_defs)}")
    print(f"  loot tables     : {len(loot)} (+{len(KNOWN_LOOTLESS_BLOCKS)} known lootless)")
    print(f"  midnightlib jars: {[Path(n).name for n in midnightlib_jars]}")
    return 0


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("usage: audit_jar.py <jar>")
        sys.exit(2)
    sys.exit(main(sys.argv[1]))
