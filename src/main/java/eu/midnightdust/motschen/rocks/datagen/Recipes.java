package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

public class Recipes extends FabricRecipeProvider {
    public Recipes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    @Override
    public String getName() {
        return "Recipes";
    }

    @Override
    protected RecipeProvider getRecipeGenerator(HolderLookup.Provider registries, RecipeOutput recipeExporter) {
        return new RocksRecipeGenerator(registries, recipeExporter);
    }

    public static class RocksRecipeGenerator extends RecipeProvider {
        protected RocksRecipeGenerator(HolderLookup.Provider registries, RecipeOutput exporter) {
            super(registries, exporter);
        }

        @Override
        public void generate() {
            generateCrafting(exporter);
        }
        private void generateCrafting(RecipeOutput exporter) {
            RocksMain.splittersByType.forEach(((rockType, splitter) -> {
                Identifier stoneID = rockType.getFragment().getStoneId();

                ShapelessRecipeBuilder.create(registries.getOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BuiltInRegistries.BLOCK.get(stoneID).asItem())
                        .input(splitter, 4)
                        .criterion(RecipeProvider.hasItem(splitter), this.conditionsFromItem(splitter))
                        .offerTo(exporter, stoneID.getPath()+"_from_splitter");
            }));
        }
    }




}
