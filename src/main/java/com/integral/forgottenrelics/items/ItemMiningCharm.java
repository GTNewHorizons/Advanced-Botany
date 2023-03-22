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
import vazkii.botania.common.Botania;

public class ItemMiningCharm extends ItemBaubleBase {

 public void registerRenderers() {}

 public ItemMiningCharm() {
	 super("ItemMiningCharm");

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemMiningCharm");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
	 itemIcon = iconRegister.registerIcon("forgottenrelics:Mining_Charm");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemMiningCharm1_1.lore") + " " + (int) (RelicsConfigHandler.miningCharmBoost*100F) + StatCollector.translateToLocal("item.ItemMiningCharm1_2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemMiningCharm2_1.lore") + " " + RelicsConfigHandler.miningCharmReach + StatCollector.translateToLocal("item.ItemMiningCharm2_2.lore"));
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
 return EnumRarity.uncommon;
 }

@Override
public BaubleType getBaubleType(ItemStack arg0) {
	return BaubleType.RING;
}

@Override
public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
	Botania.proxy.setExtraReach((EntityPlayer)player, RelicsConfigHandler.miningCharmReach);
}

@Override
public void onUnequipped(ItemStack stack, EntityLivingBase player) {
	Botania.proxy.setExtraReach((EntityPlayer)player, -RelicsConfigHandler.miningCharmReach);
}
 
 
}
