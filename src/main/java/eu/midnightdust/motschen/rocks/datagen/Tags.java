package eu.midnightdust.motschen.rocks.datagen;

//~ if >= 26.1 'TagProvider' -> 'TagsProvider' {
//~ if >= 26.1 'FabricDataOutput' -> 'FabricPackOutput' {
import eu.midnightdust.motschen.rocks.RocksMain;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class Tags {
    public static class Blocks extends FabricTagsProvider.BlockTagsProvider {
        private static final TagKey<Block> PICKAXE_MINEABLE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/pickaxe"));

        public Blocks(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider registry) {
            this.builder(PICKAXE_MINEABLE).setReplace(false)
                    .add(ResourceKey.create(Registries.BLOCK, RocksMain.GEYSER))
                    .add(ResourceKey.create(Registries.BLOCK, RocksMain.NETHER_GEYSER));
        }
    }
}
//~}
//~}
