package ab.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import ab.common.item.relic.ItemTalismanHiddenRiches;
import vazkii.botania.client.gui.SlotLocked;

public class ContainerItemChest extends Container {

    public InventoryItemChest itemChestInv;
    private int numRows;

    public ContainerItemChest(EntityPlayer player) {
        int slot = player.inventory.currentItem;
        InventoryPlayer inventoryPlayer = player.inventory;
        this.itemChestInv = new InventoryItemChest(
                player,
                slot,
                ItemTalismanHiddenRiches.getOpenChest(player.getHeldItem()));
        this.numRows = itemChestInv.getSizeInventory() / 9;
        int i, j, k;
        for (j = 0; j < this.numRows; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlotToContainer(new SlotItemChest(itemChestInv, k + j * 9, 8 + k * 18, 18 + j * 18, k));
            }
        }
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 85 + i * 18));
        }
        for (i = 0; i < 9; i++) {
            if (player.inventory.currentItem == i) {
                addSlotToContainer(new SlotLocked(inventoryPlayer, i, 8 + i * 18, 143));
            } else {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 143));
            }
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        boolean can = this.itemChestInv.isUseableByPlayer(player);
        if (!can) onContainerClosed(player);
        return can;
    }

    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        this.itemChestInv.pushInventory();
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (!itemChestInv.isRelicTalisman(stack)) {
                if (i < itemChestInv.getSizeInventory()) {
                    if (!this.mergeItemStack(stack, itemChestInv.getSizeInventory(), inventorySlots.size(), true)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(stack, 0, itemChestInv.getSizeInventory(), false)) {
                    return null;
                }
            } else {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
