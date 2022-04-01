package ab.common.core;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class CommonHelper {

	public static void fertilizer(World world, Block block, int xCoord, int yCoord, int zCoord, int count) {
		if(!(block instanceof IGrowable))
			return;
		IGrowable igrowable = (IGrowable)block;
		if(igrowable.func_149851_a(world, xCoord, yCoord, zCoord, world.isRemote))
			if(!world.isRemote)
				if(igrowable.func_149852_a(world, world.rand, xCoord, yCoord, zCoord))
					for(int i = 0; i < count; i++)
						igrowable.func_149853_b(world, world.rand, xCoord, yCoord, zCoord);
	}
	
	public static boolean setBlock(World world, Block block, int meta, int x, int y, int z, EntityPlayerMP player, boolean checkAir) {
		if(!world.isRemote) {
			if(checkAir && !(world.getBlock(x, y, z).getMaterial() == Material.air))
				return false;
			BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.mcServer.getGameType(), player, x, y, z);
	        if(event.isCanceled())
	            return false;
	        return setBlockWithY(world, block, meta, x, y, z);
		}
		return false;
	}
	
	private static boolean setBlockWithY(World world, Block block, int meta, int x, int y, int z) {
		if(y < 256 && y > 5)
			world.setBlock(x, y, z, block, meta, 3);
		else
			return false;
		return true;
	}
}
