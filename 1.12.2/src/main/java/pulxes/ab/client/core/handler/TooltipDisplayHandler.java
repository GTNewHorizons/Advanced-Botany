package pulxes.ab.client.core.handler;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.api.IRankItem;

@EventBusSubscriber(value = {Side.CLIENT}, modid = AdvancedBotany.MODID)
public class TooltipDisplayHandler {

	@SubscribeEvent
	public static void onToolTipRender(RenderTooltipEvent.PostText evt) {
		if(evt.getStack().isEmpty())
			return; 
		ItemStack stack = evt.getStack();
		Minecraft mc = Minecraft.getMinecraft();
		int width = evt.getWidth();
		int height = 3;
		int tooltipX = evt.getX();
		int tooltipY = evt.getY() - 4;
		FontRenderer font = evt.getFontRenderer();
		if(stack.getItem() instanceof IRankItem)
			drawRankDisplay(stack, tooltipX, tooltipY, width, height, font);
	}
	
	private static void drawRankDisplay(ItemStack stack, int mouseX, int mouseY, int width, int height, FontRenderer font) {
		IRankItem item = (IRankItem)stack.getItem();
		int level = item.getLevel(stack);
		int max = item.getLevels()[Math.min(item.getLevels().length - 1, level + 1)];
		boolean ss = (level >= item.getLevels().length - 1);
		int curr = item.getMana(stack);
	    float percent = (level == 0) ? 0.0F : (curr / max);
	    int rainbowWidth = Math.min(width - (ss ? 0 : 1), (int)(width * percent));
	    float huePer = (width == 0) ? 0.0F : (1.0F / width);
	    float hueOff = (vazkii.botania.client.core.handler.ClientTickHandler.ticksInGame + vazkii.botania.client.core.handler.ClientTickHandler.partialTicks) * 0.01F;
	    GlStateManager.disableDepth();
	    Gui.drawRect(mouseX - 1, mouseY - height - 1, mouseX + width + 1, mouseY, -16777216);
	    for(int i = 0; i < rainbowWidth; i++)
	    	Gui.drawRect(mouseX + i, mouseY - height, mouseX + i + 1, mouseY, Color.HSBtoRGB(hueOff + huePer * i, 1.0F, 1.0F)); 
	    Gui.drawRect(mouseX + rainbowWidth, mouseY - height, mouseX + width, mouseY, -11184811);
	    String rank = I18n.format("botania.rank" + level, new Object[0]).replaceAll("&", "\u00A7");
	    GL11.glPushAttrib(64);
	    GlStateManager.disableLighting();
	    font.drawStringWithShadow(rank, mouseX, (mouseY - 12), 16777215);
	    if(!ss) {
	    	rank = I18n.format("botania.rank" + (level + 1), new Object[0]).replaceAll("&", "\u00A7");
	    	font.drawStringWithShadow(rank, (mouseX + width - font.getStringWidth(rank)), (mouseY - 12), 16777215);
	    } 
	    GlStateManager.enableLighting();
	    GlStateManager.enableDepth();
	    GL11.glPopAttrib();
	}
}
