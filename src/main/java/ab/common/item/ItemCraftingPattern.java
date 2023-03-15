package ab.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import ab.AdvancedBotany;

public class ItemCraftingPattern extends Item {

    static IIcon[] icon;

    public ItemCraftingPattern() {
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setHasSubtypes(true);
        icon = new IIcon[10];
    }

    public void registerIcons(IIconRegister ir) {
        icon[0] = ir.registerIcon("ab:blank_pattern");
        icon[1] = ir.registerIcon("ab:pattern_diagonal");
        icon[2] = ir.registerIcon("ab:pattern_T");
        icon[3] = ir.registerIcon("ab:pattern_I");
        icon[4] = ir.registerIcon("ab:pattern_U");
        icon[5] = ir.registerIcon("ab:pattern_6rect");
        icon[6] = ir.registerIcon("ab:pattern_8");
        icon[7] = ir.registerIcon("ab:pattern_2");
        icon[8] = ir.registerIcon("ab:pattern_UV");
        icon[9] = ir.registerIcon("ab:pattern_cross");
    }

    public String getUnlocalizedName(ItemStack stack) {
        return "item.ab.pattern" + stack.getItemDamage();
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < 10; i++) par3List.add(new ItemStack(par1, 1, i));
    }

    public IIcon getIconFromDamage(int meta) {
        return icon[Math.min(meta, 9)];
    }

    public static boolean[][] getPattern(int meta) {
        switch (meta) {
            case 0:
                return new boolean[][] { new boolean[] { true, true, true }, new boolean[] { true, true, true },
                        new boolean[] { true, true, true } };
            case 1:
                return new boolean[][] { new boolean[] { false, false, true }, new boolean[] { false, true, false },
                        new boolean[] { true, false, false } };

            case 2:
                return new boolean[][] { new boolean[] { false, false, false }, new boolean[] { false, true, false },
                        new boolean[] { true, true, true } };

            case 3:
                return new boolean[][] { new boolean[] { true, false, false }, new boolean[] { true, false, false },
                        new boolean[] { true, false, false } };
            case 4:
                return new boolean[][] { new boolean[] { true, true, true }, new boolean[] { true, false, true },
                        new boolean[] { false, false, false } };
            case 5:
                return new boolean[][] { new boolean[] { true, true, true }, new boolean[] { true, true, true },
                        new boolean[] { false, false, false } };
            case 6:
                return new boolean[][] { new boolean[] { true, true, true }, new boolean[] { true, false, true },
                        new boolean[] { true, true, true } };
            case 7:
                return new boolean[][] { new boolean[] { true, true, false }, new boolean[] { false, false, false },
                        new boolean[] { false, false, false } };
            case 8:
                return new boolean[][] { new boolean[] { false, true, false }, new boolean[] { true, false, true },
                        new boolean[] { true, false, true } };
            case 9:
                return new boolean[][] { new boolean[] { false, true, false }, new boolean[] { true, true, true },
                        new boolean[] { false, true, false } };
        }
        return null;
    }
}
