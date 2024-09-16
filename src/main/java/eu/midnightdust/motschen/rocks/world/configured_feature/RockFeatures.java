package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

import static eu.midnightdust.motschen.rocks.RocksMain.rocksByType;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class RockFeatures {
    public static ConfiguredFeature<?, ?> ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> GRANITE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.GRANITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.GRANITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.GRANITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.GRANITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> DIORITE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.DIORITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.DIORITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.DIORITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.DIORITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> ANDESITE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.ANDESITE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> SAND_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> RED_SAND_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.RED_SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.RED_SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.RED_SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.RED_SANDSTONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> END_STONE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.END_STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.END_STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.END_STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.END_STONE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())
                    ));
    public static ConfiguredFeature<?, ?> GRAVEL_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(RockType.GRAVEL).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> ICE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
            new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                    .add(rocksByType.get(RockType.ICE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                    .add(rocksByType.get(RockType.ICE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.ICE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                    .add(rocksByType.get(RockType.ICE).getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );

    public static PlacedFeature ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.not(BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND, Blocks.RED_SAND, Blocks.END_STONE)))))));
    public static PlacedFeature GRANITE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(GRANITE_ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.GRANITE))))));
    public static PlacedFeature DIORITE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(DIORITE_ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.DIORITE))))));
    public static PlacedFeature ANDESITE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ANDESITE_ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.ANDESITE))))));
    public static PlacedFeature SAND_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(SAND_ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.SAND, Blocks.SANDSTONE))))));
    public static PlacedFeature RED_SAND_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(RED_SAND_ROCK_FEATURE), List.of(CountPlacementModifier.of(7), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.RED_SAND, Blocks.RED_SANDSTONE))))));
    public static PlacedFeature END_STONE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(END_STONE_ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.END_STONE))))));
    public static PlacedFeature GRAVEL_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(GRAVEL_ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.GRAVEL))))));
    public static PlacedFeature ICE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ICE_ROCK_FEATURE), List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE))))));

    public static void initConfigured(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, "rock", ROCK_FEATURE);
        register(context, "granite_rock", GRANITE_ROCK_FEATURE);
        register(context, "diorite_rock", DIORITE_ROCK_FEATURE);
        register(context, "andesite_rock", ANDESITE_ROCK_FEATURE);
        register(context, "sand_rock", SAND_ROCK_FEATURE);
        register(context, "red_sand_rock", RED_SAND_ROCK_FEATURE);
        register(context, "end_stone_rock", END_STONE_ROCK_FEATURE);
        register(context, "gravel_rock", GRAVEL_ROCK_FEATURE);
        register(context, "ice_rock", ICE_ROCK_FEATURE);
    }
    public static void initPlaced(Registerable<PlacedFeature> context) {
        register(context, "rock", ROCK_PLACED_FEATURE);
        register(context, "granite_rock", GRANITE_ROCK_PLACED_FEATURE);
        register(context, "diorite_rock", DIORITE_ROCK_PLACED_FEATURE);
        register(context, "andesite_rock", ANDESITE_ROCK_PLACED_FEATURE);
        register(context, "sand_rock", SAND_ROCK_PLACED_FEATURE);
        register(context, "red_sand_rock", RED_SAND_ROCK_PLACED_FEATURE);
        register(context, "end_stone_rock", END_STONE_ROCK_PLACED_FEATURE);
        register(context, "gravel_rock", GRAVEL_ROCK_PLACED_FEATURE);
        register(context, "ice_rock", ICE_ROCK_PLACED_FEATURE);
    }
}
