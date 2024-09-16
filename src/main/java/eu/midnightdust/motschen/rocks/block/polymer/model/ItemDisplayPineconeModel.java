package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.pb4.factorytools.api.resourcepack.BaseItemProvider;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class ItemDisplayPineconeModel extends ConditionalBlockModel {
    private final ItemDisplayElement main;
    private static ItemStack PINECONE_MODEL;

    public static void initModels() {
        PINECONE_MODEL = BaseItemProvider.requestModel(RocksMain.id("block/pinecone"));
    }

    public ItemDisplayPineconeModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple(getModel(state));
        this.main.setDisplaySize(1, 1);
        this.main.setScale(new Vector3f(1));
        this.main.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(pos.hashCode() % 360));
        this.main.setOffset(new Vec3d(0, -0.25d, 0));
        this.main.setViewRange(0.75f * (RocksConfig.polymerViewDistance / 100f));
        this.addElement(this.main);
    }

    private ItemStack getModel(BlockState state) {
        return PINECONE_MODEL;
    }
}
