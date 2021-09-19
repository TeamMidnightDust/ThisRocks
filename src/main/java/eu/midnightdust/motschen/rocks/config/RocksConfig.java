package eu.midnightdust.motschen.rocks.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class RocksConfig extends MidnightConfig {
    @Comment public static Comment needs_restart;

    @Comment public static Comment rocks;
    @Entry(name = "block.rocks.rock") public static boolean rock = true;
    @Entry(name = "block.rocks.granite_rock") public static boolean granite_rock = true;
    @Entry(name = "block.rocks.diorite_rock") public static boolean diorite_rock = true;
    @Entry(name = "block.rocks.andesite_rock") public static boolean andesite_rock = true;
    @Entry(name = "block.rocks.sand_rock") public static boolean sand_rock = true;
    @Entry(name = "block.rocks.red_sand_rock") public static boolean red_sand_rock = true;
    @Entry(name = "block.rocks.gravel_rock") public static boolean gravel_rock = true;
    @Entry(name = "block.rocks.end_stone_rock") public static boolean end_stone_rock = true;
    @Entry(name = "block.rocks.netherrack_rock") public static boolean netherrack_rock = true;
    @Entry(name = "block.rocks.soul_soil_rock") public static boolean soul_soil_rock = true;

    @Comment public static Comment sticks;
    @Entry(name = "block.rocks.oak_stick") public static boolean oak_stick = true;
    @Entry(name = "block.rocks.spruce_stick") public static boolean spruce_stick = true;
    @Entry(name = "block.rocks.birch_stick") public static boolean birch_stick = true;
    @Entry(name = "block.rocks.acacia_stick") public static boolean acacia_stick = true;
    @Entry(name = "block.rocks.jungle_stick") public static boolean jungle_stick = true;
    @Entry(name = "block.rocks.dark_oak_stick") public static boolean dark_oak_stick = true;
    @Entry(name = "block.rocks.crimson_stick") public static boolean crimson_stick = true;
    @Entry(name = "block.rocks.warped_stick") public static boolean warped_stick = true;

    @Comment public static Comment misc;
    @Entry(name = "block.rocks.pinecone") public static boolean pinecone = true;
    @Entry(name = "block.rocks.geyser") public static boolean geyser = true;
    @Entry(name = "block.rocks.nether_geyser") public static boolean nether_geyser = true;
    @Entry(name = "block.rocks.seashell") public static boolean seashell = true;
    @Entry(name = "block.rocks.starfish") public static boolean starfish = true;
    @Entry public static boolean underwater_seashell = true;
    @Entry public static boolean underwater_starfish = true;
}
