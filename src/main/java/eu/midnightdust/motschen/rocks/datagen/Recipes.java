package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class Recipes extends FabricRecipeProvider {
    public Recipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    @Override
    public String getName() {
        return "Recipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter recipeExporter) {
        return new RocksRecipeGenerator(registries, recipeExporter);
    }

    public static class RocksRecipeGenerator extends RecipeGenerator {
        protected RocksRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
            super(registries, exporter);
        }

        @Override
        public void generate() {
            generateCrafting(exporter);
        }
        private void generateCrafting(RecipeExporter exporter) {
            RocksMain.splittersByType.forEach(((rockType, splitter) -> {

                ShapelessRecipeJsonBuilder.create(registries.getOrThrow(RegistryKeys.ITEM), RecipeCategory.BUILDING_BLOCKS, Registries.BLOCK.get(Identifier.ofVanilla(rockType.name().toLowerCase())).asItem())
                        .input(splitter, 4)
                        .criterion(RecipeGenerator.hasItem(splitter), this.conditionsFromItem(splitter))
                        .offerTo(exporter, rockType.name().toLowerCase()+"_from_splitter");
            }));
        }
    }




}
