package net.gnomecraft.obtainableend.net;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ObtainableEndServerNetworking {
    public static void sendGlobalEvent(MinecraftServer server, Identifier eventId) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            PacketByteBuf buf = PacketByteBufs.create();
            if (ServerPlayNetworking.canSend(player, eventId)) {
                ServerPlayNetworking.send(player, eventId, buf);
            }
        }
    }
}
