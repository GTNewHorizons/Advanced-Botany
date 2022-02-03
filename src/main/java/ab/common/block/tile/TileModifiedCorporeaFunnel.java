package ab.common.block.tile;

import java.util.ArrayList;
import java.util.List;

import ab.api.AdvancedCorporeaHelper;
import ab.api.ICorporeaModification;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.utils.Pair;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.ICorporeaRequestor;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.block.tile.corporea.TileCorporeaFunnel;
import vazkii.botania.common.core.helper.InventoryHelper;
import vazkii.botania.common.lib.LibMisc;

public class TileModifiedCorporeaFunnel extends TileCorporeaBase implements ICorporeaRequestor, ICorporeaModification {

	ChunkCoordinates coords;

	public void doRequest() {
		ICorporeaSpark spark = getSpark();
		if (spark == null || spark.getMaster() == null)
			return;

		List<ItemStack> filter = getFilter();
		if(filter.isEmpty())
			return;
		ItemStack stack = filter.get(worldObj.rand.nextInt(filter.size()));
		int rune = this.getCatalystTile().getRune();
		if (filter.isEmpty())
			return;
		switch (rune) {
		case 0:
			int count = AdvancedCorporeaHelper.sumStackSizes(filter);
			this.doCorporeaRequest(null, count, spark);
			break;
		case -1:
		case 1:
		case 2:
		case 3:
			if (stack != null)
				this.doCorporeaRequest(stack, stack.stackSize, spark);
			break;
		}
	}

	public List<ItemStack> getFilter() {
		List<ItemStack> filter = new ArrayList();

		final int[] orientationToDir = new int[] { 3, 4, 2, 5 };
		final int[] rotationToStackSize = new int[] { 1, 16, 32, 64 };

		for (ForgeDirection dir : LibMisc.CARDINAL_DIRECTIONS) {
			List<EntityItemFrame> frames = worldObj.getEntitiesWithinAABB(EntityItemFrame.class,
					AxisAlignedBB.getBoundingBox(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ,
							xCoord + dir.offsetX + 1, yCoord + dir.offsetY + 1, zCoord + dir.offsetZ + 1));
			for (EntityItemFrame frame : frames) {
				int orientation = frame.hangingDirection;
				if (orientationToDir[orientation] == dir.ordinal()) {
					ItemStack stack = frame.getDisplayedItem();
					if (stack != null) {
						ItemStack copy = stack.copy();
						copy.stackSize = rotationToStackSize[frame.getRotation()];
						filter.add(copy);
					}
				}
			}
		}

		return filter;
	}

	@Override
	public void doCorporeaRequest(Object request, int count, ICorporeaSpark spark) {
		int rune = this.getCatalystTile().getRune();
		IInventory inv = InventoryHelper.getInventory(worldObj, xCoord, yCoord - 1, zCoord);
		if (inv == null || inv instanceof TileCorporeaFunnel)
			inv = InventoryHelper.getInventory(worldObj, xCoord, yCoord - 2, zCoord);
		switch (rune) {
		case -1:
			if (!(request instanceof ItemStack))
				return;
			if (inv == null || inv instanceof TileCorporeaFunnel)
				inv = InventoryHelper.getInventory(worldObj, xCoord, yCoord - 2, zCoord);

			List<ItemStack> stacks$ = CorporeaHelper.requestItem(request, count, spark, true, true);
			spark.onItemsRequested(stacks$);
			for (ItemStack reqStack : stacks$)
				if (request != null) {
					if (inv != null && !(inv instanceof TileCorporeaFunnel) && reqStack.stackSize == InventoryHelper
							.testInventoryInsertion(inv, reqStack, ForgeDirection.UP))
						InventoryHelper.insertItemIntoInventory(inv, reqStack);
					else {
						EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, reqStack);
						worldObj.spawnEntityInWorld(item);
					}
				}
			break;
		case 0:
			List<ItemStack> stacks = AdvancedCorporeaHelper.requestRandomItem(spark, count, true);
			spark.onItemsRequested(stacks);
			for (ItemStack reqStack : stacks)
				if (inv != null && !(inv instanceof TileCorporeaFunnel) && reqStack.stackSize == InventoryHelper
						.testInventoryInsertion(inv, reqStack, ForgeDirection.UP))
					InventoryHelper.insertItemIntoInventory(inv, reqStack);
				else {
					EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, reqStack);
					worldObj.spawnEntityInWorld(item);
				}
			break;
		case 1:
			if (request instanceof ItemStack) {
				List<ItemStack> stacks1 = CorporeaHelper.requestItem(request, -1, spark, true, true);

				for (ItemStack reqStack : stacks1)
					if (inv != null && !(inv instanceof TileCorporeaFunnel) && reqStack.stackSize == InventoryHelper
							.testInventoryInsertion(inv, reqStack, ForgeDirection.UP))
						InventoryHelper.insertItemIntoInventory(inv, reqStack);
					else {
						EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, reqStack);
						worldObj.spawnEntityInWorld(item);
					}
			}
			break;
		case 2:
			String name;
			List<ItemStack> stacks2;
			if (request instanceof ItemStack) {
				name = ((ItemStack) request).getDisplayName();
				stacks2 = AdvancedCorporeaHelper.requestSameNamedItem(name, (ItemStack) request, spark,
						((ItemStack) request).stackSize, true);
			} else if (request instanceof String) {
				name = (String) request;
				stacks2 = AdvancedCorporeaHelper.requestSameNamedItem(name, null, spark, 1, true);
			} else
				return;

			spark.onItemsRequested(stacks2);
			for (ItemStack reqStack : stacks2)
				if (inv != null && !(inv instanceof TileCorporeaFunnel) && reqStack.stackSize == InventoryHelper
						.testInventoryInsertion(inv, reqStack, ForgeDirection.UP))
					InventoryHelper.insertItemIntoInventory(inv, reqStack);
				else {
					EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, reqStack);
					worldObj.spawnEntityInWorld(item);
				}
			break;
		case 3:
			ItemStack stack = null;
			if (request instanceof Pair) {
				Pair pair = (Pair) request;
				if (pair.getKey() instanceof IInventory && pair.getValue() instanceof Integer) {
					stack = AdvancedCorporeaHelper.getStackBySlot((IInventory) pair.getKey(), (Integer) pair.getValue(),
							count, spark, true);
				}
			} else if (request instanceof ItemStack) {
				Pair pair = AdvancedCorporeaHelper.getFirstInventoryWithStackByRequest(spark, (ItemStack) request);
				if (pair.getKey() != null)
					stack = AdvancedCorporeaHelper.getStackBySlot((IInventory) pair.getKey(),
							(Integer) pair.getValue() + 1, count, spark, true);
			}
			if (stack != null)
				if (inv != null && !(inv instanceof TileCorporeaFunnel)
						&& stack.stackSize == InventoryHelper.testInventoryInsertion(inv, stack, ForgeDirection.UP))
					InventoryHelper.insertItemIntoInventory(inv, stack);
				else {
					EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, stack);
					worldObj.spawnEntityInWorld(item);
				}
			break;
		}
	}

	@Override
	public void setCatalystCoords(int x, int y, int z) {
		this.coords = new ChunkCoordinates(x, y, z);
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
		return "TileModifiedCorporeaFunnel";
	}

	@Override
	public TileCorporeaCatalyst getCatalystTile() {
		if (this.coords != null)
			return (TileCorporeaCatalyst) this.worldObj.getTileEntity(this.coords.posX, this.coords.posY,
					this.coords.posZ);
		return null;
	}

	@Override
	public boolean isModified() {
		return true;
	}

	@Override
	public void removeModification() {
		this.worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.corporeaFunnel);
	}

}
