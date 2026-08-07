package eu.midnightdust.motschen.rocks.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class Tags {
    public static class Blocks extends FabricTagsProvider.BlockTagsProvider {
//        private static final TagKey<Block> PICKAXE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("mineable/pickaxe"));
//        private static final TagKey<Block> AXE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("mineable/axe"));
//        private static final TagKey<Block> NEEDS_STONE_TOOL = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("needs_stone_tool"));

        public Blocks(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
//            List<Block> pickaxeBlocks = new ArrayList<>(DecorativeMain.BLOCKS);
//            pickaxeBlocks.removeAll(LogsWithAxes.TYPES);
//            getOrCreateTagBuilder(PICKAXE_MINEABLE).setReplace(false)
//                    .add(pickaxeBlocks.toArray(new Block[0]));
//            getOrCreateTagBuilder(AXE_MINEABLE).setReplace(false)
//                    .add(LogsWithAxes.TYPES.toArray(new Block[0]));
//            getOrCreateTagBuilder(NEEDS_STONE_TOOL).setReplace(false)
//                    .add(DecorativeMain.RockyAsphalt);
        }
    }
}
