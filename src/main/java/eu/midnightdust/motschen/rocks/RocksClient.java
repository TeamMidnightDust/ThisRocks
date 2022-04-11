package eu.midnightdust.motschen.rocks;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class RocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), new Identifier("red"), (stack, world, entity, seed) -> (stack.getNbt() != null && stack.getNbt().getString("variation").equals("red")) ?  1 : 0);
        ModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), new Identifier("pink"), (stack, world, entity, seed) -> (stack.getNbt() != null && stack.getNbt().getString("variation").equals("pink")) ?  1 : 0);
        ModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), new Identifier("orange"), (stack, world, entity, seed) -> (stack.getNbt() != null && stack.getNbt().getString("variation").equals("orange")) ?  1 : 0);
    }
}
