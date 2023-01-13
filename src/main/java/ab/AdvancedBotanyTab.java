package ab;

import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.ItemListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class AdvancedBotanyTab extends CreativeTabs {

	public AdvancedBotanyTab(String str) {
		super(str);	  		
		this.setNoTitle();
	    this.setBackgroundImageName("ab.png");
	}
	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return ItemBlock.getItemFromBlock(BlockListAB.blockABSpreader);
	}
}