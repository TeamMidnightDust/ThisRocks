package eu.midnightdust.motschen.rocks;

import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.networking.HelloPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Objects;

import static eu.midnightdust.motschen.rocks.RocksMain.STARFISH_VARIATION;

public class RocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sender.sendPacket(new HelloPayload()));

        ModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), Identifier.of("red"), (stack, world, entity, seed) -> matchesVariation(stack, StarfishVariation.RED));
        ModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), Identifier.of("pink"), (stack, world, entity, seed) -> matchesVariation(stack, StarfishVariation.PINK));
        ModelPredicateProviderRegistry.register(RocksMain.Starfish.asItem(), Identifier.of("orange"), (stack, world, entity, seed) -> matchesVariation(stack, StarfishVariation.ORANGE));
    }
    private static Integer matchesVariation(ItemStack stack, StarfishVariation variation) {
        var blockStateData = stack.getComponents().get(DataComponentTypes.BLOCK_STATE);
        if (blockStateData == null || blockStateData.isEmpty() || blockStateData.getValue(STARFISH_VARIATION) == null) return 0;
        return Objects.equals(blockStateData.getValue(STARFISH_VARIATION), variation) ? 1 : 0;
    }
}
