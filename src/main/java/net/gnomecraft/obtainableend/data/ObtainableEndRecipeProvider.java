package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.gnomecraft.obtainableend.ObtainableEnd;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ObtainableEndRecipeProvider extends FabricRecipeProvider {
    public ObtainableEndRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        new ShapedRecipeJsonBuilder(Items.END_PORTAL_FRAME, 4)
                .pattern("IEI")
                .pattern("ESE")
                .pattern("IEI")
                .input('E', Items.END_STONE)
                .input('I', Items.ENDER_EYE)
                .input('S', Items.NETHER_STAR)
                .criterion("has_end_stone", InventoryChangedCriterion.Conditions.items(Items.END_STONE))
                .offerTo(exporter);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return new Identifier(ObtainableEnd.MOD_ID, identifier.getPath());
    }
}