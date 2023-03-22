package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemOmegaCore extends Item {

 public void registerRenderers() {}

 public ItemOmegaCore() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemOmegaCore");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Omega_Core");
 }
 
 @Override
 public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {
	
	 if (!world.isRemote & entity instanceof EntityPlayer) {
		 EntityPlayer player = (EntityPlayer) entity;
		 
		 List<ItemStack> wandList = SuperpositionHandler.wandSearch((EntityPlayer) entity);
		 List<Aspect> primalAspects = Aspect.getPrimalAspects();
			
		 if (wandList.size() > 0) {
			 
		 for (int counter = 0; counter < primalAspects.size(); counter ++) {
		 
		 ItemStack randomWand = SuperpositionHandler.getRandomValidWand(wandList, primalAspects.get(counter));
			
		 if (randomWand != null)
		 ((ItemWandCasting)randomWand.getItem()).addVis(randomWand, primalAspects.get(counter), 1, true);
			
		}
		
		}
		 
	 }
	 
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()) {
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
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
 
 
}
