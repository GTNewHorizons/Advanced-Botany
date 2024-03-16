package ab.client.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import ab.client.core.handler.BoundRenderHandler;
import ab.client.core.handler.ClientHandler;
import ab.client.core.handler.HudRenderHandler;
import ab.client.core.handler.PlayerRendererHandler;
import ab.client.render.block.RenderBlockABSpreader;
import ab.client.render.block.RenderBlockEngineerHopper;
import ab.client.render.block.RenderBlockManaCharger;
import ab.client.render.block.RenderBlockManaContainer;
import ab.client.render.block.RenderBlockManaCrystalCube;
import ab.client.render.block.RenderBlockNidavellirForge;
import ab.client.render.entity.EntityNullRender;
import ab.client.render.entity.RenderEntityAdvancedSpark;
import ab.client.render.entity.RenderEntityAlphirinePortal;
import ab.client.render.item.RenderItemAntigravityCharm;
import ab.client.render.item.RenderItemSphereNavigation;
import ab.client.render.tile.RenderTileABSpreader;
import ab.client.render.tile.RenderTileBoardFate;
import ab.client.render.tile.RenderTileEngineerHopper;
import ab.client.render.tile.RenderTileGameBoard;
import ab.client.render.tile.RenderTileMagicCraftingCrate;
import ab.client.render.tile.RenderTileManaCharger;
import ab.client.render.tile.RenderTileManaContainer;
import ab.client.render.tile.RenderTileManaCrystalCube;
import ab.client.render.tile.RenderTileNidavellirForge;
import ab.common.block.tile.TileABSpreader;
import ab.common.block.tile.TileBoardFate;
import ab.common.block.tile.TileEngineerHopper;
import ab.common.block.tile.TileGameBoard;
import ab.common.block.tile.TileMagicCraftCrate;
import ab.common.block.tile.TileManaCharger;
import ab.common.block.tile.TileManaContainer;
import ab.common.block.tile.TileManaCrystalCube;
import ab.common.block.tile.TileNidavellirForge;
import ab.common.core.handler.ConfigABHandler;
import ab.common.core.proxy.CommonProxy;
import ab.common.entity.EntityAdvancedSpark;
import ab.common.entity.EntityAlphirinePortal;
import ab.common.entity.EntityManaVine;
import ab.common.entity.EntityNebulaBlaze;
import ab.common.entity.EntitySeed;
import ab.common.entity.EntitySword;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.ItemListAB;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import vazkii.botania.common.Botania;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);

        FMLCommonHandler.instance().bus().register(new ClientHandler());
        MinecraftForge.EVENT_BUS.register(new HudRenderHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerRendererHandler());
        MinecraftForge.EVENT_BUS.register(new BoundRenderHandler());

        ClientRegistry.bindTileEntitySpecialRenderer(TileNidavellirForge.class, new RenderTileNidavellirForge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileABSpreader.class, new RenderTileABSpreader());
        ClientRegistry.bindTileEntitySpecialRenderer(TileManaContainer.class, new RenderTileManaContainer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileManaCrystalCube.class, new RenderTileManaCrystalCube());
        if (ConfigABHandler.hasManaCharger)
            ClientRegistry.bindTileEntitySpecialRenderer(TileManaCharger.class, new RenderTileManaCharger());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEngineerHopper.class, new RenderTileEngineerHopper());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBoardFate.class, new RenderTileBoardFate());
        ClientRegistry.bindTileEntitySpecialRenderer(TileGameBoard.class, new RenderTileGameBoard());
        if (Botania.thaumcraftLoaded && ConfigABHandler.hasAutoThaum)
            ClientRegistry.bindTileEntitySpecialRenderer(TileMagicCraftCrate.class, new RenderTileMagicCraftingCrate());

        BlockListAB.blockABSpreaderRI = RenderingRegistry.getNextAvailableRenderId();
        BlockListAB.blockManaContainerRI = RenderingRegistry.getNextAvailableRenderId();
        BlockListAB.blockManaCrystalCubeRI = RenderingRegistry.getNextAvailableRenderId();
        BlockListAB.blockManaChargerRI = RenderingRegistry.getNextAvailableRenderId();
        BlockListAB.blockEngineerHopperRI = RenderingRegistry.getNextAvailableRenderId();
        BlockListAB.blockABPlateRI = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new RenderBlockABSpreader());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaContainer());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaCrystalCube());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaCharger());
        RenderingRegistry.registerBlockHandler(new RenderBlockEngineerHopper());
        RenderingRegistry.registerBlockHandler(new RenderBlockNidavellirForge());

        RenderingRegistry.registerEntityRenderingHandler(EntityAdvancedSpark.class, new RenderEntityAdvancedSpark());
        RenderingRegistry.registerEntityRenderingHandler(EntityNebulaBlaze.class, new EntityNullRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityManaVine.class, new EntityNullRender());
        RenderingRegistry
                .registerEntityRenderingHandler(EntityAlphirinePortal.class, new RenderEntityAlphirinePortal());
        RenderingRegistry.registerEntityRenderingHandler(EntitySword.class, new EntityNullRender());
        RenderingRegistry.registerEntityRenderingHandler(EntitySeed.class, new EntityNullRender());

        MinecraftForgeClient.registerItemRenderer(ItemListAB.itemAntigravityCharm, new RenderItemAntigravityCharm());
        MinecraftForgeClient.registerItemRenderer(ItemListAB.itemSphereNavigation, new RenderItemSphereNavigation());
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        // ItemStack crashGameForRender = null;
        // System.out.println(crashGameForRender.stackSize);
    }
}
