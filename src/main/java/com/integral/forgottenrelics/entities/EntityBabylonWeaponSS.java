package com.integral.forgottenrelics.entities;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.ApotheosisParticleMessage;
import com.integral.forgottenrelics.handlers.BurstMessage;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityThrowableCopy;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.ItemKingKey;

/**
 * A direct copy of EntityBabylonWeapon, tweaked to work with Apotheosis.
 * @author Integral
 */

public class EntityBabylonWeaponSS extends EntityThrowableCopy {
	
	private static final String TAG_CHARGING = "charging";
	private static final String TAG_VARIETY = "variety";
	private static final String TAG_CHARGE_TICKS = "chargeTicks";
	private static final String TAG_LIVE_TICKS = "liveTicks";
	private static final String TAG_DELAY = "delay";
	private static final String TAG_ROTATION = "rotation";

	public EntityBabylonWeaponSS(World world) {
		super(world);
	}

	public EntityBabylonWeaponSS(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	@Override
	protected void entityInit() {
		setSize(0F, 0F);

		dataWatcher.addObject(20, (byte) 0);
		dataWatcher.addObject(21, 0);
		dataWatcher.addObject(22, 0);
		dataWatcher.addObject(23, 0);
		dataWatcher.addObject(24, 0);
		dataWatcher.addObject(25, 0F);

		dataWatcher.setObjectWatched(20);
		dataWatcher.setObjectWatched(21);
		dataWatcher.setObjectWatched(22);
		dataWatcher.setObjectWatched(23);
		dataWatcher.setObjectWatched(24);
		dataWatcher.setObjectWatched(25);
	}

	@Override
	public void onUpdate() {
		EntityLivingBase thrower = getThrower();
		if(!worldObj.isRemote && (thrower == null || !(thrower instanceof EntityPlayer) || thrower.isDead)) {
			setDead();
			return;
		}
		EntityPlayer player = (EntityPlayer) thrower;

		double x = motionX;
		double y = motionY;
		double z = motionZ;

		int liveTime = getLiveTicks();
		int delay = getDelay();

		if(this.ticksExisted <= 15) {
			motionX = 0;
			motionY = 0;
			motionZ = 0;

			int chargeTime = getChargeTicks();
			setChargeTicks(chargeTime + 1);

			if(worldObj.rand.nextInt(20) == 0)
				worldObj.playSoundAtEntity(this, "botania:babylonSpawn", 0.1F, 1F + worldObj.rand.nextFloat() * 3F);
		} else {
			if(liveTime < delay) {
				motionX = 0;
				motionY = 0;
				motionZ = 0;
			} else if (liveTime == delay && player != null) {
				Vector3 playerLook = null;
				MovingObjectPosition lookat = ToolCommons.raytraceFromEntity(worldObj, player, true, 64);
				if(lookat == null)
					playerLook = new Vector3(player.getLookVec()).multiply(64).add(Vector3.fromEntity(player));
				else playerLook = new Vector3(lookat.blockX + 0.5, lookat.blockY + 0.5, lookat.blockZ + 0.5);

				Vector3 thisVec = Vector3.fromEntityCenter(this);
				Vector3 motionVec = playerLook.sub(thisVec).normalize().multiply(3.0D);

				x = motionVec.x;
				y = motionVec.y;
				z = motionVec.z;
				worldObj.playSoundAtEntity(this, "botania:babylonAttack", 2F, 0.1F + worldObj.rand.nextFloat() * 3F);
			}
			setLiveTicks(liveTime + 1);
			
			if(!worldObj.isRemote) {
				AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(posX, posY, posZ, lastTickPosX, lastTickPosY, lastTickPosZ).expand(2, 2, 2);
				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
				for(EntityLivingBase living : entities) {
					if(living == thrower)
						continue;

					if(living.hurtTime == 0) {
						if(player != null)
						living.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, player), RelicsConfigHandler.damageApotheosisDirect);
						
						 Vector3 targetPos = Vector3.fromEntityCenter(living);
						 Vector3 thisPos = Vector3.fromEntityCenter(this);
						 Vector3 diff = targetPos.copy().sub(thisPos);
						 
						 double proportion = 1/this.getDistanceToEntity(living);
						 
						 if (proportion > 1.2D)
							 proportion = 1.2D;
						 
						 diff.multiply(0.2F + (proportion/8));
						 
						 living.motionX += diff.x;
						 living.motionY += diff.y;
						 living.motionZ += diff.z;
						
						onImpact(new MovingObjectPosition(living));
						return;
					}
				}
			}
		}

		super.onUpdate();

		motionX = x;
		motionY = y;
		motionZ = z;

		if(liveTime > delay)
			Botania.proxy.wispFX(worldObj, posX, posY, posZ, 1F, 1F, 0F, 0.3F, 0F);

		if(liveTime > 200 + delay)
			setDead();
	}
	
	 public void invokeDamageEffects() {
		 List <EntityLivingBase> targets = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(3.0, 3.0, 3.0));
		 
		 if (targets.contains(this.getThrower()))
			 targets.remove(this.getThrower());
		 
		 for (int counterS = targets.size()-1; counterS >= 0; counterS--) {
			 if (!targets.get(counterS).isDead & !this.worldObj.isRemote) {
			 targets.get(counterS).attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), RelicsConfigHandler.damageApotheosisImpact);
			 Vector3 targetPos = Vector3.fromEntityCenter(targets.get(counterS));
			 Vector3 thisPos = Vector3.fromEntityCenter(this);
			 Vector3 diff = targetPos.copy().sub(thisPos);
			 
			 double proportion = 1/this.getDistanceToEntity(targets.get(counterS));
			 
			 if (proportion > 1.2D)
				 proportion = 1.2D;
			 
			 diff.multiply(0.1F + (proportion/8));
			 
			 targets.get(counterS).motionX += diff.x;
			 targets.get(counterS).motionY += diff.y;
			 targets.get(counterS).motionZ += diff.z;
			 }
		 }
		 
	 }

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		EntityLivingBase thrower = this.getThrower();
		
		Block block = this.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		if (pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
	    	if (block instanceof BlockBush || block instanceof BlockLeaves || block instanceof BlockLiquid) {
	    		return;
	    	}
		
		if(pos.entityHit == null || pos.entityHit != thrower) {
			
			if (!this.worldObj.isRemote & thrower != null) {
			
			SuperpositionHandler.imposeBurst(this.worldObj, this.dimension, this.posX, this.posY, this.posZ, 1.5F);
			
			this.invokeDamageEffects();
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 8.0F, (float) (0.8F + (Math.random() * 0.2)));
			}
			
			if (!this.worldObj.isRemote)
			Main.packetInstance.sendToAllAround(new ApotheosisParticleMessage(this.posX, this.posY, this.posZ, 40), new TargetPoint(dimension, this.posX, this.posY, this.posZ, 128.0D));
			
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound cmp) {
		super.writeEntityToNBT(cmp);
		cmp.setBoolean(TAG_CHARGING, isCharging());
		cmp.setInteger(TAG_VARIETY, getVariety());
		cmp.setInteger(TAG_CHARGE_TICKS, getChargeTicks());
		cmp.setInteger(TAG_LIVE_TICKS, getLiveTicks());
		cmp.setInteger(TAG_DELAY, getDelay());
		cmp.setFloat(TAG_ROTATION, getRotation());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound cmp) {
		super.readEntityFromNBT(cmp);
		setCharging(cmp.getBoolean(TAG_CHARGING));
		setVariety(cmp.getInteger(TAG_VARIETY));
		setChargeTicks(cmp.getInteger(TAG_CHARGE_TICKS));
		setLiveTicks(cmp.getInteger(TAG_LIVE_TICKS));
		setDelay(cmp.getInteger(TAG_DELAY));
		setRotation(cmp.getFloat(TAG_ROTATION));
	}

	public boolean isCharging() {
		return dataWatcher.getWatchableObjectByte(20) == 1;
	}

	public void setCharging(boolean charging) {
		dataWatcher.updateObject(20, (byte) (charging ? 1 : 0));
	}

	public int getVariety() {
		return dataWatcher.getWatchableObjectInt(21);
	}

	public void setVariety(int var) {
		dataWatcher.updateObject(21, var);
	}

	public int getChargeTicks() {
		return dataWatcher.getWatchableObjectInt(22);
	}

	public void setChargeTicks(int ticks) {
		dataWatcher.updateObject(22, ticks);
	}

	public int getLiveTicks() {
		return dataWatcher.getWatchableObjectInt(23);
	}

	public void setLiveTicks(int ticks) {
		dataWatcher.updateObject(23, ticks);
	}

	public int getDelay() {
		return dataWatcher.getWatchableObjectInt(24);
	}

	public void setDelay(int delay) {
		dataWatcher.updateObject(24, delay);
	}

	public float getRotation() {
		return dataWatcher.getWatchableObjectFloat(25);
	}

	public void setRotation(float rot) {
		dataWatcher.updateObject(25, rot);
	}

}