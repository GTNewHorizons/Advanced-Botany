package pulxes.ab.api.recipe;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class RecipeNidavellirForge extends RecipePetals {

	private int color;
	private int mana;	
	
	public RecipeNidavellirForge(ItemStack output, int mana, int color, Object... inputs) {
		super(output, inputs);
		this.mana = mana;
		this.color = color;
	}
	
	public boolean matches(IItemHandler inv) {
		List<Object> inputsMissing = new ArrayList(getInputs());
		for(int i = 1; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				break; 
			int stackIndex = -1, oredictIndex = -1;
			for(int j = 0; j < inputsMissing.size(); j++) {
				Object input = inputsMissing.get(j);
				if(input instanceof String) {
					boolean found = false;
					for(ItemStack ostack : OreDictionary.getOres((String)input, false)) {
						if(OreDictionary.itemMatches(ostack, stack, false)) {
							oredictIndex = j;
							found = true;
							break;
						} 
					} 
					if(found)
						break; 
				} else if(input instanceof ItemStack && compareStacks((ItemStack)input, stack)) {
					stackIndex = j;
					break;
				} 
			} 
			if(stackIndex != -1)
				inputsMissing.remove(stackIndex);
			else if(oredictIndex != -1)
				inputsMissing.remove(oredictIndex);
			else 
				return false;
		} 
		return inputsMissing.isEmpty();
	}
	
	private boolean compareStacks(ItemStack recipe, ItemStack supplied) {
	    return (recipe.getItem() == supplied.getItem() && recipe.getItemDamage() == supplied.getItemDamage() && ItemNBTHelper.matchTag(recipe.getTagCompound(), supplied.getTagCompound()));
	}
	
	public int getColor() {
		return this.color;
	}
	
	public int getManaUsage() {
		return this.mana;
	}
}