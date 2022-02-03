package ab.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFreyrLiana extends BlockBush {

	public BlockFreyrLiana() {
		super(Material.plants);
		this.setBlockName("BlockFreyrLiana");
		this.setStepSound(soundTypeGrass);
		this.setBlockTextureName("ab:freyrLiana");
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return world.getBlock(x, y + 1, z).getMaterial() == Material.leaves
				|| world.getBlock(x, y + 1, z).getMaterial() == Material.wood
				|| world.getBlock(x, y + 1, z).getMaterial() == Material.ground
				|| world.getBlock(x, y + 1, z).getMaterial() == Material.rock
				|| world.getBlock(x, y + 1, z).getMaterial() == Material.sand
						|| world.getBlock(x, y + 1, z).getMaterial() == Material.grass
				|| world.getBlock(x, y + 1, z) instanceof BlockFreyrLiana;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		this.checkAndDropBlock(world, x, y, z);
		world.markBlockForUpdate(x, y, z);

		for (int i1 = 1; i1 < 10; i1++) {
			Block block = world.getBlock(x, y - i1, z);
			if (block instanceof IGrowable || block instanceof BlockBush && !(block instanceof BlockFreyrLiana)) {
				for (int i = 0; i < 5 + rand.nextInt(4); i++)
					block.updateTick(world, x, y - i1, z, rand);
				break;
			}
		}
	}

	@Override
	protected void checkAndDropBlock(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_) {
		if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_)) {
			this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_,
					p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
			p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 3);
		}
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return this.canPlaceBlockAt(world, x, y, z);
	}

	public int getFlammability(final IBlockAccess world, final int x, final int y, final int z,
			final ForgeDirection face) {
		return 100;
	}

	public int getFireSpreadSpeed(final IBlockAccess world, final int x, final int y, final int z,
			final ForgeDirection face) {
		return 60;
	}
}
