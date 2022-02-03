package ab.api.recipe.lexicon;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import ab.api.recipe.RecipeAncientAlphirine;
import ab.client.core.ClientHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconRecipeMappings;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.page.PageRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class AlphirineCraftPage extends PageRecipe {
	
	private static final ResourceLocation alphirineOverlay = new ResourceLocation("botania:textures/gui/pureDaisyOverlay.png");
	private RecipeAncientAlphirine recipe;

	public AlphirineCraftPage(RecipeAncientAlphirine recipe) {
		super(".alphirineCraft");
		this.recipe = recipe;
	}
	
	public AlphirineCraftPage(RecipeAncientAlphirine recipe, String str) {
		super(".alphirineCraft" + str);
		this.recipe = recipe;
	}
	
	public void onPageAdded(LexiconEntry entry, int index) {
		LexiconRecipeMappings.map(recipe.getOutput(), entry, index);
	}
	
	@SideOnly(Side.CLIENT)
	public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {
		TextureManager render = (Minecraft.getMinecraft()).renderEngine;
		render.bindTexture(alphirineOverlay);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		((GuiScreen)gui).drawTexturedModalRect(gui.getLeft() + 40, gui.getTop() + 44, 0, 0, gui.getWidth(), gui.getHeight());
		GL11.glDisable(3042); 
		ItemStack inp = recipe.getInput().copy();
    	if (inp.getItemDamage() == 32767)
    		inp.setItemDamage(0); 
		this.renderItem(gui, gui.getLeft() + 34, gui.getTop() + 59, inp, false);
		this.renderItem(gui, gui.getLeft() + 62, gui.getTop() + 57, ItemBlockSpecialFlower.ofType("ancientAlphirine").copy(), false);
		this.renderItem(gui, gui.getLeft() + 93, gui.getTop() + 54, recipe.getOutput().copy(), false);
		FontRenderer font = (Minecraft.getMinecraft()).fontRenderer;
		String text = ClientHelper.getChanceName(recipe.getChance());
		String name = EnumChatFormatting.BOLD + StatCollector.translateToLocal("ab.name.alpherine.craft");
	    int x = gui.getLeft() + gui.getWidth() / 2 - 50;
	    int y = gui.getTop() + 85;
	    boolean unicode = font.getUnicodeFlag();
	    font.setUnicodeFlag(true);
	    font.drawString(text, x + 50 - font.getStringWidth(text) / 2, y + 15, -1728053248);
	    font.drawString(name, x + 50 - font.getStringWidth(name) / 2, y - 65, -1728053248);
	    font.setUnicodeFlag(unicode);
	}
	
	public List<ItemStack> getDisplayedRecipes() {
	    ArrayList<ItemStack> list = new ArrayList<ItemStack>();
	    list.add(recipe.getOutput()); 
	    return list;
	}
}
