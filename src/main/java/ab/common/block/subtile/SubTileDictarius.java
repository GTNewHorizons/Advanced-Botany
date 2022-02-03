package ab.common.block.subtile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileDictarius extends SubTileGenerating {
	
	int cooldown = 0;
	int workMana = 400;
	
	public void onUpdate() {
		super.onUpdate();
		int posX = this.supertile.xCoord;
		int posY = this.supertile.yCoord;
		int posZ = this.supertile.zCoord;
		World world = this.supertile.getWorldObj();
		if(this.cooldown == 0) {
			List<EntityLivingBase> livs = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, (posX + 1), (posY + 1), (posZ + 1)).expand(4, 3, 4));		
			int workMana = 0;
			for(EntityLivingBase liv : livs) {
				if(liv instanceof EntityPlayer)
					workMana += this.workMana;
				else if(liv instanceof EntityVillager)
					workMana += (int)(this.workMana / 8);
			}
			this.cooldown = 200;
			this.mana = Math.min(this.getMaxMana(), this.mana + workMana);
			sync();
		}
		if(this.cooldown > 0) {
			this.cooldown -= 1;
		}
	}
	
	public int getMaxMana() {
		return 15000;
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
}
