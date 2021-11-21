package eu.midnightdust.motschen.rocks.world;

import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.world.configured_feature.MiscFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.NetherFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.RockFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.StickFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import java.util.function.Predicate;

@SuppressWarnings({"OptionalGetWithoutIsPresent"})
public class FeatureInjector {

    public static void init() {
        // Rocks
        Predicate<BiomeSelectionContext> rocks = (ctx -> {
            Biome.Category cat = ctx.getBiome().getCategory();
            return cat != Biome.Category.NETHER && cat != Biome.Category.THEEND && cat!= Biome.Category.BEACH && cat != Biome.Category.DESERT
                    && cat != Biome.Category.MESA && cat != Biome.Category.ICY && cat != Biome.Category.OCEAN ;});
        if (RocksConfig.rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.ROCK_PLACED_FEATURE).get());
        if (RocksConfig.granite_rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.GRANITE_ROCK_PLACED_FEATURE).get());
        if (RocksConfig.diorite_rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.DIORITE_ROCK_PLACED_FEATURE).get());
        if (RocksConfig.andesite_rock) BiomeModifications.addFeature(rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.ANDESITE_ROCK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> sand_rocks = (ctx -> {
            Biome.Category cat = ctx.getBiome().getCategory();
            return cat == Biome.Category.BEACH || cat == Biome.Category.DESERT || cat == Biome.Category.MESA || ctx.getBiomeKey().getValue().toString().contains("terrestria:lush_desert");
        });
        if (RocksConfig.sand_rock) BiomeModifications.addFeature(sand_rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.SAND_ROCK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> red_sand_rocks = (ctx -> {
             Biome.Category cat = ctx.getBiome().getCategory();
             return cat == Biome.Category.MESA || cat == Biome.Category.DESERT || ctx.getBiomeKey().getValue().toString().contains("terrestria:lush_desert");
        });
        if (RocksConfig.red_sand_rock) BiomeModifications.addFeature(red_sand_rocks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.RED_SAND_ROCK_PLACED_FEATURE).get());

        if (RocksConfig.end_stone_rock) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.THEEND, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.END_STONE_ROCK_PLACED_FEATURE).get());

        // Sticks
        Predicate<BiomeSelectionContext> oak_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            Biome.Category cat = ctx.getBiome().getCategory();
        return  name.contains("minecraft:forest") || name.contains("minecraft:wooded_hills") || name.contains("oak") ||
                name.contains("minecraft:wooded_mountains") || name.contains("minecraft:plains") ||
                name.contains("minecraft:flower_forest") || name.contains("minecraft:wooded_badlands_plateau") ||
                name.contains("minecraft:modified_wooded_badlands_plateau") || cat == Biome.Category.SWAMP;});
        if (RocksConfig.oak_stick) BiomeModifications.addFeature(oak_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.OAK_STICK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> birch_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return name.contains("minecraft:forest") || name.contains("birch") || name.contains("minecraft:flower_forest");});
        if (RocksConfig.birch_stick) BiomeModifications.addFeature(birch_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.BIRCH_STICK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> spruce_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            Biome.Category cat = ctx.getBiome().getCategory();
        return name.contains("minecraft:wooded_mountains") || cat == Biome.Category.TAIGA;});
        if (RocksConfig.spruce_stick) BiomeModifications.addFeature(spruce_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.SPRUCE_STICK_PLACED_FEATURE).get());

        if (RocksConfig.acacia_stick) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.SAVANNA, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.ACACIA_STICK_PLACED_FEATURE).get());

        if (RocksConfig.jungle_stick) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.JUNGLE, GenerationStep.Feature.UNDERGROUND_DECORATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.JUNGLE_STICK_PLACED_FEATURE).get());

        Predicate<BiomeSelectionContext> dark_oak_sticks = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            return name.contains("minecraft:dark_forest") || name.contains("minecraft:dark_forest_hills") ||
                name.contains("minecraft:dark_forest_mountains");});
        if (RocksConfig.dark_oak_stick) BiomeModifications.addFeature(dark_oak_sticks, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(StickFeatures.DARK_OAK_STICK_PLACED_FEATURE).get());

        // Misc
        Predicate<BiomeSelectionContext> beach = (ctx -> {
            String name = ctx.getBiomeKey().getValue().toString();
            Biome.Category cat = ctx.getBiome().getCategory();
            return cat == Biome.Category.BEACH && !name.contains("snow");});
        if (RocksConfig.seashell) BiomeModifications.addFeature(beach, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.SEASHELL_PLACED_FEATURE).get());
        if (RocksConfig.starfish) BiomeModifications.addFeature(beach, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.STARFISH_PLACED_FEATURE).get());

        if (RocksConfig.underwater_starfish) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.OCEAN, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.UNDERWATER_STARFISH_PLACED_FEATURE).get());
        if (RocksConfig.underwater_seashell) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.OCEAN, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.UNDERWATER_SEASHELL_PLACED_FEATURE).get());

        if (RocksConfig.netherrack_rock) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.NETHER, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.NETHERRACK_ROCK_PLACED_FEATURE).get());
        if (RocksConfig.soul_soil_rock) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.NETHER, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.SOUL_SOIL_ROCK_PLACED_FEATURE).get());
        if (RocksConfig.gravel_rock) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.NETHER, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.NETHER_GRAVEL_ROCK_PLACED_FEATURE).get());
        if (RocksConfig.nether_geyser) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.NETHER, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.NETHER_GEYSER_PLACED_FEATURE).get());
        if (RocksConfig.warped_stick) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.NETHER, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.WARPED_STICK_PLACED_FEATURE).get());
        if (RocksConfig.crimson_stick) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.NETHER, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(NetherFeatures.CRIMSON_STICK_PLACED_FEATURE).get());

        if (RocksConfig.gravel_rock) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() != Biome.Category.NETHER, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeatures.GRAVEL_ROCK_PLACED_FEATURE).get());

        if (RocksConfig.geyser) BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() == Biome.Category.ICY || ctx.getBiomeKey().toString().contains("snowy"), GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(MiscFeatures.SNOWY_GEYSER_PLACED_FEATURE).get());
    }
}