package ab.common.item.equipment;

import java.util.List;

import ab.AdvancedBotany;
import ab.common.entity.EntityManaVine;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemFreyrSlingshot extends Item implements IManaItem, IManaTooltipDisplay, IManaUsingItem {

	protected static final int MAX_MANA = 150000;

	private static final String TAG_MANA = "mana";

	public ItemFreyrSlingshot() {
		this.setMaxStackSize(1);
		this.setCreativeTab(AdvancedBotany.tabAB);
		this.setUnlocalizedName("item.freyr_slingshot");
		this.setTextureName("ab:freyr_slingshot");
		this.setMaxDamage(1000);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, 1500, true))
			this.addMana(stack, 1000);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4) {
		int j = getMaxItemUseDuration(stack) - par4;
		float f = j / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		
		if(f < 1F)
			return;
		
		if(!world.isRemote) {
			EntityManaVine ball = new EntityManaVine(player.worldObj, (EntityLivingBase)player);
			ball.motionX *= 0.6;
			ball.motionY *= 0.6;
			ball.motionZ *= 0.6;
			world.spawnEntityInWorld(ball);
		}
		
		this.subMana(stack, 5000);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(this.getMana(par1ItemStack) >= 5000)
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 42000;
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}
	
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public int getDamage(ItemStack stack) {
		float mana = getMana(stack);
		return 1000 - (int) (mana / getMaxMana(stack) * 1000);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack) {
		return getDamage(stack);
	}
	
	public static void setMana(ItemStack stack, int mana) {
		ItemNBTHelper.setInt(stack, TAG_MANA, mana);
	}

	@Override
	public void addMana(ItemStack stack, int mana) {
		setMana(stack, Math.min(getMana(stack) + mana, getMaxMana(stack)));
		stack.setItemDamage(getDamage(stack));
	}
	
	public void subMana(ItemStack stack, int mana) {
		setMana(stack, Math.max(getMana(stack) - mana, 0));
		stack.setItemDamage(getDamage(stack));
	}

	@Override
	public boolean canExportManaToItem(ItemStack arg0, ItemStack arg1) {
		return false;
	}

	@Override
	public boolean canExportManaToPool(ItemStack arg0, TileEntity arg1) {
		return false;
	}

	@Override
	public boolean canReceiveManaFromItem(ItemStack arg0, ItemStack arg1) {
		return true;
	}

	@Override
	public boolean canReceiveManaFromPool(ItemStack arg0, TileEntity arg1) {
		return true;
	}

	@Override
	public int getMana(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
	}

	@Override
	public int getMaxMana(ItemStack arg0) {
		return MAX_MANA;
	}

	@Override
	public boolean isNoExport(ItemStack arg0) {
		return true;
	}

	@Override
	public float getManaFractionForDisplay(ItemStack stack) {
		return (float) getMana(stack) / (float) getMaxMana(stack);
	}

	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}

}
