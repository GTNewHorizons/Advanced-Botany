package pulxes.ab.common.core;

import javax.annotation.Nonnull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import pulxes.ab.AdvancedBotany;

public class AdvancedBotanyTab extends CreativeTabs {

	public AdvancedBotanyTab() {
		super(AdvancedBotany.MODID);
		setNoTitle();
		setBackgroundImageName("ab.png");
	}

	@Nonnull
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.SHEARS);
	}
}