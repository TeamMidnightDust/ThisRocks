package eu.midnightdust.motschen.rocks;

import com.google.common.collect.Lists;
import eu.midnightdust.motschen.rocks.block.*;
import eu.midnightdust.motschen.rocks.block.blockentity.BlockEntityInit;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.config.RocksConfig;
import eu.midnightdust.motschen.rocks.world.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static eu.midnightdust.motschen.rocks.RocksRegistryUtils.registerBlockWithItem;
import static eu.midnightdust.motschen.rocks.RocksRegistryUtils.registerItem;

public class RocksMain implements ModInitializer {
    public static final String MOD_ID = "rocks";

    public static final EnumProperty<RockVariation> ROCK_VARIATION = EnumProperty.of("variation", RockVariation.class);
    public static final EnumProperty<StickVariation> STICK_VARIATION = EnumProperty.of("variation", StickVariation.class);
    public static final EnumProperty<SeashellVariation> SEASHELL_VARIATION = EnumProperty.of("variation", SeashellVariation.class);
    public static final EnumProperty<StarfishVariation> STARFISH_VARIATION = EnumProperty.of("variation", StarfishVariation.class);

    public static Block Rock = new Rock();
    public static Block GraniteRock = new Rock();
    public static Block DioriteRock = new Rock();
    public static Block AndesiteRock = new Rock();
    public static Block SandRock = new Rock();
    public static Block RedSandRock = new Rock();
    public static Block GravelRock = new Rock();
    public static Block EndstoneRock = new Rock();
    public static Block NetherrackRock = new Rock();
    public static Block SoulSoilRock = new Rock();

    public static Block OakStick = new Stick();
    public static Block SpruceStick = new Stick();
    public static Block BirchStick = new Stick();
    public static Block AcaciaStick = new Stick();
    public static Block JungleStick = new Stick();
    public static Block DarkOakStick = new Stick();
    public static Block MangroveStick = new Stick();
    public static Block CherryStick = new Stick();
    public static Block BambooStick = new Stick();
    public static Block CrimsonStick = new Stick();
    public static Block WarpedStick = new Stick();

    public static Block Pinecone = new Pinecone();
    public static Block Seashell = new Seashell();
    public static Block Starfish = new Starfish();
    public static Block Geyser = new OverworldGeyser();
    public static Block NetherGeyser = new NetherGeyser();

    public static Item CobblestoneSplitter = new Item(new Item.Settings());
    public static Item GraniteSplitter = new Item(new Item.Settings());
    public static Item DioriteSplitter = new Item(new Item.Settings());
    public static Item AndesiteSplitter = new Item(new Item.Settings());
    public static Item SandStoneSplitter = new Item(new Item.Settings());
    public static Item RedSandStoneSplitter = new Item(new Item.Settings());
    public static Item EndStoneSplitter = new Item(new Item.Settings());
    public static Item NetherrackSplitter = new Item(new Item.Settings());
    public static Item SoulSoilSplitter = new Item(new Item.Settings());
    public static List<ItemStack> groupItems = new ArrayList<>();
    public static ItemStack cherryStack;
    public static ItemGroup RocksGroup;

    @Override
    public void onInitialize() {
        RocksConfig.init("rocks", RocksConfig.class);

        registerBlockWithItem(new Identifier(MOD_ID,"rock"), Rock);
        registerBlockWithItem(new Identifier(MOD_ID,"granite_rock"), GraniteRock);
        registerBlockWithItem(new Identifier(MOD_ID,"diorite_rock"), DioriteRock);
        registerBlockWithItem(new Identifier(MOD_ID,"andesite_rock"), AndesiteRock);
        registerBlockWithItem(new Identifier(MOD_ID,"sand_rock"), SandRock);
        registerBlockWithItem(new Identifier(MOD_ID,"red_sand_rock"), RedSandRock);
        registerBlockWithItem(new Identifier(MOD_ID,"gravel_rock"), GravelRock);
        registerBlockWithItem(new Identifier(MOD_ID,"end_stone_rock"), EndstoneRock);
        registerBlockWithItem(new Identifier(MOD_ID,"netherrack_rock"), NetherrackRock);
        registerBlockWithItem(new Identifier(MOD_ID,"soul_soil_rock"), SoulSoilRock);

        registerBlockWithItem(new Identifier(MOD_ID,"oak_stick"), OakStick);
        registerBlockWithItem(new Identifier(MOD_ID,"spruce_stick"), SpruceStick);
        registerBlockWithItem(new Identifier(MOD_ID,"birch_stick"), BirchStick);
        registerBlockWithItem(new Identifier(MOD_ID,"acacia_stick"), AcaciaStick);
        registerBlockWithItem(new Identifier(MOD_ID,"jungle_stick"), JungleStick);
        registerBlockWithItem(new Identifier(MOD_ID,"dark_oak_stick"), DarkOakStick);
        registerBlockWithItem(new Identifier(MOD_ID,"mangrove_stick"), MangroveStick);
        registerBlockWithItem(new Identifier(MOD_ID,"cherry_stick"), CherryStick);
        registerBlockWithItem(new Identifier(MOD_ID,"bamboo_stick"), BambooStick);
        registerBlockWithItem(new Identifier(MOD_ID,"crimson_stick"), CrimsonStick);
        registerBlockWithItem(new Identifier(MOD_ID,"warped_stick"), WarpedStick);

        registerBlockWithItem(new Identifier(MOD_ID,"geyser"), Geyser);
        registerBlockWithItem(new Identifier(MOD_ID,"nether_geyser"), NetherGeyser);

        registerBlockWithItem(new Identifier(MOD_ID,"pinecone"), Pinecone);
        registerBlockWithItem(new Identifier(MOD_ID,"seashell"), Seashell);
        registerBlockWithItem(new Identifier(MOD_ID,"starfish"), Starfish);

        registerItem(new Identifier(MOD_ID,"cobblestone_splitter"), CobblestoneSplitter);
        registerItem(new Identifier(MOD_ID,"granite_splitter"), GraniteSplitter);
        registerItem(new Identifier(MOD_ID,"diorite_splitter"), DioriteSplitter);
        registerItem(new Identifier(MOD_ID,"andesite_splitter"), AndesiteSplitter);
        registerItem(new Identifier(MOD_ID,"sandstone_splitter"), SandStoneSplitter);
        registerItem(new Identifier(MOD_ID,"red_sandstone_splitter"), RedSandStoneSplitter);
        registerItem(new Identifier(MOD_ID,"end_stone_splitter"), EndStoneSplitter);
        registerItem(new Identifier(MOD_ID,"netherrack_splitter"), NetherrackSplitter);
        registerItem(new Identifier(MOD_ID,"soul_soil_splitter"), SoulSoilSplitter);

        RocksGroup = FabricItemGroup.builder(new Identifier(MOD_ID, "rocks")).icon(() -> new ItemStack(RocksMain.Rock)).entries(((displayContext, entries) -> {
            List<ItemStack> visibleGroupItems = new ArrayList<>(groupItems);
            if (!displayContext.enabledFeatures().contains(FeatureFlags.UPDATE_1_20)) visibleGroupItems.remove(cherryStack);
            entries.addAll(visibleGroupItems);
        })).build();
        new FeatureRegistry<>();
        FeatureInjector.init();
        BlockEntityInit.init();
    }


}
