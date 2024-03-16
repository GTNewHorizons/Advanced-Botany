package ab.client.render.tile;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ab.client.model.ModelEngineerHopper;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileEngineerHopper extends TileEntitySpecialRenderer {

    private static final ModelEngineerHopper model = new ModelEngineerHopper();
    private static final ResourceLocation texture = new ResourceLocation("ab:textures/model/engineerHopper.png");

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float ticks) {
        double time = (tile.getWorldObj() == null) ? 0.0D : (ClientTickHandler.ticksInGame + ticks);
        if (tile != null) time += (new Random((tile.xCoord ^ tile.yCoord ^ tile.zCoord))).nextInt(360);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.5f, 1.0625f, 0.5f);
        GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.renderHoper(time);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }
}
