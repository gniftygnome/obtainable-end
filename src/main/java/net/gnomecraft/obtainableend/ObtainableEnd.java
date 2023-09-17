package net.gnomecraft.obtainableend;

import net.fabricmc.api.ModInitializer;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObtainableEnd implements ModInitializer {
	public static final String MOD_ID = "obtainable-end";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier END_FRAME_COMPLETE_PACKET_ID = Identifier.of(MOD_ID, "end_frame_complete");
	public static final Identifier END_FRAME_COMPLETE_SOUND_ID = Identifier.of(MOD_ID, "end_frame_complete_sound");
	public static final SoundEvent END_FRAME_COMPLETE_SOUND_EVENT = SoundEvent.of(END_FRAME_COMPLETE_SOUND_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("End Portals shall be Obtainable by the masses!");
	}
}