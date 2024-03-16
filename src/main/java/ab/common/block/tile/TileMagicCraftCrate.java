package ab.common.block.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.relics.ItemThaumonomicon;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileMagicWorkbench;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.item.ModItems;

public class TileMagicCraftCrate extends TileMagicWorkbench implements ISidedInventory, IInventory {

    private static final String WAITING_TAG = "waitingStack";
    private static final String PATTERN_TAG = "pattern";
    private static final int[] ACCESSIBLE_SLOTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    private boolean[][] pattern;
    private EntityPlayer player;
    int signal = 0;
    private ItemStack waitingStack;

    public TileMagicCraftCrate() {
        pattern = new boolean[][] { new boolean[] { true, true, true }, new boolean[] { true, true, true },
                new boolean[] { true, true, true } };
    }

    public void updateEntity() {
        if (!worldObj.isRemote && (craft(true) && canEject() || isFull() && !isWaiting())) ejectAll();
        int newSignal = 0;
        for (; newSignal < 9; newSignal++) if (!isLocked(newSignal) && getStackInSlot(newSignal) == null) break;
        if (isWaiting()) newSignal = 14;
        if (newSignal != signal) {
            signal = newSignal;
            worldObj.func_147453_f(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
        }
    }

    boolean craft(boolean fullCheck) {
        if (fullCheck && !isFull()) return false;
        if (player == null) return false;
        TileMagicWorkbench craft = new TileMagicWorkbench() {

            public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
                this.stackList[par1] = par2ItemStack;
            }

            public ItemStack decrStackSize(int par1, int par2) {
                if (this.stackList[par1] == null) {
                    return null;
                }
                if (this.stackList[par1].stackSize <= par2) {
                    final ItemStack var3 = this.stackList[par1];
                    this.stackList[par1] = null;
                    return var3;
                }
                final ItemStack var3 = this.stackList[par1].splitStack(par2);
                if (this.stackList[par1].stackSize == 0) {
                    this.stackList[par1] = null;
                }
                return var3;
            }
        };
        for (int i = 0; i < 10; i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack == null || isLocked(i) || stack.getItem() == ModItems.manaResource && stack.getItemDamage() == 11)
                continue;
            craft.setInventorySlotContents(i, stack.copy());
        }
        ItemStack result = ThaumcraftCraftingManager.findMatchingArcaneRecipe(craft, player);
        if (result != null) {
            final AspectList aspects = ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(craft, player);
            if (aspects.size() > 0 && this.getStackInSlot(10) != null) {
                final ItemWandCasting wand = (ItemWandCasting) this.getStackInSlot(10).getItem();
                if (wand.consumeAllVisCrafting(this.getStackInSlot(10), player, aspects, true)) {
                    for (int var2 = 0; var2 < 9; ++var2) {
                        final ItemStack var3 = craft.getStackInSlot(var2);
                        if (var3 != null) craft.decrStackSize(var2, 1);
                    }
                    for (int i = 0; i < 9; i++) setInventorySlotContents(i, craft.getStackInSlot(i));
                    setInventorySlotContents(9, result);
                    MinecraftForge.EVENT_BUS.post(new ItemCraftedEvent(player, result, craft));
                    waitingStack = null;
                    return true;
                } else {
                    waitingStack = result;
                    markDirty();
                }
            }
        } else waitingStack = null;
        return false;
    }

    boolean isFull() {
        for (int i = 0; i < 9; i++) if (!isLocked(i) && getStackInSlot(i) == null) return false;

        return true;
    }

    public boolean[][] getPattern() {
        return pattern;
    }

    public void setPattern(boolean[][] pattern) {
        this.pattern = pattern;
    }

    public int getSizeInventory() {
        return 12;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    public int getSignal() {
        return signal;
    }

    public ItemStack getWaitingStack() {
        return waitingStack;
    }

    private boolean isWaiting() {
        return waitingStack != null;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (i < 9 && !isLocked(i)) return true;
        if (i == 9) return false;
        if (i == 10) {
            if (!(itemstack.getItem() instanceof ItemWandCasting)) {
                return false;
            }
            final ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
            return !wand.isStaff(itemstack);
        }
        return itemstack.getItem() instanceof ItemThaumonomicon;
    }

    private boolean isLocked(int slot) {
        return pattern != null && !pattern[slot / 3][slot % 3];
    }

    public void readCustomNBT(NBTTagCompound nbtt) {
        super.readCustomNBT(nbtt);
        NBTTagList var2 = nbtt.getTagList("Items", 10);
        inventorySlots = new ItemStack[getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < inventorySlots.length) inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
        }
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) pattern[i][j] = nbtt.getBoolean(PATTERN_TAG + i * 3 + j);
        NBTTagCompound nbtt1 = nbtt.getCompoundTag(WAITING_TAG);
        if (nbtt1 != null) waitingStack = ItemStack.loadItemStackFromNBT(nbtt1);
    }

    public void writeCustomNBT(NBTTagCompound nbtt) {
        super.writeCustomNBT(nbtt);
        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < inventorySlots.length; ++var3) {
            if (inventorySlots[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                inventorySlots[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        nbtt.setTag("Items", var2);
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) nbtt.setBoolean(PATTERN_TAG + i * 3 + j, pattern[i][j]);
        NBTTagCompound nbtt1 = new NBTTagCompound();
        if (waitingStack != null) {
            waitingStack.writeToNBT(nbtt1);
            nbtt.setTag(WAITING_TAG, nbtt1);
        }
    }

    public void markDirty() {
        super.markDirty();
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        super.onDataPacket(manager, packet);
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    public Packet getDescriptionPacket() {
        final NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 10, compound);
    }

    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return i == 9;
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return ACCESSIBLE_SLOTS;
    }

    public boolean canInsertItem(int i, ItemStack stack, int j) {
        return i < 9;
    }

    public int getInventoryStackLimit() {
        return 1;
    }

    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side,
            int md) {
        return 0;
    }

    public ItemStack onWandRightClick(final World world, final ItemStack wandstack, final EntityPlayer player) {
        this.player = player;
        return wandstack;
    }

    public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

    public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}

    public void ejectAll() {
        for (int i = 0; i < 10; ++i) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null) eject(stack, false);
            setInventorySlotContents(i, null);
        }
        this.waitingStack = null;
        markDirty();
    }

    public void eject(ItemStack stack, boolean redstone) {
        EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord - 0.5, zCoord + 0.5, stack);
        item.motionX = 0;
        item.motionY = 0;
        item.motionZ = 0;
        if (redstone) item.age = -200;
        setInventorySlotContents(0, null);
        if (!worldObj.isRemote) worldObj.spawnEntityInWorld(item);
    }

    public boolean canEject() {
        Block blockBelow = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
        return blockBelow.isAir(worldObj, xCoord, yCoord - 1, zCoord)
                || blockBelow.getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord - 1, zCoord) == null;
    }

    ItemStack[] inventorySlots = new ItemStack[getSizeInventory()];

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventorySlots[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (inventorySlots[i] != null) {
            ItemStack stackAt;
            if (inventorySlots[i].stackSize <= j) {
                stackAt = inventorySlots[i];
                inventorySlots[i] = null;
                return stackAt;
            } else {
                stackAt = inventorySlots[i].splitStack(j);
                if (inventorySlots[i].stackSize == 0) inventorySlots[i] = null;
                return stackAt;
            }
        }
        return null;
    }

    // Copied from vazkii.botania.common.block.tile.TileSimpleInventory

    public ItemStack getStackInSlotOnClosing(int i) {
        return getStackInSlot(i);
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventorySlots[i] = itemstack;
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false
                : entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public void openInventory() {}

    public void closeInventory() {}
}
