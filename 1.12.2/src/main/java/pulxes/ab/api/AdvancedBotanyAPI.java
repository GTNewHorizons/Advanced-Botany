package pulxes.ab.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import pulxes.ab.api.recipe.RecipeAncientAlphirine;
import pulxes.ab.api.recipe.RecipeNidavellirForge;

public class AdvancedBotanyAPI {
	
	public static List<RecipeAncientAlphirine> alphirineRecipes = new ArrayList<RecipeAncientAlphirine>();
	public static List<RecipeNidavellirForge> forgeRecipes = new ArrayList<RecipeNidavellirForge>();
	
	public static final Item.ToolMaterial MITHRILL_TOOL_MATERIAL = EnumHelper.addToolMaterial("MITHRIL", 7, -1, 8.0F, 4.0F, 24);

	public static RecipeAncientAlphirine registerAlphirineRecipe(ItemStack output, Object input, int chance) {
		RecipeAncientAlphirine recipe = new RecipeAncientAlphirine(output, input, chance);
		alphirineRecipes.add(recipe);
		return recipe;
	}
	
	public static RecipeNidavellirForge registerForgeRecipe(ItemStack output, Object input1, Object input2, Object input3, int mana, int color) {
		RecipeNidavellirForge recipe = new RecipeNidavellirForge(output, mana, color, input1, input2, input3);
		forgeRecipes.add(recipe);
		return recipe;
	}
}