package eu.midnightdust.motschen.rocks;

import eu.midnightdust.motschen.rocks.datagen.*;
import eu.midnightdust.motschen.rocks.world.configured_feature.MiscFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.NetherFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.RockFeatures;
import eu.midnightdust.motschen.rocks.world.configured_feature.StickFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

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
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, RockFeatures::initConfigured);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, RockFeatures::initPlaced);
        StickFeatures.initFeatures();
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, StickFeatures::initConfigured);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, StickFeatures::initPlaced);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, NetherFeatures::initConfigured);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, NetherFeatures::initPlaced);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, MiscFeatures::initConfigured);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, MiscFeatures::initPlaced);

    }
    public static class WorldGenData extends FabricDynamicRegistryProvider {
        public WorldGenData(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
            entries.addAll(registries.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
            entries.addAll(registries.getOrThrow(RegistryKeys.PLACED_FEATURE));
        }

        @Override
        public String getName() {
            return RocksMain.MOD_ID;
        }
    }
}
