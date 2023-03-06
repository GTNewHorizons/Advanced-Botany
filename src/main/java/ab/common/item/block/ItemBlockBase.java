package ab.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

import vazkii.botania.common.achievement.IPickupAchievement;

public class ItemBlockBase extends ItemBlockWithMetadata implements IPickupAchievement {

    public ItemBlockBase(Block block) {
        super(block, block);
    }

    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + stack.getItemDamage();
    }

    public Achievement getAchievementOnPickup(ItemStack stack, EntityPlayer player, EntityItem item) {
        return (this.field_150939_a instanceof IPickupAchievement)
                ? ((IPickupAchievement) this.field_150939_a).getAchievementOnPickup(stack, player, item)
                : null;
    }
}
