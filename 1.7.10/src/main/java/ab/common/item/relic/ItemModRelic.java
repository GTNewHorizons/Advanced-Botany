package ab.common.item.relic;

import ab.AdvancedBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
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
}
