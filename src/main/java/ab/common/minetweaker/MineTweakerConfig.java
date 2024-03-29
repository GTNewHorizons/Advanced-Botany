package ab.common.minetweaker;

import minetweaker.MineTweakerAPI;

public class MineTweakerConfig {

    public static void registerMT() {
        MineTweakerAPI.registerClass(AdvancedPlateMT.class);
        MineTweakerAPI.registerClass(AdvancedPlatePageMT.class);
        MineTweakerAPI.registerClass(AlphirineMT.class);
        MineTweakerAPI.registerClass(AlphirinePageMT.class);
    }
}
