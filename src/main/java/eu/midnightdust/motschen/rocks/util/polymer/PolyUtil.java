package eu.midnightdust.motschen.rocks.util.polymer;

import eu.midnightdust.motschen.rocks.block.Rock;
import eu.midnightdust.motschen.rocks.block.Starfish;
import eu.midnightdust.motschen.rocks.block.Stick;
import eu.midnightdust.motschen.rocks.block.polymer.*;
import eu.midnightdust.motschen.rocks.block.polymer.model.*;
import eu.midnightdust.motschen.rocks.item.polymer.StarfishItemPolymer;
import eu.midnightdust.motschen.rocks.util.RocksCreativeTab;
import eu.pb4.factorytools.api.item.FactoryBlockItem;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerSyncUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.impl.HolderHolder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
//? if >= 26.1 {
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.core.api.item.PolymerCreativeModeTabUtils;
//?} else {
/*import xyz.nucleoid.packettweaker.PacketContext;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
*///?}

import static eu.midnightdust.motschen.rocks.RocksMain.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class PolyUtil {
    public static BlockState SMALL_BLOCK;
    public static BlockState PASSABLE_WATERLOGGED_BLOCK;

    public static void init() {
        //~ if >= 26.1 'TRIPWIRE_BLOCK_FLAT' -> 'TRIPWIRE_FLAT'
        SMALL_BLOCK = PolymerBlockResourceUtils.requestEmpty(BlockModelType.TRIPWIRE_FLAT);
        if (SMALL_BLOCK == null) SMALL_BLOCK = Blocks.STRUCTURE_VOID.defaultBlockState();

        //~ if >= 26.1 'KELP_BLOCK' -> 'KELP'
        PASSABLE_WATERLOGGED_BLOCK = PolymerBlockResourceUtils.requestEmpty(BlockModelType.KELP);
        if (PASSABLE_WATERLOGGED_BLOCK == null) SMALL_BLOCK = Blocks.BARRIER.defaultBlockState().setValue(WATERLOGGED, true);

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        ResourcePackExtras.forDefault().addBridgedModelsFolder(id("block"), id("item"));

        ItemDisplayNetherGeyserModel.initModels();
        ItemDisplayOverworldGeyserModel.initModels();
        ItemDisplayPineconeModel.initModels();
        ItemDisplayRockModel.initModels();
        ItemDisplaySeashellModel.initModels();
        ItemDisplayStarfishModel.initModels();
        ItemDisplayStickModel.initModels();
    }

    public static boolean hasModOnClient(@Nullable PacketContext context) {
        //~ if >= 26.1 'context.getPlayer()' -> 'PolymerCommonUtils.getPlayer(context)'
        return context != null && hasModOnClient(PolymerCommonUtils.getPlayer(context));
    }

    public static boolean hasModOnClient(ServerPlayer player) {
        return playersWithMod.contains(player);
    }

    public static Item polymerBlockItem(Block block, Identifier id) {
        if (block instanceof Starfish) return new StarfishItemPolymer((Block & PolymerBlock) block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)), Items.KELP);
        else return new FactoryBlockItem((Block & PolymerBlock) block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)), Items.KELP);
    }

    public static Item simplePolymerItem(Identifier id) {
        return new SimplePolymerItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)), Items.FLINT, true);
    }

    public static void registerPolymerGroup() {
        //~ if >= 26.1 'PolymerItemGroupUtils' -> 'PolymerCreativeModeTabUtils' {
        RocksCreativeTab.RocksGroup = PolymerCreativeModeTabUtils.builder()
                .title(Component.translatable("itemGroup.rocks.rocks"))
                .icon(RocksCreativeTab::createIcon)
                .displayItems(RocksCreativeTab::createTabItems)
                .build();
        //~ if >= 26.1 'registerPolymerItemGroup' -> 'registerPolymerCreativeModeTab'
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(id("rocks"), RocksCreativeTab.RocksGroup);
        //~}
    }

    public static void registerBlockEntities(BlockEntityType<?>... types) {
        PolymerBlockUtils.registerBlockEntity(types);
    }

    public static void hideElementHolders(ServerPlayer player) {
        PolymerSyncUtils.removeCreativeTab(RocksCreativeTab.RocksGroup, player.connection);

        List<ElementHolder> holders = new ArrayList<>(((HolderHolder)player.connection).polymer$getHolders());
        for (ElementHolder holder : holders) {
            if (holder.getAttachment() instanceof BlockBoundAttachment bbAttachment
                    && bbAttachment.getBlockState().getBlock().getDescriptionId().startsWith("block.rocks.")) {

                bbAttachment.stopWatching(player);
                player.connection.chunkSender.dropChunk(player, bbAttachment.getChunk().getPos());
                player.connection.chunkSender.markChunkPendingToSend(bbAttachment.getChunk());
            }
        }
    }

    public static Rock newRockPolymer(Identifier id) {return new RockPolymer(id);}
    public static Stick newStickPolymer(Identifier id) {return new StickPolymer(id);}
    public static Block newPineconePolymer(Identifier id) {return new PineconePolymer(id);}
    public static Block newSeashellPolymer(Identifier id) {return new SeashellPolymer(id);}
    public static Block newStarfishPolymer(Identifier id) {return new StarfishPolymer(id);}
    public static Block newOverworldGeyserPolymer(Identifier id) {return new OverworldGeyserPolymer(id);}
    public static Block newNetherGeyserPolymer(Identifier id) {return new NetherGeyserPolymer(id);}
}
