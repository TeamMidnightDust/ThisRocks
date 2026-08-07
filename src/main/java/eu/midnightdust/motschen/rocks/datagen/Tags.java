package eu.midnightdust.motschen.rocks.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class Tags {
    public static class Blocks extends FabricTagProvider.BlockTagProvider {
//        private static final TagKey<Block> PICKAXE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("mineable/pickaxe"));
//        private static final TagKey<Block> AXE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("mineable/axe"));
//        private static final TagKey<Block> NEEDS_STONE_TOOL = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("needs_stone_tool"));

        public Blocks(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
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
