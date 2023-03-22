package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityLunarFlare;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.RelicsEventHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.core.helper.Vector3;

public class ItemLunarFlares extends Item implements IWarpingGear {

	 public static final int AerCost = (int) (35*RelicsConfigHandler.lunarFlaresVisMult);
	 public static final int TerraCost = (int) (0*RelicsConfigHandler.lunarFlaresVisMult);
	 public static final int IgnisCost = (int) (50*RelicsConfigHandler.lunarFlaresVisMult);
	 public static final int AquaCost = (int) (0*RelicsConfigHandler.lunarFlaresVisMult);
	 public static final int OrdoCost = (int) (65*RelicsConfigHandler.lunarFlaresVisMult);
	 public static final int PerditioCost = (int) (0*RelicsConfigHandler.lunarFlaresVisMult);

 public ItemLunarFlares() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemLunarFlares");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Lunar_Flares");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemLunarFlares1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemLunarFlares2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemLunarFlares3_1.lore") + " " + (int) RelicsConfigHandler.damageLunarFlareImpact + " " + StatCollector.translateToLocal("item.ItemLunarFlares3_2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemLunarFlares35.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemLunarFlares4_1.lore") + " " + (int) RelicsConfigHandler.damageLunarFlareDirect + " " + StatCollector.translateToLocal("item.ItemLunarFlares4_2.lore")); 
		 
	 } else if (GuiScreen.isCtrlKeyDown()) {
			par3List.add(StatCollector.translateToLocal("item.FRVisPerSecond.lore"));
		 	par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + (this.AerCost/100.0D)*10.0D);
		 	par3List.add(" " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (this.IgnisCost/100.0D)*10.0D);
		 	par3List.add(" " + StatCollector.translateToLocal("item.FROrdoCost.lore") + (this.OrdoCost/100.0D)*10.0D);
		 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
	 }
	 
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
 }
 
 public void spawnLunarFlare(World world, EntityPlayer player, MovingObjectPosition mop) {
	 
	 if (mop != null & !world.isRemote) {
	 EntityLunarFlare flare = new EntityLunarFlare(world, player, mop.blockX, mop.blockY, mop.blockZ);
	 flare.setPosition(mop.blockX+((Math.random() - 0.5D)*12), mop.blockY+24+((Math.random() - 0.5D)*12), mop.blockZ+0.5+((Math.random() - 0.5D)*12));
	 
	 Vector3 posVec = new Vector3(mop.blockX+0.5, mop.blockY, mop.blockZ+0.5);
	 Vector3 motVec = new Vector3((Math.random() - 0.5) * 18, (24+(Math.random() - 0.5) * 18)*2.0D, (Math.random() - 0.5) * 18);
	 posVec.add(motVec);
	 motVec.normalize().negate().multiply(4.0);
	 	
	 	flare.setPosition(posVec.x, posVec.y, posVec.z);
	 	flare.motionX = motVec.x;
	 	flare.motionY = motVec.y;
	 	flare.motionZ = motVec.z;
	 
	 	world.spawnEntityInWorld(flare);
	 
	 }
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
 public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	 player.setItemInUse(stack, stack.getMaxItemUseDuration());
	 
	 return stack;
 }
 
 @Override
 public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		if(count != getMaxItemUseDuration(stack) && count % 2 == 0 && !player.worldObj.isRemote) {
			MovingObjectPosition mop = SuperpositionHandler.getPointedBlock(player, player.worldObj, 128);
			
			if (mop != null)
			if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, this.AerCost).add(Aspect.FIRE, this.IgnisCost).add(Aspect.ORDER, this.OrdoCost))) {
				this.spawnLunarFlare(player.worldObj, player, mop);
				if(count != getMaxItemUseDuration(stack) && count % 4 == 0 && !player.worldObj.isRemote)
					player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "ForgottenRelics:sound.starfall", 2.0F, (float) (1.0F + (Math.random()* 0.5F)));
			}
			
			
			
		}
		
 }


 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
 return EnumRarity.epic;
 }

@Override
public int getWarp(ItemStack arg0, EntityPlayer arg1) {
	return 3;
}
 
 
}
