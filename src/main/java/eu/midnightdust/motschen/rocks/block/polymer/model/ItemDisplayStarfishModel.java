package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import static eu.midnightdust.motschen.rocks.RocksMain.id;

public class ItemDisplayStarfishModel extends ConditionalBlockModel {
    private final ItemDisplayElement arm;
    public static ItemStack RED;
    public static ItemStack ORANGE;
    public static ItemStack PINK;

    public static void initModels() {
        RED = ItemDisplayElementUtil.getModel(id("block/starfish_red"));
        ORANGE = ItemDisplayElementUtil.getModel(id("block/starfish_orange"));
        PINK = ItemDisplayElementUtil.getModel(id("block/starfish_pink"));
    }

    public ItemDisplayStarfishModel(BlockState state, BlockPos pos) {
        ItemStack modelStack = getModel(state);
        int baseRotation = pos.hashCode() % 360;
        double xOffset = ((pos.hashCode() + pos.getX()) % 250 - 125) / 1000f;
        double zOffset = ((pos.hashCode() + pos.getZ()) % 250 - 125) / 1000f;
        arm = ItemDisplayElementUtil.createSimple(modelStack);
        arm.setDisplaySize(1, 1);
        arm.setScale(new Vector3f(1));
        arm.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(baseRotation));
        arm.setOffset(new Vec3d(xOffset, 0, zOffset));
        arm.setViewRange(0.4f * (RocksConfig.polymerViewDistance / 100f));
        this.addElement(arm);
    }

    @Override
    public void notifyUpdate(HolderAttachment.UpdateType updateType) {
        if (updateType == BlockAwareAttachment.BLOCK_STATE_UPDATE) {
            var state = this.blockState();
            ItemStack modelStack = getModel(state);
            arm.setItem(modelStack);

            this.tick();
        }
    }
    private ItemStack getModel(BlockState state) {
        return switch (state.get(RocksMain.STARFISH_VARIATION)) {
            case RED -> RED;
            case ORANGE -> ORANGE;
            case PINK -> PINK;
        };
    }
}
