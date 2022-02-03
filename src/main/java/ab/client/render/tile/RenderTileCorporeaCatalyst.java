package ab.client.render.tile;

import org.lwjgl.opengl.GL11;

import ab.common.block.tile.TileCorporeaCatalyst;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.ItemListAB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.common.item.ModItems;

public class RenderTileCorporeaCatalyst extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float fq) {

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		float time = Minecraft.getSystemTime() / 1400;
		int brightness = 0;
		if (time % 150 >= 75) {
			brightness = (int) ((int) (Minecraft.getSystemTime() / 1400) % 75);
		} else {
			brightness = (int) (75 - (int) (Minecraft.getSystemTime() / 1400) % 75);
		}
		if (tile.getBlockMetadata() == 0) {
			brightness = 25;
		}
		if (tile.getBlockMetadata() == 4) {
			brightness -= 25;
		}
		int color = 0;
		switch (tile.getBlockMetadata()) {
		case 0:
			color = 0xa5a5a5;
			break;
		case 1:
			color = 0x2f10f4;
			break;
		case 2:
			color = 0xff0112;
			break;
		case 3:
			color = 0x28f314;
			break;
		case 4:
			color = 0x6dceea;
		}

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		/*
		 * if (tile.getBlockMetadata() != 0)
		 * OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 250,
		 * 250);
		 */

		Tessellator t = new Tessellator();
		t.startDrawingQuads();
		t.setBrightness(225 - brightness);
		t.setColorOpaque_I(color);
		TileRenderer.renderCube(x + 0.5, y + 0.5, z + 0.5, 0.504, BlockListAB.blockCorporeaCatalyst.icon[0], t);
		t.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		if (tile.getBlockMetadata() > 0) {
			this.renderRune((TileCorporeaCatalyst) tile, x, y, z, fq);
			//this.renderRuneOrb((TileCorporeaCatalyst) tile, x, y, z, color);
		}
	}

	public void renderRune(TileCorporeaCatalyst tile, double x, double y, double z, float fq) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		switch (tile.sideRune) {
		case 1:
			break;
		case 0:
			GL11.glRotatef(180, 1, 0, 0);
			break;
		case 3:
			GL11.glRotatef(90, 1, 0, 0);
			break;
		case 2:
			GL11.glRotatef(-90, 1, 0, 0);
			break;
		case 5:
			GL11.glRotatef(-90, 0, 0, 1);
			break;
		case 4:
			GL11.glRotatef(90, 0, 0, 1);
			break;
		}
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0 + 0.53, 0 - 0.27);
		GL11.glScaled(1.2, 1.4, 1.2);
		GL11.glRotatef(90, 1, 0, 0);
		EntityItem entity = new EntityItem(tile.getWorldObj(), 0, 0, 0,
				new ItemStack(ModItems.rune, 1, tile.getBlockMetadata() - 1));
		entity.hoverStart = 0.0f;
		RenderManager.instance.renderEntityWithPosYaw((Entity) entity, 0, 0, 0, 0.0f, 0.0f);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
