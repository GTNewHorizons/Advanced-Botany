package ab.common.item.equipment;

import ab.common.item.ItemMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;

public class ItemNebulaRod extends ItemMod implements IManaUsingItem {

	private static final String TAG_TICKS_COOLDOWN = "ticksCooldown";
	
	public ItemNebulaRod() {
		super("nebulaRod");
	}

	public boolean usesMana(ItemStack stack) {
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}
}
