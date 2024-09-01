package ab.utils;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import vazkii.botania.common.lib.LibOreDict;

public class OreDict {

    public static final String[] FLOWER_INGREDIENT = new String[] { "flowerIngredientWhite", "flowerIngredientOrange",
            "flowerIngredientMagenta", "flowerIngredientLightBlue", "flowerIngredientYellow", "flowerIngredientLime",
            "flowerIngredientPink", "flowerIngredientGray", "flowerIngredientLightGray", "flowerIngredientCyan",
            "flowerIngredientPurple", "flowerIngredientBlue", "flowerIngredientBrown", "flowerIngredientGreen",
            "flowerIngredientRed", "flowerIngredientBlack" };

    public static final String[] FLOWER_VANILLA = new String[] { "flowerWhite", "flowerOrange", "flowerMagenta",
            "flowerLightBlue", "flowerYellow", "flowerLime", "flowerPink", "flowerGray", "flowerLightGray",
            "flowerCyan", "flowerPurple", "flowerBlue", "flowerBrown", "flowerGreen", "flowerRed", "flowerBlack" };

    public static final String GAIA_SPIRIT = LibOreDict.LIFE_ESSENCE;

    public static final String MUSHROOM = "listMagicMushroom";

    public static final String FLOWER_NONMAGICAL = "flowerNonmagical";

    // GT Material references
    public static final String MANA_STEEL_PLATE = "plateManasteel";
    public static final String DENSE_MANA_STEEL_PLATE = "plateDenseManasteel";
    public static final String TERRA_STEEL_PLATE = "plateTerrasteel";
    public static final String DENSE_TERRA_STEEL_PLATE = "plateDenseTerrasteel";
    public static final String ELEMENTIUM_PLATE = "plateElvenElementium";
    public static final String DENSE_ELEMENTIUM_STEEL_PLATE = "plateDenseElvenElementium";

    public static final ItemStack preference(String... oredictKeys) {
        for (String key : oredictKeys) {
            List<ItemStack> ores = OreDictionary.getOres(key);
            if (ores != null && !ores.isEmpty()) return ores.get(0);
        }
        throw new IllegalArgumentException("Can't find any oreDictionary entry among " + Arrays.toString(oredictKeys));
    }
}
