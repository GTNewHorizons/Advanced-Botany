package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityBabylonWeaponSS;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.core.helper.Vector3;

public class ItemApotheosis extends Item implements IWarpingGear {

 public static final int AerCost = (int) (0*RelicsConfigHandler.apotheosisVisMult);
 public static final int TerraCost = (int) (30*RelicsConfigHandler.apotheosisVisMult);
 public static final int IgnisCost = (int) (60*RelicsConfigHandler.apotheosisVisMult);
 public static final int AquaCost = (int) (0*RelicsConfigHandler.apotheosisVisMult);
 public static final int OrdoCost = (int) (50*RelicsConfigHandler.apotheosisVisMult);
 public static final int PerditioCost = (int) (75*RelicsConfigHandler.apotheosisVisMult);
	
 public ItemApotheosis() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemApotheosis");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Apotheosis");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemApotheosis1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemApotheosis2.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemApotheosis3.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemApotheosis4_1.lore") + " " + (int) RelicsConfigHandler.damageApotheosisDirect + " " + StatCollector.translateToLocal("item.ItemApotheosis4_2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemApotheosis5_1.lore") + " " + (int) RelicsConfigHandler.damageApotheosisImpact + " " + StatCollector.translateToLocal("item.ItemApotheosis5_2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemApotheosis6.lore")); 
	 } else if (GuiScreen.isCtrlKeyDown()) {
		 par3List.add(StatCollector.translateToLocal("item.FRVisPerSecond.lore"));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRTerraCost.lore") + (this.TerraCost/100.0D)*10.0D);
		 par3List.add(" " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (this.IgnisCost/100.0D)*10.0D);
		 par3List.add(" " + StatCollector.translateToLocal("item.FROrdoCost.lore") + (this.OrdoCost/100.0D)*10.0D);
		 par3List.add(" " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + (this.PerditioCost/100.0D)*10.0D);
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
	 }
	 
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
 }
 
 public void spawnBabylonWeapon(EntityPlayer player) {
	 	if (!player.worldObj.isRemote) {
	 	Vector3 originalPosX = Vector3.fromEntityCenter(player);
	 	EntityBabylonWeaponSS weapon = new EntityBabylonWeaponSS(player.worldObj, player);

	 	double rawYaw = player.getRotationYawHead();		
		double yaw;
		
		if (rawYaw < 0)
			yaw = Math.abs(rawYaw);
		else
			yaw = 360 - rawYaw;
		
		double x = 0;
		double z = 0;
		
		if (yaw >= 0 & yaw <= 90) {

			double m = ((yaw-0)/90);

			z = 1.0 - m;
			x = m;

			} else if (yaw >= 90 & yaw <= 180) {

			double m = ((yaw-90)/90);

			x = 1.0 - m;
			z = -m;

			} else if (yaw >= 180 & yaw <= 270) {

			double m = ((yaw-180)/90);

			z = -(1.0 - m);
			x = -m;

			} else if (yaw >= 270 & yaw <= 360) {

			double m = ((yaw-270)/90);

			x = -(1.0 - m);
			z = m;

		}
		
		double multV2 = yaw % 90;
		
		if (multV2 < 45) {
			// DO NOTHING
		} else if (multV2 > 45)
			multV2 = 45 - (multV2 - 45);
		
		double multV3 = 1.0D + (multV2/90);
		
		Vector3 lookV = new Vector3(x*multV3, 0, z*multV3);
		Vector3 additive = lookV.copy();
		
		for (int supercounter = 0; supercounter <= 100; supercounter++) {
			
		Vector3 finalVec = lookV.copy();
		
		double negative;
		
		if (Math.random() >= 0.5)
			negative = -1.0D;
		else
			negative = 1.0D;
		
		finalVec.rotate(80.0D * negative, new Vector3(0, 1, 0));
		
		finalVec.multiply(2.0F + (Math.random() * 10.0F));
		finalVec.add(additive.copy().multiply(Math.random() * 1.0));
		
		finalVec.y += -0.5 + (Math.random()*8);
		
		finalVec.add(originalPosX.copy());
		
		double range = 2.0D;
		List <EntityBabylonWeaponSS> weapons = player.worldObj.getEntitiesWithinAABB(EntityBabylonWeaponSS.class, AxisAlignedBB.getBoundingBox(finalVec.x-range, finalVec.y-range, finalVec.z-range, finalVec.x+range, finalVec.y+range, finalVec.z+range));
		
		if (weapons.size() > 0)
			continue;
		else {
			lookV = finalVec;
			break;
		}
		}
		
		weapon.posX = lookV.x;
		weapon.posY = lookV.y;
		weapon.posZ = lookV.z;
		weapon.rotationYaw = player.rotationYawHead;
		weapon.setVariety(itemRand.nextInt(12));
		weapon.setDelay(0);
		weapon.setRotation(MathHelper.wrapAngleTo180_float(-player.rotationYawHead + 180));

		player.worldObj.spawnEntityInWorld(weapon);
		player.worldObj.playSoundAtEntity(weapon, "botania:babylonSpawn", 1F, 1F + player.worldObj.rand.nextFloat() * 3F);
	 	}
 	}
 
 	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
 		player.setItemInUse(stack, stack.getMaxItemUseDuration());
 		
		return stack;
	}
 	
 	@Override
	public boolean isFull3D() {
		return false;
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
		
		if (count != stack.getMaxItemUseDuration() & count % 2 == 0 & !player.worldObj.isRemote) {
			
			if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.EARTH, this.TerraCost).add(Aspect.FIRE, this.IgnisCost).add(Aspect.ORDER, this.OrdoCost).add(Aspect.ENTROPY, this.PerditioCost))) {
				this.spawnBabylonWeapon(player);
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
	return 5;
}
 
 
}
