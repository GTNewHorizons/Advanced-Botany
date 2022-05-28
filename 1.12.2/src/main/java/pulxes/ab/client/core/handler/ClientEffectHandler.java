package pulxes.ab.client.core.handler;

import java.util.ArrayList;
import java.util.List;

import pulxes.ab.api.EffectRender;

public class ClientEffectHandler {

	public static List<EffectRender> effectRendersList = new ArrayList<EffectRender>();
	
	public static void addEffectRender(EffectRender effectRender) {
		effectRendersList.add(effectRender);
	}
	
	public static void tick() {
		if(!effectRendersList.isEmpty())
			for(int i = 0; i < effectRendersList.size(); i++) {
				EffectRender effectRender = effectRendersList.get(i);
				if(effectRender.tick <= 0) {
					effectRendersList.remove(i);
					continue;
				}
				effectRender.doEffect();
				effectRender.tick--;
			}
	}
}
