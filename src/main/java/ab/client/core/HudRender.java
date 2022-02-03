package ab.client.core;

import ab.api.IRenderHud;
import ab.common.block.tile.TileLebethronCore;
import ab.common.item.ItemBlackHalo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import vazkii.botania.common.item.ItemTwigWand;

public class HudRender {
	
	@SubscribeEvent
	public void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		Profiler profiler = mc.mcProfiler;
		ItemStack equippedStack = mc.thePlayer.getCurrentEquippedItem();
		if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
			profiler.startSection("advabcedBotany-hud");
			MovingObjectPosition pos = mc.objectMouseOver;
			if (pos != null) {
				Block block = mc.theWorld.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		        TileEntity tile = mc.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
		        boolean isWand = equippedStack != null && equippedStack.getItem() instanceof ItemTwigWand;
		        if (tile != null && tile instanceof IRenderHud && !isWand) {
		        	((IRenderHud)tile).renderHud(mc, event.resolution);
		        }
			}
			if (equippedStack != null && equippedStack.getItem() instanceof ItemBlackHalo) {
				profiler.startSection("blackHalo");
				ItemBlackHalo.renderHUD(event.resolution, (EntityPlayer)mc.thePlayer, equippedStack);
				profiler.endSection();
			} 
		}
	}
}
