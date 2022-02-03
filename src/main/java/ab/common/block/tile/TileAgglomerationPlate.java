package ab.common.block.tile;

import java.util.List;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAdvancedPlate;
import ab.common.lib.register.BlockListAB;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import scala.reflect.internal.Trees.This;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.ModFluffBlocks;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.TileSimpleInventory;
import vazkii.botania.common.item.ModItems;

public class TileAgglomerationPlate extends TileSimpleInventory implements ISparkAttachable, ISidedInventory {
	 
	private static final int[][] MANA_STEEL_BLOCKS = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };	  
	private static final int[][] MANA_QUARZ_BLOCKS = new int[][] { { 0, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
	
	int mana;
	public int manaToGet;
	  
	public static MultiblockSet makeMultiblockSet() {
		Multiblock mb = new Multiblock();
	    for (int[] l : MANA_STEEL_BLOCKS)
	    	mb.addComponent(l[0], 0, l[1], ModBlocks.storage, 0); 
	    for (int[] l : MANA_QUARZ_BLOCKS)
	    	mb.addComponent(l[0], 0, l[1], ModFluffBlocks.manaQuartz, 0); 
	    mb.addComponent(0, 1, 0, BlockListAB.blockABPlate, 0);
	    mb.setRenderOffset(0, 1, 0);
	    return mb.makeSet();
	}
	  
	public void updateEntity() {
		if(!this.worldObj.isRemote) {
			updateServer();
		} else {
			updateClient();
		}		
		if(hasValidPlatform()) {
			boolean hasCraft = false;
			for(RecipeAdvancedPlate recipe : AdvancedBotanyAPI.advancedPlateRecipes) {
				if(recipe.matches(this)) {
					if(this.getStackInSlot(0) == null) {
						this.manaToGet = recipe.getManaUsage();
						hasCraft = true;
						break;
					} else {
						if(this.getStackInSlot(0).isItemEqual(recipe.getOutput()) && this.getStackInSlot(0).stackSize < 64) {
							this.manaToGet = recipe.getManaUsage();
							hasCraft = true;
							break;
						}
					}
				}
			}
			if(!hasCraft) {
				this.mana = 0;
				this.manaToGet = 0;
			}
		}
		ISparkEntity spark = getAttachedSpark();
		if (spark != null) {
			List<ISparkEntity> sparkEntities = SparkHelper.getSparksAround(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D);
			for (ISparkEntity otherSpark : sparkEntities) {
				if (spark == otherSpark)
					continue; 
				if (otherSpark.getAttachedTile() != null && otherSpark.getAttachedTile() instanceof IManaPool)
					otherSpark.registerTransfer(spark); 
			} 
		}
	} 
	
	private void updateServer() {
		List<EntityItem> items = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, (this.xCoord + 1), (this.yCoord + 1), (this.zCoord + 1)));		
		for (EntityItem item : items) {
			if (!item.isDead && item.getEntityItem() != null) {
				ItemStack stack = item.getEntityItem();
				int splitCount = addItemStack(stack);
				stack.splitStack(splitCount);
				if(splitCount > 0)
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord); 
			}
		}
		if(this.isFull() && this.mana > 0) {
			ItemStack output = null;
			for(RecipeAdvancedPlate recipe : AdvancedBotanyAPI.advancedPlateRecipes) {
				if(recipe.matches(this)) {
					output = recipe.getOutput().copy();
					this.recieveMana(-recipe.getManaUsage());
					this.manaToGet = 0;
					for(int i = 1; i < this.getSizeInventory(); i++) {
						if(this.getStackInSlot(i).stackSize > 1) {
							this.getStackInSlot(i).stackSize -= 1;
						} else {
							this.setInventorySlotContents(i, null);
						}
					}
					if(this.getStackInSlot(0) != null) {
						this.getStackInSlot(0).stackSize += 1;
					} else {
						this.setInventorySlotContents(0, output);
					}
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord); 
					break;
				}
			}
		}
	}
	
	private void updateClient() {
		if(this.mana > 0)
			doParticles();
	}
	
	private void doParticles() {
		if (this.worldObj.isRemote) {
			int ticks = (int)(100.0D * getCurrentMana() / this.manaToGet);
			int totalSpiritCount = 3;
			double tickIncrement = 360.0D / totalSpiritCount;
			int speed = 5;
			double wticks = (ticks * speed) - tickIncrement;
			double r = Math.sin((ticks - 100) / 10.0D) * 2.0D;
			double g = Math.sin(wticks * Math.PI / 180.0D * 0.55D);
			for (int i = 0; i < totalSpiritCount; i++) {
				double x = this.xCoord + Math.sin(wticks * Math.PI / 180.0D) * r + 0.5D;
				double y = this.yCoord + 0.25D + Math.abs(r) * 0.7D;
				double z = this.zCoord + Math.cos(wticks * Math.PI / 180.0D) * r + 0.5D;
				wticks += tickIncrement;
				float[] colorsfx = { 0.0F, ticks / 100.0F, ticks / 100.0F };
				Botania.proxy.wispFX(this.worldObj, x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], 0.85F, (float)g * 0.05F, 0.25F);
				Botania.proxy.wispFX(this.worldObj, x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], (float)Math.random() * 0.1F + 0.1F, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, 0.9F);
				if (ticks == 100)
					for (int j = 0; j < 15; j++)
						Botania.proxy.wispFX(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, colorsfx[0], colorsfx[1], colorsfx[2], (float)Math.random() * 0.15F + 0.15F, (float)(Math.random() - 0.5D) * 0.125F, (float)(Math.random() - 0.5D) * 0.125F, (float)(Math.random() - 0.5D) * 0.125F);  
			} 
		} 
	}
	
	private int addItemStack(ItemStack stack) {
		for (int i = 1; i < getSizeInventory(); i++) {
			if(getStackInSlot(i) == null) {
				ItemStack stackToAdd = stack.copy();
				setInventorySlotContents(i, stackToAdd);	
				return stack.stackSize;
			} else {
				if(getStackInSlot(i).getItem() == stack.getItem() && getStackInSlot(i).getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, getStackInSlot(i)) && stack.getMaxStackSize() > getStackInSlot(i).stackSize) {
					if(getStackInSlot(i).stackSize < 64) {
						int count = 64 - getStackInSlot(i).stackSize;
						if(stack.stackSize <= count) {			
							getStackInSlot(i).stackSize += stack.stackSize;
							return stack.stackSize;
						} else {					
							getStackInSlot(i).stackSize += count;
							return stack.stackSize - count;
						}
					}
				}
			}
		}
		return 0;
	}
	  
	boolean hasValidPlatform() {
		return (checkAll(MANA_STEEL_BLOCKS, ModBlocks.storage, 0, -1) && checkAll(MANA_QUARZ_BLOCKS, ModFluffBlocks.manaQuartz, 0, -1));
	}
	  
	boolean checkAll(int[][] positions, Block block, int meta, int yOff) {
		for (int[] position : positions) {
			int[] positions_ = position;
			if (!checkPlatform(positions_[0], yOff, positions_[1], block, meta))
				return false; 
		} 
		return true;
	}
	  
	boolean checkPlatform(int xOff, int yOff, int zOff, Block block, int meta) {
		Block checkBlock = this.worldObj.getBlock(this.xCoord + xOff, this.yCoord + yOff, zOff + this.zCoord);
		return checkBlock == block && this.worldObj.getBlockMetadata(this.xCoord + xOff, this.yCoord + yOff, zOff + this.zCoord) == meta;
	}
	  
	public void writeCustomNBT(NBTTagCompound cmp) {
		super.writeCustomNBT(cmp);
		cmp.setInteger("mana", this.mana);
		cmp.setInteger("manaToGet", this.manaToGet);
	}
	  
	public void readCustomNBT(NBTTagCompound cmp) {
		super.readCustomNBT(cmp);
		this.mana = cmp.getInteger("mana");
		this.manaToGet = cmp.getInteger("manaToGet");
	}

	public int getSizeInventory() {
		return 4;
	}

	public String getInventoryName() {
		return "tileAdvancedPlate";
	}

	public int getCurrentMana() {
		return this.mana;
	}

	public boolean isFull() {
		return this.mana >= this.manaToGet;
	}

	public void recieveMana(final int mana) {
		this.mana = Math.min(this.mana + mana, this.manaToGet);
	}
	
	public boolean canRecieveManaFromBursts() {
		return !this.isFull();
	}
	
	public boolean canAttachSpark(ItemStack stack) {
		return true;
	}
	
	public void attachSpark(ISparkEntity entity) {}
	
	public ISparkEntity getAttachedSpark() {
		List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox(this.xCoord, (this.yCoord + 1), this.zCoord, (this.xCoord + 1), (this.yCoord + 2), (this.zCoord + 1)));
		if (sparks.size() == 1) {
			Entity e = (Entity)sparks.get(0);
			return (ISparkEntity)e;
		} 
		return null;
	}
	
	public boolean areIncomingTranfersDone() {
		return this.isFull();
	}
	
	public int getAvailableSpaceForMana() {
		return Math.max(0, this.manaToGet - getCurrentMana());
	}
	
	public int[] getAccessibleSlotsFromSide(int par1) {
		return new int[0];
	}

	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}
}
