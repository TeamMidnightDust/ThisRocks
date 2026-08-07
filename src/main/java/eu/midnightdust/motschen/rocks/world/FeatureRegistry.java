package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.world.feature.SnowFeature;
import eu.midnightdust.motschen.rocks.world.feature.UnderwaterFeature;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class FeatureRegistry {

    public static final UnderwaterFeature UNDERWATER_STARFISH_FEATURE;
    public static final UnderwaterFeature UNDERWATER_SEASHELL_FEATURE;
    public static final SnowFeature SNOWY_GEYSER_FEATURE;

    public static void init() {}

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(BuiltInRegistries.FEATURE, name, feature);
    }

    private static final WeightedStateProvider StarfishStates = new WeightedStateProvider(WeightedList.<BlockState>builder()
            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION, StarfishVariation.RED).with(BlockStateProperties.WATERLOGGED, true), 6)
            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.PINK).with(BlockStateProperties.WATERLOGGED, true), 7)
            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.ORANGE).with(BlockStateProperties.WATERLOGGED, true), 2).build());

    private static final WeightedStateProvider SeashellStates = new WeightedStateProvider(WeightedList.<BlockState>builder()
            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION, SeashellVariation.YELLOW).with(BlockStateProperties.WATERLOGGED, true), 7)
            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.PINK).with(BlockStateProperties.WATERLOGGED, true), 2)
            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.WHITE).with(BlockStateProperties.WATERLOGGED, true), 6).build());

    private static final WeightedStateProvider GeyserStates = new WeightedStateProvider(WeightedList.<BlockState>builder()
            .add(RocksMain.Geyser.getDefaultState().with(BlockStateProperties.SNOWY, true), 1).build());

    static {
        UNDERWATER_STARFISH_FEATURE = register("underwater_starfish", new UnderwaterFeature(ProbabilityFeatureConfiguration.CODEC, StarfishStates));
        UNDERWATER_SEASHELL_FEATURE = register("underwater_seashell", new UnderwaterFeature(ProbabilityFeatureConfiguration.CODEC, SeashellStates));
        SNOWY_GEYSER_FEATURE = register("snowy_geyser", new SnowFeature(ProbabilityFeatureConfiguration.CODEC, GeyserStates));
    }
}
