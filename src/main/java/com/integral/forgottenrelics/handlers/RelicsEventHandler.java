package com.integral.forgottenrelics.handlers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.items.ItemFateTome;

import baubles.api.BaubleType;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class RelicsEventHandler {
	
	@SubscribeEvent
	public void miningStuff(PlayerEvent.BreakSpeed event) {
		
		/*
		 * Handler for calculating mining speed boost from wearing Mining Charms.
		 */
		
		float miningBoost = 1.0F;
		
		if (SuperpositionHandler.hasBauble(event.entityPlayer, Main.itemAdvancedMiningCharm)) {
			miningBoost = miningBoost + RelicsConfigHandler.advancedMiningCharmBoost;
		}
		
		if (SuperpositionHandler.hasBauble(event.entityPlayer, Main.itemMiningCharm)) {
			miningBoost += RelicsConfigHandler.miningCharmBoost;
		}
		
		event.newSpeed *= miningBoost;
		
		
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onEntityAttacked(LivingAttackEvent event) {
		
		/*
		 * Handler for redirecting damage dealt BY player who possesses Chaos Core.
		 */
		if (event.source.getEntity() instanceof EntityPlayer & !event.isCanceled()) {
			EntityPlayer attackerPlayer = (EntityPlayer) event.source.getEntity();
			
			if (attackerPlayer.inventory.hasItem(Main.itemChaosCore) & Math.random() < 0.45) {
				List<Entity> entityList = event.entity.worldObj.getEntitiesWithinAABBExcludingEntity(event.entity, AxisAlignedBB.getBoundingBox(event.entity.posX-16, event.entity.posY-16, event.entity.posZ-16, event.entity.posX+16, event.entity.posY+16, event.entity.posZ+16));
	 			
	 			if (!(entityList == null) & entityList.size() > 0) {
	 			Entity randomEntity = entityList.get((int) (Math.random() * (entityList.size() - 1)));
	 				
	 			float redirectedAmount = event.ammount * ((float) (Math.random() * 2));
	 			
	 			if (Math.random() < 0.15) {
	 				attackerPlayer.attackEntityFrom(event.source, redirectedAmount);
	 			} else {
	 				randomEntity.attackEntityFrom(event.source, redirectedAmount);
	 			}
	 			
	 			event.setCanceled(true);
	 			}
			}
			
		}
		
		if (event.entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entity;
			
			/*
			 * Handler for redirecting damage dealt TO player who possesses Chaos Core.
			 */
			
			if (!event.isCanceled() & player.inventory.hasItem(Main.itemChaosCore) & Math.random() < 0.42D) {
				
				List<Entity> entityList = event.entity.worldObj.getEntitiesWithinAABBExcludingEntity(event.entity, AxisAlignedBB.getBoundingBox(event.entity.posX-16, event.entity.posY-16, event.entity.posZ-16, event.entity.posX+16, event.entity.posY+16, event.entity.posZ+16));
	 			
	 			if (!(entityList == null) & entityList.size() > 0) {
	 			Entity randomEntity = entityList.get((int) (Math.random() * (entityList.size() - 1)));
	 			
	 			//if (event.entity.hurtResistantTime == 0) {
	 				
	 			float redirectedAmount = event.ammount * ((float) (Math.random() * 2));
	 			
	 			randomEntity.attackEntityFrom(event.source, redirectedAmount);
	 			
	 			event.setCanceled(true);
	 			}
				
			}
			
			/*
			 * Handler for randomly teleport player who has Nebulous Core equipped,
			 * instead of taking damage from attack.
			 */
			
			if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, Main.itemArcanum) & Math.random() < RelicsConfigHandler.nebulousCoreDodgeChance & !SuperpositionHandler.isDamageTypeAbsolute(event.source)) {
					
					for (int counter = 0; counter <= 32; counter ++) {
						if (SuperpositionHandler.validTeleportRandomly(event.entity, event.entity.worldObj, 16)) {
							event.entity.hurtResistantTime = 20;
							event.setCanceled(true);
							break;
						}
					}
			}
			
			/*
			 * Handler for converting fire damage into healing for Ring of The Seven Suns.
			 */
			
			if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, Main.itemDarkSunRing) & Main.darkRingDamageNegations.contains(event.source.damageType)) {
				
				if (RelicsConfigHandler.darkSunRingHealLimit) {
					if (event.entity.hurtResistantTime == 0) {
						player.heal(event.ammount);
						event.entity.hurtResistantTime = 20;
					}
				} else {
					player.heal(event.ammount);
				}
				
				event.setCanceled(true);
			
			}
			
			/*
			 * Handler for deflecting incoming attack to it's source for Ring of The Seven Suns.
			 */
			
			else if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, Main.itemDarkSunRing) & event.source.getEntity() != null & Math.random() <= RelicsConfigHandler.darkSunRingDeflectChance) {
				
				if (player.hurtResistantTime == 0) {
				player.hurtResistantTime = 20;
				event.source.getEntity().attackEntityFrom(event.source, event.ammount);
				event.setCanceled(true);
				}
				
			}
			
		}
		
	}
	
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event) {
		
		/*
		 * Handler for converting damage dealt TO owners of False Justice.
		 */
		
		if (event.entity instanceof EntityPlayer & !event.isCanceled()) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
		if (player.inventory.hasItem(Main.itemFalseJustice) & !SuperpositionHandler.isDamageTypeAbsolute(event.source)) {
			event.setCanceled(true);
			if (event.source.getEntity() == null) {
			player.attackEntityFrom(new DamageRegistryHandler.DamageSourceTrueDamageUndef(), event.ammount*2);
			}
			else {
			player.attackEntityFrom(new DamageRegistryHandler.DamageSourceTrueDamage(event.source.getEntity()), event.ammount*2);
			}
		}
		
		}
		
		/*
		 * Hanlder for converting damage dealt BY owners of False Justice.
		 */
		
		else if (event.source.getEntity() instanceof EntityPlayer & !event.isCanceled()) {
			EntityPlayer attackerPlayer = (EntityPlayer) event.source.getEntity();
			
			if (attackerPlayer.inventory.hasItem(Main.itemFalseJustice) & !SuperpositionHandler.isDamageTypeAbsolute(event.source)) {
				event.setCanceled(true);
				event.entity.attackEntityFrom(new DamageRegistryHandler.DamageSourceTrueDamage(event.source.getEntity()), event.ammount*2);
			}
		}
		
		
		
		if (event.entity instanceof EntityPlayer & !event.isCanceled()) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			/*
			 * Handler for multiplication of damage dealt to owners of Chaos Core by value in range
			 * between 0.0 and 2.0.
			 */
			
			if (!event.isCanceled() & player.inventory.hasItem(Main.itemChaosCore)) {
				event.ammount = event.ammount * ((float) (Math.random() * 2));
			}
			
			/*
			 * Handler for blocking attacks that exceed damage cap for Ring of The Seven Suns.
			 */
			
			if (!event.isCanceled() & event.ammount > 100.0F & SuperpositionHandler.hasBauble(player, Main.itemDarkSunRing)) {
				event.setCanceled(true);
			}
			
			/*
			 * Handler for increasing strenght of regular attacks on wearers of Ring of The Seven Suns.
			 */
			
			if (SuperpositionHandler.hasBauble(player, Main.itemDarkSunRing) & !event.isCanceled() & Math.random() <= 0.25 & !SuperpositionHandler.isDamageTypeAbsolute(event.source)) {
				event.ammount = event.ammount + (event.ammount * ((float) Math.random()));
			}
			
			/*
			 * Handler for redirecting damage received by player to owner of Ancient Aegis
			 * nearby, if one is present.
			 */
			
			if(!event.entity.worldObj.isRemote & !SuperpositionHandler.hasBauble(player, Main.itemAncientAegis) & !event.isCanceled()) {
			EntityPlayer aegisOwner = SuperpositionHandler.findPlayerWithBauble(event.entity.worldObj, 32, Main.itemAncientAegis, player);
			
			if (aegisOwner != null) {
				aegisOwner.attackEntityFrom(event.source, event.ammount * 0.4F);
				event.ammount *= 0.6F;
			}
			}
			
			/*
			 * Handler for Ancient Aegis damage reduction.
			 */
			
			if (SuperpositionHandler.hasBauble(player, Main.itemAncientAegis) & !event.isCanceled() & !SuperpositionHandler.isDamageTypeAbsolute(event.source)) {
				event.ammount *= 1.0F - RelicsConfigHandler.ancientAegisDamageReduction;
			}
			
			/*
			 * Handler for splitting damage dealt to wearer of Superposition Ring
			 * among all other wearers, if any exist.
			 */
			
			if (SuperpositionHandler.hasBauble(player, Main.itemSuperpositionRing) & !event.isCanceled()) {
				
				List superpositioned = SuperpositionHandler.getBaubleOwnersList(player.worldObj, Main.itemSuperpositionRing);
				if (superpositioned.contains(player))
					superpositioned.remove(player);
				
				if (superpositioned.size() > 0) {
				double percent = 0.12 + (Math.random()*0.62);
				float splitAmount = (float) (event.ammount * percent);
				
				for (int counter = superpositioned.size() - 1; counter >= 0; counter--) {
					EntityPlayer cPlayer = (EntityPlayer) superpositioned.get(counter);
					cPlayer.attackEntityFrom(event.source, splitAmount/superpositioned.size());
				}
				
				event.ammount -= splitAmount;
				}
			}			
			
			/*
			 * Handler for damage absorption by Oblivion Amulet.
			 */
			
			if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, Main.itemOblivionAmulet) & !SuperpositionHandler.isDamageTypeAbsolute(event.source)) {
				if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.FIRE, (int)(event.ammount*8*RelicsConfigHandler.oblivionAmuletVisMult)).add(Aspect.ENTROPY, (int)(event.ammount*8*RelicsConfigHandler.oblivionAmuletVisMult)))) {
				 
				 ItemStack oblivionAmulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
					
				 ItemNBTHelper.setFloat(oblivionAmulet, "IDamageStored", ItemNBTHelper.getFloat(oblivionAmulet, "IDamageStored", 0) + event.ammount);
				 
				 event.setCanceled(true);
				 
			}
			}
			
			
		}
		
		
	}
	
	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		
		
		 // Handlers to prevent double kill glitch that appears when handling False Justice's effects. 
		 
		
		if (event.entity instanceof EntityPlayer & !(event.source instanceof DamageRegistryHandler.DamageSourceTrueDamage) & !(event.source instanceof DamageRegistryHandler.DamageSourceTrueDamageUndef)) {
			EntityPlayer playerAttacked = (EntityPlayer) event.entity;
			if (playerAttacked.inventory.hasItem(Main.itemFalseJustice)) {
				event.setCanceled(true);
			}
		}
		else if (event.source.getEntity() instanceof EntityPlayer  & !(event.source instanceof DamageRegistryHandler.DamageSourceTrueDamage) & !(event.source instanceof DamageRegistryHandler.DamageSourceTrueDamageUndef)) {
			EntityPlayer playerAttacker = (EntityPlayer) event.source.getEntity();
			if (playerAttacker.inventory.hasItem(Main.itemFalseJustice)) {
				event.setCanceled(true);
			}
		}
		
	}
	
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(LivingDeathEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;

			if(player.inventory.hasItem(Main.itemOmegaCore)) {
				event.setCanceled(true);
				player.setHealth(player.getMaxHealth());
			}
			
			/*
			 * Handler for Tome of Broken Fates death prevention.
			 */

			else if (!player.worldObj.isRemote) {
				
				ItemStack fateTomeStack = (SuperpositionHandler.findFirst(player, Main.itemFateTome));
				
				if (fateTomeStack != null) {
					
					if (fateTomeStack.hasTagCompound()) {
					    
					    if (ItemNBTHelper.verifyExistance(fateTomeStack, "IFateCooldown"))
					    if (ItemNBTHelper.getInt(fateTomeStack, "IFateCooldown", 0) == 0)
					    if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, ItemFateTome.AerCost).add(Aspect.EARTH, ItemFateTome.TerraCost).add(Aspect.FIRE, ItemFateTome.IgnisCost).add(Aspect.WATER, ItemFateTome.AquaCost).add(Aspect.ORDER, ItemFateTome.OrdoCost).add(Aspect.ENTROPY, ItemFateTome.PerditioCost))) {
					    	
					    	ItemNBTHelper.setInt(fateTomeStack, "IFateCooldown", (int) (600 + Math.random() * 1200));    	
							event.setCanceled(true);
							
					    	player.setHealth(player.getMaxHealth());
					    	
					    	if (Math.random() <= 0.75) {
					    		player.addPotionEffect(new PotionEffect(11, 200, 2, false));
					    		player.addPotionEffect(new PotionEffect(10, 500, 1, false));
					    		player.addPotionEffect(new PotionEffect(12, 1000, 0, false));
					    	} else {
					    		player.addPotionEffect(new PotionEffect(18, 600, 2, false));
						    	player.addPotionEffect(new PotionEffect(15, 200, 0, false));
						    	player.addPotionEffect(new PotionEffect(20, 300, 1, false));
					    	}
					    	SuperpositionHandler.imposeBurst(player.worldObj, player.dimension, player.posX, player.posY+1, player.posZ, 1.5f);
					    	player.worldObj.playSoundEffect((double) player.posX + 0.5D, (double) player.posY + 1.5D, (double) player.posZ + 0.5D, "thaumcraft:runicShieldCharge", 1.0F, 1.0F);
					    	
					    }
					    
				}
					
				}
				
			}
		}
	}
	
	
	
}
