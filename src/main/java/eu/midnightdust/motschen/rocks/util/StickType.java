package eu.midnightdust.motschen.rocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

import java.util.Arrays;

import static eu.midnightdust.motschen.rocks.RocksMain.id;

public enum StickType {
    OAK("oak", Blocks.OAK_LOG), SPRUCE("spruce", Blocks.SPRUCE_LOG), BIRCH("birch", Blocks.BIRCH_LOG),
    JUNGLE("jungle", Blocks.JUNGLE_LOG), ACACIA("acacia", Blocks.ACACIA_LOG), DARK_OAK("dark_oak", Blocks.DARK_OAK_LOG),
    CHERRY("cherry", Blocks.CHERRY_LOG), MANGROVE("mangrove", Blocks.MANGROVE_LOG), PALE_OAK("pale_oak", Blocks.PALE_OAK_LOG),

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
        variations[0] = id("small_"+name);
        variations[1] = id("medium_"+name);
        variations[2] = id("large_"+name);
        return variations;
    }

    public static StickType fromBlockName(String name) {
        return Arrays.stream(values()).filter(type -> name
                .replace("block.rocks.", "")
                .replace("small_", "")
                .replace("medium_", "")
                .replace("large_", "")
                .equals(type.getName())).findFirst().orElse(StickType.OAK);
    }
}
