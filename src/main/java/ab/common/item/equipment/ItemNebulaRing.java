package ab.common.item.equipment;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import ab.api.AdvancedBotanyAPI;

public class ItemNebulaRing extends ItemMithrillRing {

    public ItemNebulaRing(String name) {
        super(name);
        this.setMaxDamage(1000);
        this.setNoRepair();
    }

    public EnumRarity getRarity(ItemStack stack) {
        return AdvancedBotanyAPI.rarityNebula;
    }

    public int getMaxMana(ItemStack stack) {
        return 48000000;
    }
}
