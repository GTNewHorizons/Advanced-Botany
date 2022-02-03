package ab.common.block.subtile;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.common.items.ItemResource;
import thaumcraft.common.items.relics.ItemThaumonomicon;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.ItemLexicon;

public class SubTileAngryMallos extends SubTileGenerating {

	int cooldown = 0;
	static int workMana = 360;
	
	public void onUpdate() {
		super.onUpdate();
		if(this.cooldown > 0)
			this.cooldown -= 1;
		int posX = this.supertile.xCoord;
		int posY = this.supertile.yCoord;
		int posZ = this.supertile.zCoord;
		World world = this.supertile.getWorldObj();
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, (posX + 1), (posY + 1), (posZ + 1)).expand(1, 0, 1));		
		for(EntityItem item : items) {
			ItemStack stack = ((EntityItem)item).getEntityItem();
			if(this.cooldown > 0)
				break;
			else if(stack == null)
				continue;
			else if(searchKnowledge(stack) == 0) 
				continue;
			int mana = searchKnowledge(stack);
			if(stack.stackSize > 1)
				stack.stackSize -= 1;
			else
				item.setDead();
			this.mana = Math.min(this.getMaxMana(), this.mana + mana);
			this.cooldown = 18;
			if(world.isRemote)
				for (int i = 0; i < 10; i++) {
					float m = 0.2F;
					float mx = (float)(Math.random() - 0.5D) * m;
					float my = (float)(Math.random() - 0.5D) * m;
					float mz = (float)(Math.random() - 0.5D) * m;
					world.spawnParticle("iconcrack_" + Item.getIdFromItem(stack.getItem()) + "_" + stack.getItemDamage(), item.posX, item.posY, item.posZ, mx, my, mz);
				} 
			break;
		}
	}
	
	public int getMaxMana() {
		return 76800;
	}
	
	public int getColor() {
		return 0xdbc989;
	}
	
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setInteger("cooldown", this.cooldown);
	}
		  
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		this.cooldown = cmp.getInteger("cooldown");
	}
	
	static int searchKnowledge(ItemStack stack) {
		if(stack.getItem() instanceof ItemBook || stack.getItem() instanceof ItemEditableBook)
			return workMana;
		else if(stack.getItem() instanceof ItemEnchantedBook) 
			return (int)(workMana * 2.5f);
		else if(stack.getItem() instanceof ItemLexicon)
			return workMana * 3;
		if(Botania.thaumcraftLoaded) {
			if(stack.getItem() instanceof ItemThaumonomicon)
				return (int)(workMana * 3.2f);
			else if(stack.getItem() instanceof ItemResource && stack.getItemDamage() == 9)
				return workMana * 2;
		}
		return 0;
	}
}
