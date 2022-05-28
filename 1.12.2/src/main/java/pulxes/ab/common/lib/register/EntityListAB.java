package pulxes.ab.common.lib.register;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.entity.EntityAlphirinePortal;
import pulxes.ab.common.entity.EntityElvenSpark;
import pulxes.ab.common.entity.EntityProjectileSpaceBurst;
import pulxes.ab.common.lib.LibEntityNames;

public class EntityListAB {

	public static void init() {
		int id = 0;
	    EntityRegistry.registerModEntity(LibEntityNames.SPACE_BURST_REGISTRY, EntityProjectileSpaceBurst.class, AdvancedBotany.MODID + ":spaceBurst", id++, AdvancedBotany.instance, 64, 10, true);
	    EntityRegistry.registerModEntity(LibEntityNames.ALPHIRINE_PORTAL_REGISTRY, EntityAlphirinePortal.class, AdvancedBotany.MODID + ":alphirinePortal", id++, AdvancedBotany.instance, 64, 10, true);
	    EntityRegistry.registerModEntity(LibEntityNames.ELVEN_SPARK_REGISTRY, EntityElvenSpark.class, AdvancedBotany.MODID + ":elvenSpark", id++, AdvancedBotany.instance, 64, 10, true);
	}
}
