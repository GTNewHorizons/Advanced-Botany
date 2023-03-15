package ab.common.item;

import net.minecraft.item.Item;

import ab.AdvancedBotany;

public class ItemMod extends Item {

    public ItemMod(String name) {
        this.setTextureName("ab:" + name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(AdvancedBotany.tabAB);
    }
}
