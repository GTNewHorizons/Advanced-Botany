package ab.common.core.proxy;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import ab.common.core.handler.ConfigABHandler;
import ab.common.core.handler.GuiHandler;
import ab.common.lib.register.AchievementRegister;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.EntityListAB;
import ab.common.lib.register.FlowerRegister;
import ab.common.lib.register.ItemListAB;
import ab.common.lib.register.RecipeListAB;
import ab.common.minetweaker.MineTweakerConfig;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import vazkii.botania.common.item.ModItems;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigABHandler.loadConfig(event.getSuggestedConfigurationFile());
		BlockListAB.init();
		ItemListAB.init();
		AchievementRegister.init();
		EntityListAB.init();
		FlowerRegister.init();
	}
 
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(AdvancedBotany.instance, new GuiHandler());
		RecipeListAB.init();
		AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.infiniteFruit));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.kingKey));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.flugelEye));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.thorRing));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.odinRing));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.lokiRing));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemFreyrSlingshot));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemTalismanHiddenRiches));
		AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemPocketWardrobe));
	}
	 
	public void postInit(FMLPostInitializationEvent event) {
		ConfigABHandler.loadPostInit();
		if(Loader.isModLoaded("MineTweaker3")) 		
			MineTweakerConfig.registerMT();
	}
}
