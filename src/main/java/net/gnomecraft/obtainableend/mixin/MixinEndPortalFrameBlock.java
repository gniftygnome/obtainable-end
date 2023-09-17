package net.gnomecraft.obtainableend.mixin;

import net.gnomecraft.obtainableend.ObtainableEnd;
import net.gnomecraft.obtainableend.net.ObtainableEndServerNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;

@Mixin(EndPortalFrameBlock.class)
public abstract class MixinEndPortalFrameBlock extends Block {
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

    public MixinEndPortalFrameBlock(Settings settings) {
        super(settings);
    }

    /*
     * Whenever an end portal frame piece is placed, see if it is part of a complete frame.
     * If it is part of a complete frame, reorient all frame pieces to make the frame valid.
     */
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        outer: for (Vec3i centerOffset : PORTAL_POSITIONS.keySet()) {
            BlockPos center = pos.add(centerOffset);

            for (Vec3i frameOffset : PORTAL_POSITIONS.keySet()) {
                BlockPos frame = center.add(frameOffset);

                if (!world.getBlockState(frame).isOf(Blocks.END_PORTAL_FRAME)) {
                    continue outer;
                }
            }

            for (Vec3i frameOffset : PORTAL_POSITIONS.keySet()) {
                BlockPos frame = center.add(frameOffset);

                world.setBlockState(frame, world.getBlockState(frame).with(EndPortalFrameBlock.FACING, PORTAL_POSITIONS.get(frameOffset)), 2);
            }

            MinecraftServer server = world.getServer();
            if (server != null) {
                ObtainableEndServerNetworking.sendGlobalEvent(server, ObtainableEnd.END_FRAME_COMPLETE_PACKET_ID);
            }

            break;
        }
    }

    /*
     * When an end portal frame piece is broken, try to break any associated end portal blocks.
     */
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        Direction primary = state.get(EndPortalFrameBlock.FACING);
        Direction secondary = primary.rotateClockwise(Direction.Axis.Y);

        super.onStateReplaced(state, world, pos, newState, moved);
        newState = world.getBlockState(pos);

        if (    newState != null &&
                newState.isOf(Blocks.END_PORTAL_FRAME) &&
                newState.get(EndPortalFrameBlock.EYE) &&
                primary.equals(newState.get(EndPortalFrameBlock.FACING))) {

            // Replaced with functionally equivalent frame piece.
            return;
        }

        for (int movePrimary = 1; movePrimary <= 3; ++movePrimary) {
            for (int moveSecondary = -2; moveSecondary <= 2; ++moveSecondary) {
                BlockPos target = pos.offset(primary, movePrimary).offset(secondary, moveSecondary);

                if (world.getBlockState(target).isOf(Blocks.END_PORTAL)) {
                    world.breakBlock(target, false);
                }
            }
        }
    }
}
