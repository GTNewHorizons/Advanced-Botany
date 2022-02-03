package ab.common.entity;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntityNebulaBlaze extends EntityThrowable {
	
	public EntityNebulaBlaze(World world) {
		super(world);
		this.dataWatcher.addObject(27, "");
		this.dataWatcher.setObjectWatched(27);
	}
	
	public EntityNebulaBlaze(World world, EntityPlayer e) {
		super(world, e);
		this.dataWatcher.addObject(27, e.getCommandSenderName());
		this.dataWatcher.setObjectWatched(27);
	}
	
	public void onUpdate() {
		if(this.ticksExisted >= 240)
			setDead();	
		double posX = this.posX;
		double posY = this.posY;
		double posZ = this.posZ;
		super.onUpdate();
		AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1, this.posY + 1, this.posZ + 1).expand(3.75f, 3.75f, 3.75f);
		List<EntityLivingBase> livs = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		for(EntityLivingBase liv : livs) {
			if(liv instanceof EntityPlayer)
				continue;
			else if(liv.getHealth() <= 0.0f)
				continue;
			double d1 = (this.posX - liv.posX);
			double d2 = (this.posY - liv.posY - liv.getEyeHeight());
			double d3 = (this.posZ - liv.posZ);
			double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
			if(d4 < 3.1D)
				d4 = 3.1D;
			double d5 = 1.0D - d4;
			this.setPosition(posX, posY, posZ);
			this.motionX = d1 / d4 * d5 * 0.325D + (liv.motionX * 0.85f);
            this.motionY = d2 / d4 * d5 * 0.325D + (liv.motionY * 0.85f);
            this.motionZ = d3 / d4 * d5 * 0.325D + (liv.motionZ * 0.85f);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			break;
		}
		if(this.worldObj.isRemote) 
			for(int i = 0; i < 9; i++)
				Botania.proxy.wispFX(this.worldObj, this.posX, this.posY, this.posZ, 1.0f - (float)(Math.random() / 5.0f), 0.0f + (float)(Math.random() / 5.0f), 1.0f - (float)(Math.random() / 5.0f) , 0.2F * (float)((Math.random()) - 0.25D), (float)((Math.random() / 2) - 0.25D) * 0.03F, (float)((Math.random() / 2) - 0.25D) * 0.03F, (float)((Math.random() / 2) - 0.25D) * 0.03F);	
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float help, float me, int please) {}	
	
	protected float getGravityVelocity() {
		return 0.0F;
	}
	
	public String getPlayerName() {
		if(this.dataWatcher.getWatchableObjectString(27) == null)
			return "null";
		return this.dataWatcher.getWatchableObjectString(27);
	}

	protected void onImpact(MovingObjectPosition mov) {
		Block block = this.worldObj.getBlock(mov.blockX, mov.blockY, mov.blockZ);
		if(mov.entityHit != null && mov.entityHit instanceof EntityLivingBase) {
			EntityPlayer player = mov.entityHit.worldObj.getPlayerEntityByName(getPlayerName());
			EntityLivingBase liv = (EntityLivingBase)mov.entityHit;
			if(liv instanceof EntityPlayer && liv == player)
				return;
			else if(liv.getHealth() <= 0.0f)
				return;
			liv.hurtResistantTime = 0;
			liv.attackEntityFrom((player == null) ? DamageSource.magic : DamageSource.causePlayerDamage(player), 13.0f);
		}
		else if(block instanceof net.minecraft.block.BlockBush || block instanceof net.minecraft.block.BlockLeaves)
	    	return;
	    setDead();
	}
}