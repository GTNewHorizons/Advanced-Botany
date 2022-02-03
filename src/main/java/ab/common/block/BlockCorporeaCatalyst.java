package ab.common.block;

import java.util.Random;

import ab.AdvancedBotany;
import ab.api.ICorporeaModification;
import ab.common.block.tile.TileCorporeaCatalyst;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.material.ItemRune;

public class BlockCorporeaCatalyst extends Block implements ITileEntityProvider, IWandable {

	public IIcon[] icon;

	public BlockCorporeaCatalyst() {
		super(Material.rock);
		this.icon = new IIcon[4];
		setHardness(5.5F);
		this.setCreativeTab(AdvancedBotany.tabAB);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister ir) {
		// this.icon[0] = ModBlocks.livingrock.getIcon(0, 0);
		this.icon[0] = ir.registerIcon("ab:corporeaCatalyst");
		this.icon[1] = ir.registerIcon("ab:goodPicture");
		this.icon[2] = ir.registerIcon("ab:star_sign");
		this.icon[3] = ir.registerIcon("ab:corporea_downside");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int par1, final int par2) {
		return ModBlocks.livingrock.getIcon(0, 0);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {
		int meta = world.getBlockMetadata(x, y, z);
		if (player.isSneaking() && meta != 0) {
			if (!world.isRemote)
				world.spawnEntityInWorld(
						new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.rune, 1, meta - 1)));
			world.setBlockMetadataWithNotify(x, y, z, 0, 0);
			return true;
		}
		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRune) {
			int damage = player.getHeldItem().getItemDamage();
			if (meta == 0 && damage < 4) {
				world.setBlockMetadataWithNotify(x, y, z, damage + 1, 1);
				player.getHeldItem().stackSize--;
				((TileCorporeaCatalyst) world.getTileEntity(x, y, z)).sideRune = side;
				return true;
			}
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		Random random = world.rand;
		if (meta != 0) {

			ItemStack itemstack = new ItemStack(ModItems.rune, 1, meta - 1);
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;
			EntityItem entityitem;

			entityitem = new EntityItem(world, x + f, y + f1, z + f2, itemstack);
			float f3 = 0.05F;
			entityitem.motionX = (float) random.nextGaussian() * f3;
			entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float) random.nextGaussian() * f3;
			world.spawnEntityInWorld(entityitem);

			world.func_147453_f(x, y, z, block);
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int oldMeta) {
		TileCorporeaCatalyst tile = (TileCorporeaCatalyst) world.getTileEntity(x, y, z);
		for (ChunkCoordinates coords : tile.getBindings()) {
			TileEntity target = world.getTileEntity(coords.posX, coords.posY, coords.posZ);
			if (target instanceof ICorporeaModification) {
				((ICorporeaModification) target).removeModification();
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(target);
			}
		}
	}

	@Override
	public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
		return true;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileCorporeaCatalyst();
	}

}
