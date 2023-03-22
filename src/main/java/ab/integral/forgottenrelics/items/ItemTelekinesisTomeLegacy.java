package com.integral.forgottenrelics.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
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
import thaumcraft.common.entities.EntityAspectOrb;
import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
import thaumcraft.common.items.wands.WandManager;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityDoppleganger;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.DamageRegistryHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTelekinesisTomeLegacy extends Item implements IWarpingGear {

    public static final int AerCost = 6;
    public static final int TerraCost = 0;
    public static final int IgnisCost = 200;
    public static final int AquaCost = 0;
    public static final int OrdoCost = 6;
    public static final int PerditioCost = 2;

    private static final float RANGE = 3F;
    private static final int COST = 2;

    private static final String TAG_TICKS_TILL_EXPIRE = "ticksTillExpire";
    private static final String TAG_TICKS_COOLDOWN = "ticksCooldown";
    private static final String TAG_ATTACK_COOLDOWN = "attackCooldown";
    private static final String TAG_TARGET = "target";
    private static final String TAG_DIST = "dist";
    private static final String TAG_RE_DIST = "reDist";

    public ItemTelekinesisTomeLegacy() {
        this.maxStackSize = 1;
        this.setUnlocalizedName("ItemTelekinesisTome");
        this.setCreativeTab(Main.tabForgottenRelics);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("forgottenrelics:Telekinesis_Tome");
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
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
            par3List.add(StatCollector.translateToLocal("item.ItemTelekinesisTome11.lore"));
        } else if (GuiScreen.isCtrlKeyDown()) {
            par3List.add(StatCollector.translateToLocal("item.FRVisPerTick.lore"));
            par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + (this.AerCost / 100.0D));
            par3List.add(" " + StatCollector.translateToLocal("item.FRPerditioCost.lore") + (this.OrdoCost / 100.0D));
            par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
            par3List.add(StatCollector.translateToLocal("item.FRVisPerCast.lore"));
            par3List.add(" " + StatCollector.translateToLocal("item.FRAerCost.lore") + (80 / 100.0D) * 1.0D);
            par3List.add(" " + StatCollector.translateToLocal("item.FROrdoCost.lore") + (50 / 100.0D) * 1.0D);
            par3List.add(" " + StatCollector.translateToLocal("item.FRIgnisCost.lore") + (200 / 100.0D) * 1.0D);
        } else {
            par3List.add(StatCollector.translateToLocal("item.FRViscostTooltip.lore"));
            par3List.add(StatCollector.translateToLocal("item.FRShiftTooltip.lore"));
        }

        par3List.add(StatCollector.translateToLocal("item.FREmpty.lore"));
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity par3Entity, int p_77663_4_, boolean p_77663_5_) {
        if (!(par3Entity instanceof EntityPlayer)) return;

        // if (!world.isRemote)
        // ((EntityPlayer)par3Entity).inventoryContainer.detectAndSendChanges();

        int ticksTillExpire = ItemNBTHelper.getInt(stack, TAG_TICKS_TILL_EXPIRE, 0);
        int ticksCooldown = ItemNBTHelper.getInt(stack, TAG_TICKS_COOLDOWN, 0);
        int attackCooldown = ItemNBTHelper.getInt(stack, TAG_ATTACK_COOLDOWN, 0);

        if (ticksTillExpire == 0) {
            ItemNBTHelper.setInt(stack, TAG_TARGET, -1);
            ItemNBTHelper.setDouble(stack, TAG_DIST, -1);
            ItemNBTHelper.setDouble(stack, TAG_RE_DIST, -1);
        }

        if (attackCooldown > 0) attackCooldown--;

        if (ticksCooldown > 0) ticksCooldown--;

        ticksTillExpire--;
        ItemNBTHelper.setInt(stack, TAG_TICKS_TILL_EXPIRE, ticksTillExpire);
        ItemNBTHelper.setInt(stack, TAG_TICKS_COOLDOWN, ticksCooldown);
        ItemNBTHelper.setInt(stack, TAG_ATTACK_COOLDOWN, attackCooldown);

        EntityPlayer player = (EntityPlayer) par3Entity;
        PotionEffect haste = player.getActivePotionEffect(Potion.digSpeed);
        float check = haste == null ? 0.16666667F : haste.getAmplifier() == 1 ? 0.5F : 0.4F;
        // if(player.getCurrentEquippedItem() == stack && player.swingProgress == check && !world.isRemote)
        // if (ItemNBTHelper.getBoolean(stack, "IScheduledHandler", false)) {
        // ItemNBTHelper.setBoolean(stack, "IScheduledHandler", false);
        // leftClick(player);
        // }
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

        if (entityLiving instanceof EntityPlayer) {
            leftClick((EntityPlayer) entityLiving);
        }

        return true;

    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        int targetID = ItemNBTHelper.getInt(stack, TAG_TARGET, -1);
        int ticksCooldown = ItemNBTHelper.getInt(stack, TAG_TICKS_COOLDOWN, 0);
        double length = ItemNBTHelper.getDouble(stack, TAG_DIST, -1);
        double re_dist = ItemNBTHelper.getDouble(stack, TAG_RE_DIST, -1);

        if (ticksCooldown == 0) {
            Entity item = null;
            if (targetID != -1 && player.worldObj.getEntityByID(targetID) != null) {
                Entity taritem = player.worldObj.getEntityByID(targetID);

                boolean found = false;
                Vector3 target = Vector3.fromEntityCenter(player);
                List<Entity> entities = new ArrayList<Entity>();
                int distance = 1;
                while (entities.size() == 0 && distance < 32) {
                    target.add(new Vector3(player.getLookVec()).multiply(distance));

                    target.y += 0.5;
                    entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(
                            player,
                            AxisAlignedBB.getBoundingBox(
                                    target.x - RANGE,
                                    target.y - RANGE,
                                    target.z - RANGE,
                                    target.x + RANGE,
                                    target.y + RANGE,
                                    target.z + RANGE));
                    distance++;
                    if (entities.contains(taritem)) found = true;
                }

                if (found) item = player.worldObj.getEntityByID(targetID);
            }

            if (item == null) {
                Vector3 target = Vector3.fromEntityCenter(player);
                List<Entity> entities = new ArrayList<Entity>();
                int distance = 1;
                while (entities.size() == 0 && distance < 32) {
                    target.add(new Vector3(player.getLookVec()).multiply(distance));

                    target.y += 0.5;
                    entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(
                            player,
                            AxisAlignedBB.getBoundingBox(
                                    target.x - RANGE,
                                    target.y - RANGE,
                                    target.z - RANGE,
                                    target.x + RANGE,
                                    target.y + RANGE,
                                    target.z + RANGE));
                    distance++;

                    if (entities.size() > 0) for (int counter = 0; counter <= entities.size() - 1; counter++) {
                        Entity itemS = entities.get(counter);
                        if (itemS instanceof EntityXPOrb || itemS instanceof EntityAspectOrb
                                || itemS instanceof EntityThaumcraftBoss
                                || itemS instanceof EntityDoppleganger) {
                            entities.remove(itemS);
                            counter = -1;
                        }
                    }

                    if (entities.size() > 0) item = entities.get(0);

                }

                if (item != null) re_dist = player.getDistanceToEntity(item);

                length = 7.5D;

                if (item instanceof EntityItem) length = 2.0D;

            }

            if (item != null) {

                if (WandManager
                        .consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, 6).add(Aspect.ORDER, 6))) {
                    if (item instanceof EntityItem) ((EntityItem) item).delayBeforeCanPickup = 5;

                    if (!world.isRemote) {
                        Container inventory = player.inventoryContainer;
                        ((EntityPlayerMP) player).sendContainerToPlayer(inventory);
                    }

                    if (item instanceof EntityLivingBase) {
                        EntityLivingBase targetEntity = (EntityLivingBase) item;
                        targetEntity.fallDistance = 0.0F;
                        if (targetEntity.getActivePotionEffect(Potion.moveSlowdown) == null)
                            targetEntity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2, 3, true));
                    }

                    Vector3 target3 = Vector3.fromEntityCenter(player);
                    if (player.isSneaking()) {
                        target3.add(new Vector3(player.getLookVec()).multiply(re_dist));
                    } else {
                        target3.add(new Vector3(player.getLookVec()).multiply(length));
                        re_dist = player.getDistanceToEntity(item);
                    }

                    target3.y += 0.5;
                    if (item instanceof EntityItem) target3.y += 0.25;

                    for (int i = 0; i < 4; i++) {
                        float r = 0.2F + (float) Math.random() * 0.3F;
                        float g = 0.0F;
                        float b = 0.5F + (float) Math.random() * 0.2F;
                        float s = 0.2F + (float) Math.random() * 0.1F;
                        float m = 0.15F;
                        float xm = ((float) Math.random() - 0.5F) * m;
                        float ym = ((float) Math.random() - 0.5F) * m;
                        float zm = ((float) Math.random() - 0.5F) * m;

                        Botania.proxy.wispFX(
                                world,
                                item.posX + item.width / 2,
                                item.posY + item.height / 2,
                                item.posZ + item.width / 2,
                                r,
                                g,
                                b,
                                s,
                                xm,
                                ym,
                                zm);
                    }

                    for (int counter = 0; counter <= 8; counter++) Main.proxy.spawnSuperParticle(
                            world,
                            "portalstuff",
                            item.posX,
                            item.posY,
                            item.posZ,
                            (Math.random() - 0.5D) * 3.0D,
                            (Math.random() - 0.5D) * 3.0D,
                            (Math.random() - 0.5D) * 3.0D,
                            1.0F,
                            64);

                    double multiplier = item.getDistance(target3.x, target3.y, target3.z);
                    float vectorPower = 0.66666F;

                    if (multiplier < 1) {
                        vectorPower = 0.333333F;
                    } else if (multiplier >= 8) {
                        vectorPower *= (float) (multiplier / 8.0F);
                    }

                    setEntityMotionFromVector(item, target3, vectorPower);

                    ItemNBTHelper.setInt(stack, TAG_TARGET, item.getEntityId());
                    ItemNBTHelper.setDouble(stack, TAG_DIST, length);
                    ItemNBTHelper.setDouble(stack, TAG_RE_DIST, re_dist);
                }
            }

            if (item != null) ItemNBTHelper.setInt(stack, TAG_TICKS_TILL_EXPIRE, 5);
        }
        return stack;
    }

    public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
        Vector3 entityVector = Vector3.fromEntityCenter(entity);
        Vector3 finalVector = originalPosVector.copy().subtract(entityVector);

        if (finalVector.mag() > 1) finalVector.normalize();

        entity.motionX = finalVector.x * modifier;
        entity.motionY = finalVector.y * modifier;
        entity.motionZ = finalVector.z * modifier;
    }

    public static void leftClick(EntityPlayer player) {
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.getItem() == Main.itemTelekinesisTome) {
            int attackCooldown = ItemNBTHelper.getInt(stack, TAG_ATTACK_COOLDOWN, 0);
            int targetID = ItemNBTHelper.getInt(stack, TAG_TARGET, -1);
            ItemNBTHelper.getDouble(stack, TAG_DIST, -1);
            ItemNBTHelper.getDouble(stack, TAG_RE_DIST, -1);
            Entity item = null;

            if (targetID != -1 && player.worldObj.getEntityByID(targetID) != null) {
                Entity taritem = player.worldObj.getEntityByID(targetID);

                boolean found = false;
                Vector3 target = Vector3.fromEntityCenter(player);
                List<Entity> entities = new ArrayList<Entity>();
                int distance = 1;
                while (entities.size() == 0 && distance < 32) {
                    target.add(new Vector3(player.getLookVec()).multiply(distance));

                    target.y += 0.5;
                    entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(
                            player,
                            AxisAlignedBB.getBoundingBox(
                                    target.x - RANGE,
                                    target.y - RANGE,
                                    target.z - RANGE,
                                    target.x + RANGE,
                                    target.y + RANGE,
                                    target.z + RANGE));
                    distance++;
                    if (entities.contains(taritem)) found = true;
                }

                if (found) {
                    item = taritem;
                    Vector3 moveVector = new Vector3(player.getLookVec().normalize());
                    if (item instanceof EntityItem) {
                        lightningAttack(player, item, stack, player.worldObj);
                        item.setDead();
                        return;
                    } else {
                        if (player.getDistanceToEntity(item) <= 16 & attackCooldown == 0)
                            lightningAttack(player, item, stack, player.worldObj);
                        return;
                    }
                }
            }
        }
    }

    public static void lightningAttack(EntityPlayer player, Entity target, ItemStack stack, World world) {

        if (world.isRemote) return;

        Vector3 TVec = Vector3.fromEntityCenter(target);
        Vector3 moveVector = new Vector3(player.getLookVec().normalize());

        if (WandManager.consumeVisFromInventory(
                player,
                new AspectList().add(Aspect.AIR, 80).add(Aspect.ORDER, 50).add(Aspect.FIRE, 200))) {

            for (int counter = 0; counter <= 3; counter++) Main.proxy.lightning(
                    player.worldObj,
                    player.posX,
                    player.posY + 1.0,
                    player.posZ,
                    TVec.x,
                    TVec.y,
                    TVec.z,
                    20,
                    (float) ((1 / player.getDistanceToEntity(target)) * (Math.random()) * 4.0F),
                    (int) (player.getDistanceToEntity(target) * 1.2F),
                    0,
                    0.175F);
            player.worldObj.playSoundAtEntity(player, "thaumcraft:zap", 1F, 0.8F);

            target.attackEntityFrom(
                    new DamageRegistryHandler.DamageSourceTLightning(player),
                    (float) (16 + (24 * Math.random())));

        }

        if (player.isSneaking())
            if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.AIR, 150).add(Aspect.ORDER, 80))
                    & !player.worldObj.isRemote) {
                        target.motionX = moveVector.x * 3.0F;
                        target.motionY = moveVector.y * 1.5F;
                        target.motionZ = moveVector.z * 3.0F;

                    }

    }

    @Override
    public int getWarp(ItemStack arg0, EntityPlayer arg1) {
        return 4;
    }
}
