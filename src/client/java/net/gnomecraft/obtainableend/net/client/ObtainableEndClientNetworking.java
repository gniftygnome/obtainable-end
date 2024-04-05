package net.gnomecraft.obtainableend.net.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.gnomecraft.obtainableend.ObtainableEnd;
import net.gnomecraft.obtainableend.net.EndFrameCompletionS2CPacket;
import net.minecraft.client.network.ClientPlayerEntity;

public class ObtainableEndClientNetworking {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(EndFrameCompletionS2CPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPlayerEntity player = context.client().player;
                if (ObtainableEnd.getConfig().completionSound && player != null) {
                    player.playSound(ObtainableEnd.END_FRAME_COMPLETE_SOUND_EVENT, 1.0f, 1.0f);
                }
            });
        });
    }
}
