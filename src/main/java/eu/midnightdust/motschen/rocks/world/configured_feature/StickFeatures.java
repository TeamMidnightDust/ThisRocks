package eu.midnightdust.motschen.rocks.world.configured_feature;

import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
//? if <=26.2 {
/*import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
*///? } else {
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
//? }
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.midnightdust.motschen.rocks.RocksMain.STICK_VARIATION;
import static eu.midnightdust.motschen.rocks.RocksMain.sticksByType;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class StickFeatures {
    //? if <=26.2 {
    /*private static final Map<StickType, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new HashMap<>();
     *///? } else {
    private static final Map<StickType, Feature> CONFIGURED_FEATURES = new HashMap<>();
    //? }

    public static List<PlacementModifier> getModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), List.of(groundBlocks)))));
    }
    public static List<PlacementModifier> getNetherModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountOnEveryLayerPlacement.of(6), BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), List.of(groundBlocks))),
                CountPlacement.of(count), RarityFilter.onAverageOnceEvery(rarity), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
    }

    public static void init() {
        for (StickType type : StickType.values()) {
            //? if <=26.2 {
            /*ConfiguredFeature<?, ?> STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
             *///? } else {
            Feature STICK_FEATURE = new SimpleBlockFeature(
            //? }
                    new WeightedStateProvider(WeightedList.<BlockState>builder()
                            .add(sticksByType.get(type).defaultBlockState().setValue(STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(sticksByType.get(type).defaultBlockState().setValue(STICK_VARIATION, StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(type).defaultBlockState().setValue(STICK_VARIATION, StickVariation.LARGE), 1).build())/*? if <=26.2 {*//*)*//*?}*/
            );
            CONFIGURED_FEATURES.put(type, STICK_FEATURE);
        }
    }

    //? if <=26.2 {
    /*public static void initConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
     *///? } else {
    public static void initConfigured(BootstrapContext<Feature> context) {
        //? }
        for (StickType type : StickType.values()) {
            register(context, type.getName()+"_stick", CONFIGURED_FEATURES.get(type));
        }
    }

    public static void initPlaced(BootstrapContext<PlacedFeature> context) {
        for (StickType type : StickType.values()) {
            PlacedFeature STICK_PLACED_FEATURE = switch (type) {
                case CRIMSON -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getNetherModifiers(9, 1, Blocks.CRIMSON_NYLIUM));
                case WARPED -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getNetherModifiers(9, 1, Blocks.WARPED_NYLIUM));
                case PALE_OAK -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(20, 1, Blocks.GRASS_BLOCK, Blocks.PALE_MOSS_BLOCK));
                case SPRUCE -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.GRASS_BLOCK, Blocks.SNOW_BLOCK, Blocks.PODZOL));
                //? if >= 26.3
                case POPLAR -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(10, 1, Blocks.GRASS_BLOCK));
                default -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.GRASS_BLOCK, Blocks.MUD, Blocks.PODZOL));
            };
            register(context, type.getName() + "_stick", STICK_PLACED_FEATURE);
        }
    }
}
