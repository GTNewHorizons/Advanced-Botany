package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class ItemWeatherStone extends Item {

 public int VisCost = (int) (2500 * RelicsConfigHandler.weatherStoneVisMult);

 public ItemWeatherStone() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemWeatherStone");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Weather_Stone");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemWeatherStone1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemWeatherStone2_1.lore") + " " + (int) (this.VisCost/100) + " " + StatCollector.translateToLocal("item.ItemWeatherStone2_2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemWeatherStone3.lore"));
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		
	 } 
 }
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRaining())
		player.setItemInUse(stack, stack.getMaxItemUseDuration());
		
		return stack;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 60;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		
		Vector3 vec = Vector3.fromEntityCenter(player);
		
		if (count == 1 & player.worldObj.getWorldInfo().isRaining())
		if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, this.VisCost).add(Aspect.EARTH, this.VisCost).add(Aspect.WATER, this.VisCost))) {
			
			for(int i = 0; i <= 24; i++) {
				float r = 0.0F;
				float g = 0.3F + (float) Math.random() * 0.5F;
				float b = 0.8F + (float) Math.random() * 0.2F;
				float s = 0.2F + (float) Math.random() * 0.2F;
				float m = 0.15F;
				float xm = ((float) Math.random() - 0.5F) * m;
				float ym = ((float) Math.random() - 0.5F) * m;
				float zm = ((float) Math.random() - 0.5F) * m;
				
				
				Botania.proxy.wispFX(player.worldObj, vec.x, vec.y, vec.z, r, g, b, s, xm, ym, zm);
			}
			
			player.worldObj.playSoundEffect(vec.x, vec.y, vec.z, "botania:altarCraft", 1.0F, (float) (0.8F + (Math.random() * 0.2F)));
			
			player.worldObj.getWorldInfo().setRaining(false);
			player.worldObj.getWorldInfo().setRainTime(24000 + ((int) (Math.random()*976000)));
		}
	}


 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
 return EnumRarity.epic;
 }
 
 
}
