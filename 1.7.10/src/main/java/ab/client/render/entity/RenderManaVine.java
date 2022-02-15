package ab.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import ab.common.lib.register.BlockListAB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class RenderManaVine extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float something, float pticks) {
//		Random rand = entity.worldObj.rand;
//		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
//		GL11.glPushMatrix();
//		GL11.glTranslated(x, y, z);
//		GL11.glEnable(GL11.GL_BLEND);
//		Tessellator t = new Tessellator();
//		t.startDrawingQuads();
//		t.setColorRGBA_I(0x1de30d, 200);
//		TileRenderer.renderCube(x, y, z, 0.3, Blocks.quartz_block.getIcon(0, 0), t);
//		t.draw();
//		GL11.glDisable(GL11.GL_BLEND);
//		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
