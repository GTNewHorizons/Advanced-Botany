package ab.common.item.equipment.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemNebulaLegs extends ItemNebulaArmor {

    public ItemNebulaLegs() {
        super(2, "nebulaLegs");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            ItemStack legs = player.getCurrentArmor(1);
            if (legs != null && legs.getItem() instanceof ItemNebulaLegs) {
                player.motionY += getJump(legs);
                player.fallDistance = -getFallBuffer(legs);
            }
        }
    }

    private float getJump(ItemStack stack) {
        return 0.3f * (1.0f - (this.getDamage(stack) / 1000.0f));
    }

    private float getFallBuffer(ItemStack stack) {
        return 12.0f * (1.0f - (this.getDamage(stack) / 1000.0f));
    }
}
