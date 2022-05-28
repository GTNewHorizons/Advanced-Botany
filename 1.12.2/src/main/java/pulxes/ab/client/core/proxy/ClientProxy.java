package pulxes.ab.client.core.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pulxes.ab.client.core.handler.ClientTickHandler;
import pulxes.ab.client.core.handler.IconMiscHandler;
import pulxes.ab.client.core.handler.RenderColorHandler;
import pulxes.ab.client.render.tile.RenderTileNidavellirForge;
import pulxes.ab.common.block.tile.TileNidavellirForge;
import pulxes.ab.common.core.proxy.CommonProxy;
import pulxes.ab.common.entity.EntityAlphirinePortal;
import pulxes.ab.common.entity.EntityElvenSpark;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(IconMiscHandler.INSTANCE);
	
		ClientRegistry.bindTileEntitySpecialRenderer(TileNidavellirForge.class, new RenderTileNidavellirForge());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityAlphirinePortal.class, pulxes.ab.client.render.entity.RenderEntityAlphirinePortal::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityElvenSpark.class, pulxes.ab.client.render.entity.RenderEntityElvenSpark::new);
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