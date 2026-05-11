package net.gnomecraft.obtainableend.net;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class ObtainableEndServerNetworking {
    public static void init() {
        PayloadTypeRegistry.clientboundPlay().register(EndFrameCompletionS2CPacket.ID, EndFrameCompletionS2CPacket.CODEC);
    }

    public static void sendEndFrameCompleteEvent(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (ServerPlayNetworking.canSend(player, EndFrameCompletionS2CPacket.ID)) {
                ServerPlayNetworking.send(player, new EndFrameCompletionS2CPacket(player.getUUID()));
            }
        }
    }
}
