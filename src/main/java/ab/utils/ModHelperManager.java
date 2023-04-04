package ab.common.utils;

import java.util.ArrayList;
import java.util.List;

import ab.common.lib.register.RecipeListAB;

public class ModHelperManager {

    private static List<IModHelper> helpers;

    public static void preInit() {
        setupHelpers();

        for (IModHelper helper : helpers) {
            helper.preInit();
        }
    }

    public static void init() {
        for (IModHelper helper : helpers) {
            helper.init();
        }
    }

    public static void postInit() {
        for (IModHelper helper : helpers) {
            helper.postInit();
        }
    }

    private static void setupHelpers() {
        helpers = new ArrayList<>();
        helpers.add(new RecipeListAB());
    }
}
