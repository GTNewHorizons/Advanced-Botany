package pulxes.ab.common.entity;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntityAlphirinePortal extends Entity {
	
	private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityProjectileSpaceBurst.class, DataSerializers.ITEM_STACK);
	private static final ItemStack DEFAULT_STACK = new ItemStack(Items.AIR);
	
	public EntityAlphirinePortal(World world) {
		super(world);
		setSize(1.0F, 1.0F);
		isImmuneToFire = true;
	}

	protected void entityInit() {
		this.dataManager.register(ITEM, DEFAULT_STACK);
	}
	
	public void onUpdate() {
		super.onUpdate();
		if(ticksExisted >= 40) {
			if(getItemStack() == DEFAULT_STACK)
				setDead();
			else if(!world.isRemote) {
				EntityItem itemResult = new EntityItem(world, posX, posY, posZ, getItemStack());
				world.spawnEntity(itemResult);
				setDead();
			}
		}
	}
	
	public ItemStack getItemStack() {
		return this.dataManager.get(ITEM);
	}
	
	public void setItemStack(ItemStack stack) {
		this.dataManager.set(ITEM, stack);
	}

	protected void readEntityFromNBT(NBTTagCompound nbtt) {
		ticksExisted = nbtt.getInteger("ticksExisted");
		ItemStack portalLoot = getItemStack().copy();
		portalLoot.writeToNBT(nbtt);
	}

	protected void writeEntityToNBT(NBTTagCompound nbtt) {
		nbtt.setInteger("ticksExisted", ticksExisted);
		setItemStack(new ItemStack(nbtt));
	}
}