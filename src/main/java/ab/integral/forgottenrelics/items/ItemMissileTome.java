package com.integral.forgottenrelics.items;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.Botania;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityRageousMissile;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This code is heavily based on such of Rod of Unstable Reservoir from Botania, and is generally a direct upgrade of
 * it. I assume that such an unusual weapon mechanic should take practically useful form, and since original item
 * definitely were not powerful enough to make things happen, I had a honor of creating this relic.
 * 
 * @author Integral
 */

public class ItemMissileTome extends Item implements IWarpingGear {

    public static final int AerCost = (int) (0 * RelicsConfigHandler.nuclearFuryVisMult);
    public static final int TerraCost = (int) (0 * RelicsConfigHandler.nuclearFuryVisMult);
    public static final int IgnisCost = (int) (20 * RelicsConfigHandler.nuclearFuryVisMult);
    public static final int AquaCost = (int) (0 * RelicsConfigHandler.nuclearFuryVisMult);
    public static final int OrdoCost = (int) (10 * RelicsConfigHandler.nuclearFuryVisMult);
    public static final int PerditioCost = (int) (15 * RelicsConfigHandler.nuclearFuryVisMult);

    public ItemMissileTome() {
        super();
        setMaxStackSize(1);
        setUnlocalizedName("ItemMissileTome");
        setCreativeTab(Main.tabForgottenRelics);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.ItemMissileTome1.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemMissileTome2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemMissileTome3.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemMissileTome4.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemMissileTome5.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemMissileTome6.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(
                    StatCollector.translateToLocal("item.ItemMissileTome7_1.lore") + " "
                            + (int) RelicsConfigHandler.nuclearFuryDamageMIN
                            + "-"
                            + (int) RelicsConfigHandler.nuclearFuryDamageMAX
                            + " "
                            + StatCollector.translateToLocal("item.ItemMissileTome7_2.lore"));
        } else if (GuiScreen.isCtrlKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.FRVisPerSecond.lore"));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (this.IgnisCost / 100.0D * 10.0D));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FROrdoCost.lore") + (this.OrdoCost / 100.0D * 10.0D));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRPerditioCost.lore")
                            + (this.PerditioCost / 100.0D) * 10.0D);
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));
            par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
        }

        par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Missile_Tome");
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (count != getMaxItemUseDuration(stack) && count % 2 == 0 && !player.worldObj.isRemote) {

            if (WandManager.consumeVisFromInventory(
                    player,
                    new AspectList().add(Aspect.FIRE, this.IgnisCost).add(Aspect.ORDER, this.OrdoCost)
                            .add(Aspect.ENTROPY, this.PerditioCost)))
                spawnMissile(
                        player.worldObj,
                        player,
                        player.posX + ((Math.random() - 0.5) * 3.1),
                        player.posY + 3.8 + (Math.random() - 0.5 * 3.1),
                        player.posZ + ((Math.random() - 0.5) * 3.1));
            Botania.proxy.sparkleFX(player.worldObj, player.posX, player.posY + 2.4, player.posZ, 1F, 0.4F, 1F, 6F, 6);
        }
    }

    public boolean spawnMissile(World world, EntityPlayer thrower, double x, double y, double z) {
        EntityRageousMissile missile;
        if (thrower != null) {
            missile = new EntityRageousMissile(thrower, false);

            missile.setPosition(x, y, z);
            if (!world.isRemote) {
                world.playSoundEffect(x, y, z, "botania:missile", 0.6F, 0.8F + (float) Math.random() * 0.2F);
                world.spawnEntityInWorld(missile);

                Thaumcraft.proxy.burst(thrower.worldObj, x, y, z, 0.25F);

                return true;
            }

        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    @Override
    public boolean isFull3D() {
        return false;
    }

    @Override
    public int getWarp(ItemStack arg0, EntityPlayer arg1) {
        return 5;
    }
}
