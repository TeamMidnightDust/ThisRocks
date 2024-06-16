package eu.midnightdust.motschen.rocks;

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
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
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
    public static ItemGroup RocksGroup;
    public static final RegistryKey<ItemGroup> ROCKS_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "rocks"));

    @Override
    public void onInitialize() {
        RocksConfig.init("rocks", RocksConfig.class);

        registerBlockWithItem(Identifier.of(MOD_ID,"rock"), Rock);
        registerBlockWithItem(Identifier.of(MOD_ID,"granite_rock"), GraniteRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"diorite_rock"), DioriteRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"andesite_rock"), AndesiteRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"sand_rock"), SandRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"red_sand_rock"), RedSandRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"gravel_rock"), GravelRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"end_stone_rock"), EndstoneRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"netherrack_rock"), NetherrackRock);
        registerBlockWithItem(Identifier.of(MOD_ID,"soul_soil_rock"), SoulSoilRock);

        registerBlockWithItem(Identifier.of(MOD_ID,"oak_stick"), OakStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"spruce_stick"), SpruceStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"birch_stick"), BirchStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"acacia_stick"), AcaciaStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"jungle_stick"), JungleStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"dark_oak_stick"), DarkOakStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"mangrove_stick"), MangroveStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"cherry_stick"), CherryStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"bamboo_stick"), BambooStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"crimson_stick"), CrimsonStick);
        registerBlockWithItem(Identifier.of(MOD_ID,"warped_stick"), WarpedStick);

        registerBlockWithItem(Identifier.of(MOD_ID,"geyser"), Geyser);
        registerBlockWithItem(Identifier.of(MOD_ID,"nether_geyser"), NetherGeyser);

        registerBlockWithItem(Identifier.of(MOD_ID,"pinecone"), Pinecone);
        registerBlockWithItem(Identifier.of(MOD_ID,"seashell"), Seashell);
        registerBlockWithItem(Identifier.of(MOD_ID,"starfish"), Starfish);

        registerItem(Identifier.of(MOD_ID,"cobblestone_splitter"), CobblestoneSplitter);
        registerItem(Identifier.of(MOD_ID,"granite_splitter"), GraniteSplitter);
        registerItem(Identifier.of(MOD_ID,"diorite_splitter"), DioriteSplitter);
        registerItem(Identifier.of(MOD_ID,"andesite_splitter"), AndesiteSplitter);
        registerItem(Identifier.of(MOD_ID,"sandstone_splitter"), SandStoneSplitter);
        registerItem(Identifier.of(MOD_ID,"red_sandstone_splitter"), RedSandStoneSplitter);
        registerItem(Identifier.of(MOD_ID,"end_stone_splitter"), EndStoneSplitter);
        registerItem(Identifier.of(MOD_ID,"netherrack_splitter"), NetherrackSplitter);
        registerItem(Identifier.of(MOD_ID,"soul_soil_splitter"), SoulSoilSplitter);

        RocksGroup = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.rocks.rocks")).icon(() -> new ItemStack(RocksMain.Rock)).entries(((displayContext, entries) -> {
            List<ItemStack> visibleGroupItems = new ArrayList<>(groupItems);
            entries.addAll(visibleGroupItems);
        })).build();
        Registry.register(Registries.ITEM_GROUP, ROCKS_GROUP, RocksGroup);
        new FeatureRegistry<>();
        FeatureInjector.init();
        BlockEntityInit.init();
    }


}
