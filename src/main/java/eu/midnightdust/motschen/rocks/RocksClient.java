package eu.midnightdust.motschen.rocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class RocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), new Identifier("red"), (stack, world, entity, seed) -> (stack.getTag() != null && stack.getTag().getString("variation").equals("red")) ?  1 : 0);
        FabricModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), new Identifier("pink"), (stack, world, entity, seed) -> (stack.getTag() != null && stack.getTag().getString("variation").equals("pink")) ?  1 : 0);
        FabricModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), new Identifier("orange"), (stack, world, entity, seed) -> (stack.getTag() != null && stack.getTag().getString("variation").equals("orange")) ?  1 : 0);
    }
}
