package pulxes.ab.common.item;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pulxes.ab.AdvancedBotany;
import vazkii.botania.client.render.IModelRegister;

public class ItemMod extends Item implements IModelRegister {

	public ItemMod(String name) {
		this.setCreativeTab(AdvancedBotany.tabAB);
		this.setRegistryName(new ResourceLocation("ab", name));
	    this.setUnlocalizedName(name);
	}
	
	@Nonnull
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack stack) {
		return super.getUnlocalizedNameInefficiently(stack).replaceAll("item\\.", "item.ab:");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
