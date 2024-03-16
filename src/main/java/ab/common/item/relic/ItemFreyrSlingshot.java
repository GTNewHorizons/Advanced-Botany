package ab.common.item.relic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ab.common.entity.EntityManaVine;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemFreyrSlingshot extends ItemModRelic implements IManaUsingItem {

    protected static final int MAX_MANA = 50000;

    private static final String TAG_MANA = "mana";

    public ItemFreyrSlingshot() {
        super("freyrSlingshot");
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4) {
        int j = getMaxItemUseDuration(stack) - par4;
        float f = j / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f < 1.0F) return;
        if (!world.isRemote && ManaItemHandler.requestManaExactForTool(stack, player, 5000, true)) {
            EntityManaVine ball = new EntityManaVine(world, player);
            ball.motionX *= 0.9D;
            ball.motionY *= 0.9D;
            ball.motionZ *= 0.9D;
            ball.setAttacker(player.getCommandSenderName());
            world.spawnEntityInWorld(ball);
            player.worldObj.playSoundAtEntity(player, "ab:freyrSlingshot", 0.4F, 2.8F);
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (ManaItemHandler.requestManaExactForTool(stack, player, 5000, false))
            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 42000;
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    public boolean usesMana(ItemStack stack) {
        return true;
    }
}
