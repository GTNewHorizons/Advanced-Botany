package com.integral.forgottenrelics.items;

import java.util.ArrayList;
import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.DamageRegistryHandler;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;

/**
 * This code is heavily based on such of Rod of Shaded Mesa from Botania,
 * and is generally an ideological inheritor of it.
 * It's our sacred duty to make telekinesis GREAT AGAIN!
 * @author Integral
 */

public class ItemTelekinesisTome extends Item implements IWarpingGear {
	
	 public static final int AerCost = (int) (20*RelicsConfigHandler.telekinesisTomeVisMult);
	 public static final int TerraCost = (int) (0*RelicsConfigHandler.telekinesisTomeVisMult);
	 public static final int IgnisCost = (int) (0*RelicsConfigHandler.telekinesisTomeVisMult);
	 public static final int AquaCost = (int) (0*RelicsConfigHandler.telekinesisTomeVisMult);
	 public static final int OrdoCost = (int) (20*RelicsConfigHandler.telekinesisTomeVisMult);
	 public static final int PerditioCost = (int) (0*RelicsConfigHandler.telekinesisTomeVisMult);

	private static final float RANGE = 3F;
	private static final int COST = 2;

	private static final String TAG_TICKS_TILL_EXPIRE = "ticksTillExpire";
	private static final String TAG_TICKS_COOLDOWN = "ticksCooldown";
	private static final String TAG_TARGET = "target";
	private static final String TAG_DIST = "dist";
	private static final String TAG_RE_DIST = "reDist";

	public ItemTelekinesisTome() {
		this.setMaxStackSize(1);
		this.setUnlocalizedName("ItemTelekinesisTome");
		this.setCreativeTab(Main.tabForgottenRelics);
	}
	
	@Override
	 public EnumRarity getRarity(ItemStack itemStack)
	 {
	 return EnumRarity.epic;
	 }
	 
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
		 if(GuiScreen.isShiftKeyDown()){
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome1.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome2.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome3.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome4.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome5.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome6.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome7.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome8.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome9.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome10.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome11_1.lore") + " " + (int) RelicsConfigHandler.telekinesisTomeDamageMIN + "-" + (int) RelicsConfigHandler.telekinesisTomeDamageMAX + " " + StatCollector.translateToLocal("item.ItemTelekinesisTome11_2.lore"));
		 } else if (GuiScreen.isCtrlKeyDown()) {
				par3List.add(StatCollector.translateToLocal("item.FRVisPerTick.lore"));
			 	par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + ItemChaosTome.round(((this.AerCost/100.0D)/3.0D), 2));
			 	par3List.add(" " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + ItemChaosTome.round(((this.OrdoCost/100.0D)/3.0D), 2));
			 	par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
			 	par3List.add(StatCollector.translateToLocal("item.FRVisPerCast.lore"));
			 	par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + (80/100.0D)*RelicsConfigHandler.telekinesisTomeVisMult);
			 	par3List.add(" " + StatCollector.translateToLocal("item.FROrdoCost.lore") + (50/100.0D)*RelicsConfigHandler.telekinesisTomeVisMult);
			 	par3List.add(" " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (200/100.0D)*RelicsConfigHandler.telekinesisTomeVisMult);
		 }
		 else {
			 par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
			 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
		 }
		 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
	 }
	
	 @Override
	 public void registerIcons(IIconRegister iconRegister) {
		 itemIcon = iconRegister.registerIcon("forgottenrelics:Telekinesis_Tome");
	 }

	@Override
	public void onUpdate(ItemStack stack, World world, Entity par3Entity, int p_77663_4_, boolean p_77663_5_) {
		if(!(par3Entity instanceof EntityPlayer))
			return;

		int ticksTillExpire = ItemNBTHelper.getInt(stack, TAG_TICKS_TILL_EXPIRE, 0);
		int ticksCooldown = ItemNBTHelper.getInt(stack, TAG_TICKS_COOLDOWN, 0);
		int attackCooldown = ItemNBTHelper.getInt(stack, "AttackCooldown", 0);

		if(ticksTillExpire == 0) {
			ItemNBTHelper.setInt(stack, TAG_TARGET, -1);
			ItemNBTHelper.setDouble(stack, TAG_DIST, -1);
			ItemNBTHelper.setDouble(stack, TAG_RE_DIST, -1);
		}
		
		if (attackCooldown > 0)
			attackCooldown--;
		
		if(ticksCooldown > 0)
			ticksCooldown--;

		ticksTillExpire--;
		ItemNBTHelper.setInt(stack, TAG_TICKS_TILL_EXPIRE, ticksTillExpire);
		ItemNBTHelper.setInt(stack, TAG_TICKS_COOLDOWN, ticksCooldown);
		ItemNBTHelper.setInt(stack, "AttackCooldown", attackCooldown);

		EntityPlayer player = (EntityPlayer) par3Entity;
		PotionEffect haste = player.getActivePotionEffect(Potion.digSpeed);
		float check = haste == null ? 0.16666667F : haste.getAmplifier() == 1 ? 0.5F : 0.4F;
		if(player.getCurrentEquippedItem() == stack && player.swingProgress == check && !world.isRemote)
			leftClick(player);
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
		
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		int targetID = ItemNBTHelper.getInt(stack, TAG_TARGET, -1);
		int ticksCooldown = ItemNBTHelper.getInt(stack, TAG_TICKS_COOLDOWN, 0);
		double length = ItemNBTHelper.getDouble(stack, TAG_DIST, -1);
		
		double re_dist = ItemNBTHelper.getDouble(stack, TAG_RE_DIST, -1);
		
		if(ticksCooldown == 0) {
			
			EntityLivingBase item = null;
			
			if(targetID != -1 && player.worldObj.getEntityByID(targetID) != null) {
				item = this.getExistingTarget(player, world, targetID, RANGE+3);
			}

			if (item == null) {
				item = this.searchForTarget(player, world, RANGE);
			}
			
			length = 7.5D;
			
			if (item != null & re_dist == -1)
				re_dist = player.getDistanceToEntity(item);

			if(item != null) {
				
				if(WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, this.AerCost).add(Aspect.ORDER, this.OrdoCost))) {
					
					if (!world.isRemote) {
						Container inventory = player.inventoryContainer;
						((EntityPlayerMP)player).sendContainerToPlayer(inventory);
						}
					
					EntityLivingBase targetEntity = (EntityLivingBase)item;
					targetEntity.fallDistance = 0.0F;
					if(targetEntity.getActivePotionEffect(Potion.moveSlowdown) == null)
						targetEntity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2, 3, true));
					

					Vector3 target3 = Vector3.fromEntityCenter(player);
					
					if (player.isSneaking()) {
						target3.add(new Vector3(player.getLookVec()).multiply(re_dist));
					} else {
						target3.add(new Vector3(player.getLookVec()).multiply(length));
						re_dist = player.getDistanceToEntity(item);
					}

					target3.y += 0.5;
					
					Vector3 entityCenter = Vector3.fromEntityCenter(item);

					for(int i = 0; i <= 6; i++) {
						float r = 0.2F + (float) Math.random() * 0.3F;
						float g = 0.0F;
						float b = 0.5F + (float) Math.random() * 0.2F;
						float s = 0.2F + (float) Math.random() * 0.1F;
						float m = 0.15F;
						float xm = ((float) Math.random() - 0.5F) * m;
						float ym = ((float) Math.random() - 0.5F) * m;
						float zm = ((float) Math.random() - 0.5F) * m;
						
						
						Botania.proxy.wispFX(world, entityCenter.x, entityCenter.y, entityCenter.z, r, g, b, s, xm, ym, zm);
					}
					
					for (int counter = 0; counter <= 8; counter ++)
						Main.proxy.spawnSuperParticle(world, "portalstuff", entityCenter.x, entityCenter.y, entityCenter.z, (Math.random() - 0.5D)*3.0D, (Math.random() - 0.5D)*3.0D, (Math.random() - 0.5D)*3.0D, 1.0F, 64);
					
					
					double multiplier = item.getDistance(target3.x, target3.y, target3.z);
					float vectorPower = 0.66666F;
					
					if (multiplier < 1.5) {
						vectorPower = 0.333333F;
					} else if (multiplier >= 8) {
						vectorPower *= (float) (multiplier/8.0F);
					}
					
					if(SuperpositionHandler.isEntityBlacklistedFromTelekinesis(item))
						return stack;
					
					setEntityMotionFromVector(item, target3, vectorPower);

					ItemNBTHelper.setInt(stack, TAG_TARGET, item.getEntityId());
					ItemNBTHelper.setDouble(stack, TAG_DIST, length);
					ItemNBTHelper.setDouble(stack, TAG_RE_DIST, re_dist);
				}
			}

			if(item != null)
				ItemNBTHelper.setInt(stack, TAG_TICKS_TILL_EXPIRE, 5);
		}
		
		return stack;
	}

	public void leftClick(EntityPlayer player) {
		ItemStack stack = player.getHeldItem();
		if(stack != null && stack.getItem() == Main.itemTelekinesisTome) {
			int targetID = ItemNBTHelper.getInt(stack, TAG_TARGET, -1);
			
			EntityLivingBase item = null;
			
			if(targetID != -1 && player.worldObj.getEntityByID(targetID) != null) {
				item = this.getExistingTarget(player, player.worldObj, targetID, RANGE+3);
				
				if(item != null)
					this.lightningAttack(player, item, stack, player.worldObj);
			}
		}
	}
	
	/**
	 * Sets entity motion from vector... Okay, not helpful.
	 * @param entity
	 * @param originalPosVector
	 * @param modifier
	 */
	
	public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosVector.copy().subtract(entityVector);

		if(finalVector.mag() > 1)
			finalVector.normalize();

		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}
	
	
	/**
	 * Finds new target for Telekinesis Tome, if previous is lost or doesn't exist at all.
	 * @param player
	 * @param world
	 * @param range Range checked per each iteration.
	 * @return
	 */
	
	public EntityLivingBase searchForTarget(EntityPlayer player, World world, float range) {
		EntityLivingBase newTarget = null;
		Vector3 target = Vector3.fromEntityCenter(player);
		List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
		int distance = 1;
		
		while(entities.size() == 0 && distance < 25) {
			target.add(new Vector3(player.getLookVec()).multiply(distance));

			target.y += 0.5;
			entities = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(target.x - range, target.y - range, target.z - range, target.x + range, target.y + range, target.z + range));
			
			if (entities.contains(player))
				entities.remove(player);
			
			distance++;
		}

		if(entities.size() > 0) {
			newTarget = entities.get(0);
		}
		
		return newTarget;
		
	}
	
	/**
	 * Attempts to find an existing target for Telekinesis Tome by given entity ID.
	 * This basically allows to smoothly control any entity once it was targeted,
	 * without Tome constantly switching to other targets within scan zone.
	 * @param player
	 * @param world
	 * @param targetID
	 * @param range Range checked per each iteration.
	 * @return
	 */
	
	public EntityLivingBase getExistingTarget(EntityPlayer player, World world, int targetID, float range) {
		EntityLivingBase taritem = (EntityLivingBase) world.getEntityByID(targetID);
		Vector3 target = Vector3.fromEntityCenter(player);
		List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
		int distance = 1;
		
		while(entities.size() == 0 && distance < 25) {
			target.add(new Vector3(player.getLookVec()).multiply(distance));

			target.y += 0.5;
			entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(target.x - range, target.y - range, target.z - range, target.x + range, target.y + range, target.z + range));
			distance++;
			
			if (entities.contains(player))
				entities.remove(player);
			
			if(entities.contains(taritem))
				return taritem;
		}
		
		return null;
	}
	
	/**
	 * Performs a lightning attack from given player to target entity.
	 * @param player
	 * @param target
	 * @param stack
	 * @param world
	 */
	
	public static void lightningAttack(EntityPlayer player, EntityLivingBase target, ItemStack stack, World world) {
		
		if (world.isRemote || ItemNBTHelper.getInt(stack, "AttackCooldown", 0) != 0)
			return;
		
		Vector3 TVec = Vector3.fromEntityCenter(target);
		Vector3 moveVector = new Vector3(player.getLookVec().normalize());
		
		if (player.getDistanceToEntity(target) <= 16)
		if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, (int) (80*RelicsConfigHandler.telekinesisTomeVisMult)).add(Aspect.ORDER, (int) (50*RelicsConfigHandler.telekinesisTomeVisMult)).add(Aspect.FIRE, (int) (200*RelicsConfigHandler.telekinesisTomeVisMult)))) {
			
			for (int counter = 0; counter <= 3; counter++)
			SuperpositionHandler.imposeLightning(player.worldObj, player.dimension, player.posX, player.posY+1.0, player.posZ, TVec.x, TVec.y, TVec.z, 20, (float) ((1/player.getDistanceToEntity(target)) * (2.0F + (Math.random()) * 4.0F)), (int) (player.getDistanceToEntity(target)*1.2F), 0, (float) (0.225F+(player.getDistanceSqToEntity(target)/2000)));
			player.worldObj.playSoundAtEntity(player, "thaumcraft:zap", 1F, 0.8F);
			
			target.attackEntityFrom(new DamageRegistryHandler.DamageSourceTLightning(player), ((float) (16 + (24 * Math.random()))*1.0F));
			
			}
			
			if (player.isSneaking())
			if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, (int) (150*RelicsConfigHandler.telekinesisTomeVisMult)).add(Aspect.ORDER, (int) (80*RelicsConfigHandler.telekinesisTomeVisMult))) & !player.worldObj.isRemote) {
			target.motionX = moveVector.x * 3.0F;
			target.motionY = moveVector.y * 1.5F;
			target.motionZ = moveVector.z * 3.0F;
			
			ItemNBTHelper.setInt(stack, TAG_TARGET, -1);
			ItemNBTHelper.setDouble(stack, TAG_DIST, -1);
			ItemNBTHelper.setDouble(stack, TAG_RE_DIST, -1);
			ItemNBTHelper.setInt(stack, TAG_TICKS_COOLDOWN, 10);
			}
			
			ItemNBTHelper.setInt(stack, "AttackCooldown", 10);
		
	}

	@Override
	public int getWarp(ItemStack arg0, EntityPlayer arg1) {
		return 4;
	}
}