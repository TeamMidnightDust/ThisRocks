package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.block.OverworldGeyser;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.entity.decoration.Brightness;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class ItemDisplayOverworldGeyserModel extends ConditionalBlockModel {
    private final ItemDisplayElement main;
    private final ItemDisplayElement magma;
    public static ItemStack OVERWORLD;

    public static void initModels() {
        OVERWORLD = ItemDisplayElementUtil.getModel(RocksMain.id("block/geyser_off"));
    }

    public ItemDisplayOverworldGeyserModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple(getModel(state));
        this.main.setDisplaySize(1, 1);
        this.main.setScale(new Vector3f(2));
        int rotation = pos.hashCode() % 360;
        this.main.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        if (state.get(Properties.SNOWY)) this.main.setOffset(new Vec3d(0d, 0.125d, 0d));
        this.main.setViewRange(0.75f * (RocksConfig.polymerViewDistance / 100f));
        this.addElement(this.main);

        this.magma = ItemDisplayElementUtil.createSimple(new ItemStack(Items.MAGMA_BLOCK));
        this.magma.setDisplaySize(1, 1);
        this.magma.setScale(new Vector3f(0.73f, 0.01f, 0.73f));
        this.magma.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        this.magma.setBrightness(Brightness.FULL);
        this.magma.setOffset(new Vec3d(0d, state.get(Properties.SNOWY) ? -0.355d : -0.48d, 0d));
        this.magma.setViewRange(state.get(OverworldGeyser.ACTIVE) ? (0.75f * (RocksConfig.polymerViewDistance / 100f)) : 0);
        this.addElement(this.magma);
    }

    @Override
    public void notifyUpdate(HolderAttachment.UpdateType updateType) {
        if (updateType == BlockAwareAttachment.BLOCK_STATE_UPDATE) {
            var state = this.blockState();
            if (state.get(Properties.SNOWY)) {
                this.main.setOffset(new Vec3d(0d, 0.125d, 0d));
                this.magma.setOffset(new Vec3d(0d, -0.355d, 0d));
            }
            else {
                this.main.setOffset(new Vec3d(0, 0, 0));
                this.magma.setOffset(new Vec3d(0, -0.48d, 0));
            }
            this.magma.setViewRange(state.get(OverworldGeyser.ACTIVE) ? (0.75f * (RocksConfig.polymerViewDistance / 100f)) : 0);

            this.tick();
        }
    }
    private ItemStack getModel(BlockState state) {
        return OVERWORLD;
    }
}
