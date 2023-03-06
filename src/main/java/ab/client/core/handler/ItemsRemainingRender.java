package ab.client.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemsRemainingRender {

    private static int maxTicks = 30;
    private static int leaveTicks = 20;
    private static ItemStack stack;
    private static int ticks;
    private static String text;

    @SideOnly(Side.CLIENT)
    public static void render(ScaledResolution resolution, float partTicks) {
        if (ticks > 0 && stack != null) {
            int pos = maxTicks - ticks;
            Minecraft mc = Minecraft.getMinecraft();
            int x = resolution.getScaledWidth() / 2 + 10 + Math.max(0, pos - leaveTicks);
            int y = resolution.getScaledHeight() / 2;
            int start = maxTicks - leaveTicks;
            float alpha = (ticks + partTicks > start) ? 1.0F : ((ticks + partTicks) / start);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glEnable(32826);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
            RenderHelper.enableGUIStandardItemLighting();
            int xp = x + (int) (16.0F * (1.0F - alpha));
            GL11.glTranslatef(xp, y, 0.0F);
            GL11.glScalef(alpha, 1.0F, 1.0F);
            RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, 0, 0);
            GL11.glScalef(1.0F / alpha, 1.0F, 1.0F);
            GL11.glTranslatef(-xp, -y, 0.0F);
            RenderHelper.disableStandardItemLighting();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3042);
            int color = 0xFFFFFF | (int) (alpha * 255.0F) << 24;
            mc.fontRenderer.drawStringWithShadow(text, x + 20, y + 6, color);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void tick() {
        if (ticks > 0) ticks--;
    }

    public static void set(ItemStack stack, String text) {
        ItemsRemainingRender.stack = stack;
        ItemsRemainingRender.text = text;
        ticks = (stack == null) ? 0 : maxTicks;
    }
}
