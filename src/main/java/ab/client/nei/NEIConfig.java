package ab.client.nei;

import ab.AdvancedBotany;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;

public class NEIConfig implements IConfigureNEI {

	public String getName() {
		return AdvancedBotany.modid;
	}

	public String getVersion() {
		return AdvancedBotany.version;
	}

	public void loadConfig() {
		API.registerRecipeHandler((ICraftingHandler)new RecipeHandlerAlphirine());
		API.registerUsageHandler((IUsageHandler)new RecipeHandlerAlphirine());
		API.registerRecipeHandler((ICraftingHandler)new RecipeHandlerAdvancedPlate());
	}
}
