package net.gnomecraft.obtainableend.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class ObtainableEndBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    protected ObtainableEndBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        // basic block tags
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(Blocks.END_PORTAL_FRAME);
    }

    @Override
    public String getName() {
        return "Obtainable End Block Tags";
    }
}
