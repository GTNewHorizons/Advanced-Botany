package ab.client.core.handler;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import ab.AdvancedBotany;
import ab.common.core.handler.ConfigABHandler;
import cpw.mods.fml.client.config.GuiConfig;

public class GuiABConfigHandler extends GuiConfig {

    public GuiABConfigHandler(GuiScreen parentScreen) {
        super(
                parentScreen,
                (new ConfigElement(ConfigABHandler.config.getCategory("general"))).getChildElements(),
                AdvancedBotany.modid,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigABHandler.config.toString()));
    }
}
