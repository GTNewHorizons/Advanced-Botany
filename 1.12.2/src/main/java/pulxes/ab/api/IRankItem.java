package pulxes.ab.api;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.mana.IManaItem;

public interface IRankItem extends IManaItem {
	
	public int getLevel(ItemStack stack);
	
	public int[] getLevels();
}

