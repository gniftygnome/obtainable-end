package net.gnomecraft.obtainableend.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.gnomecraft.obtainableend.ObtainableEnd;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.tags.TagKey;
import org.jspecify.annotations.NullMarked;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/*
 * When End Portal Frame blocks are configured not to be immune to destruction by the Wither,
 * this mixin effectively removes End Portal Frame blocks from the Wither Immune block tag.
 */
@NullMarked
@Mixin(WitherBoss.class)
public class MixinWitherBoss {
    @WrapOperation(method = "canDestroy",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    @SuppressWarnings("unused")
    private static boolean obtainableend$frameImmunity(BlockState instance, TagKey<Block> tag, Operation<Boolean> original) {
        if (!ObtainableEnd.getConfig().frameIsWitherImmune && instance.is(Blocks.END_PORTAL_FRAME)) {
            return false;
        }

        return original.call(instance, tag);
    }
}
