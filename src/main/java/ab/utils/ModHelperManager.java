package ab.utils;

import ab.common.lib.register.RecipeListAB;

public class ModHelperManager {

    private static IModHelper helper;

    public static void preInit() {
        setupHelpers();

        helper.preInit();
    }

    public static void init() {
        helper.init();
    }

    public static void postInit() {
        helper.postInit();
    }

    private static void setupHelpers() {

        helper = new RecipeListAB();
    }
}
