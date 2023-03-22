package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;

public class ItemChaosCore extends Item implements IWarpingGear {

 public void registerRenderers() {}

 public ItemChaosCore() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemChaosCore");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Chaos_Core");
 }
 
 	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {
 		if (!world.isRemote & Math.random() <= 0.000208) {
	    	int randomizedPotionID = 1 + (int)(Math.random() * 21);
	    	if (randomizedPotionID == 6 || randomizedPotionID == 7) {
	    		randomizedPotionID = 20;
	    	}
	    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(randomizedPotionID, 100 + (int)(Math.random() * 2400), (int)(Math.random() * 3), false));
	    }
	}


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemChaosCore1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemChaosCore2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemChaosCore3.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemChaosCore4.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemChaosCore5.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemChaosCore6.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemChaosCore7.lore"));
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		
	 }
	 
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
 }


 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
 return EnumRarity.epic;
 }

@Override
public int getWarp(ItemStack arg0, EntityPlayer arg1) {
	return 2;
}
 
 
}
