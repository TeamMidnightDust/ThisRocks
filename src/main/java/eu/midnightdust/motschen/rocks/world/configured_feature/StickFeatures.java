package eu.midnightdust.motschen.rocks.world.configured_feature;

import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.midnightdust.motschen.rocks.RocksMain.STICK_VARIATION;
import static eu.midnightdust.motschen.rocks.RocksMain.sticksByType;
import static eu.midnightdust.motschen.rocks.util.RegistryUtil.register;

public class StickFeatures {
    private static final Map<StickType, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new HashMap<>();

    public static List<PlacementModifier> getModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.of(rarity),
                InSquarePlacement.of(), PlacementUtils.WORLD_SURFACE_WG_HEIGHTMAP, BiomeFilter.of(),
                BlockPredicateFilter.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0),
                        groundBlocks))));
    }
    public static List<PlacementModifier> getNetherModifiers(int count, int rarity, Block... groundBlocks) {
        return List.of(CountPlacement.of(count), RarityFilter.of(rarity),
                InSquarePlacement.of(), PlacementUtils.BOTTOM_TO_TOP_RANGE, BiomeFilter.of(),
                BlockPredicateFilter.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0),
                        groundBlocks))));
    }

    public static void init() {
        for (StickType type : StickType.values()) {
            ConfiguredFeature<?, ?> STICK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                    new WeightedStateProvider(WeightedList.<BlockState>builder()
                            .add(sticksByType.get(type).getDefaultState().with(STICK_VARIATION, StickVariation.SMALL), 7)
                            .add(sticksByType.get(type).getDefaultState().with(STICK_VARIATION, StickVariation.MEDIUM), 5)
                            .add(sticksByType.get(type).getDefaultState().with(STICK_VARIATION, StickVariation.LARGE), 1).build()))
            );
            CONFIGURED_FEATURES.put(type, STICK_FEATURE);
        }
    }

    public static void initConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        for (StickType type : StickType.values()) {
            register(context, type.getName()+"_stick", CONFIGURED_FEATURES.get(type));
        }
    }

    public static void initPlaced(BootstrapContext<PlacedFeature> context) {
        for (StickType type : StickType.values()) {
            PlacedFeature STICK_PLACED_FEATURE = switch (type) {
                case CRIMSON -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getNetherModifiers(90, 1, Blocks.CRIMSON_NYLIUM));
                case WARPED -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getNetherModifiers(90, 1, Blocks.WARPED_NYLIUM));
                case PALE_OAK -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiers(20, 1, Blocks.GRASS_BLOCK, Blocks.PALE_MOSS_BLOCK));
                case SPRUCE -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.GRASS_BLOCK, Blocks.SNOW_BLOCK, Blocks.PODZOL));
                default -> new PlacedFeature(Holder.of(CONFIGURED_FEATURES.get(type)), getModifiers(3, 1, Blocks.GRASS_BLOCK, Blocks.MUD, Blocks.PODZOL));
            };
            register(context, type.getName() + "_stick", STICK_PLACED_FEATURE);
        }
    }
}
