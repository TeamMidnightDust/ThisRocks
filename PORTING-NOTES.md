# Porting notes: This Rocks! → Minecraft 26.2 (Fabric)

These notes are for a future maintainer of this fork, or for TeamMidnightDust
evaluating an upstream PR built from this branch. They record what was done,
how, why, and — just as important — what has *not* been verified.

## 1. Provenance

This fork was ported from
[`TeamMidnightDust/ThisRocks`](https://github.com/TeamMidnightDust/ThisRocks)
at commit `6a566e11ffdb2f866e860c39e13e0ec138a7367b` (branch `1.21.11`),
licensed MIT. Upstream has no `26.x` branch; this port has no direct upstream
counterpart to diff against.

- Fork: `Arilas/ThisRocks`, branch `port/26.2-fabric`.
- Mod version: `1.9.4+1.21.11` → `1.9.4+26.2`.
- The branch is 25 commits ahead of `upstream/1.21.11`, structured as a
  two-phase port (see §3) followed by build retargeting, source migration,
  datagen regeneration, an automated jar audit, and this document.

## 2. Toolchain

| Component | Version |
|---|---|
| Minecraft | 26.2 |
| Fabric Loom | 1.17.17 |
| Gradle | 9.5.1 |
| Java | 25 |
| Fabric Loader | 0.19.3 |
| Fabric API | 0.155.2+26.2 |
| MidnightLib | 1.9.3+26.2-fabric |
| Polymer | 0.17.3+26.2 |
| FactoryTools | 0.11.2+26.2 |

## 3. The two-phase method

The port was done as two distinct phases, committed separately so each is
independently reviewable:

1. **Phase 1 — mechanical Yarn→Mojang class rename**, applied to source that
   still targets 1.21.11 and still compiles under Yarn mappings. This produces
   a tree that references Mojang-mapped class names but has not yet been
   retargeted to a new Minecraft version or build toolchain.
2. **Phase 2 — build retarget and API migration**, which points the build at
   MC 26.2 / Loom 1.17.17 / Mojang mappings and then fixes the resulting
   compile errors, which are by then genuine API-surface changes rather than
   mapping-name changes.

This ordering matters and is reusable for porting any other Yarn-mapped mod
past the point where Yarn support ends. Note that the intermediate commits in
this sequence (end of Phase 1 through the start of Phase 2's fixes) do **not**
individually compile — that is by design, so the mechanical rename is
reviewable as an isolated diff from the API migration. Only the branch as a
whole, at its tip, is required to compile.

### Why Phase 1 exists at all

Yarn mappings stop at 1.21.11. Minecraft 26.x has no Yarn release, and the
ecosystem this mod depends on (Polymer) ships built against Mojang's own
(ProGuard) mappings. There is no way to stay on Yarn and consume a current
Polymer. Yarn→Mojmap class-name conversion is therefore mandatory before any
API migration work can begin, not optional cleanup.

### The class dictionary

`tools/yarn_to_mojmap.py` builds the Yarn→Mojang class dictionary as a
**three-artifact join on the obfuscated name**: intermediary tiny mappings
(obf ↔ intermediary), Yarn tiny mappings (intermediary ↔ Yarn), and Mojang's
own ProGuard mapping file (obf ↔ Mojang), joined through the shared
obfuscated name to produce Yarn ↔ Mojang directly. Two-artifact joins (e.g.
matching on intermediary names alone) don't work here because Yarn's tiny
format and Mojang's ProGuard format don't share a key without the
intermediary layer as the pivot.

### Reusable gotchas (the most valuable part of this exercise for future ports)

- **Pin the exact Yarn build the mod actually used.** This mod was written
  against Yarn `1.21.11+build.2`. Later Yarn builds for the same Minecraft
  version rename `Pool` → `WeightedPool`, and picking a later build silently
  leaves an unresolvable import in the rewritten source. The dictionary must
  be built from the same Yarn build the mod's `build.gradle` pinned, not
  "whatever is latest for that Minecraft version."
- **Never apply renames as sequential regex passes over the same names.**
  Both `RegistryKeys` → `Registries` and `Registries` → `BuiltInRegistries`
  are valid entries in the dictionary. Running them as two sequential
  find/replace passes turns every `RegistryKeys` into `BuiltInRegistries`
  (the second pass re-matches the first pass's output), silently producing
  the wrong registry class. Renames must be computed from the original names
  in one pass, not chained.
- **Never rename a simple name the compilation unit itself declares.** This
  mod's own `datagen/Models.java` declares `public class Models`, which
  collides with vanilla `net.minecraft.client.data.Models` (renamed to
  `ModelTemplates` in Mojang mappings). A rename tool that doesn't special-case
  this will rename the mod's own class declaration, producing an illegal
  Java file (declared type name no longer matches the filename) while also
  wildcard-importing the class it just renamed to. Four types in this
  codebase collided with vanilla simple names this way; only one was live at
  the time it was caught, but the tool now excludes any simple name the
  compilation unit declares, at every rename-synthesis point, rather than
  handling collisions case by case.
- **Wildcard imports must be expanded to explicit imports, and the expansion
  must include nested classes.** A blanket `import net.minecraft.block.*;`
  hides which simple names are actually referenced, so a regex-based renamer
  that only scans explicit imports will miss classes referenced solely
  through a wildcard — including nested classes reached through it (e.g.
  `AbstractBlock.Settings` used via a wildcard import of `net.minecraft.block`,
  which needs to become `BlockBehaviour.Properties`, not `BlockBehaviour.Settings`).
  Both the wildcard-import branch and the explicit-import branch of the
  rename tool need to run the same nested-class scan, or one of them will
  quietly leave Yarn nested-class names behind in code that otherwise looks
  fully converted.

## 4. Build changes that are non-obvious for 26.2

MC 26.2 changed enough about how Loom and Gradle work together that a
build.gradle written against 1.21.11-era Loom examples will not translate
directly:

- **MC 26.2 ships unobfuscated.** There is **no** `mappings` line in
  `build.gradle` at all — `loom.officialMojangMappings()` (the standard
  Mojmap idiom for older, obfuscated Minecraft versions) is wrong here and
  will not resolve correctly, because there is no obfuscation to map away.
- **No `remapJar` / `remapSourcesJar`.** Because there is nothing to remap,
  `publishing {}` must reference the plain `jar` / `sourcesJar` tasks
  directly.
- **Plugin id is `net.fabricmc.fabric-loom`**, not the shorthand
  `fabric-loom` used in some older examples.
- **Gradle 9 removed `archivesBaseName`.** Use
  `base { archivesName = project.archives_base_name }` instead.
- **Loom 1.17 has no `modImplementation`** and performs no remapping step at
  all; regular `implementation` is used throughout `build.gradle`.

These four points were each individually wrong in the initial plan for this
port and were corrected against a working, independently-verified 26.2
Fabric project before being applied here — worth checking against a current
real project rather than an older mappings-era tutorial when porting another
mod.

## 5. Deletions and why

- **`src/main/resources/rocks.mixins.json`** — named a mixin class that does
  not exist in the source tree, and was never referenced from
  `fabric.mod.json`'s `mixins` key. Dead configuration from before this
  fork's history; removed rather than carried forward.
- **`src/main/resources/thisrocks.accesswidener`** — existed solely to widen
  access for the mixin declared (but unused) in `rocks.mixins.json` above.
  With that mixin gone, the access widener had no remaining purpose.
- **The `me.shedaniel.unified-publishing` Gradle plugin** — predates Gradle 9
  and does not work under it. Removed from `build.gradle`; `publishing {}`
  now uses the plain `maven-publish` plugin instead.

## 6. MidnightLib

`implementation include("maven.modrinth:midnightlib:${midnightlib_version}")`
in `build.gradle` works correctly under Loom 1.17 on 26.2 — this was an open
question during the build retarget (dependency resolution alone doesn't prove
jar-in-jar nesting actually happens, since `jar` can't run until the source
compiles) and was resolved empirically once the jar could be built:
`build/libs/rocks-1.9.4+26.2.jar` contains
`META-INF/jars/midnightlib-1.9.3+26.2-fabric.jar` (60,027 bytes), and the
jar's `fabric.mod.json` declares it under the `jars` key. MidnightLib is also
declared as a hard `depends` entry in `fabric.mod.json` (not just bundled),
so a build that somehow shipped without the nested jar would still fail
loudly rather than silently missing a dependency.

## 7. The two 26.2 runtime traps

This is the single most important section for a future maintainer, because
both traps compile cleanly and only fail at runtime.

**MC 26.2 binds item `DataComponents` in a deferred pass that runs *after*
mod entrypoints.** Concretely: `Holder.components()` — which every
`ItemStack` constructor eventually needs — throws `NullPointerException:
Components not bound yet` if the `ItemStack` is constructed during
`onInitialize()`. On 1.21.11 this ordering held instead, so upstream's code
was valid when written; 26.2 changed it out from under any mod that eagerly
builds `ItemStack`s at mod-init time. This broke two separate, unrelated
places in this codebase:

1. **The creative-tab item list.** `RocksMain.groupItems` was
   `List<ItemStack>`, populated eagerly during registration. Fixed by
   changing it to `List<Supplier<ItemStack>>` and resolving each supplier at
   display time inside the two `displayItems` callbacks. Commit `05613f1`.
2. **Polymer's `initModels()` calls in `PolyUtil.init()`.** Each of the seven
   `ItemDisplay*Model.initModels()` calls resolves a `LazyItemStack`, which
   ultimately constructs an `ItemStack` the same way. Fixed by deferring all
   seven calls from `onInitialize()` to a `ServerLifecycleEvents.SERVER_STARTING`
   handler. Commit `b15807d`.

The second of these was reachable **only on a dedicated server** —
`polymerMode` requires `!isClientEnv()`, which is false in singleplayer/LAN —
so no client-side playtest, and no `runClient` launch, would ever have
exercised this path. It was caught only because Task 7's `runDatagen`
regeneration run happened to share the same underlying cause as the first
bug, prompting a targeted look for other init-time `ItemStack` construction.
A dedicated-server boot (§9) is what actually exercises this fix; a client
launch alone would not have caught it, and would have shipped it broken.

## 8. Generated resources

`src/main/generated` was regenerated from scratch (including a clean removal
of Fabric datagen's own cache, which otherwise short-circuits unchanged-looking
output and produces a false "byte-identical" reading — this happened once
during the port and was caught before being trusted). The result: 286 files
in both the 1.21.11 baseline and the 26.2 regeneration, 0 additions, 0
removals, 221 byte-identical, 65 changed. All 65 changes fall into three
categories, all driven by vanilla codec/behavior changes rather than by any
mod-code change:

- **26 loot tables** drop an explicit `"bonus_rolls": 0.0` field — it is now
  a codec default and is simply omitted from serialized output. No loot
  entries or conditions were lost.
- **10 recipes** drop an explicit `"count": 1` field for the same reason — a
  codec default, now omitted. Recipe namespacing (`rocks:...`) and
  ingredients are unaffected.
- **29 placed features** change `matching_blocks: minecraft:air` to
  `matching_block_tag: minecraft:air`. This is the one actual behavioral
  difference in the whole port, and it is entirely vanilla's, not this mod's:
  vanilla's own `BlockPredicate.ONLY_IN_AIR_PREDICATE` (which this mod's
  feature definitions reference by referring to the vanilla constant, not by
  redefining it) changed from an exact match on the `minecraft:air` block to
  a match against the `minecraft:air` **tag**, which contains `air`,
  `void_air`, and `cave_air`. This widens where the predicate matches. In
  practice this is expected to be inert for these particular features: they
  are `TOP_LAYER_MODIFICATION` surface decoration whose target is always
  plain surface air, `cave_air` is underground (out of reach of surface
  decoration), and `void_air` is out of world bounds. The mod's own code is
  unchanged here — it references the vanilla constant and tracks vanilla's
  definition of it exactly, the same as every vanilla feature using that
  predicate would.

All `assets/` output (blockstates, item model definitions, models, language
files) is byte-identical to the 1.21.11 baseline — there is no schema drift
anywhere in the client-facing generated resources.

## 9. What is verified and what is not

Be precise here — it matters for anyone deciding whether this is ready to
ship or PR.

### Verified by actual execution

- A dedicated server (`./gradlew runServer`) boots cleanly to
  `Done (1.276s)! For help, type "help"`.
- All expected mods load: `rocks 1.9.4+26.2`, `midnightlib 1.9.3`,
  `factorytools 0.11.2+26.2`, and every `polymer-*` module.
- World generation completes (`Preparing spawn area: 100%`) with zero errors.
- Shutdown is clean (`stop` command → orderly save-and-stop sequence, no
  crash report).
- Zero occurrences anywhere in the logs of `Components not bound yet` or any
  `NullPointerException`.
- **Polymer mode genuinely activated on that server run, not just in theory.**
  This is proven, not inferred: `run/polymer/` and `run/config/polymer` were
  created on disk, and those directories are only created by
  `PolymerResourcePackUtils.addModAssets()`, which only runs inside
  `PolyUtil.init()`, which only runs when `polymerMode == true`. Since
  `polymerMode` requires a dedicated server and this was one, the
  `SERVER_STARTING` deferral fix from §7 was actually exercised by execution
  on this run, not merely justified by init-order reasoning.

### Not verified — requires a human at the keyboard

- Surface rock and stick generation in a real, explored world.
- The creative tab, including its contents and all three starfish colour
  variants.
- The MidnightLib config screen for this mod (rocks/sticks/misc/effects
  categories).
- Geyser levitation behavior.
- Rock block drops (this is the specific loot-table failure mode that broke
  other mods in this porting project's history — do not skip re-checking it
  here).
- **Polymer's actual in-world rendering to a vanilla (non-Polymer-aware)
  client.** Server boot and resource-pack generation succeeding is not the
  same as confirming a vanilla client renders the substituted blocks/items
  correctly.

## 10. Known issues and follow-ups

- **Upstream bug, deliberately preserved as-is.** `PolyUtil.java:52` assigns
  the waterlogged fallback block to `SMALL_BLOCK` instead of
  `PASSABLE_WATERLOGGED_BLOCK`. As a result, `PASSABLE_WATERLOGGED_BLOCK` can
  remain `null` if `requestEmpty(KELP)` ever fails, and a later read of it
  (in `Seashell`/`StarfishPolymer.getPolymerBlockState`) would NPE. This bug
  predates the port and was left exactly as upstream wrote it, specifically
  so this fork's diff against upstream stays minimal and PR-able. It is
  worth reporting to TeamMidnightDust separately rather than silently fixing
  it here.
- **`datagen/Models.java` uses reflection** (`setAccessible`) on three
  private vanilla members: `TextureSlot.create(String)` (private static) and
  `BlockModelGenerators.blockStateOutput` / `.modelOutput` (private final).
  Confirmed via `javap -p` that no public 26.2 API exposes any of these — the
  reflection is a genuine necessity, not a shortcut around an available API.
  **Open follow-up:** test whether Loom accepts `loom.accessWidenerPath` in
  `build.gradle` **without** a corresponding `accessWidener` key in
  `fabric.mod.json`. If Loom does allow that combination, it would be a
  strictly better mechanism than reflection here — compile-time validated
  access, and zero runtime footprint in players' games (an access widener
  declared in `fabric.mod.json` widens access in every user's game at
  runtime, including for datagen-only code that never runs outside the
  build). This was flagged during the port but not tried; whoever picks it
  up should record the outcome either way.
- **`PolyUtil` reaches into `eu.pb4.polymer.virtualentity.impl.HolderHolder`**,
  an implementation-package class, not public API. This is inherited
  directly from upstream (unchanged in shape between the Polymer version
  upstream used and 0.17.3), not introduced by this port, but it remains
  fragile across future Polymer updates since impl packages carry no
  compatibility guarantee.
- **`PolymerCommonUtils` resolves only transitively**, via `polymer-core`'s
  own compile-scope dependency on `polymer-common`, rather than through an
  explicit dependency declared in this mod's own `build.gradle`. It works
  today because Polymer upstream declares that dependency at compile scope,
  but declaring `polymer-common` explicitly here would remove that
  fragility and stop depending on an upstream implementation detail holding.
- **`tools/audit_jar.py`'s Yarn-leak check is a curated 4-prefix sample**
  (checks for a handful of representative Yarn-only class-name byte strings
  in the built jar), not an exhaustive scan of every Yarn class name that
  could theoretically leak. A leak through some other Yarn-mapped class name
  outside the sampled four would pass the audit silently.
- **`gradle.properties` still carries `release_type`, `curseforge_id`, and
  `modrinth_id`**, left over from before the `unified-publishing` plugin was
  removed (§5). They are unused now and could be deleted in a follow-up
  cleanup pass.

## 11. Fork-local scaffolding — exclude from any upstream PR

The following exist only to support this port and are not appropriate to
include in a PR back to `TeamMidnightDust/ThisRocks`:

- `docs/superpowers/` — the plan and design documents that drove this port.
- `tools/yarn_to_mojmap.py` (and its test file) — the Yarn→Mojang class
  dictionary tool described in §3.
- `tools/audit_jar.py` — the built-jar structural audit described in §10.

Any upstream PR built from this branch should drop these paths, along with
`.superpowers/` (the session-level SDD ledger this porting effort tracked its
own progress in), before submission.
