package ab.common.lib.register;

import ab.common.item.*;
import ab.common.item.equipment.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

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
	
	public static Item itemFreyrSlingshot;
	
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
		
		//ItemListAB.itemFreyrSlingshot = new ItemFreyrSlingshot();
		//GameRegistry.registerItem(itemFreyrSlingshot, "itemFreyrSlingshot");
		
	}
}
