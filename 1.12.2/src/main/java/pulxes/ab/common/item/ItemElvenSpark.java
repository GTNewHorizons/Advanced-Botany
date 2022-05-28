package pulxes.ab.common.item;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pulxes.ab.common.entity.EntityElvenSpark;
import pulxes.ab.common.lib.LibItemNames;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.spark.ISparkAttachable;

public class ItemElvenSpark extends ItemMod implements IManaGivingItem {

	public ItemElvenSpark() {
		super(LibItemNames.ELVEN_SPARK);
	}
	
	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xv, float yv, float zv) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof ISparkAttachable) {
			ISparkAttachable attach = (ISparkAttachable)tile;
			ItemStack stack = player.getHeldItem(hand);
			if(attach.canAttachSpark(stack) && attach.getAttachedSpark() == null) {
				if(!world.isRemote) {
					stack.shrink(1);
					EntityElvenSpark spark = new EntityElvenSpark(world);
					spark.setPosition(pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D);
					world.spawnEntity(spark);
					attach.attachSpark(spark);
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);
				} 
				return EnumActionResult.SUCCESS;
			} 
		} 
		return EnumActionResult.PASS;
	}
}