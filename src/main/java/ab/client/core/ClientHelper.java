package ab.client.core;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.helper.Vector3;

public class ClientHelper {

    public static final ResourceLocation miscHuds = new ResourceLocation("ab:textures/misc/miscHuds.png");
    private static final ResourceLocation field_147529_c = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
    private static final Random field_147527_e = new Random(31100L);
    public static Minecraft mc = Minecraft.getMinecraft();
    private static FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);

    public static void renderCosmicBackground() {
        field_147527_e.setSeed(31100L);
        float f4 = 0.24F;
        for (int i = 0; i < 16; i++) {
            GL11.glPushMatrix();
            float f5 = (16 - i);
            float f6 = 0.0625F;
            float f7 = 1.0F / (f5 + 1.0F);
            if (i == 0) {
                mc.renderEngine.bindTexture(field_147529_c);
                f7 = 0.1F;
                f5 = 65.0F;
                f6 = 0.125F;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            }
            if (i == 1) {
                mc.renderEngine.bindTexture(field_147526_d);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                f6 = 0.5F;
            }
            GL11.glTranslatef(0.0f, 1.5f, 0.0f);
            GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
            GL11.glTexGen(GL11.GL_S, GL11.GL_OBJECT_PLANE, func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
            GL11.glTexGen(GL11.GL_T, GL11.GL_OBJECT_PLANE, func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
            GL11.glTexGen(GL11.GL_R, GL11.GL_OBJECT_PLANE, func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
            GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
            GL11.glEnable(GL11.GL_TEXTURE_GEN_S);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_T);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_R);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_Q);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, (float) (Minecraft.getSystemTime() % 20000L) / 20000.0F, 0.0F);
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.5F, 0.5F, 0.0F);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            Color color = Color.getHSBColor((float) Minecraft.getSystemTime() / 20.0F % 360.0F / 360.0F, 1.0F, 1.0F);
            float f11 = color.getRed() / 255.0F;
            float f12 = color.getGreen() / 255.0F;
            float f13 = color.getBlue() / 255.0F;
            tessellator.setBrightness(60);
            tessellator.setColorRGBA_F(f11 * f7, f12 * f7, f13 * f7, 1.0F);
            tessellator.addVertex(0.0D, f4, 0.0D);
            tessellator.addVertex(0.0D, f4, 1.0D);
            tessellator.addVertex(1.0D, f4, 1.0D);
            tessellator.addVertex(1.0D, f4, 0.0D);
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_R);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_Q);
    }

    private static FloatBuffer func_147525_a(float p_147525_1_, float p_147525_2_, float p_147525_3_,
            float p_147525_4_) {
        field_147528_b.clear();
        field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
        field_147528_b.flip();
        return field_147528_b;
    }

    public static void drawArrow(int x, int y, boolean side) {
        mc.renderEngine.bindTexture(miscHuds);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0f);
        vazkii.botania.client.core.helper.RenderHelper.drawTexturedModalRect(x, y, 0.0F, 0, 10, side ? 0 : 22, 38);
    }

    public static void drawChanceBar(int x, int y, int chance) {
        mc.renderEngine.bindTexture(miscHuds);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0f);
        vazkii.botania.client.core.helper.RenderHelper.drawTexturedModalRect(x, y, 0.0F, 0, 0, 57, 6);
        int chancePercentage = Math.max(0, (int) ((chance / 100.f) * 55.0D));
        vazkii.botania.client.core.helper.RenderHelper.drawTexturedModalRect(x + 1, y + 1, 0.0F, 0, 6, 55, 4);
        Color color = new Color(
                Color.HSBtoRGB(
                        (chance) / 360.0f,
                        ((float) Math.sin((ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks) * 0.2D)
                                + 1.0F) * 0.3F + 0.4F,
                        1.0f));
        GL11.glColor4ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) 255);
        vazkii.botania.client.core.helper.RenderHelper
                .drawTexturedModalRect(x + 1, y + 1, 0.0F, 0, 6, Math.min(55, chancePercentage), 4);
    }

    public static void renderPoolManaBar(int x, int y, int color, float alpha, int mana) {
        Minecraft mc = Minecraft.getMinecraft();
        int poolCount = (int) Math.floor(mana / 1000000.0D);
        if (poolCount < 0) poolCount = 0;
        int onePoolMana = mana - (poolCount * 1000000);
        String strPool = poolCount + "x";
        int xc = x - mc.fontRenderer.getStringWidth(strPool) / 2;
        int yc = y;
        GL11.glPushMatrix();
        GL11.glTranslatef(xc + 42.0f, yc + 5.0f, 0.0f);
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem.getInstance()
                .renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModBlocks.pool), 0, 0);
        RenderHelper.disableStandardItemLighting();
        GL11.glTranslatef(18.0f, 5.0f, 300.0f);
        mc.fontRenderer.drawString(strPool, 0, 0, color);
        GL11.glPopMatrix();
        if ((poolCount * 1000000) == mana) onePoolMana = poolCount * 1000000;
        HUDHandler.renderManaBar(x, y, color, alpha, onePoolMana, 1000000);
    }

    public static void drawPoolManaHUD(ScaledResolution res, String name, int mana, int maxMana, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        int poolCount = (int) Math.floor(mana / 1000000.0D);
        int maxPoolCount = (int) Math.floor(maxMana / 1000000.0D);
        if (poolCount < 0) poolCount = 0;
        if (maxPoolCount < 0) maxPoolCount = 0;
        int onePoolMana = mana - (poolCount * 1000000);
        String strPool = poolCount + "x / " + maxPoolCount + "x";
        int xc = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(strPool) / 2 - 3;
        int yc = res.getScaledHeight() / 2;
        GL11.glPushMatrix();
        GL11.glTranslatef(xc - 6.0f, yc + 30.0f, 0.0f);
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem.getInstance()
                .renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModBlocks.pool), 0, 0);
        RenderHelper.disableStandardItemLighting();
        GL11.glTranslatef(18.0f, 4.5f, 300.0f);
        mc.fontRenderer.drawStringWithShadow(strPool, 0, 0, color);
        GL11.glPopMatrix();
        if ((poolCount * 1000000) == mana) onePoolMana = poolCount * 1000000;
        HUDHandler.drawSimpleManaHUD(color, onePoolMana, 1000000, name, res);
    }

    public static void setLightmapTextureCoords() {
        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
    }

    public static Vector3 setRotation(float angel, float vX, float vY, float vZ, Vector3 v3) {
        Vector3 rVec = new Vector3(vX, vY, vZ);
        Vector3 rVec1 = v3.copy().normalize();
        double rAngel = Math.toRadians(angel) * 0.5f;
        double sin = Math.sin(rAngel);
        double x = rVec.x * sin;
        double y = rVec.y * sin;
        double z = rVec.z * sin;
        rAngel = Math.cos(rAngel);
        double d = -x * rVec1.x - y * rVec1.y - z * rVec1.z;
        double d1 = rAngel * rVec1.x + y * rVec1.z - z * rVec1.y;
        double d2 = rAngel * rVec1.y - x * rVec1.z + z * rVec1.x;
        double d3 = rAngel * rVec1.z + x * rVec1.y - y * rVec1.x;
        v3.x = d1 * rAngel - d * x - d2 * z + d3 * y;
        v3.y = d2 * rAngel - d * y + d1 * z - d3 * x;
        v3.z = d3 * rAngel - d * z - d1 * y + d2 * x;
        return v3;
    }

    public static Color getCorporeaRuneColor(int posX, int posY, int posZ, int meta) {
        double time = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
        time += (new Random((posX ^ posY ^ posZ))).nextInt(360);
        float sin = (float) (Math.sin(time / 20.0f) * 0.15f) - 0.15f;
        int color = 0;
        switch (meta) {
            case 0:
                color = Color.HSBtoRGB(0.0f, 0.0f, 0.54f + (sin / 1.2f));
                break;
            case 1:
                color = Color.HSBtoRGB(0.688f, 0.93f, 0.96f + sin - 0.15f);
                break;
            case 2:
                color = Color.HSBtoRGB(0.983f, 0.99f, 1.0f + sin - 0.15f);
                break;
            case 3:
                color = Color.HSBtoRGB(0.319f, 0.92f, 0.95f + sin - 0.15f);
                break;
            case 4:
                color = Color.HSBtoRGB(0.536f, 0.53f, 0.92f + sin - 0.15f);
                break;
        }
        return new Color(color);
    }

    public static void renderIcon(IIcon icon, int light) {
        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((0.0F - f5), (0.0F - f6), 0.0D, f, f3);
        tessellator.addVertexWithUV((f4 - f5), (0.0F - f6), 0.0D, f1, f3);
        tessellator.addVertexWithUV((f4 - f5), (f4 - f6), 0.0D, f1, f2);
        tessellator.addVertexWithUV((0.0F - f5), (f4 - f6), 0.0D, f, f2);
        tessellator.draw();
    }
}
