package ab.client.render.entity;

import net.minecraft.util.IIcon;

import ab.common.entity.EntityAdvancedSpark;
import ab.common.item.ItemAdvancedSpark;
import vazkii.botania.client.render.entity.RenderSparkBase;
import vazkii.botania.common.item.ItemSparkUpgrade;

public class RenderEntityAdvancedSpark extends RenderSparkBase<EntityAdvancedSpark> {

    public IIcon getSpinningIcon(EntityAdvancedSpark entity) {
        int upgrade = entity.getUpgrade() - 1;
        return (upgrade >= 0 && upgrade < ItemSparkUpgrade.worldIcons.length) ? ItemSparkUpgrade.worldIcons[upgrade]
                : null;
    }

    public IIcon getBaseIcon(EntityAdvancedSpark entity) {
        return ItemAdvancedSpark.worldIcon;
    }
}
