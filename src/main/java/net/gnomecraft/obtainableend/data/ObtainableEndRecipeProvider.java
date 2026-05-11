package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class ObtainableEndRecipeProvider extends FabricRecipeProvider {
    public ObtainableEndRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.DECORATIONS, Items.END_PORTAL_FRAME, 4)
                        .pattern("IEI")
                        .pattern("ESE")
                        .pattern("IEI")
                        .define('E', Items.END_STONE)
                        .define('I', Items.ENDER_EYE)
                        .define('S', Items.NETHER_STAR)
                        .unlockedBy("has_end_stone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.END_STONE))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "Obtainable End Recipes";
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        // For this mod, we need to generate recipes and advancements in the minecraft namespace.
        return identifier;
    }
}