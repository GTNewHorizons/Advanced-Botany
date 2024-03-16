package ab.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.recipe.IFlowerComponent;

public class ItemABResource extends Item implements IFlowerComponent {

    private IIcon[] icons = new IIcon[7];

    public ItemABResource() {
        this.setHasSubtypes(true);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setUnlocalizedName("resourceAB");
    }

    public void registerIcons(IIconRegister ir) {
        for (int i = 0; i < icons.length; i++) {
            icons[i] = ir.registerIcon("ab:resourceAB_" + i);
        }
    }

    public EnumRarity getRarity(ItemStack stack) {
        if (stack.getItemDamage() == 5 || stack.getItemDamage() == 6) return AdvancedBotanyAPI.rarityNebula;
        return super.getRarity(stack);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.getItemDamage() == 2;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < icons.length; i++) {
            list.add(new ItemStack((Item) this, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return icons[Math.min(icons.length - 1, par1)];
    }

    public boolean canFit(ItemStack stack, IInventory apothecary) {
        int meta = stack.getItemDamage();
        return (meta == 3 || meta == 2 || meta == 4);
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return "item.resourceAB_" + stack.getItemDamage();
    }

    public int getParticleColor(ItemStack stack) {
        return 10158080;
    }

    public boolean isElvenItem(ItemStack stack) {
        return false;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() != 3) return super.onItemRightClick(stack, world, player);
        player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() != 3) return super.onEaten(stack, world, player);
        else if (!world.isRemote) player.addPotionEffect(new PotionEffect(Potion.confusion.id, 120, 3));
        ItemStack bottle = new ItemStack(Items.glass_bottle);
        if (!player.inventory.addItemStackToInventory(bottle)) player.dropPlayerItemWithRandomChoice(bottle, false);
        --stack.stackSize;
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return stack.getItemDamage() != 3 ? super.getMaxItemUseDuration(stack) : 24;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return stack.getItemDamage() != 3 ? super.getItemUseAction(stack) : EnumAction.drink;
    }
}
