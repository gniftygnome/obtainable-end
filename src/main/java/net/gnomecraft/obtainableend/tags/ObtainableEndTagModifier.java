package net.gnomecraft.obtainableend.tags;

import net.gnomecraft.obtainableend.ObtainableEnd;
import net.minecraft.util.registry.Registry;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagManagerLoader;

public class ObtainableEndTagModifier {
    public static void init() {
        TagsLoadedEvent.register(Registry.BLOCK.getKey(), ObtainableEndTagModifier::updateBlockTags);
    }

    // We have to help the compiler understand the generics.
    private static <T> void updateBlockTags(TagManagerLoader.RegistryTags<T> registryTags) {
        if (!ObtainableEnd.getConfig().frameIsWitherImmune) {
            registryTags.tags().put(BlockTags.WITHER_IMMUNE.id(),
                    registryTags.tags().get(BlockTags.WITHER_IMMUNE.id()));
        }
    }
}
