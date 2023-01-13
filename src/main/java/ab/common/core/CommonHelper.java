package ab.common.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

public class CommonHelper {

	public static void fertilizer(World world, Block block, int xCoord, int yCoord, int zCoord, int count, EntityPlayer player) {
		if(world.isRemote || !(block instanceof IGrowable) || block instanceof BlockSapling || world.getTileEntity(xCoord, yCoord, zCoord) != null)
			return;
		
		if(player != null) {
			BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(xCoord, yCoord, zCoord, world, world.getBlock(xCoord, yCoord, zCoord), world.getBlockMetadata(xCoord, yCoord, zCoord), player);
			MinecraftForge.EVENT_BUS.post(event);
			if(event.isCanceled())
				return;
		}
		
		IGrowable igrowable = (IGrowable)block;
		if(igrowable.func_149851_a(world, xCoord, yCoord, zCoord, world.isRemote))
			if(igrowable.func_149852_a(world, world.rand, xCoord, yCoord, zCoord)) {
				int meta = world.getBlockMetadata(xCoord, yCoord, zCoord);
				for(int i = 0; i < count; i++) {
					if(meta != world.getBlockMetadata(xCoord, yCoord, zCoord))
						return;
					igrowable.func_149853_b(world, world.rand, xCoord, yCoord, zCoord);
				}
			}
	}
	
	public static boolean setBlock(World world, Block block, int meta, int x, int y, int z, EntityPlayer player, boolean checkAir) {
		if(!world.isRemote) {
			if(checkAir && !(world.getBlock(x, y, z).getMaterial() == Material.air))
				return false;
			BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(x, y, z, world, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z), player);
			MinecraftForge.EVENT_BUS.post(event);
			if(event.isCanceled())
				return false;
	        return setBlockWithY(world, block, meta, x, y, z);
		}
		return false;
	}
	
	private static boolean setBlockWithY(World world, Block block, int meta, int x, int y, int z) {
		if(y < 256)
			world.setBlock(x, y, z, block, meta, 3);
		else
			return false;
		return true;
	}
}
