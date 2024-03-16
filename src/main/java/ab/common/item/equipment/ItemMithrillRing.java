package ab.common.item.equipment;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import baubles.api.BaubleType;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemMithrillRing extends ItemBauble implements IManaItem, IManaTooltipDisplay {

    public ItemMithrillRing(String name) {
        super(name);
        this.setMaxDamage(1000);
        this.setNoRepair();
    }

    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.RING;
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 10000));
    }

    public int getDamage(ItemStack stack) {
        float mana = getMana(stack);
        return 1000 - (int) (mana / getMaxMana(stack) * 1000.0F);
    }

    public int getDisplayDamage(ItemStack stack) {
        return getDamage(stack);
    }

    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    public static void setMana(ItemStack stack, int mana) {
        ItemNBTHelper.setInt(stack, "mana", mana);
    }

    public int getMana(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "mana", 0);
    }

    public int getMaxMana(ItemStack stack) {
        return 8000000;
    }

    public void addMana(ItemStack stack, int mana) {
        setMana(stack, Math.min(getMana(stack) + mana, getMaxMana(stack)));
        stack.setItemDamage(getDamage(stack));
    }

    public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
        return true;
    }

    public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
        return true;
    }

    public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
        return true;
    }

    public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
        return true;
    }

    public boolean isNoExport(ItemStack stack) {
        return false;
    }

    public float getManaFractionForDisplay(ItemStack stack) {
        return getMana(stack) / getMaxMana(stack);
    }
}
