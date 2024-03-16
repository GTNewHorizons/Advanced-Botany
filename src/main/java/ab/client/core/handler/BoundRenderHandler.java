package ab.client.core.handler;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL11;

import ab.api.IBoundRender;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.wand.ICoordBoundItem;
import vazkii.botania.api.wand.IWireframeAABBProvider;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class BoundRenderHandler {

    @SubscribeEvent
    public void onWorldRenderLast(RenderWorldLastEvent event) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(2896);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        Tessellator.renderingWorldRenderer = false;
        EntityClientPlayerMP entityClientPlayerMP = (Minecraft.getMinecraft()).thePlayer;
        ItemStack stack = entityClientPlayerMP.getCurrentEquippedItem();
        int color = Color.HSBtoRGB((ClientTickHandler.ticksInGame % 200) / 200.0F, 0.6F, 1.0F);
        if (stack != null && stack.getItem() instanceof ICoordBoundItem) {
            ChunkCoordinates[] coords = null;
            MovingObjectPosition pos = (Minecraft.getMinecraft()).objectMouseOver;
            if (pos != null) {
                TileEntity tile = (Minecraft.getMinecraft()).theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
                if (tile != null && tile instanceof IBoundRender) coords = ((IBoundRender) tile).getBlocksCoord();
            }
            if (coords != null) for (int i = 0; i < coords.length; i++) {
                if (coords[i].posY == -1) continue;
                renderBlockOutlineAt(coords[i], color, 1.0f);
            }
        }
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private void renderBlockOutlineAt(ChunkCoordinates pos, int color, float thickness) {
        GL11.glPushMatrix();
        GL11.glTranslated(
                pos.posX - RenderManager.renderPosX,
                pos.posY - RenderManager.renderPosY,
                pos.posZ - RenderManager.renderPosZ + 1.0D);
        Color colorRGB = new Color(color);
        GL11.glColor4ub((byte) colorRGB.getRed(), (byte) colorRGB.getGreen(), (byte) colorRGB.getBlue(), (byte) -1);
        WorldClient worldClient = (Minecraft.getMinecraft()).theWorld;
        Block block = worldClient.getBlock(pos.posX, pos.posY, pos.posZ);
        if (block != null) {
            AxisAlignedBB axis;
            if (block instanceof IWireframeAABBProvider) {
                axis = ((IWireframeAABBProvider) block)
                        .getWireframeAABB((World) worldClient, pos.posX, pos.posY, pos.posZ);
            } else {
                axis = block.getSelectedBoundingBoxFromPool((World) worldClient, pos.posX, pos.posY, pos.posZ);
            }
            if (axis != null) {
                axis.minX -= pos.posX;
                axis.maxX -= pos.posX;
                axis.minY -= pos.posY;
                axis.maxY -= pos.posY;
                axis.minZ -= (pos.posZ + 1);
                axis.maxZ -= (pos.posZ + 1);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glLineWidth(thickness);
                renderBlockOutline(axis);
                GL11.glLineWidth(thickness + 3.0F);
                GL11.glColor4ub(
                        (byte) colorRGB.getRed(),
                        (byte) colorRGB.getGreen(),
                        (byte) colorRGB.getBlue(),
                        (byte) 64);
                renderBlockOutline(axis);
            }
        }
        GL11.glPopMatrix();
    }

    private void renderBlockOutline(AxisAlignedBB aabb) {
        Tessellator tessellator = Tessellator.instance;
        double ix = aabb.minX;
        double iy = aabb.minY;
        double iz = aabb.minZ;
        double ax = aabb.maxX;
        double ay = aabb.maxY;
        double az = aabb.maxZ;
        tessellator.startDrawing(1);
        tessellator.addVertex(ix, iy, iz);
        tessellator.addVertex(ix, ay, iz);
        tessellator.addVertex(ix, ay, iz);
        tessellator.addVertex(ax, ay, iz);
        tessellator.addVertex(ax, ay, iz);
        tessellator.addVertex(ax, iy, iz);
        tessellator.addVertex(ax, iy, iz);
        tessellator.addVertex(ix, iy, iz);
        tessellator.addVertex(ix, iy, az);
        tessellator.addVertex(ix, ay, az);
        tessellator.addVertex(ix, iy, az);
        tessellator.addVertex(ax, iy, az);
        tessellator.addVertex(ax, iy, az);
        tessellator.addVertex(ax, ay, az);
        tessellator.addVertex(ix, ay, az);
        tessellator.addVertex(ax, ay, az);
        tessellator.addVertex(ix, iy, iz);
        tessellator.addVertex(ix, iy, az);
        tessellator.addVertex(ix, ay, iz);
        tessellator.addVertex(ix, ay, az);
        tessellator.addVertex(ax, iy, iz);
        tessellator.addVertex(ax, iy, az);
        tessellator.addVertex(ax, ay, iz);
        tessellator.addVertex(ax, ay, az);
        tessellator.draw();
    }
}
