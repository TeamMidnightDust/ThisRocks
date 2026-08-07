package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.block.NetherGeyser;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.util.ParticleUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class NetherGeyserBlockEntity extends BlockEntity {
    private int countdown = 0;

    public NetherGeyserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.NETHER_GEYSER_BE, pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, NetherGeyserBlockEntity blockEntity) {
        if (world == null || world.isClient()) return;
        if (world.getBlockState(pos).getBlock() == RocksMain.NetherGeyser) {
            Player player = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, true);
            Player player2 = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, true);


            if (player != null) {
                world.setBlockState(pos, state.with(NetherGeyser.ACTIVE, true));

                if (RocksConfig.netherGeyserDamage && world instanceof ServerLevel serverWorld) {
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
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.LAVA, new Vec3(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5), new Vec3(1, 1.5d, 1), 1);
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.LAVA, new Vec3(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5), new Vec3(1, 1.5d, 1), 1);
                    ParticleUtil.spawnParticle(watchingPlayer, ParticleTypes.SMOKE, new Vec3(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5), new Vec3(0, 0.3, 0), 1);
                });
            }
        }
    }
}
