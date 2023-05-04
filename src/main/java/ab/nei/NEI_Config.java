package ab.nei;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import ab.AdvancedBotany;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEI_Config implements IConfigureNEI {

    public static boolean isAdded = true;

    @Override
    public void loadConfig() {
        NEI_Config.isAdded = false;
        NEI_Config.isAdded = true;
    }

    public static void hide(Block aBlock) {
        API.hideItem(new ItemStack(aBlock, 1));
    }

    public static void hide(Item aItem) {
        API.hideItem(new ItemStack(aItem, 1));
    }

    @Override
    public String getName() {
        return "Advanced Botany NEI Plugin";
    }

    @Override
    public String getVersion() {
        return AdvancedBotany.version;
    }
}
