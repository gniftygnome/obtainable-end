package net.gnomecraft.obtainableend.tags;

import net.gnomecraft.obtainableend.ObtainableEnd;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagManagerLoader;

public class ObtainableEndTagModifier {
    public static void init() {
        TagsLoadedEvent.register(RegistryKeys.BLOCK, ObtainableEndTagModifier::updateBlockTags);
    }

    // We have to help the compiler understand the generics.
    private static <T> void updateBlockTags(TagManagerLoader.RegistryTags<T> registryTags) {
        if (!ObtainableEnd.getConfig().frameIsWitherImmune) {
            registryTags.tags().put(BlockTags.WITHER_IMMUNE.id(),
                    registryTags.tags().get(BlockTags.WITHER_IMMUNE.id()).stream()
                            .filter(entry -> entry.value() != Blocks.END_PORTAL_FRAME)
                            .toList());
        }
    }
}
