package ab.common.block.subtile;

import java.util.List;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAncientAlphirine;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.ModFluffBlocks;
import vazkii.botania.common.item.ItemLexicon;

public class SubTileAncientAlphirine extends SubTileFunctional {
	
	protected static int manaRequare = 4500;

	public void onUpdate() {
		super.onUpdate();
		int posX = this.supertile.xCoord;
		int posY = this.supertile.yCoord;
		int posZ = this.supertile.zCoord;
		World world = this.supertile.getWorldObj();
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, (posX + 1), (posY + 1), (posZ + 1)).expand(1, 0, 1));		
		if(this.ticksExisted % 10 == 0) {
			label48: for(EntityItem item : items) {
				for(RecipeAncientAlphirine recipe : AdvancedBotanyAPI.alphirineRecipes) {
					if(recipe.matches(item.getEntityItem())) {
						if(this.mana >= manaRequare && item.getEntityItem().stackSize >= 1) {
							if(world.isRemote) {
								spawnParticle(world, item);
							} else {
								if(item.getEntityItem().stackSize > 1)
									item.getEntityItem().stackSize -= 1;
								else
									item.setDead();
								if(world.rand.nextInt(175) <= recipe.getChance()) {
									EntityItem itemResult = new EntityItem(world, item.posX, item.posY, item.posZ);
									itemResult.setEntityItemStack(recipe.getOutput().copy());
									world.spawnEntityInWorld(itemResult);
								}
							}
							this.mana -= manaRequare;
							break label48;
						}
					}
				}	
				if(item.getEntityItem().getItem() instanceof ItemLexicon) {
					if(!((ItemLexicon)item.getEntityItem().getItem()).isKnowledgeUnlocked(item.getEntityItem(), RecipeListAB.forgotten) && this.mana >= 45000) {				
						((ItemLexicon)item.getEntityItem().getItem()).unlockKnowledge(item.getEntityItem(), RecipeListAB.forgotten);			
						this.mana -= manaRequare;
						if(world.isRemote)
							spawnParticle(world, item);
						break label48;
					}
				}	
			}
		}		
	}
	
	private void spawnParticle(World world, EntityItem item) {
		for(int i = 0; i < 12; i++)
			Botania.proxy.wispFX(world, item.posX, item.posY, item.posZ, 232 - (int)(Math.random() * 20), 212 - (int)(Math.random() * 20), 82 - (int)(Math.random() * 20), (float)Math.random() * 0.25F, (float)(Math.random() - 0.5D) * 0.0275F, (float)(Math.random() - 0.5D) * 0.0275F, (float)(Math.random() - 0.5D) * 0.0275F);  	
	}
	
	public int getMaxMana() {
		return 180000;
	}
	  
	public int getColor() {
		return 0xd0bf58;
	}
	
	public LexiconEntry getEntry() {
		return RecipeListAB.ancientAlphirine;
	}
}