package ab.client.render.tile;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ab.common.block.tile.TileABSpreader;
import vazkii.botania.api.mana.ILens;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.proxy.ClientProxy;
import vazkii.botania.client.model.ModelSpreader;
import vazkii.botania.client.render.item.RenderLens;

public class RenderTileABSpreader extends TileEntitySpecialRenderer {

    private static final ResourceLocation texture_0 = new ResourceLocation("ab:textures/model/lebethronSpreader.png");
    private static final ResourceLocation texture_1 = new ResourceLocation(
            "ab:textures/model/lebethronSpreader_halloween.png");
    private static final ModelSpreader model = new ModelSpreader();

    public void renderTileEntityAt(final TileEntity tileentity, final double x, final double y, final double z,
            final float ticks) {
        TileABSpreader spreader = (TileABSpreader) tileentity;
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.5f, 1.5f, 0.5f);
        GL11.glRotatef(spreader.rotationX + 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, -1.0f, 0.0f);
        GL11.glRotatef(spreader.rotationY, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, 1.0f, 0.0f);
        if (!ClientProxy.dootDoot) Minecraft.getMinecraft().renderEngine.bindTexture(texture_0);
        else Minecraft.getMinecraft().renderEngine.bindTexture(texture_1);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        double time = ClientTickHandler.ticksInGame + ticks;

        model.render();
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        final double worldTicks = (tileentity.getWorldObj() == null) ? 0.0 : time;
        GL11.glRotatef((float) worldTicks % 360.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, (float) Math.sin(worldTicks / 20.0) * 0.05f, 0.0f);
        model.renderCube();
        GL11.glPopMatrix();
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        final ItemStack stack = spreader.getStackInSlot(0);
        if (stack != null) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
            final ILens lens = (ILens) stack.getItem();
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.4f, -1.4f, -0.4375f);
            GL11.glScalef(0.8f, 0.8f, 0.8f);
            RenderLens.render(stack, lens.getLensColor(stack));
            GL11.glPopMatrix();
        }
        if (spreader.paddingColor != -1) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            final Block block = Blocks.carpet;
            final int color2 = spreader.paddingColor;
            final RenderBlocks render = RenderBlocks.getInstance();
            final float f = 0.0625f;
            GL11.glTranslatef(0.0f, -f, 0.0f);
            render.renderBlockAsItem(block, color2, 1.0f);
            GL11.glTranslatef(0.0f, -f * 15.0f, 0.0f);
            render.renderBlockAsItem(block, color2, 1.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glPushMatrix();
            GL11.glScalef(f * 14.0f, 1.0f, 1.0f);
            render.renderBlockAsItem(block, color2, 1.0f);
            GL11.glPopMatrix();
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, 0.0f, -f / 2.0f);
            GL11.glScalef(f * 14.0f, 1.0f, f * 15.0f);
            render.renderBlockAsItem(block, color2, 1.0f);
            GL11.glTranslatef(0.0f, f * 15.0f, 0.0f);
            render.renderBlockAsItem(block, color2, 1.0f);
        }
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }
}
