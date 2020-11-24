package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.block.NetherGeyser;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

public class NetherGeyserBlockEntity extends BlockEntity implements Tickable {
    private int countdown = 0;

    public NetherGeyserBlockEntity() {
        super(BlockEntityInit.NETHER_GEYSER_BE);
    }

    @Override
    public void tick() {
        BlockPos pos = this.pos;
        PlayerEntity player = this.world.getClosestPlayer(pos.getX()+0.5,pos.getY()+0.5, pos.getZ()+0.5,3,true);
        PlayerEntity player2 = this.world.getClosestPlayer(pos.getX()+0.5,pos.getY()+0.5, pos.getZ()+0.5,1,true);
        BlockState state = this.world.getBlockState(pos);

        if (player != null) {
            world.setBlockState(pos, state.with(NetherGeyser.ACTIVE, true));
            player.damage(DamageSource.ON_FIRE,1);
            if (player2 != null) {
                player2.damage(DamageSource.ON_FIRE,4);
            }
            countdown = 1000;
        }
        else {
            if (countdown > 0) {
                countdown = countdown - 1;
            }
            if (countdown == 0) {
                world.setBlockState(pos, state.with(NetherGeyser.ACTIVE, false));
            }
        }

        if (Boolean.TRUE.equals(state.get(NetherGeyser.ACTIVE))) {
            world.addParticle(ParticleTypes.LAVA,pos.getX()+0.5,pos.getY()+0.1,pos.getZ()+0.5,1,1.5,1);
            world.addParticle(ParticleTypes.LAVA,pos.getX()+0.5,pos.getY()+1.0,pos.getZ()+0.5,1,1.5,1);
            world.addParticle(ParticleTypes.SMOKE,pos.getX()+0.5,pos.getY()+0.1,pos.getZ()+0.5,0,0.3,0);
        }
    }
}
