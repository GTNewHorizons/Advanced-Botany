package ab.common.integration.corporea;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.inventory.IInventory;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.CorporeaRequestEvent;

public class EventCorporeaHandler {
	
	@SubscribeEvent
	public void onCorporeaRequest(CorporeaRequestEvent event) {
		for(IInventory inv : CorporeaHelper.getInventoriesOnNetwork(event.spark)) 
			if(inv instanceof ICorporeaInterrupter)
				((ICorporeaInterrupter)inv).onInterrupt(event);
	}

}
