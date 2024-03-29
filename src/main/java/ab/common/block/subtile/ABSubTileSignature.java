package ab.common.block.subtile;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.api.subtile.signature.PassiveFlower;
import vazkii.botania.api.subtile.signature.SubTileSignature;
import vazkii.botania.common.block.BlockSpecialFlower;

public class ABSubTileSignature extends SubTileSignature {

    String name;
    IIcon icon;

    public ABSubTileSignature(String name) {
        this.name = name;
    }

    public void registerIcons(IIconRegister reg) {
        this.icon = reg.registerIcon("ab:flower_" + this.name);
        BlockSpecialFlower.icons.put(name, icon);
    }

    public IIcon getIconForStack(ItemStack item) {
        return this.icon;
    }

    public String getUnlocalizedNameForStack(ItemStack item) {
        return unlocalizedName("");
    }

    private String unlocalizedName(String end) {
        return "ab.flower." + this.name + end;
    }

    public String getUnlocalizedLoreTextForStack(ItemStack stack) {
        return unlocalizedName(".reference");
    }

    public String getType() {
        Class<? extends SubTileEntity> clazz = BotaniaAPI.getSubTileMapping(this.name);
        if (clazz == null) return "uwotm8";
        if (clazz.getAnnotation(PassiveFlower.class) != null) return "botania.flowerType.passiveGenerating";
        if (SubTileGenerating.class.isAssignableFrom(clazz)) return "botania.flowerType.generating";
        if (SubTileFunctional.class.isAssignableFrom(clazz)) return "botania.flowerType.functional";
        return "botania.flowerType.misc";
    }

    public void addTooltip(ItemStack stack, EntityPlayer player, List<String> tooltip) {
        tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal(getType()));
    }
}
