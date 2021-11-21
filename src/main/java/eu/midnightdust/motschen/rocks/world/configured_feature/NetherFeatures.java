package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
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

public class NetherFeatures {

    public static ConfiguredFeature<?, ?> NETHERRACK_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(1024, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.NETHERRACK,Blocks.WARPED_NYLIUM,Blocks.CRIMSON_NYLIUM), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> SOUL_SOIL_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(1024, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.SOUL_SOIL,Blocks.SOUL_SAND), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> NETHER_GRAVEL_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(1024, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.GRAVEL), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> NETHER_GEYSER_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(1024, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(RocksMain.NetherGeyser.getDefaultState(), 1))))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.NETHERRACK), new BlockPos(0, -1, 0))))));

    public static ConfiguredFeature<?, ?> WARPED_STICK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(1024, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.WARPED_NYLIUM), new BlockPos(0, -1, 0))))));
    public static ConfiguredFeature<?, ?> CRIMSON_STICK_FEATURE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(1024, 0, 0,() -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build())))
            .withBlockPredicateFilter(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.CRIMSON_NYLIUM), new BlockPos(0, -1, 0))))));

    public static PlacedFeature NETHERRACK_ROCK_PLACED_FEATURE = NETHERRACK_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
    public static PlacedFeature SOUL_SOIL_ROCK_PLACED_FEATURE = SOUL_SOIL_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
    public static PlacedFeature NETHER_GRAVEL_ROCK_PLACED_FEATURE = NETHER_GRAVEL_ROCK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
    public static PlacedFeature NETHER_GEYSER_PLACED_FEATURE = NETHER_GEYSER_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
    public static PlacedFeature WARPED_STICK_PLACED_FEATURE = WARPED_STICK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
    public static PlacedFeature CRIMSON_STICK_PLACED_FEATURE = CRIMSON_STICK_FEATURE.withPlacement(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());

    public static void init() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "netherrack_rock"), NETHERRACK_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "soul_soil_rock"), SOUL_SOIL_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "nether_gravel_rock"), NETHER_GRAVEL_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "nether_geyser"), NETHER_GEYSER_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "warped_stick"), WARPED_STICK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "crimson_stick"), CRIMSON_STICK_FEATURE);

        Registry<PlacedFeature> placedRegistry = BuiltinRegistries.PLACED_FEATURE;
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "netherrack_rock"), NETHERRACK_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "soul_soil_rock"), SOUL_SOIL_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "nether_gravel_rock"), NETHER_GRAVEL_ROCK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "nether_geyser"), NETHER_GEYSER_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "warped_stick"), WARPED_STICK_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "crimson_stick"), CRIMSON_STICK_PLACED_FEATURE);
    }

}
