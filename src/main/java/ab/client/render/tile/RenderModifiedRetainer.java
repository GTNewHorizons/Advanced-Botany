package ab.client.render.tile;

import org.lwjgl.opengl.GL11;

import ab.common.block.tile.TileModifiedRetainer;
import ab.common.lib.register.BlockListAB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderModifiedRetainer extends TileEntitySpecialRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float fq) {
		if(!((TileModifiedRetainer)tile).isModified() || ((TileModifiedRetainer)tile).getCatalystTile() == null)
			return;
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		int meta = ((TileModifiedRetainer)tile).getCatalystTile().getBlockMetadata();
		float time = Minecraft.getSystemTime() / 1400;
		int brightness = 0;
		if (time % 150 >= 75) {
			brightness = 225 - (int)((int) (Minecraft.getSystemTime() / 1400) % 75);
		} else {
			brightness = 225 - (int)(75 - (int) (Minecraft.getSystemTime() / 1400) % 75);
		}
		if (meta == 0) {
			brightness = 200;
		}
		if (meta == 4) {
			brightness += 20;
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
		Tessellator t = new Tessellator();
		t.startDrawingQuads();
		t.setBrightness(brightness);
		t.setColorOpaque_I(color);
		TileRenderer.renderCube(x + 0.5, y + 0.5, z + 0.5, 0.504, BlockListAB.blockModifiedRetainer.icons[0], t);
		t.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
