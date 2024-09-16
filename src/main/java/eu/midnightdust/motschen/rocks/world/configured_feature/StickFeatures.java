package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WoodType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

import static eu.midnightdust.motschen.rocks.RocksMain.sticksByType;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class StickFeatures {
    public static ConfiguredFeature<?, ?> OAK_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(sticksByType.get(WoodType.OAK).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .add(sticksByType.get(WoodType.OAK).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(WoodType.OAK).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
            );
    public static ConfiguredFeature<?, ?> SPRUCE_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(sticksByType.get(WoodType.SPRUCE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .add(sticksByType.get(WoodType.SPRUCE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(WoodType.SPRUCE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1)
                            .add(RocksMain.Pinecone.getDefaultState(), 1).build()))
            );
    public static ConfiguredFeature<?, ?> BIRCH_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(sticksByType.get(WoodType.BIRCH).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .add(sticksByType.get(WoodType.BIRCH).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(WoodType.BIRCH).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
            );
    public static ConfiguredFeature<?, ?> ACACIA_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(sticksByType.get(WoodType.ACACIA).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .add(sticksByType.get(WoodType.ACACIA).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(WoodType.ACACIA).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
            );
    public static ConfiguredFeature<?, ?> JUNGLE_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(sticksByType.get(WoodType.JUNGLE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .add(sticksByType.get(WoodType.JUNGLE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(WoodType.JUNGLE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
            );
    public static ConfiguredFeature<?, ?> DARK_OAK_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(sticksByType.get(WoodType.DARK_OAK).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .add(sticksByType.get(WoodType.DARK_OAK).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(WoodType.DARK_OAK).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
            );
    public static ConfiguredFeature<?, ?> MANGROVE_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
            new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                    .add(sticksByType.get(WoodType.MANGROVE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                    .add(sticksByType.get(WoodType.MANGROVE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                    .add(sticksByType.get(WoodType.MANGROVE).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> CHERRY_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
            new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                    .add(sticksByType.get(WoodType.CHERRY).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                    .add(sticksByType.get(WoodType.CHERRY).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                    .add(sticksByType.get(WoodType.CHERRY).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
    );
    public static ConfiguredFeature<?, ?> BAMBOO_STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
            new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                    .add(sticksByType.get(WoodType.BAMBOO).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                    .add(sticksByType.get(WoodType.BAMBOO).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                    .add(sticksByType.get(WoodType.BAMBOO).getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()))
    );

    public static final List<PlacementModifier> modifiers = List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.GRASS_BLOCK, Blocks.MUD, Blocks.PODZOL)))));
    public static PlacedFeature OAK_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(OAK_STICK_FEATURE), modifiers);
    public static PlacedFeature SPRUCE_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(SPRUCE_STICK_FEATURE), modifiers);
    public static PlacedFeature BIRCH_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(BIRCH_STICK_FEATURE), modifiers);
    public static PlacedFeature ACACIA_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ACACIA_STICK_FEATURE), modifiers);
    public static PlacedFeature JUNGLE_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(JUNGLE_STICK_FEATURE), modifiers);
    public static PlacedFeature DARK_OAK_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(DARK_OAK_STICK_FEATURE), modifiers);
    public static PlacedFeature CHERRY_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(CHERRY_STICK_FEATURE), modifiers);
    public static PlacedFeature MANGROVE_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(MANGROVE_STICK_FEATURE), modifiers);
    public static PlacedFeature BAMBOO_STICK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(BAMBOO_STICK_FEATURE), modifiers);

    public static void initConfigured(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, "oak_stick", OAK_STICK_FEATURE);
        register(context, "spruce_stick", SPRUCE_STICK_FEATURE);
        register(context, "birch_stick", BIRCH_STICK_FEATURE);
        register(context, "acacia_stick", ACACIA_STICK_FEATURE);
        register(context, "jungle_stick", JUNGLE_STICK_FEATURE);
        register(context, "dark_oak_stick", DARK_OAK_STICK_FEATURE);
        register(context, "cherry_stick", CHERRY_STICK_FEATURE);
        register(context, "mangrove_stick", MANGROVE_STICK_FEATURE);
        register(context, "bamboo_stick", BAMBOO_STICK_FEATURE);
    }
    public static void initPlaced(Registerable<PlacedFeature> context) {
        register(context, "oak_stick", OAK_STICK_PLACED_FEATURE);
        register(context, "spruce_stick", SPRUCE_STICK_PLACED_FEATURE);
        register(context, "birch_stick", BIRCH_STICK_PLACED_FEATURE);
        register(context, "acacia_stick", ACACIA_STICK_PLACED_FEATURE);
        register(context, "jungle_stick", JUNGLE_STICK_PLACED_FEATURE);
        register(context, "dark_oak_stick", DARK_OAK_STICK_PLACED_FEATURE);
        register(context, "cherry_stick", CHERRY_STICK_PLACED_FEATURE);
        register(context, "mangrove_stick", MANGROVE_STICK_PLACED_FEATURE);
        register(context, "bamboo_stick", BAMBOO_STICK_PLACED_FEATURE);
    }
}
