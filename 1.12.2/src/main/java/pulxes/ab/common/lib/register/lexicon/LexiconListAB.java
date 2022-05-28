package pulxes.ab.common.lib.register.lexicon;

import net.minecraft.util.text.TextFormatting;
import pulxes.ab.common.lib.register.FlowerListAB;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.BLexiconCategory;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconListAB {
	
	public static final LexiconCategory CATEGORY_FORGOTTEN;
	public static final KnowledgeType KNOWLEDGE_FORGOTTEN;

	public static LexiconEntry ancientAlphirine;
	
	public static void init() {
		ancientAlphirine = new BasicLexiconEntry("ancientAlphirine", CATEGORY_FORGOTTEN);
		ancientAlphirine.setPriority().setKnowledgeType(BotaniaAPI.elvenKnowledge).setLexiconPages(new LexiconPage[] { new PageText("0") }).setIcon(ItemBlockSpecialFlower.ofType(FlowerListAB.ANCIENT_ALPHIRINE));
	}
	
	static {
		BotaniaAPI.addCategory(CATEGORY_FORGOTTEN = new BLexiconCategory("forgotten", 5));
		KNOWLEDGE_FORGOTTEN = BotaniaAPI.registerKnowledgeType("forgotten", TextFormatting.BLUE, false);
	}
}
