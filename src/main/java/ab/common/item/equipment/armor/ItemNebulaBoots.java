package ab.common.item.equipment.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.common.Botania;

public class ItemNebulaBoots extends ItemNebulaArmor {

    private static final float MAX_SPEED = 0.275f;
    public static List<String> playersWithStepup = new ArrayList<String>();

    public ItemNebulaBoots() {
        super(3, "nebulaBoots");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);
        if (world.isRemote && player.getCurrentArmor(0) != null
                && player.getCurrentArmor(0).getItem() instanceof ItemNebulaBoots
                && player.isSprinting()) {
            float w = 0.6F;
            float c = 1.0F - w;
            float r = w + (float) Math.random() * c;
            float g = w + (float) Math.random() * c;
            float b = w + (float) Math.random() * c;
            for (int i = 0; i < 2; i++) Botania.proxy.sparkleFX(
                    world,
                    player.posX + (Math.random() - 0.5f),
                    player.posY - 1.25f + (Math.random() / 4 - 0.125f),
                    player.posZ + (Math.random() - 0.5f),
                    r,
                    g,
                    b,
                    (float) (0.7f + (Math.random() / 2)),
                    25);
        }
    }

    @SubscribeEvent
    public void updatePlayerStepStatus(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            ItemStack armor = player.getCurrentArmor(0);
            String s = playerStr(player);
            if (playersWithStepup.contains(s)) {
                if (shouldPlayerHaveStepup(player)) {
                    if ((player.onGround || player.capabilities.isFlying) && player.moveForward > 0.0F) {
                        float speed = getSpeed(armor) * (player.isSprinting() ? 1.0f : 0.2f);
                        player.moveFlying(0.0F, 1.0F, player.capabilities.isFlying ? speed * 0.6f : speed);
                    }
                    if (player.isSneaking()) {
                        player.stepHeight = 0.50001F;
                    } else {
                        player.stepHeight = 1.0F;
                    }
                } else {
                    player.stepHeight = 0.5F;
                    playersWithStepup.remove(s);
                }
            } else if (shouldPlayerHaveStepup(player)) {
                playersWithStepup.add(s);
                player.stepHeight = 1.0F;
            }
        }
    }

    private boolean shouldPlayerHaveStepup(EntityPlayer player) {
        ItemStack armor = player.getCurrentArmor(0);
        return (armor != null && armor.getItem() instanceof ItemNebulaBoots);
    }

    private float getSpeed(ItemStack stack) {
        return MAX_SPEED * (1.0f - (this.getDamage(stack) / 1000.0f));
    }
}
