package ab.client.core.handler;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import ab.client.core.ClientHelper;
import ab.common.item.equipment.armor.ItemNebulaArmor;
import ab.common.item.equipment.armor.ItemNebulaHelm;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class PlayerRendererHandler {

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Specials.Post event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack item = player.getCurrentArmor(3);
        if (item != null && item.getItem() instanceof ItemNebulaHelm) {
            Item helm = player.getCurrentArmor(3).getItem();
            boolean hasPhantomInk = helm instanceof IPhantomInkable && ((IPhantomInkable) helm).hasPhantomInk(item);
            if (!hasPhantomInk && ((ItemNebulaHelm) helm).enableCosmicFace(item)) {
                float angelX = event.renderer.modelBipedMain.bipedHead.rotateAngleX * 180.0F / (float) Math.PI;
                float angelY = event.renderer.modelBipedMain.bipedHead.rotateAngleY * 180.0F / (float) Math.PI;
                GL11.glPushMatrix();
                if (player.isSneaking()) GL11.glTranslatef(
                        event.renderer.modelBipedMain.bipedHead.rotationPointX * 0.0625f,
                        event.renderer.modelBipedMain.bipedHead.rotationPointY * 0.0625f,
                        0.0f);
                GL11.glRotatef(angelY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(angelX, 1.0F, 0.0F, 0.0F);
                renderCosmicFace();
                GL11.glPopMatrix();
            }
        }
    }

    private void renderCosmicFace() {
        double worldTime = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(2884);

        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.4f, 0.4f);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.8675f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        boolean hasFog = GL11.glGetBoolean(2912);
        if (hasFog) GL11.glDisable(2912);
        ClientHelper.renderCosmicBackground();
        if (hasFog) GL11.glEnable(2912);
        GL11.glPopMatrix();

        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
        (Minecraft.getMinecraft()).renderEngine.bindTexture(TextureMap.locationItemsTexture);
        GL11.glTranslatef(-0.2F, -0.35F, -0.25F);
        GL11.glScalef(0.4f, 0.4f, 0.4f);
        float f = ItemNebulaArmor.nebulaEyes.getMinU();
        float f1 = ItemNebulaArmor.nebulaEyes.getMaxU();
        float f2 = ItemNebulaArmor.nebulaEyes.getMinV();
        float f3 = ItemNebulaArmor.nebulaEyes.getMaxV();
        Color color = Color.getHSBColor((float) Minecraft.getSystemTime() / 20.0F % 360.0F / 360.0F, 1.0F, 1.0F);
        float r = color.getRed() / 510.0F;
        float g = color.getGreen() / 510.0F;
        float b = color.getBlue() / 510.0F;
        GL11.glColor4f(0.5f + r, 0.5f + g, 0.5f + b, 0.685f + (float) Math.sin(worldTime / 50.0D) * 0.175F);
        ItemRenderer.renderItemIn2D(
                Tessellator.instance,
                f1,
                f2,
                f,
                f3,
                ItemNebulaArmor.nebulaEyes.getIconWidth(),
                ItemNebulaArmor.nebulaEyes.getIconHeight(),
                0.03125F);
        GL11.glDisable(3042);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }
}
