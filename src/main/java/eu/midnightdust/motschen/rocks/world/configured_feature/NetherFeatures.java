package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class NetherFeatures {

    public static ConfiguredFeature<?, ?> NETHERRACK_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> SOUL_SOIL_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> NETHER_GRAVEL_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> NETHER_GEYSER_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(RocksMain.NetherGeyser.getDefaultState(), 1)))
    );
    public static ConfiguredFeature<?, ?> WARPED_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> CRIMSON_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
    );

    public static PlacedFeature NETHERRACK_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(NETHERRACK_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.netherrack_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate .bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.NETHERRACK,Blocks.WARPED_NYLIUM,Blocks.CRIMSON_NYLIUM))))));
    public static PlacedFeature SOUL_SOIL_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(SOUL_SOIL_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.soul_soil_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.SOUL_SOIL,Blocks.SOUL_SAND))))));
    public static PlacedFeature NETHER_GRAVEL_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(NETHER_GRAVEL_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.gravel_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.GRAVEL))))));
    public static PlacedFeature NETHER_GEYSER_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(NETHER_GEYSER_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.nether_geyser_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.NETHERRACK))))));
    public static PlacedFeature WARPED_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(WARPED_STICK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.warped_stick_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.WARPED_NYLIUM))))));
    public static PlacedFeature CRIMSON_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(CRIMSON_STICK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.crimson_stick_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.CRIMSON_NYLIUM))))));

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
