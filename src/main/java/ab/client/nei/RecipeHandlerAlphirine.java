package ab.client.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAncientAlphirine;
import ab.client.core.ClientHelper;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class RecipeHandlerAlphirine extends TemplateRecipeHandler {

    public class CachedHandlerAlphirine extends TemplateRecipeHandler.CachedRecipe {

        public List<PositionedStack> inputs = new ArrayList<PositionedStack>();
        public PositionedStack output;
        public List<PositionedStack> otherStacks = new ArrayList<PositionedStack>();
        public int chance;

        public CachedHandlerAlphirine(RecipeAncientAlphirine recipe) {
            if (recipe == null) return;
            this.inputs.add(new PositionedStack(ItemBlockSpecialFlower.ofType("ancientAlphirine"), 71, 23));
            this.inputs.add(new PositionedStack(recipe.getInput(), 42, 23));
            this.output = new PositionedStack(recipe.getOutput(), 101, 23);
            this.chance = recipe.getChance();
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(RecipeHandlerAlphirine.this.cycleticks / 20, this.inputs);
        }

        public PositionedStack getResult() {
            return this.output;
        }

        public List<PositionedStack> getOtherStacks() {
            return this.otherStacks;
        }

        public boolean contains(Collection<PositionedStack> ingredients, ItemStack ingredient) {
            if (ingredients == this.inputs) for (PositionedStack stack : ingredients) {
                if (stack.contains(ingredient)) return true;
            }
            return super.contains(ingredients, ingredient);
        }
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("ab.nei.alphirine");
    }

    public String getRecipeID() {
        return "ab.alphirine";
    }

    public String getGuiTexture() {
        return "botania:textures/gui/neiBlank.png";
    }

    public int recipiesPerPage() {
        return 2;
    }

    public void loadTransferRects() {
        this.transferRects.add(
                new TemplateRecipeHandler.RecipeTransferRect(
                        new Rectangle(70, 22, 18, 18),
                        getRecipeID(),
                        new Object[0]));
    }

    public void drawBackground(int recipe) {
        super.drawBackground(recipe);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        GuiDraw.changeTexture("botania:textures/gui/pureDaisyOverlay.png");
        GuiDraw.drawTexturedModalRect(45, 10, 0, 0, 65, 44);
        int chance = ((CachedHandlerAlphirine) this.arecipes.get(recipe)).chance;
        ClientHelper.drawChanceBar(52, 58, chance);
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeID())) {
            for (RecipeAncientAlphirine recipe : AdvancedBotanyAPI.alphirineRecipes) {
                if (recipe == null) continue;
                this.arecipes.add(new CachedHandlerAlphirine(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (RecipeAncientAlphirine recipe : AdvancedBotanyAPI.alphirineRecipes) {
            if (recipe == null) continue;
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getOutput(), result))
                this.arecipes.add(new CachedHandlerAlphirine(recipe));
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (RecipeAncientAlphirine recipe : AdvancedBotanyAPI.alphirineRecipes) {
            if (recipe == null) continue;
            CachedHandlerAlphirine crecipe = new CachedHandlerAlphirine(recipe);
            if (crecipe.contains(crecipe.getIngredients(), ingredient)
                    || crecipe.contains(crecipe.getOtherStacks(), ingredient))
                this.arecipes.add(crecipe);
        }
    }
}
