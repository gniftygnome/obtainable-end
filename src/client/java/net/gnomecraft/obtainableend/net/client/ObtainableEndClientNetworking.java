package net.gnomecraft.obtainableend.net.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.gnomecraft.obtainableend.ObtainableEnd;
import net.minecraft.sound.SoundCategory;

public class ObtainableEndClientNetworking {
    public static void register() {
        assert ObtainableEnd.END_FRAME_COMPLETE_PACKET_ID != null;
        ClientPlayNetworking.registerGlobalReceiver(ObtainableEnd.END_FRAME_COMPLETE_PACKET_ID, (client, handler, buf, sender) -> {
            client.execute(() -> {
                if (ObtainableEnd.getConfig().completionSound && client.player != null) {
                    client.player.playSound(ObtainableEnd.END_FRAME_COMPLETE_SOUND_EVENT, SoundCategory.HOSTILE, 1.0f, 1.0f);
                }
            });
        });
    }
}
