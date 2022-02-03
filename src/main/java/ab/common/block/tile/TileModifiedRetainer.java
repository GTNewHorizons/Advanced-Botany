package ab.common.block.tile;

import ab.api.ICorporeaModification;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChunkCoordinates;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

public class TileModifiedRetainer extends TileCorporeaRetainer implements ICorporeaModification {
	
	ChunkCoordinates coords = new ChunkCoordinates();

	@Override
	public void setCatalystCoords(int x, int y, int z) {
		coords.set(x, y, z);
	}

	@Override
	public TileCorporeaCatalyst getCatalystTile() {
		return (TileCorporeaCatalyst) worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
	}

	@Override
	public boolean isModified() {
		return true;
	}

	@Override
	public void removeModification() {
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.corporeaRetainer);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtt) {
		super.readFromNBT(nbtt);
		int coordsX = nbtt.getInteger("coordsX");
		int coordsY = nbtt.getInteger("coordsY");
		int coordsZ = nbtt.getInteger("coordsZ");
		this.coords = new ChunkCoordinates(coordsX, coordsY, coordsZ);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtt) {
		super.writeToNBT(nbtt);
		nbtt.setInteger("coordsX", this.coords.posX);
		nbtt.setInteger("coordsY", this.coords.posY);
		nbtt.setInteger("coordsZ", this.coords.posZ);
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
	
}
