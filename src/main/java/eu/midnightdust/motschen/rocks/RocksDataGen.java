package eu.midnightdust.motschen.rocks;

import eu.midnightdust.motschen.rocks.datagen.*;
import eu.midnightdust.motschen.rocks.world.configured_feature.MiscFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.NetherFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.RockFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.StickFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import java.util.concurrent.CompletableFuture;

public class RocksDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(WorldGenData::new);
        pack.addProvider(LootTables.BlockLootTables::new);
        pack.addProvider(Tags.Blocks::new);
        pack.addProvider(Recipes::new);
        pack.addProvider(Language.English::new);
        pack.addProvider(Language.German::new);

        pack.addProvider(Models::new);
    }
    @Override
    public String getEffectiveModId() {
        return "rocks";
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        RockFeatures.init();
        registryBuilder.add(Registries.CONFIGURED_FEATURE, RockFeatures::initConfigured);
        registryBuilder.add(Registries.PLACED_FEATURE, RockFeatures::initPlaced);
        StickFeatures.init();
        registryBuilder.add(Registries.CONFIGURED_FEATURE, StickFeatures::initConfigured);
        registryBuilder.add(Registries.PLACED_FEATURE, StickFeatures::initPlaced);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, NetherFeatures::initConfigured);
        registryBuilder.add(Registries.PLACED_FEATURE, NetherFeatures::initPlaced);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, MiscFeatures::initConfigured);
        registryBuilder.add(Registries.PLACED_FEATURE, MiscFeatures::initPlaced);

    }
    public static class WorldGenData extends FabricDynamicRegistryProvider {
        //~ if >= 26.1 'net.fabricmc.fabric.api.datagen.v1.FabricDataOutput' -> 'net.fabricmc.fabric.api.datagen.v1.FabricPackOutput'
        public WorldGenData(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
            entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
        }

        @Override
        public String getName() {
            return RocksMain.MOD_ID;
        }
    }
}
