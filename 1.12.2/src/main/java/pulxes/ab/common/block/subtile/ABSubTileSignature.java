package pulxes.ab.common.block.subtile;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.api.subtile.signature.SubTileSignature;

public class ABSubTileSignature implements SubTileSignature {
	
	private final String name;
	  
	public ABSubTileSignature(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public String getUnlocalizedNameForStack(ItemStack stack) {
		return unlocalizedName("");
	}
	  
	public String getUnlocalizedLoreTextForStack(ItemStack stack) {
		return unlocalizedName(".reference");
	}
	
	private String unlocalizedName(String end) {
		return "tile.ab:flower." + this.name + end;
	}
	
	public String getType() {
		Class<? extends SubTileEntity> clazz = BotaniaAPI.getSubTileMapping(this.name);
		if(clazz == null)
			return "uwotm8"; 
		if(SubTileGenerating.class.isAssignableFrom(clazz))
			return "botania.flowerType.generating"; 
		if(SubTileFunctional.class.isAssignableFrom(clazz))
			return "botania.flowerType.functional"; 
		return "botania.flowerType.misc";
	}
	  
	@SideOnly(Side.CLIENT)
	public void addTooltip(ItemStack stack, World world, List<String> tooltip) {
		tooltip.add(TextFormatting.BLUE + I18n.format(getType(), new Object[0]));
	}
}