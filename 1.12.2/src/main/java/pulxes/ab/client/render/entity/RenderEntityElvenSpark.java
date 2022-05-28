package pulxes.ab.client.render.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import pulxes.ab.client.core.handler.IconMiscHandler;
import pulxes.ab.common.entity.EntityElvenSpark;
import vazkii.botania.client.core.handler.MiscellaneousIcons;
import vazkii.botania.client.render.entity.RenderSparkBase;

public class RenderEntityElvenSpark extends RenderSparkBase<EntityElvenSpark> {

	public RenderEntityElvenSpark(RenderManager manager) {
		super(manager);
	}
	
	public TextureAtlasSprite getBaseIcon(EntityElvenSpark entity) {
	    return IconMiscHandler.INSTANCE.elvenSpark;
	}
	
	public TextureAtlasSprite getSpinningIcon(EntityElvenSpark entity) {
	    int upgrade = entity.getUpgrade().ordinal() - 1;
	    return (upgrade >= 0 && upgrade < MiscellaneousIcons.INSTANCE.sparkUpgradeIcons.length) ? MiscellaneousIcons.INSTANCE.sparkUpgradeIcons[upgrade] : null;
	}
}