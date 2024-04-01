package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ObtainableEndDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(ObtainableEndBlockLootTableProvider::new);
        dataGenerator.addProvider(ObtainableEndBlockTagProvider::new);
        dataGenerator.addProvider(ObtainableEndRecipeProvider::new);
    }
}
