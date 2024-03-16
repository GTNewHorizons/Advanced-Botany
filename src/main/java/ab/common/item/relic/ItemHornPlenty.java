package ab.common.item.relic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import ab.client.core.handler.PlayerItemUsingSound.ClientSoundHandler;
import ab.common.core.handler.ConfigABHandler;
import ab.common.core.handler.NetworkHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemHornPlenty extends ItemModRelic {

    public static final String[] dropFewItems = new String[] { "dropFewItems", "func_70628_a" };
    private static final short maxChargeLoot = 16;
    private static final int manaCost = 64000;

    public ItemHornPlenty() {
        super("hornPlenty");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int pos, boolean equipped) {
        super.onUpdate(stack, world, entity, pos, equipped);
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            short lastChargeLoot = getLastChargeLoot(stack);
            short chargeLoot = getChargeLoot(stack);
            if (lastChargeLoot != chargeLoot) {
                setLastChargeLoot(stack, chargeLoot);
                NetworkHandler.sendPacketToHornHud((EntityPlayerMP) player, chargeLoot);
            }
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!hasChargeLoot(stack) && ManaItemHandler.requestManaExactForTool(stack, player, manaCost, false))
            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int time) {
        time = this.getMaxItemUseDuration(stack) - time;
        if (time > 48) {
            if (!player.worldObj.isRemote && ManaItemHandler.requestManaExactForTool(stack, player, manaCost, true)) {
                setChargeLoot(stack, maxChargeLoot);
                player.worldObj.playSoundAtEntity(player, "random.orb", 1.2F, 4.0F);
            }
            player.stopUsingItem();
        } else if (player.worldObj.isRemote)
            ClientSoundHandler.playSound(player, "ab:hornPlentyUsing", 2.4f, 2.47F, 19, false);
    }

    @SideOnly(Side.CLIENT)
    public boolean showDurabilityBar(ItemStack stack) {
        return hasChargeLoot(stack);
    }

    @SideOnly(Side.CLIENT)
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0f - (double) (getChargeLoot(stack)) / (double) maxChargeLoot;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 42000;
    }

    public boolean isFull3D() {
        return true;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }

    @SubscribeEvent
    public void onPlayerAttack(LivingDropsEvent event) {
        if (event != null && event.source != null && event.source.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
            ItemStack horn = null;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack == null) continue;
                else if (stack.getItem() instanceof ItemHornPlenty) {
                    if (!hasChargeLoot(stack)) continue;
                    horn = stack;
                    break;
                }
            }
            if (!player.worldObj.isRemote && horn != null && hasChargeLoot(horn)) {
                if (player.worldObj.rand.nextInt(100) < 20 && event.entityLiving != null
                        && !(event.entityLiving instanceof IBossDisplayData)
                        && isVallidEntity(event.entityLiving)) {
                    try {
                        EntityLivingBase liv = event.entityLiving;
                        ReflectionHelper
                                .findMethod(
                                        EntityLivingBase.class,
                                        liv,
                                        dropFewItems,
                                        new Class[] { boolean.class, int.class })
                                .invoke(liv, new Object[] { true, (int) (event.lootingLevel * 1.5f) });
                        setChargeLoot(horn, (short) (getChargeLoot(horn) - 1));
                        player.worldObj.playSoundAtEntity(player, "random.orb", 1.9F, 0.8F);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setLastChargeLoot(ItemStack stack, short count) {
        ItemNBTHelper.setShort(stack, "lastChargeLoot", count);
    }

    public short getLastChargeLoot(ItemStack stack) {
        return ItemNBTHelper.getShort(stack, "lastChargeLoot", (short) 0);
    }

    public void setChargeLoot(ItemStack stack, short count) {
        ItemNBTHelper.setShort(stack, "chargeLoot", count);
    }

    public short getChargeLoot(ItemStack stack) {
        return ItemNBTHelper.getShort(stack, "chargeLoot", (short) 0);
    }

    public boolean hasChargeLoot(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "chargeLoot", 0) > 0;
    }

    public static boolean isVallidEntity(EntityLivingBase liv) {
        for (String entityName : ConfigABHandler.lockEntityListToHorn)
            if (liv.getClass().getSimpleName().equals(entityName)) return false;
        return true;
    }
}
