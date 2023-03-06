package ab.common.core.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import ab.AdvancedBotany;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigABHandler {

    public static Configuration config;

    public static final String CATEGORY_BALANCE = "general." + "balance";
    public static final String CATEGORY_BALANCE_MANA_COST = "general." + "balance.manacost";
    public static final String CATEGORY_RELICS = "general." + "relics";
    public static final String CATEGORY_INTEGRATION = "general." + "integration";

    // Balance

    public static int[] maxContainerMana = new int[] { 64000000, 8000000, 64000000 };
    public static double protectionFactorNebula = 1.0f;
    public static double damageFactorSpaceSword = 1.0f;
    public static int nebulaWandCooldownTick = 18;
    public static int spreaderMaxMana = 128000;
    public static int spreaderBurstMana = 32000;
    public static String[] lockWorldNameNebulaRod = new String[] {};
    public static int limitXZCoords = 30000000;
    public static int maxDictariusCount = 64;
    public static int sprawlRodMaxArea = 64;

    // Balance - mana cost

    public static int nebulaRodManaCost = 180;
    public static int sphereNavigationManaCost = 2500;

    // Relics

    public static String[] lockEntityListToHorn = new String[] {};
    public static String[] relicNames = new String[] { "infiniteFruit", "kingKey", "flugelEye", "thorRing", "odinRing",
            "lokiRing", "freyrSlingshot", "keyHiddenRiches", "pocketWardrobe", "sphereNavigation", "hornPlenty" };
    public static boolean[] fateBoardRelicEnables = new boolean[relicNames.length];

    // Integration

    public static boolean hasAutoThaum = true;

    // Misc

    public static boolean useManaChargerAnimation = true;
    public static boolean hasManaCharger = true;

    static {
        for (int i = 0; i < fateBoardRelicEnables.length; i++) fateBoardRelicEnables[i] = true;
    }

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);
        config.load();
        loadCategories();
        load();
        FMLCommonHandler.instance().bus().register(new ChangeListener());
    }

    public static void load() {

        // Balance Settings

        String desc = "Maximum mana capacity for Mana Container";
        maxContainerMana[0] = loadPropInt(
                CATEGORY_BALANCE,
                "Mana Container Capacity",
                desc,
                false,
                maxContainerMana[0]);
        desc = "Maximum mana capacity for Diluted Mana Container";
        maxContainerMana[1] = loadPropInt(
                CATEGORY_BALANCE,
                "Mana Container Capacity (Diluted)",
                desc,
                false,
                maxContainerMana[1]);
        desc = "Maximum mana capacity for Fabulous Mana Container";
        maxContainerMana[2] = loadPropInt(
                CATEGORY_BALANCE,
                "Mana Container Capacity (Fabulous)",
                desc,
                false,
                maxContainerMana[2]);
        desc = "Protection factor for nebula armour";
        protectionFactorNebula = loadPropDouble(
                CATEGORY_BALANCE,
                "Nebula Armor Protection Factor",
                desc,
                false,
                protectionFactorNebula);
        desc = "Damage factor for Space Blade";
        damageFactorSpaceSword = loadPropDouble(
                CATEGORY_BALANCE,
                "Space Sword Damage Factor",
                desc,
                false,
                damageFactorSpaceSword);
        desc = "The number of ticks needed to restore 1 unit of the Rod of Nebula strength.";
        nebulaWandCooldownTick = loadPropInt(
                CATEGORY_BALANCE,
                "Rod of Nebula Cooldown",
                desc,
                false,
                nebulaWandCooldownTick);
        desc = "Maximum amount of mana held in a mana spreader";
        spreaderMaxMana = loadPropInt(CATEGORY_BALANCE, "Spreader Max Mana", desc, false, spreaderMaxMana);
        desc = "Amount of Mana in a Mana Burst";
        spreaderBurstMana = loadPropInt(CATEGORY_BALANCE, "Spreader Burst Mana", desc, false, spreaderBurstMana);
        desc = "To block a creature, type it's class name";
        lockWorldNameNebulaRod = loadPropString(
                CATEGORY_BALANCE,
                "Locking Worlds for Teleportation with Nebula Rod",
                desc,
                false,
                lockWorldNameNebulaRod);
        desc = "Limitation on X Z coordinates for teleportation, do not increase the default value";
        limitXZCoords = loadPropInt(
                CATEGORY_BALANCE,
                "Restriction on X Z coordinates for Rod of Nebula",
                desc,
                false,
                limitXZCoords);
        desc = "Limit the number of flowers next to each other";
        maxDictariusCount = loadPropInt(CATEGORY_BALANCE, "Max Dictarius Count", desc, false, maxDictariusCount);
        desc = "Changes the area of effect of a projectile created with Rod of Sprawl";
        sprawlRodMaxArea = loadPropInt(CATEGORY_BALANCE, "Rod of Sprawl Max Area", desc, false, sprawlRodMaxArea);

        // Balance Settings - mana cost

        desc = "Mana cost to restore one unit of strength";
        nebulaRodManaCost = loadPropInt(
                CATEGORY_BALANCE_MANA_COST,
                "Rod of Nebula repair Mana cost",
                desc,
                false,
                nebulaRodManaCost);

        desc = "Mana cost to try to find the specified block";
        sphereNavigationManaCost = loadPropInt(
                CATEGORY_BALANCE_MANA_COST,
                "Sphere of Navigation Mana cost",
                desc,
                false,
                sphereNavigationManaCost);

        // Relics Settings

        lockEntityListToHorn = loadPropString(
                CATEGORY_RELICS,
                "Blocked creatures for double drop",
                desc,
                false,
                lockEntityListToHorn);
        desc = "Enter the name of the World you want to add to the blacklist";

        desc = "Enable or disable relic drop on the Fate Playing Board";
        for (int i = 0; i < fateBoardRelicEnables.length; i++) fateBoardRelicEnables[i] = loadPropBool(
                CATEGORY_RELICS,
                "Enable relic: " + relicNames[i],
                desc,
                false,
                fateBoardRelicEnables[i]);

        // Misc Settings

        desc = "Activating the charging animation for the Mana Charger";
        useManaChargerAnimation = loadPropBool(
                "general",
                "Mana Charger lighting",
                desc,
                false,
                useManaChargerAnimation);
        desc = "Switching the Mana Charger on or off in the game";
        hasManaCharger = loadPropBool("general", "Enable Mana Charger", desc, true, hasManaCharger);

        // Integration Settings

        desc = "Switching the Thaumim Crafty Crate on or off in the game";
        hasAutoThaum = loadPropBool(CATEGORY_INTEGRATION, "Enable Thaumim Crafty Crate", desc, true, hasAutoThaum);

        if (config.hasChanged()) config.save();
    }

    public static void loadCategories() {
        addCategory(CATEGORY_BALANCE, "Advanced Botany: balance settings");
        addCategory(CATEGORY_BALANCE_MANA_COST, "Advanced Botany: balance settings (mana cost)");
        addCategory(CATEGORY_RELICS, "Advanced Botany: relics settings");
        addCategory(CATEGORY_INTEGRATION, "Advanced Botany: integration settings");
    }

    public static void addCategory(String name, String comment) {
        config.addCustomCategoryComment(name, comment);
        config.getCategory(name).setLanguageKey(name);
    }

    public static void loadPostInit() {
        if (config.hasChanged()) config.save();
    }

    public static String[] loadPropString(String category, String propName, String desc, boolean hasRestart,
            String[] default_) {
        Property prop = config.get(category, propName, default_);
        prop.comment = desc;
        prop.setRequiresMcRestart(hasRestart);
        return prop.getStringList();
    }

    public static boolean loadPropBool(String category, String propName, String desc, boolean hasRestart,
            boolean default_) {
        Property prop = config.get(category, propName, default_);
        prop.comment = desc;
        prop.setRequiresMcRestart(hasRestart);
        return prop.getBoolean(default_);
    }

    public static int loadPropInt(String category, String propName, String desc, boolean hasRestart, int default_) {
        Property prop = config.get(category, propName, default_);
        prop.comment = desc;
        prop.setRequiresMcRestart(hasRestart);
        return prop.getInt(default_);
    }

    public static double loadPropDouble(String category, String propName, String desc, boolean hasRestart,
            double default_) {
        Property prop = config.get(category, propName, default_);
        prop.comment = desc;
        prop.setRequiresMcRestart(hasRestart);
        return prop.getDouble(default_);
    }

    public static class ChangeListener {

        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
            if (eventArgs.modID.equals(AdvancedBotany.modid)) ConfigABHandler.load();
        }
    }
}
