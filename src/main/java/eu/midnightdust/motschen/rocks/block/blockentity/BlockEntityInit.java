package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static eu.midnightdust.motschen.rocks.RocksMain.polymerMode;

public class BlockEntityInit {
    public static BlockEntityType<OverworldGeyserBlockEntity> OVERWORLD_GEYSER_BE;
    public static BlockEntityType<NetherGeyserBlockEntity> NETHER_GEYSER_BE;

    public static void init() {
        OVERWORLD_GEYSER_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(RocksMain.MOD_ID,"overworld_geyser_blockentity"), BlockEntityType.Builder.create(OverworldGeyserBlockEntity::new, RocksMain.Geyser).build(null));
        NETHER_GEYSER_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(RocksMain.MOD_ID,"nether_geyser_blockentity"), BlockEntityType.Builder.create(NetherGeyserBlockEntity::new, RocksMain.NetherGeyser).build(null));
        if (polymerMode) PolyUtil.registerBlockEntities(OVERWORLD_GEYSER_BE, NETHER_GEYSER_BE);
    }
}
