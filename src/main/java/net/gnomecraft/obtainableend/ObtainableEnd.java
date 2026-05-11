package net.gnomecraft.obtainableend;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.gnomecraft.obtainableend.config.ObtainableEndConfig;
import net.gnomecraft.obtainableend.net.ObtainableEndServerNetworking;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObtainableEnd implements ModInitializer {
	public static final String MOD_ID = "obtainable_end";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier END_FRAME_COMPLETE_SOUND_ID = Identifier.fromNamespaceAndPath(MOD_ID, "end_frame_complete_sound");
	public static final SoundEvent END_FRAME_COMPLETE_SOUND_EVENT = SoundEvent.createVariableRangeEvent(END_FRAME_COMPLETE_SOUND_ID);

	@Override
	public void onInitialize() {
		// Register the mod's config
		AutoConfig.register(ObtainableEndConfig.class, Toml4jConfigSerializer::new);

		ObtainableEndServerNetworking.init();

		LOGGER.info("End Portals shall be Obtainable by the masses!");
	}

	public static ObtainableEndConfig getConfig() {
		return AutoConfig.getConfigHolder(ObtainableEndConfig.class).getConfig();
	}
}