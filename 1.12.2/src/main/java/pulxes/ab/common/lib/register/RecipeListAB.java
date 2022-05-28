package pulxes.ab.common.lib.register;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import pulxes.ab.api.AdvancedBotanyAPI;
import pulxes.ab.api.recipe.RecipeAncientAlphirine;
import pulxes.ab.api.recipe.RecipeNidavellirForge;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

public class RecipeListAB {
	
	public static RecipeAncientAlphirine forgottenLandRecipe;
	
	public static RecipeNidavellirForge mithrillRecipe;
	public static RecipeNidavellirForge terrasteelRecipe;
	public static RecipeNidavellirForge manaStarRecipe;
	public static RecipeNidavellirForge nebulaRecipe;

	public static void init() {
		forgottenLandRecipe = AdvancedBotanyAPI.registerAlphirineRecipe(new ItemStack(ItemListAB.itemResource, 1, 3), new ItemStack(ModItems.manaResource, 1, 15), 75);
	
		mithrillRecipe = AdvancedBotanyAPI.registerForgeRecipe(new ItemStack(ItemListAB.itemResource, 1, 0), new ItemStack(ModItems.manaResource, 1, 5), new ItemStack(ModBlocks.storage, 1, 0), new ItemStack(ModItems.manaResource, 1, 18), 2500000, 0x25d6b7);
		terrasteelRecipe = AdvancedBotanyAPI.registerForgeRecipe(new ItemStack(ModItems.manaResource, 1, 4), new ItemStack(ModItems.manaResource, 1, 2), new ItemStack(ModItems.manaResource, 1, 0), new ItemStack(ModItems.manaResource, 1, 1), 500000, 0x29de20);
		manaStarRecipe = AdvancedBotanyAPI.registerForgeRecipe(new ItemStack(ItemListAB.itemResource, 1, 2), new ItemStack(ModItems.manaResource, 1, 23), new ItemStack(ModItems.manaResource, 1, 5), new ItemStack(Items.NETHER_STAR), 250000, 0x6bc9ec);
		nebulaRecipe = AdvancedBotanyAPI.registerForgeRecipe(new ItemStack(ItemListAB.itemResource, 1, 5), new ItemStack(ModItems.manaResource, 1, 8), new ItemStack(ModBlocks.storage, 1, 4), new ItemStack(ItemListAB.itemResource, 1, 1), 25000000, 0x8d16e0);
	}
}
