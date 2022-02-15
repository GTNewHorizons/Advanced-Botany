package ab.client.render.tile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import ab.client.model.ModelManaContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileManaContainer extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture = new ResourceLocation("ab:textures/model/mana_container.png");
	private static final ModelManaContainer model = new ModelManaContainer();
	
	public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z, final float ticks) {
		double worldTime = (tileentity.getWorldObj() == null) ? 0.0D : (ClientTickHandler.ticksInGame + ticks);
		if (tileentity != null)
			worldTime += (new Random((tileentity.xCoord ^ tileentity.yCoord ^ tileentity.zCoord))).nextInt(360); 	
		GL11.glPushMatrix();	
		GL11.glEnable(32826);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5f, 1.3f, 0.5f);
		GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		GL11.glRotatef((float)worldTime * 0.375F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslated(0.0D, Math.sin(worldTime / 80.0D) / 20.0D - 0.025D, 0.0D);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		model.render();
		GL11.glEnable(32826);
		GL11.glPopMatrix();
	}
}
