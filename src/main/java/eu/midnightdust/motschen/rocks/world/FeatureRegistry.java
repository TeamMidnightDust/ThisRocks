package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.world.feature.SnowFeature;
import eu.midnightdust.motschen.rocks.world.feature.UnderwaterFeature;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class FeatureRegistry {

    public static final UnderwaterFeature UNDERWATER_STARFISH_FEATURE;
    public static final UnderwaterFeature UNDERWATER_SEASHELL_FEATURE;
    public static final SnowFeature SNOWY_GEYSER_FEATURE;

    public static void init() {}

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, name, feature);
    }

    private static final WeightedBlockStateProvider StarfishStates = new WeightedBlockStateProvider(Pool.<BlockState>builder()
            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION, StarfishVariation.RED).with(Properties.WATERLOGGED, true), 6)
            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.PINK).with(Properties.WATERLOGGED, true), 7)
            .add(RocksMain.Starfish.getDefaultState().with(RocksMain.STARFISH_VARIATION,StarfishVariation.ORANGE).with(Properties.WATERLOGGED, true), 2).build());

    private static final WeightedBlockStateProvider SeashellStates = new WeightedBlockStateProvider(Pool.<BlockState>builder()
            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION, SeashellVariation.YELLOW).with(Properties.WATERLOGGED, true), 7)
            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.PINK).with(Properties.WATERLOGGED, true), 2)
            .add(RocksMain.Seashell.getDefaultState().with(RocksMain.SEASHELL_VARIATION,SeashellVariation.WHITE).with(Properties.WATERLOGGED, true), 6).build());

    private static final WeightedBlockStateProvider GeyserStates = new WeightedBlockStateProvider(Pool.<BlockState>builder()
            .add(RocksMain.Geyser.getDefaultState().with(Properties.SNOWY, true), 1).build());

    static {
        UNDERWATER_STARFISH_FEATURE = register("underwater_starfish", new UnderwaterFeature(ProbabilityConfig.CODEC, StarfishStates));
        UNDERWATER_SEASHELL_FEATURE = register("underwater_seashell", new UnderwaterFeature(ProbabilityConfig.CODEC, SeashellStates));
        SNOWY_GEYSER_FEATURE = register("snowy_geyser", new SnowFeature(ProbabilityConfig.CODEC, GeyserStates));
    }
}
