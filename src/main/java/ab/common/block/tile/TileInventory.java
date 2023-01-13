package ab.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import vazkii.botania.common.block.tile.TileMod;

public abstract class TileInventory extends TileMod implements IInventory {

	ItemStack[] inventorySlots = new ItemStack[getSizeInventory()];
	  
	public void readCustomNBT(NBTTagCompound par1NBTTagCompound) {
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
	    this.inventorySlots = new ItemStack[getSizeInventory()];
	    for(int var3 = 0; var3 < var2.tagCount(); var3++) {
	    	NBTTagCompound var4 = var2.getCompoundTagAt(var3);
	    	byte var5 = var4.getByte("Slot");
	    	if(var5 >= 0 && var5 < this.inventorySlots.length)
	    		this.inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4); 
	    } 
	}
	  
	public void writeCustomNBT(NBTTagCompound par1NBTTagCompound) {
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < this.inventorySlots.length; var3++) {
			if (this.inventorySlots[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.inventorySlots[var3].writeToNBT(var4);
				var2.appendTag((NBTBase)var4);
			} 
		} 
		par1NBTTagCompound.setTag("Items", (NBTBase)var2);
	}
	  
	public ItemStack getStackInSlot(int i) {
		return this.inventorySlots[i];
	}
	  
	public ItemStack decrStackSize(int i, int j) {
		if(this.inventorySlots[i] != null) {
			if(!this.worldObj.isRemote)
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord); 
			if((this.inventorySlots[i]).stackSize <= j) {
				ItemStack itemStack = this.inventorySlots[i];
				this.inventorySlots[i] = null;
				markDirty();
				return itemStack;
			} 
			ItemStack stackAt = this.inventorySlots[i].splitStack(j);
			if((this.inventorySlots[i]).stackSize == 0)
				this.inventorySlots[i] = null; 
			markDirty();
			return stackAt;	
		} 
		return null;
	}
	  
	public ItemStack getStackInSlotOnClosing(int i) {
		return getStackInSlot(i);
	}
	  
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.inventorySlots[i] = itemstack;
		markDirty();
		if (!this.worldObj.isRemote)
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord); 
	}	
	  
	public int getInventoryStackLimit() {
		return 64;
	}
	  
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) ? false : ((entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D));
	}
	
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	  
	public boolean hasCustomInventoryName() {
		return false;
	}
	  
	public void openInventory() {}
	  
	public void closeInventory() {}
}