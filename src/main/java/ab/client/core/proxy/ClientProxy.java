package ab.client.core.proxy;

import ab.client.core.ClientHandler;
import ab.client.core.HudRender;
import ab.client.render.block.RenderBlockABSpreader;
import ab.client.render.block.RenderBlockManaCharger;
import ab.client.render.block.RenderBlockManaContainer;
import ab.client.render.block.RenderBlockManaCrystalCube;
import ab.client.render.entity.EntityNullRender;
import ab.client.render.entity.RenderEntityAdvancedSpark;
import ab.client.render.entity.RenderManaVine;
import ab.client.render.item.RenderAntigravityCharm;
import ab.client.render.tile.RenderModifiedFunnel;
import ab.client.render.tile.RenderModifiedInterrupter;
import ab.client.render.tile.RenderModifiedRetainer;
import ab.client.render.tile.RenderTileABSpreader;
import ab.client.render.tile.RenderTileAgglomerationPlate;
import ab.client.render.tile.RenderTileCorporeaCatalyst;
import ab.client.render.tile.RenderTileManaCharger;
import ab.client.render.tile.RenderTileManaContainer;
import ab.client.render.tile.RenderTileManaCrystalCube;
import ab.client.render.tile.RenderTileModifiedInterceptor;
import ab.common.block.tile.TileABSpreader;
import ab.common.block.tile.TileAgglomerationPlate;
import ab.common.block.tile.TileCorporeaCatalyst;
import ab.common.block.tile.TileCorporeaInterrupter;
import ab.common.block.tile.TileManaCharger;
import ab.common.block.tile.TileManaContainer;
import ab.common.block.tile.TileManaCrystalCube;
import ab.common.block.tile.TileModifiedCorporeaFunnel;
import ab.common.block.tile.TileModifiedInterceptor;
import ab.common.block.tile.TileModifiedRetainer;
import ab.common.core.proxy.CommonProxy;
import ab.common.entity.EntityAdvancedSpark;
import ab.common.entity.EntityManaVine;
import ab.common.entity.EntityNebulaBlaze;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.ItemListAB;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) { 
    	super.init(event);
    	
    	FMLCommonHandler.instance().bus().register(new ClientHandler());
    	MinecraftForge.EVENT_BUS.register(new HudRender());
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileAgglomerationPlate.class, new RenderTileAgglomerationPlate());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileABSpreader.class, new RenderTileABSpreader());    
    	ClientRegistry.bindTileEntitySpecialRenderer(TileManaContainer.class, new RenderTileManaContainer());    
    	ClientRegistry.bindTileEntitySpecialRenderer(TileManaCrystalCube.class, new RenderTileManaCrystalCube());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileManaCharger.class, new RenderTileManaCharger()); 
    	
//    	ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaCatalyst.class, new RenderTileCorporeaCatalyst());   
//    	ClientRegistry.bindTileEntitySpecialRenderer(TileModifiedCorporeaFunnel.class, new RenderModifiedFunnel());   
//    	ClientRegistry.bindTileEntitySpecialRenderer(TileCorporeaInterrupter.class, new RenderModifiedInterrupter());  
//    	ClientRegistry.bindTileEntitySpecialRenderer(TileModifiedInterceptor.class, new RenderTileModifiedInterceptor()); 
//    	ClientRegistry.bindTileEntitySpecialRenderer(TileModifiedRetainer.class, new RenderModifiedRetainer()); 
    	
    	BlockListAB.blockABSpreaderRI = RenderingRegistry.getNextAvailableRenderId();
    	BlockListAB.blockManaContainerRI = RenderingRegistry.getNextAvailableRenderId();
    	BlockListAB.blockManaCrystalCubeRI = RenderingRegistry.getNextAvailableRenderId();
    	BlockListAB.blockManaChargerRI = RenderingRegistry.getNextAvailableRenderId();
    	
//    	BlockListAB.blockCorporeaCatalystRI = RenderingRegistry.getNextAvailableRenderId();
//    	BlockListAB.blockModifiedCorporeaFunnelRI = RenderingRegistry.getNextAvailableRenderId();
//    	BlockListAB.blockModifiedCorporeaInterrupterRI = RenderingRegistry.getNextAvailableRenderId();
//    	BlockListAB.blockModifiedCorporeaInterceptorRI = RenderingRegistry.getNextAvailableRenderId();
    	
        RenderingRegistry.registerBlockHandler(new RenderBlockABSpreader());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaContainer());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaCrystalCube());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaCharger());
        
        RenderingRegistry.registerEntityRenderingHandler(EntityAdvancedSpark.class, new RenderEntityAdvancedSpark());
        RenderingRegistry.registerEntityRenderingHandler(EntityNebulaBlaze.class, new EntityNullRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityManaVine.class, (Render)new RenderManaVine());
        
        MinecraftForgeClient.registerItemRenderer(ItemListAB.itemAntigravityCharm, new RenderAntigravityCharm());
    
//        ItemStack error = null;
//        System.out.println(error.stackSize);
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}