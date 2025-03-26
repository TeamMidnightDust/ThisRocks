package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.util.RockType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyStateLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Property;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class LootTables {
    public static class BlockLootTables extends FabricBlockLootTableProvider {
        public BlockLootTables(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            RocksMain.rocksByType.forEach((rockType, rock) -> addSilkTouchDrop(rock, rockType != RockType.GRAVEL ? RocksMain.splittersByType.get(rockType) : Items.FLINT));
            RocksMain.sticksByType.forEach((stickType, stick) -> addSilkTouchDrop(stick, Items.STICK));
            addSilkTouchDrop(RocksMain.Pinecone, Items.SPRUCE_SAPLING);
            addSilkTouchOrRareDrop(RocksMain.Seashell, Items.NAUTILUS_SHELL, 0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f);
            addCopyStateDrop(RocksMain.Starfish, RocksMain.STARFISH_VARIATION);
        }

        public void addCopyStateDrop(Block block, Property<?>... properties) {
            var lootFunction = CopyStateLootFunction.builder(block);
            Arrays.stream(properties).forEach(lootFunction::addProperty);

            addDrop(block, LootTable.builder().pool(this.addSurvivesExplosionCondition(block,
                    LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(block)
                            .apply(lootFunction)))));
        }
        public void addSilkTouchDrop(Block block, Item alternative) {
            addDrop(block, this.dropsWithSilkTouch(block, ItemEntry.builder(alternative)));
        }
        public void addSilkTouchOrRareDrop(Block block, Item alternative, float... chances) {
            RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
            addDrop(block, this.dropsWithSilkTouch(block, ItemEntry.builder(alternative).conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), chances))));
        }
    }
}

