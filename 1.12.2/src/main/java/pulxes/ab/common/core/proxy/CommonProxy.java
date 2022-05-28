package pulxes.ab.common.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pulxes.ab.client.core.handler.RenderColorHandler;
import pulxes.ab.common.core.handler.AdvBotanyConfigHandler;
import pulxes.ab.common.core.handler.NetworkHandler;
import pulxes.ab.common.lib.register.EntityListAB;
import pulxes.ab.common.lib.register.FlowerListAB;
import pulxes.ab.common.lib.register.ItemListAB;
import pulxes.ab.common.lib.register.RecipeListAB;
import pulxes.ab.common.lib.register.lexicon.LexiconListAB;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		EntityListAB.init();
		FlowerListAB.init();
		AdvBotanyConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
	}
	
	public void init(FMLInitializationEvent event) {
		NetworkHandler.registerPackets();
		RecipeListAB.init();
		LexiconListAB.init();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		AdvBotanyConfigHandler.loadPostInit();
	}
}
