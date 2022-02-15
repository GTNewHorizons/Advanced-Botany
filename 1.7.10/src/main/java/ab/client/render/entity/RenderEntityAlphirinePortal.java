package ab.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import ab.common.block.BlockLebethronWood;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderEntityAlphirinePortal extends Render {

	public void doRender(Entity entity, double x, double y, double z, float fl, float fl1) {
		double worldTime = (entity.worldObj == null) ? 0.0D : (ClientTickHandler.ticksInGame + fl1);
		if (entity != null)
			worldTime += (new Random(((int)entity.posX ^ (int)entity.posY ^ (int)entity.posZ))).nextInt(360); 	
		Minecraft mc = Minecraft.getMinecraft();
		float burn = Math.min(1.0f, entity.ticksExisted * 0.0561f);
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 1);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glColor4f(230.0f / 255.0f, 211.0F / 255.0f, 0.0F, 1.0f);
		burn = Math.max(0.0f, (float)(burn + (Math.sin(worldTime / 3.2f) / 9.0f)));
		GL11.glScalef(burn / 3.15f, burn / 3.15f, burn / 3.15f);
	    GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		GL11.glDisable(GL11.GL_CULL_FACE);
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		func_77026_a(BlockLebethronWood.iconPortal, 220); 
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0f);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}	
	
	private void func_77026_a(IIcon icon, int light) {
		Tessellator tessellator = Tessellator.instance;
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
	    float f2 = icon.getMinV();
	    float f3 = icon.getMaxV();
	    float f4 = 1.0F;
	    float f5 = 0.5F;
	    float f6 = 0.25F;
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    tessellator.addVertexWithUV((0.0F - f5), (0.0F - f6), 0.0D, f, f3);
	    tessellator.addVertexWithUV((f4 - f5), (0.0F - f6), 0.0D, f1, f3);
	    tessellator.addVertexWithUV((f4 - f5), (f4 - f6), 0.0D, f1, f2);
	    tessellator.addVertexWithUV((0.0F - f5), (f4 - f6), 0.0D, f, f2);
	    tessellator.draw();
	  }

	protected ResourceLocation getEntityTexture(Entity entity) {
		return AbstractClientPlayer.locationStevePng;
	}
}
