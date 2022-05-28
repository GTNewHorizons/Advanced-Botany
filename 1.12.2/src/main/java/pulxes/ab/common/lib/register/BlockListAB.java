package pulxes.ab.common.lib.register;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.block.BlockNidavellirForge;
import pulxes.ab.common.block.tile.TileNidavellirForge;
import pulxes.ab.common.item.block.ItemBlockMod;
import pulxes.ab.common.lib.LibBlockNames;

@EventBusSubscriber(modid = AdvancedBotany.MODID)
public class BlockListAB {
	
	public static final Block nidavellirForge = new BlockNidavellirForge();

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();
	    r.register(nidavellirForge);
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		r.register((new ItemBlockMod(nidavellirForge)).setRegistryName(nidavellirForge.getRegistryName()));
		initTileEntities();
	}
	
	private static void initTileEntities() {
		registerTile(TileNidavellirForge.class, LibBlockNames.NIDAVELLIR_FORGE);
	}
	
	private static void registerTile(Class<? extends TileEntity> clazz, String key) {
	    GameRegistry.registerTileEntity(clazz, AdvancedBotany.MODID + ":" + key);
	}
}