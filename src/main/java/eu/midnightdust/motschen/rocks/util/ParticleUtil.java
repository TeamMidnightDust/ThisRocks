package eu.midnightdust.motschen.rocks.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ParticleUtil {
    public static void spawnParticle(ServerPlayerEntity player, ParticleType<?> type, Vec3d pos, Vec3d offset, float speed) {
        ServerPlayNetworking.getSender(player).sendPacket(new ParticleS2CPacket((ParticleEffect) type, false, true, pos.x, pos.y, pos.z,
                (float) offset.x / 16f, (float) offset.y / 16f, (float) offset.z / 16f, speed, 1));
    }
}
