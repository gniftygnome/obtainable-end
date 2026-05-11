package net.gnomecraft.obtainableend.mixin;

import net.gnomecraft.obtainableend.net.ObtainableEndServerNetworking;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;
import java.util.Optional;

@NullMarked
@Mixin(EndPortalFrameBlock.class)
public abstract class MixinEndPortalFrameBlock extends Block {
    @Unique
    private static final Map<Vec3i, Direction> PORTAL_POSITIONS = Map.ofEntries(
            Map.entry(new Vec3i(-1, 0, -2), Direction.SOUTH),
            Map.entry(new Vec3i( 0, 0, -2), Direction.SOUTH),
            Map.entry(new Vec3i( 1, 0, -2), Direction.SOUTH),
            Map.entry(new Vec3i(-2, 0, -1), Direction.EAST),
            Map.entry(new Vec3i(-2, 0,  0), Direction.EAST),
            Map.entry(new Vec3i(-2, 0,  1), Direction.EAST),
            Map.entry(new Vec3i( 2, 0, -1), Direction.WEST),
            Map.entry(new Vec3i( 2, 0,  0), Direction.WEST),
            Map.entry(new Vec3i( 2, 0,  1), Direction.WEST),
            Map.entry(new Vec3i(-1, 0,  2), Direction.NORTH),
            Map.entry(new Vec3i( 0, 0,  2), Direction.NORTH),
            Map.entry(new Vec3i( 1, 0,  2), Direction.NORTH)
    );

    public MixinEndPortalFrameBlock(Properties settings) {
        super(settings);
    }

    @ModifyArg(method= "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V"))
    private static BlockBehaviour.Properties obtainableend$breakableFrames(BlockBehaviour.Properties settings) {
        // Allow us to datagen and set the block loot by undoing settings.dropsNothing().
        settings.overrideLootTable(Optional.of(ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("blocks/end_portal_frame"))));

        // Allows players to break end portal frame blocks in the same time as obsidian, by adjusting
        // the hardness to that of obsidian but leaving the resistance like end portal frame block.
        return settings.destroyTime(50.0f);
    }

    /*
     * Whenever an end portal frame piece is placed, see if it is part of a complete frame.
     * If it is part of a complete frame, reorient all frame pieces to make the frame valid.
     */
    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        outer: for (Vec3i centerOffset : PORTAL_POSITIONS.keySet()) {
            BlockPos center = pos.offset(centerOffset);

            for (Vec3i frameOffset : PORTAL_POSITIONS.keySet()) {
                BlockPos frame = center.offset(frameOffset);

                if (!world.getBlockState(frame).is(Blocks.END_PORTAL_FRAME)) {
                    continue outer;
                }
            }

            for (Vec3i frameOffset : PORTAL_POSITIONS.keySet()) {
                BlockPos frame = center.offset(frameOffset);

                world.setBlock(frame, world.getBlockState(frame).setValue(EndPortalFrameBlock.FACING, PORTAL_POSITIONS.get(frameOffset)), 2);
            }

            MinecraftServer server = world.getServer();
            if (server != null) {
                ObtainableEndServerNetworking.sendEndFrameCompleteEvent(server);
            }

            break;
        }
    }

    /*
     * When an end portal frame piece is broken, try to break any associated end portal blocks.
     */
    @Override
    public void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        Direction primary = state.getValue(EndPortalFrameBlock.FACING);
        Direction secondary = primary.getClockWise(Direction.Axis.Y);

        super.affectNeighborsAfterRemoval(state, world, pos, moved);
        BlockState newState = world.getBlockState(pos);

        if (    newState.is(Blocks.END_PORTAL_FRAME) &&
                newState.getValue(EndPortalFrameBlock.HAS_EYE) &&
                primary.equals(newState.getValue(EndPortalFrameBlock.FACING))) {

            // Replaced with functionally equivalent frame piece.
            return;
        }

        BlockPos target;
        for (int movePrimary = 1; movePrimary <= 3; ++movePrimary) {
            for (int moveSecondary = -2; moveSecondary <= 2; ++moveSecondary) {
                target = pos.relative(primary, movePrimary).relative(secondary, moveSecondary);

                if (world.getBlockState(target).is(Blocks.END_PORTAL)) {
                    world.destroyBlock(target, false);
                }
            }
        }
    }
}
