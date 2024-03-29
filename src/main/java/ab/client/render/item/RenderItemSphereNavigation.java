package ab.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ab.common.item.relic.ItemSphereNavigation;

public class RenderItemSphereNavigation implements IItemRenderer {

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item,
            IItemRenderer.ItemRendererHelper helper) {
        return (helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION
                || helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING);
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack stack, Object... data) {
        GL11.glPushMatrix();
        if (type == type.INVENTORY) {
            GL11.glScalef(16, 16, 16);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(-1.0f, -1.0f, 0);
        } else if (type == type.ENTITY) {
            GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
            if (stack.isOnItemFrame()) GL11.glTranslatef(0.0F, -0.3F, 0.01F);
        }
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        IIcon icon = ItemSphereNavigation.icons[0];
        ItemRenderer.renderItemIn2D(
                Tessellator.instance,
                icon.getMaxU(),
                icon.getMinV(),
                icon.getMinU(),
                icon.getMaxV(),
                icon.getIconWidth(),
                icon.getIconHeight(),
                0.0625F);
        IIcon iconEnable = ItemSphereNavigation.icons[stack.getItemDamage() == 0 ? 4 : 3];
        ItemRenderer.renderItemIn2D(
                Tessellator.instance,
                iconEnable.getMaxU(),
                iconEnable.getMinV(),
                iconEnable.getMinU(),
                iconEnable.getMaxV(),
                iconEnable.getIconWidth(),
                iconEnable.getIconHeight(),
                0.0625F);
        double time = (double) Minecraft.getSystemTime();
        if (type != type.INVENTORY) {
            GL11.glScalef(1.0f, 1.0f, 1.05f);
            GL11.glTranslated(0.0f, 0.0f, 0.0015625f);
        }
        IIcon iconBlock = ItemSphereNavigation.icons[ItemSphereNavigation.getFindBlock(stack) != null ? 1 : 2];
        if (ItemSphereNavigation.getFindBlock(stack) != null) {
            GL11.glTranslated(0, Math.cos(time / 650.0) * 0.0075 - 0.015f, 0.0f);
        }
        ItemRenderer.renderItemIn2D(
                Tessellator.instance,
                iconBlock.getMaxU(),
                iconBlock.getMinV(),
                iconBlock.getMinU(),
                iconBlock.getMaxV(),
                iconBlock.getIconWidth(),
                iconBlock.getIconHeight(),
                0.0625F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
