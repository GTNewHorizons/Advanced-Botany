package ab.client.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import vazkii.botania.common.Botania;
import ab.client.core.handler.BoundRenderHandler;
import ab.client.core.handler.ClientHandler;
import ab.client.core.handler.HudRenderHandler;
import ab.client.core.handler.PlayerRendererHandler;
import ab.client.render.block.*;
import ab.client.render.entity.*;
import ab.client.render.item.RenderItemAntigravityCharm;
import ab.client.render.item.RenderItemSphereNavigation;
import ab.client.render.tile.*;
import ab.common.block.tile.*;
import ab.common.core.handler.ConfigABHandler;
import ab.common.core.proxy.CommonProxy;
import ab.common.entity.*;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.ItemListAB;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
        ClientRegistry.bindTileEntitySpecialRenderer(TileFountainMana.class, new RenderTileFountainMana());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFountainAlchemy.class, new RenderTileFountainAlchemy());
        ClientRegistry
                .bindTileEntitySpecialRenderer(TileFountainConjuration.class, new RenderTileFountainConjuration());
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
        BlockListAB.blockABFountainRI = RenderingRegistry.getNextAvailableRenderId();
        BlockListAB.blockABAlchemyRI = RenderingRegistry.getNextAvailableRenderId();
        BlockListAB.blockABConjurationRI = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new RenderBlockABSpreader());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaContainer());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaCrystalCube());
        RenderingRegistry.registerBlockHandler(new RenderBlockManaCharger());
        RenderingRegistry.registerBlockHandler(new RenderBlockEngineerHopper());
        RenderingRegistry.registerBlockHandler(new RenderBlockNidavellirForge());
        RenderingRegistry.registerBlockHandler(new RenderBlockFountainMana());
        RenderingRegistry.registerBlockHandler(new RenderBlockFountainAlchemy());
        RenderingRegistry.registerBlockHandler(new RenderBlockFountainConjuration());

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
