package pulxes.ab.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.core.handler.AdvBotanyConfigHandler;

public class GuiAdvBotanyConfig extends GuiConfig {

	public GuiAdvBotanyConfig(GuiScreen gui) {
		super(gui, (new ConfigElement(AdvBotanyConfigHandler.config.getCategory("general"))).getChildElements(), AdvancedBotany.MODID, false, false, GuiConfig.getAbridgedConfigPath(AdvBotanyConfigHandler.config.toString()));
	}
}
