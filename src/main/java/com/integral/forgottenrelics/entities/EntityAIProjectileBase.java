package com.integral.forgottenrelics.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import thaumcraft.common.*;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;
import cpw.mods.fml.common.registry.*;
import io.netty.buffer.*;

public class EntityAIProjectileBase extends EntityThrowable implements IEntityAdditionalSpawnData {

    int targetID;
    EntityLivingBase target;

    public EntityAIProjectileBase(final World par1World) {
        super(par1World);
        this.targetID = 0;
        setSize(0.0F, 0.0F);
    }

    public EntityAIProjectileBase(final World par1World, final EntityLivingBase par2EntityLiving,
            final EntityLivingBase t, final boolean r) {
        super(par1World, par2EntityLiving);
        this.targetID = 0;
        this.target = t;
        setSize(0.0F, 0.0F);
    }

    protected float getGravityVelocity() {
        return 0.0f;
    }

    public void writeSpawnData(final ByteBuf data) {
        int id = -1;
        if (this.target != null) {
            id = this.target.getEntityId();
        }
        data.writeInt(id);
    }

    public void readSpawnData(final ByteBuf data) {
        final int id = data.readInt();
        try {
            if (id >= 0) {
                this.target = (EntityLivingBase) this.worldObj.getEntityByID(id);
            }
        } catch (Exception ex) {}
    }

    protected void onImpact(final MovingObjectPosition mop) {

        this.setDead();
    }

    public float getShadowSize() {
        return 0.1f;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > (1000)) {
            this.setDead();
        }

        double lastTickPosX = this.lastTickPosX;
        double lastTickPosY = this.lastTickPosY - this.yOffset + this.height / 2.0F;
        double lastTickPosZ = this.lastTickPosZ;

        Vector3 thisVec = Vector3.fromEntityCenter(this);
        Vector3 oldPos = new Vector3(lastTickPosX, lastTickPosY, lastTickPosZ);
        Vector3 diff = thisVec.copy().sub(oldPos);
        Vector3 step = diff.copy().normalize().multiply(0.05);
        int steps = (int) (diff.mag() / step.mag());
        Vector3 particlePos = oldPos.copy();

        float rc = 0.0F;
        // float gc = (float) (0.8F + (Math.random() * 0.2F));
        // float bc = (float) (0.6F + (Math.random() * 0.4F));

        for (int i = 0; i < steps; i++) {
            Botania.proxy.sparkleFX(
                    worldObj,
                    particlePos.x,
                    particlePos.y,
                    particlePos.z,
                    rc,
                    (float) (0.8F + (Math.random() * 0.2F)),
                    (float) (0.4F + (Math.random() * 0.6F)),
                    0.8F,
                    2);
            if (worldObj.rand.nextInt(steps) <= 1) Botania.proxy.sparkleFX(
                    worldObj,
                    particlePos.x + (Math.random() - 0.5) * 0.4,
                    particlePos.y + (Math.random() - 0.5) * 0.4,
                    particlePos.z + (Math.random() - 0.5) * 0.4,
                    rc,
                    (float) (0.8F + (Math.random() * 0.2F)),
                    (float) (0.4F + (Math.random() * 0.6F)),
                    0.8F,
                    2);

            particlePos.add(step);
        }

        if (this.target != null) {

            final double d = this.getDistanceSqToEntity((Entity) this.target);
            double dx = this.target.posX - this.posX;
            double dy = this.target.boundingBox.minY + this.target.height * 0.6 - this.posY;
            double dz = this.target.posZ - this.posZ;
            final double d2 = 1.5;
            dx /= d;
            dy /= d;
            dz /= d;
            this.motionX += dx * d2;
            this.motionY += dy * d2;
            this.motionZ += dz * d2;
            this.motionX = MathHelper.clamp_float((float) this.motionX, -0.25f, 0.25f);
            this.motionY = MathHelper.clamp_float((float) this.motionY, -0.25f, 0.25f);
            this.motionZ = MathHelper.clamp_float((float) this.motionZ, -0.25f, 0.25f);

        } else this.setDead();

    }
}
