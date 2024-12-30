package eu.midnightdust.motschen.rocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class StickType {
    public static WoodType fromBlockName(String name) {
        return WoodType.stream().filter(woodType -> Objects.equals(woodType.name(), name
                .replace("block.rocks.", "").replace("_stick", "")
        )).findFirst().orElse(WoodType.OAK);
    }
    public static Block getBaseBlock(WoodType woodType) {
        String logName = woodType.name() + "_";
        if (woodType.soundType() == BlockSoundGroup.NETHER_WOOD) logName += "stem";
        else if (woodType.soundType() == BlockSoundGroup.BAMBOO_WOOD) logName += "block";
        else logName += "log";

        if (Registries.BLOCK.containsId(Identifier.ofVanilla(logName))) {
            return Registries.BLOCK.get(Identifier.ofVanilla(logName));
        }
        return null;
    }
}
