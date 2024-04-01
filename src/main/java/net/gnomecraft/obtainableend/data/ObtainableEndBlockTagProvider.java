package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;

public class ObtainableEndBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    protected ObtainableEndBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void generateTags() {
        // basic block tags
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(Blocks.END_PORTAL_FRAME);
    }
}
