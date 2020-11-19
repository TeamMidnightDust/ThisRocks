package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableSet;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class StickFeatures {
    public static ConfiguredFeature<?, ?> OAK_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.OakStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .addState(RocksMain.OakStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .addState(RocksMain.OakStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> SPRUCE_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.SpruceStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .addState(RocksMain.SpruceStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .addState(RocksMain.SpruceStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1)
                            .addState(RocksMain.Pinecone.getDefaultState(), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> BIRCH_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.BirchStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .addState(RocksMain.BirchStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .addState(RocksMain.BirchStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> ACACIA_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.AcaciaStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .addState(RocksMain.AcaciaStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .addState(RocksMain.AcaciaStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> JUNGLE_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.JungleStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .addState(RocksMain.JungleStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .addState(RocksMain.JungleStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> DARK_OAK_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.DarkOakStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.SMALL), 7)
                            .addState(RocksMain.DarkOakStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .addState(RocksMain.DarkOakStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);

    public static void init() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "oak_stick"), OAK_STICK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "spruce_stick"), SPRUCE_STICK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "birch_stick"), BIRCH_STICK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "acacia_stick"), ACACIA_STICK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "jungle_stick"), JUNGLE_STICK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "dark_oak_stick"), DARK_OAK_STICK_FEATURE);
    }

}
