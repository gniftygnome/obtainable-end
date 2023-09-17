package net.gnomecraft.obtainableend;

import net.fabricmc.api.ClientModInitializer;
import net.gnomecraft.obtainableend.net.client.ObtainableEndClientNetworking;

public class ObtainableEndClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ObtainableEndClientNetworking.register();
	}
}