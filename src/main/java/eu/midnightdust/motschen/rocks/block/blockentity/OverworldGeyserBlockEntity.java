package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.block.OverworldGeyser;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OverworldGeyserBlockEntity extends BlockEntity {
    public int countdown = 0;

    public OverworldGeyserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.OVERWORLD_GEYSER_BE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, OverworldGeyserBlockEntity blockEntity) {
        assert world != null;
        if (world.getBlockState(pos).getBlock() == RocksMain.Geyser) {
            PlayerEntity player = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, true);
            PlayerEntity player2 = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, true);

            if (player2 != null && player2.getY() >= pos.getY() && player2.getY() <= pos.getY() + 5 && (pos.getX() <= player2.getX() && pos.getX() + 1 >= player2.getX()) && (pos.getZ() <= player2.getZ() && pos.getZ() + 1 >= player2.getZ())) {
                player2.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 2, 10, true, false, false));
            }

            if (player != null) {
                world.setBlockState(pos, state.with(OverworldGeyser.ACTIVE, true));
                blockEntity.countdown = 1000;
            } else {
                if (blockEntity.countdown > 0) {
                    blockEntity.countdown = blockEntity.countdown - 1;
                }
                if (blockEntity.countdown == 0) {
                    world.setBlockState(pos, state.with(OverworldGeyser.ACTIVE, false));
                }
            }

            if (state.get(OverworldGeyser.ACTIVE)) {
                world.addParticle(ParticleTypes.SPIT, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, 0, 1.0, 0);
                world.addParticle(ParticleTypes.SPIT, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 0, 1.0, 0);
                world.addParticle(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, -0.01, 1.5, -0.01);
            }
        }
    }
}
