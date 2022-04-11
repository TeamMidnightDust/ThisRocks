package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class RockFeatures {
    public static ConfiguredFeature<?, ?> ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> GRANITE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> DIORITE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> ANDESITE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> SAND_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> RED_SAND_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> END_STONE_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())
                    ));
    public static ConfiguredFeature<?, ?> GRAVEL_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()))
    );

    public static PlacedFeature ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.not(BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.ICE,Blocks.SAND,Blocks.RED_SAND,Blocks.END_STONE), new BlockPos(0, -1, 0)))))));
    public static PlacedFeature GRANITE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(GRANITE_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.granite_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.GRANITE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature DIORITE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(DIORITE_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.diorite_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.DIORITE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature ANDESITE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ANDESITE_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.andesite_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.ANDESITE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature SAND_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(SAND_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.sand_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.SAND, Blocks.SANDSTONE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature RED_SAND_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(RED_SAND_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.red_sand_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.RED_SAND, Blocks.RED_SANDSTONE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature END_STONE_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(END_STONE_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.end_stone_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.END_STONE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature GRAVEL_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(GRAVEL_ROCK_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.gravel_rock_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.GRAVEL), new BlockPos(0, -1, 0))))));

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
