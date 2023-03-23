package com.integral.forgottenrelics.items;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import baubles.api.BaubleType;
import baubles.api.IBauble;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeificAmulet extends ItemBaubleBase implements IBauble {

    public void registerRenderers() {}

    public ItemDeificAmulet() {
        super("ItemDeificAmulet");
        this.maxStackSize = 1;
        this.setUnlocalizedName("ItemDeificAmulet");
        this.setCreativeTab(Main.tabForgottenRelics);

    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Deific_Amulet");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            if (RelicsConfigHandler.deificAmuletEffectImmunity)
                par3List.add(StatCollector.translateToLocal("item.ItemDeificAmulet1.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemDeificAmulet2.lore"));
            if (RelicsConfigHandler.deificAmuletInvincibility)
                par3List.add(StatCollector.translateToLocal("item.ItemDeificAmulet3.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemDeificAmulet4.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemDeificAmulet5.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));

        }
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
        super.onWornTick(itemstack, entity);

        if (!entity.worldObj.isRemote & entity instanceof EntityPlayer) {

            if (!(((EntityLivingBase) entity).getActivePotionEffects() == null)
                    & RelicsConfigHandler.deificAmuletEffectImmunity) {
                ((EntityLivingBase) entity).clearActivePotions();
            }

            if (entity.isBurning()) {
                entity.extinguish();
            }

            if (entity.getAir() == 0) {

                if (WandManager.consumeVisFromInventory(
                        (EntityPlayer) entity,
                        new AspectList().add(Aspect.AIR, (int) (1000 * RelicsConfigHandler.deificAmuletVisMult))
                                .add(Aspect.WATER, (int) (1000 * RelicsConfigHandler.deificAmuletVisMult)))) {
                    entity.setAir(300);
                }

            }

            if (RelicsConfigHandler.deificAmuletInvincibility) {

                if (ItemNBTHelper.getInt(itemstack, "ICooldown", 0) == 0 & entity.hurtResistantTime > 10) {
                    entity.hurtResistantTime = 40;
                    ItemNBTHelper.setInt(itemstack, "ICooldown", 32);
                }

                if (ItemNBTHelper.getInt(itemstack, "ICooldown", 0) > 0) {
                    ItemNBTHelper.setInt(itemstack, "ICooldown", ItemNBTHelper.getInt(itemstack, "ICooldown", 0) - 1);
                }

            }
        }

    }

}
