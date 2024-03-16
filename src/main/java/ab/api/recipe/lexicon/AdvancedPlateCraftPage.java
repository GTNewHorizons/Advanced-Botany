package ab.api.recipe.lexicon;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAdvancedPlate;
import ab.client.core.ClientHelper;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconRecipeMappings;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.lexicon.page.PageRecipe;

public class AdvancedPlateCraftPage extends PageRecipe {

    private static final ResourceLocation plateOverlay = new ResourceLocation("botania:textures/gui/petalOverlay.png");
    private RecipeAdvancedPlate recipe;
    private final ItemStack resultStack;
    int ticksElapsed = 0;

    public AdvancedPlateCraftPage(LexiconEntry entry, ItemStack stack) {
        this(entry, stack, "");
    }

    public AdvancedPlateCraftPage(LexiconEntry entry, ItemStack stack, String str) {
        super(str);
        resultStack = stack;
        refreshRecipe(entry, stack);
    }

    public ItemStack getResult() {
        return resultStack;
    }

    public void refreshRecipe(LexiconEntry entry, ItemStack stack) {
        RecipeAdvancedPlate rec = null;
        for (RecipeAdvancedPlate recipe : AdvancedBotanyAPI.advancedPlateRecipes) {
            if (stack != null && recipe.getOutput() != null && recipe.getOutput().isItemEqual(stack)) {
                rec = recipe;
                break;
            }
        }
        if (rec == null) entry.pages.remove(this);
        recipe = rec;
    }

    public void onPageAdded(LexiconEntry entry, int index) {
        LexiconRecipeMappings.map(recipe.getOutput(), entry, index);
    }

    @SideOnly(Side.CLIENT)
    public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {
        TextureManager render = (Minecraft.getMinecraft()).renderEngine;
        renderItemAtGridPos(gui, 3, 0, recipe.getOutput(), false);
        renderItemAtGridPos(gui, 2, 1, new ItemStack(BlockListAB.blockABPlate), false);
        List<ItemStack> inputs = recipe.getInputs();
        int degreePerInput = (int) (360.0F / inputs.size());
        float currentDegree = ConfigHandler.lexiconRotatingItems ? (GuiScreen.isShiftKeyDown() ? this.ticksElapsed
                : (this.ticksElapsed + ClientTickHandler.partialTicks)) : 0.0F;
        for (ItemStack obj : inputs) {
            ItemStack copy = obj.copy();
            if (copy.getItemDamage() == 32767) copy.setItemDamage(0);
            renderItemAtAngle(gui, currentDegree, copy);
            currentDegree += degreePerInput;
        }
        renderManaBar(gui, mx, my);
        render.bindTexture(plateOverlay);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());
        GL11.glDisable(3042);
    }

    @SideOnly(Side.CLIENT)
    public void renderManaBar(IGuiLexiconEntry gui, int mx, int my) {
        FontRenderer font = (Minecraft.getMinecraft()).fontRenderer;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        String manaUsage = StatCollector.translateToLocal("botaniamisc.manaUsage");
        font.drawString(
                manaUsage,
                gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(manaUsage) / 2,
                gui.getTop() + 105,
                1711276032);
        int x = gui.getLeft() + gui.getWidth() / 2 - 50;
        int y = gui.getTop() + 120;
        String stopStr = StatCollector.translateToLocal("botaniamisc.shiftToStopSpin");
        ClientHelper.renderPoolManaBar(x, y - 5, 0x239ddc, 1.0f, this.recipe.getManaUsage());
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);
        font.drawString(stopStr, x + 49 - font.getStringWidth(stopStr) / 2, y + 18, -1728053248);
        font.setUnicodeFlag(unicode);
        GL11.glDisable(3042);
    }

    @SideOnly(Side.CLIENT)
    public void updateScreen() {
        if (GuiScreen.isShiftKeyDown()) return;
        this.ticksElapsed++;
    }

    public List<ItemStack> getDisplayedRecipes() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.add(recipe.getOutput());
        return list;
    }
}
