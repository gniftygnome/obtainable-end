package net.gnomecraft.obtainableend.net;

import net.gnomecraft.obtainableend.ObtainableEnd;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public record EndFrameCompletionS2CPacket(UUID profile) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<EndFrameCompletionS2CPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ObtainableEnd.MOD_ID, "end_frame_complete"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EndFrameCompletionS2CPacket> CODEC = CustomPacketPayload.codec(EndFrameCompletionS2CPacket::write, EndFrameCompletionS2CPacket::new);

    public EndFrameCompletionS2CPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readUUID());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(profile);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
