package eu.midnightdust.motschen.rocks.world.configured_feature;

import com.google.common.collect.ImmutableList;
import eu.midnightdust.motschen.rocks.RocksMain;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
//? if <=26.2 {
/*import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
*///? } else {
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
//? }
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import java.util.List;

import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class NetherFeatures {
    //? if <=26.2 {
    /*public static ConfiguredFeature<?, ?> NETHER_GEYSER_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
    *///? } else {
    public static Feature NETHER_GEYSER_FEATURE = new SimpleBlockFeature(
    //? }
                    //~ if >= 26.3 'SimpleStateProvider.simple' -> 'new SimpleStateProvider'
                    new SimpleStateProvider(RocksMain.NetherGeyser.defaultBlockState()) /*? if <=26.2 {*//*)*//*?}*/
    );

    public static PlacedFeature NETHER_GEYSER_PLACED_FEATURE = new PlacedFeature(Holder.direct(NETHER_GEYSER_FEATURE),
            List.of(CountPlacement.of(15), RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                    PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
                    BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                            BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), ImmutableList.of(Blocks.NETHERRACK))))));

    //? if <=26.2 {
    /*public static void initConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
     *///? } else {
    public static void initConfigured(BootstrapContext<Feature> context) {
    //? }
        register(context, "nether_geyser", NETHER_GEYSER_FEATURE);
    }
    public static void initPlaced(BootstrapContext<PlacedFeature> context) {
        register(context, "nether_geyser", NETHER_GEYSER_PLACED_FEATURE);
    }
}
