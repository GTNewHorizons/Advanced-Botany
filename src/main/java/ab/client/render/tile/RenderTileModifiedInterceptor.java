package ab.client.render.tile;

import org.lwjgl.opengl.GL11;

import ab.common.block.tile.TileModifiedCorporeaFunnel;
import ab.common.block.tile.TileModifiedInterceptor;
import ab.common.lib.register.BlockListAB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTileModifiedInterceptor extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float fq) {
		if(((TileModifiedInterceptor)tile).getCatalystTile() == null)
			return;
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		int meta = ((TileModifiedInterceptor)tile).getCatalystTile().getBlockMetadata();
		float time = Minecraft.getSystemTime() / 1400;
		int brightness = 0;
		if (time % 150 >= 75) {
			brightness = (int) ((int) (Minecraft.getSystemTime() / 1400) % 75);
		} else {
			brightness = (int) (75 - (int) (Minecraft.getSystemTime() / 1400) % 75);
		}
		if (meta == 0) {
			brightness = 25;
		}
		if (meta == 4) {
			brightness -= 25;
		}
		int color = 0;
		switch (meta) {
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
		TileRenderer.renderLateralSides(x + 0.5, y + 0.5, z + 0.5, 0.504, BlockListAB.blockModifiedInterceptor.icons[0], t);
		TileRenderer.renderUpperlSide(x + 0.5, y + 0.5, z + 0.5, 0.504, BlockListAB.blockCorporeaCatalyst.icon[2], t);
		TileRenderer.renderDownside(x + 0.5, y + 0.5, z + 0.5, 0.504, BlockListAB.blockCorporeaCatalyst.icon[3], t);
		t.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
