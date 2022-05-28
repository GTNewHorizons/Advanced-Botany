package pulxes.ab.client.core.handler;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.client.core.helper.IconHelper;

public class IconMiscHandler {
	
	public static final IconMiscHandler INSTANCE = new IconMiscHandler();
	
	public TextureAtlasSprite alphirinePortal;
	public TextureAtlasSprite elvenSpark;

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre evt) {
		this.alphirinePortal = IconHelper.forName(evt.getMap(), "alphirine_portal", "blocks");
		this.elvenSpark = IconHelper.forName(evt.getMap(), "elven_spark", "items");
	}
}
