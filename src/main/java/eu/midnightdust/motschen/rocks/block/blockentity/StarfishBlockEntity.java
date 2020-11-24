package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import net.minecraft.block.entity.BlockEntity;

public class StarfishBlockEntity extends BlockEntity {

    public StarfishBlockEntity() {
        super(BlockEntityInit.STARFISH_BE);
    }

    public StarfishVariation getVariation() {
        return this.world.getBlockState(pos).get(RocksMain.STARFISH_VARIATION);
    }
}

