package ab.common.item.equipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNebulaRing extends ItemMithrillRing {

	public ItemNebulaRing(String name) {
		super(name);
		this.setMaxDamage(1000);
		this.setNoRepair();
	} 
	
	public int getMaxMana(ItemStack stack) {
		return 48000000;
	}	
}
