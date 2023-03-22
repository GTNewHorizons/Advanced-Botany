package com.integral.forgottenrelics.entities;

import java.util.LinkedList;
import java.util.List;

import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.lib.LibObfuscation;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;

/**
 * Advanced implementation of EntityMagicMissile from Botania.
 * This stuff definitely makes some work.
 * @author Integral
 */

public class EntityRageousMissile extends EntityThrowable {

	private static final String TAG_TIME = "time";
	private static final String TAG_THROWER = "thrower";

	double lockX, lockY = -1, lockZ;
	int time = 0;

	public EntityRageousMissile(World world) {
		super(world);
		setSize(0.15F, 0.15F);
	}

	public EntityRageousMissile(EntityPlayer thrower, boolean evil) {
		this(thrower.worldObj);
		ReflectionHelper.setPrivateValue(EntityThrowable.class, this, thrower, LibObfuscation.THROWER);
		setEvil(evil);
		setThrower(thrower);
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(25, (byte) 0);
		dataWatcher.addObject(26, 0);
		dataWatcher.addObject(27, "Undefined");
	}

	public void setEvil(boolean evil) {
		dataWatcher.updateObject(25, (byte) (evil ? 1 : 0));
	}
	
	public void setThrower(EntityPlayer player) {
		dataWatcher.updateObject(27, player.getDisplayName());
	}

	public boolean isEvil() {
		return dataWatcher.getWatchableObjectByte(25) == 1;
	}
	
	public EntityPlayer getTrueThrower() {
		return worldObj.getPlayerEntityByName(dataWatcher.getWatchableObjectString(27));
	}

	public void setTarget(EntityLivingBase e) {
		dataWatcher.updateObject(26, e == null ? -1 : e.getEntityId());
	}

	public EntityLivingBase getTargetEntity() {
		int id = dataWatcher.getWatchableObjectInt(26);
		Entity e = worldObj.getEntityByID(id);
		if(e != null && e instanceof EntityLivingBase)
			return (EntityLivingBase) e;

		return null;
	}

	@Override
	public void onUpdate() {
		double lastTickPosX = this.lastTickPosX;
		double lastTickPosY = this.lastTickPosY - this.yOffset + this.height/2.0F;
		double lastTickPosZ = this.lastTickPosZ;

		super.onUpdate();

		if(!worldObj.isRemote & !getTarget() & time > 160) {
			setDead();
			return;
		}

		boolean evil = isEvil();
		Vector3 thisVec = Vector3.fromEntityCenter(this);
		Vector3 oldPos = new Vector3(lastTickPosX, lastTickPosY, lastTickPosZ);
		Vector3 diff = thisVec.copy().sub(oldPos);
		Vector3 step = diff.copy().normalize().multiply(0.05);
		int steps = (int) (diff.mag() / step.mag());
		Vector3 particlePos = oldPos.copy();
		
		float rc = 0.0F;
		//float gc = (float) (0.8F + (Math.random() * 0.2F));
		//float bc = (float) (0.6F + (Math.random() * 0.4F));
		
		Botania.proxy.setSparkleFXCorrupt(false);
		for(int i = 0; i < steps; i++) {
			Botania.proxy.sparkleFX(worldObj, particlePos.x, particlePos.y, particlePos.z, rc, (float) (0.8F + (Math.random() * 0.2F)), (float) (0.4F + (Math.random() * 0.6F)), 0.8F, 2);
			if(worldObj.rand.nextInt(steps) <= 1)
				Botania.proxy.sparkleFX(worldObj, particlePos.x + (Math.random() - 0.5) * 0.4, particlePos.y + (Math.random() - 0.5) * 0.4, particlePos.z + (Math.random() - 0.5) * 0.4, rc, (float) (0.8F + (Math.random() * 0.2F)), (float) (0.4F + (Math.random() * 0.6F)), 0.8F, 2);

			particlePos.add(step);
		}
		
		Botania.proxy.setSparkleFXCorrupt(false);

		EntityLivingBase target = getTargetEntity();
		if(target != null) {
			if(lockY == -1) {
				lockX = target.posX;
				lockY = target.posY;
				lockZ = target.posZ;
			}

			Vector3 targetVec = Vector3.fromEntityCenter(target);
			Vector3 diffVec = targetVec.copy().sub(thisVec);
			Vector3 motionVec = diffVec.copy().normalize().multiply(0.5);
			motionX = motionVec.x;
			motionY = motionVec.y;
			if(time < 30)
				motionY = Math.abs(motionY);
			motionZ = motionVec.z;
			
			/*
			if(time < 20) {
				//motionY = Math.abs(motionY);
				
				Vector3 randomtVec = new Vector3(this.posX+((Math.random() - 0.5D)*16), this.posY+((Math.random() - 0.5D)*16), this.posZ+((Math.random() - 0.5D)*16));
				Vector3 diffRandomVec = randomtVec.copy().sub(thisVec);
				Vector3 motionRandomVec = diffRandomVec.copy().normalize().multiply(evil ? 0.5 : 0.6);
				motionX = motionRandomVec.x;
				motionY = motionRandomVec.y;
				motionZ = motionRandomVec.z;
				
			}
			*/
			
			if (worldObj.isRemote) {
			List<EntityLivingBase> targetList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX - 0.5, posY - 0.5, posZ - 0.5, posX + 0.5, posY + 0.5, posZ + 0.5));
			if(targetList.size() > 0) {
				
				for(int i = 0; i < 12; i++) {
					float r = 0.0F;
					float g = 0.8F + (float) Math.random() * 0.2F;
					float b = 0.4F + (float) Math.random() * 0.6F;
					float s = 0.2F + (float) Math.random() * 0.1F;
					float m = 0.15F;
					float xm = ((float) Math.random() - 0.5F) * m;
					float ym = ((float) Math.random() - 0.5F) * m;
					float zm = ((float) Math.random() - 0.5F) * m;
					
					
					Botania.proxy.wispFX(worldObj, this.posX + this.width / 2, this.posY + this.height / 2, this.posZ + this.width / 2, r, g, b, s, xm, ym, zm);
				}
				
				//this.onImpact(new MovingObjectPosition(targetList.get(0)));
				
			}
			
			}

			if(evil && diffVec.mag() < 1)
				setDead();
		} else {
			
			Vector3 targetVec = new Vector3(this.posX+((Math.random() - 0.5D)*16), this.posY+((Math.random() - 0.5D)*16), this.posZ+((Math.random() - 0.5D)*16));
			Vector3 diffVec = targetVec.copy().sub(thisVec);
			Vector3 motionVec = diffVec.copy().normalize().multiply(0.5);
			motionX = motionVec.x;
			motionY = motionVec.y;
			motionZ = motionVec.z;
			
		}

		time++;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound cmp) {
		super.writeEntityToNBT(cmp);
		cmp.setInteger(TAG_TIME, time);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound cmp) {
		super.readEntityFromNBT(cmp);
		time = cmp.getInteger(TAG_TIME);
	}


	public boolean getTarget() {
		EntityLivingBase target = getTargetEntity();
		if(target != null && target.getHealth() > 0 && !target.isDead && worldObj.loadedEntityList.contains(target))
			return true;
		if(target != null)
			setTarget(null);
		
		List entities = new LinkedList();

		double range = 32;
			entities = worldObj.getEntitiesWithinAABBExcludingEntity(this.getTrueThrower(), AxisAlignedBB.getBoundingBox(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
	
		while(entities.size() > 0) {
			Entity e = (Entity) entities.get(worldObj.rand.nextInt(entities.size()));
			if(!(e instanceof EntityLivingBase) || e.isDead) { // Just in case...
				entities.remove(e);
				continue;
			}

			target = (EntityLivingBase) e;
			setTarget(target);
			break;
		}

		return target != null;
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		Block block = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		
		if(pos.entityHit != null & getTargetEntity() == pos.entityHit) {
			
			EntityPlayer thrower = getTrueThrower();
			if(thrower != null) {
				EntityPlayer player = thrower instanceof EntityPlayer ? (EntityPlayer) thrower : null;
				pos.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, thrower), (float) (RelicsConfigHandler.nuclearFuryDamageMIN + (Math.random() * (RelicsConfigHandler.nuclearFuryDamageMAX - RelicsConfigHandler.nuclearFuryDamageMIN))));
			}
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.fizz", 2.0F, (float) (0.8F + (Math.random() * 0.2F)));
			setDead();
		}
		
	}

}