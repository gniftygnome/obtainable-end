package net.gnomecraft.obtainableend.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.gnomecraft.obtainableend.tags.TagsLoadedEvent;
import net.minecraft.registry.tag.TagManagerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(TagManagerLoader.class)
public class MixinTagManagerLoader {
    @Shadow
    private List<TagManagerLoader.RegistryTags<?>> registryTags;

    @ModifyReturnValue(method="reload", at=@At("RETURN"))
    @SuppressWarnings("unused")
    private CompletableFuture<Void> obtainableend$reloadAdder(CompletableFuture<Void> original) {
        return original.whenComplete(this::obtainableend$whenComplete);
    }

    private void obtainableend$whenComplete(Object object, Throwable throwable) {
        TagsLoadedEvent.runEvents(registryTags);
    }
}
