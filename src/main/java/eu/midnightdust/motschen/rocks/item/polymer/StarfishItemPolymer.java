package eu.midnightdust.motschen.rocks.item.polymer;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.pb4.factorytools.api.item.AutoModeledPolymerItem;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.hasModOnClient;
import static eu.midnightdust.motschen.rocks.util.polymer.PolyUtil.polymerId;

public class StarfishItemPolymer extends BlockItem implements AutoModeledPolymerItem {
    private final Item polymerItem;

    public <T extends Block & PolymerBlock> StarfishItemPolymer(T block, Settings settings, Item item) {
        super(block, settings);
        this.polymerItem = item;
    }

    @Override
    public Item getPolymerItem() {
        return polymerItem;
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        var state = itemStack.getComponents().get(DataComponentTypes.BLOCK_STATE);
        if (state != null && !state.isEmpty()) {
            StarfishVariation variation = state.getValue(RocksMain.STARFISH_VARIATION);
            if (variation != null) return MODELS.get(variation).value();
        }
        return MODELS.get(this).value();
    }

    @Override
    public void onRegistered(Identifier selfId) {
        var item = Identifier.of(selfId.getNamespace(), "item/" + selfId.getPath());
        MODELS.put(this, PolymerResourcePackUtils.requestModel(this.getPolymerItem(), item));
        for (StarfishVariation variation : StarfishVariation.values()) {
            MODELS.put(variation, PolymerResourcePackUtils.requestModel(this.getPolymerItem(), polymerId("item/" + variation.toString() + "_" + selfId.getPath())));
        }
    }

    @Override
    public boolean canSyncRawToClient(@Nullable ServerPlayerEntity player) {
        return hasModOnClient(player);
    }
}
