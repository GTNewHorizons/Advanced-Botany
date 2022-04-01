package ab.common.item.relic;

import java.util.List;

import ab.AdvancedBotany;
import ab.common.entity.EntityManaVine;
import ab.common.item.ItemMod;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.achievement.AchievementMod;
import vazkii.botania.common.core.helper.ItemNBTHelper;

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
		if(f < 1.0F)
			return;
		if(!world.isRemote && ManaItemHandler.requestManaExactForTool(stack, player, 5000, true)) {
			EntityManaVine ball = new EntityManaVine(world, player);
			ball.motionX *= 0.9D;
			ball.motionY *= 0.9D;
			ball.motionZ *= 0.9D;
			ball.setAttacker(player.getCommandSenderName());
			world.spawnEntityInWorld(ball);
		}
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(ManaItemHandler.requestManaExactForTool(stack, player, 5000, false))
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