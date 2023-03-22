package com.integral.forgottenrelics.items;

import java.util.LinkedList;
import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemDormantArcanum extends ItemBaubleBase implements IBauble {

 public void registerRenderers() {}

 public ItemDormantArcanum() {
	 super("ItemDormantArcanum");
	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemDormantArcanum");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Nebulous_Core_Dormant");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 
	 if(GuiScreen.isShiftKeyDown()){
		  
		 par3List.add(StatCollector.translateToLocal("item.ItemDormantArcanum1.lore")); 
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		
	 }
	 
	 if (par1ItemStack.hasTagCompound()) {
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FRCode6.lore") + ((ItemNBTHelper.getInt(par1ItemStack, "ILifetime", 0)*2) / 100.0D) + StatCollector.translateToLocal("item.ItemDormantArcanum2.lore"));
	 
	 }
 }


 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
 return EnumRarity.epic;
 }

@Override
public BaubleType getBaubleType(ItemStack arg0) {
	return BaubleType.AMULET;
}

@Override
public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
	super.onWornTick(itemstack, entity);
	
	if (itemstack.hasTagCompound() & !entity.worldObj.isRemote & entity instanceof EntityPlayer) {
		
		if (ItemNBTHelper.getInt(itemstack, "ILifetime", 0) > 0 ) {
			
			if (WandManager.consumeVisFromInventory((EntityPlayer) entity, new AspectList().add(Aspect.AIR, 3).add(Aspect.FIRE, 3).add(Aspect.EARTH, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.ENTROPY, 3))) {
			ItemNBTHelper.setInt(itemstack, "ILifetime", ItemNBTHelper.getInt(itemstack, "ILifetime", 0) - 1);
		}
			
		} else {
			InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(((EntityPlayer)entity));
			baubles.setInventorySlotContents(0, new ItemStack(Main.itemArcanum));
		}
		
	}
	
}
 
 
}
