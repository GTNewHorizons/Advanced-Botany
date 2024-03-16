package ab.client.render.entity;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ab.client.core.ClientHelper;
import ab.common.block.BlockLebethronWood;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderEntityAlphirinePortal extends Render {

    public void doRender(Entity entity, double x, double y, double z, float fl, float fl1) {
        double worldTime = (entity.worldObj == null) ? 0.0D : (ClientTickHandler.ticksInGame + fl1);
        if (entity != null)
            worldTime += (new Random(((int) entity.posX ^ (int) entity.posY ^ (int) entity.posZ))).nextInt(360);
        Minecraft mc = Minecraft.getMinecraft();
        float burn = Math.min(1.0f, entity.ticksExisted * 0.0561f);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        burn = Math.max(0.0f, (float) (burn + (Math.sin(worldTime / 3.2f) / 9.0f)));
        GL11.glScalef(burn / 3.15f, burn / 3.15f, burn / 3.15f);
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glDisable(GL11.GL_CULL_FACE);
        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        ClientHelper.setLightmapTextureCoords();
        ClientHelper.renderIcon(BlockLebethronWood.iconPortal, 220);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}
