package ab.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import ab.common.core.CommonHelper;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

public class BlockFreyrLiana extends BlockBush implements ILexiconable {

    public BlockFreyrLiana() {
        super(Material.plants);
        this.setBlockName("BlockFreyrLiana");
        this.setStepSound(soundTypeGrass);
        this.setBlockTextureName("ab:freyrLiana");
    }

    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        return RecipeListAB.freyrSlingshot;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.getBlock(x, y + 1, z).getMaterial() == Material.leaves
                || world.getBlock(x, y + 1, z).getMaterial() == Material.wood
                || world.getBlock(x, y + 1, z).getMaterial() == Material.ground
                || world.getBlock(x, y + 1, z).getMaterial() == Material.rock
                || world.getBlock(x, y + 1, z).getMaterial() == Material.sand
                || world.getBlock(x, y + 1, z).getMaterial() == Material.grass
                || world.getBlock(x, y + 1, z) instanceof BlockFreyrLiana;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        this.checkAndDropBlock(world, x, y, z);
        world.markBlockForUpdate(x, y, z);
        for (int i1 = 1; i1 < 5; i1++) {
            Block block = world.getBlock(x, y - i1, z);
            if (block instanceof IGrowable) {
                CommonHelper.fertilizer(world, block, x, y - i1, z, 18, null);
                if (world.getBlock(x, y - i1 - 1, z) == BlockListAB.blockTerraFarmland)
                    world.getBlock(x, y - i1 - 1, z).updateTick(world, x, y - i1 - 1, z, rand);
                break;
            }
        }
    }

    protected void checkAndDropBlock(World world, int x, int y, int z) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlock(x, y, z, getBlockById(0), 0, 3);
        }
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

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
