package com.integral.forgottenrelics.items;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemParadox extends ItemSword implements IRepairable, IWarpingGear {

    public ItemParadox(ToolMaterial m) {
        super(m);
        this.setCreativeTab(Main.tabForgottenRelics);
        this.setTextureName("forgottenrelics:Paradox");
        this.setUnlocalizedName("ItemParadox");
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

        double currentDamage = Math.random() * RelicsConfigHandler.paradoxDamageCap;

        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) currentDamage);
        player.attackEntityFrom(
                DamageSource.causePlayerDamage(player),
                (float) (RelicsConfigHandler.paradoxDamageCap - currentDamage));

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.ItemParadox1.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemParadox2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemParadox3.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemParadox4.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemParadox5.lore"));
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));

        }
        par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
        par3List.add(
                StatCollector.translateToLocal("item.ItemParadoxDamage_1.lore")
                        + (int) RelicsConfigHandler.paradoxDamageCap
                        + StatCollector.translateToLocal("item.ItemParadoxDamage_2.lore"));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.epic;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
        if ((!world.isRemote) && (stack.isItemDamaged()) && (entity.ticksExisted % 20 == 0)) {
            stack.damageItem(-1, (EntityLivingBase) entity);
        }

    }

    @Override
    public int getWarp(ItemStack arg0, EntityPlayer arg1) {
        return 8;
    }
}
