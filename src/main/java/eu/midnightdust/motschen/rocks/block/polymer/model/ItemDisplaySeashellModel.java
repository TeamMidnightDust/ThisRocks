package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.pb4.factorytools.api.resourcepack.BaseItemProvider;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class ItemDisplaySeashellModel extends ConditionalBlockModel {
    private final ItemDisplayElement main;
    public static ItemStack PINK;
    public static ItemStack WHITE;
    public static ItemStack YELLOW;

    public static void initModels() {
        PINK = BaseItemProvider.requestModel(RocksMain.id("block/seashell_pink"));
        WHITE = BaseItemProvider.requestModel(RocksMain.id("block/seashell_white"));
        YELLOW = BaseItemProvider.requestModel(RocksMain.id("block/seashell_yellow"));
    }

    public ItemDisplaySeashellModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple(getModel(state));
        this.main.setDisplaySize(1, 1);
        this.main.setScale(new Vector3f(2));
        this.main.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(90 * (pos.hashCode() % 4)));
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
        return switch (state.get(RocksMain.SEASHELL_VARIATION)) {
            case PINK -> PINK;
            case WHITE -> WHITE;
            case YELLOW -> YELLOW;
        };
    }
}
