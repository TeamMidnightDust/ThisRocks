package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.block.OverworldGeyser;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

public class OverworldGeyserBlockEntity extends BlockEntity implements Tickable {
    private int countdown = 0;

    public OverworldGeyserBlockEntity() {
        super(BlockEntityInit.OVERWORLD_GEYSER_BE);
    }

    @Override
    public void tick() {
        if (world.getBlockState(pos).getBlock() == RocksMain.Geyser) {
            PlayerEntity player = this.world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, true);
            PlayerEntity player2 = this.world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, true);
            PlayerEntity player3 = null;

            if (player2 != null && player2.getY() >= pos.getY() && (pos.getX() <= player2.getX() && pos.getX() + 1 >= player2.getX()) && (pos.getZ() <= player2.getZ() && pos.getZ() + 1 >= player2.getZ())) {
                player3 = player2;
            }
            BlockState state = this.getCachedState();

            if (player != null) {
                world.setBlockState(pos, state.with(OverworldGeyser.ACTIVE, true));

                if (player3 != null) {
                    player3.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 2, 12, true, false, false));
                }
                countdown = 1000;
            } else {
                if (countdown > 0) {
                    countdown = countdown - 1;
                }
                if (countdown == 0) {
                    world.setBlockState(pos, state.with(OverworldGeyser.ACTIVE, false));
                }
            }

            if (world != null && state.get(OverworldGeyser.ACTIVE) == true) {
                world.addParticle(ParticleTypes.SPIT, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, 0, 1.0, 0);
                world.addParticle(ParticleTypes.SPIT, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 0, 1.0, 0);
                world.addParticle(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, -0.01, 1.5, -0.01);
            }
        }
    }
}
