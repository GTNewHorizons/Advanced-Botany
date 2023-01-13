package ab.common.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import vazkii.botania.api.BotaniaAPI;

public class ItemBlockBoard extends ItemBlockBase {

	public ItemBlockBoard(Block block) {
		super(block);
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		if(stack.getItemDamage() == 1) {
			if(GuiScreen.isShiftKeyDown()) {
				addStringToTooltip(StatCollector.translateToLocal("abmisc.fateBoard.info0"), list);
				addStringToTooltip(StatCollector.translateToLocal("abmisc.fateBoard.info1"), list);
			} else 
				addStringToTooltip(StatCollector.translateToLocal("botaniamisc.shiftinfo"), list); 	
		}
	}
	 
	public void addStringToTooltip(String s, List tooltip) {
		tooltip.add(s.replaceAll("&", "\u00A7"));
	}

	public EnumRarity getRarity(ItemStack stack) {
		if(stack.getItemDamage() == 1)
			return BotaniaAPI.rarityRelic;
		return super.getRarity(stack);
	}
}