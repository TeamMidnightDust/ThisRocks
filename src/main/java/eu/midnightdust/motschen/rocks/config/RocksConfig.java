package eu.midnightdust.motschen.rocks.config;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.ArrayList;
import java.util.List;

public class RocksConfig extends MidnightConfig {
    public final static String rocks = "rocks";

    @Comment(category = rocks, centered = true) public static Comment needs_restart;
    @Entry(category = rocks, name = "block.rocks.rock") public static boolean rock = true;
    @Entry(category = rocks) public static boolean rockMix = true;
    @Entry(category = rocks, name = "block.rocks.granite_rock") public static boolean graniteRock = true;
    @Entry(category = rocks, name = "block.rocks.diorite_rock") public static boolean dioriteRock = true;
    @Entry(category = rocks, name = "block.rocks.andesite_rock") public static boolean andesiteRock = true;
    @Entry(category = rocks, name = "block.rocks.sand_rock") public static boolean sandRock = true;
    @Entry(category = rocks, name = "block.rocks.red_sand_rock") public static boolean redSandRock = true;
    @Entry(category = rocks, name = "block.rocks.gravel_rock") public static boolean gravelRock = true;
    @Entry(category = rocks, name = "block.rocks.ice_rock") public static boolean iceRock = true;
    @Entry(category = rocks, name = "block.rocks.end_stone_rock") public static boolean endStoneRock = true;
    @Entry(category = rocks, name = "block.rocks.netherrack_rock") public static boolean netherrackRock = true;
    @Entry(category = rocks, name = "block.rocks.soul_soil_rock") public static boolean soulSoilRock = true;

    public final static String sticks = "sticks";
    @Comment(category = sticks, centered = true) public static Comment needs_restart1;
    @Entry(category = sticks, name = "block.rocks.oak_stick") public static boolean oakStick = true;
    @Entry(category = sticks, name = "block.rocks.spruce_stick") public static boolean spruceStick = true;
    @Entry(category = sticks, name = "block.rocks.birch_stick") public static boolean birchStick = true;
    @Entry(category = sticks, name = "block.rocks.acacia_stick") public static boolean acaciaStick = true;
    @Entry(category = sticks, name = "block.rocks.jungle_stick") public static boolean jungleStick = true;
    @Entry(category = sticks, name = "block.rocks.dark_oak_stick") public static boolean darkOakStick = true;
    @Entry(category = sticks, name = "block.rocks.pale_oak_stick") public static boolean paleOakStick = true;
    @Entry(category = sticks, name = "block.rocks.mangrove_stick") public static boolean mangroveStick = true;
    @Entry(category = sticks, name = "block.rocks.cherry_stick") public static boolean cherryStick = true;
    @Entry(category = sticks, name = "block.rocks.bamboo_stick") public static boolean bambooStick = true;
    @Entry(category = sticks, name = "block.rocks.crimson_stick") public static boolean crimsonStick = true;
    @Entry(category = sticks, name = "block.rocks.warped_stick") public static boolean warpedStick = true;

    public final static String misc = "misc";
    @Comment(category = misc, centered = true) public static Comment needs_restart2;
    @Entry(category = misc, name = "block.rocks.pinecone") public static boolean pinecone = true;
    @Entry(category = misc, name = "block.rocks.geyser") public static boolean geyser = true;
    @Entry(category = misc, name = "block.rocks.nether_geyser") public static boolean netherGeyser = true;
    @Entry(category = misc, name = "block.rocks.seashell") public static boolean seashell = true;
    @Entry(category = misc, name = "block.rocks.starfish") public static boolean starfish = true;
    @Entry(category = misc) public static boolean underwaterSeashell = true;
    @Entry(category = misc) public static boolean underwaterStarfish = true;

    public final static String effects = "effects";
    @Entry(category = effects) public static boolean geyserLevitation = true;
    @Entry(category = effects) public static boolean netherGeyserDamage = true;

    @Entry(category = effects) public static boolean enablePolymerMode = true;
    @Entry(category = effects) public static boolean forcePolymerMode = false;
    @Entry(category = effects, requiredMod = "factorytools", min = 0, max = 200, isSlider = true) public static int polymerViewDistance = 100;
    @Entry(category = effects) public static List<String> biomeExclusions = new ArrayList<>();
}
