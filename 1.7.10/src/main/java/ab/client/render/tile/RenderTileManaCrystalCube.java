package ab.client.render.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.model.ModelCrystalCube;
import vazkii.botania.common.item.ModItems;

public class RenderTileManaCrystalCube extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation texture = new ResourceLocation("ab:textures/model/crystalCube.png");	  
	private static final ModelCrystalCube model = new ModelCrystalCube();
	private EntityItem entity = null;
	
	public void renderTileEntityAt(TileEntity tile, double d0, double d1, double d2, float f) {
		if(this.entity == null)
			this.entity = new EntityItem(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, new ItemStack(ModItems.twigWand)); 
		this.entity.age = ClientTickHandler.ticksInGame;
		double time = (ClientTickHandler.ticksInGame + f);
		double worldTicks = (tile.getWorldObj() == null) ? 0.0D : time;
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslated(d0, d1, d2);
		mc.renderEngine.bindTexture(texture);
		GL11.glTranslatef(0.5F, 1.5F, 0.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		this.model.renderBase();
		GL11.glTranslatef(0.0F, (float)Math.sin(worldTicks / 20.0D) * 0.05F, 0.0F);
		if(tile.getWorldObj() != null) {
			GL11.glPushMatrix();
			float s = 0.5F;
			GL11.glTranslatef(0.0F, 0.8F, 0.0F);
			GL11.glScalef(s, s, s);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			((Render)RenderManager.instance.entityRenderMap.get(EntityItem.class)).doRender((Entity)this.entity, 0.0D, 0.0D, 0.0D, 1.0F, f);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(texture);
		} 
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
		this.model.renderCube();
		GL11.glColor3f(1.0F, 1.0F, 1.0F); 
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glEnable(32826);
		GL11.glPopMatrix();
	}
}
