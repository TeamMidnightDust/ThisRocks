package eu.midnightdust.motschen.rocks.item.polymer;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;
import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.polymerId;

public class StarfishItemPolymer extends BlockItem implements PolymerItem {
    private final Item polymerItem;

    public <T extends Block & PolymerBlock> StarfishItemPolymer(T block, Settings settings, Item item) {
        super(block, settings);
        this.polymerItem = item;
    }

    @Override
    public @Nullable Identifier getPolymerItemModel(ItemStack itemStack, PacketContext context) {
        var state = itemStack.getComponents().get(DataComponentTypes.BLOCK_STATE);
        if (state != null && !state.isEmpty()) {
            StarfishVariation variation = state.getValue(RocksMain.STARFISH_VARIATION);
            if (variation != null) return ResourcePackExtras.bridgeModel(polymerId("item/"+variation + "_starfish"));
        }
        return itemStack.get(DataComponentTypes.ITEM_MODEL);
    }

    @Override
    public boolean canSyncRawToClient(PacketContext context) {
        return hasModOnClient(context.getPlayer());
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext packetContext) {
        return polymerItem;
    }
}
