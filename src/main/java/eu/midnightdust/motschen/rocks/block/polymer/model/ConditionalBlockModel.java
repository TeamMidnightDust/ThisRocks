package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;

public abstract class ConditionalBlockModel extends BlockModel {
    @Override
    public boolean startWatching(ServerGamePacketListenerImpl player) {
        if (hasModOnClient(player.player)) return false;
        else return super.startWatching(player);
    }
}
