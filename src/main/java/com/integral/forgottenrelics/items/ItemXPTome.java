package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import vazkii.botania.common.core.helper.ExperienceHelper;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemXPTome extends Item {

 public static final String TAG_ABSORPTION = "AbsorptionMode";
 public static final int xpPortion = 5;

 public ItemXPTome() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemXPTome");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }
 


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
	 itemIcon = iconRegister.registerIcon("forgottenrelics:XP_Tome");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
	 
	 String cMode;
	 if (!ItemNBTHelper.getBoolean(par1ItemStack, "IsActive", false))
		 cMode = StatCollector.translateToLocal("item.ItemXPTomeDeactivated.lore");
	 else if (ItemNBTHelper.getBoolean(par1ItemStack, this.TAG_ABSORPTION, true))
		 cMode = StatCollector.translateToLocal("item.ItemXPTomeAbsorption.lore");
	 else
		 cMode = StatCollector.translateToLocal("item.ItemXPTomeExtraction.lore");
	 
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome3.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome4.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome5.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome6.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome7.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome8.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemXPTome9.lore"));
		 
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		
	 }
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
	 par3List.add(StatCollector.translateToLocal("item.ItemXPTomeMode.lore") + " " + cMode);
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
	 par3List.add(StatCollector.translateToLocal("item.ItemXPTomeExp.lore"));
	 par3List.add(StatCollector.translateToLocal("item.FRCode6.lore") + ItemNBTHelper.getInt(par1ItemStack, "XPStored", 0) + " " + StatCollector.translateToLocal("item.ItemXPTomeUnits.lore") + " " + ExperienceHelper.getLevelForExperience(ItemNBTHelper.getInt(par1ItemStack, "XPStored", 0)) + " " + StatCollector.translateToLocal("item.ItemXPTomeLevels.lore"));
	 
 }
 
 @Override
 public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {
	 
	 if (!(entity instanceof EntityPlayer) || world.isRemote || !ItemNBTHelper.getBoolean(itemstack, "IsActive", false))
		 return;
	 
	 boolean action = false;
	 
	 EntityPlayer player = (EntityPlayer) entity;
	 
	 if (ItemNBTHelper.getBoolean(itemstack, this.TAG_ABSORPTION, true)) {
		 
		 if (ExperienceHelper.getPlayerXP(player) >= this.xpPortion) {
		 ExperienceHelper.drainPlayerXP(player, this.xpPortion);
		 ItemNBTHelper.setInt(itemstack, "XPStored", ItemNBTHelper.getInt(itemstack, "XPStored", 0) + this.xpPortion);
		 action = true;
		 }
		 else if (ExperienceHelper.getPlayerXP(player) > 0 & ExperienceHelper.getPlayerXP(player) < this.xpPortion) {
		 int exp = ExperienceHelper.getPlayerXP(player);
		 ExperienceHelper.drainPlayerXP(player, exp);
		 ItemNBTHelper.setInt(itemstack, "XPStored", ItemNBTHelper.getInt(itemstack, "XPStored", 0) + exp);
		 action = true;
		 }
		
	 
	 } else {
		 
		 int xp = ItemNBTHelper.getInt(itemstack, "XPStored", 0);
		 
			if (xp >= this.xpPortion) {
				ItemNBTHelper.setInt(itemstack, "XPStored", xp-this.xpPortion);
				ExperienceHelper.addPlayerXP(player, this.xpPortion);	
				action = true;
			} else if (xp > 0 & xp < this.xpPortion) {
				ItemNBTHelper.setInt(itemstack, "XPStored", 0);
				ExperienceHelper.addPlayerXP(player, xp);
				action = true;
			}
		 
	 }
	 
	 if (action)
	 player.inventoryContainer.detectAndSendChanges();
	 
 }
 
 	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	 	
	 	if (!player.isSneaking()) {
	 		
	 		if (ItemNBTHelper.getBoolean(stack, this.TAG_ABSORPTION, true)) {
	 		ItemNBTHelper.setBoolean(stack, this.TAG_ABSORPTION, false);
	 		world.playSoundAtEntity(player, "random.levelup", 1.0F, (float) (0.4F + (Math.random() * 0.1F)));
	 		}
	 		else { 
	 		ItemNBTHelper.setBoolean(stack, this.TAG_ABSORPTION, true);
	 		world.playSoundAtEntity(player, "random.levelup", 1.0F, (float) (0.4F + (Math.random() * 0.1F)));
	 		}
	 	} else {
	 		
	 		if (ItemNBTHelper.getBoolean(stack, "IsActive", false)) {
	 			ItemNBTHelper.setBoolean(stack, "IsActive", false);
	 			world.playSoundAtEntity(player, "thaumcraft:hhoff", 1.0F, (float) (0.8F + (Math.random() * 0.2F)));
	 		}
	 		else {
	 			ItemNBTHelper.setBoolean(stack, "IsActive", true);
	 			world.playSoundAtEntity(player, "thaumcraft:hhon", 1.0F, (float) (0.8F + (Math.random() * 0.2F)));
	 		}
	 	}
	 	
	 	
	 	
	 	player.swingItem();
	 	
	 	
		
		return stack;
	}
	
	@Override
	public boolean isFull3D() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
        
		boolean effect = ItemNBTHelper.getBoolean(stack, "IsActive", false);
		
		if (effect)
			return true;
		else
			return false;
		
    }


 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
 return EnumRarity.epic;
 }
 
 
}
