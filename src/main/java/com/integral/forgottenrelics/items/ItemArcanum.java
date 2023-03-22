package com.integral.forgottenrelics.items;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArcanum extends ItemBaubleBase implements IBauble, IVisDiscountGear {

    public void registerRenderers() {}

    public ItemArcanum() {
        super("ItemArcanum");
        this.maxStackSize = 1;
        this.setUnlocalizedName("ItemArcanum");
        this.setCreativeTab(Main.tabForgottenRelics);

    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Nebulous_Core");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(
                StatCollector.translateToLocal("item.ItemArcanum1.lore") + " "
                        + this.getVisDiscount(par1ItemStack, par2EntityPlayer, Aspect.ENTROPY)
                        + "%");
        par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));

        if (GuiScreen.isShiftKeyDown()) {

            par3List.add(StatCollector.translateToLocal("item.ItemArcanum2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemArcanum3.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemArcanum4.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemArcanum5.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemArcanum6.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemArcanum7.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemArcanum8.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemArcanum9.lore"));
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

        if (entity instanceof EntityPlayer & !entity.worldObj.isRemote
                & Math.random() <= (0.025 * RelicsConfigHandler.arcanumGenRate)) {

            List<ItemStack> wandList = SuperpositionHandler.wandSearch((EntityPlayer) entity);
            List<Aspect> primalAspects = Aspect.getPrimalAspects();

            if (wandList.size() > 0) {

                Aspect randomAspect = primalAspects.get((int) (Math.random() * 5));

                ItemStack randomWand = SuperpositionHandler.getRandomValidWand(wandList, randomAspect);

                if (randomWand != null)
                    ((ItemWandCasting) randomWand.getItem()).addVis(randomWand, randomAspect, 1, true);

            }

        }

        else if (Math.random() <= 0.000208 & !entity.worldObj.isRemote) {

            for (int counter = 0; counter <= 32; counter++) {
                if (SuperpositionHandler.validTeleportRandomly(entity, entity.worldObj, 32)) {
                    break;
                }
            }

        }

        else if (Math.random() <= 0.000027 & !entity.worldObj.isRemote & entity instanceof EntityPlayer) {

            ItemStack replacedStack = new ItemStack(Main.itemDormantArcanum);
            ItemNBTHelper.setInt(
                    replacedStack,
                    "ILifetime",
                    (int) ((1200 + (Math.random() * 6000)) * RelicsConfigHandler.dormantArcanumVisMult));

            InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(((EntityPlayer) entity));
            baubles.setInventorySlotContents(0, replacedStack);

        }

    }

    @Override
    public int getVisDiscount(ItemStack arg0, EntityPlayer arg1, Aspect arg2) {
        return 35;
    }

}
