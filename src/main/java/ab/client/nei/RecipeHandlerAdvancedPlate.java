package ab.client.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAdvancedPlate;
import ab.client.core.ClientHelper;
import ab.common.lib.register.BlockListAB;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class RecipeHandlerAdvancedPlate extends TemplateRecipeHandler {

    public class CachedAdvancedPlateRecipe extends TemplateRecipeHandler.CachedRecipe {

        public List<PositionedStack> inputs = new ArrayList<>();
        public PositionedStack output;
        public int manaUsage;

        public CachedAdvancedPlateRecipe(RecipeAdvancedPlate recipe) {
            setIngredients(recipe.getInputs());
            this.output = new PositionedStack(recipe.getOutput(), 111, 21);
            this.inputs.add(new PositionedStack(new ItemStack(BlockListAB.blockABPlate), 73, 55));
            this.manaUsage = recipe.getManaUsage();
        }

        public void setIngredients(List<Object> inputs) {
            float degreePerInput = 360.0F / inputs.size();
            float currentDegree = -90.0F;
            for (Object obj : inputs) {
                int posX = (int) Math.round(73.0D + Math.cos(currentDegree * Math.PI / 180.0D) * 32.0D);
                int posY = (int) Math.round(55.0D + Math.sin(currentDegree * Math.PI / 180.0D) * 32.0D);
                if (obj instanceof String oreName) {
                    this.inputs.add(new PositionedStack(OreDictionary.getOres(oreName), posX, posY));
                } else if (obj instanceof ItemStack itemStack) {
                    this.inputs.add(new PositionedStack(itemStack, posX, posY));
                }
                currentDegree += degreePerInput;
            }
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(RecipeHandlerAdvancedPlate.this.cycleticks / 20, this.inputs);
        }

        public PositionedStack getResult() {
            return this.output;
        }
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("ab.nei.advancedPlate");
    }

    public String getRecipeID() {
        return "ab.advancedPlate";
    }

    public String getGuiTexture() {
        return "botania:textures/gui/neiBlank.png";
    }

    public void loadTransferRects() {
        this.transferRects
                .add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(72, 54, 18, 18), getRecipeID()));
    }

    public int recipiesPerPage() {
        return 1;
    }

    public void drawBackground(int recipe) {
        super.drawBackground(recipe);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        GuiDraw.changeTexture("botania:textures/gui/petalOverlay.png");
        GuiDraw.drawTexturedModalRect(45, 10, 38, 7, 92, 92);
        int mana = ((CachedAdvancedPlateRecipe) this.arecipes.get(recipe)).manaUsage;
        ClientHelper.renderPoolManaBar(32, 112, 0x239ddc, 1.0f, mana);
    }

    public CachedAdvancedPlateRecipe getCachedRecipe(RecipeAdvancedPlate recipe) {
        return new CachedAdvancedPlateRecipe(recipe);
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeID())) {
            for (RecipeAdvancedPlate recipe : AdvancedBotanyAPI.advancedPlateRecipes) {
                this.arecipes.add(getCachedRecipe(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (RecipeAdvancedPlate recipe : AdvancedBotanyAPI.advancedPlateRecipes) {
            if (recipe == null) continue;
            if (((recipe.getOutput()).stackTagCompound != null
                    && NEIServerUtils.areStacksSameType(recipe.getOutput(), result))
                    || ((recipe.getOutput()).stackTagCompound == null
                            && NEIServerUtils.areStacksSameTypeCrafting(recipe.getOutput(), result)))
                this.arecipes.add(getCachedRecipe(recipe));
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (RecipeAdvancedPlate recipe : AdvancedBotanyAPI.advancedPlateRecipes) {
            if (recipe == null) continue;
            CachedAdvancedPlateRecipe crecipe = getCachedRecipe(recipe);
            if (crecipe.contains(crecipe.getIngredients(), ingredient)
                    || crecipe.contains(crecipe.getOtherStacks(), ingredient))
                this.arecipes.add(crecipe);
        }
    }
}
