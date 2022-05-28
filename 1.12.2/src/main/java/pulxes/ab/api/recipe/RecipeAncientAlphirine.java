package pulxes.ab.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeAncientAlphirine {

	private ItemStack output;
	private Object input;
	private int chance;
	
	public RecipeAncientAlphirine(ItemStack output, Object input, int chance) {
		this.output = output;
		if(input instanceof String || input instanceof ItemStack)
			this.input = input;
		else
			throw new IllegalArgumentException("Invalid input");
		this.input = input;
		if(chance > 100)
			chance = 100;
		else if(chance <= 0)
			chance = 1;
		this.chance = chance;
	}
	
	public boolean matches(ItemStack stack) {
		if(stack.isEmpty())
			return false;
		if(input instanceof String) {
			boolean found = false;
			for(ItemStack oreStack : OreDictionary.getOres((String)input, false)) {
				if(OreDictionary.itemMatches(oreStack, stack, false)) {
					found = true;
					break;
				} 
			} 
			return found;
		} else if(input instanceof ItemStack) {
			ItemStack inputCopy = ((ItemStack)input).copy();
			if(inputCopy.getItemDamage() == 32767)
				inputCopy.setItemDamage(stack.getItemDamage());
			return stack.getItem() == inputCopy.getItem() && stack.getItemDamage() == inputCopy.getItemDamage();
		}
		return false;
	}
	
	public ItemStack getOutput() {
		return output;
	}
	
	public Object getInput() {
		return input;
	}
	
	public int getChance() {
		return chance;
	}
}