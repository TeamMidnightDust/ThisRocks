package eu.midnightdust.motschen.rocks.util;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import static eu.midnightdust.motschen.rocks.RocksMain.STARFISH_VARIATION;
import static eu.midnightdust.motschen.rocks.RocksMain.polymerMode;

public class RegistryUtil {
    public static <T extends Block> T registerBlockWithItem(Identifier id, T block) {
        Registry.register(Registries.BLOCK, id, block);
        registerItem(id, blockItem(block, id));
        return block;
    }
    public static Item blockItem(Block block, Identifier id) {
        if (polymerMode) return PolyUtil.polymerBlockItem(block, id);
        return new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id)));
    }
    public static Item registerItem(Identifier id, Item item) {
        Registry.register(Registries.ITEM, id, item);
        if (id.equals(Identifier.of(RocksMain.MOD_ID, "starfish"))) putStarfishItems(item);
        else RocksMain.groupItems.add(new ItemStack(item));
        return item;
    }
    private static void putStarfishItems(Item starfish) {
        for (StarfishVariation variation : StarfishVariation.values()) {
            ItemStack starfishType = new ItemStack(starfish);
            starfishType.applyComponentsFrom(ComponentMap.builder().add(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(STARFISH_VARIATION, variation)).build());
            RocksMain.groupItems.add(starfishType);
        }
    }
    public static void register(Registerable<ConfiguredFeature<?, ?>> context, String name, ConfiguredFeature<?, ?> feature) {
        context.register(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(RocksMain.MOD_ID, name)), feature);
    }
    public static void register(Registerable<PlacedFeature> context, String name, PlacedFeature feature) {
        context.register(RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(RocksMain.MOD_ID, name)), feature);
    }
}
