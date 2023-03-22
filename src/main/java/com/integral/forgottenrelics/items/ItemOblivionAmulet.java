package com.integral.forgottenrelics.items;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

import thaumcraft.api.IWarpingGear;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import baubles.api.BaubleType;
import baubles.api.IBauble;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.DamageRegistryHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOblivionAmulet extends ItemBaubleBase implements IBauble, IWarpingGear {

    public void registerRenderers() {}

    public ItemOblivionAmulet() {
        super("ItemOblivionAmulet");
        this.maxStackSize = 1;
        this.setUnlocalizedName("ItemOblivionAmulet");
        this.setCreativeTab(Main.tabForgottenRelics);

    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Oblivion_Amulet");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.ItemOblivionAmulet1.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemOblivionAmulet2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemOblivionAmulet3.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemOblivionAmulet4.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemOblivionAmulet5.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));

        }
        par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));

        if (par1ItemStack.hasTagCompound()) {

            par3List.add(
                    StatCollector.translateToLocal("item.ItemOblivionAmuletDamage.lore") + " "
                            + Math.round(par1ItemStack.getTagCompound().getFloat("IDamageStored") * 100.0) / 100.0);
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
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

        if (!entity.worldObj.isRemote & itemstack.hasTagCompound() & Math.random() <= 0.0008) {

            if (ItemNBTHelper.getInt(itemstack, "IDamageStored", 0) > 0) {

                float getDamage = (float) (ItemNBTHelper.getFloat(itemstack, "IDamageStored", 0) * Math.random());

                if (getDamage > 100 & Math.random() <= 0.9) {
                    getDamage = (float) (100 * Math.random());
                }

                ItemNBTHelper.setFloat(
                        itemstack,
                        "IDamageStored",
                        ItemNBTHelper.getFloat(itemstack, "IDamageStored", 0F) - getDamage);

                entity.attackEntityFrom(new DamageRegistryHandler.DamageSourceOblivion(), getDamage);

            }

        } else if (!entity.worldObj.isRemote & Math.random() <= 0.0004) {
            double omega = Math.random();
            int PotionID = 0;

            if (omega <= 0.25) PotionID = 4;
            else if (omega <= 0.5) PotionID = 15;
            else if (omega <= 0.75) PotionID = 18;
            else PotionID = 20;

            entity.addPotionEffect(
                    new PotionEffect(PotionID, (int) (100 + Math.random() * 2000), (int) (Math.random() * 3), true));

        }

    }

    @Override
    public int getWarp(ItemStack arg0, EntityPlayer arg1) {
        return 4;
    }

}
