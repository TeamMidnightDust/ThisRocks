package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.world.FeatureRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class MiscFeatures {
    public static List<PlacementModifier> placementModifiers = List.of(RarityFilterPlacementModifier.of(1),
            SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    private static final ConfiguredFeature<?, ?> SEASHELL_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.YELLOW), 7)
                            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.PINK), 2)
                            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.WHITE), 6).build()
            )));
    public static ConfiguredFeature<?, ?> STARFISH_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.RED), 2)
                            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.PINK), 6)
                            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.ORANGE), 7).build()))
    );

    public static ConfiguredFeature<?, ?> UNDERWATER_STARFISH_FEATURE = new ConfiguredFeature<>(FeatureRegistry.UNDERWATER_STARFISH_FEATURE, new ProbabilityConfig(1));
    public static ConfiguredFeature<?, ?> UNDERWATER_SEASHELL_FEATURE = new ConfiguredFeature<>(FeatureRegistry.UNDERWATER_SEASHELL_FEATURE, new ProbabilityConfig(1));
    public static ConfiguredFeature<?, ?> SNOWY_GEYSER_FEATURE = new ConfiguredFeature<>(FeatureRegistry.SNOWY_GEYSER_FEATURE, new ProbabilityConfig(1));

    public static PlacedFeature SEASHELL_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(SEASHELL_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.seashell_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.SAND, Blocks.SANDSTONE, Blocks.RED_SAND, Blocks.RED_SANDSTONE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature STARFISH_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(STARFISH_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.starfish_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(ImmutableList.of(Blocks.SAND, Blocks.SANDSTONE, Blocks.RED_SAND, Blocks.RED_SANDSTONE), new BlockPos(0, -1, 0))))));
    public static PlacedFeature UNDERWATER_SEASHELL_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(UNDERWATER_SEASHELL_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.underwater_seashell_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static PlacedFeature UNDERWATER_STARFISH_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(UNDERWATER_STARFISH_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.underwater_starfish_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static PlacedFeature SNOWY_GEYSER_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(SNOWY_GEYSER_FEATURE), List.of(CountPlacementModifier.of(RocksConfig.geyser_count), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));

    public static void init() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "seashell"), SEASHELL_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "starfish"), STARFISH_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "underwater_starfish"), UNDERWATER_STARFISH_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "underwater_seashell"), UNDERWATER_SEASHELL_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "snowy_geyser"), SNOWY_GEYSER_FEATURE);

        Registry<PlacedFeature> placedRegistry = BuiltinRegistries.PLACED_FEATURE;
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "seashell"), SEASHELL_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "starfish"), STARFISH_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "underwater_seashell"), UNDERWATER_SEASHELL_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "underwater_starfish"), UNDERWATER_STARFISH_PLACED_FEATURE);
        Registry.register(placedRegistry, new Identifier(RocksMain.MOD_ID, "snowy_geyser"),SNOWY_GEYSER_PLACED_FEATURE);
    }

}
