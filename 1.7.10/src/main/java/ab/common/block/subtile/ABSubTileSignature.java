package ab.common.block.subtile;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
		return "ab.flower." + this.name;
	}
	  
	public String getUnlocalizedLoreTextForStack(ItemStack item) {
		return "tile.ab.flower." + this.name + ".lore";
	}
	
	public void addTooltip(ItemStack stack, EntityPlayer player, List<String> tooltip) {
		
	}
}
