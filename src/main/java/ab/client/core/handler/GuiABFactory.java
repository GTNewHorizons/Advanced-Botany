package ab.client.core.handler;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiABFactory implements IModGuiFactory {

	public void initialize(Minecraft minecraftInstance) {}
	  
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return GuiABConfigHandler.class;
	}
	  
	public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
	  
	public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement element) {
		return null;
	}
}
