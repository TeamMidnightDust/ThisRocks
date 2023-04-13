package eu.midnightdust.motschen.rocks;

import eu.midnightdust.motschen.rocks.world.FeatureInjector;
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
        System.out.println("out");
    }
    @Override
    public String getEffectiveModId() {
        return "rocks";
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        System.out.println("building registry");
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, RockFeatures::initConfigured);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, RockFeatures::initPlaced);
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
            System.out.println("configure");
            entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
            entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
        }

        @Override
        public String getName() {
            return RocksMain.MOD_ID;
        }
    }
}
