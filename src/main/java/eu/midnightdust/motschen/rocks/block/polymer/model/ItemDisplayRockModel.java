package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.util.RockType;
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

public class ItemDisplayRockModel extends ConditionalBlockModel {
    private final ItemDisplayElement main;
    public static Map<RockType, ItemStack[]> models = new HashMap<>();

    public static void initModels() {
        for (RockType type : RockType.values()) {
            var stacks = new ItemStack[4];
            for (int i = 0; i < 4; i++) {
                stacks[i] = ItemDisplayElementUtil.getModel(RocksMain.id("block/"+type.getVariations()[i].getPath()));
            }
            models.put(type, stacks);
        }
    }

    public ItemDisplayRockModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple(getModel(state));
        this.main.setDisplaySize(1, 1);
        this.main.setScale(new Vector3f(2));
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
        return models.get(RockType.fromBlockName(state.getBlock().getTranslationKey()))[state.get(RocksMain.ROCK_VARIATION).ordinal()];
    }
}
