package com.integral.forgottenrelics.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.List;

import org.lwjgl.opengl.GL11;

import vazkii.botania.api.item.IBaubleRender;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.lib.LibItemNames;
import baubles.api.BaubleType;

import com.google.common.collect.Multimap;
import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAncientAegis extends ItemBaubleBaseModifier {

	public ItemAncientAegis() {
		super("ItemAncientAegis");
		this.setCreativeTab(Main.tabForgottenRelics);
		this.setUnlocalizedName("ItemAncientAegis");
	}
	
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
		 if(GuiScreen.isShiftKeyDown()){
			 par3List.add(StatCollector.translateToLocal("item.ItemAncientAegis1_1.lore") + " " + (int) (RelicsConfigHandler.ancientAegisDamageReduction*100F) + StatCollector.translateToLocal("item.ItemAncientAegis1_2.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemAncientAegis2.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemAncientAegis3.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemAncientAegis4.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.ItemAncientAegis5.lore")); 
			 par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
			 par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
		 }
		 else {
			 par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore")); 
			
		 }
	 }
	 
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		super.onWornTick(stack, player);
		
		if (!player.worldObj.isRemote & player.ticksExisted % 20 == 0 & player.getMaxHealth() != player.getHealth()) {
			player.heal(1);
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack itemStack) {
		 return EnumRarity.epic;
	}
	 
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		 itemIcon = iconRegister.registerIcon("forgottenrelics:Ancient_Aegis");
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {
		attributes.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(getBaubleUUID(stack), "Bauble modifier", 1, 0));
	}

}