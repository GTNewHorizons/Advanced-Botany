package pulxes.ab.common.lib;

import net.minecraft.util.ResourceLocation;
import pulxes.ab.AdvancedBotany;

public class LibEntityNames {
	
	public static final ResourceLocation SPACE_BURST_REGISTRY = makeName("space_burst");
	public static final ResourceLocation ALPHIRINE_PORTAL_REGISTRY = makeName("alphirine_portal");
	public static final ResourceLocation ELVEN_SPARK_REGISTRY = makeName("elven_spark");

	private static ResourceLocation makeName(String str) {
		return new ResourceLocation(AdvancedBotany.MODID, str);
	}
}
