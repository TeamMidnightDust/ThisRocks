package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableSet;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.world.RocksDecorators;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class NetherFeatures {

    public static ConfiguredFeature<?, ?> NETHERRACK_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.NetherrackRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .whitelist(ImmutableSet.of(Blocks.NETHERRACK,Blocks.WARPED_NYLIUM,Blocks.CRIMSON_NYLIUM)).cannotProject().build()).decorate(RocksDecorators.ROCK).repeat(128);
    public static ConfiguredFeature<?, ?> SOUL_SOIL_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.SoulSoilRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .whitelist(ImmutableSet.of(Blocks.SOUL_SOIL,Blocks.SOUL_SAND)).cannotProject().build()).decorate(RocksDecorators.ROCK).repeat(128);
    public static ConfiguredFeature<?, ?> NETHER_GRAVEL_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .add(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .whitelist(ImmutableSet.of(Blocks.GRAVEL)).cannotProject().build()).decorate(RocksDecorators.ROCK).repeat(128);
    public static ConfiguredFeature<?, ?> NETHER_GEYSER_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(RocksMain.NetherGeyser.getDefaultState()),SimpleBlockPlacer.INSTANCE)
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .whitelist(ImmutableSet.of(Blocks.NETHERRACK)).cannotProject().build())).decorate(RocksDecorators.ROCK).repeat(16);

    public static ConfiguredFeature<?, ?> WARPED_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(RocksMain.WarpedStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .whitelist(ImmutableSet.of(Blocks.WARPED_NYLIUM)).cannotProject().build()).decorate(RocksDecorators.ROCK).repeat(128);
    public static ConfiguredFeature<?, ?> CRIMSON_STICK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.MEDIUM), 5)
                            .add(RocksMain.CrimsonStick.getDefaultState().with(RocksMain.STICK_VARIATION,StickVariation.LARGE), 1).build()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .whitelist(ImmutableSet.of(Blocks.CRIMSON_NYLIUM)).cannotProject().build()).decorate(RocksDecorators.ROCK).repeat(128);

    public static void init() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;

        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "netherrack_rock"), NETHERRACK_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "soul_soil_rock"), SOUL_SOIL_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "nether_gravel_rock"), NETHER_GRAVEL_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "nether_geyser"), NETHER_GEYSER_FEATURE);

        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "warped_stick"), WARPED_STICK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "crimson_stick"), CRIMSON_STICK_FEATURE);
    }

}
