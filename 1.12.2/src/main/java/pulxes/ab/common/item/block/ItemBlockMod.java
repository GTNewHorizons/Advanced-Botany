package pulxes.ab.common.item.block;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import pulxes.ab.AdvancedBotany;

public class ItemBlockMod extends ItemBlock {

	public ItemBlockMod(Block block) {
		super(block);
	}
	  
	@Nonnull
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
		return getUnlocalizedNameInefficiently_(par1ItemStack).replaceAll("tile.", "tile." + AdvancedBotany.MODID + ":");
	}
	  
	public String getUnlocalizedNameInefficiently_(ItemStack stack) {
		return super.getUnlocalizedNameInefficiently(stack);
	}
}
