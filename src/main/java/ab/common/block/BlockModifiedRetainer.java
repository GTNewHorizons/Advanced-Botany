package ab.common.block;

import java.util.Random;

import ab.common.block.tile.TileModifiedRetainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.corporea.BlockCorporeaRetainer;

public class BlockModifiedRetainer extends BlockCorporeaRetainer {
	
	public IIcon[] icons;

	public BlockModifiedRetainer() {
		super();
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister ir) {
		icons = new IIcon[1];
		this.icons[0] = ir.registerIcon("ab:retainer_rune");
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int s) {
		return ((TileModifiedRetainer) world.getTileEntity(x, y, z)).hasPendingRequest() ? 15 : 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileModifiedRetainer();
	}
	
	@Override
	public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(ModBlocks.corporeaRetainer);
    }
	
	@Override
	public IIcon getIcon(int par1, int par2) {
		return ModBlocks.corporeaRetainer.getIcon(par1, par2);
	}
}
