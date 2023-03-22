package com.integral.forgottenrelics.items;

import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityAIProjectileBase;
import com.integral.forgottenrelics.entities.EntityCrimsonOrb;
import com.integral.forgottenrelics.entities.EntityDarkMatterOrb;
import com.integral.forgottenrelics.entities.EntitySoulEnergy;
import com.integral.forgottenrelics.handlers.DamageRegistryHandler;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.WandManager;
import thaumcraft.common.lib.utils.EntityUtils;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class ItemSoulTome extends Item implements IWarpingGear, IRepairable {

	 public static final int AerCost = (int) (20*RelicsConfigHandler.soulTomeVisMult);
	 public static final int TerraCost = (int) (25*RelicsConfigHandler.soulTomeVisMult);
	 public static final int IgnisCost = (int) (35*RelicsConfigHandler.soulTomeVisMult);
	 public static final int AquaCost = (int) (0*RelicsConfigHandler.soulTomeVisMult);
	 public static final int OrdoCost = (int) (0*RelicsConfigHandler.soulTomeVisMult);
	 public static final int PerditioCost = (int) (50*RelicsConfigHandler.soulTomeVisMult);

 public ItemSoulTome() {

	 this.maxStackSize = 1;
	 this.setUnlocalizedName("ItemSoulTome");
	 this.setCreativeTab(Main.tabForgottenRelics);

 }


 @Override
 public void registerIcons(IIconRegister iconRegister)
 {
 itemIcon = iconRegister.registerIcon("forgottenrelics:Soul_Tome");
 }


 @Override
 @SideOnly(Side.CLIENT)
 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
 {
	 if(GuiScreen.isShiftKeyDown()){
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome1.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome2.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome3.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome4.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome5.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome6.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome7.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome8.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
		 par3List.add(StatCollector.translateToLocal("item.ItemSoulTome9.lore")); 
	 } else if (GuiScreen.isCtrlKeyDown()) {
		 par3List.add(StatCollector.translateToLocal("item.FRVisPerSecond.lore"));
		 par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + (this.AerCost/100.0D)*10.0D);
		 par3List.add(" " + StatCollector.translateToLocal("item.FRTerraCost.lore") + (this.TerraCost/100.0D)*10.0D);
		 par3List.add(" " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (this.IgnisCost/100.0D)*10.0D);
		 par3List.add(" " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + (this.PerditioCost/100.0D)*10.0D);
	 }
	 else {
		 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		 par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
	 }
	 
	 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
 }
 
 	public boolean spawnSoul(World world, EntityLivingBase player, EntityLivingBase target) {
		
		if (!world.isRemote) {
			
			Vector3 originalPos = Vector3.fromEntityCenter(player);
			Vector3 vector = originalPos.add(new Vector3(player.getLookVec()).multiply(1.0F));
			vector.y += 0.5;
			
			Vector3 motion = new Vector3(player.getLookVec()).multiply(1.25F);
			
			EntitySoulEnergy orb = new EntitySoulEnergy(world, player, target, false);
			orb.setPosition(vector.x, vector.y, vector.z);
			orb.motionX = motion.x;
			orb.motionY = motion.y;
			orb.motionZ = motion.z;
			
			world.playSoundEffect(player.posX, player.posY, player.posZ, "botania:missile", 2.0F, 0.8F + (float) Math.random() * 0.2F);
			world.spawnEntityInWorld(orb);
			
			return true;
		}
			
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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		
		return stack;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		
		player.motionX = 0;
		player.motionZ = 0;
		
		int searchRange = 20;
		List <EntityLivingBase> entities = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(player.posX - searchRange, player.posY - searchRange, player.posZ - searchRange, player.posX + searchRange, player.posY + searchRange, player.posZ + searchRange));
		
		if (entities.contains(player))
			entities.remove(player);
		
		if (count < (getMaxItemUseDuration(stack)-20))
		for (int counter = entities.size()-1; counter >= 0; counter--) {
			if (player.getDistanceToEntity(entities.get(counter)) <= 3)
				if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.FIRE, (int) (150*RelicsConfigHandler.soulTomeVisMult)).add(Aspect.ENTROPY, (int) (120*RelicsConfigHandler.soulTomeVisMult)))) {
				
				EntityLivingBase thrownEntity = entities.get(counter);
				
				Vector3 entityVec = Vector3.fromEntityCenter(thrownEntity);
				Vector3 playerVec = Vector3.fromEntityCenter(player);
				
				Vector3 diff = (entityVec.copy().sub(playerVec)).multiply((1/player.getDistanceToEntity(thrownEntity))*3.0F);
				
				float curve = (float) ((1/player.getDistance(entityVec.x, entityVec.y, entityVec.z)) * 8.0F);
				
				if (!player.worldObj.isRemote)
				for (int counterZ = 0; counterZ <= 3; counterZ++)
				Main.proxy.lightning(player.worldObj, player.posX, player.posY+1.0, player.posZ, entityVec.x, entityVec.y, entityVec.z, 40, curve, (int) (player.getDistance(entityVec.x, entityVec.y, entityVec.z)*6.0F), 0, 0.075F);
				
				player.worldObj.playSoundAtEntity(player, "thaumcraft:zap", 1F, 0.8F);
				
				thrownEntity.attackEntityFrom(new DamageRegistryHandler.DamageSourceTLightning(player), (float) (20 + (80 * Math.random())));
				
				thrownEntity.motionX = diff.x;
				thrownEntity.motionY = diff.y+1.0F;
				thrownEntity.motionZ = diff.z;
				
			}
		}
		
		/*
		int range = 4;
		for(int i = 0; i < 360; i += 16) {
	        
			//float r = (float) (0.9F + (Math.random() * 0.1F));
			//float g = (float) (0.1F + (Math.random() * 0.2F));
			//float b = 0.0F;
			
			float r = -1.0F;
			float g = 0.8F + (float) Math.random() * 0.2F;
			float b = 0.4F + (float) Math.random() * 0.6F;
			
			float m = 0.15F;
			float mv = 0.35F;

			float rad = i * (float) Math.PI / 180F;
			double x = player.posX - Math.cos(rad) * range;
			double y = player.posY;
			double z = player.posZ - Math.sin(rad) * range;
			
			Thaumcraft.proxy.blockRunes(player.worldObj, x-0.5, y, z-0.5, r, g, b, 20, (float) ((Math.random() - 0.5D)*0.1));
			//Botania.proxy.wispFX(player.worldObj, x, y, z, r, g, b, 0.5F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * mv, (float) (Math.random() - 0.5F) * m);
		}*/
		
		if (count < (getMaxItemUseDuration(stack)-20) & count % 4 == 0)
		if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.EARTH, this.TerraCost).add(Aspect.AIR, this.AerCost).add(Aspect.FIRE, this.IgnisCost).add(Aspect.ENTROPY, this.PerditioCost))) {
			
			EntityLivingBase randomEntity = null;
			
			if (entities.size() > 0)
				randomEntity = entities.get((int) (entities.size() * Math.random()));
			
			if (randomEntity != null & !player.worldObj.isRemote) {
				
				float soulDamage = randomEntity.getMaxHealth()/RelicsConfigHandler.soulTomeDivisor;
				
				if (soulDamage > 20)
					soulDamage = 20.0F;
				else if (soulDamage < 1)
					soulDamage = 1.0F;
				
				randomEntity.attackEntityFrom(new DamageRegistryHandler.DamageSourceSoulDrain(player), soulDamage);
				spawnSoul(player.worldObj, randomEntity, player);
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
