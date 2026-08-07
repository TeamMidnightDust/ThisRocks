package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.block.OverworldGeyser;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.util.ParticleUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;

public class OverworldGeyserBlockEntity extends BlockEntity {
    public int countdown = 0;

    public OverworldGeyserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.OVERWORLD_GEYSER_BE, pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, OverworldGeyserBlockEntity blockEntity) {
        if (world == null || world.isClient()) return;
        if (world.getBlockState(pos).getBlock() == RocksMain.Geyser) {
            Player player = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, true);
            Player player2 = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, true);

            if (RocksConfig.geyserLevitation && player2 != null && (player2.getBlockPos().equals(pos) || world.raycast(new ClipBlockStateContext(pos.toCenterPos(), player2.getEntityPos(), blockState -> !blockState.isAir() && !blockState.isOf(RocksMain.Geyser))).getType() == HitResult.Type.MISS) && player2.getY() >= pos.getY() && player2.getY() <= pos.getY() + 5 && (pos.getX() <= player2.getX() && pos.getX() + 1 >= player2.getX()) && (pos.getZ() <= player2.getZ() && pos.getZ() + 1 >= player2.getZ())) {
                player2.addStatusEffect(new MobEffectInstance(MobEffects.LEVITATION, 2, 10, true, false, false));
            }

            if (player != null) {
                world.setBlockState(pos, state.with(OverworldGeyser.ACTIVE, true));
                if (world.getBlockState(pos.down()).getBlock() instanceof GrassBlock) world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(GrassBlock.SNOWY, true));
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
                PlayerLookup.tracking(blockEntity).forEach(watchingPlayer -> {
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.SPIT, new Vec3(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5), new Vec3(0, 16.0, 0), 0.3f);
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.SPIT, new Vec3(pos.getX() + 0.5, pos.getY() + 2.3, pos.getZ() + 0.5), new Vec3(0, 64.0, 0), 0.1f);
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.SPLASH, new Vec3(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5), new Vec3(-0.01, 16.5, -0.01), 0.3f);
                });
            }
        }
    }
}
