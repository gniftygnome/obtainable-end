package net.gnomecraft.obtainableend.tags;

import net.gnomecraft.obtainableend.ObtainableEnd;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.registry.Registry;

public class ObtainableEndTagModifier {
    public static void init() {
        TagsLoadedEvent.register(Registry.BLOCK.getKey(), ObtainableEndTagModifier::updateBlockTags);
    }

    // We have to help the compiler understand the generics.
    private static <T> void updateBlockTags(TagManagerLoader.RegistryTags<T> registryTags) {
        if (!ObtainableEnd.getConfig().frameIsWitherImmune) {
            registryTags.tags().put(BlockTags.WITHER_IMMUNE.id(),
                    new Tag<>(registryTags.tags().get(BlockTags.WITHER_IMMUNE.id()).values().stream()
                            .filter(entry -> entry.value() != Blocks.END_PORTAL_FRAME)
                            .toList()));
        }
    }
}
