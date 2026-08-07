package eu.midnightdust.motschen.rocks.util;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static eu.midnightdust.motschen.rocks.RocksMain.STARFISH_VARIATION;
import static eu.midnightdust.motschen.rocks.RocksMain.polymerMode;

public class RegistryUtil {
    public static <T extends Block> T registerBlockWithItem(Identifier id, T block) {
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        registerItem(id, blockItem(block, id));
        return block;
    }
    public static Item blockItem(Block block, Identifier id) {
        if (polymerMode) return PolyUtil.polymerBlockItem(block, id);
        return new BlockItem(block, new Item.Properties().registryKey(ResourceKey.of(Registries.ITEM, id)));
    }
    public static Item registerItem(Identifier id, Item item) {
        Registry.register(BuiltInRegistries.ITEM, id, item);
        if (id.equals(Identifier.of(RocksMain.MOD_ID, "starfish"))) putStarfishItems(item);
        else RocksMain.groupItems.add(new ItemStack(item));
        return item;
    }
    private static void putStarfishItems(Item starfish) {
        for (StarfishVariation variation : StarfishVariation.values()) {
            ItemStack starfishType = new ItemStack(starfish);
            starfishType.applyComponentsFrom(DataComponentMap.builder().add(DataComponents.BLOCK_STATE, BlockItemStateProperties.DEFAULT.with(STARFISH_VARIATION, variation)).build());
            RocksMain.groupItems.add(starfishType);
        }
    }
    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> context, String name, ConfiguredFeature<?, ?> feature) {
        context.register(ResourceKey.of(Registries.CONFIGURED_FEATURE, Identifier.of(RocksMain.MOD_ID, name)), feature);
    }
    public static void register(BootstrapContext<PlacedFeature> context, String name, PlacedFeature feature) {
        context.register(ResourceKey.of(Registries.PLACED_FEATURE, Identifier.of(RocksMain.MOD_ID, name)), feature);
    }
}
