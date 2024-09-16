package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class Recipes extends FabricRecipeProvider {
    public Recipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    @Override
    public void generate(RecipeExporter exporter) {
        generateCrafting(exporter);
    }

        private void generateCrafting(RecipeExporter exporter) {
            RocksMain.splittersByType.forEach(((rockType, splitter) -> {

                ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Registries.BLOCK.get(Identifier.ofVanilla(rockType.name().toLowerCase())))
                        .input(splitter, 4)
                        .criterion(FabricRecipeProvider.hasItem(splitter), FabricRecipeProvider.conditionsFromItem(splitter))
                        .offerTo(exporter, rockType.name().toLowerCase()+"_from_splitter");
            }));
        }
}
