package ab.client.render.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityNullRender extends Render {

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {}

    protected ResourceLocation getEntityTexture(Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}
