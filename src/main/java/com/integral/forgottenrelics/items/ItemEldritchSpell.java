package com.integral.forgottenrelics.items;

import java.util.ArrayList;
import java.util.List;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityDarkMatterOrb;
import com.integral.forgottenrelics.entities.EntityCrimsonOrb;
import com.integral.forgottenrelics.entities.EntityRageousMissile;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.entities.EntityAspectOrb;
import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
import thaumcraft.common.entities.projectile.EntityEldritchOrb;
import thaumcraft.common.entities.projectile.EntityGolemOrb;
import thaumcraft.common.items.wands.WandManager;
import thaumcraft.common.tiles.TileEldritchObelisk;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityDoppleganger;

/**
 * Well, this one is not quite original, but...
 * Why not?
 * @author Integral
 */

public class ItemEldritchSpell extends Item implements IWarpingGear {
	
	 public static final int AerCost = (int) (0*RelicsConfigHandler.eldritchSpellVisMult);
	 public static final int TerraCost = (int) (0*RelicsConfigHandler.eldritchSpellVisMult);
	 public static final int IgnisCost = (int) (0*RelicsConfigHandler.eldritchSpellVisMult);
	 public static final int AquaCost = (int) (0*RelicsConfigHandler.eldritchSpellVisMult);
	 public static final int OrdoCost = (int) (0*RelicsConfigHandler.eldritchSpellVisMult);
	 public static final int PerditioCost = (int) (400*RelicsConfigHandler.eldritchSpellVisMult);

	public ItemEldritchSpell() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("ItemEldritchSpell");
		setCreativeTab(Main.tabForgottenRelics);
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {
		
	}
	
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
		 
		 float theDamage;
		 
		 if (par2EntityPlayer.dimension == Config.dimensionOuterId)
			 theDamage = RelicsConfigHandler.eldritchSpellDamageEx;
		 else
			 theDamage = RelicsConfigHandler.eldritchSpellDamage;
		 
		 if(GuiScreen.isShiftKeyDown()){
			 par3List.add(StatCollector.translateToLocal("item.ItemEldritchSpell1.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemEldritchSpell2.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemEldritchSpell3.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemEldritchSpell4.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemEldritchSpell5_1.lore") + " " + theDamage + " " + StatCollector.translateToLocal("item.ItemEldritchSpell5_2.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemEldritchSpell6.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemEldritchSpell7.lore")); 
		 } else if (GuiScreen.isCtrlKeyDown()) {
				par3List.add(StatCollector.translateToLocal("item.FRVisPerCast.lore"));
			 	par3List.add(" " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + (this.PerditioCost/100.0D));
			 }
		 else {
			 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
		 }
		 
		 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
	 }


	 @Override
	 public EnumRarity getRarity(ItemStack itemStack)
	 {
	 return EnumRarity.epic;
	 }
	
	 @Override
	 public void registerIcons(IIconRegister iconRegister)
	 {
		 itemIcon = iconRegister.registerIcon("forgottenrelics:Eldritch_Spell");
	 }

	 public boolean spawnOrb(World world, EntityPlayer player) {
		
		if (!world.isRemote) {
			
			Vector3 originalPos = Vector3.fromEntityCenter(player);
			Vector3 vector = originalPos.add(new Vector3(player.getLookVec()).multiply(1.0F));
			vector.y += 0.5;
			
			Vector3 motion = new Vector3(player.getLookVec()).multiply(1.25F);
			
			EntityDarkMatterOrb orb = new EntityDarkMatterOrb(world, player);
			orb.setPosition(vector.x, vector.y, vector.z);
			
			orb.motionX = motion.x;
			orb.motionY = motion.y;
			orb.motionZ = motion.z;
			
			world.playSoundEffect(player.posX, player.posY, player.posZ, "thaumcraft:egattack", 0.6F, 0.8F + (float) Math.random() * 0.2F);
			world.spawnEntityInWorld(orb);
			
			return true;
		}
			
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if (player.xpCooldown == 0) {
			
			if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.ENTROPY, this.PerditioCost))) {
				
			if (!world.isRemote) {
			Container inventory = player.inventoryContainer;
			((EntityPlayerMP)player).sendContainerToPlayer(inventory);
			}
			
			spawnOrb(world, player);
			player.xpCooldown = 20;
			player.swingItem();
			
		}
			
		}
		
		return stack;
	}

	@Override
	public boolean isFull3D() {
		return false;
	}

	@Override
	public int getWarp(ItemStack arg0, EntityPlayer arg1) {
		return 4;
	}
}