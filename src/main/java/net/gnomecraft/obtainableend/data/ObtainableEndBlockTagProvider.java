package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class ObtainableEndBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    protected ObtainableEndBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup registries) {
        // basic block tags
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).add(Blocks.END_PORTAL_FRAME);
    }
}
