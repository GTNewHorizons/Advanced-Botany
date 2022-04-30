package pulxes.ab.client.core.proxy;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import pulxes.ab.client.gui.GuiAdvBotanyConfig;

public class GuiFactory implements IModGuiFactory {
	
	public void initialize(Minecraft minecraftInstance) {}
	  
	public boolean hasConfigGui() {
		return true;
	}
	  
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new GuiAdvBotanyConfig(parentScreen);
	}
	  
	public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
}
