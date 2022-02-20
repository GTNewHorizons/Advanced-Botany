package ab.api;

import java.util.ArrayList;
import java.util.List;

import ab.api.recipe.RecipeAdvancedPlate;
import ab.api.recipe.RecipeAncientAlphirine;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class AdvancedBotanyAPI {

	public static List<RecipeAdvancedPlate> advancedPlateRecipes = new ArrayList<RecipeAdvancedPlate>();
	public static List<RecipeAncientAlphirine> alphirineRecipes = new ArrayList<RecipeAncientAlphirine>();
	public static List<TerraFarmlandList> farmlandList = new ArrayList<TerraFarmlandList>();
	
	public static Item.ToolMaterial mithrilToolMaterial = EnumHelper.addToolMaterial("MITHRIL", 7, -1, 8.0F, 8.0F, 24);
	public static ItemArmor.ArmorMaterial nebulaArmorMaterial = EnumHelper.addArmorMaterial("NEBULA", 0, new int[] { 7, 15, 11, 9 }, 26);
	
	public static RecipeAdvancedPlate registerAdvancedPlateRecipe(ItemStack output, ItemStack input1, ItemStack input2, ItemStack input3, int mana, int color) {
		RecipeAdvancedPlate recipe = new RecipeAdvancedPlate(output, mana, color, input1, input2, input3);
		advancedPlateRecipes.add(recipe);
		return recipe;
	}
	
	public static RecipeAncientAlphirine registerAlphirineRecipe(ItemStack output, ItemStack input, int chance) {
		if(chance > 100)
			chance = 100;
		else if(chance <= 0)
			chance = 1;
		RecipeAncientAlphirine recipe = new RecipeAncientAlphirine(output, input, chance);
		alphirineRecipes.add(recipe);
		return recipe;
	}
	
	public static TerraFarmlandList registerFarmlandSeed(Block block, int meta) {
		TerraFarmlandList seed = new TerraFarmlandList(block, meta);
		farmlandList.add(seed);
		return seed;
	}
}
