package com.integral.forgottenrelics.entities;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.renderers.tile.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.*;

public class FXBurst extends EntityFX
{
    public FXBurst(final World world, final double d, final double d1, final double d2, final float f) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.particleRed = 0.0f;
        this.particleGreen = 0.8F + (float) Math.random() * 0.2F;
        this.particleBlue = 0.4F + (float) Math.random() * 0.6F;
        this.particleGravity = 0.0f;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.particleScale *= f;
        this.particleMaxAge = 31;
        this.noClip = false;
        this.setSize(0.01f, 0.01f);
    }
    
    public void renderParticle(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        tessellator.draw();
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        UtilsFX.bindTexture(TileNodeRenderer.nodetex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        final float var8 = this.particleAge % 32 / 32.0f;
        final float var9 = var8 + 0.03125f;
        final float var10 = 0.96875f;
        final float var11 = 1.0f;
        final float var12 = this.particleScale;
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - FXBurst.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - FXBurst.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - FXBurst.interpPosZ);
        final float var16 = 1.0f;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0f);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12), (double)var9, (double)var11);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12), (double)var9, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12), (double)var8, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12), (double)var8, (double)var11);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getParticleTexture());
        tessellator.startDrawingQuads();
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
    
    public void setGravity(final float value) {
        this.particleGravity = value;
    }
}
