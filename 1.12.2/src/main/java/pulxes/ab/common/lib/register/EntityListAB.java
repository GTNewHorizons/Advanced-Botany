package pulxes.ab.common.lib.register;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.entity.EntityProjectileSpaceBurst;
import pulxes.ab.common.lib.LibEntityNames;

public class EntityListAB {

	public static void init() {
		int id = 0;
	    EntityRegistry.registerModEntity(LibEntityNames.SPACE_BURST_REGISTRY, EntityProjectileSpaceBurst.class, "ab:spaceBurst", id++, AdvancedBotany.instance, 64, 10, true);
	}
}
