package eu.midnightdust.motschen.rocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class RocksRegistryUtils {
    public static void registerBlockWithItem(Identifier id, Block block) {
        Registry.register(Registries.BLOCK, id, block);
        registerItem(id, new BlockItem(block, new Item.Settings()));
    }
    public static void registerItem(Identifier id, Item item) {
        Registry.register(Registries.ITEM, id, item);
        if (id.equals(new Identifier(RocksMain.MOD_ID, "starfish"))) putStarfishItems(item);
        else {
            ItemStack itemStack = new ItemStack(item);
            RocksMain.groupItems.add(itemStack);
            if (id.equals(new Identifier(RocksMain.MOD_ID, "cherry_stick"))) RocksMain.cherryStack = itemStack;
        }
    }
    private static void putStarfishItems(Item starfish) {
        ItemStack redStarfish = new ItemStack(starfish);
        redStarfish.getOrCreateNbt().putString("variation", "red");
        RocksMain.groupItems.add(redStarfish);
        ItemStack orangeStarfish = new ItemStack(starfish);
        orangeStarfish.getOrCreateNbt().putString("variation", "orange");
        RocksMain.groupItems.add(orangeStarfish);
        ItemStack pinkStarfish = new ItemStack(starfish);
        pinkStarfish.getOrCreateNbt().putString("variation", "pink");
        RocksMain.groupItems.add(pinkStarfish);
    }
    public static void register(Registerable<ConfiguredFeature<?, ?>> context, String name, ConfiguredFeature<?, ?> feature) {
        context.register(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(RocksMain.MOD_ID, name)), feature);
    }
    public static void register(Registerable<PlacedFeature> context, String name, PlacedFeature feature) {
        context.register(RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(RocksMain.MOD_ID, name)), feature);
    }
}
