package ab.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import ab.common.item.relic.ItemTalismanHiddenRiches;
import ab.common.lib.register.ItemListAB;

public class InventoryItemChest implements IInventory {

    EntityPlayer player;
    int slot;
    ItemStack[] stacks = null;
    boolean invPushed = false;
    ItemStack storedInv = null;
    int openChest;

    public InventoryItemChest(EntityPlayer player, int slot, int openChest) {
        this.player = player;
        this.slot = slot;
        this.openChest = openChest;
    }

    public static boolean isRelicTalisman(ItemStack stack) {
        return (stack != null && stack.getItem() == ItemListAB.itemTalismanHiddenRiches);
    }

    protected ItemStack getStack() {
        ItemStack stack = this.player.inventory.getStackInSlot(this.slot);
        if (stack != null) this.storedInv = stack;
        return stack;
    }

    protected ItemStack[] getInventory() {
        if (this.stacks != null) return this.stacks;
        ItemStack stack = getStack();
        if (isRelicTalisman(getStack())) {
            this.stacks = ItemTalismanHiddenRiches.getChestLoot(stack, openChest);
            return this.stacks;
        }
        return new ItemStack[getSizeInventory()];
    }

    public void pushInventory() {
        if (this.invPushed) return;
        ItemStack stack = getStack();
        if (stack == null) stack = this.storedInv;
        if (stack != null) {
            ItemStack[] inv = getInventory();
            ItemTalismanHiddenRiches.setChestLoot(stack, inv, openChest);
            ItemTalismanHiddenRiches.setOpenChest(stack, -1);
        }
        this.invPushed = true;
    }

    public int getSizeInventory() {
        return 27;
    }

    public ItemStack getStackInSlot(int i) {
        return getInventory()[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        ItemStack[] inventorySlots = getInventory();
        if (inventorySlots[i] != null) {
            if ((inventorySlots[i]).stackSize <= j) {
                ItemStack itemStack = inventorySlots[i];
                inventorySlots[i] = null;
                return itemStack;
            }
            ItemStack stackAt = inventorySlots[i].splitStack(j);
            if ((inventorySlots[i]).stackSize == 0) inventorySlots[i] = null;
            return stackAt;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        return getStackInSlot(i);
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        ItemStack[] inventorySlots = getInventory();
        inventorySlots[i] = itemstack;
    }

    public int getInventoryStackLimit() {
        return isRelicTalisman(getStack()) ? 64 : 0;
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return isRelicTalisman(getStack());
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return isRelicTalisman(getStack());
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public void openInventory() {}

    public void closeInventory() {}

    public String getInventoryName() {
        return "itemChestTalisman";
    }

    public void markDirty() {}
}
