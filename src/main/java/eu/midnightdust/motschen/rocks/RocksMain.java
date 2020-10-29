package eu.midnightdust.motschen.rocks;

import eu.midnightdust.motschen.rocks.block.*;
import eu.midnightdust.motschen.rocks.blockstates.RockVariation;
import eu.midnightdust.motschen.rocks.blockstates.SeashellVariation;
import eu.midnightdust.motschen.rocks.blockstates.StickVariation;
import eu.midnightdust.motschen.rocks.world.FeatureInjector;
import eu.midnightdust.motschen.rocks.world.MiscFeatures;
import eu.midnightdust.motschen.rocks.world.RockFeatures;
import eu.midnightdust.motschen.rocks.world.StickFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RocksMain implements ModInitializer {

    public static final String MOD_ID = "rocks";

    public static final ItemGroup RocksGroup = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "rocks"), () -> new ItemStack(RocksMain.Rock));

    public static final EnumProperty<RockVariation> ROCK_VARIATION = EnumProperty.of("variation", RockVariation.class);
    public static final EnumProperty<StickVariation> STICK_VARIATION = EnumProperty.of("variation", StickVariation.class);
    public static final EnumProperty<SeashellVariation> SEASHELL_VARIATION = EnumProperty.of("variation", SeashellVariation.class);

    public static Block Rock = new Rock();
    public static Block SandRock = new Rock();
    public static Block RedSandRock = new Rock();
    public static Block EndstoneRock = new Rock();

    public static Block OakStick = new Stick();
    public static Block SpruceStick = new Stick();
    public static Block BirchStick = new Stick();
    public static Block AcaciaStick = new Stick();
    public static Block JungleStick = new Stick();
    public static Block DarkOakStick = new Stick();

    public static Block Pinecone = new Pinecone();
    public static Block Seashell = new Seashell();

    public static Item CobbleStoneSplitter = new Item(new Item.Settings().group(RocksMain.RocksGroup));
    public static Item SandStoneSplitter = new Item(new Item.Settings().group(RocksMain.RocksGroup));
    public static Item RedSandStoneSplitter = new Item(new Item.Settings().group(RocksMain.RocksGroup));
    public static Item EndStoneSplitter = new Item(new Item.Settings().group(RocksMain.RocksGroup));

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"rock"), Rock);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"rock"), new BlockItem(Rock, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"sand_rock"), SandRock);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"sand_rock"), new BlockItem(SandRock, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"red_sand_rock"), RedSandRock);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"red_sand_rock"), new BlockItem(RedSandRock, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"end_stone_rock"), EndstoneRock);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"end_stone_rock"), new BlockItem(EndstoneRock, new Item.Settings().group(RocksMain.RocksGroup)));

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"oak_stick"), OakStick);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"oak_stick"), new BlockItem(OakStick, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"spruce_stick"), SpruceStick);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"spruce_stick"), new BlockItem(SpruceStick, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"birch_stick"), BirchStick);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"birch_stick"), new BlockItem(BirchStick, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"acacia_stick"), AcaciaStick);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"acacia_stick"), new BlockItem(AcaciaStick, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"jungle_stick"), JungleStick);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"jungle_stick"), new BlockItem(JungleStick, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"dark_oak_stick"), DarkOakStick);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"dark_oak_stick"), new BlockItem(DarkOakStick, new Item.Settings().group(RocksMain.RocksGroup)));
        
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"pinecone"), Pinecone);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"pinecone"), new BlockItem(Pinecone, new Item.Settings().group(RocksMain.RocksGroup)));
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID,"seashell"), Seashell);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"seashell"), new BlockItem(Seashell, new Item.Settings().group(RocksMain.RocksGroup)));

        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"cobblestone_splitter"), CobbleStoneSplitter);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"sandstone_splitter"), SandStoneSplitter);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"red_sandstone_splitter"), RedSandStoneSplitter);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID,"end_stone_splitter"), EndStoneSplitter);

        RockFeatures.init();
        StickFeatures.init();
        MiscFeatures.init();
        FeatureInjector.init();
    }
}
