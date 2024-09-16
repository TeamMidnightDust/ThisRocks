package eu.midnightdust.motschen.rocks.networking;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record HelloPayload() implements CustomPayload {
    public static final Identifier hello = RocksMain.id("hello_payload");
    public static final CustomPayload.Id<HelloPayload> PACKET_ID = new CustomPayload.Id<>(hello);
    public static final PacketCodec<RegistryByteBuf, HelloPayload> codec = PacketCodec.of(HelloPayload::write, HelloPayload::read);

    public static HelloPayload read(RegistryByteBuf buf) {
        return new HelloPayload();
    }

    public void write(RegistryByteBuf buf) {}

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}