package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class NetherFeatures {
    public static ConfiguredFeature<?, ?> NETHER_GEYSER_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(RocksMain.NetherGeyser.getDefaultState(), 1)))
    );

    public static PlacedFeature NETHER_GEYSER_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(NETHER_GEYSER_FEATURE),
            List.of(CountPlacementModifier.of(15), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(),
                    PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of(),
                    BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR,
                            BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.NETHERRACK))))));

    public static void initConfigured(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, "nether_geyser", NETHER_GEYSER_FEATURE);
    }
    public static void initPlaced(Registerable<PlacedFeature> context) {
        register(context, "nether_geyser", NETHER_GEYSER_PLACED_FEATURE);
    }
}
