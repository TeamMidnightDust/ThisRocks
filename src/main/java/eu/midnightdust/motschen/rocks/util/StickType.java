package eu.midnightdust.motschen.rocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Objects;

import static eu.midnightdust.motschen.rocks.RocksMain.id;

public enum StickType {
    OAK("oak", Blocks.OAK_LOG), SPRUCE("spruce", Blocks.SPRUCE_LOG), BIRCH("birch", Blocks.BIRCH_LOG),
    JUNGLE("jungle", Blocks.JUNGLE_LOG), ACACIA("acacia", Blocks.ACACIA_LOG), DARK_OAK("dark_oak", Blocks.DARK_OAK_LOG),
    CHERRY("cherry", Blocks.CHERRY_LOG), MANGROVE("mangrove", Blocks.MANGROVE_LOG),

    BAMBOO("bamboo", Blocks.BAMBOO_BLOCK), WARPED("warped", Blocks.WARPED_STEM), CRIMSON("crimson", Blocks.CRIMSON_STEM);

    private final String name;
    private final Block baseBlock;

    StickType(String name, Block baseBlock) {
        this.name = name;
        this.baseBlock = baseBlock;
    }

    public String getName() {
        return this.name;
    }
    public Block getBaseBlock() {
        return baseBlock;
    }

    public Identifier[] getVariations() {
        var variations = new Identifier[3];
        variations[0] = id(name+"_stick_small");
        variations[1] = id(name+"_stick_medium");
        variations[2] = id(name+"_stick_large");
        return variations;
    }

    public static StickType fromBlockName(String name) {
        return Arrays.stream(StickType.values()).filter(woodType -> Objects.equals(woodType.getName(), name
                .replace("block.rocks.", "").replace("_stick", "")
        )).findFirst().orElse(StickType.OAK);
    }
}
