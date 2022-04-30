package pulxes.ab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import pulxes.ab.common.core.AdvancedBotanyTab;
import pulxes.ab.common.core.proxy.CommonProxy;

@Mod(modid = AdvancedBotany.MODID, name="Advanced Botany", version = AdvancedBotany.VERSION, dependencies = "required-after:botania", guiFactory = "pulxes.ab.client.core.proxy.GuiFactory", acceptedMinecraftVersions = "[1.12.2]")
public class AdvancedBotany {

	public static final String MODID = "advancedbotany";
	public static final String VERSION = "2.0.0";
	
	public static final CreativeTabs tabAB = new AdvancedBotanyTab();
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Mod.Instance(MODID)
    public static AdvancedBotany instance;
	
	@SidedProxy(serverSide = "pulxes.ab.common.core.proxy.CommonProxy", clientSide = "pulxes.ab.client.core.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {	
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}