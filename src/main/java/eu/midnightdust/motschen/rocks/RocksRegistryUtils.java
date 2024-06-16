package eu.midnightdust.motschen.rocks;

import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import static eu.midnightdust.motschen.rocks.RocksMain.STARFISH_VARIATION;

public class RocksRegistryUtils {
    public static void registerBlockWithItem(Identifier id, Block block) {
        Registry.register(Registries.BLOCK, id, block);
        registerItem(id, new BlockItem(block, new Item.Settings()));
    }
    public static void registerItem(Identifier id, Item item) {
        Registry.register(Registries.ITEM, id, item);
        if (id.equals(Identifier.of(RocksMain.MOD_ID, "starfish"))) putStarfishItems(item);
        else {
            ItemStack itemStack = new ItemStack(item);
            RocksMain.groupItems.add(itemStack);
        }
    }
    private static void putStarfishItems(Item starfish) {
        ItemStack redStarfish = new ItemStack(starfish);
        redStarfish.applyComponentsFrom(ComponentMap.builder().add(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(STARFISH_VARIATION, StarfishVariation.RED)).build());
        RocksMain.groupItems.add(redStarfish);
        ItemStack orangeStarfish = new ItemStack(starfish);
        orangeStarfish.applyComponentsFrom(ComponentMap.builder().add(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(STARFISH_VARIATION, StarfishVariation.ORANGE)).build());
        RocksMain.groupItems.add(orangeStarfish);
        ItemStack pinkStarfish = new ItemStack(starfish);
        pinkStarfish.applyComponentsFrom(ComponentMap.builder().add(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(STARFISH_VARIATION, StarfishVariation.PINK)).build());
        RocksMain.groupItems.add(pinkStarfish);
    }
    public static void register(Registerable<ConfiguredFeature<?, ?>> context, String name, ConfiguredFeature<?, ?> feature) {
        context.register(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(RocksMain.MOD_ID, name)), feature);
    }
    public static void register(Registerable<PlacedFeature> context, String name, PlacedFeature feature) {
        context.register(RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(RocksMain.MOD_ID, name)), feature);
    }
}
