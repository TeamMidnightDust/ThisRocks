package eu.midnightdust.motschen.rocks.block.blockentity;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityInit {
    public static BlockEntityType<StarfishBlockEntity> STARFISH_BE;

    public static void init() {
        STARFISH_BE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RocksMain.MOD_ID,"starfish_blockentity"), BlockEntityType.Builder.create(StarfishBlockEntity::new, RocksMain.Starfish).build(null));
    }
}
