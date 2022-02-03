package ab.common.block;

import java.util.Random;

import ab.common.block.tile.TileModifiedInterceptor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.corporea.BlockCorporeaBase;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;

public class BlockModifiedInterceptor extends BlockCorporeaBase  {
	
	public IIcon[] icons;

	public BlockModifiedInterceptor() {
		super(Material.iron, "CorporeaInterceptor");
		setHardness(5.5F);
		setStepSound(soundTypeMetal);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister ir) {
		icons = new IIcon[1];
		this.icons[0] = ir.registerIcon("ab:interceptor_rune");
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		world.setBlockMetadataWithNotify(x, y, z, 0, 3);
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@Override
	public int tickRate(World world) {
		return 2;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return world.getBlockMetadata(x, y, z) != 0 ? 15 : 0;
	}

	@Override
	public TileCorporeaBase createNewTileEntity(World world, int meta) {
		return new TileModifiedInterceptor();
	}
	
	@Override
	public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(ModBlocks.corporeaInterceptor);
    }
	
	@Override
	public IIcon getIcon(int par1, int par2) {
		return ModBlocks.corporeaInterceptor.getIcon(par1, par2);
	}

}
