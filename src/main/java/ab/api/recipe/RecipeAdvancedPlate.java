package ab.api.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class RecipeAdvancedPlate {

	private ItemStack output;
	private List<ItemStack> inputs;	
	private int mana;	
	
	public RecipeAdvancedPlate(ItemStack output, int mana, ItemStack... inputs) {
		this.output = output;
		this.mana = mana;
		List<ItemStack> inputsToSet = new ArrayList();
		for (ItemStack obj : inputs) 
			inputsToSet.add(obj);	
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
	
	public boolean matches(IInventory inv) {
		List<Object> inputsMissing = new ArrayList(this.inputs);
		for (int i = 1; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null)
				break;
			int stackIndex = -1;
			for (int j = 0; j < inputsMissing.size(); j++) {
				Object input = inputsMissing.get(j);
				if (input instanceof ItemStack && simpleAreStacksEqual((ItemStack)input, stack)) {
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
	
	boolean simpleAreStacksEqual(ItemStack stack, ItemStack stack2) {
		return (stack.getItem() == stack2.getItem() && stack.getItemDamage() == stack2.getItemDamage());
	}
}
