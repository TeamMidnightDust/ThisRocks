package eu.midnightdust.motschen.rocks.util.polymer;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.block.Rock;
import eu.midnightdust.motschen.rocks.block.Starfish;
import eu.midnightdust.motschen.rocks.block.Stick;
import eu.midnightdust.motschen.rocks.block.polymer.*;
import eu.midnightdust.motschen.rocks.block.polymer.model.*;
import eu.midnightdust.motschen.rocks.item.polymer.StarfishItemPolymer;
import eu.midnightdust.motschen.rocks.util.RockType;
import eu.pb4.factorytools.api.item.FactoryBlockItem;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.core.api.utils.PolymerSyncUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.impl.HolderHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static eu.midnightdust.motschen.rocks.RocksMain.*;
import static net.minecraft.state.property.Properties.WATERLOGGED;

public class PolyUtil {
    public static BlockState SMALL_BLOCK;
    public static BlockState PASSABLE_WATERLOGGED_BLOCK;

    public static void init() {
        SMALL_BLOCK = PolymerBlockResourceUtils.requestEmpty(BlockModelType.TRIPWIRE_BLOCK_FLAT);
        if (SMALL_BLOCK == null) SMALL_BLOCK = Blocks.STRUCTURE_VOID.getDefaultState();

        PASSABLE_WATERLOGGED_BLOCK = PolymerBlockResourceUtils.requestEmpty(BlockModelType.KELP_BLOCK);
        if (PASSABLE_WATERLOGGED_BLOCK == null) SMALL_BLOCK = Blocks.BARRIER.getDefaultState().with(WATERLOGGED, true);

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        ItemDisplayNetherGeyserModel.initModels();
        ItemDisplayOverworldGeyserModel.initModels();
        ItemDisplayPineconeModel.initModels();
        ItemDisplayRockModel.initModels();
        ItemDisplaySeashellModel.initModels();
        ItemDisplayStarfishModel.initModels();
        ItemDisplayStickModel.initModels();
    }
    public static Identifier polymerId(String path) {
        return Identifier.of("polymer-rocks", path);
    }

    public static boolean hasModOnClient(ServerPlayerEntity player) {
        return playersWithMod.contains(player);
    }

    public static Item polymerBlockItem(Block block, Identifier id) {
        if (block instanceof Starfish) return new StarfishItemPolymer((Block & PolymerBlock) block, new Item.Settings(), Items.KELP);
        else return new FactoryBlockItem((Block & PolymerBlock) block, new Item.Settings(), Items.KELP);
    }

    public static Item simplePolymerItem(Identifier id) {
        return new ModeledItem(Items.FLINT, new Item.Settings());
    }

    public static void registerPolymerGroup() {
        RocksMain.RocksGroup = PolymerItemGroupUtils.builder().displayName(Text.translatable("itemGroup.rocks.rocks")).icon(() ->
                new ItemStack(rocksByType.get(RockType.STONE))).entries((displayContext, entries) -> entries.addAll(RocksMain.groupItems)).build();
        PolymerItemGroupUtils.registerPolymerItemGroup(id("rocks"), RocksMain.RocksGroup);
    }

    public static void registerBlockEntities(BlockEntityType<?>... types) {
        PolymerBlockUtils.registerBlockEntity(types);
    }

    public static void hideElementHolders(ServerPlayerEntity player) {
        PolymerSyncUtils.removeCreativeTab(RocksGroup, player.networkHandler);

        List<ElementHolder> holders = new ArrayList<>(((HolderHolder)player.networkHandler).polymer$getHolders());
        for (ElementHolder holder : holders) {
            if (holder.getAttachment() instanceof BlockBoundAttachment bbAttachment
                    && bbAttachment.getBlockState().getBlock().getTranslationKey().startsWith("block.rocks.")) {

                bbAttachment.stopWatching(player);
                player.networkHandler.chunkDataSender.unload(player, bbAttachment.getChunk().getPos());
                player.networkHandler.chunkDataSender.add(bbAttachment.getChunk());
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
