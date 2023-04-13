package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Predicate;

public class FeatureInjector {

    public static void init() {
        // Rocks
        Predicate<BiomeSelectionContext> rocks = (ctx ->
            !ctx.hasTag(BiomeTags.IS_NETHER) && !ctx.hasTag(BiomeTags.END_CITY_HAS_STRUCTURE) && !ctx.hasTag(BiomeTags.IS_BEACH) && !ctx.hasTag(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE)
                    && !ctx.hasTag(BiomeTags.IS_BADLANDS) && !ctx.hasTag(BiomeTags.IGLOO_HAS_STRUCTURE) && !ctx.hasTag(BiomeTags.IS_OCEAN));
        if (RocksConfig.rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("rock"));
        if (RocksConfig.granite_rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("granite_rock"));
        if (RocksConfig.diorite_rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("diorite_rock"));
        if (RocksConfig.andesite_rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("andesite_rock"));

        Predicate<BiomeSelectionContext> sand_rocks = (ctx -> ctx.hasTag(BiomeTags.IS_BEACH) || ctx.hasTag(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE) || ctx.hasTag(BiomeTags.IS_BADLANDS) || ctx.getBiomeKey().getValue().toString().contains("terrestria:lush_desert"));
        if (RocksConfig.sand_rock) BiomeModifications.addFeature(sand_rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("sand_rock"));

        Predicate<BiomeSelectionContext> red_sand_rocks = (ctx -> ctx.hasTag(BiomeTags.IS_BADLANDS) || ctx.hasTag(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE) || ctx.getBiomeKey().getValue().toString().contains("terrestria:lush_desert"));

        if (RocksConfig.red_sand_rock) BiomeModifications.addFeature(red_sand_rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("red_sand_rock"));

        if (RocksConfig.end_stone_rock) BiomeModifications.addFeature(ctx -> ctx.getBiomeKey().getValue().toString().contains("the_end"), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("end_stone_rock"));

        // Sticks
        Predicate<BiomeSelectionContext> oak_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
        return  name.contains("minecraft:forest") || name.contains("minecraft:wooded_hills") || name.contains("oak") ||
                name.contains("minecraft:wooded_mountains") || name.contains("minecraft:plains") ||
                name.contains("minecraft:flower_forest") || name.contains("minecraft:wooded_badlands_plateau") ||
                name.contains("minecraft:modified_wooded_badlands_plateau") || ctx.hasTag(BiomeTags.SWAMP_HUT_HAS_STRUCTURE);});
        if (RocksConfig.oak_stick) BiomeModifications.addFeature(oak_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("oak_stick"));

        Predicate<BiomeSelectionContext> birch_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return name.contains("minecraft:forest") || name.contains("birch") || name.contains("minecraft:flower_forest");});
        if (RocksConfig.birch_stick) BiomeModifications.addFeature(birch_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("birch_stick"));

        Predicate<BiomeSelectionContext> spruce_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
        return name.contains("minecraft:wooded_mountains") || ctx.hasTag(BiomeTags.IS_TAIGA);});
        if (RocksConfig.spruce_stick) BiomeModifications.addFeature(spruce_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("spruce_stick"));

        if (RocksConfig.acacia_stick) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.VILLAGE_SAVANNA_HAS_STRUCTURE), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("acacia_stick"));

        if (RocksConfig.jungle_stick) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_JUNGLE), GenerationStep.Feature.UNDERGROUND_DECORATION, getKey("jungle_stick"));

        Predicate<BiomeSelectionContext> dark_oak_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return name.contains("minecraft:dark_forest") || name.contains("minecraft:dark_forest_hills") ||
                name.contains("minecraft:dark_forest_mountains");});
        if (RocksConfig.dark_oak_stick) BiomeModifications.addFeature(dark_oak_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("dark_oak_stick"));

        // Misc
        Predicate<BiomeSelectionContext> beach = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return ctx.hasTag(BiomeTags.IS_BEACH) && !name.contains("snow");});
        if (RocksConfig.seashell) BiomeModifications.addFeature(beach, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("seashell"));
        if (RocksConfig.starfish) BiomeModifications.addFeature(beach, GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("starfish"));

        if (RocksConfig.underwater_starfish) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_OCEAN), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("underwater_starfish"));
        if (RocksConfig.underwater_seashell) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_OCEAN), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("underwater_seashell"));

        if (RocksConfig.netherrack_rock) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("netherrack_rock"));
        if (RocksConfig.soul_soil_rock) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("soul_soil_rock"));
        if (RocksConfig.gravel_rock) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("nether_gravel_rock"));
        if (RocksConfig.nether_geyser) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("nether_geyser"));
        if (RocksConfig.warped_stick) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("warped_stick"));
        if (RocksConfig.crimson_stick) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("crimson_stick"));

        if (RocksConfig.gravel_rock) BiomeModifications.addFeature(ctx -> !ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("gravel_rock"));

        if (RocksConfig.geyser) BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IGLOO_HAS_STRUCTURE) || ctx.getBiomeKey().toString().contains("snowy"), GenerationStep.Feature.TOP_LAYER_MODIFICATION, getKey("snowy_geyser"));
    }
    public static RegistryKey<PlacedFeature> getKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(RocksMain.MOD_ID, name));
    }
}