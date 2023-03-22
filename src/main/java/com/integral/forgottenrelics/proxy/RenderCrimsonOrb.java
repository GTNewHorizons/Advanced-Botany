package com.integral.forgottenrelics.proxy;

import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

import com.integral.forgottenrelics.entities.EntityCrimsonOrb;

import thaumcraft.client.fx.*;
import thaumcraft.client.lib.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

public class RenderCrimsonOrb extends Render
{
    private Random random;
    
    public RenderCrimsonOrb() {
        this.random = new Random();
        this.shadowSize = 0.0f;
    }
    
    public void renderEntityAt(final Entity entity, final double x, final double y, final double z, final float fq, final float pticks) {
        final Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        UtilsFX.bindTexture(ParticleEngine.particleTexture);
        final float f2 = (1 + entity.ticksExisted % 6) / 8.0f;
        final float f3 = f2 + 0.125f;
        float f4 = 0.875f;
        if (entity instanceof EntityCrimsonOrb && ((EntityCrimsonOrb)entity).red) {
            f4 = 0.75f;
        }
        final float f5 = f4 + 0.125f;
        final float f6 = 1.0f;
        final float f7 = 0.5f;
        final float f8 = 0.5f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        final float bob = MathHelper.sin(entity.ticksExisted / 5.0f) * 0.2f + 0.2f;
        GL11.glScalef(1.0f + bob, 1.0f + bob, 1.0f + bob);
        tessellator.startDrawingQuads();
        tessellator.setBrightness(220);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.addVertexWithUV((double)(-f7), (double)(-f8), 0.0, (double)f2, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(-f8), 0.0, (double)f3, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(1.0f - f8), 0.0, (double)f3, (double)f4);
        tessellator.addVertexWithUV((double)(-f7), (double)(1.0f - f8), 0.0, (double)f2, (double)f4);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}
