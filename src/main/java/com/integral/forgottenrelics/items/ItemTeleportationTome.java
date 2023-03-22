package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.PortalTraceMessage;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;
import com.integral.forgottenrelics.proxy.ClientProxy;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
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
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.WandManager;
import thaumcraft.common.lib.utils.EntityUtils;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;

public class ItemTeleportationTome extends Item implements IWarpingGear, IRepairable {

	 public static final int AerCost = (int) (160*RelicsConfigHandler.discordTomeVisMult);
	 public static final int TerraCost = (int) (0*RelicsConfigHandler.discordTomeVisMult);
	 public static final int IgnisCost = (int) (0*RelicsConfigHandler.discordTomeVisMult);
	 public static final int AquaCost = (int) (0*RelicsConfigHandler.discordTomeVisMult);
	 public static final int OrdoCost = (int) (240*RelicsConfigHandler.discordTomeVisMult);
	 public static final int PerditioCost = (int) (240*RelicsConfigHandler.discordTomeVisMult);

 public ItemTeleportationTome() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemTeleportationTome");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Teleportation_Tome");
 }
 
 @Override
 public void onUpdate(ItemStack stack, World world, Entity par3Entity, int p_77663_4_, boolean p_77663_5_) {
	 
	 if (ItemNBTHelper.getInt(stack, "ICooldown", 0) > 0)
		 ItemNBTHelper.setInt(stack, "ICooldown", ItemNBTHelper.getInt(stack, "ICooldown", 0) - 1);
	 
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemTeleportationTome1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemTeleportationTome2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemTeleportationTome3.lore"));
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemTeleportationTome4.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemTeleportationTome5.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemTeleportationTome6.lore"));
	 } else if (GuiScreen.isCtrlKeyDown()) {
		 par3List.add(StatCollector.translateToLocal("item.FRVisPerCast.lore"));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + (this.AerCost/100.0D));
		 par3List.add(" " + StatCollector.translateToLocal("item.FROrdoCost.lore") + (this.OrdoCost/100.0D));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + (this.PerditioCost/100.0D));
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
	 }
	 
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
 }
 
 @Override
 public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	 
	 if (ItemNBTHelper.getInt(stack, "ICooldown", 0) != 0)
		 return stack;
	 
	 if (true) {
	 
	 Entity pointedEntity = EntityUtils.getPointedEntity(world, player, 0.0D, 128.0D, 4F);
	 
	 if (player.isSneaking() & WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, this.AerCost).add(Aspect.ORDER, this.OrdoCost).add(Aspect.ENTROPY, this.PerditioCost))) {
		 
		 if (!world.isRemote) {
				Container inventory = player.inventoryContainer;
				((EntityPlayerMP) player).sendContainerToPlayer(inventory);
			 }
		 
		 SuperpositionHandler.imposeBurst(world, player.dimension, player.posX, player.posY+1, player.posZ, 1.25f);
			
		 Vector3 primalVec = Vector3.fromEntityCenter(player);
		 
		 primalVec.y -= 0.5;
		 
		 Vector3 lookVec = primalVec.copy().add(new Vector3(player.getLookVec()).multiply(16.0F));
			
		 //double SX = player.motionX;
		 //double SY = player.motionY;
		 //double SZ = player.motionZ;
		 
		 world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
		 if (!world.isRemote)
		 player.setPositionAndUpdate(lookVec.x, lookVec.y, lookVec.z);
		 world.playSoundEffect(lookVec.x, lookVec.y, lookVec.z, "mob.endermen.portal", 1.0F, 1.0F);
		 
		 //player.motionX = SX;
		 //player.motionY = SY;
		 //player.motionZ = SZ;
			
		 Vector3 finalVec = Vector3.fromEntityCenter(player);
		 finalVec.y -= 0.5;
		 
		 if (!world.isRemote)
		 Main.packetInstance.sendToAllAround(new PortalTraceMessage(primalVec.x, primalVec.y, primalVec.z, finalVec.x, finalVec.y, finalVec.z, player.getDistance(primalVec.x, primalVec.y, primalVec.z)), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 128.0D));
		 
		 ItemNBTHelper.setInt(stack, "ICooldown", 20);
		 
		 return stack;
		 
	 } else if (pointedEntity != null & pointedEntity instanceof EntityLivingBase & WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, this.AerCost).add(Aspect.ORDER, this.OrdoCost).add(Aspect.ENTROPY, this.PerditioCost))) {
		 
		 if (!world.isRemote) {
				Container inventory = player.inventoryContainer;
				((EntityPlayerMP) player).sendContainerToPlayer(inventory);
			 } 
		 
		 if (!world.isRemote)
		 SuperpositionHandler.imposeBurst(world, player.dimension, player.posX, player.posY+1, player.posZ, 1.25f);
				
		 Vector3 primalVec = Vector3.fromEntityCenter(player);
		 Vector3 finalVec = Vector3.fromEntityCenter(pointedEntity);
			
		 if (!world.isRemote)
		 player.setPositionAndUpdate(finalVec.x, finalVec.y, finalVec.z);
		 
		 ((EntityLivingBase) pointedEntity).setPositionAndUpdate(primalVec.x, primalVec.y, primalVec.z);
		 
		 world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
		 world.playSoundEffect(pointedEntity.posX, pointedEntity.posY, pointedEntity.posZ, "mob.endermen.portal", 1.0F, 1.0F);
		
		 if (!world.isRemote)
		 Main.packetInstance.sendToAllAround(new PortalTraceMessage(primalVec.x, primalVec.y, primalVec.z, finalVec.x, finalVec.y, finalVec.z, player.getDistance(primalVec.x, primalVec.y, primalVec.z)), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 128.0D));
		 
		 ItemNBTHelper.setInt(stack, "ICooldown", 20);
		 
		 return stack;
		 
	 } else {
		 
		 MovingObjectPosition pointed = SuperpositionHandler.getPointedBlock(player, world, 128.0F);
		 
		 if (pointed != null & WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, this.AerCost).add(Aspect.ORDER, this.OrdoCost).add(Aspect.ENTROPY, this.PerditioCost))) {
			 
			 if (!world.isRemote) {
					Container inventory = player.inventoryContainer;
					((EntityPlayerMP) player).sendContainerToPlayer(inventory);
				 }
			 
			 int x = pointed.blockX;
			 int y = pointed.blockY;
			 int z = pointed.blockZ;
			 
			 for (int counter = 0; counter <= 32; counter++) {
					
					if (!world.isAirBlock(x, y+counter-1, z) & world.getBlock(x, y+counter-1, z).isCollidable() & world.isAirBlock(x, y+counter, z) & world.isAirBlock(x, y+counter+1, z)) {
						
						if (!world.isRemote)
						SuperpositionHandler.imposeBurst(world, player.dimension, player.posX, player.posY+1, player.posZ, 1.25f);
						
						Vector3 primalVec = Vector3.fromEntityCenter(player);
						
						world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
						
						if (!world.isRemote)
						player.setPositionAndUpdate(x+0.5, y+counter, z+0.5);
						
						world.playSoundEffect(x, y+counter, z, "mob.endermen.portal", 1.0F, 1.0F);
						
						
						Vector3 finalVec = Vector3.fromEntityCenter(player);
						
						if (!world.isRemote)
						Main.packetInstance.sendToAllAround(new PortalTraceMessage(primalVec.x, primalVec.y, primalVec.z, finalVec.x, finalVec.y, finalVec.z, player.getDistance(primalVec.x, primalVec.y, primalVec.z)), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 128.0D));
						
						ItemNBTHelper.setInt(stack, "ICooldown", 20);
						
						return stack;
					}
					
				}
			 
		 }
		 
	 }
	 
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
	return 2;
}
 
 
}
