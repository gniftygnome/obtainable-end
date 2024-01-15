package net.gnomecraft.obtainableend.mixin;

import net.gnomecraft.obtainableend.tags.TagsLoadedEvent;
import net.minecraft.registry.tag.TagManagerLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(TagManagerLoader.class)
public class MixinTagManagerLoader {
    @Shadow
    private List<TagManagerLoader.RegistryTags<?>> registryTags;

    @Inject(method="reload", at=@At("TAIL"), cancellable = true)
    private void obtainableend$reloadAdder(ResourceReloader.Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.setReturnValue(cir.getReturnValue().whenComplete(this::obtainableend$whenComplete));
    }

    private void obtainableend$whenComplete(Object object, Throwable throwable) {
        TagsLoadedEvent.runEvents(registryTags);
    }
}
