package com.integral.forgottenrelics.entities;

import net.minecraft.entity.projectile.*;

import java.util.List;

import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.common.registry.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.*;
import thaumcraft.common.*;
import net.minecraft.util.*;

public class EntityCrimsonOrb extends EntityThrowable implements IEntityAdditionalSpawnData
{
	
    int targetID;
    EntityLivingBase target;
    public boolean red;
    EntityLivingBase caster;
    int casterID;
    
    public EntityCrimsonOrb(final World par1World) {
        super(par1World);
        this.targetID = 0;
        this.red = false;
        this.casterID = 0;
    }
    
    public EntityCrimsonOrb(final World par1World, final EntityLivingBase c, final EntityLivingBase t, final boolean r) {
        super(par1World, c);
        this.targetID = 0;
        this.casterID = 0;
        this.red = false;
        this.target = t;
        this.caster = c;
        this.red = r;
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
    
    public void writeSpawnData(final ByteBuf data) {
        int idTarget = -1;
        int idCaster = -1;
        if (this.target != null) {
            idTarget = this.target.getEntityId();
        }
        if (this.caster != null) {
            idCaster = this.caster.getEntityId();
        }
        
        data.writeInt(idTarget);
        data.writeInt(idCaster);
        data.writeBoolean(this.red);
    }
    
    public void readSpawnData(final ByteBuf data) {
        final int targetID = data.readInt();
        final int casterID = data.readInt();
        try {
            if (targetID >= 0) {
                this.target = (EntityLivingBase)this.worldObj.getEntityByID(targetID);
            }
        }
        catch (Exception ex) {}
        
        try {
            if (casterID >= 0) {
                this.caster = (EntityLivingBase)this.worldObj.getEntityByID(casterID);
            }
        }
        catch (Exception ex) {}
        
        this.red = data.readBoolean();
    }
    
    protected void onImpact(final MovingObjectPosition mop) {
        if (!this.worldObj.isRemote && this.getThrower() != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            mop.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage((Entity)this, (Entity)this.getThrower()), (float) (RelicsConfigHandler.crimsonSpellDamageMIN + (Math.random() * (RelicsConfigHandler.crimsonSpellDamageMAX - RelicsConfigHandler.crimsonSpellDamageMIN))));
            
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thaumcraft:shock", 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
            SuperpositionHandler.imposeBurst(this.worldObj, this.dimension, this.posX, this.posY, this.posZ, 1.0f);
            this.setDead();
            
        } else if (!this.worldObj.isRemote & mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
        	Block block = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
        	if (!(block instanceof BlockBush) & !(block instanceof BlockLeaves) & !(block instanceof BlockLiquid)) {
        		this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thaumcraft:shock", 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        		SuperpositionHandler.imposeBurst(this.worldObj, this.dimension, this.posX, this.posY, this.posZ, 1.0f);
        		this.setDead();
        	}
        }
        
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public void getNewTarget() {
    	int searchRange = 32;
    	List <EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.posX - searchRange, this.posY - searchRange, this.posZ - searchRange, this.posX + searchRange, this.posY + searchRange, this.posZ + searchRange));
    	
    	if (entities.size() > 0) {
			
			for (int counter = entities.size() - 1; counter >= 0; counter--) {
				if (!entities.get(counter).canEntityBeSeen(this)) {
					entities.remove(counter);
					counter = entities.size();
				}
			}
			
		}
    	
    	if (entities.contains(this))
    		entities.remove(this);
    	
    	if (entities.contains(caster))
    		entities.remove(caster);
    	
    	if (entities.size() > 0)
    		this.target = entities.get((int) ((entities.size() - 1) * Math.random()));
    }
    
    public float getShadowSize() {
        return 0.1f;
    }
    
	public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > (1000)) {
            this.setDead();
        }
        
        if (!this.red)
        	this.setDead();
        
        if (this.target != null) {
        	
        	if (this.target.isDead) {
        		this.getNewTarget();
            }
        	
            final double d = this.getDistanceSqToEntity((Entity)this.target);
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
            this.motionX = MathHelper.clamp_float((float)this.motionX, -0.25f, 0.25f);
            this.motionY = MathHelper.clamp_float((float)this.motionY, -0.25f, 0.25f);
            this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.25f, 0.25f);
            
            if (this.ticksExisted < 5 & this.motionY < 0.0D)
            	this.motionY = Math.abs(this.motionY);
        } else {
        	this.getNewTarget();
        }
    }
    
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        if (p_70097_1_.getEntity() != null) {
            final Vec3 vec3 = p_70097_1_.getEntity().getLookVec();
            if (vec3 != null) {
                this.motionX = vec3.xCoord;
                this.motionY = vec3.yCoord;
                this.motionZ = vec3.zCoord;
                this.motionX *= 0.9;
                this.motionY *= 0.9;
                this.motionZ *= 0.9;
                this.worldObj.playSoundAtEntity((Entity)this, "thaumcraft:zap", 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
            }
            return true;
        }
        return false;
    }
}
