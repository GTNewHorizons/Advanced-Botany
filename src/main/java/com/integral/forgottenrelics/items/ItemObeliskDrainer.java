package com.integral.forgottenrelics.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;
import thaumcraft.common.tiles.TileEldritchObelisk;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;

public class ItemObeliskDrainer extends Item implements IWarpingGear, IRepairable {

 public void registerRenderers() {}

 public ItemObeliskDrainer() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemObeliskDrainer");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
	 itemIcon = iconRegister.registerIcon("forgottenrelics:Obelisk_Drainer");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemObeliskDrainer1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemObeliskDrainer2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemObeliskDrainer3.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemObeliskDrainer4.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemObeliskDrainer5.lore")); 
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		
	 }
	 
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
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
	 
	 double x = ItemNBTHelper.getDouble(stack, "IDetectedX", 0);
	 double y = ItemNBTHelper.getDouble(stack, "IDetectedY", 0);
	 double z = ItemNBTHelper.getDouble(stack, "IDetectedZ", 0);
	 
	 if (!(player.worldObj.getTileEntity((int)x, (int)y, (int)z) instanceof TileEldritchObelisk) || player.getDistance(x, y+2.5, z) > 16) {
		 player.setItemInUse(stack, 0);
		 return;
	 }
	 
	 if (count % 30 == 0 & count != this.getMaxItemUseDuration(stack) & !player.worldObj.isRemote) {
		 
		 Vector3 thisVec = Vector3.fromEntityCenter(player);
		 Vector3 targetVec = Vector3.fromTileEntityCenter(player.worldObj.getTileEntity((int)x, (int)y, (int)z));
		 Vector3 diffVec = targetVec.copy().sub(thisVec);
		 Vector3 motionVec = thisVec.add(diffVec.copy().multiply((1/player.getDistance(x, y+2.5, z))*0.5));
		 
		 float curve = (float) ((1/player.getDistance(x, y+2.5, z)) * (2.0F + ((Math.random()) * 4.0F)));
		 if (player.getDistance(x, y+2.5, z) <= 3)
			curve = (float) ((1/player.getDistance(x, y+2.5, z)) * 8.0F);
		 
		 for (int counter = 0; counter <= 3; counter++)
		 SuperpositionHandler.imposeLightning(player.worldObj, player.dimension, x+0.5, y+2.75, z+0.5, motionVec.x, motionVec.y, motionVec.z, 20, curve, (int) (player.getDistance(x, y+2.5, z)*1.6F), 0, 0.075F);
		 
		 player.worldObj.playSoundAtEntity(player, "thaumcraft:zap", 1F, 0.8F);
		 
		 player.attackEntityFrom(DamageSource.generic, 0.01F);
		 player.heal(4);
		 player.getFoodStats().addStats(2, 1.0F);
		 List primals = Aspect.getPrimalAspects();
		 Aspect randomAspect = (Aspect) primals.get((int) (Math.random()*(primals.size())));
		 ItemStack randomWand = SuperpositionHandler.getRandomValidWand(SuperpositionHandler.wandSearch(player), randomAspect);
		 
		 if (randomWand != null)
				((ItemWandCasting)randomWand.getItem()).addVis(randomWand, randomAspect, (int) ((5+(Math.random()*15))*RelicsConfigHandler.obeliskDrainerVisMult), true);
		 
		 
	 }
	 
	 if (!player.worldObj.isRemote & Math.random() <= 0.175) {
		 float h = 0.0F;
		 if (Math.random() > 0.5)
			 h = 0.4F;
		 else
			 h = -0.4F;
		 //Main.proxy.lightning(player.worldObj, x+0.5, y+3.0, z+0.5, x+(Math.random() - 0.5D)*4, y+(Math.random() - 0.5D)*4, z+(Math.random() - 0.5D)*4, 20, 0.5F, 10, 0, 0.075F);
		 SuperpositionHandler.imposeArcLightning(player.worldObj, player.dimension, x+0.5, y+2.5+((Math.random() - 0.5D)*2), z+0.5, x+0.5+((Math.random() - 0.5D)*4), y+2.5+((Math.random() - 0.5D)*4), z+0.5+((Math.random() - 0.5D)*4), 1F, 0.6F, 1F, h);
	 }
	 
	 	
	 
	 if (player != null & player.worldObj.isRemote) {
		 
	 Main.proxy.wispFX4(player.worldObj, x + 0.5, (double)(y + 1 + player.worldObj.rand.nextFloat() * 3.0f), z + 0.5, player, 5, true, 1.0f);
	 for (int counterrr = 0; counterrr <= 4; counterrr++)
		 player.worldObj.spawnParticle("portal", x+0.5, y+2.5+((Math.random() - 0.5D)*2), z+0.5, ((Math.random() - 0.5D)*3), ((Math.random() - 0.5D)*0.3), ((Math.random() - 0.5D)*3));
	 
	 }
 }
 
 @Override
 public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	 
	 List loadedTiles = new ArrayList(world.loadedTileEntityList);
	 
	 TileEldritchObelisk requiredObelisk = null;
	 
	 for (int counter = 0; counter <= loadedTiles.size()-1; counter++) {
		 if (!(loadedTiles.get(counter) instanceof TileEldritchObelisk)) {
			 loadedTiles.remove(counter);
			 counter = -1;
			 continue;
		 }
	 }
	 
	 for (int counter = 0; counter <= loadedTiles.size()-1; counter++) {
		 TileEldritchObelisk checkedObelisk = (TileEldritchObelisk) loadedTiles.get(counter);
		 
		 if (player.getDistance(checkedObelisk.xCoord, checkedObelisk.yCoord, checkedObelisk.zCoord) > 16.0D || checkedObelisk.getWorldObj().provider.dimensionId != player.dimension) {
			 loadedTiles.remove(counter);
			 counter = -1;
			 continue;
		 } else {
			 requiredObelisk = checkedObelisk;
			 break;
		 }
		 
	 }
	 
	 if (requiredObelisk != null) {
		 
		 ItemNBTHelper.setDouble(stack, "IDetectedX", requiredObelisk.xCoord);
		 ItemNBTHelper.setDouble(stack, "IDetectedY", requiredObelisk.yCoord);
		 ItemNBTHelper.setDouble(stack, "IDetectedZ", requiredObelisk.zCoord);
		 
		 player.setItemInUse(stack, getMaxItemUseDuration(stack));
	 }
	 
	 return stack;	
 }


 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
 return EnumRarity.epic;
 }

@Override
public int getWarp(ItemStack arg0, EntityPlayer arg1) {
	
	return 4;
}
 
 
}
