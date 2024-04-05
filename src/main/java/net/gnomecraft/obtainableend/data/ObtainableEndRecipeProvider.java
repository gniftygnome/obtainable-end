package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ObtainableEndRecipeProvider extends FabricRecipeProvider {
    public ObtainableEndRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        new ShapedRecipeJsonBuilder(RecipeCategory.DECORATIONS, Items.END_PORTAL_FRAME, 4)
                .pattern("IEI")
                .pattern("ESE")
                .pattern("IEI")
                .input('E', Items.END_STONE)
                .input('I', Items.ENDER_EYE)
                .input('S', Items.NETHER_STAR)
                .criterion("has_end_stone", InventoryChangedCriterion.Conditions.items(Items.END_STONE))
                .offerTo(exporter, new Identifier("end_portal_frame"));
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        // For this mod, we need to generate recipes and advancements in the minecraft namespace.
        return identifier;
    }
}