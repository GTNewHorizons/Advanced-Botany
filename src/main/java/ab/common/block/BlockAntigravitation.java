package ab.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;

public class BlockAntigravitation extends Block {

    public BlockAntigravitation() {
        super(Material.ground);
        this.setHardness(-1.0f);
        this.setTickRandomly(true);
        this.useNeighborBrightness = false;
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public int getRenderType() {
        return -1;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
        return false;
    }

    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_,
            int p_149690_5_, float p_149690_6_, int p_149690_7_) {}

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (world.isAirBlock(x, y + 1, z)) world.setBlockToAir(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (random.nextBoolean()) {
            float r = random.nextInt(128) / 255.0f;
            float g = (random.nextInt(128) + 70) / 255.0f;
            float b = 200.0f / 255.0f;
            Botania.proxy.wispFX(
                    world,
                    x + 0.1f + Math.random() * 0.8f,
                    y - 0.18f + 1.0f,
                    z + 0.1 + Math.random() * 0.8,
                    r,
                    g,
                    b,
                    0.24F * (float) (Math.random() - 0.5D),
                    0.0f,
                    (float) (0.001f + Math.random() * 0.01f),
                    0.0f);
        }
    }
}
