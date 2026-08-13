package eu.midnightdust.motschen.rocks.datagen;

//~ if >= 26.1 'FabricDataOutput' -> 'FabricPackOutput' {
import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.util.RockType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//~ if >= 26.1 'net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider' -> 'net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider'
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class LootTables {
    //~ if >= 26.1 'FabricBlockLootTableProvider' -> 'FabricBlockLootSubProvider'
    public static class BlockLootTables extends FabricBlockLootTableProvider {
        public BlockLootTables(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
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
            var lootFunction = CopyBlockState.copyState(block);
            Arrays.stream(properties).forEach(lootFunction::copy);

            add(block, LootTable.lootTable().withPool(this.applyExplosionCondition(block,
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block)
                            .apply(lootFunction)))));
        }
        public void addSilkTouchDrop(Block block, Item alternative) {
            add(block, this.createSilkTouchDispatchTable(block, LootItem.lootTableItem(alternative)));
        }
        public void addSilkTouchOrRareDrop(Block block, Item alternative, float... chances) {
            HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            add(block, this.createSilkTouchDispatchTable(block, LootItem.lootTableItem(alternative).when(BonusLevelTableCondition.bonusLevelFlatChance(impl.getOrThrow(Enchantments.FORTUNE), chances))));
        }
    }
}
//~}