package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.util.StickType;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class ItemDisplayStickModel extends ConditionalBlockModel {
    private final ItemDisplayElement main;
    public static Map<StickType, ItemStack[]> models = new HashMap<>();

    public static void initModels() {
        for (StickType type : StickType.values()) {
            var stacks = new ItemStack[3];
            stacks[0] = ItemDisplayElementUtil.getModel(RocksMain.id("block/small_"+type.name()+"_stick"));
            stacks[1] = ItemDisplayElementUtil.getModel(RocksMain.id("block/medium_"+type.name()+"_stick"));
            stacks[2] = ItemDisplayElementUtil.getModel(RocksMain.id("block/large_"+type.name()+"_stick"));
            models.put(type, stacks);
        }
    }

    public ItemDisplayStickModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple(getModel(state));
        this.main.setDisplaySize(1, 1);
        this.main.setScale(new Vector3f(1));
        this.main.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(90 * (pos.hashCode() % 4)));
        this.main.setViewRange(0.75f * (RocksConfig.polymerViewDistance / 100f));
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
        return models.get(StickType.fromBlockName(state.getBlock().getTranslationKey()))[state.get(RocksMain.STICK_VARIATION).ordinal()];
    }
}
