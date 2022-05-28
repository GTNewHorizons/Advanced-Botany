package pulxes.ab.client.core.handler;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.client.core.handler.PlayerItemUsingSound.ClientSoundHandler;

public class ClientTickHandler {

	@SubscribeEvent
	public void clientTickEnd(TickEvent.ClientTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			ClientSoundHandler.tick();
			ClientEffectHandler.tick();
		}
	}
	
	public static void renderTooltip() {
		
	}
}