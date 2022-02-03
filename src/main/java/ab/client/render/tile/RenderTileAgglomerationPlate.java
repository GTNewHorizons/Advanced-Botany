package ab.client.render.tile;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import ab.common.block.tile.TileAgglomerationPlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.common.block.mana.BlockTerraPlate;

public class RenderTileAgglomerationPlate extends TileEntitySpecialRenderer {
	
	private List<EntityItem> entityList = null;
	
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		TileAgglomerationPlate tile = (TileAgglomerationPlate)tileentity;		
		float max = 50000.0F;
	    float alphaMod = Math.min(max, tile.getCurrentMana()) / max;
	    
	    //Botania code
	    GL11.glPushMatrix();
	    GL11.glTranslated(x, y, z);
	    GL11.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
	    GL11.glTranslatef(0.0F, 0.0F, -0.1885F);
	    GL11.glEnable(3042);
	    GL11.glBlendFunc(770, 771);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glDisable(3008);
	    float alpha = (float)((Math.sin((ClientTickHandler.ticksInGame + f) / 8.0D) + 1.0D) / 5.0D + 0.6D) * alphaMod;
	    if (ShaderHelper.useShaders()) {
	    	GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
	    } else {
	    	int light = 15728880;
	    	int lightmapX = light % 65536;
	    	int lightmapY = light / 65536;
	    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
	    	GL11.glColor4f(0.6F + (float)((Math.cos((ClientTickHandler.ticksInGame + f) / 6.0D) + 1.0D) / 5.0D), 0.1F, 0.9F, alpha);
	    } 
	    (Minecraft.getMinecraft()).renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	    ShaderHelper.useShader(ShaderHelper.terraPlateRune);
	    renderIcon(0, 0, BlockTerraPlate.overlay, 1, 1, 240);
	    ShaderHelper.releaseShader();
	    GL11.glEnable(3008);
	    GL11.glDisable(3042);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glPopMatrix();	
	    
		if(entityList == null) {
			List<EntityItem> list = new ArrayList();
			for(int i = 0; i < tile.getSizeInventory(); i++) {
				list.add(new EntityItem(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord));
			}
			entityList = list;
		}
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5f, y + 0.25f, z + 0.5f);
		GL11.glScalef(0.4F, 0.4F, 0.4F);
		for(int i = 1; i < entityList.size(); i++) {
			ItemStack stack = tile.getStackInSlot(i);
			if(stack != null) {
				switch(i) {
					case 1:
						GL11.glTranslated(0.15f, 0, 0);
						break;
					case 2:
						GL11.glTranslated(-0.15f, 0, -0.15f);
						break;
					case 3:
						GL11.glTranslated(-0.15f, 0, 0.15f);
						break;
				}
				entityList.get(i).setEntityItemStack(stack);
				entityList.get(i).age = ClientTickHandler.ticksInGame;
				((Render)RenderManager.instance.entityRenderMap.get(EntityItem.class)).doRender((Entity)entityList.get(i), 0.0D, 0.0D, 0.0D, 1.0F, f);
			}			
		}
		GL11.glPopMatrix();
		
		if(tile.getStackInSlot(0) != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.65f, z + 0.5);
			GL11.glScalef(0.6f, 0.6f, 0.6f);
			entityList.get(0).setEntityItemStack(tile.getStackInSlot(0));
			entityList.get(0).age = ClientTickHandler.ticksInGame;
			((Render)RenderManager.instance.entityRenderMap.get(EntityItem.class)).doRender((Entity)entityList.get(0), 0.0D, 0.0D, 0.0D, 1.0F, f);
			GL11.glPopMatrix();
		}
	}
	
	public void renderIcon(int par1, int par2, IIcon par3Icon, int par4, int par5, int brightness) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setBrightness(brightness);
		tessellator.addVertexWithUV((par1 + 0), (par2 + par5), 0.0D, par3Icon.getMinU(), par3Icon.getMaxV());
		tessellator.addVertexWithUV((par1 + par4), (par2 + par5), 0.0D, par3Icon.getMaxU(), par3Icon.getMaxV());
		tessellator.addVertexWithUV((par1 + par4), (par2 + 0), 0.0D, par3Icon.getMaxU(), par3Icon.getMinV());
		tessellator.addVertexWithUV((par1 + 0), (par2 + 0), 0.0D, par3Icon.getMinU(), par3Icon.getMinV());
		tessellator.draw();
	}
}
