package ab.api.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import vazkii.botania.api.recipe.RecipePetals;

public class RecipeAdvancedPlate extends RecipePetals {

    private final int color;
    private final int mana;

    public static RecipeAdvancedPlate fromOreDictOutput(String output, int mana, int color, Object... inputs) {
        List<ItemStack> ores = OreDictionary.getOres(output);
        return !ores.isEmpty() ? new RecipeAdvancedPlate(ores.get(0), mana, color, inputs) : null;
    }

    public RecipeAdvancedPlate(ItemStack output, int mana, int color, Object... inputs) {
        super(output, inputs);
        this.mana = mana;
        this.color = color;
    }

    public int getManaUsage() {
        return this.mana;
    }

    public int getColor() {
        return this.color;
    }
}
