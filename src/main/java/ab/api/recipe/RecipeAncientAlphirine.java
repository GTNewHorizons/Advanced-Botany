package ab.api.recipe;

import net.minecraft.item.ItemStack;

public class RecipeAncientAlphirine {

    private ItemStack output;
    private ItemStack input;
    private int chance;

    public RecipeAncientAlphirine(ItemStack output, ItemStack input, int chance) {
        this.output = output;
        this.input = input;
        if (chance > 100) chance = 100;
        else if (chance <= 0) chance = 1;
        this.chance = chance;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack getInput() {
        return input;
    }

    public int getChance() {
        return chance;
    }

    public boolean matches(ItemStack stack) {
        ItemStack inputCopy = this.input.copy();
        if (inputCopy.getItemDamage() == 32767) inputCopy.setItemDamage(stack.getItemDamage());
        return stack.getItem() == input.getItem() && stack.getItemDamage() == inputCopy.getItemDamage();
    }
}
