package ab.client.render.tile;

import java.awt.Color;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import ab.client.core.ClientHelper;
import ab.client.model.ModelManaContainer;
import ab.common.block.tile.TileManaContainer;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.common.block.mana.BlockPool;

public class RenderTileManaContainer extends TileEntitySpecialRenderer {

    private static final ResourceLocation texture = new ResourceLocation("ab:textures/model/mana_container.png");
    private static final ResourceLocation texture1 = new ResourceLocation("ab:textures/model/mana_container1.png");
    private static final ModelManaContainer model = new ModelManaContainer();

    public static int metadata;

    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float ticks) {
        TileManaContainer container = (TileManaContainer) tileentity;
        double worldTime = (tileentity.getWorldObj() == null) ? 0.0D : (ClientTickHandler.ticksInGame + ticks);
        if (tileentity != null)
            worldTime += (new Random((tileentity.xCoord ^ tileentity.yCoord ^ tileentity.zCoord))).nextInt(360);
        boolean dil = (tileentity.getWorldObj() == null) ? ((metadata == 1)) : ((tileentity.getBlockMetadata() == 1));
        boolean fab = (tileentity.getWorldObj() == null) ? ((metadata == 2)) : ((tileentity.getBlockMetadata() == 2));
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.5f, 1.075f, 0.5f);
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        GL11.glRotatef((float) worldTime * 0.375F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslated(0.0D, Math.sin(worldTime / 80.0D) / 20.0D - 0.025D, 0.0D);
        GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
        if (fab) {
            float time = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
            if (tileentity != null)
                time += (new Random((tileentity.xCoord ^ tileentity.yCoord ^ tileentity.zCoord))).nextInt(100000);
            Color color = Color.getHSBColor((float) (time * 0.005F), 0.6F, 1.0F);
            GL11.glColor4ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) -1);
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(dil ? texture1 : texture);
        model.render();
        renderMana(tileentity.getWorldObj(), container);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
        metadata = 0;
    }

    public void renderMana(World world, TileManaContainer container) {
        if (world != null) {
            (Minecraft.getMinecraft()).renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            int mana = container.getCurrentMana();
            int cap = container.getMaxMana();
            float waterLevel = ((float) mana / (float) cap) * 0.8F;
            float v = 0.125F;
            float w = -v * 3.5F;
            if (waterLevel > 0.0F) {
                float s = 0.5f;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(3008);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0f);
                GL11.glScalef(s, s, s);
                GL11.glTranslatef(0.0f, 1.71F - waterLevel, -0.25f);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                ShaderHelper.useShader(ShaderHelper.manaPool);
                ClientHelper.renderIcon(BlockPool.manaIcon, 240);
                ShaderHelper.releaseShader();
                GL11.glEnable(3008);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
    }
}
