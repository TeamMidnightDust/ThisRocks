package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.block.NetherGeyser;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.util.ParticleUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NetherGeyserBlockEntity extends BlockEntity {
    private int countdown = 0;

    public NetherGeyserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.NETHER_GEYSER_BE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, NetherGeyserBlockEntity blockEntity) {
        if (world == null || world.isClient) return;
        if (world.getBlockState(pos).getBlock() == RocksMain.NetherGeyser) {
            PlayerEntity player = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, true);
            PlayerEntity player2 = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, true);


            if (player != null) {
                world.setBlockState(pos, state.with(NetherGeyser.ACTIVE, true));

                if (RocksConfig.netherGeyserDamage && world instanceof ServerWorld serverWorld) {
                    player.damage(serverWorld, world.getDamageSources().onFire(), 1);
                    if (player2 != null) {
                        player2.damage(serverWorld, world.getDamageSources().onFire(), 4);
                    }
                }
                blockEntity.countdown = 1000;
            } else {
                if (blockEntity.countdown > 0) {
                    blockEntity.countdown = blockEntity.countdown - 1;
                }
                if (blockEntity.countdown == 0) {
                    world.setBlockState(pos, state.with(NetherGeyser.ACTIVE, false));
                }
            }

            if (state.get(NetherGeyser.ACTIVE)) {
                PlayerLookup.tracking(blockEntity).forEach(watchingPlayer -> {
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.LAVA, new Vec3d(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5), new Vec3d(1, 1.5d, 1), 1);
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.LAVA, new Vec3d(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5), new Vec3d(1, 1.5d, 1), 1);
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.SMOKE, new Vec3d(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5), new Vec3d(0, 0.3, 0), 1);
                });
            }
        }
    }
}
