package eu.midnightdust.motschen.rocks.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class RocksConfig extends MidnightConfig {
    public final static String rocks = "rocks";

    @Comment(category = rocks) public static Comment needs_restart;
    @Entry(category = rocks, name = "block.rocks.rock") public static boolean rock = true;
    @Entry(category = rocks, name = "block.rocks.granite_rock") public static boolean granite_rock = true;
    @Entry(category = rocks, name = "block.rocks.diorite_rock") public static boolean diorite_rock = true;
    @Entry(category = rocks, name = "block.rocks.andesite_rock") public static boolean andesite_rock = true;
    @Entry(category = rocks, name = "block.rocks.sand_rock") public static boolean sand_rock = true;
    @Entry(category = rocks, name = "block.rocks.red_sand_rock") public static boolean red_sand_rock = true;
    @Entry(category = rocks, name = "block.rocks.gravel_rock") public static boolean gravel_rock = true;
    @Entry(category = rocks, name = "block.rocks.end_stone_rock") public static boolean end_stone_rock = true;
    @Entry(category = rocks, name = "block.rocks.netherrack_rock") public static boolean netherrack_rock = true;
    @Entry(category = rocks, name = "block.rocks.soul_soil_rock") public static boolean soul_soil_rock = true;

    public final static String sticks = "sticks";
    @Comment(category = sticks) public static Comment needs_restart1;
    @Entry(category = sticks, name = "block.rocks.oak_stick") public static boolean oak_stick = true;
    @Entry(category = sticks, name = "block.rocks.spruce_stick") public static boolean spruce_stick = true;
    @Entry(category = sticks, name = "block.rocks.birch_stick") public static boolean birch_stick = true;
    @Entry(category = sticks, name = "block.rocks.acacia_stick") public static boolean acacia_stick = true;
    @Entry(category = sticks, name = "block.rocks.jungle_stick") public static boolean jungle_stick = true;
    @Entry(category = sticks, name = "block.rocks.dark_oak_stick") public static boolean dark_oak_stick = true;
    @Entry(category = sticks, name = "block.rocks.crimson_stick") public static boolean crimson_stick = true;
    @Entry(category = sticks, name = "block.rocks.warped_stick") public static boolean warped_stick = true;

    public final static String misc = "misc";
    @Comment(category = misc) public static Comment needs_restart2;
    @Entry(category = misc, name = "block.rocks.pinecone") public static boolean pinecone = true;
    @Entry(category = misc, name = "block.rocks.geyser") public static boolean geyser = true;
    @Entry(category = misc, name = "block.rocks.nether_geyser") public static boolean nether_geyser = true;
    @Entry(category = misc, name = "block.rocks.seashell") public static boolean seashell = true;
    @Entry(category = misc, name = "block.rocks.starfish") public static boolean starfish = true;
    @Entry(category = misc) public static boolean underwater_seashell = true;
    @Entry(category = misc) public static boolean underwater_starfish = true;
}
