package eu.midnightdust.motschen.rocks.networking;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record HelloPayload() implements CustomPacketPayload {
    public static final Identifier hello = RocksMain.id("hello_payload");
    public static final CustomPacketPayload.Type<HelloPayload> PACKET_ID = new CustomPacketPayload.Type<>(hello);
    public static final StreamCodec<RegistryFriendlyByteBuf, HelloPayload> codec = StreamCodec.of(HelloPayload::write, HelloPayload::read);

    public static HelloPayload read(RegistryFriendlyByteBuf buf) {
        return new HelloPayload();
    }

    public void write(RegistryFriendlyByteBuf buf) {}

    @Override
    public Id<? extends CustomPacketPayload> getId() {
        return PACKET_ID;
    }
}