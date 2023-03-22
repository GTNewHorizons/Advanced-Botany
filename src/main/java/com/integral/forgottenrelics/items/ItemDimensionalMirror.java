package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.common.Thaumcraft;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;

public class ItemDimensionalMirror extends Item {

 public void registerRenderers() {}

 public ItemDimensionalMirror() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemDimensionalMirror");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Dimensional_Mirror");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemDimensionalMirror1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemDimensionalMirror2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemDimensionalMirror3.lore")); 
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		
	 }
	 
	 if (par1ItemStack.hasTagCompound()) {
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
	 par3List.add(StatCollector.translateToLocal("item.MirrorLoc.lore"));
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
	 par3List.add(StatCollector.translateToLocal("item.MirrorX.lore") + ItemNBTHelper.getInt(par1ItemStack, "IStoredX", 0)); 
	 par3List.add(StatCollector.translateToLocal("item.MirrorY.lore") + ItemNBTHelper.getInt(par1ItemStack, "IStoredY", 0)); 
	 par3List.add(StatCollector.translateToLocal("item.MirrorZ.lore") + ItemNBTHelper.getInt(par1ItemStack, "IStoredZ", 0));
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
	 par3List.add(StatCollector.translateToLocal("item.MirrorDimension.lore") + ItemNBTHelper.getInt(par1ItemStack, "IDimensionID", 0)); 
	 }
 }
 
  	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 80;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
        
		boolean effect = stack.hasTagCompound();
		
		if (effect)
			return true;
		else
			return false;
		
    }
	
	@Override
	public boolean isFull3D() {
		return false;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		
		Vector3 vec = Vector3.fromEntityCenter(player);
		
		for (int counter = 0; counter <= 3; counter++)
		player.worldObj.spawnParticle("portal", vec.x, vec.y, vec.z, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3);
		
		if (count == 1) {
			 int dimension = ItemNBTHelper.getInt(stack, "IDimensionID", 0);
			 int x = ItemNBTHelper.getInt(stack, "IStoredX", 0);
			 int y = ItemNBTHelper.getInt(stack, "IStoredY", 0);
			 int z = ItemNBTHelper.getInt(stack, "IStoredZ", 0);
			 
			 SuperpositionHandler.imposeBurst(player.worldObj, player.dimension, vec.x, vec.y, vec.z, 1.25F);
			 
			 if (!player.worldObj.isRemote & player.dimension != dimension) {
				 ((EntityPlayerMP)player).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, dimension);
			 }
				
			 player.setPositionAndUpdate(x+0.5, y+0.5, z+0.5);
				 
			 player.worldObj.playSoundEffect(vec.x, vec.y, vec.z, "mob.endermen.portal", 1.0F, (float) (0.8F + (Math.random() * 0.2)));
			 player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, (float) (0.8F + (Math.random() * 0.2)));
			 
			 for (int counter = 0; counter <= 128; counter++)
				player.worldObj.spawnParticle("portal", player.posX, player.posY-1.0D, player.posZ, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3);
		}
		
	}
 
 @Override
 public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	 	boolean written = false;
	 	
	    if (stack.hasTagCompound())
	        written = true;
	    else
	        written = false;
	    
	 
	 
	 
	 if (written & !player.isSneaking()) {
		 
		 if (!RelicsConfigHandler.interdimensionalMirror & player.dimension != ItemNBTHelper.getInt(stack, "IDimensionID", 0))
			 return stack;
		 
		 player.setItemInUse(stack, stack.getMaxItemUseDuration());
		 
	 } else if (player.isSneaking()) {
		 
			 ItemNBTHelper.setInt(stack, "IStoredX", (int) player.posX);
			 ItemNBTHelper.setInt(stack, "IStoredY", (int) player.posY);
			 ItemNBTHelper.setInt(stack, "IStoredZ", (int) player.posZ);
			 
			 ItemNBTHelper.setInt(stack, "IDimensionID", player.dimension);
			 
			 world.playSoundEffect(player.posX, player.posY, player.posZ, "thaumcraft:jar", 1.0f, 2.0f);
			 
			 player.swingItem();
		 
	 }
		
	 return stack;
 }


 @Override
 public EnumRarity getRarity(ItemStack itemStack)
 {
 return EnumRarity.epic;
 }
 
 
}
