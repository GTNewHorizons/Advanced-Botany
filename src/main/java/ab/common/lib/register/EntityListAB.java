package ab.common.lib.register;

import ab.AdvancedBotany;
import ab.common.entity.EntityAdvancedSpark;
import ab.common.entity.EntityAlphirinePortal;
import ab.common.entity.EntityManaVine;
import ab.common.entity.EntityNebulaBlaze;
import ab.common.entity.EntitySeed;
import ab.common.entity.EntitySword;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityListAB {

	 public static void init() {
		 int id = 0;
		 EntityRegistry.registerModEntity(EntityAdvancedSpark.class, "advancedSpark", id++, AdvancedBotany.instance, 64, 10, false);
		 EntityRegistry.registerModEntity(EntityNebulaBlaze.class, "nebulaBlaze", id++, AdvancedBotany.instance, 64, 10, true);
		 EntityRegistry.registerModEntity(EntityManaVine.class, "manaVineBall", id++, AdvancedBotany.instance, 64, 10, true);
		 EntityRegistry.registerModEntity(EntityAlphirinePortal.class, "alphirinePortal", id++, AdvancedBotany.instance, 64, 10, false);
		 EntityRegistry.registerModEntity(EntitySword.class, "entitySword", id++, AdvancedBotany.instance, 64, 10, true);
		 EntityRegistry.registerModEntity(EntitySeed.class, "entitySeed", id++, AdvancedBotany.instance, 64, 10, true);
	 }
}
