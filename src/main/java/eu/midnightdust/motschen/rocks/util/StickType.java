package eu.midnightdust.motschen.rocks.util;

import net.minecraft.block.WoodType;

import java.util.Objects;

public class StickType {
    public static WoodType fromBlockName(String name) {
        return WoodType.stream().filter(woodType -> Objects.equals(woodType.name(), name
                .replace("block.rocks.", "").replace("_stick", "")
        )).findFirst().orElse(WoodType.OAK);
    }
}
