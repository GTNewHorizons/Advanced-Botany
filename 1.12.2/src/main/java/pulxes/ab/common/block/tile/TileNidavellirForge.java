package pulxes.ab.common.block.tile;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import pulxes.ab.api.AdvancedBotanyAPI;
import pulxes.ab.api.recipe.RecipeNidavellirForge;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.tile.TileSimpleInventory;
import vazkii.botania.common.item.ModItems;

public class TileNidavellirForge extends TileSimpleInventory implements ITickable, ISparkAttachable {

	private int wasMana;
	private int mana;
	public int manaToGet;
	private RecipeNidavellirForge currentRecipe;
	private int recipeID;
	public boolean requestUpdate;
	
	public void update() {
		if(!world.isRemote)
			updateServer();
		else
			doParticle(pos.getX(), pos.getY(), pos.getZ(), (float)mana / (float)manaToGet, recipeID);
		ISparkEntity spark = getAttachedSpark();
		if(spark != null) {
			List<ISparkEntity> sparkEntities = SparkHelper.getSparksAround(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
			for (ISparkEntity otherSpark : sparkEntities) {
				if(spark == otherSpark)
					continue; 
				if(otherSpark.getAttachedTile() != null && otherSpark.getAttachedTile() instanceof vazkii.botania.api.mana.IManaPool)
					otherSpark.registerTransfer(spark); 
			} 
		} 
	}
	
	private void updateServer() {
		wasMana = mana;
		
		if(requestUpdate) 
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
		boolean hasUpdate = false;
		
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)));
		for(EntityItem item : items)
			if(!item.isDead && !item.getItem().isEmpty()) {
				ItemStack stack = item.getItem();
				hasUpdate = addItem(null, stack, true);
			}
		
		int wasManaToGet = manaToGet;
		boolean hasCraft = false;
		int recipeID = 0;
		
		for(RecipeNidavellirForge recipe : AdvancedBotanyAPI.forgeRecipes) {
			if(recipe.matches(itemHandler)) {
				this.recipeID = recipeID;
				if(mana > 0 && isFull()) {
					ItemStack output = recipe.getOutput().copy();
					recieveMana(-recipe.getManaUsage());
					manaToGet = 0;
					for(int i = 1; i < getSizeInventory(); i++)
						itemHandler.getStackInSlot(i).shrink(1);
					if(itemHandler.getStackInSlot(0).isEmpty())
						itemHandler.setStackInSlot(0, output);
					else
						itemHandler.getStackInSlot(0).grow(1);
					hasUpdate = true;
		//			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "botania:terrasteelCraft", 1.0F, 2.0F);
					break;
				} else {
					if(itemHandler.getStackInSlot(0).isEmpty() || (isItemEqual(recipe.getOutput(), itemHandler.getStackInSlot(0)) && itemHandler.getStackInSlot(0).getCount() < itemHandler.getStackInSlot(0).getMaxStackSize())) {
						manaToGet = recipe.getManaUsage();
						currentRecipe = recipe;
						hasCraft = true;
						break;
					}
				}
			}
			recipeID++;
		}
		
		if(!hasCraft) {
			currentRecipe = null;
			mana = 0;
			manaToGet = 0;
		}
		
		if(world.getTotalWorldTime() % 20 == 0 && wasMana > 0 && mana != wasMana)
			hasUpdate = true;
		
		if(manaToGet != wasManaToGet)
			hasUpdate = true;
		
		requestUpdate = hasUpdate;
	}
	
	public static void doParticle(int posX, int posY, int posZ, float craftProcess, int recipeID) {
		if(craftProcess > 0.0f) {			
			float size = 0.4f;
			double tickIncrement = 360.0D / 3;
			double wticks = tickIncrement;
			
			double radius = (1.0f - craftProcess) * 0.525f;
			float rotate = 82.0f + (craftProcess * 360.0f);
			double gravitation = Math.cos(1.0f - craftProcess) * 0.4f;
			
			int color = 0x24cce0;
			if(recipeID > 0 && !AdvancedBotanyAPI.forgeRecipes.isEmpty())
				color = AdvancedBotanyAPI.forgeRecipes.get(Math.min(AdvancedBotanyAPI.forgeRecipes.size(), recipeID)).getColor();
			float hsb[] = Color.RGBtoHSB(color & 255, color >> 8 & 255, color >> 16 & 255, null);
			int color1 = Color.HSBtoRGB(hsb[0], hsb[1], craftProcess);
			float[] colorsfx = { (color1 & 255) / 255.0f, (color1 >> 8 & 255) / 255.0f, (color1 >> 16 & 255) / 255.0f };
			
			for(int i = 0; i < 3; i++) {		
				double x = posX + Math.sin((rotate + wticks) * Math.PI / 180.0D) * radius + 0.5D;
				double y = posY + 0.7D + gravitation;
				double z = posZ + Math.cos((rotate + wticks) * Math.PI / 180.0D) * radius + 0.5D;
				
				wticks += tickIncrement;
	
				Botania.proxy.wispFX(x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], 0.425F * size, 0.0f, 0.27F);
				Botania.proxy.wispFX(x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], (float)Math.random() * 0.05F + 0.01F * size, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, 0.9F);
				
				if(craftProcess == 1.0F)
					for(int j = 0; j < 12; j++)
						Botania.proxy.wispFX(posX + 0.5D, posY + 1.1D, posZ + 0.5D, colorsfx[0], colorsfx[1], colorsfx[2], (float)Math.random() * 0.15F + 0.15F * size, (float)(Math.random() - 0.5D) * 0.125F * size, (float)(Math.random() - 0.5D) * 0.125F * size, (float)(Math.random() - 0.5D) * 0.125F * size, 0.8f);  
			}
		}
	}
	
	public boolean addItem(@Nullable EntityPlayer player, ItemStack stack, boolean isDrop) {
		if(stack.getItem() == ModItems.twigWand || stack.getItem() == ModItems.lexicon)
			return false;
		for(int i = 1; i < getSizeInventory(); i++) {
			if(itemHandler.getStackInSlot(i).isEmpty()) {
				requestUpdate = true;
				ItemStack stackToAdd = stack.copy();
				int countToAdd = isDrop ? Math.min(Math.min(stackToAdd.getMaxStackSize(), stackToAdd.getCount()), itemHandler.getSlotLimit(i)) : 1;
				stackToAdd.setCount(countToAdd);
				itemHandler.setStackInSlot(i, stackToAdd);
				if(player == null || !player.capabilities.isCreativeMode)
					stack.shrink(countToAdd);
				break;
			} else {
				ItemStack slotStack = itemHandler.getStackInSlot(i);
				if(isItemEqual(slotStack, stack)) {
					int availableCount = slotStack.getMaxStackSize() - slotStack.getCount();
					if(availableCount > 0) {
						requestUpdate = true;
						ItemStack stackToAdd = stack.copy();
						int countToAdd = isDrop ? Math.min(availableCount, stackToAdd.getCount()) : 1;
						slotStack.grow(countToAdd);
						if(player == null || !player.capabilities.isCreativeMode)
							stack.shrink(countToAdd);
						break;
					}
				}
			}
		} 
		return true;
	}
	
	public static boolean isItemEqual(ItemStack stack, ItemStack stack1) {
		return stack.isItemEqual(stack1) && ItemStack.areItemStackTagsEqual(stack, stack1);
	}
	
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound nbtt) {
		NBTTagCompound ret = super.writeToNBT(nbtt);
		nbtt.setBoolean("requestUpdate", requestUpdate);
		return ret;
	}
	
	public void readFromNBT(NBTTagCompound nbtt) {
	    super.readFromNBT(nbtt);
		this.requestUpdate = nbtt.getBoolean("requestUpdate");
	}

	public void writePacketNBT(NBTTagCompound nbtt) {
		super.writePacketNBT(nbtt);
		nbtt.setInteger("mana", mana);
		nbtt.setInteger("manaToGet", manaToGet);
		nbtt.setInteger("recipeID", currentRecipe == null ? -1 : recipeID);
	}
	  
	public void readPacketNBT(NBTTagCompound nbtt) {
		super.readPacketNBT(nbtt);
		this.mana = nbtt.getInteger("mana");
		this.manaToGet = nbtt.getInteger("manaToGet");
		int recipeID = nbtt.getInteger("recipeID");
		if(recipeID == -1)
			this.currentRecipe = null;
		else			
			this.currentRecipe = AdvancedBotanyAPI.forgeRecipes.get(recipeID);
	}

	public int getSizeInventory() {
		return 4;
	}

	public int getCurrentMana() {
		return mana;
	}

	public boolean isFull() {
		return mana >= manaToGet;
	}

	public void recieveMana(int mana) {
		this.mana = Math.min(this.mana + mana, manaToGet);
	}
	
	public boolean canRecieveManaFromBursts() {
		return !this.isFull();
	}
	
	public boolean canAttachSpark(ItemStack stack) {
		return true;
	}
	
	public void attachSpark(ISparkEntity entity) {}
	
	public ISparkEntity getAttachedSpark() {
		List<Entity> sparks = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.pos.up(), this.pos.up().add(1, 1, 1)), Predicates.instanceOf(ISparkEntity.class));
		if(sparks.size() == 1) {
			Entity e = sparks.get(0);
			return (ISparkEntity)e;
		} 
		return null;
	}
	
	public boolean areIncomingTranfersDone() {
		return isFull();
	}

	public int getAvailableSpaceForMana() {
		return Math.max(0, manaToGet - getCurrentMana());
	}
}