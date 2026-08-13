package eu.midnightdust.motschen.rocks.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ParticleUtil {
    public static void spawnParticle(ServerPlayer player, ParticleType<?> type, Vec3 pos, Vec3 offset, float speed) {
        ServerPlayNetworking.getSender(player).sendPacket(new ClientboundLevelParticlesPacket((ParticleOptions) type, false, true, pos.x, pos.y, pos.z,
                (float) offset.x / 16f, (float) offset.y / 16f, (float) offset.z / 16f, speed, 1));
    }
}
