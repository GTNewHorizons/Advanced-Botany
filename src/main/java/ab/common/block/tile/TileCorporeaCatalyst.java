package ab.common.block.tile;

import java.util.LinkedList;
import java.util.List;

import ab.api.ICorporeaModification;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import vazkii.botania.api.wand.IWandBindable;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.corporea.TileCorporeaCrystalCube;
import vazkii.botania.common.block.tile.corporea.TileCorporeaFunnel;
import vazkii.botania.common.block.tile.corporea.TileCorporeaInterceptor;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

public class TileCorporeaCatalyst extends TileEntity implements IWandBindable {

	public int sideRune;
	private List<ChunkCoordinates> bindings = new LinkedList<ChunkCoordinates>();

	@Override
	public ChunkCoordinates getBinding() {
		return new ChunkCoordinates(0, 0, 0);
	}

	public List<ChunkCoordinates> getBindings() {
		return this.bindings;
	}

	@Override
	public void updateEntity() {
		List<ChunkCoordinates> bindingsCopy = new LinkedList(bindings);
		for (ChunkCoordinates coords : bindingsCopy) {
			if (!isModified(coords.posX, coords.posY, coords.posZ)
					|| ((ICorporeaModification) this.worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ))
							.getCatalystTile() != this) {
				bindings.remove(coords);
			}
		}
	}

	private boolean isModified(int x, int y, int z) {
		return this.worldObj.getTileEntity(x, y, z) instanceof ICorporeaModification
				&& ((ICorporeaModification) this.worldObj.getTileEntity(x, y, z)).isModified();
	}

	public boolean modify(int x, int y, int z) {
		Block block = this.worldObj.getBlock(x, y, z);
		if (block == ModBlocks.corporeaFunnel)
			this.worldObj.setBlock(x, y, z, BlockListAB.blockModifiedCorporeaFunnel);
		else if ((block == ModBlocks.corporeaInterceptor))
			this.worldObj.setBlock(x, y, z, BlockListAB.blockModifiedInterceptor);
		else if ((block == ModBlocks.corporeaRetainer))
			this.worldObj.setBlock(x, y, z, BlockListAB.blockModifiedRetainer);
		else
			return false;
		return true;
	}

	@Override
	public boolean canSelect(EntityPlayer player, ItemStack stack, int x, int y, int z, int side) {
		return true;
	}

	public int getRune() {
		return this.getBlockMetadata() - 1;
	}

	@Override
	public boolean bindTo(EntityPlayer player, ItemStack stack, int x, int y, int z, int side) {
		ChunkCoordinates coords = new ChunkCoordinates(x, y, z);
		if (isModified(x, y, z)) {
			if (this.bindings.contains(coords)) {
				this.bindings.remove(coords);
				((ICorporeaModification)worldObj.getTileEntity(x, y, z)).removeModification();
				return true;
			} else
				((ICorporeaModification) this.worldObj.getTileEntity(x, y, z)).getCatalystTile().bindings
						.remove(coords);
		} else if (Math.abs(x - this.xCoord) < 8 && Math.abs(y - this.yCoord) < 8 && Math.abs(z - this.zCoord) < 8
				&& canBeModified(this.worldObj.getTileEntity(x, y, z))) {
			modify(x, y, z);
		} else
			return false;
		bindings.add(coords);
		((ICorporeaModification) this.worldObj.getTileEntity(x, y, z)).setCatalystCoords(this.xCoord, this.yCoord,
				this.zCoord);
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtt) {
		super.readFromNBT(nbtt);
		this.sideRune = nbtt.getInteger("sideRune");
		int[] xCoords = nbtt.getIntArray("xCoords");
		int[] yCoords = nbtt.getIntArray("yCoords");
		int[] zCoords = nbtt.getIntArray("zCoords");

		int i = 0;
		while (i < xCoords.length) {
			bindings.add(new ChunkCoordinates(xCoords[i], yCoords[i], zCoords[i]));
			i++;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtt) {
		super.writeToNBT(nbtt);
		nbtt.setInteger("sideRune", sideRune);

		int[] xCoords = new int[bindings.size()];
		int[] yCoords = new int[bindings.size()];
		int[] zCoords = new int[bindings.size()];
		int i = 0;
		for (ChunkCoordinates coords : bindings) {
			xCoords[i] = coords.posX;
			yCoords[i] = coords.posY;
			zCoords[i] = coords.posZ;
			i++;
		}

		nbtt.setIntArray("xCoords", xCoords);
		nbtt.setIntArray("yCoords", yCoords);
		nbtt.setIntArray("zCoords", zCoords);
	}

	@Override
	public final void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity paket) {
		super.onDataPacket(manager, paket);
		this.readFromNBT(paket.func_148857_g());
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 10, compound);
	}

	private boolean canBeModified(TileEntity tile) {
		return tile instanceof TileCorporeaCrystalCube || tile instanceof TileCorporeaFunnel
				|| tile instanceof TileCorporeaInterceptor || tile instanceof TileCorporeaRetainer
				|| tile instanceof ICorporeaModification;
	}
}
