package ab.common.core.proxy;

import ab.AdvancedBotany;
import ab.common.core.handler.ConfigABHandler;
import ab.common.core.handler.GuiHandler;
import ab.common.core.handler.NetworkHandler;
import ab.common.lib.register.AchievementRegister;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.EntityListAB;
import ab.common.lib.register.FlowerRegister;
import ab.common.lib.register.ItemListAB;
import ab.common.minetweaker.MineTweakerConfig;
import ab.nei.IMCForNEI;
import ab.utils.CraftingManager;
import ab.utils.ModHelperManager;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@SuppressWarnings("unused")
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        BlockListAB.init();
        ItemListAB.init();
        AchievementRegister.init();
        ModHelperManager.preInit();
        EntityListAB.init();
        FlowerRegister.init();
        ConfigABHandler.loadConfig(event.getSuggestedConfigurationFile());
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(AdvancedBotany.instance, new GuiHandler());
        NetworkHandler.registerPackets();
        IMCForNEI.IMCSender();
        ModHelperManager.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ModHelperManager.postInit();
        CraftingManager.setupCrafting();
        ConfigABHandler.loadPostInit();
        if (Loader.isModLoaded("MineTweaker3")) MineTweakerConfig.registerMT();
    }
}
