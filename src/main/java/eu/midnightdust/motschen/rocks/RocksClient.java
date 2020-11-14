package eu.midnightdust.motschen.rocks;

import eu.midnightdust.motschen.rocks.block.blockentity.BlockEntityInit;
import eu.midnightdust.motschen.rocks.block.render.StarfishBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

public class RocksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityInit.STARFISH_BE, StarfishBlockEntityRenderer::new);
    }
}
