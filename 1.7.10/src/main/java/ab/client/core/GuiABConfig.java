package ab.client.core;

import ab.AdvancedBotany;
import ab.common.core.ConfigABHandler;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class GuiABConfig extends GuiConfig {

	public GuiABConfig(GuiScreen parentScreen) {
		super(parentScreen, (new ConfigElement(ConfigABHandler.config.getCategory("general"))).getChildElements(), AdvancedBotany.modid, false, false, GuiConfig.getAbridgedConfigPath(ConfigABHandler.config.toString()));
	}
}
