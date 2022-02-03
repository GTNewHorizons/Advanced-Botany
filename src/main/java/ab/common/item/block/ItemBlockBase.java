package ab.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlockWithMetadata {

	public ItemBlockBase(Block block) {
		super(block, block);
    }
	    
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + stack.getItemDamage();
	}
}
