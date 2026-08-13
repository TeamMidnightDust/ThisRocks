package eu.midnightdust.motschen.rocks.util;

import eu.midnightdust.motschen.rocks.util.polymer.PolyUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static eu.midnightdust.motschen.rocks.RocksMain.*;

//? if >= 26.1 {
import net.minecraft.world.item.ItemStackTemplate;
//?}

public class RocksCreativeTab {
    //~ if >= 26.1 'ItemStack' -> 'ItemStackTemplate'
    private static final List<ItemStackTemplate> groupItems = new ArrayList<>();
    public static CreativeModeTab RocksGroup;
    public static final ResourceKey<@NotNull CreativeModeTab> ROCKS_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MOD_ID, "rocks"));

    public static void addItem(Item item) {
        //~ if >= 26.1 'ItemStack' -> 'ItemStackTemplate'
        groupItems.add(new ItemStackTemplate(item));
    }

    public static void addItem(Item item, DataComponentPatch patch) {
        //? if >= 26.1 {
        groupItems.add(new ItemStackTemplate(item, patch));
        //?} else {
        /*ItemStack stack = new ItemStack(item);
        stack.applyComponents(patch);
        groupItems.add(stack);
        *///?}
    }

    public static void registerItemGroup() {
        if (polymerMode) PolyUtil.registerPolymerGroup();
        else {
            //~ if >= 26.1 'net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup' -> 'net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab'
            RocksGroup = net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.rocks.rocks"))
                    .icon(RocksCreativeTab::createIcon)
                    .displayItems(RocksCreativeTab::createTabItems)
                    .build();
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ROCKS_GROUP, RocksGroup);
        }
    }

    public static ItemStack createIcon() {
        return new ItemStack(rocksByType.get(RockType.STONE));
    }

    public static void createTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        //? if >= 26.1 {
        output.acceptAll(groupItems.stream().map(ItemStackTemplate::create).toList());
        //?} else {
        /*output.acceptAll(groupItems);
        *///?}
    }
}
