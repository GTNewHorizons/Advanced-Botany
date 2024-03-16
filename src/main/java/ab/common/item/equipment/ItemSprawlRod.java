package ab.common.item.equipment;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import ab.common.core.handler.ConfigABHandler;
import ab.common.entity.EntitySeed;
import ab.common.item.ItemMod;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.ModItems;

public class ItemSprawlRod extends ItemMod implements IManaUsingItem {

    public ItemSprawlRod() {
        super("sprawlRod");
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setMaxDamage(100);
    }

    public boolean usesMana(ItemStack stack) {
        return true;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    public boolean isFull3D() {
        return true;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if (!world.isRemote && player instanceof EntityPlayer
                && stack.getItemDamage() > 0
                && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, 760, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
    }

    public ItemStack onItemRightClick(ItemStack rod, World world, EntityPlayer p) {
        if (rod.getItemDamage() != 0) return rod;
        ItemStack seed = null;
        for (int i = 0; i < p.inventory.getSizeInventory(); i++) {
            ItemStack slot = p.inventory.getStackInSlot(i);
            if (slot == null) continue;
            if (slot.getItem() == ModItems.grassSeeds) {
                seed = slot;
                break;
            }
        }
        if (seed == null) return rod;
        p.setItemInUse(rod, getMaxItemUseDuration(rod));
        return rod;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer p, int time) {
        if (p.worldObj.isRemote) {
            time = this.getMaxItemUseDuration(stack) - time;
            if (time % 2 == 0 && time != 0) return;
            ItemStack seed = null;
            for (int i = 0; i < p.inventory.getSizeInventory(); i++) {
                ItemStack slot = p.inventory.getStackInSlot(i);
                if (slot == null) continue;
                if (slot.getItem() == ModItems.grassSeeds) {
                    seed = slot;
                    break;
                }
            }
            if (seed == null) return;
            int ticks = Math.min(128, time);
            float fTicks = ticks / 128.0f;
            Vec3 look = p.getLookVec();
            double posX = p.posX + (look.xCoord * 1.4f) + ((Math.random() - 0.5f) * fTicks * 0.3f);
            double posY = p.posY + (look.yCoord * 1.4f) + ((Math.random() - 0.5f) * fTicks * 0.3f);
            double posZ = p.posZ + (look.zCoord * 1.4f) + ((Math.random() - 0.5f) * fTicks * 0.3f);
            Color color = getSeedColor(seed);
            Botania.proxy.wispFX(
                    p.worldObj,
                    posX,
                    posY,
                    posZ,
                    color.getRed() / 255.0f,
                    color.getGreen() / 255.0f,
                    color.getBlue() / 255.0f,
                    0.5F * fTicks - (float) (Math.random() * 0.1f),
                    0.0f,
                    0.5F);
        }
    }

    public void onPlayerStoppedUsing(ItemStack rod, World world, EntityPlayer player, int lastTime) {
        int useTime = getMaxItemUseDuration(rod) - lastTime;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack == null) continue;
            if (stack.getItem() == ModItems.grassSeeds) {
                if (!world.isRemote) {
                    EntitySeed entitySeed = new EntitySeed(world, player);
                    entitySeed.setSeed(stack.copy());
                    entitySeed
                            .setRadius((int) ((Math.min(useTime, 128.0f) / 128.0f) * ConfigABHandler.sprawlRodMaxArea));
                    entitySeed.setAttacker(player.getCommandSenderName());
                    world.spawnEntityInWorld(entitySeed);
                }
                if (stack.stackSize > 1) stack.stackSize--;
                else player.inventory.setInventorySlotContents(i, null);
                if (!player.capabilities.isCreativeMode)
                    rod.setItemDamage(Math.min(100, (int) (useTime / 128.0f * 100.0f)));
                break;
            }
        }
    }

    public static Color getSeedColor(ItemStack seed) {
        int meta = seed.getItemDamage();
        float r = 0.0f;
        float g = 0.4F;
        float b = 0.0f;
        switch (meta) {
            case 1:
                r = 0.5F;
                g = 0.37F;
                b = 0.0F;
                break;
            case 2:
                r = 0.27F;
                g = 0.0F;
                b = 0.33F;
                break;
            case 3:
                r = 0.4F;
                g = 0.5F;
                b = 0.05F;
                break;
            case 4:
                r = 0.75F;
                g = 0.7F;
                b = 0.0F;
                break;
            case 5:
                r = 0.0F;
                g = 0.5F;
                b = 0.1F;
                break;
            case 6:
                r = 0.75F;
                g = 0.0F;
                b = 0.0F;
                break;
            case 7:
                r = 0.0F;
                g = 0.55F;
                b = 0.55F;
                break;
            case 8:
                r = 0.4F;
                g = 0.1F;
                b = 0.4F;
                break;
        }
        return new Color(r, g, b);
    }
}
