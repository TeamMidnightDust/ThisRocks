package eu.midnightdust.motschen.rocks.world.configured_feature;

import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.midnightdust.motschen.rocks.RocksMain.STICK_VARIATION;
import static eu.midnightdust.motschen.rocks.RocksMain.sticksByType;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class StickFeatures {
    private static final Map<StickType, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new HashMap<>();

    public static List<PlacementModifier> getModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacementModifier.of(count), RarityFilterPlacementModifier.of(rarity),
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(),
                BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0),
                        groundBlocks))));
    }
    public static List<PlacementModifier> getNetherModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacementModifier.of(count), RarityFilterPlacementModifier.of(rarity),
                SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(),
                BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0),
                        groundBlocks))));
    }

    public static void init() {
        for (StickType type : StickType.values()) {
            ConfiguredFeature<?, ?> STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(sticksByType.get(type).getDefaultState().with(STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(sticksByType.get(type).getDefaultState().with(STICK_VARIATION, StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(type).getDefaultState().with(STICK_VARIATION, StickVariation.LARGE), 1).build()))
            );
            CONFIGURED_FEATURES.put(type, STICK_FEATURE);
        }
    }

    public static void initConfigured(Registerable<ConfiguredFeature<?, ?>> context) {
        for (StickType type : StickType.values()) {
            register(context, type.getName()+"_stick", CONFIGURED_FEATURES.get(type));
        }
    }

    public static void initPlaced(Registerable<PlacedFeature> context) {
        for (StickType type : StickType.values()) {
            PlacedFeature STICK_PLACED_FEATURE = switch (type) {
                case CRIMSON -> new PlacedFeature(RegistryEntry.of(CONFIGURED_FEATURES.get(type)), getNetherModifiers(90, 1, Blocks.CRIMSON_NYLIUM));
                case WARPED -> new PlacedFeature(RegistryEntry.of(CONFIGURED_FEATURES.get(type)), getNetherModifiers(90, 1, Blocks.WARPED_NYLIUM));
                case PALE_OAK -> new PlacedFeature(RegistryEntry.of(CONFIGURED_FEATURES.get(type)), getModifiers(20, 1, Blocks.GRASS_BLOCK, Blocks.PALE_MOSS_BLOCK));
                case SPRUCE -> new PlacedFeature(RegistryEntry.of(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.GRASS_BLOCK, Blocks.SNOW_BLOCK, Blocks.PODZOL));
                default -> new PlacedFeature(RegistryEntry.of(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.GRASS_BLOCK, Blocks.MUD, Blocks.PODZOL));
            };
            register(context, type.getName() + "_stick", STICK_PLACED_FEATURE);
        }
    }
}
