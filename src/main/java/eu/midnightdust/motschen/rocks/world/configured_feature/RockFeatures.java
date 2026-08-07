package eu.midnightdust.motschen.rocks.world.configured_feature;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.Holder;
import net.minecraft.util.random.WeightedList;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.midnightdust.motschen.rocks.RocksMain.*;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class RockFeatures {
    private static final Map<RockType, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new HashMap<>();

    public static List<PlacementModifier> getModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.of(rarity),
                InSquarePlacement.of(), PlacementUtils.WORLD_SURFACE_WG_HEIGHTMAP, BiomeFilter.of(),
                BlockPredicateFilter.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), groundBlocks))));
    }
    public static List<PlacementModifier> getModifiersInvertedGroundCheck(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.of(rarity),
                InSquarePlacement.of(), PlacementUtils.WORLD_SURFACE_WG_HEIGHTMAP, BiomeFilter.of(),
                BlockPredicateFilter.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.not(BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), groundBlocks)))));
    }
    public static List<PlacementModifier> getNetherModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.of(rarity),
                InSquarePlacement.of(), PlacementUtils.BOTTOM_TO_TOP_RANGE, BiomeFilter.of(),
                BlockPredicateFilter.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), groundBlocks))));
    }
    public static List<PlacementModifier> getEndModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.of(rarity),
                InSquarePlacement.of(), PlacementUtils.MOTION_BLOCKING_HEIGHTMAP, BiomeFilter.of(),
                BlockPredicateFilter.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), groundBlocks))));
    }
    public static ConfiguredFeature<?, ?> ROCK_MIX_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(rocksByType.get(RockType.GRANITE).getDefaultState().with(ROCK_VARIATION,RockVariation.TINY), 10).add(rocksByType.get(RockType.GRANITE).getDefaultState().with(ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.GRANITE).getDefaultState().with(ROCK_VARIATION,RockVariation.MEDIUM), 5).add(rocksByType.get(RockType.GRANITE).getDefaultState().with(ROCK_VARIATION,RockVariation.LARGE), 1)
                    .add(rocksByType.get(RockType.DIORITE).getDefaultState().with(ROCK_VARIATION,RockVariation.TINY), 10).add(rocksByType.get(RockType.DIORITE).getDefaultState().with(ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.DIORITE).getDefaultState().with(ROCK_VARIATION,RockVariation.MEDIUM), 5).add(rocksByType.get(RockType.DIORITE).getDefaultState().with(ROCK_VARIATION,RockVariation.LARGE), 1)
                    .add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(ROCK_VARIATION,RockVariation.TINY), 10).add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(ROCK_VARIATION,RockVariation.MEDIUM), 5).add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(ROCK_VARIATION,RockVariation.LARGE), 1)
                    .build()))
    );
    public static ConfiguredFeature<?, ?> NETHER_GRAVEL_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
            new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                    .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                    .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );

    public static PlacedFeature ROCK_MIX_PLACED_FEATURE = new PlacedFeature(Holder.of(ROCK_MIX_FEATURE), getModifiersInvertedGroundCheck(3, 1, Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND, Blocks.RED_SAND, Blocks.END_STONE));
    public static PlacedFeature NETHER_GRAVEL_ROCK_PLACED_FEATURE = new PlacedFeature(Holder.of(NETHER_GRAVEL_ROCK_FEATURE), getNetherModifiers(30, 1, Blocks.GRAVEL));

    public static void init() {
        for (RockType type : RockType.values()) {
            ConfiguredFeature<?, ?> ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                    new WeightedStateProvider(WeightedList.<BlockState>builder()
                            .add(rocksByType.get(type).getDefaultState().with(ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(rocksByType.get(type).getDefaultState().with(ROCK_VARIATION, RockVariation.SMALL), 7)
                            .add(rocksByType.get(type).getDefaultState().with(ROCK_VARIATION, RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(type).getDefaultState().with(ROCK_VARIATION, RockVariation.LARGE), 1)
                            .build()))
            );
            CONFIGURED_FEATURES.put(type, ROCK_FEATURE);
        }
    }

    public static void initConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, "rock_mix", ROCK_MIX_FEATURE);
        register(context, "nether_gravel_rock", NETHER_GRAVEL_ROCK_FEATURE);

        for (RockType type : RockType.values()) {
            register(context, type.getName(), CONFIGURED_FEATURES.get(type));
        }
    }

    public static void initPlaced(BootstrapContext<PlacedFeature> context) {
        register(context, "rock_mix", ROCK_MIX_PLACED_FEATURE);
        register(context, "nether_gravel_rock", NETHER_GRAVEL_ROCK_PLACED_FEATURE);

        for (RockType type : RockType.values()) {
            PlacedFeature ROCK_PLACED_FEATURE = switch (type) {
                case STONE -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiersInvertedGroundCheck(3, 1, Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND, Blocks.RED_SAND, Blocks.END_STONE));
                case SANDSTONE -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.SAND, Blocks.SANDSTONE));
                case RED_SANDSTONE -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiers(7, 1, Blocks.RED_SAND, Blocks.RED_SANDSTONE));
                case ICE -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiers(3, 5, Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE));

                case NETHERRACK -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getNetherModifiers(90, 1, Blocks.NETHERRACK, Blocks.WARPED_NYLIUM, Blocks.CRIMSON_NYLIUM));
                case SOUL_SOIL -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getNetherModifiers(60, 1, Blocks.SOUL_SOIL, Blocks.SOUL_SAND));

                case END_STONE -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getEndModifiers(3, 1, type.getStoneBlock()));

                default -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, type.getStoneBlock()));
            };
            register(context, type.getName(), ROCK_PLACED_FEATURE);
        }
    }
}
