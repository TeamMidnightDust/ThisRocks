package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableSet;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.world.FeatureRegistry;
import eu.midnightdust.motschen.rocks.world.RocksDecorators;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class MiscFeatures {
    public static ConfiguredFeature<?, ?> SEASHELL_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.YELLOW), 7)
                            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.PINK), 2)
                            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.WHITE), 6).build()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.SAND, Blocks.SANDSTONE, Blocks.RED_SAND, Blocks.RED_SANDSTONE))
                    .build()).decorate(RocksDecorators.ROCK);
    public static ConfiguredFeature<?, ?> STARFISH_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.RED), 2)
                            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.PINK), 6)
                            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.ORANGE), 7).build()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.SAND, Blocks.SANDSTONE, Blocks.RED_SAND, Blocks.RED_SANDSTONE))
                    .build()).decorate(RocksDecorators.ROCK);

    public static ConfiguredFeature<?, ?> UNDERWATER_STARFISH_FEATURE = FeatureRegistry.UNDERWATER_STARFISH_FEATURE.configure(new ProbabilityConfig(1));
    public static ConfiguredFeature<?, ?> UNDERWATER_SEASHELL_FEATURE = FeatureRegistry.UNDERWATER_SEASHELL_FEATURE.configure(new ProbabilityConfig(1));
    public static ConfiguredFeature<?, ?> SNOWY_GEYSER_FEATURE = FeatureRegistry.SNOWY_GEYSER_FEATURE.configure(new ProbabilityConfig(1)).decorate(RocksDecorators.ROCK);

    public static void init() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "seashell"), SEASHELL_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "starfish"), STARFISH_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "underwater_starfish"), UNDERWATER_STARFISH_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "underwater_seashell"), UNDERWATER_SEASHELL_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "snowy_geyser"), SNOWY_GEYSER_FEATURE);
    }

}
