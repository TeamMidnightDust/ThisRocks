package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityInit {
    public static BlockEntityType<StarfishBlockEntity> STARFISH_BE;
    public static BlockEntityType<OverworldGeyserBlockEntity> OVERWORLD_GEYSER_BE;
    public static BlockEntityType<NetherGeyserBlockEntity> NETHER_GEYSER_BE;

    public static void init() {
        STARFISH_BE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RocksMain.MOD_ID,"starfish_blockentity"), BlockEntityType.Builder.create(StarfishBlockEntity::new, RocksMain.Starfish).build(null));
        OVERWORLD_GEYSER_BE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RocksMain.MOD_ID,"overworld_geyser_blockentity"), BlockEntityType.Builder.create(OverworldGeyserBlockEntity::new, RocksMain.Geyser).build(null));
        NETHER_GEYSER_BE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RocksMain.MOD_ID,"nether_geyser_blockentity"), BlockEntityType.Builder.create(NetherGeyserBlockEntity::new, RocksMain.NetherGeyser).build(null));
    }
}
