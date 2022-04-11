package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.world.configured_feature.MiscFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.NetherFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.RockFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.StickFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.GenerationStep;
import java.util.function.Predicate;

@SuppressWarnings({"OptionalGetWithoutIsPresent"})
public class FeatureInjector {

    public static void init() {
        // Rocks
        Predicate<BiomeSelectionContext> rocks = (ctx ->
            !ctx.hasTag(BiomeTags.IS_NETHER) && !ctx.hasTag(BiomeTags.END_CITY_HAS_STRUCTURE) && !ctx.hasTag(BiomeTags.IS_BEACH) && !ctx.hasTag(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE)
                    && !ctx.hasTag(BiomeTags.IS_BADLANDS) && !ctx.hasTag(BiomeTags.IGLOO_HAS_STRUCTURE) && !ctx.hasTag(BiomeTags.IS_OCEAN));
        BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.ROCK_PLACED_FEATURE).get());
        BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.GRANITE_ROCK_PLACED_FEATURE).get());
        BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.DIORITE_ROCK_PLACED_FEATURE).get());
        BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.ANDESITE_ROCK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> sand_rocks = (ctx -> ctx.hasTag(BiomeTags.IS_BEACH) || ctx.hasTag(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE) || ctx.hasTag(BiomeTags.IS_BADLANDS) || ctx.getBiomeKey().getValue().toString().contains("terrestria:lush_desert"));
        BiomeModifications.addFeature(sand_rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.SAND_ROCK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> red_sand_rocks = (ctx -> ctx.hasTag(BiomeTags.IS_BADLANDS) || ctx.hasTag(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE) || ctx.getBiomeKey().getValue().toString().contains("terrestria:lush_desert"));

        BiomeModifications.addFeature(red_sand_rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.RED_SAND_ROCK_PLACED_FEATURE).get());

        BiomeModifications.addFeature(ctx -> ctx.getBiomeKey().getValue().toString().contains("the_end"), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.END_STONE_ROCK_PLACED_FEATURE).get());

        // Sticks
        Predicate<BiomeSelectionContext> oak_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
        return  name.contains("minecraft:forest") || name.contains("minecraft:wooded_hills") || name.contains("oak") ||
                name.contains("minecraft:wooded_mountains") || name.contains("minecraft:plains") ||
                name.contains("minecraft:flower_forest") || name.contains("minecraft:wooded_badlands_plateau") ||
                name.contains("minecraft:modified_wooded_badlands_plateau") || ctx.hasTag(BiomeTags.SWAMP_HUT_HAS_STRUCTURE);});
        BiomeModifications.addFeature(oak_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.OAK_STICK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> birch_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return name.contains("minecraft:forest") || name.contains("birch") || name.contains("minecraft:flower_forest");});
        BiomeModifications.addFeature(birch_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.BIRCH_STICK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> spruce_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
        return name.contains("minecraft:wooded_mountains") || ctx.hasTag(BiomeTags.IS_TAIGA);});
        BiomeModifications.addFeature(spruce_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.SPRUCE_STICK_PLACED_FEATURE).get());

        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.VILLAGE_SAVANNA_HAS_STRUCTURE), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.ACACIA_STICK_PLACED_FEATURE).get());

        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_JUNGLE), GenerationStep.Feature.UNDERGROUND_DECORATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.JUNGLE_STICK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> dark_oak_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return name.contains("minecraft:dark_forest") || name.contains("minecraft:dark_forest_hills") ||
                name.contains("minecraft:dark_forest_mountains");});
        BiomeModifications.addFeature(dark_oak_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.DARK_OAK_STICK_PLACED_FEATURE).get());

        // Misc
        Predicate<BiomeSelectionContext> beach = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return ctx.hasTag(BiomeTags.IS_BEACH) && !name.contains("snow");});
        BiomeModifications.addFeature(beach, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.SEASHELL_PLACED_FEATURE).get());
        BiomeModifications.addFeature(beach, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.STARFISH_PLACED_FEATURE).get());

        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_OCEAN), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.UNDERWATER_STARFISH_PLACED_FEATURE).get());
        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_OCEAN), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.UNDERWATER_SEASHELL_PLACED_FEATURE).get());

        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.NETHERRACK_ROCK_PLACED_FEATURE).get());
        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.SOUL_SOIL_ROCK_PLACED_FEATURE).get());
        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.NETHER_GRAVEL_ROCK_PLACED_FEATURE).get());
        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.NETHER_GEYSER_PLACED_FEATURE).get());
        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.WARPED_STICK_PLACED_FEATURE).get());
        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.CRIMSON_STICK_PLACED_FEATURE).get());

        BiomeModifications.addFeature(ctx -> !ctx.hasTag(BiomeTags.IS_NETHER), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.GRAVEL_ROCK_PLACED_FEATURE).get());

        BiomeModifications.addFeature(ctx -> ctx.hasTag(BiomeTags.IGLOO_HAS_STRUCTURE) || ctx.getBiomeKey().toString().contains("snowy"), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.SNOWY_GEYSER_PLACED_FEATURE).get());
    }
}