package ab.api.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class RecipeFountainMana {

    private ItemStack output;
    private int color;
    private List<ItemStack> inputs;
    private int mana;

    public RecipeFountainMana(ItemStack output, int mana, int color, ItemStack... inputs2) {
        this.output = output;
        this.mana = mana;
        this.color = color;
        List<ItemStack> inputsToSet = new ArrayList();
        for (ItemStack obj : inputs2) inputsToSet.add(obj);
        this.inputs = inputsToSet;
    }

    public List<ItemStack> getInputs() {
        return new ArrayList(this.inputs);
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getManaUsage() {
        return this.mana;
    }

    public int getColor() {
        return this.color;
    }

    public boolean matches(IInventory inv) {
        List<ItemStack> inputsMissing = new ArrayList(this.inputs);
        for (int i = 1; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) break;
            int stackIndex = -1;
            for (int j = 0; j < inputsMissing.size(); j++) {
                ItemStack input = inputsMissing.get(j);
                if (input instanceof ItemStack && simpleAreStacksEqual(input.copy(), stack)) {
                    stackIndex = j;
                    break;
                }
            }
            if (stackIndex != -1) {
                inputsMissing.remove(stackIndex);
            } else {
                return false;
            }
        }
        return inputsMissing.isEmpty();
    }

    boolean simpleAreStacksEqual(ItemStack input, ItemStack stack) {
        if (input.getItemDamage() == 32767) input.setItemDamage(stack.getItemDamage());
        return (input.getItem() == stack.getItem() && input.getItemDamage() == stack.getItemDamage());
    }
}
