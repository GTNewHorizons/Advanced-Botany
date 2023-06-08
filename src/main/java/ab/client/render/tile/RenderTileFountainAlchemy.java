package ab.client.render.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.botania.client.core.handler.ClientTickHandler;
import ab.client.model.ModelFountainAlchemy;
import ab.common.block.tile.TileFountainAlchemy;

public class RenderTileFountainAlchemy extends TileEntitySpecialRenderer {

    private List<EntityItem> entityList = null;
    private static final ResourceLocation texture = new ResourceLocation("ab:textures/model/FountainAlchemy.png");
    private static final ModelFountainAlchemy model = new ModelFountainAlchemy();

    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        TileFountainAlchemy tile = (TileFountainAlchemy) tileentity;
        double worldTime = 0.0D;
        int meta = 2;
        float invRender = 0.0f;
        if (tileentity != null && tileentity.getWorldObj() != null) {
            worldTime = ClientTickHandler.ticksInGame + f
                    + (new Random((tileentity.xCoord ^ tileentity.yCoord ^ tileentity.zCoord))).nextInt(360);
            meta = tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        } else invRender = 0.0875f;
        float indetY = (float) (Math.sin(worldTime / 18.0D) / 24.0D);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x - invRender, y, z);
        GL11.glTranslatef(0.5f, 1.5f, 0.5f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 1.0f);
        GL11.glRotatef(90.0f * meta, 0.0f, 1.0f, 0.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.renderBottom();
        GL11.glTranslatef(0.0f, indetY, 0.0f);
        model.renderTop();
        GL11.glEnable(32826);
        GL11.glPopMatrix();
        if (entityList == null) {
            List<EntityItem> list = new ArrayList();
            for (int i = 0; i < tile.getSizeInventory(); i++) {
                list.add(new EntityItem(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord));
            }
            entityList = list;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5f, y + 0.675f - indetY, z + 0.5f);
        GL11.glScalef(0.2F, 0.225F, 0.225F);
        for (int i = 1; i < entityList.size(); i++) {
            GL11.glPushMatrix();
            ItemStack stack = tile.getStackInSlot(i);
            if (stack != null) {
                switch (i) {
                    case 1:
                        GL11.glTranslated(0.15f, 0.0f, 0.0f);
                        break;
                    case 2:
                        GL11.glTranslated(-0.15f, 0.0f, -0.15f);
                        break;
                    case 3:
                        GL11.glTranslated(-0.15f, 0.0f, 0.15f);
                        break;
                }
                entityList.get(i).setEntityItemStack(stack);
                entityList.get(i).age = ClientTickHandler.ticksInGame;
                ((Render) RenderManager.instance.entityRenderMap.get(EntityItem.class))
                        .doRender((Entity) entityList.get(i), 0.0D, 0.0D, 0.0D, 1.0F, f);
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        if (tile.getStackInSlot(0) != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y + 0.915f - indetY, z + 0.5);
            GL11.glScalef(0.45f, 0.45f, 0.45f);
            entityList.get(0).setEntityItemStack(tile.getStackInSlot(0));
            entityList.get(0).age = ClientTickHandler.ticksInGame;
            ((Render) RenderManager.instance.entityRenderMap.get(EntityItem.class))
                    .doRender((Entity) entityList.get(0), 0.0D, 0.0D, 0.0D, 1.0F, f);
            GL11.glPopMatrix();
        }
    }
}
