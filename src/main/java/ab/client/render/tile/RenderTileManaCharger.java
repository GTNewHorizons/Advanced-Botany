package ab.client.render.tile;

import java.awt.Color;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

import ab.client.model.ModelManaCharger;
import ab.common.block.tile.TileManaCharger;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileManaCharger extends TileEntitySpecialRenderer {

    private static final ResourceLocation texture = new ResourceLocation("ab:textures/model/manaCharger.png");
    private static final ModelManaCharger model = new ModelManaCharger();
    public TileManaCharger charger;

    public void renderTileEntityAt(final TileEntity tile, final double x, final double y, final double z,
            final float ticks) {
        this.charger = (TileManaCharger) tile;
        double time = (tile.getWorldObj() == null) ? 0.0D : (ClientTickHandler.ticksInGame + ticks);
        if (tile != null) time += (new Random((tile.xCoord ^ tile.yCoord ^ tile.zCoord))).nextInt(360);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.5f, 1.65f, 0.5f);
        GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render(this, time);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }

    public void renderItemStack(ItemStack stack) {
        if (stack != null) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.renderEngine.bindTexture(
                    (stack.getItem() instanceof net.minecraft.item.ItemBlock) ? TextureMap.locationBlocksTexture
                            : TextureMap.locationItemsTexture);
            float s = 0.25F;
            GL11.glScalef(s, s, s);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            if (!ForgeHooksClient.renderEntityItem(
                    new EntityItem(
                            this.charger.getWorldObj(),
                            this.charger.xCoord,
                            this.charger.yCoord,
                            this.charger.zCoord,
                            stack),
                    stack,
                    0.0F,
                    0.0F,
                    (this.charger.getWorldObj()).rand,
                    mc.renderEngine,
                    RenderBlocks.getInstance(),
                    1)) {
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                if (stack.getItem() instanceof net.minecraft.item.ItemBlock
                        && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType())) {
                    GL11.glScalef(0.5F, 0.5F, 0.5F);
                    GL11.glTranslatef(1.0F, 1.1F, 0.0F);
                    GL11.glPushMatrix();
                    RenderBlocks.getInstance()
                            .renderBlockAsItem(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 1.0F);
                    GL11.glPopMatrix();
                    GL11.glTranslatef(-1.0F, -1.1F, 0.0F);
                    GL11.glScalef(2.0F, 2.0F, 2.0F);
                } else {
                    int renderPass = 0;
                    do {
                        IIcon icon = stack.getItem().getIcon(stack, renderPass);
                        if (icon == null) continue;
                        Color color = new Color(stack.getItem().getColorFromItemStack(stack, renderPass));
                        GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
                        float f = icon.getMinU();
                        float f1 = icon.getMaxU();
                        float f2 = icon.getMinV();
                        float f3 = icon.getMaxV();
                        ItemRenderer.renderItemIn2D(
                                Tessellator.instance,
                                f1,
                                f2,
                                f,
                                f3,
                                icon.getIconWidth(),
                                icon.getIconHeight(),
                                0.0625F);
                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                        ++renderPass;
                    } while (renderPass < stack.getItem().getRenderPasses(stack.getItemDamage()));
                }
            }
            GL11.glScalef(1.0F / s, 1.0F / s, 1.0F / s);
            (Minecraft.getMinecraft()).renderEngine.bindTexture(texture);
        }
    }
}
