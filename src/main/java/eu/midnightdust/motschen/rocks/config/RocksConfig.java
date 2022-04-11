package eu.midnightdust.motschen.rocks.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class RocksConfig extends MidnightConfig {
    @Comment public static Comment needs_restart;

    @Comment public static Comment rocks;
    @Entry(name = "block.rocks.rock") public static int rock_count = 3;
    @Entry(name = "block.rocks.granite_rock") public static int granite_rock_count = 3;
    @Entry(name = "block.rocks.diorite_rock") public static int diorite_rock_count = 3;
    @Entry(name = "block.rocks.andesite_rock") public static int andesite_rock_count = 3;
    @Entry(name = "block.rocks.sand_rock") public static int sand_rock_count = 3;
    @Entry(name = "block.rocks.red_sand_rock") public static int red_sand_rock_count = 3;
    @Entry(name = "block.rocks.gravel_rock") public static int gravel_rock_count = 3;
    @Entry(name = "block.rocks.end_stone_rock") public static int end_stone_rock_count = 3;
    @Entry(name = "block.rocks.netherrack_rock") public static int netherrack_rock_count = 90;
    @Entry(name = "block.rocks.soul_soil_rock") public static int soul_soil_rock_count = 3;

    @Comment public static Comment sticks;
    @Entry(name = "block.rocks.oak_stick") public static int oak_stick_count = 3;
    @Entry(name = "block.rocks.spruce_stick") public static int spruce_stick_count = 3;
    @Entry(name = "block.rocks.birch_stick") public static int birch_stick_count = 3;
    @Entry(name = "block.rocks.acacia_stick") public static int acacia_stick_count = 3;
    @Entry(name = "block.rocks.jungle_stick") public static int jungle_stick_count = 3;
    @Entry(name = "block.rocks.dark_oak_stick") public static int dark_oak_stick_count = 3;
    @Entry(name = "block.rocks.crimson_stick") public static int crimson_stick_count = 90;
    @Entry(name = "block.rocks.warped_stick") public static int warped_stick_count = 90;

    @Comment public static Comment misc;
    @Entry(name = "block.rocks.pinecone") public static int pinecone_count = 3;
    @Entry(name = "block.rocks.geyser") public static int geyser_count = 3;
    @Entry(name = "block.rocks.nether_geyser") public static int nether_geyser_count = 30;
    @Entry(name = "block.rocks.seashell") public static int seashell_count = 1;
    @Entry(name = "block.rocks.starfish") public static int starfish_count = 1;
    @Entry public static int underwater_seashell_count = 3;
    @Entry public static int underwater_starfish_count = 3;
}
