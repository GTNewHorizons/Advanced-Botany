package com.integral.forgottenrelics.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.core.helper.Vector3;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityCrimsonOrb;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * I just can't believe that no one ever made such a thing.
 * 
 * @author Integral
 */

public class ItemCrimsonSpell extends Item implements IWarpingGear {

    public static final int AerCost = (int) (0 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int TerraCost = (int) (0 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int IgnisCost = (int) (480 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int AquaCost = (int) (0 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int OrdoCost = (int) (0 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int PerditioCost = (int) (360 * RelicsConfigHandler.chaosTomeVisMult);

    public static final float SEARCH_RANGE = 3F;

    public ItemCrimsonSpell() {
        super();
        setMaxStackSize(1);
        setUnlocalizedName("ItemCrimsonSpell");
        setCreativeTab(Main.tabForgottenRelics);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i,
            boolean b) {/*
                         * if (!world.isRemote & entity.ticksExisted % 16 == 0) { if (ItemNBTHelper.getInt(itemstack,
                         * "ICooldown", 0) > 0) { ItemNBTHelper.setInt(itemstack, "ICooldown",
                         * (ItemNBTHelper.getInt(itemstack, "ICooldown", 0) - 16)); } if
                         * (ItemNBTHelper.getInt(itemstack, "ICooldown", 0) % 8 != 0 || ItemNBTHelper.getInt(itemstack,
                         * "ICooldown", 0) < 0) { ItemNBTHelper.setInt(itemstack, "ICooldown", 0); } }
                         */
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.ItemCrimsonSpell1.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemCrimsonSpell2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemCrimsonSpell3.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemCrimsonSpell4.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemCrimsonSpell5.lore"));
            par3List.add(
                    StatCollector.translateToLocal("item.ItemCrimsonSpell6_1.lore") + " "
                            + (int) RelicsConfigHandler.crimsonSpellDamageMIN
                            + "-"
                            + (int) RelicsConfigHandler.crimsonSpellDamageMAX
                            + " "
                            + StatCollector.translateToLocal("item.ItemCrimsonSpell6_2.lore"));
        } else if (GuiScreen.isCtrlKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.FRVisPerCast.lore"));
            par3List.add(" " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (this.IgnisCost / 100.0D));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + (this.PerditioCost / 100.0D));
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));
            par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
        }

        par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Crimson_Spell");
    }

    public boolean spawnOrb(World world, EntityPlayer player, EntityLivingBase target) {

        if (!world.isRemote) {

            Vector3 originalPos = Vector3.fromEntityCenter(player);
            Vector3 vector = originalPos.add(new Vector3(player.getLookVec()).multiply(1.0F));
            vector.y += 0.5;

            Vector3 motion = new Vector3(player.getLookVec()).multiply(0.75F);

            EntityCrimsonOrb orb = new EntityCrimsonOrb(world, player, target, true);
            orb.setPosition(vector.x, vector.y, vector.z);

            orb.motionX = motion.x;
            orb.motionY = motion.y;
            orb.motionZ = motion.z;

            world.playSoundEffect(
                    player.posX,
                    player.posY,
                    player.posZ,
                    "thaumcraft:egattack",
                    0.6F,
                    0.8F + (float) Math.random() * 0.2F);
            world.spawnEntityInWorld(orb);

            return true;
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        if (player.xpCooldown == 0) {

            int gRange = 32;

            EntityLivingBase target = null;

            Vector3 vec = Vector3.fromEntityCenter(player);
            List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
            int distance = 1;
            while (entities.size() == 0 && distance < gRange) {

                float superposition = 0;
                if (distance > 10) superposition = 3F;
                if (distance > 20) superposition = 5F;

                vec.add(new Vector3(player.getLookVec()).multiply(distance));
                vec.y += 0.5;
                entities = player.worldObj.getEntitiesWithinAABB(
                        EntityLivingBase.class,
                        AxisAlignedBB.getBoundingBox(
                                vec.x - (SEARCH_RANGE + superposition),
                                vec.y - (SEARCH_RANGE + superposition),
                                vec.z - (SEARCH_RANGE + superposition),
                                vec.x + (SEARCH_RANGE + superposition),
                                vec.y + (SEARCH_RANGE + superposition),
                                vec.z + (SEARCH_RANGE + superposition)));
                if (entities.contains(player)) entities.remove(player);

                distance++;

            }

            boolean notFound = false;

            if (entities.size() == 0) {
                notFound = true;
                entities = player.worldObj.getEntitiesWithinAABB(
                        EntityLivingBase.class,
                        AxisAlignedBB.getBoundingBox(
                                player.posX - gRange,
                                player.posY - gRange,
                                player.posZ - gRange,
                                player.posX + gRange,
                                player.posY + gRange,
                                player.posZ + gRange));
            }

            if (entities.size() > 0 & notFound) {

                for (int counter = entities.size() - 1; counter >= 0; counter--) {
                    if (!entities.get(counter).canEntityBeSeen(player)) {
                        entities.remove(counter);
                        counter = entities.size();
                    }
                }

            }

            if (entities.contains(player)) entities.remove(player);

            if (WandManager.consumeVisFromInventory(
                    player,
                    new AspectList().add(Aspect.FIRE, this.IgnisCost).add(Aspect.ENTROPY, this.PerditioCost))) {

                if (!world.isRemote) {
                    Container inventory = player.inventoryContainer;
                    ((EntityPlayerMP) player).sendContainerToPlayer(inventory);
                }

                if (entities.size() > 0) target = entities.get((int) ((entities.size() - 1) * Math.random()));
                else target = null;

                spawnOrb(world, player, target);
                player.xpCooldown = 30;
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
        return 3;
    }
}
