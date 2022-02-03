package ab.common.block;

import java.util.ArrayList;
import java.util.Random;

import ab.AdvancedBotany;
import ab.common.block.tile.TileModifiedCorporeaFunnel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.corporea.BlockCorporeaBase;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.lib.LibBlockNames;

public class BlockModifiedFunnel extends BlockCorporeaBase {
	
	public IIcon[] icons;
	
	public BlockModifiedFunnel() {
		super(Material.iron, "CorporeaFunnel");
		setHardness(5.5F);
		setStepSound(soundTypeMetal);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister ir) {
		icons = new IIcon[1];
		this.icons[0] = ir.registerIcon("ab:funnel_rune");
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		boolean power = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
		int meta = world.getBlockMetadata(x, y, z);
		boolean powered = (meta & 8) != 0;
		if(power && !powered) {
			((TileModifiedCorporeaFunnel) world.getTileEntity(x, y, z)).doRequest();
			world.setBlockMetadataWithNotify(x, y, z, meta | 8, 3);
		} else if(!power && powered)
			world.setBlockMetadataWithNotify(x, y, z, meta & -9, 3);
	}
	
	@Override
	public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(ModBlocks.corporeaFunnel);
    }
	
	@Override
	public TileCorporeaBase createNewTileEntity(World arg0, int arg1) {
		return new TileModifiedCorporeaFunnel();
	}
	
	@Override
	public IIcon getIcon(int par1, int par2) {
		return ModBlocks.corporeaFunnel.getIcon(par1, par2);
	}
}
