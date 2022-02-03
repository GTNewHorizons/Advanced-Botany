package ab.common.block;

import ab.AdvancedBotany;
import ab.common.block.tile.TileCorporeaInterrupter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.common.block.corporea.BlockCorporeaBase;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;

public class BlockCorporeaInterrupter extends BlockCorporeaBase {
	
	public IIcon icons[];

	public BlockCorporeaInterrupter() {
		super(Material.iron, "CorporeaInterrupter");
		this.icons = new IIcon[3];
		this.setCreativeTab(AdvancedBotany.tabAB);
		setHardness(5.5F);
		setStepSound(soundTypeMetal);
	}

	@Override
	public TileCorporeaBase createNewTileEntity(World world, int meta) {
		return new TileCorporeaInterrupter();
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[side > 1 ? 1 : 0];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		for(int i = 0; i < icons.length; i++) {
			this.icons[2] = ir.registerIcon("ab:interrupter_rune");
			this.icons[1] = ir.registerIcon("ab:CorporeaInterrupter0");
			this.icons[0] = ir.registerIcon("ab:CorporeaInterrupter1");
		}	
	}

}
