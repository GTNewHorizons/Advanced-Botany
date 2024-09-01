package ab.common.item.equipment.armor;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import ab.client.model.armor.ModelArmorWildHunt;
import ab.common.lib.register.ItemListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.item.equipment.armor.manasteel.ItemManasteelArmor;

public class ItemWildHuntArmor extends ItemManasteelArmor {

    static ItemStack[] armorset;

    public ItemWildHuntArmor(int type, String name) {
        super(type, name, AdvancedBotanyAPI.wildHundArmor);
        this.setCreativeTab(AdvancedBotany.tabAB);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return AdvancedBotanyAPI.rarityWildHunt;
    }

    public ItemStack[] getArmorSetStacks() {
        if (armorset == null) armorset = new ItemStack[] { new ItemStack(ItemListAB.itemWildHuntHelm),
                new ItemStack(ItemListAB.itemWildHuntChest), new ItemStack(ItemListAB.itemWildHuntLegs),
                new ItemStack(ItemListAB.itemWildHuntBoots) };
        return armorset;
    }

    public boolean hasArmorSetItem(EntityPlayer player, int i) {
        ItemStack stack = player.inventory.armorInventory[3 - i];
        if (stack == null) return false;
        switch (i) {
            case 0:
                return (stack.getItem() == ItemListAB.itemWildHuntHelm);
            case 1:
                return (stack.getItem() == ItemListAB.itemWildHuntChest);
            case 2:
                return (stack.getItem() == ItemListAB.itemWildHuntLegs);
            case 3:
                return (stack.getItem() == ItemListAB.itemWildHuntBoots);
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped provideArmorModelForSlot(ItemStack stack, int slot) {
        this.models[slot] = new ModelArmorWildHunt(slot);
        return this.models[slot];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.itemIcon = ir.registerIcon("ab:" + this.getUnlocalizedName().replaceAll("item\\.", ""));
    }

    public String getArmorTextureAfterInk(ItemStack stack, int slot) {
        return "ab:textures/model/wildHuntArmor.png";
    }

    public void addArmorSetDescription(ItemStack stack, List<String> list) {
        addStringToTooltip(StatCollector.translateToLocal("ab.armorset.wildHunt.desc0"), list);
        addStringToTooltip(StatCollector.translateToLocal("ab.armorset.wildHunt.desc1"), list);
        addStringToTooltip(StatCollector.translateToLocal("ab.armorset.wildHunt.desc2"), list);
    }
}
