package ab.common.minetweaker;

import java.util.List;

import net.minecraft.item.ItemStack;

import ab.api.recipe.lexicon.AdvancedPlateCraftPage;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;

@ZenClass("mods.advBotany.AdvancedPlatePage")
public class AdvancedPlatePageMT {

    @ZenMethod
    public static void refresh(String name, int page) {
        List<LexiconEntry> entries = BotaniaAPI.getAllEntries();
        LexiconEntry entry = null;
        LexiconPage recipePage = null;
        for (int i = 0; i < entries.size(); i++) {
            if (((LexiconEntry) entries.get(i)).getUnlocalizedName().equalsIgnoreCase(name)) {
                entry = entries.get(i);
                recipePage = entries.get(i).pages.get(page);
                break;
            }
        }
        if (entry == null) {
            MineTweakerAPI.getLogger().logError("Cannot find lexicon entry");
            return;
        } else if (recipePage == null || !(recipePage instanceof AdvancedPlateCraftPage)) {
            MineTweakerAPI.getLogger().logError("Cannot find Advanced Plate Craft Page");
            return;
        }
        MineTweakerAPI.apply(new RefreshPage(entry, (AdvancedPlateCraftPage) recipePage, page));
    }

    private static class RefreshPage implements IUndoableAction {

        private LexiconEntry entry;
        private AdvancedPlateCraftPage page;
        private ItemStack result;
        private int pageCount;

        public RefreshPage(LexiconEntry lexEntry, AdvancedPlateCraftPage abPlatePage, int count) {
            page = abPlatePage;
            result = abPlatePage.getResult();
            pageCount = count;
            entry = lexEntry;
        }

        public void apply() {
            page.refreshRecipe(entry, result);
        }

        public boolean canUndo() {
            return true;
        }

        public void undo() {
            for (LexiconPage page : entry.pages) {
                if (page instanceof AdvancedPlateCraftPage) {
                    AdvancedPlateCraftPage platePage = (AdvancedPlateCraftPage) page;
                    if (platePage.getResult().isItemEqual(result)) return;
                }
            }
            entry.pages.add(pageCount, new AdvancedPlateCraftPage(entry, result, page.getUnlocalizedName()));
        }

        public String describe() {
            return "Refresh Advanced Plate Page for item " + result.getDisplayName();
        }

        public String describeUndo() {
            return "Undo Refresh Advanced Plate Page for item " + result.getDisplayName();
        }

        public Object getOverrideKey() {
            return null;
        }
    }

    @ZenMethod
    public static void addPage(String name, int page, IItemStack stack, String text) {
        LexiconEntry entry = null;
        ItemStack result = MineTweakerMC.getItemStack(stack);
        List<LexiconEntry> entries = BotaniaAPI.getAllEntries();
        for (int i = 0; i < entries.size(); i++) {
            if (((LexiconEntry) entries.get(i)).getUnlocalizedName().equalsIgnoreCase(name)) {
                entry = entries.get(i);
                break;
            }
        }
        if (entry == null) {
            MineTweakerAPI.getLogger().logError("Cannot find lexicon entry");
            return;
        }
        page = Math.min(entry.pages.size(), page);
        MineTweakerAPI.apply(new AddPage(entry, result, page, text));
    }

    private static class AddPage implements IUndoableAction {

        private LexiconEntry entry;
        private ItemStack result;
        private int pageCount;
        private String name;

        public AddPage(LexiconEntry lexEntry, ItemStack stack, int count, String text) {
            entry = lexEntry;
            result = stack;
            pageCount = count;
            name = text;
        }

        public void apply() {
            entry.pages.add(pageCount, new AdvancedPlateCraftPage(entry, result, name));
        }

        public boolean canUndo() {
            return true;
        }

        public void undo() {
            entry.pages.remove(pageCount);
        }

        public String describe() {
            return "Add Advanced Plate Page for item " + result.getDisplayName();
        }

        public String describeUndo() {
            return "Remove Advanced Plate Page for item " + result.getDisplayName();
        }

        public Object getOverrideKey() {
            return null;
        }
    }
}
