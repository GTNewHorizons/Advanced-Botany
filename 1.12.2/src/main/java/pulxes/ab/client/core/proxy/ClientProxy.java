package pulxes.ab.client.core.proxy;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pulxes.ab.client.core.handler.ClientTickHandler;
import pulxes.ab.client.core.handler.RenderColorHandler;
import pulxes.ab.common.core.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		RenderColorHandler.init();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
