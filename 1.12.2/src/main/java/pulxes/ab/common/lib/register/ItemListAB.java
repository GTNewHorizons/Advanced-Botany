package pulxes.ab.common.lib.register;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.item.*;
import pulxes.ab.common.item.equipment.*;

@EventBusSubscriber(modid = AdvancedBotany.MODID)
public class ItemListAB {
	
	public static Item itemResource = new ItemResource();
	public static Item itemNebulaRod = new ItemNebulaRod();
	public static Item itemSpaceBlade = new ItemSpaceBlade();

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		r.register(itemResource);
		r.register(itemNebulaRod);
		r.register(itemSpaceBlade);
	}
}
