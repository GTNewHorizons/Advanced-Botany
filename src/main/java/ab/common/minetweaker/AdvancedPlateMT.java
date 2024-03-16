package ab.common.minetweaker;

import net.minecraft.item.ItemStack;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAdvancedPlate;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.advBotany.AdvancedPlate")
public class AdvancedPlateMT {

    @ZenMethod
    public static void addRecipe(IItemStack output, String rgb, IItemStack input1, IItemStack input2, IItemStack input3,
            int mana) {
        MineTweakerAPI.apply(
                new Add(
                        new RecipeAdvancedPlate(
                                MineTweakerMC.getItemStack(output),
                                mana,
                                Integer.decode(rgb),
                                MineTweakerMC.getItemStack(input1),
                                MineTweakerMC.getItemStack(input2),
                                MineTweakerMC.getItemStack(input3))));
    }

    private static class Add implements IUndoableAction {

        private RecipeAdvancedPlate recipe;

        public Add(RecipeAdvancedPlate recipe) {
            this.recipe = recipe;
        }

        public void apply() {
            AdvancedBotanyAPI.advancedPlateRecipes.add(recipe);
        }

        public boolean canUndo() {
            return true;
        }

        public void undo() {
            AdvancedBotanyAPI.advancedPlateRecipes.remove(recipe);
        }

        public String describe() {
            return "Adding Advanced Plate Recipe for " + recipe.getOutput().getDisplayName();
        }

        public String describeUndo() {
            return "Removing Advanced Plate Recipe for " + recipe.getOutput().getDisplayName();
        }

        public Object getOverrideKey() {
            return null;
        }
    }

    @ZenMethod
    public static void remove(IItemStack output) {
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(output)));
    }

    private static class Remove implements IUndoableAction {

        private ItemStack output;
        private RecipeAdvancedPlate recipe;

        public Remove(ItemStack output) {
            this.output = output;
        }

        public void apply() {
            for (RecipeAdvancedPlate r : AdvancedBotanyAPI.advancedPlateRecipes) {
                if (r.getOutput() != null && r.getOutput().isItemEqual(output)) {
                    AdvancedBotanyAPI.advancedPlateRecipes.remove(r);
                    recipe = r;
                    break;
                }
            }
        }

        public boolean canUndo() {
            return true;
        }

        public void undo() {
            AdvancedBotanyAPI.advancedPlateRecipes.add(recipe);
        }

        public String describe() {
            return "Removing Advanced Plate Recipe for " + output.getDisplayName();
        }

        public String describeUndo() {
            return "Restoring Advanced Plate Recipe for " + output.getDisplayName();
        }

        public Object getOverrideKey() {
            return null;
        }
    }
}
