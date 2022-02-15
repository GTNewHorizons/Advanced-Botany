package ab.common.block;

import ab.AdvancedBotany;
import ab.common.block.tile.TileAgglomerationPlate;
import ab.common.lib.register.RecipeListAB;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.block.tile.TileSimpleInventory;

public class BlockAgglomerationPlate extends BlockContainer implements ILexiconable {

	private IIcon[] icons;
	
	public BlockAgglomerationPlate() {
	    super(Material.iron);
		this.setCreativeTab(AdvancedBotany.tabAB);
	    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
	    this.setHardness(3.0F);
	    this.setResistance(10.0F);
	    this.setStepSound(soundTypeMetal);
	    this.setBlockName("ABPlate");
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileAgglomerationPlate tile = (TileAgglomerationPlate)world.getTileEntity(x, y, z);	
		if (player.isSneaking()) {
			if(tile.getStackInSlot(0) != null) {
				ItemStack copy = tile.getStackInSlot(0).copy();
				if(!player.inventory.addItemStackToInventory(copy))
					player.dropPlayerItemWithRandomChoice(copy, false); 
				tile.setInventorySlotContents(0, null);
				world.func_147453_f(x, y, z, (Block)this);
				return true;
			}
			for(int i = tile.getSizeInventory() - 1; i > 0; i--) {
				ItemStack stack = tile.getStackInSlot(i);
				if(stack != null) {
					ItemStack copy = stack.copy();
					System.out.println(i + " " + copy);
					if(!player.inventory.addItemStackToInventory(copy))
						player.dropPlayerItemWithRandomChoice(copy, false); 
					tile.setInventorySlotContents(i, null);
					world.func_147453_f(x, y, z, (Block)this);
					return true;
				}
			}
		}
		return false;
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if(!world.isRemote) {
			TileSimpleInventory inv = (TileSimpleInventory)world.getTileEntity(x, y, z);
			if (inv != null) {
				for (int i = 0; i < inv.getSizeInventory(); i++) {
					ItemStack stack = inv.getStackInSlot(i);
					if (stack != null) {
						float f = world.rand.nextFloat() * 0.8F + 0.1F;
			            float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
			            float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
			            EntityItem entityitem = new EntityItem(world, (x + f), (y + f1), (z + f2), stack.copy());
			            float f3 = 0.05F;
			            entityitem.motionX = ((float)world.rand.nextGaussian() * f3);
			            entityitem.motionY = ((float)world.rand.nextGaussian() * f3 + 0.2F);
			            entityitem.motionZ = ((float)world.rand.nextGaussian() * f3);
			            if (stack.hasTagCompound())
			            	entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy()); 
			            world.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	public void registerBlockIcons(IIconRegister ir) {
		this.icons = new IIcon[3];
		for (int i = 0; i < this.icons.length; i++)
			this.icons[i] = ir.registerIcon("ab:advancedPlate_" + i); 
	}
	
	public IIcon getIcon(int par1, int par2) {
		return this.icons[Math.min(2, par1)];
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	  
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
		return false;
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileAgglomerationPlate();
	}

	public LexiconEntry getEntry(World arg0, int arg1, int arg2, int arg3, EntityPlayer arg4, ItemStack arg5) {
		return RecipeListAB.advandedAgglomerationPlate;
	}
}
