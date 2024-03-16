package ab.common.item.relic;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import ab.AdvancedBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.item.relic.ItemRelic;

public class ItemModRelic extends ItemRelic {

    public ItemModRelic(String name) {
        super(name);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setNoRepair();
    }

    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        String str = this.getUnlocalizedName(stack);
        return str == null ? "" : StatCollector.translateToLocal(str);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        String str = this.getUnlocalizedName();
        this.itemIcon = ir.registerIcon("ab:" + str.replace("item.", ""));
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (GuiScreen.isShiftKeyDown()) {
            String bind = getSoulbindUsernameS(stack);
            if (bind.isEmpty()) addStringToTooltip(StatCollector.translateToLocal("botaniamisc.relicUnbound"), list);
            else {
                addStringToTooltip(
                        String.format(
                                StatCollector.translateToLocal("botaniamisc.relicSoulbound"),
                                new Object[] { bind }),
                        list);
                if (!isRightPlayer(player, stack)) addStringToTooltip(
                        String.format(
                                StatCollector.translateToLocal("botaniamisc.notYourSagittarius"),
                                new Object[] { bind }),
                        list);
            }
        } else addStringToTooltip(StatCollector.translateToLocal("botaniamisc.shiftinfo"), list);
    }

    public void addStringToTooltip(String s, List tooltip) {
        tooltip.add(s.replaceAll("&", "\u00A7"));
    }
}
