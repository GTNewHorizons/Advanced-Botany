package com.integral.forgottenrelics.handlers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.integral.forgottenrelics.Main;

import baubles.api.BaubleType;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.entity.EntityDoppleganger;

public class SuperpositionHandler {
	
	public static void imposeArcLightning(World world, int dimension, double x, double y, double z, double destX, double destY, double destZ, float r, float g, float b, float h) {
		if (!world.isRemote)
		Main.packetInstance.sendToAllAround(new ArcLightningMessage(x, y, z, destX, destY, destZ, r, g, b, h), new TargetPoint(dimension, x, y, z, 128.0D));
	}
	
	public static void imposeLightning(World world, int dimension, double x, double y, double z, double destX, double destY, double destZ, int duration, float curve, int speed, int type, float width) {
		if (!world.isRemote) 
		Main.packetInstance.sendToAllAround(new LightningMessage(x, y, z, destX, destY, destZ, duration, curve, speed, type, width), new TargetPoint(dimension, x, y, z, 128.0D));
	}
	
	public static void imposeBurst(World world, int dimension, double x, double y, double z, float size) {
		if (!world.isRemote)
		Main.packetInstance.sendToAllAround(new BurstMessage(x, y, z, size), new TargetPoint(dimension, x, y, z, 128.0D));
	}
	
	/**
	 * Registers aspect tag on given item, for all it's existing meta-ID
	 * variants, within range specified by startCount and metaLimit.
	 */
	
	public static void setItemAspectsForMetaRange(ItemStack stack, AspectList list, int metaLimit, int startCount) {
		
		for (int counter = startCount; counter <= metaLimit; counter++) {
			stack.setItemDamage(counter);
			ThaumcraftApi.registerObjectTag(stack, list);
		}
		
	}
	
	public static boolean isDamageTypeAbsolute(DamageSource source) {
		if (source == DamageSource.outOfWorld || source == DamageSource.starve || source instanceof DamageRegistryHandler.DamageSourceFate || source instanceof DamageRegistryHandler.DamageSourceOblivion || source instanceof DamageRegistryHandler.DamageSourceSoulDrain || source instanceof DamageRegistryHandler.DamageSourceTrueDamage || source instanceof DamageRegistryHandler.DamageSourceTrueDamageUndef)
		return true;
		else
		return false;
	}
	
	public static boolean isEntityBlacklistedFromTelekinesis(EntityLivingBase entity) {
		if (entity instanceof EntityThaumcraftBoss || entity instanceof EntityDoppleganger)
		return true;
		else
		return false;
	}
	
	public static String getBaubleTooltip(BaubleType type) {
		String str = "";
		
		switch (type) {
		
		case AMULET: str = StatCollector.translateToLocal("item.FRAmulet.lore");
			break;
			
		case BELT: str = StatCollector.translateToLocal("item.FRBelt.lore");
			break;
			
		case RING: str = StatCollector.translateToLocal("item.FRRing.lore");
			break;
			
		default: str = "";
			break;
		
		}
		
		return str;
	}
	
	/**
	 * Performs ray trace for blocks in the direction of player's look, within given range.
	 * @return First block in the line of sight, null if none found.
	 */
	
	public static MovingObjectPosition getPointedBlock(EntityPlayer player, World world, float range) {
		
		double d0 = player.posX;
        double d1 = player.posY + 1.62D - (double) player.yOffset;
        double d2 = player.posZ;
		
		Vec3 position = Vec3.createVectorHelper(d0, d1, d2);
		Vec3 look = player.getLook(1.0F);
		Vec3 finalvec = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
		
		MovingObjectPosition mop = world.rayTraceBlocks(position, finalvec);
		
		return mop;
	}
	
	/**
	 * Searches for all players that have specified bauble equipped.
	 * @return List of players that have specified... you should have understood by now, didn't you?
	 */
	
	public static List <EntityPlayer> getBaubleOwnersList(World world, Item baubleItem) {
		
		List<EntityPlayer> returnList = new LinkedList();
		
		if (!world.isRemote) {
		
		List<EntityPlayer> playersList = new ArrayList(MinecraftServer.getServer().getConfigurationManager().playerEntityList);
		
		for (int counter = playersList.size() - 1; counter >= 0; counter --) {
			
			if (SuperpositionHandler.hasBauble(playersList.get(counter), baubleItem)) {
				returnList.add(playersList.get(counter));
			}
			
		}
		
		}
		
		return returnList;
	}
	
	/**
	 * Searches for players within given radius from given entity
	 * that have specified item within their bauble inventory, and 
	 * constructs a list of them.
	 * @return Random player from constructed list, null if none were found.
	 */
	
	public static EntityPlayer findPlayerWithBauble(World world, int radius, Item baubleItem, EntityLivingBase entity) {
		
		List<EntityPlayer> returnList = new LinkedList();
		
		if (!world.isRemote) {
		List<EntityPlayer> playerList = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(entity.posX-radius, entity.posY-radius, entity.posZ-radius, entity.posX+radius, entity.posY+radius, entity.posZ+radius));
		
		if (playerList.contains(entity))
			playerList.remove(entity);
		
		for (int counter = playerList.size() - 1; counter >= 0; counter --) {
			
			if (SuperpositionHandler.hasBauble(playerList.get(counter), baubleItem)) {
				returnList.add(playerList.get(counter));
			}
			
		}
		
		if (returnList.size() > 0) {
			return returnList.get((int) ((returnList.size() - 1) * Math.random()));
		} else {
			return null;
		}
		
		
		}
		
		return null;
	}
	
	/**
	 * Checks if given player has specified item equipped as bauble.
	 * @return True if item is equipped, false otherwise.
	 */
	
	public static boolean hasBauble(EntityPlayer player, Item theBauble) {
		
		InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
		List<Item> baubleList = new ArrayList();
		if (baubles.getStackInSlot(0) != null)
		baubleList.add(baubles.getStackInSlot(0).getItem());
		if (baubles.getStackInSlot(1) != null)
		baubleList.add(baubles.getStackInSlot(1).getItem());
		if (baubles.getStackInSlot(2) != null)
		baubleList.add(baubles.getStackInSlot(2).getItem());
		if (baubles.getStackInSlot(3) != null)
		baubleList.add(baubles.getStackInSlot(3).getItem());
		
		if (baubleList.contains(theBauble)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Attempts to teleport entity at given coordinates, or nearest valid location on Y axis.
	 * @return True if successfull, false otherwise.
	 */
	
	public static boolean validTeleport(Entity entity, double x_init, double y_init, double z_init, World world) {
		
		int x = (int) x_init;
		int y = (int) y_init;
		int z = (int) z_init;
		
		Block block = world.getBlock(x, y-1, z);
		
		if (block != Blocks.air & block.isCollidable()) {
			
			for (int counter = 0; counter <= 32; counter++) {
				
				if (!world.isAirBlock(x, y+counter-1, z) & world.getBlock(x, y+counter-1, z).isCollidable() & world.isAirBlock(x, y+counter, z) & world.isAirBlock(x, y+counter+1, z)) {
					
					SuperpositionHandler.imposeBurst(entity.worldObj, entity.dimension, entity.posX, entity.posY+1, entity.posZ, 1.25f);
					
					entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.0F, 1.0F);
					((EntityLivingBase) entity).setPositionAndUpdate(x+0.5, y+counter, z+0.5);
					entity.worldObj.playSoundEffect(x, y+counter, z, "mob.endermen.portal", 1.0F, 1.0F);
					
					return true;
				}
				
			}
			
		} else {
			
			for (int counter = 0; counter <= 32; counter++) {
				
				if (!world.isAirBlock(x, y-counter-1, z) & world.getBlock(x, y-counter-1, z).isCollidable() & world.isAirBlock(x, y-counter, z) & world.isAirBlock(x, y-counter+1, z)) {
					
					SuperpositionHandler.imposeBurst(entity.worldObj, entity.dimension, entity.posX, entity.posY+1, entity.posZ, 1.25f);
					
					entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.0F, 1.0F);
					((EntityLivingBase) entity).setPositionAndUpdate(x+0.5, y-counter, z+0.5);
					entity.worldObj.playSoundEffect(x, y-counter, z, "mob.endermen.portal", 1.0F, 1.0F);
					
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	
	/**
	  * Attempts to find valid location within given radius and teleport entity there.
	  * @return True if teleportation were successfull, false otherwise.
	  */
	 public static boolean validTeleportRandomly(Entity entity, World world, int radius) {
		 int d = radius*2;
		 
	     double x = entity.posX + ((Math.random()-0.5D)*d);
	     double y = entity.posY + ((Math.random()-0.5D)*d);
	     double z = entity.posZ + ((Math.random()-0.5D)*d);
	     return SuperpositionHandler.validTeleport(entity, x, y, z, world);
	 }
	
	/**
	 * @param list - ItemStack array. All elements must be instances of ItemWandCasting.
	 * @return Random wand from given ItemStack list that is not fully charged with given Aspect. Null if none found.
	 */
	
	public static ItemStack getRandomValidWand(List<ItemStack> list, Aspect aspect) {
		
		List <ItemStack> validWand = new LinkedList();
		
		ItemStack randomWand = null;
		
		if (list.size() > 0) {
			
			for (int counter = 0; counter < list.size(); counter++) {
				ItemStack sheduledWand = list.get(counter);
				if (((ItemWandCasting)sheduledWand.getItem()).getVis(sheduledWand, aspect) < ((ItemWandCasting)sheduledWand.getItem()).getMaxVis(sheduledWand)) {
					validWand.add(sheduledWand);
                }
			}
			
			if (validWand.size() > 0) {
			randomWand = validWand.get((int)(Math.random() * (((validWand.size()-1)) + 1)));
			}
			return randomWand;
		}
		
		
		
		return randomWand;
	}
	
	/**
	 * @return An array containing all instances of ItemWandCasting within player inventory.
	 */
	
	public static List wandSearch(EntityPlayer player) {
		
		List<ItemStack> itemStackList = new LinkedList<ItemStack>();
		
		 for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
	            if (player.inventory.mainInventory[slot] == null)
	                continue;
	            if (player.inventory.mainInventory[slot].getItem() instanceof ItemWandCasting) {
	            	itemStackList.add(player.inventory.mainInventory[slot]);
	            }
	        }
		
		
		
		return itemStackList;
	}
	
	/**
	 * @return An array containing all ItemStacks of given Item within player inventory.
	 */
	
	public static List itemSearch(EntityPlayer player, Item researchItem) {
		
		List<ItemStack> itemStackList = new LinkedList<ItemStack>();
		
		 for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
	            if (player.inventory.mainInventory[slot] == null)
	                continue;
	            if (player.inventory.mainInventory[slot].getItem() == researchItem) {
	   
	            	itemStackList.add(player.inventory.mainInventory[slot]);
	                
	            }
	        }
		
		
		
		return itemStackList;
	}
	
	/**
	 * Because I'm fucking tired of this shit.
	 */
	
	public static boolean sidedVisConsumption(EntityPlayer player, World world, AspectList list) {
		
		if (!world.isRemote) {
			if (WandManager.consumeVisFromInventory(player, list))
			return true;
			else
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Yeah. The description of Tome of Broken Fates was quite literal.
	 */
	
	public static void insanelyDisastrousConsequences(EntityPlayer player) {
		while (player.inventory.hasItem(Main.itemFateTome)) {
			player.inventory.consumeInventoryItem(Main.itemFateTome);
		}
		
			List<EntityLivingBase> entityList = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(player.posX-64, player.posY-64, player.posZ-64, player.posX+64, player.posY+64, player.posZ+64));
			
			if (!(entityList == null) & entityList.size() > 0) {
			
			for (int counter = entityList.size(); counter > 0; counter--) {
				
				entityList.get(counter - 1).attackEntityFrom(new DamageRegistryHandler.DamageSourceFate(), 40000.0F);
				player.worldObj.newExplosion(player, entityList.get(counter - 1).posX, entityList.get(counter - 1).posY, entityList.get(counter - 1).posZ, 16F, true, true);
			}
		
			}
			
			player.worldObj.newExplosion(player, player.posX, player.posY, player.posZ, 100F, true, true);
		
	}
	
	/**
	 * Searches for ItemStacks of given Item within player's inventory.
	 * @param searchItem - Item to be searched.
	 * @return First available ItemStack of given Item, null if none found.
	 */
	
	public static ItemStack findFirst(EntityPlayer player, Item searchItem) {
        for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
            if (player.inventory.mainInventory[slot] == null)
                continue;
            if (player.inventory.mainInventory[slot].getItem() == searchItem) {
                return player.inventory.mainInventory[slot];
            }
        }
        
        return null;
    }
	
	/**
	 * Converts ItemStack of consumableItem within player's inventory to ItemStack of a single leftoverItem.
	 * 
	 * @param player
	 * @param consumableItem Item to convert
	 * @param leftoverItem Item to convert to
	 */
	
	public static void convertStuff(EntityPlayer player, Item consumableItem, Item leftoverItem) {
        for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
            if (player.inventory.mainInventory[slot] == null)
                continue;
            if (player.inventory.mainInventory[slot].getItem() == consumableItem) {
                player.inventory.mainInventory[slot] = new ItemStack(leftoverItem);
                return;
            }
        }
    }
	
	

}
