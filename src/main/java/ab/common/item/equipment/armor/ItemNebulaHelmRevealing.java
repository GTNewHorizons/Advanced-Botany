package ab.common.item.equipment.armor;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;

@InterfaceList({@Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IGoggles", striprefs = true), @Interface(modid = "Thaumcraft", iface = "thaumcraft.api.nodes.IRevealer", striprefs = true)})
public class ItemNebulaHelmRevealing extends ItemNebulaHelm implements IGoggles, IRevealer {

	public ItemNebulaHelmRevealing() {
		super("nebulaHelmRevealing");
	}
	
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
		  
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
