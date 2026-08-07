package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
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
    public Recipes(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    @Override
    public String getName() {
        return "Recipes";
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeExporter) {
        return new RocksRecipeGenerator(registries, recipeExporter);
    }

    public static class RocksRecipeGenerator extends RecipeProvider {
        protected RocksRecipeGenerator(HolderLookup.Provider registries, RecipeOutput exporter) {
            super(registries, exporter);
        }

        @Override
        public void buildRecipes() {
            generateCrafting(output);
        }
        private void generateCrafting(RecipeOutput exporter) {
            RocksMain.splittersByType.forEach(((rockType, splitter) -> {
                Identifier stoneID = rockType.getFragment().getStoneId();

                ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BuiltInRegistries.BLOCK.getValue(stoneID).asItem())
                        .requires(splitter, 4)
                        .unlockedBy(getHasName(splitter), has(splitter))
                        .save(exporter, stoneID.getPath()+"_from_splitter");
            }));
        }
    }




}
