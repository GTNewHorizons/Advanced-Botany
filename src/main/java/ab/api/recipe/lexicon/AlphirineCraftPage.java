package ab.api.recipe.lexicon;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAncientAlphirine;
import ab.client.core.ClientHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconRecipeMappings;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.page.PageRecipe;

public class AlphirineCraftPage extends PageRecipe {

    private static final ResourceLocation alphirineOverlay = new ResourceLocation(
            "botania:textures/gui/pureDaisyOverlay.png");
    private RecipeAncientAlphirine recipe;
    private final ItemStack resultStack;

    public AlphirineCraftPage(LexiconEntry entry, ItemStack stack) {
        this(entry, stack, "");
    }

    public AlphirineCraftPage(LexiconEntry entry, ItemStack stack, String str) {
        super(str);
        resultStack = stack;
        refreshRecipe(entry, stack);
    }

    public ItemStack getResult() {
        return resultStack;
    }

    public void refreshRecipe(LexiconEntry entry, ItemStack stack) {
        RecipeAncientAlphirine rec = null;
        for (RecipeAncientAlphirine recipe : AdvancedBotanyAPI.alphirineRecipes) {
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
        FontRenderer font = (Minecraft.getMinecraft()).fontRenderer;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        render.bindTexture(alphirineOverlay);
        ((GuiScreen) gui)
                .drawTexturedModalRect(gui.getLeft() + 40, gui.getTop() + 44, 0, 0, gui.getWidth(), gui.getHeight());
        ItemStack inp = recipe.getInput().copy();
        if (inp.getItemDamage() == 32767) inp.setItemDamage(0);
        this.renderItem(gui, gui.getLeft() + 34, gui.getTop() + 59, inp, false);
        this.renderItem(
                gui,
                gui.getLeft() + 62,
                gui.getTop() + 57,
                ItemBlockSpecialFlower.ofType("ancientAlphirine").copy(),
                false);
        this.renderItem(gui, gui.getLeft() + 93, gui.getTop() + 54, recipe.getOutput().copy(), false);
        int x = gui.getLeft() + gui.getWidth() / 2 - 50;
        int y = gui.getTop() + 85;
        String name = EnumChatFormatting.BOLD + StatCollector.translateToLocal("ab.name.alpherine.craft");
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);
        font.drawString(name, x + 50 - font.getStringWidth(name) / 2, y - 65, -1728053248);
        font.setUnicodeFlag(unicode);
        ClientHelper.drawChanceBar(x + 21, y + 14, recipe.getChance());
        GL11.glDisable(3042);
    }

    public List<ItemStack> getDisplayedRecipes() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.add(recipe.getOutput());
        return list;
    }
}
