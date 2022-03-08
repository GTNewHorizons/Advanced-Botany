package ab.common.lib.register;

import ab.AdvancedBotany;
import ab.common.entity.EntityAdvancedSpark;
import ab.common.entity.EntityAlphirinePortal;
import ab.common.entity.EntityManaVine;
import ab.common.entity.EntityNebulaBlaze;
import ab.common.entity.EntitySword;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityListAB {

	 public static void init() {
		 int id = 0;
		 EntityRegistry.registerModEntity(EntityAdvancedSpark.class, "ab:advancedSpark", id++, AdvancedBotany.instance, 64, 10, false);
		 EntityRegistry.registerModEntity(EntityNebulaBlaze.class, "ab:nebulaBlaze", id++, AdvancedBotany.instance, 64, 10, true);
		 EntityRegistry.registerModEntity(EntityManaVine.class, "ab:manaVineBall", id++, AdvancedBotany.instance, 64, 10, true);
		 EntityRegistry.registerModEntity(EntityAlphirinePortal.class, "ab:alphirinePortal", id++, AdvancedBotany.instance, 64, 10, false);
		 EntityRegistry.registerModEntity(EntitySword.class, "ab:entitySword", id++, AdvancedBotany.instance, 64, 10, true);
	 }
}
