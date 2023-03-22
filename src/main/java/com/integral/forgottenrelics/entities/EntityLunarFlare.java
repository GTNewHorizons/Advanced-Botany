package com.integral.forgottenrelics.entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import thaumcraft.common.*;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.LunarBurstMessage;
import com.integral.forgottenrelics.handlers.LunarFlaresParticleMessage;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.registry.*;
import io.netty.buffer.*;

public class EntityLunarFlare extends EntityThrowable implements IEntityAdditionalSpawnData {

    int lockX;
    int lockY;
    int lockZ;
    EntityLivingBase thrower;

    public EntityLunarFlare(final World par1World) {
        super(par1World);
        setSize(0.0F, 0.0F);
    }

    public EntityLunarFlare(final World par1World, final EntityLivingBase par2EntityLiving, int x, int y, int z) {
        super(par1World, par2EntityLiving);
        this.thrower = par2EntityLiving;
        this.lockX = x;
        this.lockY = y;
        this.lockZ = z;
        setSize(0.0F, 0.0F);
    }

    protected float getGravityVelocity() {
        return 0.0f;
    }

    public void writeSpawnData(final ByteBuf data) {
        data.writeInt(this.lockX);
        data.writeInt(this.lockY);
        data.writeInt(this.lockZ);
    }

    public void readSpawnData(final ByteBuf data) {
        this.lockX = data.readInt();
        this.lockY = data.readInt();
        this.lockZ = data.readInt();
    }

    protected void onImpact(final MovingObjectPosition mop) {

        if (this.getThrower() == null & !worldObj.isRemote) {
            this.setDead();
            return;
        }

        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {

            if (mop.entityHit != this.getThrower())
                mop.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 100.0F);
        }

        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (mop.blockX == this.lockX & mop.blockY == this.lockY & mop.blockZ == this.lockZ) {
                List<EntityLivingBase> affectedEntities = worldObj
                        .getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(2.5, 2.5, 2.5));
                if (affectedEntities.contains(this.getThrower())) affectedEntities.remove(this.getThrower());

                if (affectedEntities.contains(mop.entityHit)) affectedEntities.remove(mop.entityHit);

                for (int counter = affectedEntities.size() - 1; counter >= 0; counter--) {
                    if (!affectedEntities.get(counter).isDead & !this.worldObj.isRemote) {
                        affectedEntities.get(counter).attackEntityFrom(
                                DamageSource.causeIndirectMagicDamage(this, this.getThrower()),
                                75.0F);

                        Vector3 targetPos = Vector3.fromEntityCenter(affectedEntities.get(counter));
                        Vector3 thisPos = Vector3.fromEntityCenter(this);
                        Vector3 diff = targetPos.copy().sub(thisPos);

                        double proportion = 1 / this.getDistanceToEntity(affectedEntities.get(counter));
                        diff.multiply(0.2F + (proportion / 16));

                        affectedEntities.get(counter).motionX += diff.x;
                        affectedEntities.get(counter).motionY += diff.y;
                        affectedEntities.get(counter).motionZ += diff.z;
                    }
                }

                if (!this.worldObj.isRemote) Main.packetInstance.sendToAllAround(
                        new LunarFlaresParticleMessage(this.lockX + 0.5, this.lockY + 1.25, this.lockZ + 0.5, 48),
                        new TargetPoint(dimension, this.posX, this.posY, this.posZ, 128.0D));

                worldObj.playAuxSFX(
                        2001,
                        this.lockX,
                        this.lockY,
                        this.lockZ,
                        Block.getIdFromBlock(worldObj.getBlock(this.lockX, this.lockY, this.lockZ))
                                + (worldObj.getBlockMetadata(this.lockX, this.lockY, this.lockZ) << 12));
                worldObj.playSoundEffect(
                        this.lockX,
                        this.lockY,
                        this.lockZ,
                        "ForgottenRelics:sound.lunarFlare",
                        16.0F,
                        0.8F + (float) Math.random() * 0.2F);

                if (!this.worldObj.isRemote) Main.packetInstance.sendToAllAround(
                        new LunarBurstMessage(this.lockX + 0.5, this.lockY + 1.5, this.lockZ + 0.5, 2.0F),
                        new TargetPoint(dimension, this.posX, this.posY, this.posZ, 128.0D));

                // Thaumcraft.proxy.burst(worldObj, this.lockX+0.5, this.lockY+1.5, this.lockZ+0.5, 2.0F);

                // if (!worldObj.isRemote)
                this.setDead();
            }

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

        float rc = 0.0F;
        // float gc = (float) (0.8F + (Math.random() * 0.2F));
        // float bc = (float) (0.6F + (Math.random() * 0.4F));

        for (int i = 0; i < steps; i++) {
            Botania.proxy.sparkleFX(
                    worldObj,
                    particlePos.x + (Math.random() - 0.5) * 0.2,
                    particlePos.y + (Math.random() - 0.5) * 0.2,
                    particlePos.z + (Math.random() - 0.5) * 0.2,
                    rc,
                    (float) (0.8F + (Math.random() * 0.2F)),
                    (float) (0.4F + (Math.random() * 0.6F)),
                    2.0F,
                    1);
            if (worldObj.rand.nextInt(steps) <= 1) Botania.proxy.sparkleFX(
                    worldObj,
                    particlePos.x + (Math.random() - 0.5) * 1.0,
                    particlePos.y + (Math.random() - 0.5) * 1.0,
                    particlePos.z + (Math.random() - 0.5) * 1.0,
                    rc,
                    (float) (0.8F + (Math.random() * 0.2F)),
                    (float) (0.4F + (Math.random() * 0.6F)),
                    2.4F,
                    4);

            particlePos.add(step);
        }

    }
}
