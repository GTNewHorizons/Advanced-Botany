package ab.client.core;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.block.ModBlocks;

public class ClientHelper {
	
	public static String getChanceName(int chance) {
		String name = "";
		switch((int)(chance / 16.6f)) {
			case 0: name = "lowest";
				break;
			case 1: name = "veryLow";
				break;
			case 2: name = "low";
				break;
			case 3: name = "middle";
				break;
			case 4: name = "high";
				break;
			case 5: name = "veryHigh";
				break;
			case 6: name = "highest";
				break;
		}
		return StatCollector.translateToLocal("ab.alphirine.change") + ": " + StatCollector.translateToLocal("ab.chance." + name);
	}
	
	public static void renderPoolManaBar(int x, int y, int color, float alpha, int mana) {
		Minecraft mc = Minecraft.getMinecraft();
		int poolCount = (int)Math.floor(mana / 1000000.0D);
		if(poolCount < 0)
			poolCount = 0;
		int onePoolMana = mana - (poolCount * 1000000);
		String strPool = poolCount + "x";
		int xc = x - mc.fontRenderer.getStringWidth(strPool) / 2;
	    int yc = y;
		GL11.glPushMatrix();
		GL11.glTranslatef(xc + 42.0f, yc + 5.0f, 0.0f);
		RenderHelper.enableGUIStandardItemLighting();
    	RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModBlocks.pool), 0, 0);
    	RenderHelper.disableStandardItemLighting();
    	GL11.glTranslatef(18.0f, 5.0f, 300.0f);
    	mc.fontRenderer.drawString(strPool, 0, 0, color);
    	GL11.glPopMatrix();
    	if((poolCount * 1000000) == mana)
    		onePoolMana = poolCount * 1000000;
		HUDHandler.renderManaBar(x, y, color, alpha, onePoolMana, 1000000);
	}
	
	public static void drawPoolManaHUD(ScaledResolution res, String name, int mana, int color) {
		Minecraft mc = Minecraft.getMinecraft();
		int poolCount = (int)Math.floor(mana / 1000000.0D);
		if(poolCount < 0)
			poolCount = 0;
		int onePoolMana = mana - (poolCount * 1000000);
		String strPool = poolCount + "x";
		int xc = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(strPool) / 2 - 3;
	    int yc = res.getScaledHeight() / 2;
		GL11.glPushMatrix();
		GL11.glTranslatef(xc - 6.0f, yc + 30.0f, 0.0f);
		RenderHelper.enableGUIStandardItemLighting();
    	RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModBlocks.pool), 0, 0);
    	RenderHelper.disableStandardItemLighting();
    	GL11.glTranslatef(18.0f, 4.5f, 300.0f);
    	mc.fontRenderer.drawStringWithShadow(strPool, 0, 0, color);
    	GL11.glPopMatrix();
    	if((poolCount * 1000000) == mana)
    		onePoolMana = poolCount * 1000000;
		HUDHandler.drawSimpleManaHUD(color, onePoolMana, 1000000, name, res);
	}
	
	public static void drawPoolManaHUD(ScaledResolution res, String name, int mana, int maxMana, int color) {
		Minecraft mc = Minecraft.getMinecraft();
		int poolCount = (int)Math.floor(mana / 1000000.0D);
		int maxPoolCount = (int)Math.floor(maxMana / 1000000.0D);
		if(poolCount < 0)
			poolCount = 0;
		if(maxPoolCount < 0)
			maxPoolCount = 0;
		int onePoolMana = mana - (poolCount * 1000000);
		String strPool = poolCount + "x / " + maxPoolCount + "x";
		int xc = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(strPool) / 2 - 3;
	    int yc = res.getScaledHeight() / 2;
		GL11.glPushMatrix();
		GL11.glTranslatef(xc - 6.0f, yc + 30.0f, 0.0f);
		RenderHelper.enableGUIStandardItemLighting();
    	RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModBlocks.pool), 0, 0);
    	RenderHelper.disableStandardItemLighting();
    	GL11.glTranslatef(18.0f, 4.5f, 300.0f);
    	mc.fontRenderer.drawStringWithShadow(strPool, 0, 0, color);
    	GL11.glPopMatrix();
    	if((poolCount * 1000000) == mana)
    		onePoolMana = poolCount * 1000000;
		HUDHandler.drawSimpleManaHUD(color, onePoolMana, 1000000, name, res);
	}
}