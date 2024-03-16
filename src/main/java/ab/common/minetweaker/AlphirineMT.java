package ab.common.minetweaker;

import net.minecraft.item.ItemStack;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAncientAlphirine;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.advBotany.AncientAlphirine")
public class AlphirineMT {

    @ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input, int chance) {
        MineTweakerAPI.apply(
                new Add(
                        new RecipeAncientAlphirine(
                                MineTweakerMC.getItemStack(output),
                                MineTweakerMC.getItemStack(input),
                                chance)));
    }

    private static class Add implements IUndoableAction {

        private RecipeAncientAlphirine recipe;

        public Add(RecipeAncientAlphirine recipe) {
            this.recipe = recipe;
        }

        public void apply() {
            AdvancedBotanyAPI.alphirineRecipes.add(recipe);
        }

        public boolean canUndo() {
            return true;
        }

        public void undo() {
            AdvancedBotanyAPI.alphirineRecipes.remove(recipe);
        }

        public String describe() {
            return "Adding Ancient Alphirine Recipe for " + recipe.getOutput().getDisplayName();
        }

        public String describeUndo() {
            return "Removing Ancient Alphirine Recipe for " + recipe.getOutput().getDisplayName();
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
        private RecipeAncientAlphirine recipe;

        public Remove(ItemStack output) {
            this.output = output;
        }

        public void apply() {
            for (RecipeAncientAlphirine r : AdvancedBotanyAPI.alphirineRecipes) {
                if (r.getOutput() != null && r.getOutput().isItemEqual(output)) {
                    AdvancedBotanyAPI.alphirineRecipes.remove(r);
                    recipe = r;
                    break;
                }
            }
        }

        public boolean canUndo() {
            return true;
        }

        public void undo() {
            AdvancedBotanyAPI.alphirineRecipes.add(recipe);
        }

        public String describe() {
            return "Removing Ancient Alphirine Recipe for " + output.getDisplayName();
        }

        public String describeUndo() {
            return "Restoring Ancient Alphirinedvanced Plate Recipe for " + output.getDisplayName();
        }

        public Object getOverrideKey() {
            return null;
        }
    }
}
