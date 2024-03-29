package ab.common.item.equipment;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import ab.AdvancedBotany;
import baubles.api.BaubleType;

public class ItemManaFlower extends ItemBauble implements IManaGivingItem {

    public ItemManaFlower() {
        super("manaFlower");
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setNoRepair();
    }

    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        super.onWornTick(stack, player);
        if (player instanceof EntityPlayer && player.ticksExisted % 5 == 0)
            ManaItemHandler.dispatchManaExact(stack, (EntityPlayer) player, 54, true);
    }

    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.BELT;
    }
}
