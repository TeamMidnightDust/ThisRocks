package eu.midnightdust.motschen.rocks.datagen;

//~ if >= 26.1 'FabricDataOutput' -> 'FabricPackOutput' {
import eu.midnightdust.motschen.rocks.RocksMain;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class Recipes extends FabricRecipeProvider {
    @Override
    public String getName() {
        return "Recipes";
    }

    public Recipes(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    //? if <=26.2 {
    /*@Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeExporter) {
        return new RocksRecipeGenerator(registries, recipeExporter);
    }

    public static class RocksRecipeGenerator extends RecipeProvider {
        protected RocksRecipeGenerator(HolderLookup.Provider registries, RecipeOutput exporter) {
            super(registries, exporter);
        }
    *///? } else {
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RocksRecipeGenerator(registries, recipes, advancements);
    }
    public static class RocksRecipeGenerator extends RecipeProvider {
        private final HolderLookup.Provider registries;

        protected RocksRecipeGenerator(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
            super(recipeOutput, advancementOutput);
            this.registries = registries;
        }
    //? }

        @Override
        public void buildRecipes() {
            generateCrafting(output);
        }
        private void generateCrafting(RecipeOutput exporter) {
            RocksMain.splittersByType.forEach(((rockType, splitter) -> {
                Identifier stoneID = rockType.getFragment().getStoneId();

                ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, BuiltInRegistries.BLOCK.getValue(stoneID).asItem())
                        .requires(splitter, 4)
                        .unlockedBy(RecipeProvider.getHasName(splitter), this.has(splitter))
                        .save(exporter, stoneID.getPath()+"_from_splitter");
            }));
        }
    }
}
//~}