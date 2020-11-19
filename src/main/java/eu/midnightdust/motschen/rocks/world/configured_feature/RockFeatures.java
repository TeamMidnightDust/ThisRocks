package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableSet;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class RockFeatures {
    public static ConfiguredFeature<?, ?> ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.Rock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).blacklist(ImmutableSet.of(Blocks.ICE.getDefaultState(),Blocks.SAND.getDefaultState(),Blocks.RED_SAND.getDefaultState()))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> GRANITE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.GraniteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.GRANITE))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> DIORITE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.DioriteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.DIORITE))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> ANDESITE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.AndesiteRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.ANDESITE))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> SAND_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.SAND, Blocks.SANDSTONE))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> RED_SAND_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.RED_SAND, Blocks.RED_SANDSTONE))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> END_STONE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);
    public static ConfiguredFeature<?, ?> GRAVEL_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.GravelRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0).whitelist(ImmutableSet.of(Blocks.GRAVEL))
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE);

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
    }

}
