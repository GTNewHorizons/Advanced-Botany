package com.integral.forgottenrelics.items;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.common.Mod.EventHandler;
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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemFateTome extends Item implements IWarpingGear {

	public static final int AerCost = (int) (10000*RelicsConfigHandler.fateTomeVisMult);
	public static final int TerraCost = (int) (10000*RelicsConfigHandler.fateTomeVisMult);
	public static final int IgnisCost = (int) (10000*RelicsConfigHandler.fateTomeVisMult);
	public static final int AquaCost = (int) (10000*RelicsConfigHandler.fateTomeVisMult);
	public static final int OrdoCost = (int) (10000*RelicsConfigHandler.fateTomeVisMult);
	public static final int PerditioCost = (int) (10000*RelicsConfigHandler.fateTomeVisMult);

 public ItemFateTome() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemFateTome");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
	 itemIcon = iconRegister.registerIcon("forgottenrelics:Tome_of_Broken_Fates");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome3.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome4.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome5.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome6.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome7.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome8.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemFateTome9.lore")); 
	 } else if (GuiScreen.isCtrlKeyDown()) {
		 par3List.add(StatCollector.translateToLocal("item.FRVisPerCast.lore"));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + (this.AerCost/100.0D));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRTerraCost.lore") + (this.TerraCost/100.0D));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (this.IgnisCost/100.0D));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRAquaCost.lore") + (this.AquaCost/100.0D));
		 par3List.add(" " + StatCollector.translateToLocal("item.FROrdoCost.lore") + (this.OrdoCost/100.0D));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + (this.PerditioCost/100.0D));
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore")); 
	 }
	 
	 if (par1ItemStack.hasTagCompound())
		 if (ItemNBTHelper.verifyExistance(par1ItemStack, "IFateCooldown"))
		 	if (ItemNBTHelper.getInt(par1ItemStack, "IFateCooldown", 0) > 0) {
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FRCode" + (int) ((Math.random()*15) + 1) + ".lore") + StatCollector.translateToLocal("item.ItemFateTomeCooldown.lore") + " " + new BigDecimal((ItemNBTHelper.getInt(par1ItemStack, "IFateCooldown", 0) / 20.0D)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + " " + StatCollector.translateToLocal("item.FRSeconds.lore")); 
		 }
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
 }
 
 
 
 
 	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {
 			
 			if (!(entity instanceof EntityPlayer))
 				return;
 		
			if (!itemstack.hasTagCompound() & !world.isRemote) {
				    
				    ItemNBTHelper.setInt(itemstack, "IFateID", (int) (Math.random() * Integer.MAX_VALUE));
				    ItemNBTHelper.setInt(itemstack, "IFateCooldown", 0);				    
				    
			} else if (!world.isRemote) {
				
				if (ItemNBTHelper.verifyExistance(itemstack, "IFateCooldown")) {
					if (ItemNBTHelper.getInt(itemstack, "IFateCooldown", 0) > 0) {
						ItemNBTHelper.setInt(itemstack, "IFateCooldown", ItemNBTHelper.getInt(itemstack, "IFateCooldown", 0) - 1);
						((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
					}
				}
				
			}
			
			if ((Math.random() <= 0.000016) & (!world.isRemote))
			if (entity instanceof EntityPlayer) {
				
				EntityPlayer player = ((EntityPlayer) entity);
				
				if (SuperpositionHandler.itemSearch(player, Main.itemFateTome).size() > 1) {
					SuperpositionHandler.insanelyDisastrousConsequences(player);
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
	return 7;
}
 
 
}
