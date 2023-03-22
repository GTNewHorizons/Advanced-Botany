package com.integral.forgottenrelics.entities;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import thaumcraft.common.*;
import thaumcraft.common.config.Config;
import net.minecraft.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import java.util.*;

import com.integral.forgottenrelics.handlers.DamageRegistryHandler;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

public class EntityDarkMatterOrb extends EntityThrowable
{
    public EntityDarkMatterOrb(final World par1World) {
        super(par1World);
    }
    
    public EntityDarkMatterOrb(final World par1World, final EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
    
    public void onUpdate() {
        super.onUpdate();
        
        double absMotionX = this.motionX;
        double absMotionY = this.motionY;
        double absMotionZ = this.motionZ;
        
        Math.abs(absMotionX);
        Math.abs(absMotionY);
        Math.abs(absMotionZ);
        
        if (this.ticksExisted > 1000) {
            this.setDead();
        } else if (this.ticksExisted >= 20 & absMotionX < 0.01D & absMotionY < 0.01D & absMotionZ < 0.01D) {
        	MovingObjectPosition mop = new MovingObjectPosition(this);
        	this.onImpact(mop);
        }
        if (this.worldObj.isRemote)
        Thaumcraft.proxy.wispFXEG(this.worldObj, this.posX, (double)(float)(this.posY + 0.22 * this.height), this.posZ, (Entity)this);
    }
    
    public void handleHealthUpdate(final byte b) {
        if (b == 16) {
            if (this.worldObj.isRemote) {
                for (int a = 0; a < 30; ++a) {
                    final float fx = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3f;
                    final float fy = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3f;
                    final float fz = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3f;
                    Thaumcraft.proxy.wispFX3(this.worldObj, this.posX + fx, this.posY + fy, this.posZ + fz, this.posX + fx * 8.0f, this.posY + fy * 8.0f, this.posZ + fz * 8.0f, 0.3f, 5, true, 0.02f);
                }
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    protected void onImpact(final MovingObjectPosition mop) {
    	
    	// TODO Immediately destroy orb on block impact
    	
    	Block block = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
    	
    	if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
    	if (block instanceof BlockBush || block instanceof BlockLeaves || block instanceof BlockLiquid) {
    		return;
    	}
    	
        if (!this.worldObj.isRemote && this.getThrower() != null) {
            final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.getThrower(), this.boundingBox.expand(1.0, 1.0, 1.0));
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity1 = (Entity) list.get(i);
                if (entity1 instanceof EntityLivingBase) {
                	
                	if (this.worldObj.provider.dimensionId == Config.dimensionOuterId) {
                		
                		((EntityLivingBase)entity1).attackEntityFrom(new DamageRegistryHandler.DamageSourceDarkMatter((Entity)this.getThrower()), RelicsConfigHandler.eldritchSpellDamageEx);
                        try {
                            ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.weakness.id, 320, 2));
                            ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 2));
                            ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.wither.id, 250, 3));
                        }
                        catch (Exception ex) {}
                		
                	} else {
                		
                		((EntityLivingBase)entity1).attackEntityFrom(new DamageRegistryHandler.DamageSourceDarkMatter((Entity)this.getThrower()), RelicsConfigHandler.eldritchSpellDamage);
                        try {
                            ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.weakness.id, 160, 1));
                            ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 1));
                            ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.wither.id, 200, 0));
                        }
                        catch (Exception ex) {}
                		
                	}
                	
                    
                }
            }
            this.worldObj.playSoundAtEntity((Entity)this, "random.fizz", 0.5f, 2.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8f);
            this.ticksExisted = 1000;
            this.worldObj.setEntityState((Entity)this, (byte)16);
        }
    }
    
    public float getShadowSize() {
        return 0.1f;
    }
}
