package eu.midnightdust.motschen.rocks.block.polymer.model;

import com.mojang.math.Axis;
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
//? if >= 26.1
import eu.pb4.factorytools.api.util.LazyItemStack;

public class ItemDisplaySeashellModel extends ConditionalBlockModel {
    private final ItemDisplayElement main;
    //~ if >= 26.1 'ItemStack' -> 'LazyItemStack' {
    public static LazyItemStack PINK;
    public static LazyItemStack WHITE;
    public static LazyItemStack YELLOW;
    //~}

    public static void initModels() {
        PINK = ItemDisplayElementUtil.getModel(RocksMain.id("block/seashell_pink"));
        WHITE = ItemDisplayElementUtil.getModel(RocksMain.id("block/seashell_white"));
        YELLOW = ItemDisplayElementUtil.getModel(RocksMain.id("block/seashell_yellow"));
    }

    public ItemDisplaySeashellModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple(getModel(state));
        this.main.setDisplaySize(1, 1);
        this.main.setScale(new Vector3f(2));
        this.main.setRightRotation(Axis.YP.rotationDegrees(90 * (pos.hashCode() % 4)));
        this.main.setViewRange(0.5f * (RocksConfig.polymerViewDistance / 100f));
        this.addElement(this.main);
    }

    @Override
    public void notifyUpdate(HolderAttachment.UpdateType updateType) {
        if (updateType == BlockAwareAttachment.BLOCK_STATE_UPDATE) {
            var state = this.blockState();
            this.main.setItem(getModel(state));

            this.tick();
        }
    }
    private ItemStack getModel(BlockState state) {
        return switch (state.getValue(RocksMain.SEASHELL_VARIATION)) {
            case PINK -> PINK/*? if >= 26.1 {*/.get()/*?}*/;
            case WHITE -> WHITE/*? if >= 26.1 {*/.get()/*?}*/;
            case YELLOW -> YELLOW/*? if >= 26.1 {*/.get()/*?}*/;
        };
    }
}
