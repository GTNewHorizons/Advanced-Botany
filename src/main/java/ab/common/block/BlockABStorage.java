package ab.common.block;

import java.util.List;

import ab.AdvancedBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockABStorage extends Block {

private static IIcon[] icon = new IIcon[1];
	
	public BlockABStorage() {
	    super(Material.iron);
		this.setCreativeTab(AdvancedBotany.tabAB);
		this.setHardness(3.0F);
	    this.setResistance(10.0F);
	    this.setStepSound(soundTypeMetal);
	    this.setBlockName("abStorage");
	}
	
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
		for(int i = 0; i < icon.length; i++) {
			list.add(new ItemStack(par1, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {	
		for (int i = 0; i < icon.length; i++)
			icon[i] = ir.registerIcon("ab:abStorage_" + i);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icon[meta];
	}
	
	public int damageDropped(int par1) {
		return par1;
	}
	
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}
