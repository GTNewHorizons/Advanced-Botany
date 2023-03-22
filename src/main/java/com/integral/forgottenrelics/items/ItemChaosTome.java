package com.integral.forgottenrelics.items;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.core.helper.Vector3;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityChaoticOrb;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChaosTome extends Item implements IWarpingGear {

    public static final int AerCost = (int) (100 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int TerraCost = (int) (100 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int IgnisCost = (int) (100 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int AquaCost = (int) (100 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int OrdoCost = (int) (100 * RelicsConfigHandler.chaosTomeVisMult);
    public static final int PerditioCost = (int) (100 * RelicsConfigHandler.chaosTomeVisMult);

    public ItemChaosTome() {

        this.maxStackSize = 1;
        this.setUnlocalizedName("ItemChaosTome");
        this.setCreativeTab(Main.tabForgottenRelics);

    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Chaos_Tome");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.ItemChaosTome1.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemChaosTome2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemChaosTome3.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemChaosTome4.lore"));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(
                    StatCollector.translateToLocal("item.ItemChaosTome5_1.lore") + " "
                            + "1-"
                            + (int) RelicsConfigHandler.chaosTomeDamageCap
                            + " "
                            + StatCollector.translateToLocal("item.ItemChaosTome5_2.lore"));
            par3List.add(StatCollector.translateToLocal("item.ItemChaosTome6.lore"));
        } else if (GuiScreen.isCtrlKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.FRVisPerSecond.lore"));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRAerCost.lore")
                            + this.round((((int) this.AerCost * Math.random()) / 100.0D) * 10.0D, 2));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRTerraCost.lore")
                            + this.round((((int) this.TerraCost * Math.random()) / 100.0D) * 10.0D, 2));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRIgnisCost.lore")
                            + this.round((((int) this.IgnisCost * Math.random()) / 100.0D) * 10.0D, 2));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRAquaCost.lore")
                            + this.round((((int) this.AquaCost * Math.random()) / 100.0D) * 10.0D, 2));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FROrdoCost.lore")
                            + this.round((((int) this.OrdoCost * Math.random()) / 100.0D) * 10.0D, 2));
            par3List.add(
                    " " + StatCollector.translateToLocal("item.FRPerditioCost.lore")
                            + this.round((((int) this.PerditioCost * Math.random()) / 100.0D) * 10.0D, 2));
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));
            par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
        }

        par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
    }

    public boolean spawnOrb(World world, EntityPlayer player) {

        if (!world.isRemote) {

            Vector3 originalPos = Vector3.fromEntityCenter(player);
            Vector3 vector = originalPos.add(new Vector3(player.getLookVec()).multiply(1.0F));
            vector.y += 0.5;

            EntityChaoticOrb orb = null;

            if (Math.random() <= 0.35D) orb = new EntityChaoticOrb(world, player, true);
            else orb = new EntityChaoticOrb(world, player, false);

            orb.setPosition(
                    originalPos.x + ((Math.random() - 0.5D) * 3.0D),
                    originalPos.y + ((Math.random() - 0.5D) * 1.0D),
                    originalPos.z + ((Math.random() - 0.5D) * 3.0D));

            Vector3 motion = (new Vector3(orb.posX, orb.posY, orb.posZ).sub(originalPos.copy()))
                    .multiply(0.2D + (Math.random() * 0.2D));

            orb.motionX = motion.x;
            orb.motionY = motion.y;
            orb.motionZ = motion.z;

            world.spawnEntityInWorld(orb);

            if (!world.isRemote)
                world.playSoundAtEntity(orb, "thaumcraft:ice", 0.3f, 0.8f + world.rand.nextFloat() * 0.1f);

            return true;
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, stack.getMaxItemUseDuration());

        return stack;
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

            if (WandManager.consumeVisFromInventory(
                    player,
                    new AspectList().add(Aspect.AIR, (int) (this.AerCost * Math.random()))
                            .add(Aspect.EARTH, (int) (this.TerraCost * Math.random()))
                            .add(Aspect.WATER, (int) (this.AquaCost * Math.random()))
                            .add(Aspect.FIRE, (int) (this.IgnisCost * Math.random()))
                            .add(Aspect.ORDER, (int) (this.OrdoCost * Math.random()))
                            .add(Aspect.ENTROPY, (int) (this.PerditioCost * Math.random())))) {
                this.spawnOrb(player.worldObj, player);
            }

        }

    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    public int getWarp(ItemStack arg0, EntityPlayer arg1) {
        return 4;
    }

}
