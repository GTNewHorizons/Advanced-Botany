package ab.common.item.equipment.armor;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import ab.client.model.armor.ModelArmorNebula;
import ab.common.lib.register.ItemListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.armor.manasteel.ItemManasteelArmor;

public class ItemNebulaArmor extends ItemManasteelArmor implements IManaItem, IManaTooltipDisplay {

    protected static final int MAX_MANA = 250000;
    private static final String TAG_MANA = "mana";

    static ItemStack[] armorset;
    public static IIcon nebulaEyes;

    public ItemNebulaArmor(int type, String name) {
        super(type, name, AdvancedBotanyAPI.nebulaArmorMaterial);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setMaxDamage(1000);
        this.setNoRepair();
    }

    public EnumRarity getRarity(ItemStack stack) {
        return AdvancedBotanyAPI.rarityNebula;
    }

    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source,
            double damage, int slot) {
        return new ISpecialArmor.ArmorProperties(
                0,
                getArmorMaterial().getDamageReductionAmount(slot)
                        * (0.03f + (0.0725D * (1.0f - (this.getDamage(armor) / 1000.0f)))),
                2147483647);
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped provideArmorModelForSlot(ItemStack stack, int slot) {
        this.models[slot] = new ModelArmorNebula(slot);
        return this.models[slot];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.itemIcon = ir.registerIcon("ab:" + this.getUnlocalizedName().replaceAll("item\\.", ""));
        this.nebulaEyes = ir.registerIcon("ab:nebulaEyes");
    }

    public String getArmorTextureAfterInk(ItemStack stack, int slot) {
        return "ab:textures/model/nebulaArmor.png";
    }

    public ItemStack[] getArmorSetStacks() {
        if (armorset == null) armorset = new ItemStack[] { new ItemStack(ItemListAB.itemNebulaHelm),
                new ItemStack(ItemListAB.itemNebulaChest), new ItemStack(ItemListAB.itemNebulaLegs),
                new ItemStack(ItemListAB.itemNebulaBoots) };
        return armorset;
    }

    public boolean hasArmorSetItem(EntityPlayer player, int i) {
        ItemStack stack = player.inventory.armorInventory[3 - i];
        if (stack == null) return false;
        switch (i) {
            case 0:
                return (stack.getItem() == ItemListAB.itemNebulaHelm
                        || stack.getItem() == ItemListAB.itemNebulaHelmReveal);
            case 1:
                return (stack.getItem() == ItemListAB.itemNebulaChest);
            case 2:
                return (stack.getItem() == ItemListAB.itemNebulaLegs);
            case 3:
                return (stack.getItem() == ItemListAB.itemNebulaBoots);
        }
        return false;
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        int manaVal = Math.min(damage * 15, this.getMana(stack));
        if (entity instanceof EntityPlayer && !entity.worldObj.isRemote)
            if (!ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) entity, manaVal, true))
                this.addMana(stack, -manaVal);
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if (!world.isRemote && this.getMana(stack) != this.getMaxMana(stack)
                && ManaItemHandler.requestManaExactForTool(stack, player, 1000, true))
            this.addMana(stack, 1000);
    }

    public void addArmorSetDescription(ItemStack stack, List<String> list) {
        addStringToTooltip(StatCollector.translateToLocal("ab.armorset.nebula.desc0"), list);
        addStringToTooltip(StatCollector.translateToLocal("botania.armorset.terrasteel.desc1"), list);
        addStringToTooltip(StatCollector.translateToLocal("botania.armorset.terrasteel.desc2"), list);
    }

    public int getDamage(ItemStack stack) {
        float mana = getMana(stack);
        return 1000 - (int) (mana / getMaxMana(stack) * 1000.0F);
    }

    public int getDisplayDamage(ItemStack stack) {
        return getDamage(stack);
    }

    public static String playerStr(EntityPlayer player) {
        return player.getGameProfile().getName() + ":" + player.worldObj.isRemote;
    }

    public String getArmorSetName() {
        return StatCollector.translateToLocal("ab.armorset.nebula.name");
    }

    public float getManaFractionForDisplay(ItemStack stack) {
        return (float) getMana(stack) / (float) getMaxMana(stack);
    }

    public static void setMana(ItemStack stack, int mana) {
        ItemNBTHelper.setInt(stack, TAG_MANA, mana);
    }

    public void addMana(ItemStack stack, int mana) {
        setMana(stack, Math.min(getMana(stack) + mana, getMaxMana(stack)));
        stack.setItemDamage(getDamage(stack));
    }

    public boolean canExportManaToItem(ItemStack stack, ItemStack stack1) {
        return false;
    }

    public boolean canExportManaToPool(ItemStack arg0, TileEntity arg1) {
        return false;
    }

    public boolean canReceiveManaFromItem(ItemStack arg0, ItemStack arg1) {
        return true;
    }

    public boolean canReceiveManaFromPool(ItemStack arg0, TileEntity arg1) {
        return true;
    }

    public int getMana(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
    }

    public int getMaxMana(ItemStack arg0) {
        return MAX_MANA;
    }

    public boolean isNoExport(ItemStack arg0) {
        return true;
    }
}
