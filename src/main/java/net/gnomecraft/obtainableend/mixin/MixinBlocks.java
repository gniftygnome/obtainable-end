package net.gnomecraft.obtainableend.mixin;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Blocks.class)
public class MixinBlocks {
    /*
     * The intent here is to make it possible for players to break end portal frames in the same time as obsidian.
     * The new strength setting is the hardness of obsidian with the resistance of end portal frames.
     */
    @ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/EndPortalFrameBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static AbstractBlock.Settings obtainableend$breakableFrames(AbstractBlock.Settings settings) {
        return FabricBlockSettings.copyOf(settings).strength(50.0f, 3600000.0f).drops(null);
    }
}
