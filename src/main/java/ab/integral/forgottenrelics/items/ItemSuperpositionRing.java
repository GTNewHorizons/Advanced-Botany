package com.integral.forgottenrelics.items;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;

import vazkii.botania.common.core.helper.Vector3;
import baubles.api.BaubleType;
import baubles.api.IBauble;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSuperpositionRing extends ItemBaubleBase implements IBauble {

    public ItemSuperpositionRing() {
        super("ItemSuperpositionRing");
        this.maxStackSize = 1;
        this.setUnlocalizedName("ItemSuperpositionRing");
        this.setCreativeTab(Main.tabForgottenRelics);

    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Superposition_Ring");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.ItemSuperpositionRing1.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemSuperpositionRing2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemSuperpositionRing3.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemSuperpositionRing4.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemSuperpositionRing5.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));

        }
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
        super.onWornTick(itemstack, entity);

        if (entity.ticksExisted % 600 == 0 & !entity.worldObj.isRemote & entity instanceof EntityPlayer) {

            if (Math.random() >= 0.05) {
                List<EntityPlayer> players = SuperpositionHandler
                        .getBaubleOwnersList(entity.worldObj, Main.itemSuperpositionRing);
                if (players.contains(entity)) players.remove(entity);

                if (players.size() > 0) {
                    EntityPlayer randomPlayer = players.get((int) (Math.random() * players.size()));
                    Vector3 pos1 = Vector3.fromEntityCenter(entity);
                    Vector3 pos2 = Vector3.fromEntityCenter(randomPlayer);

                    if (randomPlayer.dimension != entity.dimension) {
                        int dim1 = entity.dimension;
                        int dim2 = randomPlayer.dimension;
                        ((EntityPlayerMP) randomPlayer).mcServer.getConfigurationManager().transferPlayerToDimension(
                                (EntityPlayerMP) randomPlayer,
                                dim1,
                                ((WorldServer) randomPlayer.worldObj).getDefaultTeleporter());
                        ((EntityPlayerMP) entity).mcServer.getConfigurationManager().transferPlayerToDimension(
                                (EntityPlayerMP) entity,
                                dim2,
                                ((WorldServer) entity.worldObj).getDefaultTeleporter());
                    }

                    entity.setPositionAndUpdate(pos2.x, pos2.y, pos2.z);
                    randomPlayer.setPositionAndUpdate(pos1.x, pos1.y, pos1.z);
                    entity.worldObj.playSoundEffect(
                            pos2.x,
                            pos2.y,
                            pos2.z,
                            "mob.endermen.portal",
                            1.0F,
                            (float) (0.8F + (Math.random() * 0.2)));
                    randomPlayer.worldObj.playSoundEffect(
                            pos1.x,
                            pos1.y,
                            pos1.z,
                            "mob.endermen.portal",
                            1.0F,
                            (float) (0.8F + (Math.random() * 0.2)));
                }
            }

        }

    }

}
