package ab.client.gui;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotItemChest extends Slot {

    InventoryItemChest itemChestInv;
    int slot;

    public SlotItemChest(InventoryItemChest inv, int index, int x, int y, int slot) {
        super(inv, index, x, y);
        this.itemChestInv = inv;
        this.slot = slot;
    }

    public void onSlotChange(ItemStack oldStack, ItemStack newStack) {
        itemChestInv.setInventorySlotContents(slot, newStack);
    }

    public boolean isItemValid(ItemStack stack) {
        return !itemChestInv.isRelicTalisman(stack);
    }
}
