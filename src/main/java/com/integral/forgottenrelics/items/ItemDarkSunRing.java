package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import baubles.api.BaubleType;
import baubles.api.IBauble;
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
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;

public class ItemDarkSunRing extends ItemBaubleBase implements IBauble {

 public void registerRenderers() {}

 public ItemDarkSunRing() {
	 super("ItemDarkSunRing");
	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemDarkSunRing");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
	 itemIcon = iconRegister.registerIcon("forgottenrelics:Dark_Sun_Ring");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing2_1.lore") + " " + (int) RelicsConfigHandler.darkSunRingDamageCap + StatCollector.translateToLocal("item.ItemDarkSunRing2_2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing3.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing4_1.lore") + " " + (int) (RelicsConfigHandler.darkSunRingDeflectChance*100F) + StatCollector.translateToLocal("item.ItemDarkSunRing4_2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing5.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing6.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing7.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing8.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemDarkSunRing9.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		
	 }
 }

 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
	 return EnumRarity.epic;
 }

@Override
public BaubleType getBaubleType(ItemStack arg0) {
	return BaubleType.RING;
}

@Override
public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
	super.onWornTick(itemstack, entity);
	if (entity.isBurning()) {
		entity.extinguish();
	}
	
}
 
 
}
