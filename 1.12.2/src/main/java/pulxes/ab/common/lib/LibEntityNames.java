package pulxes.ab.common.lib;

import net.minecraft.util.ResourceLocation;

public class LibEntityNames {
	
	public static final ResourceLocation SPACE_BURST_REGISTRY = makeName("space_burst");

	private static ResourceLocation makeName(String str) {
		return new ResourceLocation("ab", str);
	}
}
