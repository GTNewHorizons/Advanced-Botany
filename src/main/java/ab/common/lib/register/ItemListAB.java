package ab.common.lib.register;

import net.minecraft.item.Item;

import ab.common.core.handler.ConfigABHandler;
import ab.common.item.*;
import ab.common.item.equipment.*;
import ab.common.item.equipment.armor.ItemNebulaBoots;
import ab.common.item.equipment.armor.ItemNebulaChest;
import ab.common.item.equipment.armor.ItemNebulaHelm;
import ab.common.item.equipment.armor.ItemNebulaHelmRevealing;
import ab.common.item.equipment.armor.ItemNebulaLegs;
import ab.common.item.relic.ItemFreyrSlingshot;
import ab.common.item.relic.ItemHornPlenty;
import ab.common.item.relic.ItemPocketWardrobe;
import ab.common.item.relic.ItemSphereNavigation;
import ab.common.item.relic.ItemTalismanHiddenRiches;
import cpw.mods.fml.common.registry.GameRegistry;
import vazkii.botania.common.Botania;

public class ItemListAB {

    public static Item itemABResource;
    public static Item itemMithrillRing;
    public static Item itemMihrillMultiTool;
    public static Item itemNebulaRing;
    public static Item itemAdvancedSpark;
    public static Item itemMithrillSword;
    public static Item itemTerraHoe;
    public static Item itemManaFlower;
    public static Item itemBlackHalo;
    public static Item itemNebulaBlaze;
    public static Item itemAntigravityCharm;
    public static Item itemNebulaHelm;
    public static Item itemNebulaHelmReveal;
    public static Item itemNebulaChest;
    public static Item itemNebulaLegs;
    public static Item itemNebulaBoots;
    public static Item itemFreyrSlingshot;
    public static Item itemNebulaRod;
    public static Item itemCraftingPattern;
    public static Item itemSprawlRod;
    public static Item itemPocketWardrobe;
    public static Item itemTalismanHiddenRiches;
    public static Item itemHornPlenty;
    public static Item itemSphereNavigation;
    public static Item itemAquaSword;
    public static Item itemWildHuntHelm;
    public static Item itemWildHuntChest;
    public static Item itemWildHuntLegs;
    public static Item itemWildHuntBoots;

    public static void init() {
        ItemListAB.itemABResource = new ItemABResource();
        GameRegistry.registerItem(itemABResource, "itemABResource");
        ItemListAB.itemMithrillRing = new ItemMithrillRing("mithrillManaRing");
        GameRegistry.registerItem(itemMithrillRing, "itemMithrillRing");
        ItemListAB.itemNebulaRing = new ItemNebulaRing("nebulaManaRing");
        GameRegistry.registerItem(itemNebulaRing, "itemNebulaRing");
        ItemListAB.itemMihrillMultiTool = new ItemMithrillMultiTool();
        GameRegistry.registerItem(itemMihrillMultiTool, "itemMihrillMultiTool");
        ItemListAB.itemAdvancedSpark = new ItemAdvancedSpark();
        GameRegistry.registerItem(itemAdvancedSpark, "itemAdvancedSpark");
        ItemListAB.itemMithrillSword = new ItemSpaceBlade();
        GameRegistry.registerItem(itemMithrillSword, "itemMithrillSword");
        ItemListAB.itemTerraHoe = new ItemTerraHoe();
        GameRegistry.registerItem(itemTerraHoe, "itemTerraHoe");
        ItemListAB.itemManaFlower = new ItemManaFlower();
        GameRegistry.registerItem(itemManaFlower, "itemManaFlower");
        ItemListAB.itemBlackHalo = new ItemBlackHalo();
        GameRegistry.registerItem(itemBlackHalo, "itemBlackHalo");
        ItemListAB.itemNebulaBlaze = new ItemNebulaBlaze();
        GameRegistry.registerItem(itemNebulaBlaze, "itemNebulaBlaze");
        ItemListAB.itemAntigravityCharm = new ItemAntigravityCharm();
        GameRegistry.registerItem(itemAntigravityCharm, "itemAntigravityCharm");
        ItemListAB.itemNebulaRod = new ItemNebulaRod();
        GameRegistry.registerItem(itemNebulaRod, "itemNebulaRod");
        ItemListAB.itemSprawlRod = new ItemSprawlRod();
        GameRegistry.registerItem(itemSprawlRod, "itemSprawlRod");
        ItemListAB.itemAquaSword = new ItemAquaSword();
        GameRegistry.registerItem(itemAquaSword, "itemAquaSword");

        ItemListAB.itemPocketWardrobe = new ItemPocketWardrobe();
        ItemListAB.itemTalismanHiddenRiches = new ItemTalismanHiddenRiches();
        ItemListAB.itemFreyrSlingshot = new ItemFreyrSlingshot();
        ItemListAB.itemHornPlenty = new ItemHornPlenty();
        ItemListAB.itemSphereNavigation = new ItemSphereNavigation();

        // ItemListAB.itemWildHuntHelm = new ItemWildHuntHelm();
        // ItemListAB.itemWildHuntChest = new ItemWildHuntChest();
        // ItemListAB.itemWildHuntLegs = new ItemWildHuntLegs();
        // ItemListAB.itemWildHuntBoots = new ItemWildHuntBoots();

        ItemListAB.itemNebulaHelm = new ItemNebulaHelm();
        ItemListAB.itemNebulaChest = new ItemNebulaChest();
        ItemListAB.itemNebulaLegs = new ItemNebulaLegs();
        ItemListAB.itemNebulaBoots = new ItemNebulaBoots();
        if (Botania.thaumcraftLoaded) thaumcraft();

    }

    public static void thaumcraft() {
        ItemListAB.itemNebulaHelmReveal = new ItemNebulaHelmRevealing();
        if (ConfigABHandler.hasAutoThaum) {
            ItemListAB.itemCraftingPattern = new ItemCraftingPattern();
            GameRegistry.registerItem(itemCraftingPattern, "itemCraftingPattern");
        }
    }
}
