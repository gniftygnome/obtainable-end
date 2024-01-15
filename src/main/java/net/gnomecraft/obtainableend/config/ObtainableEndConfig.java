package net.gnomecraft.obtainableend.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.gnomecraft.obtainableend.ObtainableEnd;

@SuppressWarnings("unused")
@Config(name = ObtainableEnd.MOD_ID)
public class ObtainableEndConfig implements ConfigData {
    //@ConfigEntry.Category("blocks")
    //@ConfigEntry.Gui.PrefixText
    //@ConfigEntry.Gui.Tooltip
    public Boolean completionSound = true;

    public Boolean frameIsWitherImmune = false;
}
