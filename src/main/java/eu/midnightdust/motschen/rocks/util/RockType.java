package eu.midnightdust.motschen.rocks.util;

import net.minecraft.util.Identifier;

import java.util.Arrays;

import static eu.midnightdust.motschen.rocks.RocksMain.id;

public enum RockType {
    STONE("rock"), ANDESITE("andesite_rock"), GRANITE("granite_rock"),
    DIORITE("diorite_rock"), GRAVEL("gravel_rock"), SANDSTONE("sand_rock"),
    RED_SANDSTONE("red_sand_rock"), NETHERRACK("netherrack_rock"),
    SOUL_SOIL("soul_soil_rock"), END_STONE("end_stone_rock"), ICE("ice_rock");

    private final String name;

    RockType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public String getSplitterName() {
        String splitterName = this.name().toLowerCase()+ "_splitter";
        if (this.equals(RockType.STONE)) splitterName = "cobblestone_splitter";
        return splitterName;
    }

    public Identifier[] getVariations() {
        var variations = new Identifier[4];
        variations[0] = id("tiny_"+name);
        variations[1] = id("small_"+name);
        variations[2] = id("medium_"+name);
        variations[3] = id("large_"+name);
        return variations;
    }

    public static RockType fromBlockName(String name) {
        return Arrays.stream(values()).filter(type -> name
                .replace("block.rocks.", "")
                .replace("tiny", "")
                .replace("small_", "")
                .replace("medium_", "")
                .replace("large_", "")
                .equals(type.getName())).findFirst().orElse(RockType.STONE);
    }
}
