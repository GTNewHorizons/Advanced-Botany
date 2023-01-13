package ab.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntityAlphirinePortal extends Entity {
	
	private static final ItemStack itemStack = new ItemStack(Blocks.air);

	public EntityAlphirinePortal(World world) {
		super(world);
		this.dataWatcher.addObject(27, itemStack.copy());
		this.dataWatcher.setObjectWatched(27);
	}

	protected void entityInit() {}
	
	public void onUpdate() {
		super.onUpdate();
		if(this.ticksExisted >= 40) {
			if(this.getStack() == itemStack)
				this.setDead();
			if(!this.worldObj.isRemote) {
				EntityItem itemResult = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.getStack());
				this.worldObj.spawnEntityInWorld(itemResult);
				this.setDead();
			}
		}
	}

	protected void readEntityFromNBT(NBTTagCompound nbtt) {
		this.ticksExisted = nbtt.getInteger("portalTick");
		NBTTagCompound stackCmp = nbtt.getCompoundTag("dropStack");
		ItemStack stack = ItemStack.loadItemStackFromNBT(stackCmp);
		setStack(stack);
	}

	protected void writeEntityToNBT(NBTTagCompound nbtt) {
		nbtt.setInteger("portalTick", this.ticksExisted);
		ItemStack stack = getStack();
	    NBTTagCompound stackNbt = new NBTTagCompound();
	    if(stack != null)
	    	stack.writeToNBT(stackNbt); 
	    nbtt.setTag("dropStack", stackNbt);
	}
	
	public ItemStack getStack() {
		return this.dataWatcher.getWatchableObjectItemStack(27);
	}
	
	public void setStack(ItemStack stack) {
		this.dataWatcher.updateObject(27, stack);
	}
}