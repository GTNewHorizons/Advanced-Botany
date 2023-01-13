package ab.common.item;

import ab.AdvancedBotany;
import net.minecraft.item.Item;

public class ItemMod extends Item {

	public ItemMod(String name) {
		this.setTextureName("ab:" + name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(AdvancedBotany.tabAB);
	}
}
