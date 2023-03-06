package ab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
