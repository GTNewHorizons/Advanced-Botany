package pulxes.ab.client.core.handler;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import pulxes.ab.common.item.equipment.ItemSpaceBlade;
import pulxes.ab.common.lib.register.ItemListAB;

public class RenderColorHandler {

	public static void init() {
		ItemColors items = Minecraft.getMinecraft().getItemColors();
		items.registerItemColorHandler((s, t) -> (t == 1) ? Color.HSBtoRGB(0.836F, 1.0F - ((float)ItemSpaceBlade.getRechargeTick(s) / (float)ItemSpaceBlade.MAX_RECHARGE_TICK), 1.0F) : -1, new Item[] { ItemListAB.itemSpaceBlade } );
	}
}
