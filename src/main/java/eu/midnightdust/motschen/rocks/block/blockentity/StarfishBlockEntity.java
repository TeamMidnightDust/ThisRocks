package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

public class StarfishBlockEntity extends BlockEntity implements Tickable {
    private String variation;

    public StarfishBlockEntity() {
        super(BlockEntityInit.STARFISH_BE);
    }

    @Override
    public void tick() {
        BlockPos pos = this.pos;
        BlockState state = this.world.getBlockState(pos);

        if (world != null && state.get(RocksMain.STARFISH_VARIATION) == StarfishVariation.RED) {
            variation = String.valueOf(StarfishVariation.RED);
            return;
        }
        else if (world != null && state.get(RocksMain.STARFISH_VARIATION) == StarfishVariation.PINK) {
            variation = String.valueOf(StarfishVariation.PINK);
            return;
        }
        else {
            variation = String.valueOf(StarfishVariation.ORANGE);
            return;
        }
    }
    public String getVariation() {
        return variation;
    }
}

