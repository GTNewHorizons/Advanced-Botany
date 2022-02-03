package ab.common.block.tile;

import java.util.ArrayList;
import java.util.List;

import ab.api.AdvancedCorporeaHelper;
import ab.api.ICorporeaModification;
import ab.common.lib.utils.Pair;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.ICorporeaInterceptor;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;
import vazkii.botania.common.lib.LibMisc;

public class TileModifiedInterceptor extends TileCorporeaBase implements ICorporeaInterceptor, ICorporeaModification {

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
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.corporeaInterceptor);
	}

	@Override
	public void interceptRequest(Object request, int count, ICorporeaSpark spark, ICorporeaSpark source,
			List<ItemStack> stacks, List<IInventory> inventories, boolean doit) {
	}

	@Override
	public void interceptRequestLast(Object request, int count, ICorporeaSpark spark, ICorporeaSpark source,
			List<ItemStack> stacks, List<IInventory> inventories, boolean doit) {
		int rune = this.getCatalystTile().getRune();
		Object filter = getFilter();
		switch (rune) {
		case -1:
			if (requestMatches(request, filter)) {
				int missing = count;
				for (ItemStack stack_ : stacks)
					missing -= stack_.stackSize;

				if (missing > 0 && getBlockMetadata() == 0) {
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 1 | 2);
					worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, getBlockType(), 2);

					TileEntity requestor = (TileEntity) source.getInventory();
					for (ForgeDirection dir : LibMisc.CARDINAL_DIRECTIONS) {
						TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
						if (tile != null && tile instanceof TileCorporeaRetainer)
							((TileCorporeaRetainer) tile).setPendingRequest(requestor.xCoord, requestor.yCoord,
									requestor.zCoord, request, count);
					}

					return;
				}
			}
			break;
		case 0:
			if (requestMatches(request, filter)) {

				int missing = count;
				for (ItemStack stack_ : stacks)
					missing -= stack_.stackSize;

				if (missing > 0 && getBlockMetadata() == 0) {
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 1 | 2);
					worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, getBlockType(), 2);

					TileEntity requestor = (TileEntity) source.getInventory();
					for (ForgeDirection dir : LibMisc.CARDINAL_DIRECTIONS) {
						TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
						if (tile != null && tile instanceof TileCorporeaRetainer)
							((TileCorporeaRetainer) tile).setPendingRequest(requestor.xCoord, requestor.yCoord,
									requestor.zCoord,
									AdvancedCorporeaHelper.trasformateRequest(spark, request, ItemStack.class), count);
					}

					return;
				}
			}
			break;
		case 1:
			if (!requestMatches(request, filter)) {
				int missing = count;
				for (ItemStack stack_ : stacks)
					missing -= stack_.stackSize;

				if (missing > 0 && getBlockMetadata() == 0) {
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 1 | 2);
					worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, getBlockType(), 2);

					TileEntity requestor = (TileEntity) source.getInventory();
					for (ForgeDirection dir : LibMisc.CARDINAL_DIRECTIONS) {
						TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
						if (tile != null && tile instanceof TileCorporeaRetainer)
							((TileCorporeaRetainer) tile).setPendingRequest(requestor.xCoord, requestor.yCoord,
									requestor.zCoord, request, count);
					}

					return;
				}
			}
			break;
		case 2:
			if (requestMatches(request, filter)) {

			int missing = count;
			for (ItemStack stack_ : stacks)
				missing -= stack_.stackSize;

			if (missing > 0 && getBlockMetadata() == 0) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 1 | 2);
				worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, getBlockType(), 2);

				TileEntity requestor = (TileEntity) source.getInventory();
				for (ForgeDirection dir : LibMisc.CARDINAL_DIRECTIONS) {
					TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
					if (tile != null && tile instanceof TileCorporeaRetainer)
						((TileCorporeaRetainer) tile).setPendingRequest(requestor.xCoord, requestor.yCoord,
								requestor.zCoord,
								AdvancedCorporeaHelper.trasformateRequest(spark, request, IInventory.class), count);
				}

				return;
			}
		}
		break;
		case 3:
			if (requestMatches(request, filter)) {

				int missing = count;
				for (ItemStack stack_ : stacks)
					missing -= stack_.stackSize;

				if (missing > 0 && getBlockMetadata() == 0) {
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 1 | 2);
					worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, getBlockType(), 2);

					TileEntity requestor = (TileEntity) source.getInventory();
					for (ForgeDirection dir : LibMisc.CARDINAL_DIRECTIONS) {
						TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
						if (tile != null && tile instanceof TileCorporeaRetainer)
							((TileCorporeaRetainer) tile).setPendingRequest(requestor.xCoord, requestor.yCoord,
									requestor.zCoord,
									AdvancedCorporeaHelper.trasformateRequest(spark, request, String.class), count);
					}

					return;
				}
			}
			break;
		}
	}

	public Object getFilter() {
		List<ItemStack> filter = new ArrayList();

		final int[] orientationToDir = new int[] { 3, 4, 2, 5 };

		for (ForgeDirection dir : LibMisc.CARDINAL_DIRECTIONS) {
			List<EntityItemFrame> frames = worldObj.getEntitiesWithinAABB(EntityItemFrame.class,
					AxisAlignedBB.getBoundingBox(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ,
							xCoord + dir.offsetX + 1, yCoord + dir.offsetY + 1, zCoord + dir.offsetZ + 1));
			for (EntityItemFrame frame : frames) {
				int orientation = frame.hangingDirection;
				if (orientationToDir[orientation] == dir.ordinal())
					if (frame.getDisplayedItem() == null)
						return "anyone";
				filter.add(frame.getDisplayedItem());
			}
		}
		return filter;
	}

	private boolean requestMatches(Object request, Object filter) {
		if (filter instanceof String)
			return true;

		if (filter == null)
			return false;

		List<ItemStack> list = (List<ItemStack>) filter;

		for (ItemStack filter$ : list) {
			if (request instanceof ItemStack) {
				ItemStack stack = (ItemStack) request;
				return stack != null && stack.isItemEqual(filter$) && ItemStack.areItemStackTagsEqual(filter$, stack);
			}

			if (request instanceof Pair) {
				Pair pair = (Pair) request;
				Object key = pair.getKey();
				Object value = pair.getValue();
				if (key instanceof IInventory && value instanceof Integer) {
					ItemStack stack = ((IInventory) key).getStackInSlot((Integer) value);
					return stack != null && stack.isItemEqual(filter$)
							&& ItemStack.areItemStackTagsEqual(filter$, stack);
				}
				if (key instanceof String)
					return filter$.getDisplayName().equals((String) key);
			}

			String name = (String) request;
			return CorporeaHelper.stacksMatch(filter$, name);
		}

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
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

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public String getInventoryName() {
		return "TileModifiedInterceptor";
	}

}
