package ab.common.item.equipment;

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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemFreyrSlingshot extends ItemMod implements IManaItem, IManaTooltipDisplay, IManaUsingItem {

	protected static final int MAX_MANA = 50000;

	private static final String TAG_MANA = "mana";

	public ItemFreyrSlingshot() {
		super("freyrSlingshot");
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
	}
	
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, 1500, true))
			this.addMana(stack, 1000);
	}
	
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4) {
		int j = getMaxItemUseDuration(stack) - par4;
		float f = j / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;	
		if(f < 1.0F)
			return;	
		if(!world.isRemote) {
			EntityManaVine ball = new EntityManaVine(player.worldObj, (EntityLivingBase)player);
			ball.motionX *= 0.9D;
			ball.motionY *= 0.9D;
			ball.motionZ *= 0.9D;
			world.spawnEntityInWorld(ball);
		}
		int manaVal = Math.min(10000, this.getMana(stack));
		if(!ManaItemHandler.requestManaExactForTool(stack, player, manaVal, true)) {
			this.addMana(stack, -manaVal);
		}
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(this.getMana(par1ItemStack) >= 10000)
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 42000;
	}
	
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}
	
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return Integer.MAX_VALUE;
	}
	
	public int getDamage(ItemStack stack) {
		float mana = getMana(stack);
		return 1000 - (int)(mana / getMaxMana(stack) * 1000.0F);
	}
	  
	public int getDisplayDamage(ItemStack stack) {
		return getDamage(stack);
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	public static void setMana(ItemStack stack, int mana) {
		ItemNBTHelper.setInt(stack, TAG_MANA, mana);
	}

	public void addMana(ItemStack stack, int mana) {
		setMana(stack, Math.min(getMana(stack) + mana, getMaxMana(stack)));
		stack.setItemDamage(getDamage(stack));
	}

	public boolean canExportManaToItem(ItemStack arg0, ItemStack arg1) {
		return false;
	}
	
	public boolean canExportManaToPool(ItemStack arg0, TileEntity arg1) {
		return false;
	}

	public boolean canReceiveManaFromItem(ItemStack arg0, ItemStack arg1) {
		return true;
	}

	public boolean canReceiveManaFromPool(ItemStack arg0, TileEntity arg1) {
		return true;
	}

	public int getMana(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
	}

	public int getMaxMana(ItemStack arg0) {
		return MAX_MANA;
	}

	public boolean isNoExport(ItemStack arg0) {
		return true;
	}

	public float getManaFractionForDisplay(ItemStack stack) {
		return (float) getMana(stack) / (float) getMaxMana(stack);
	}

	public boolean usesMana(ItemStack stack) {
		return true;
	}
}