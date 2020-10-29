package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
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
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE).applyChance(1);
    public static ConfiguredFeature<?, ?> SAND_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.SandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE).applyChance(1);
    public static ConfiguredFeature<?, ?> RED_SAND_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.RedSandRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE).applyChance(1);
    public static ConfiguredFeature<?, ?> END_STONE_ROCK_FEATURE = Feature.RANDOM_PATCH.configure(
            (new RandomPatchFeatureConfig.Builder(
                    new WeightedBlockStateProvider()
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.TINY), 10)
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                            .addState(RocksMain.EndstoneRock.getDefaultState().with(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1).spreadX(0).spreadY(0).spreadZ(0)
                    .build()).decorate(ConfiguredFeatures.Decorators.FIRE).applyChance(1);

    public static void init() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "rock"), ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "sand_rock"), SAND_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "red_sand_rock"), RED_SAND_ROCK_FEATURE);
        Registry.register(registry, new Identifier(RocksMain.MOD_ID, "endstone_rock"), END_STONE_ROCK_FEATURE);
    }

}
