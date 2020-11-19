package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.world.feature.SnowFeature;
import eu.midnightdust.motschen.rocks.world.feature.UnderwaterFeature;
import net.minecraft.state.property.Properties;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class FeatureRegistry<FC extends FeatureConfig> {

    public static final UnderwaterFeature UNDERWATER_STARFISH_FEATURE;
    public static final UnderwaterFeature UNDERWATER_SEASHELL_FEATURE;
    public static final SnowFeature SNOWY_GEYSER_FEATURE;

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, name, feature);
    }

    private static final WeightedBlockStateProvider StarfishStates = new WeightedBlockStateProvider()
            .addState(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION, StarfishVariation.RED).with(Properties.WATERLOGGED, true), 6)
            .addState(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.PINK).with(Properties.WATERLOGGED, true), 7)
            .addState(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.ORANGE).with(Properties.WATERLOGGED, true), 2);

    private static final WeightedBlockStateProvider SeashellStates = new WeightedBlockStateProvider()
            .addState(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION, SeashellVariation.YELLOW).with(Properties.WATERLOGGED, true), 7)
            .addState(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.PINK).with(Properties.WATERLOGGED, true), 2)
            .addState(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.WHITE).with(Properties.WATERLOGGED, true), 6);

    private static final WeightedBlockStateProvider GeyserStates = new WeightedBlockStateProvider()
            .addState(RocksMain.Geyser.getDefaultState().with(Properties.SNOWY, true), 1);

    static {
        UNDERWATER_STARFISH_FEATURE = register("underwater_starfish", new UnderwaterFeature(ProbabilityConfig.CODEC, StarfishStates));
        UNDERWATER_SEASHELL_FEATURE = register("underwater_seashell", new UnderwaterFeature(ProbabilityConfig.CODEC, SeashellStates));
        SNOWY_GEYSER_FEATURE = register("snowy_geyser", new SnowFeature(ProbabilityConfig.CODEC, GeyserStates));
    }
}
