package com.integral.forgottenrelics.entities;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import cpw.mods.fml.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class FXWisp extends EntityFX
{
    Entity target;
    public boolean shrink;
    float moteParticleScale;
    int moteHalfLife;
    public boolean tinkle;
    public int blendmode;
    
    public FXWisp(final World world, final double d, final double d1, final double d2, final float f, final float f1, final float f2) {
        this(world, d, d1, d2, 1.0f, f, f1, f2);
    }
    
    public FXWisp(final World world, final double d, final double d1, final double d2, final float f, float red, final float green, final float blue) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.target = null;
        this.shrink = false;
        this.tinkle = false;
        this.blendmode = 1;
        if (red == 0.0f) {
            red = 1.0f;
        }
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleGravity = 0.0f;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.particleScale *= f;
        this.moteParticleScale = this.particleScale;
        this.particleMaxAge = (int)(36.0 / (Math.random() * 0.3 + 0.7));
        this.moteHalfLife = this.particleMaxAge / 2;
        this.noClip = false;
        this.setSize(0.1f, 0.1f);
        final EntityLivingBase renderentity = FMLClientHandler.instance().getClient().renderViewEntity;
        int visibleDistance = 50;
        if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) {
            visibleDistance = 25;
        }
        if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) {
            this.particleMaxAge = 0;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }
    
    public FXWisp(final World world, final double d, final double d1, final double d2, final float f, final int type) {
        this(world, d, d1, d2, f, 0.0f, 0.0f, 0.0f);
        switch (type) {
            case 0: {
                this.particleRed = 0.75f + world.rand.nextFloat() * 0.25f;
                this.particleGreen = 0.25f + world.rand.nextFloat() * 0.25f;
                this.particleBlue = 0.75f + world.rand.nextFloat() * 0.25f;
                break;
            }
            case 1: {
                this.particleRed = 0.5f + world.rand.nextFloat() * 0.3f;
                this.particleGreen = 0.5f + world.rand.nextFloat() * 0.3f;
                this.particleBlue = 0.2f;
                break;
            }
            case 2: {
                this.particleRed = 0.2f;
                this.particleGreen = 0.2f;
                this.particleBlue = 0.7f + world.rand.nextFloat() * 0.3f;
                break;
            }
            case 3: {
                this.particleRed = 0.2f;
                this.particleGreen = 0.7f + world.rand.nextFloat() * 0.3f;
                this.particleBlue = 0.2f;
                break;
            }
            case 4: {
                this.particleRed = 0.7f + world.rand.nextFloat() * 0.3f;
                this.particleGreen = 0.2f;
                this.particleBlue = 0.2f;
                break;
            }
            case 5: {
                this.blendmode = 771;
                this.particleRed = world.rand.nextFloat() * 0.1f;
                this.particleGreen = world.rand.nextFloat() * 0.1f;
                this.particleBlue = world.rand.nextFloat() * 0.1f;
                break;
            }
            case 6: {
                this.particleRed = 0.8f + world.rand.nextFloat() * 0.2f;
                this.particleGreen = 0.8f + world.rand.nextFloat() * 0.2f;
                this.particleBlue = 0.8f + world.rand.nextFloat() * 0.2f;
                break;
            }
            case 7: {
                this.particleRed = 0.7f + world.rand.nextFloat() * 0.3f;
                this.particleGreen = 0.5f + world.rand.nextFloat() * 0.2f;
                this.particleBlue = 0.3f + world.rand.nextFloat() * 0.1f;
                break;
            }
        }
    }
    
    public FXWisp(final World world, final double d, final double d1, final double d2, final double x, final double y, final double z, final float f, final int type) {
        this(world, d, d1, d2, f, type);
        if (this.particleMaxAge > 0) {
            final double dx = x - this.posX;
            final double dy = y - this.posY;
            final double dz = z - this.posZ;
            this.motionX = dx / this.particleMaxAge;
            this.motionY = dy / this.particleMaxAge;
            this.motionZ = dz / this.particleMaxAge;
        }
    }
    
    public FXWisp(final World world, final double d, final double d1, final double d2, final Entity tar, final int type) {
        this(world, d, d1, d2, 0.4f, type);
        this.target = tar;
    }
    
    public FXWisp(final World world, final double d, final double d1, final double d2, final double x, final double y, final double z, final float f, final float red, final float green, final float blue) {
        this(world, d, d1, d2, f, red, green, blue);
        if (this.particleMaxAge > 0) {
            final double dx = x - this.posX;
            final double dy = y - this.posY;
            final double dz = z - this.posZ;
            this.motionX = dx / this.particleMaxAge;
            this.motionY = dy / this.particleMaxAge;
            this.motionZ = dz / this.particleMaxAge;
        }
    }
    
    public void renderParticle(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        float agescale = 0.0f;
        if (this.shrink) {
            agescale = (this.particleMaxAge - (float)this.particleAge) / this.particleMaxAge;
        }
        else {
            agescale = this.particleAge / (float)this.moteHalfLife;
            if (agescale > 1.0f) {
                agescale = 2.0f - agescale;
            }
        }
        this.particleScale = this.moteParticleScale * agescale;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        final float f6 = 0.5f * this.particleScale;
        final float f7 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - FXWisp.interpPosX);
        final float f8 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - FXWisp.interpPosY);
        final float f9 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - FXWisp.interpPosZ);
        final float var8 = 0.0f;
        final float var9 = 0.125f;
        final float var10 = 0.875f;
        final float var11 = 1.0f;
        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 0.5f);
        tessellator.addVertexWithUV((double)(f7 - f1 * f6 - f4 * f6), (double)(f8 - f2 * f6), (double)(f9 - f3 * f6 - f5 * f6), (double)var9, (double)var11);
        tessellator.addVertexWithUV((double)(f7 - f1 * f6 + f4 * f6), (double)(f8 + f2 * f6), (double)(f9 - f3 * f6 + f5 * f6), (double)var9, (double)var10);
        tessellator.addVertexWithUV((double)(f7 + f1 * f6 + f4 * f6), (double)(f8 + f2 * f6), (double)(f9 + f3 * f6 + f5 * f6), (double)var8, (double)var10);
        tessellator.addVertexWithUV((double)(f7 + f1 * f6 - f4 * f6), (double)(f8 - f2 * f6), (double)(f9 + f3 * f6 - f5 * f6), (double)var8, (double)var11);
    }
    
    public int getFXLayer() {
        return (this.blendmode != 1) ? 1 : 0;
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge == 0 && this.tinkle && this.worldObj.rand.nextInt(3) == 0) {
            this.worldObj.playSoundAtEntity((Entity)this, "random.orb", 0.02f, 0.5f * ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.6f + 2.0f));
        }
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.motionY -= 0.04 * this.particleGravity;
        if (!this.noClip) {
            this.pushOutOfBlocks(this.posX, this.posY, this.posZ);
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.target != null) {
            this.motionX *= 0.985;
            this.motionY *= 0.985;
            this.motionZ *= 0.985;
            double dx = this.target.posX - this.posX;
            double dy = this.target.posY - this.posY;
            double dz = this.target.posZ - this.posZ;
            final double d13 = 0.2;
            final double d14 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
            dx /= d14;
            dy /= d14;
            dz /= d14;
            this.motionX += dx * d13;
            this.motionY += dy * d13;
            this.motionZ += dz * d13;
            this.motionX = MathHelper.clamp_float((float)this.motionX, -0.2f, 0.2f);
            this.motionY = MathHelper.clamp_float((float)this.motionY, -0.2f, 0.2f);
            this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.2f, 0.2f);
        }
        else {
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (this.onGround) {
                this.motionX *= 0.699999988079071;
                this.motionZ *= 0.699999988079071;
            }
        }
    }
    
    protected boolean pushOutOfBlocks(final double par1, final double par3, final double par5) {
        final int var7 = MathHelper.floor_double(par1);
        final int var8 = MathHelper.floor_double(par3);
        final int var9 = MathHelper.floor_double(par5);
        final double var10 = par1 - var7;
        final double var11 = par3 - var8;
        final double var12 = par5 - var9;
        if (!this.worldObj.isAirBlock(var7, var8, var9) && this.worldObj.isBlockNormalCubeDefault(var7, var8, var9, true) && !this.worldObj.isAnyLiquid(this.boundingBox)) {
            final boolean var13 = !this.worldObj.isBlockNormalCubeDefault(var7 - 1, var8, var9, true);
            final boolean var14 = !this.worldObj.isBlockNormalCubeDefault(var7 + 1, var8, var9, true);
            final boolean var15 = !this.worldObj.isBlockNormalCubeDefault(var7, var8 - 1, var9, true);
            final boolean var16 = !this.worldObj.isBlockNormalCubeDefault(var7, var8 + 1, var9, true);
            final boolean var17 = !this.worldObj.isBlockNormalCubeDefault(var7, var8, var9 - 1, true);
            final boolean var18 = !this.worldObj.isBlockNormalCubeDefault(var7, var8, var9 + 1, true);
            byte var19 = -1;
            double var20 = 9999.0;
            if (var13 && var10 < var20) {
                var20 = var10;
                var19 = 0;
            }
            if (var14 && 1.0 - var10 < var20) {
                var20 = 1.0 - var10;
                var19 = 1;
            }
            if (var15 && var11 < var20) {
                var20 = var11;
                var19 = 2;
            }
            if (var16 && 1.0 - var11 < var20) {
                var20 = 1.0 - var11;
                var19 = 3;
            }
            if (var17 && var12 < var20) {
                var20 = var12;
                var19 = 4;
            }
            if (var18 && 1.0 - var12 < var20) {
                var20 = 1.0 - var12;
                var19 = 5;
            }
            final float var21 = this.rand.nextFloat() * 0.05f + 0.025f;
            final float var22 = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            if (var19 == 0) {
                this.motionX = -var21;
                final double n = var22;
                this.motionZ = n;
                this.motionY = n;
            }
            if (var19 == 1) {
                this.motionX = var21;
                final double n2 = var22;
                this.motionZ = n2;
                this.motionY = n2;
            }
            if (var19 == 2) {
                this.motionY = -var21;
                final double n3 = var22;
                this.motionZ = n3;
                this.motionX = n3;
            }
            if (var19 == 3) {
                this.motionY = var21;
                final double n4 = var22;
                this.motionZ = n4;
                this.motionX = n4;
            }
            if (var19 == 4) {
                this.motionZ = -var21;
                final double n5 = var22;
                this.motionX = n5;
                this.motionY = n5;
            }
            if (var19 == 5) {
                this.motionZ = var21;
                final double n6 = var22;
                this.motionX = n6;
                this.motionY = n6;
            }
            return true;
        }
        return false;
    }
    
    public void setGravity(final float value) {
        this.particleGravity = value;
    }
}
