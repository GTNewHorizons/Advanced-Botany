package pulxes.ab.common.entity;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;

public class EntityProjectileSpaceBurst extends EntityThrowable {
	
	private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityProjectileSpaceBurst.class, DataSerializers.FLOAT);
	private static final DataParameter<String> ATTACKER_NAME = EntityDataManager.createKey(EntityProjectileSpaceBurst.class, DataSerializers.STRING);
	
	public EntityProjectileSpaceBurst(World world) {
		super(world);
	}
		  
	public EntityProjectileSpaceBurst(EntityLivingBase thrower) {
		super(thrower.world, thrower);
	}
	
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(DAMAGE, Float.valueOf(0.0f));
		this.dataManager.register(ATTACKER_NAME, "");
	}
	
	public void onUpdate() {
		super.onUpdate();
		updateState();
		if(ticksExisted < 20) {
			this.motionX *= 1.115f;
			this.motionY *= 1.115f;
			this.motionZ *= 1.115f;
		} else if(ticksExisted > 160)
			setDead();
		if(world.isRemote) {
			for(int i = 0; i < 12; i++) {
				float r = world.rand.nextBoolean() ? (225.0f / 255.0f) : (101.0f / 255.0f);
				float g = world.rand.nextBoolean() ? (67.0f / 255.0f) : (209.0f / 255.0f);
				float b = world.rand.nextBoolean() ? (240.0f / 255.0f) : (225.0f / 255.0f);
				Botania.proxy.sparkleFX(posX + ((Math.random() - 0.5D)) * 0.25f, posY + ((Math.random() - 0.5D) * 0.25f), (posZ + (Math.random() - 0.5D) * 0.25f), r + (float)(Math.random() / 4 - 0.125D), g + (float)(Math.random() / 4 - 0.125D), b + (float)(Math.random() / 4 - 0.125D), 1.6F * (float)(Math.random() - 0.5D), 2);
			} 
		}
	}
	
	public void updateState() {		
		String attacker = getAttacker();	
		AxisAlignedBB axis = (new AxisAlignedBB(posX, posY, posZ, lastTickPosX, lastTickPosY, lastTickPosZ)).grow(1.0D);
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		for(EntityLivingBase living : entities) {
			if(living instanceof EntityPlayer && (((EntityPlayer)living).getName().equals(attacker) || (FMLCommonHandler.instance().getMinecraftServerInstance() != null && !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled())))
				continue; 
			if(living.hurtTime == 0) {
				float damage = getDamage();
	        	if(!world.isRemote) {
	        		EntityPlayer player = living.world.getPlayerEntityByName(attacker);
	        		living.attackEntityFrom((player == null) ? DamageSource.MAGIC : DamageSource.causePlayerDamage(player), damage);
	        		setDead();
	        		break;
	        	}
			} 
		} 
	}
	
	protected void onImpact(@Nonnull RayTraceResult result) {
		if(result.getBlockPos() != null) {
			IBlockState state = world.getBlockState(result.getBlockPos());
			if(state != null) {
				if(state.getBlock() instanceof net.minecraft.block.BlockBush || state.getBlock() instanceof net.minecraft.block.BlockLeaves)
					return;
				setDead();
			}
		}
	}
	
	public void readEntityFromNBT(NBTTagCompound nbtt) {
		super.readEntityFromNBT(nbtt);
		ticksExisted = nbtt.getInteger("ticksExisted");
		setDamage(nbtt.getInteger("burtDamage"));
		setAttacker(nbtt.getString("attackerName"));
	}

	public void writeEntityToNBT(NBTTagCompound nbtt) {
		super.writeEntityToNBT(nbtt);
		nbtt.setInteger("ticksExisted", ticksExisted);
		nbtt.setFloat("burtDamage", getDamage());
		nbtt.setString("attackerName", getAttacker());
	}
	
	protected float getGravityVelocity() {
		return 0.0F;
	}
	
	public String getAttacker() {
		return this.dataManager.get(ATTACKER_NAME);
	}
	
	public void setAttacker(String str) {
		this.dataManager.set(ATTACKER_NAME, str);
	}
	
	public float getDamage() {
		return this.dataManager.get(DAMAGE);
	}
	
	public void setDamage(float damage) {
		this.dataManager.set(DAMAGE, damage);
	}
}