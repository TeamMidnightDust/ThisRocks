package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.util.BiomeCondition;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

public class FeatureInjector {
    public static void init() {
        // Rocks
        BiomeCondition rocksCondition = BiomeCondition.builder()
                .rejectTag(BiomeTags.IS_NETHER)
                .rejectTag(BiomeTags.HAS_END_CITY)
                .rejectTag(BiomeTags.IS_BEACH)
                .rejectTag(BiomeTags.HAS_DESERT_PYRAMID)
                .rejectTag(BiomeTags.IS_BADLANDS)
                .rejectTag(BiomeTags.HAS_IGLOO)
                .rejectTag(BiomeTags.IS_OCEAN)
                .build();
        addTopLayerFeature(RocksConfig.rock, "rock", rocksCondition);
        addTopLayerFeature(RocksConfig.rockMix, "rock_mix", rocksCondition);
        addTopLayerFeature(RocksConfig.graniteRock, "granite_rock", rocksCondition);
        addTopLayerFeature(RocksConfig.dioriteRock, "diorite_rock", rocksCondition);
        addTopLayerFeature(RocksConfig.andesiteRock, "andesite_rock", rocksCondition);

        addTopLayerFeature(RocksConfig.sandRock, "sand_rock", BiomeCondition.builder().acceptTag(BiomeTags.IS_BEACH).acceptTag(BiomeTags.IS_BADLANDS).acceptTag(BiomeTags.HAS_DESERT_PYRAMID).acceptBiomeRegex(".*desert.*").build());
        addTopLayerFeature(RocksConfig.redSandRock, "red_sand_rock", BiomeCondition.builder().acceptTag(BiomeTags.IS_BADLANDS).acceptTag(BiomeTags.HAS_DESERT_PYRAMID).build());

        addTopLayerFeature(RocksConfig.iceRock, "ice_rock", BiomeCondition.builder().acceptTag(BiomeTags.HAS_IGLOO).acceptPredicate(biome -> biome.getBaseTemperature() < 0.15F).build());

        addTopLayerFeature(RocksConfig.gravelRock, "gravel_rock", BiomeCondition.builder().acceptBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS).acceptBiome(Biomes.STONY_SHORE).acceptTag(BiomeTags.IS_MOUNTAIN).build());
        addTopLayerFeature(RocksConfig.endStoneRock, "end_stone_rock", BiomeCondition.builder().acceptBiome(Biomes.THE_END).build());

        // Sticks
        addTopLayerFeature(RocksConfig.oakStick, "oak_stick", BiomeCondition.builder()
                .acceptBiome(Biomes.FOREST)
                .acceptBiome(Biomes.WINDSWEPT_HILLS)
                .acceptBiome(Biomes.PLAINS)
                .acceptBiome(Biomes.FLOWER_FOREST)
                .acceptBiome(Biomes.WOODED_BADLANDS)
                .acceptBiome(Biomes.SWAMP)
                .acceptBiome(Biomes.RIVER)
                .acceptBiomeRegex("^(?!.*pale|.*dark).*oak.*")
                .build()
        );

        addTopLayerFeature(RocksConfig.birchStick, "birch_stick", BiomeCondition.builder().acceptBiome(Biomes.FOREST).acceptBiome(Biomes.FLOWER_FOREST).acceptBiomeRegex(".*birch.*").build());

        addTopLayerFeature(RocksConfig.spruceStick, "spruce_stick", BiomeCondition.builder().acceptTag(BiomeTags.IS_TAIGA).acceptBiome(Biomes.WINDSWEPT_HILLS).acceptBiomeRegex(".*spruce.*").build());
        addTopLayerFeature(RocksConfig.pinecone, "pinecone", BiomeCondition.builder().acceptTag(BiomeTags.IS_TAIGA).acceptBiome(Biomes.WINDSWEPT_HILLS).acceptBiomeRegex(".*spruce.*").build());

        addTopLayerFeature(RocksConfig.acaciaStick, "acacia_stick", BiomeCondition.builder().acceptTag(BiomeTags.HAS_VILLAGE_SAVANNA).build());
        addTopLayerFeature(RocksConfig.jungleStick, "jungle_stick", BiomeCondition.builder().acceptTag(BiomeTags.IS_JUNGLE).build());
        addTopLayerFeature(RocksConfig.bambooStick, "bamboo_stick", BiomeCondition.builder().acceptTag(BiomeTags.IS_JUNGLE).build());
        addTopLayerFeature(RocksConfig.paleOakStick, "pale_oak_stick", BiomeCondition.builder().acceptBiome(Biomes.PALE_GARDEN).build());
        //? if >= 26.3
        addTopLayerFeature(RocksConfig.poplarStick, "poplar_stick", BiomeCondition.builder().acceptBiome(Biomes.DAPPLED_FOREST).build());

        addTopLayerFeature(RocksConfig.darkOakStick, "dark_oak_stick", BiomeCondition.builder().acceptBiome(Biomes.DARK_FOREST).build());
        addTopLayerFeature(RocksConfig.mangroveStick, "mangrove_stick", BiomeCondition.builder().acceptBiome(Biomes.MANGROVE_SWAMP).build());
        addTopLayerFeature(RocksConfig.cherryStick, "cherry_stick", BiomeCondition.builder().acceptBiome(Biomes.CHERRY_GROVE).build());

        // Misc
        addTopLayerFeature(RocksConfig.seashell, "seashell", BiomeCondition.builder().acceptTag(BiomeTags.IS_BEACH).rejectBiomeRegex(".*snow.*").build());
        addTopLayerFeature(RocksConfig.starfish, "starfish", BiomeCondition.builder().acceptTag(BiomeTags.IS_BEACH).rejectBiomeRegex(".*snow.*").build());

        addTopLayerFeature(RocksConfig.underwaterSeashell, "underwater_seashell", BiomeCondition.builder().acceptTag(BiomeTags.IS_OCEAN).build());
        addTopLayerFeature(RocksConfig.underwaterStarfish, "underwater_starfish", BiomeCondition.builder().acceptTag(BiomeTags.IS_OCEAN).rejectBiomeRegex(".*cold.*").build());

        addTopLayerFeature(RocksConfig.netherrackRock, "netherrack_rock", BiomeCondition.builder().acceptTag(BiomeTags.IS_NETHER).build());
        addTopLayerFeature(RocksConfig.soulSoilRock, "soul_soil_rock", BiomeCondition.builder().acceptTag(BiomeTags.IS_NETHER).build());
        addTopLayerFeature(RocksConfig.gravelRock, "nether_gravel_rock", BiomeCondition.builder().acceptTag(BiomeTags.IS_NETHER).build());

        addTopLayerFeature(RocksConfig.netherGeyser, "nether_geyser", BiomeCondition.builder().acceptTag(BiomeTags.IS_NETHER).build());
        addTopLayerFeature(RocksConfig.warpedStick, "warped_stick", BiomeCondition.builder().acceptTag(BiomeTags.IS_NETHER).build());
        addTopLayerFeature(RocksConfig.crimsonStick, "crimson_stick", BiomeCondition.builder().acceptTag(BiomeTags.IS_NETHER).build());

        addTopLayerFeature(RocksConfig.geyser, "snowy_geyser", BiomeCondition.builder().acceptTag(BiomeTags.HAS_IGLOO).acceptBiomeRegex(".*snowy.*").build());
    }

    public static ResourceKey<PlacedFeature> getFeatureKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(RocksMain.MOD_ID, name));
    }

    public static boolean isExcluded(BiomeSelectionContext ctx) {
        return RocksConfig.biomeExclusions.contains(ctx.getBiomeKey().identifier().toString());
    }

    public static void addTopLayerFeature(boolean config, String name, @Nullable BiomeCondition biomeCondition) {
        if (config) {
            BiomeModifications.addFeature(
                    ctx -> {
                        if (isExcluded(ctx)) return false;
                        if (biomeCondition == null) return true;
                        if (biomeCondition.invalidTags().stream().anyMatch(ctx::hasTag)) return false;
                        if (biomeCondition.invalidRegexes().stream().anyMatch(r -> ctx.getBiomeKey().identifier().toString().matches(r))) return false;
                        if (biomeCondition.includeByDefault()) return true;
                        boolean shouldAdd = false;
                        shouldAdd |= biomeCondition.validIds().contains(ctx.getBiomeKey().identifier().toString());
                        shouldAdd |= biomeCondition.validRegexes().stream().anyMatch(r -> ctx.getBiomeKey().identifier().toString().matches(r));
                        shouldAdd |= biomeCondition.validTags().stream().anyMatch(ctx::hasTag);
                        shouldAdd |= biomeCondition.validPredicates().stream().anyMatch(p -> p.test(ctx.getBiome()));
                        return shouldAdd;
                    },
                    GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                    getFeatureKey(name));
        }
    }


}