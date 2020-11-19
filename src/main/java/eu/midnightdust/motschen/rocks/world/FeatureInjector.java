package eu.midnightdust.motschen.rocks.world;

import com.google.common.collect.Lists;
import eu.midnightdust.motschen.rocks.mixin.GenerationSettingsAccessorMixin;
import eu.midnightdust.motschen.rocks.world.configured_feature.MiscFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.NetherFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.RockFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.StickFeatures;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FeatureInjector {

    public static void init() {
        BuiltinRegistries.BIOME.forEach(FeatureInjector::addRockToBiome);
        RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, identifier, biome) -> addRockToBiome(biome));
    }

    private static void addRockToBiome(Biome biome) {
        // Rocks
        if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome.getCategory() != Biome.Category.BEACH && biome.getCategory() != Biome.Category.DESERT && biome.getCategory() != Biome.Category.MESA && biome.getCategory() != Biome.Category.ICY && biome.getCategory() != Biome.Category.OCEAN) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.ROCK_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.GRANITE_ROCK_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.DIORITE_ROCK_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.ANDESITE_ROCK_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.BEACH || biome.getCategory() == Biome.Category.DESERT || biome.getCategory() == Biome.Category.MESA || biome.toString().contains("terrestria:lush_desert")) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.SAND_ROCK_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.MESA || biome.getCategory() == Biome.Category.DESERT || biome.toString().contains("terrestria:lush_desert")) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.RED_SAND_ROCK_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.THEEND) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.END_STONE_ROCK_FEATURE);
        }
        // Sticks
        if (biome.toString().contains("minecraft:forest") || biome.toString().contains("minecraft:wooded_hills") ||
                biome.toString().contains("minecraft:wooded_mountains") || biome.toString().contains("minecraft:plains") ||
                biome.toString().contains("minecraft:flower_forest") || biome.toString().contains("minecraft:wooded_badlands_plateau") ||
                biome.toString().contains("minecraft:modified_wooded_badlands_plateau") || biome.getCategory() == Biome.Category.SWAMP) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, StickFeatures.OAK_STICK_FEATURE);
        }
        if (biome.toString().contains("minecraft:forest") || biome.toString().contains("minecraft:birch_forest") ||
                biome.toString().contains("minecraft:birch_forest_hills") || biome.toString().contains("minecraft:flower_forest")) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, StickFeatures.BIRCH_STICK_FEATURE);
        }
        if (biome.toString().contains("minecraft:wooded_mountains") || biome.getCategory() == Biome.Category.TAIGA) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, StickFeatures.SPRUCE_STICK_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.SAVANNA) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, StickFeatures.ACACIA_STICK_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.JUNGLE) {
            addRockFeature(biome, GenerationStep.Feature.UNDERGROUND_DECORATION, StickFeatures.JUNGLE_STICK_FEATURE);
        }
        if (biome.toString().contains("minecraft:dark_forest") || biome.toString().contains("minecraft:dark_forest_hills") ||
                biome.toString().contains("minecraft:dark_forest_mountains")) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, StickFeatures.DARK_OAK_STICK_FEATURE);
        }
        // Misc
        if (biome.getCategory() == Biome.Category.BEACH && !biome.toString().contains("minecraft:snowy_beach")) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, MiscFeatures.SEASHELL_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, MiscFeatures.STARFISH_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.OCEAN) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, MiscFeatures.UNDERWATER_STARFISH_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, MiscFeatures.UNDERWATER_SEASHELL_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.NETHER) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, NetherFeatures.NETHERRACK_ROCK_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, NetherFeatures.SOUL_SOIL_ROCK_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, NetherFeatures.NETHER_GRAVEL_ROCK_FEATURE);
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, NetherFeatures.NETHER_GEYSER_FEATURE);
        }
        if (biome.getCategory() != Biome.Category.NETHER) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RockFeatures.GRAVEL_ROCK_FEATURE);
        }
        if (biome.getCategory() == Biome.Category.ICY) {
            addRockFeature(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, MiscFeatures.SNOWY_GEYSER_FEATURE);
        }
    }

    public static void addRockFeature(Biome biome, GenerationStep.Feature step, ConfiguredFeature<?, ?> feature) {
        GenerationSettingsAccessorMixin generationSettingsAccessor  = (GenerationSettingsAccessorMixin) biome.getGenerationSettings();
        int stepIndex = step.ordinal();
        List<List<Supplier<ConfiguredFeature<?, ?>>>> featuresByStep = new ArrayList<>( generationSettingsAccessor.getFeatures());
        while (featuresByStep.size() <= stepIndex) {
            featuresByStep.add(Lists.newArrayList());
        }
        List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(featuresByStep.get(stepIndex));
        features.add(() -> feature);
        featuresByStep.set(stepIndex, features);
        generationSettingsAccessor.setFeatures(featuresByStep);
    }
}