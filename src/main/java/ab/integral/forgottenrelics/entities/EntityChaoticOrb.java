package com.integral.forgottenrelics.entities;

import java.util.*;

import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import thaumcraft.common.*;
import thaumcraft.common.config.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.lib.world.*;

import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.common.registry.*;
import io.netty.buffer.*;

/**
 * A direct copy of EntityPrimalOrb, tweaked to work with... Whatever.
 * 
 * @author Integral
 */

public class EntityChaoticOrb extends EntityThrowable implements IEntityAdditionalSpawnData {

    int count;
    boolean seeker;
    int oi;

    public EntityChaoticOrb(final World par1World) {
        super(par1World);
        this.count = 0;
        this.seeker = false;
        this.oi = 0;
    }

    public EntityChaoticOrb(final World par1World, final EntityLivingBase par2EntityLiving, final boolean seeker) {
        super(par1World, par2EntityLiving);
        this.count = 0;
        this.seeker = false;
        this.oi = 0;
        this.seeker = seeker;
        this.oi = par2EntityLiving.getEntityId();
    }

    public void writeSpawnData(final ByteBuf data) {
        data.writeBoolean(this.seeker);
        data.writeInt(this.oi);
    }

    public void readSpawnData(final ByteBuf data) {
        this.seeker = data.readBoolean();
        this.oi = data.readInt();
    }

    protected float getGravityVelocity() {
        return 0.001f;
    }

    protected float func_70182_d() {
        return 0.5f;
    }

    public void onUpdate() {
        ++this.count;
        if (this.isInsideOfMaterial(Material.portal)) {
            this.onImpact(new MovingObjectPosition((Entity) this));
        }
        if (this.worldObj.isRemote) {
            for (int a = 0; a < 6; ++a) {
                Thaumcraft.proxy.wispFX4(
                        this.worldObj,
                        (double) ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f),
                        (double) ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f),
                        (double) ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f),
                        (Entity) this,
                        a,
                        true,
                        0.0f);
            }
            Thaumcraft.proxy.wispFX2(
                    this.worldObj,
                    this.posX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f,
                    this.posY + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f,
                    this.posZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f,
                    0.1f,
                    this.rand.nextInt(6),
                    true,
                    true,
                    0.0f);
        }
        final Random rr = new Random(this.getEntityId() + this.count);
        if (this.ticksExisted > 20) {
            if (!this.seeker) {
                this.motionX += (rr.nextFloat() - rr.nextFloat()) * 0.01f;
                this.motionY += (rr.nextFloat() - rr.nextFloat()) * 0.01f;
                this.motionZ += (rr.nextFloat() - rr.nextFloat()) * 0.01f;
            } else {
                final List<Entity> l = (List<Entity>) EntityUtils.getEntitiesInRange(
                        this.worldObj,
                        this.posX,
                        this.posY,
                        this.posZ,
                        (Entity) this,
                        (Class) EntityLivingBase.class,
                        16.0);
                double d = Double.MAX_VALUE;
                Entity t = null;
                for (final Entity e : l) {

                    if (e.getEntityId() == this.oi) {
                        if (Math.random() < 0.8D) continue;
                    }
                    if (e.isDead) {
                        continue;
                    }
                    final double dd = this.getDistanceSqToEntity(e);
                    if (dd >= d) {
                        continue;
                    }
                    d = dd;
                    t = e;

                }
                if (t != null) {
                    double dx = t.posX - this.posX;
                    double dy = t.boundingBox.minY + t.height * 0.9 - this.posY;
                    double dz = t.posZ - this.posZ;
                    final double d2 = 0.2;
                    dx /= d;
                    dy /= d;
                    dz /= d;
                    this.motionX += dx * d2;
                    this.motionY += dy * d2;
                    this.motionZ += dz * d2;
                    this.motionX = MathHelper.clamp_float((float) this.motionX, -0.2f, 0.2f);
                    this.motionY = MathHelper.clamp_float((float) this.motionY, -0.2f, 0.2f);
                    this.motionZ = MathHelper.clamp_float((float) this.motionZ, -0.2f, 0.2f);
                }
            }
        }
        super.onUpdate();
        if (this.ticksExisted > 5000) {
            this.setDead();
        }
    }

    protected void onImpact(final MovingObjectPosition mop) {
        if (this.worldObj.isRemote) {
            for (int a = 0; a < 6; ++a) {
                for (int b = 0; b < 6; ++b) {
                    final float fx = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.5f;
                    final float fy = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.5f;
                    final float fz = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.5f;
                    Thaumcraft.proxy.wispFX3(
                            this.worldObj,
                            this.posX + fx,
                            this.posY + fy,
                            this.posZ + fz,
                            this.posX + fx * 10.0f,
                            this.posY + fy * 10.0f,
                            this.posZ + fz * 10.0f,
                            0.4f,
                            b,
                            true,
                            0.05f);
                }
            }
        }
        if (!this.worldObj.isRemote) {
            float specialchance = 1.0f;
            float expl = 2.0f;
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                    && this.isInsideOfMaterial(Material.portal)) {
                expl = 4.0f;
                specialchance = 10.0f;
            }

            if (mop.entityHit != null & this.getThrower() != null) mop.entityHit.attackEntityFrom(
                    DamageSource.causeIndirectMagicDamage(this, this.getThrower()),
                    (float) (1.0F + (Math.random() * RelicsConfigHandler.chaosTomeDamageCap)));

            this.worldObj.createExplosion(
                    (Entity) null,
                    this.posX,
                    this.posY,
                    this.posZ,
                    (float) (1.0F + (Math.random() * 6.0F)),
                    true);
            if (!this.seeker && this.rand.nextInt(100) <= specialchance) {
                if (this.rand.nextBoolean()) {
                    this.taintSplosion();
                } else {
                    ThaumcraftWorldGenerator.createRandomNodeAt(
                            this.worldObj,
                            mop.blockX,
                            mop.blockY,
                            mop.blockZ,
                            this.rand,
                            false,
                            false,
                            true);
                }
            }
            this.setDead();
        }
    }

    public void taintSplosion() {
        final int x = (int) this.posX;
        final int y = (int) this.posY;
        final int z = (int) this.posZ;
        for (int a = 0; a < 10; ++a) {
            final int xx = x + (int) (this.rand.nextFloat() - this.rand.nextFloat() * 6.0f);
            final int zz = z + (int) (this.rand.nextFloat() - this.rand.nextFloat() * 6.0f);
            if (this.rand.nextBoolean()
                    && this.worldObj.getBiomeGenForCoords(xx, zz) != ThaumcraftWorldGenerator.biomeTaint) {
                Utils.setBiomeAt(this.worldObj, xx, zz, ThaumcraftWorldGenerator.biomeTaint);
                final int yy = this.worldObj.getHeightValue(xx, zz);
                if (!this.worldObj.isAirBlock(xx, yy - 1, zz)) {
                    this.worldObj.setBlock(xx, yy, zz, ConfigBlocks.blockTaintFibres, 0, 3);
                }
            }
        }
    }

    public float getShadowSize() {
        return 0.1f;
    }
}
