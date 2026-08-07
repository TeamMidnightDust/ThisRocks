package eu.midnightdust.motschen.rocks.util;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
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

    public Identifier getStoneId() {
        return Identifier.ofVanilla(this.toString().toLowerCase());
    }

    public Block getStoneBlock() {
        return Registries.BLOCK.get(getStoneId());
    }

    public Identifier[] getVariations() {
        var variations = new Identifier[4];
        variations[0] = id(name+"_tiny");
        variations[1] = id(name+"_small");
        variations[2] = id(name+"_medium");
        variations[3] = id(name+"_large");
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

    public Fragment getFragment() {
        return new Fragment(this);
    }

    public static class Fragment {
        private final RockType type;

        Fragment(RockType type) {
            this.type = type;
        }

        public String getName() {
            String splitterName = type.name().toLowerCase()+ "_splitter";
            if (type.equals(RockType.STONE)) splitterName = "cobblestone_splitter";
            return splitterName;
        }
        public Identifier getStoneId() {
            if (type==STONE) return Identifier.ofVanilla("cobblestone");
            return Identifier.ofVanilla(type.toString().toLowerCase());
        }
        public Block getStoneBlock() {
            return Registries.BLOCK.get(getStoneId());
        }
    }
}
