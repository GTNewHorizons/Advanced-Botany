package pulxes.ab.client.render.entity;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import pulxes.ab.client.core.handler.IconMiscHandler;
import pulxes.ab.client.core.helper.ClientHelper;
import pulxes.ab.common.entity.EntityAlphirinePortal;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderEntityAlphirinePortal extends Render<EntityAlphirinePortal> {

	public RenderEntityAlphirinePortal(RenderManager renderManager) {
		super(renderManager);
	}
	
	public void doRender(@Nonnull EntityAlphirinePortal entity, double x, double y, double z, float fl1, float par9) {
		double worldTime = (entity.world == null) ? 0.0D : (ClientTickHandler.ticksInGame + fl1);
		if(entity != null)
			worldTime += (new Random(((int)entity.posX ^ (int)entity.posY ^ (int)entity.posZ))).nextInt(360); 	
	
		Minecraft mc = Minecraft.getMinecraft();
		float lbx = OpenGlHelper.lastBrightnessX;
		float lby = OpenGlHelper.lastBrightnessY;
		float burn = Math.min(1.0f, entity.ticksExisted * 0.0461f);
		float scale = Math.max(0.0f, (float)(burn + (Math.sin(worldTime / 4.2f) / 10.0f))) / 3.14f;

		GlStateManager.pushMatrix();
		
		GlStateManager.translate(x, y, z);
		GlStateManager.enableBlend();
	    GlStateManager.blendFunc(770, 771);
	    GlStateManager.disableLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		GlStateManager.disableCull();
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		ClientHelper.setLightmapTextureCoords();
		ClientHelper.renderIcon(IconMiscHandler.INSTANCE.alphirinePortal, 220);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		
		GlStateManager.popMatrix();
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
	}

	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull EntityAlphirinePortal entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}