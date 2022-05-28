package pulxes.ab.client.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.item.equipment.ItemBlackHoleBox;
import vazkii.botania.common.item.ItemCraftingHalo;

@EventBusSubscriber(value = {Side.CLIENT}, modid = AdvancedBotany.MODID)
public class HUDHandler {
	
	@SubscribeEvent
	public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
	    Profiler profiler = mc.mcProfiler;
	    ItemStack main = mc.player.getHeldItemMainhand();
	    ItemStack offhand = mc.player.getHeldItemOffhand();
	    if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
	    	boolean hasCraftingHalo = (mc.player.getHeldItemMainhand().getItem() instanceof ItemCraftingHalo) || (mc.player.getHeldItemOffhand().getItem() instanceof ItemCraftingHalo);
	    	if(!hasCraftingHalo) {
	    		if(!main.isEmpty() && main.getItem() instanceof ItemBlackHoleBox) {
		    		profiler.startSection("blackHoleBox_main");
		    		ItemBlackHoleBox.renderHUD(event.getResolution(), mc.player, main);
		    		profiler.endSection();
		    	} else if(!offhand.isEmpty() && offhand.getItem() instanceof ItemBlackHoleBox) {
		    		profiler.startSection("blackHoleBox_off");
		    		ItemBlackHoleBox.renderHUD(event.getResolution(), mc.player, offhand);
		    		profiler.endSection();
		    	} 
	    	}
	    }
	}

}
