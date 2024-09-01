package ab.common.item.equipment;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.item.ICosmeticAttachable;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.entity.EntityDoppleganger;

public abstract class ItemBauble extends Item implements IBauble, ICosmeticAttachable, IPhantomInkable {

    public ItemBauble(String name) {
        this.setMaxStackSize(1);
        this.setUnlocalizedName(name);
        this.setTextureName("ab:" + name);
        this.setCreativeTab(AdvancedBotany.tabAB);
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!EntityDoppleganger.isTruePlayer((Entity) par3EntityPlayer)) return par1ItemStack;
        if (canEquip(par1ItemStack, (EntityLivingBase) par3EntityPlayer)) {
            InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(par3EntityPlayer);
            for (int i = 0; i < baubles.getSizeInventory(); i++) {
                if (baubles.isItemValidForSlot(i, par1ItemStack)) {
                    ItemStack stackInSlot = baubles.getStackInSlot(i);
                    if (stackInSlot == null || ((IBauble) stackInSlot.getItem())
                            .canUnequip(stackInSlot, (EntityLivingBase) par3EntityPlayer)) {
                        if (!par2World.isRemote) {
                            baubles.setInventorySlotContents(i, par1ItemStack.copy());
                            if (!par3EntityPlayer.capabilities.isCreativeMode) par3EntityPlayer.inventory
                                    .setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
                        }
                        if (stackInSlot != null) {
                            ((IBauble) stackInSlot.getItem())
                                    .onUnequipped(stackInSlot, (EntityLivingBase) par3EntityPlayer);
                            return stackInSlot.copy();
                        }
                        break;
                    }
                }
            }
        }
        return par1ItemStack;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) addHiddenTooltip(par1ItemStack, par2EntityPlayer, par3List, par4);
        else addStringToTooltip(StatCollector.translateToLocal("botaniamisc.shiftinfo"), par3List);
    }

    public void addHiddenTooltip(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List,
            boolean par4) {
        BaubleType type = getBaubleType(par1ItemStack);
        addStringToTooltip(StatCollector.translateToLocal("botania.baubletype." + type.name().toLowerCase()), par3List);
        String key = RenderHelper.getKeyDisplayString("Baubles Inventory");
        if (key != null) addStringToTooltip(
                StatCollector.translateToLocal("botania.baubletooltip").replaceAll("%key%", key),
                par3List);
        ItemStack cosmetic = getCosmeticItem(par1ItemStack);
        if (cosmetic != null) addStringToTooltip(
                String.format(
                        StatCollector.translateToLocal("botaniamisc.hasCosmetic"),
                        new Object[] { cosmetic.getDisplayName() }),
                par3List);
        if (hasPhantomInk(par1ItemStack))
            addStringToTooltip(StatCollector.translateToLocal("botaniamisc.hasPhantomInk"), par3List);
    }

    void addStringToTooltip(String s, List tooltip) {
        tooltip.add(s.replaceAll("&", "\u00A7"));
    }

    public boolean canEquip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (getLastPlayerHashcode(stack) != player.hashCode()) {
            onEquippedOrLoadedIntoWorld(stack, player);
            setLastPlayerHashcode(stack, player.hashCode());
        }
    }

    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        if (player != null) {
            if (!player.worldObj.isRemote)
                player.worldObj.playSoundAtEntity((Entity) player, "botania:equipBauble", 0.1F, 1.3F);
            if (player instanceof EntityPlayer)
                ((EntityPlayer) player).addStat((StatBase) ModAchievements.baubleWear, 1);
            onEquippedOrLoadedIntoWorld(stack, player);
            setLastPlayerHashcode(stack, player.hashCode());
        }
    }

    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {}

    public void onUnequipped(ItemStack stack, EntityLivingBase player) {}

    public ItemStack getCosmeticItem(ItemStack stack) {
        NBTTagCompound cmp = ItemNBTHelper.getCompound(stack, "cosmeticItem", true);
        if (cmp == null) return null;
        return ItemStack.loadItemStackFromNBT(cmp);
    }

    public void setCosmeticItem(ItemStack stack, ItemStack cosmetic) {
        NBTTagCompound cmp = new NBTTagCompound();
        if (cosmetic != null) cosmetic.writeToNBT(cmp);
        ItemNBTHelper.setCompound(stack, "cosmeticItem", cmp);
    }

    public boolean hasContainerItem(ItemStack stack) {
        return (getContainerItem(stack) != null);
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        return getCosmeticItem(itemStack);
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_) {
        return false;
    }

    public static UUID getBaubleUUID(ItemStack stack) {
        long most = ItemNBTHelper.getLong(stack, "baubleUUIDMost", 0L);
        if (most == 0L) {
            UUID uuid = UUID.randomUUID();
            ItemNBTHelper.setLong(stack, "baubleUUIDMost", uuid.getMostSignificantBits());
            ItemNBTHelper.setLong(stack, "baubleUUIDLeast", uuid.getLeastSignificantBits());
            return getBaubleUUID(stack);
        }
        long least = ItemNBTHelper.getLong(stack, "baubleUUIDLeast", 0L);
        return new UUID(most, least);
    }

    public static int getLastPlayerHashcode(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "playerHashcode", 0);
    }

    public static void setLastPlayerHashcode(ItemStack stack, int hash) {
        ItemNBTHelper.setInt(stack, "playerHashcode", hash);
    }

    public boolean hasPhantomInk(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "phantomInk", false);
    }

    public void setPhantomInk(ItemStack stack, boolean ink) {
        ItemNBTHelper.setBoolean(stack, "phantomInk", ink);
    }
}
