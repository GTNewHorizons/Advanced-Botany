package ab.common.item.equipment.armor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemNebulaChest extends ItemNebulaArmor {

    public static List<String> playersWithFlight = new ArrayList<String>();

    public ItemNebulaChest() {
        super(1, "nebulaChest");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public Multimap getAttributeModifiers(ItemStack stack) {
        HashMultimap hashMultimap = HashMultimap.create();
        UUID uuid = new UUID(getUnlocalizedName().hashCode(), 0L);
        hashMultimap.put(
                SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(),
                new AttributeModifier(
                        uuid,
                        "NebulaChest modifier",
                        1.0D * (1.0f - (this.getDamage(stack) / 1000.0f)),
                        0));
        return (Multimap) hashMultimap;
    }

    @SubscribeEvent
    public void updatePlayerFlyStatus(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (playersWithFlight.contains(playerStr(player))) {
                if (shouldPlayerHaveFlight(player)) {
                    player.capabilities.allowFlying = true;
                } else {
                    if (!player.capabilities.isCreativeMode) {
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        player.capabilities.disableDamage = false;
                    }
                    playersWithFlight.remove(playerStr(player));
                }
            } else if (shouldPlayerHaveFlight(player)) {
                playersWithFlight.add(playerStr(player));
                player.capabilities.allowFlying = true;
            }
        }
    }

    private static boolean shouldPlayerHaveFlight(EntityPlayer player) {
        ItemStack armor = player.getCurrentArmor(2);
        return (armor != null && armor.getItem() instanceof ItemNebulaChest);
    }
}
