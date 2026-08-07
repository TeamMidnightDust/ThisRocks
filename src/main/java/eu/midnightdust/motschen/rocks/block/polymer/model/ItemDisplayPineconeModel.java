package eu.midnightdust.motschen.rocks.block.polymer.model;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ItemDisplayPineconeModel extends ConditionalBlockModel {
    private final ItemDisplayElement main;
    private static ItemStack PINECONE_MODEL;

    public static void initModels() {
        PINECONE_MODEL = ItemDisplayElementUtil.getModel(RocksMain.id("block/pinecone")).get();
    }

    public ItemDisplayPineconeModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple(getModel(state));
        this.main.setDisplaySize(1, 1);
        this.main.setScale(new Vector3f(1));
        this.main.setRightRotation(Axis.YP.rotationDegrees(pos.hashCode() % 360));
        this.main.setOffset(new Vec3(0, -0.25d, 0));
        this.main.setViewRange(0.75f * (RocksConfig.polymerViewDistance / 100f));
        this.addElement(this.main);
    }

    private ItemStack getModel(BlockState state) {
        return PINECONE_MODEL;
    }
}
