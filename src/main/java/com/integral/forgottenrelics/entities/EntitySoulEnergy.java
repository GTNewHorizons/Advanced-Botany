package com.integral.forgottenrelics.entities;

import java.util.List;

import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import thaumcraft.common.*;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;
import cpw.mods.fml.common.registry.*;
import io.netty.buffer.*;

public class EntitySoulEnergy extends EntityThrowable implements IEntityAdditionalSpawnData {

    int targetID;
    EntityLivingBase target;

    public EntitySoulEnergy(final World par1World) {
        super(par1World);
        this.targetID = 0;
        setSize(0.0F, 0.0F);
    }

    public EntitySoulEnergy(final World par1World, final EntityLivingBase par2EntityLiving, final EntityLivingBase t,
            final boolean r) {
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

        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) if (mop.entityHit == this.target) {

        }

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

        float rc = 1.0F;
        float gc = 1.0F;
        float bc = 1.0F;

        for (int i = 0; i < steps; i++) {
            Botania.proxy.sparkleFX(worldObj, particlePos.x, particlePos.y, particlePos.z, rc, gc, bc, 0.8F, 2);
            if (worldObj.rand.nextInt(steps) <= 1) Botania.proxy.sparkleFX(
                    worldObj,
                    particlePos.x + (Math.random() - 0.5) * 0.4,
                    particlePos.y + (Math.random() - 0.5) * 0.4,
                    particlePos.z + (Math.random() - 0.5) * 0.4,
                    rc,
                    gc,
                    bc,
                    0.8F,
                    2);

            particlePos.add(step);
        }

        if (true) {
            List<EntityLivingBase> targetList = worldObj.getEntitiesWithinAABB(
                    EntityLivingBase.class,
                    AxisAlignedBB
                            .getBoundingBox(posX - 0.5, posY - 0.5, posZ - 0.5, posX + 0.5, posY + 0.5, posZ + 0.5));
            if (targetList.contains(target)) {

                for (int i = 0; i <= 6; i++) {
                    float r = 1.0F;
                    float g = 1.0F;
                    float b = 1.0F;
                    float s = 0.1F + (float) Math.random() * 0.1F;
                    float m = 0.15F;
                    float xm = ((float) Math.random() - 0.5F) * m;
                    float ym = ((float) Math.random() - 0.5F) * m;
                    float zm = ((float) Math.random() - 0.5F) * m;

                    Botania.proxy.wispFX(
                            worldObj,
                            this.posX + this.width / 2,
                            this.posY + this.height / 2,
                            this.posZ + this.width / 2,
                            r,
                            g,
                            b,
                            s,
                            xm,
                            ym,
                            zm);
                }

                this.worldObj.playSoundEffect(
                        target.posX,
                        target.posY,
                        target.posZ,
                        "random.fizz",
                        0.6F,
                        0.8F + (float) Math.random() * 0.2F);

                target.heal(1);
                ((EntityPlayer) target).getFoodStats().addStats(1, 1.0F);;

                this.setDead();

            }

        }

        if (this.target != null) {

            final double d = this.getDistanceSqToEntity((Entity) this.target);
            double dx = this.target.posX - this.posX;
            double dy = this.target.boundingBox.minY + this.target.height * 0.6 - this.posY;
            double dz = this.target.posZ - this.posZ;
            final double d2 = 0.3;
            dx /= d;
            dy /= d;
            dz /= d;

            this.motionX += dx * d2;
            this.motionY += dy * d2;
            this.motionZ += dz * d2;

            this.motionX = MathHelper.clamp_float((float) this.motionX, -0.35f, 0.35f);
            this.motionY = MathHelper.clamp_float((float) this.motionY, -0.35f, 0.35f);
            this.motionZ = MathHelper.clamp_float((float) this.motionZ, -0.35f, 0.35f);

        } else this.setDead();

    }
}
