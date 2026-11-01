package eu.midnightdust.motschen.rocks.world.configured_feature;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.util.RockType;
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

import static eu.midnightdust.motschen.rocks.RocksMain.*;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class RockFeatures {
    //? if <=26.2 {
    /*private static final Map<RockType, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new HashMap<>();
     *///? } else {
    private static final Map<RockType, Feature> CONFIGURED_FEATURES = new HashMap<>();
    //? }

    public static List<PlacementModifier> getModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), List.of(groundBlocks)))));
    }
    public static List<PlacementModifier> getModifiersInvertedGroundCheck(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), List.of(groundBlocks))))));
    }
    public static List<PlacementModifier> getNetherModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountOnEveryLayerPlacement.of(6), BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), List.of(groundBlocks))),
                CountPlacement.of(count), RarityFilter.onAverageOnceEvery(rarity), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
    }
    public static List<PlacementModifier> getEndModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), List.of(groundBlocks)))));
    }

    //? if <=26.2 {
    /*public static ConfiguredFeature<?, ?> ROCK_MIX_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
     *///? } else {
    public static Feature ROCK_MIX_FEATURE = new SimpleBlockFeature(
    //? }
            new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(rocksByType.get(RockType.GRANITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.TINY), 10).add(rocksByType.get(RockType.GRANITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.GRANITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.MEDIUM), 5).add(rocksByType.get(RockType.GRANITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.LARGE), 1)
                    .add(rocksByType.get(RockType.DIORITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.TINY), 10).add(rocksByType.get(RockType.DIORITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.DIORITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.MEDIUM), 5).add(rocksByType.get(RockType.DIORITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.LARGE), 1)
                    .add(rocksByType.get(RockType.ANDESITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.TINY), 10).add(rocksByType.get(RockType.ANDESITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.ANDESITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.MEDIUM), 5).add(rocksByType.get(RockType.ANDESITE).defaultBlockState().setValue(ROCK_VARIATION,RockVariation.LARGE), 1)
                    .build()) /*? if <=26.2 {*//*)*//*?}*/
    );

    //? if <=26.2 {
    /*public static ConfiguredFeature<?, ?> NETHER_GRAVEL_ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
     *///? } else {
    public static Feature NETHER_GRAVEL_ROCK_FEATURE = new SimpleBlockFeature(
    //? }
            new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(rocksByType.get(RockType.GRAVEL).defaultBlockState().setValue(RocksMain.ROCK_VARIATION, RockVariation.TINY), 10)
                    .add(rocksByType.get(RockType.GRAVEL).defaultBlockState().setValue(RocksMain.ROCK_VARIATION,RockVariation.SMALL), 7)
                    .add(rocksByType.get(RockType.GRAVEL).defaultBlockState().setValue(RocksMain.ROCK_VARIATION,RockVariation.MEDIUM), 5)
                    .add(rocksByType.get(RockType.GRAVEL).defaultBlockState().setValue(RocksMain.ROCK_VARIATION,RockVariation.LARGE), 1).build())/*? if <=26.2 {*//*)*//*?}*/
    );

    public static PlacedFeature ROCK_MIX_PLACED_FEATURE = new PlacedFeature(Holder.direct(ROCK_MIX_FEATURE), getModifiersInvertedGroundCheck(3, 3, Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND, Blocks.RED_SAND, Blocks.END_STONE));
    public static PlacedFeature NETHER_GRAVEL_ROCK_PLACED_FEATURE = new PlacedFeature(Holder.direct(NETHER_GRAVEL_ROCK_FEATURE), getNetherModifiers(3, 1, Blocks.GRAVEL));

    public static void init() {
        for (RockType type : RockType.values()) {
            //? if <=26.2 {
            /*ConfiguredFeature<?, ?> ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
             *///? } else {
            Feature ROCK_FEATURE = new SimpleBlockFeature(
            //? }
                    new WeightedStateProvider(WeightedList.<BlockState>builder()
                            .add(rocksByType.get(type).defaultBlockState().setValue(ROCK_VARIATION, RockVariation.TINY), 10)
                            .add(rocksByType.get(type).defaultBlockState().setValue(ROCK_VARIATION, RockVariation.SMALL), 7)
                            .add(rocksByType.get(type).defaultBlockState().setValue(ROCK_VARIATION, RockVariation.MEDIUM), 5)
                            .add(rocksByType.get(type).defaultBlockState().setValue(ROCK_VARIATION, RockVariation.LARGE), 1)
                            .build())/*? if <=26.2 {*//*)*//*?}*/
            );
            CONFIGURED_FEATURES.put(type, ROCK_FEATURE);
        }
    }

    //? if <=26.2 {
    /*public static void initConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
     *///? } else {
    public static void initConfigured(BootstrapContext<Feature> context) {
        //? }
        register(context, "rock_mix", ROCK_MIX_FEATURE);
        register(context, "nether_gravel_rock", NETHER_GRAVEL_ROCK_FEATURE);

        for (RockType type : RockType.values()) {
            register(context, type.getName(), CONFIGURED_FEATURES.get(type));
        }
    }

    public static void initPlaced(BootstrapContext<PlacedFeature> context) {
        register(context, "rock_mix", ROCK_MIX_PLACED_FEATURE);
        register(context, "nether_gravel_rock", NETHER_GRAVEL_ROCK_PLACED_FEATURE);

        for (RockType type : RockType.values()) {
            PlacedFeature ROCK_PLACED_FEATURE = switch (type) {
                case STONE -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiersInvertedGroundCheck(2, 1, Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND, Blocks.RED_SAND, Blocks.END_STONE));
                case SANDSTONE -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.SAND, Blocks.SANDSTONE));
                case RED_SANDSTONE -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(7, 1, Blocks.RED_SAND, Blocks.RED_SANDSTONE));
                case ICE -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(3, 5, Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE));

                case NETHERRACK -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getNetherModifiers(5, 1, Blocks.NETHERRACK, Blocks.WARPED_NYLIUM, Blocks.CRIMSON_NYLIUM));
                case SOUL_SOIL -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getNetherModifiers(6, 1, Blocks.SOUL_SOIL, Blocks.SOUL_SAND));

                case END_STONE -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getEndModifiers(3, 1, type.getStoneBlock()));

                default -> new PlacedFeature(Holder.direct(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, type.getStoneBlock()));
            };
            register(context, type.getName(), ROCK_PLACED_FEATURE);
        }
    }
}
