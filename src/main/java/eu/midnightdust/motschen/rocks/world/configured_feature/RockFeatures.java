package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.world.RocksDecorators;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class RockFeatures {
    public static ConfiguredFeature<?, ?> ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.not(BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.ICE,Blocks.SAND,Blocks.RED_SAND), new BlockPos(0, -1, 0)))))));
    public static ConfiguredFeature<?, ?> GRANITE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.GRANITE), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> DIORITE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.DIORITE), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> ANDESITE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.ANDESITE), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> SAND_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.SAND, Blocks.SANDSTONE), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> RED_SAND_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.RED_SAND, Blocks.RED_SANDSTONE), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> END_STONE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())
                    )).withPlacement()));
    public static ConfiguredFeature<?, ?> GRAVEL_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(128, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.GRAVEL), new BlockPos(0, -1, 0))))));

    public static PlacedFeature ROCK_PLACED_FEATURE = ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static PlacedFeature GRANITE_ROCK_PLACED_FEATURE = GRANITE_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static PlacedFeature DIORITE_ROCK_PLACED_FEATURE = DIORITE_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static PlacedFeature ANDESITE_ROCK_PLACED_FEATURE = ANDESITE_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static PlacedFeature SAND_ROCK_PLACED_FEATURE = SAND_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static PlacedFeature RED_SAND_ROCK_PLACED_FEATURE = RED_SAND_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static PlacedFeature END_STONE_ROCK_PLACED_FEATURE = END_STONE_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static PlacedFeature GRAVEL_ROCK_PLACED_FEATURE = GRAVEL_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static void init() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "rock"), ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "granite_rock"), GRANITE_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "diorite_rock"), DIORITE_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "andesite_rock"), ANDESITE_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "sand_rock"), SAND_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "red_sand_rock"), RED_SAND_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "endstone_rock"), END_STONE_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "gravel_rock"), GRAVEL_ROCK_FEATURE);

        Registry<PlacedFeature> placedRegistry = BuiltinRegistries.PLACED_FEATURE;
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "rock"), ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "granite_rock"), GRANITE_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "diorite_rock"), DIORITE_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "andesite_rock"), ANDESITE_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "sand_rock"), SAND_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "red_sand_rock"), RED_SAND_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "endstone_rock"), END_STONE_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "gravel_rock"), GRAVEL_ROCK_PLACED_FEATURE);
    }

}
