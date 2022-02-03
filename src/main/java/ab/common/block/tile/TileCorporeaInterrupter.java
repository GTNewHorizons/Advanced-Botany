package ab.common.block.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ab.api.AdvancedCorporeaHelper;
import ab.api.ICorporeaModification;
import ab.common.integration.corporea.ICorporeaInterrupter;
import ab.common.lib.utils.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.CorporeaRequestEvent;
import vazkii.botania.api.corporea.ICorporeaInterceptor;
import vazkii.botania.api.corporea.ICorporeaSpark;
import vazkii.botania.api.corporea.IWrappedInventory;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class TileCorporeaInterrupter extends TileCorporeaBase implements ICorporeaInterrupter, ICorporeaModification {

	ChunkCoordinates coords = new ChunkCoordinates();;
	boolean hasCatalyst = false;
	
	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public String getInventoryName() {
		return "CorporeaInterrupter";
	}

	@Override
	public void onInterrupt(CorporeaRequestEvent event) {
		if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
			return;
		int rune = -1;
		if (this.getCatalystTile() != null)
			rune = this.getCatalystTile().getRune();
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		IInventory inv$ = event.spark.getInventory();
		List<IInventory> inventories = CorporeaHelper.getInventoriesOnNetwork(event.spark);
		List<IWrappedInventory> inventoriesW = AdvancedCorporeaHelper.wrapInventory(inventories);
		Map<ICorporeaInterceptor, ICorporeaSpark> interceptors = new HashMap<ICorporeaInterceptor, ICorporeaSpark>();
		switch (rune) {
		case -1:
			if (this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) == inv$
					|| this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) == inv$)
				event.setCanceled(true);
		case 1:
			if (this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) == inv$
					|| this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) == inv$)
				for (IWrappedInventory invW : inventoriesW) {
					ICorporeaSpark invSpark = invW.getSpark();

					Object originalInventory = invW.getWrappedObject();
					if (originalInventory instanceof ICorporeaInterceptor) {
						ICorporeaInterceptor interceptor = (ICorporeaInterceptor) originalInventory;
						interceptor.interceptRequest(event.request, event.count, invSpark, event.spark, stacks,
								inventories, event.realRequest);
						interceptors.put(interceptor, invSpark);
					}
				}
			break;
		case 2:
			ItemStack stack = null;
			if (event.request instanceof ItemStack)
				stack = ((ItemStack) event.request).copy();
			if (event.request instanceof Pair) {
				Pair pair = (Pair) event.request;
				if (pair.getKey() instanceof IInventory && pair.getValue() instanceof Integer)
					stack = ((IInventory) pair.getKey()).getStackInSlot((Integer) pair.getValue());
				else if (pair.getKey() instanceof String) {

				}
			}
			ItemNBTHelper.initNBT(stack);
			String customName = ItemNBTHelper.getNBT(stack).getCompoundTag("display").getString("Name");
			if (customName.equals("")) {
				return;
			}
			if (this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) == inv$
					|| this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) == inv$) {
				event.setCanceled(true);

				for (IWrappedInventory invW : inventoriesW) {
					ICorporeaSpark invSpark = invW.getSpark();

					Object originalInventory = invW.getWrappedObject();
					if (originalInventory instanceof ICorporeaInterceptor) {
						ICorporeaInterceptor interceptor = (ICorporeaInterceptor) originalInventory;
						interceptor.interceptRequest(event.request, event.count, invSpark, event.spark, stacks,
								inventories, event.realRequest);
						interceptors.put(interceptor, invSpark);
					}
				}
			}
			break;
		case 3:
			if (this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) == inv$
					|| this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) == inv$
					|| this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) == inv$) {
				event.setCanceled(true);
				TileEntity tileI = this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
				ICorporeaSpark spark;
				if (tileI instanceof ICorporeaInterceptor) {
					spark = getSparkForBlock(tileI);
					interceptors.put((ICorporeaInterceptor) tileI, spark);
					((ICorporeaInterceptor) tileI).interceptRequest(event.request, event.count, spark, event.spark,
							stacks, inventories, event.realRequest);
				}
				tileI = this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
				if (tileI instanceof ICorporeaInterceptor) {
					spark = getSparkForBlock(tileI);
					interceptors.put((ICorporeaInterceptor) tileI, spark);
					((ICorporeaInterceptor) tileI).interceptRequest(event.request, event.count, spark, event.spark,
							stacks, inventories, event.realRequest);
				}
				tileI = this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
				if (tileI instanceof ICorporeaInterceptor) {
					spark = getSparkForBlock(tileI);
					interceptors.put((ICorporeaInterceptor) tileI, spark);
					((ICorporeaInterceptor) tileI).interceptRequest(event.request, event.count, spark, event.spark,
							stacks, inventories, event.realRequest);
				}
				tileI = this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
				if (tileI instanceof ICorporeaInterceptor) {
					spark = getSparkForBlock(tileI);
					interceptors.put((ICorporeaInterceptor) tileI, spark);
					((ICorporeaInterceptor) tileI).interceptRequest(event.request, event.count, spark, event.spark,
							stacks, inventories, event.realRequest);
				}
				tileI = this.worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
				if (tileI instanceof ICorporeaInterceptor) {
					spark = getSparkForBlock(tileI);
					interceptors.put((ICorporeaInterceptor) tileI, spark);
					((ICorporeaInterceptor) tileI).interceptRequest(event.request, event.count, spark, event.spark,
							stacks, inventories, event.realRequest);
				}
				tileI = this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
				if (tileI instanceof ICorporeaInterceptor) {
					spark = getSparkForBlock(tileI);
					interceptors.put((ICorporeaInterceptor) tileI, spark);
					((ICorporeaInterceptor) tileI).interceptRequest(event.request, event.count, spark, event.spark,
							stacks, inventories, event.realRequest);
				}
			}
			break;
		}
		if (!interceptors.isEmpty())
			for (ICorporeaInterceptor interceptor : interceptors.keySet())
				interceptor.interceptRequestLast(event.request, event.count, interceptors.get(interceptor), event.spark,
						stacks, inventories, event.realRequest);
	}

	private ICorporeaSpark getSparkForBlock(TileEntity tile) {
		return CorporeaHelper.getSparkForBlock(worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtt) {
		super.readFromNBT(nbtt);
		this.hasCatalyst = nbtt.getBoolean("hasCatalyst");
		if (hasCatalyst) {
			int coordsX = nbtt.getInteger("coordsX");
			int coordsY = nbtt.getInteger("coordsY");
			int coordsZ = nbtt.getInteger("coordsZ");
			this.coords = new ChunkCoordinates(coordsX, coordsY, coordsZ);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtt) {
		super.writeToNBT(nbtt);
		nbtt.setInteger("coordsX", this.coords.posX);
		nbtt.setInteger("coordsY", this.coords.posY);
		nbtt.setInteger("coordsZ", this.coords.posZ);
		nbtt.setBoolean("hasCatalyst", hasCatalyst);
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
	public void setCatalystCoords(int x, int y, int z) {
		coords.set(x, y, z);
		this.hasCatalyst = true;
	}

	@Override
	public TileCorporeaCatalyst getCatalystTile() {
		if (this.hasCatalyst)
			return (TileCorporeaCatalyst) worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
		return null;
	}

	@Override
	public boolean isModified() {
		return this.hasCatalyst;
	}

	@Override
	public void removeModification() {
		coords.set(0, 0, 0);
		hasCatalyst = false;
	}

}
