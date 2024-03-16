package ab.client.render.tile;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ab.common.block.BlockMagicCraftCrate;
import ab.common.block.tile.TileMagicCraftCrate;
import ab.common.lib.register.BlockListAB;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileMagicCraftingCrate extends TileEntitySpecialRenderer {

    private ModelCrateBook model = new ModelCrateBook();

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float fq) {
        double time = (tile.getWorldObj() == null) ? 0.0D : (ClientTickHandler.ticksInGame + fq);
        if (tile != null) time += (new Random((tile.xCoord ^ tile.yCoord ^ tile.zCoord))).nextInt(360);
        float f1 = 0.002f;
        float f2 = 0.125f;
        float f3 = 0.1875f;
        float f4 = 0.25f;
        TileMagicCraftCrate crate = (TileMagicCraftCrate) tile;
        boolean[][] pattern = crate.getPattern();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Tessellator t = new Tessellator();
        t.startDrawingQuads();
        t.setColorRGBA_I(0xfc0195, 50);
        IIcon icon = ((BlockMagicCraftCrate) BlockListAB.blockMagicCraftCrate).nothing;
        float maxU = icon.getMaxU();
        float maxV = icon.getMaxV();
        float minU = icon.getMinU();
        float minV = icon.getMinV();
        boolean hasPattern = false;
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
            if (!pattern[i][j]) {
                int i1 = 2 - i;
                int j1 = 2 - j;
                hasPattern = true;
                t.addVertexWithUV(x + f4 + f3 * j1 + f2, y + 1 + f1, z + f4 + f3 * i1 + f2, maxU, maxV);
                t.addVertexWithUV(x + f4 + f3 * j1 + f2, y + 1 + f1, z + f4 + f3 * i1, maxU, minV);
                t.addVertexWithUV(x + f4 + f3 * j1, y + 1 + f1, z + f4 + f3 * i1, minU, minV);
                t.addVertexWithUV(x + f4 + f3 * j1, y + 1 + f1, z + f4 + f3 * i1 + f2, minU, maxV);
                float f0 = f2;
                if (i == 0 && j == 1) f0 = 0.0625f;
                float minY = (float) (y + f4 + f3 * i1);
                float maxY = minY + f0;
                t.addVertexWithUV(x + f4 + f3 * j1, minY, z - f1, maxU, maxV);
                t.addVertexWithUV(x + f4 + f3 * j1, maxY, z - f1, maxU, minV);
                t.addVertexWithUV(x + f4 + f3 * j1 + f2, maxY, z - f1, minU, minV);
                t.addVertexWithUV(x + f4 + f3 * j1 + f2, minY, z - f1, minU, maxV);
                t.addVertexWithUV(x + 1 + f1, minY, z + f4 + f3 * j1, maxU, maxV);
                t.addVertexWithUV(x + 1 + f1, maxY, z + f4 + f3 * j1, maxU, minV);
                t.addVertexWithUV(x + 1 + f1, maxY, z + f4 + f3 * j1 + f2, minU, minV);
                t.addVertexWithUV(x + 1 + f1, minY, z + f4 + f3 * j1 + f2, minU, maxV);
                j1 = j;
                t.addVertexWithUV(x - f1, minY, z + f4 + f3 * j1 + f2, maxU, maxV);
                t.addVertexWithUV(x - f1, maxY, z + f4 + f3 * j1 + f2, maxU, minV);
                t.addVertexWithUV(x - f1, maxY, z + f4 + f3 * j1, minU, minV);
                t.addVertexWithUV(x - f1, minY, z + f4 + f3 * j1, minU, maxV);
                t.addVertexWithUV(x + f4 + f3 * j1 + f2, minY, z + 1 + f1, maxU, maxV);
                t.addVertexWithUV(x + f4 + f3 * j1 + f2, maxY, z + 1 + f1, maxU, minV);
                t.addVertexWithUV(x + f4 + f3 * j1, maxY, z + 1 + f1, minU, minV);
                t.addVertexWithUV(x + f4 + f3 * j1, minY, z + 1 + f1, minU, maxV);
            }
        }
        t.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        ItemStack wand = crate.getStackInSlot(10);
        if (wand != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5f, y + 1.12f + (Math.sin(time / 24.0D) / 40.0D - 0.05D), z + 0.275f);
            GL11.glRotated(Math.sin(time / 24.0D) * 5.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            EntityItem entity = new EntityItem(tile.getWorldObj(), 0, -0.125, 0, wand.copy());
            entity.hoverStart = 0.0f;
            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(entity, 0, -0.285, 0, 0.0f, 0.0f);
            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
        }
        ItemStack book = crate.getStackInSlot(11);
        if (book != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.7f, y + 0.825f + (Math.sin(-time / 24.0D) / 40.0D - 0.05D), z + 0.825f);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotated(Math.sin(-time / 24.0D) * 5.0f, 0.0f, 0.0f, 1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(model.texture);
            model.render();
            GL11.glPopMatrix();
        }
    }

    public class ModelCrateBook extends ModelBase {

        public final ResourceLocation texture = new ResourceLocation("thaumcraft:textures/models/fortress_armor.png");
        private ModelRenderer Book;

        public ModelCrateBook() {
            textureWidth = 128;
            textureHeight = 64;
            Book = new ModelRenderer(this, 100, 8);
            Book.addBox(1.0F, -0.3F, 4.0F, 5, 7, 2);
            Book.setRotationPoint(0.0F, 0.0F, 0.0F);
            Book.setTextureSize(128, 64);
        }

        public void render() {
            Book.render(0.0625f);
        }
    }
}
