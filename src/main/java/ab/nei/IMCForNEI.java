package ab.nei;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInterModComms;

public class IMCForNEI {

    public static void IMCSender() {
        sendHandler("ab.client.nei.RecipeHandlerAlphirine", "Botania:lexicon");
        sendCatalyst("ab.client.nei.RecipeHandlerAlphirine", "Botania:lexicon");

        sendHandler("ab.client.nei.RecipeHandlerAdvancedPlate", "AdvancedBotany:blockABPlate");
        sendCatalyst("ab.client.nei.RecipeHandlerAdvancedPlate", "AdvancedBotany:blockABPlate");

        sendHandler("ab.client.nei.RecipeHandlerFountainMana", "AdvancedBotany:blockABFountain");
        sendCatalyst("ab.client.nei.RecipeHandlerFountainMana", "AdvancedBotany:blockABFountain");

        sendHandler("ab.client.nei.RecipeHandlerFountainAlchemy", "AdvancedBotany:blockABAlchemy");
        sendCatalyst("ab.client.nei.RecipeHandlerFountainAlchemy", "AdvancedBotany:blockABAlchemy");

        sendHandler("ab.client.nei.RecipeHandlerFountainConjuration", "AdvancedBotany:blockABConjuration");
        sendCatalyst("ab.client.nei.RecipeHandlerFountainConjuration", "AdvancedBotany:blockABConjuration");

    }

    private static void sendHandler(String aName, String aBlock) {
        sendHandler(aName, aBlock, 1);
    }

    private static void sendHandler(String aName, String aBlock, int maxRecipesPerPage) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", "Advanced Botany");
        aNBT.setString("modId", "AdvancedBotany");
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", aBlock);
        aNBT.setInteger("handlerHeight", 135);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", maxRecipesPerPage);
        aNBT.setInteger("yShift", 6);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack, int aPriority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("itemName", aStack);
        aNBT.setInteger("priority", aPriority);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack) {
        sendCatalyst(aName, aStack, 0);
    }
}
