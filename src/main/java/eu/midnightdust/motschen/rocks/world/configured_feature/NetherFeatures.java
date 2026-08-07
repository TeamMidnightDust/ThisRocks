package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.Holder;
import net.minecraft.util.random.WeightedList;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.List;

import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class NetherFeatures {
    public static ConfiguredFeature<?, ?> NETHER_GEYSER_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                    new WeightedStateProvider(WeightedList.<BlockState>builder().add(RocksMain.NetherGeyser.defaultBlockState(), 1)))
    );

    public static PlacedFeature NETHER_GEYSER_PLACED_FEATURE = new PlacedFeature(Holder.direct(NETHER_GEYSER_FEATURE),
            List.of(CountPlacement.of(15), RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                    PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
                    BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                            BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.NETHERRACK))))));

    public static void initConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, "nether_geyser", NETHER_GEYSER_FEATURE);
    }
    public static void initPlaced(BootstrapContext<PlacedFeature> context) {
        register(context, "nether_geyser", NETHER_GEYSER_PLACED_FEATURE);
    }
}
