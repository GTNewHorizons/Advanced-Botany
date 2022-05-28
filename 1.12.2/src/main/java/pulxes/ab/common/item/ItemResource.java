package pulxes.ab.common.item;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.lib.LibItemNames;
import vazkii.botania.api.recipe.IFlowerComponent;

public class ItemResource extends ItemMod implements IFlowerComponent {

	public ItemResource() {
		super("resourse");
		this.setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.getItemDamage() == 2;
    }
	
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if(isInCreativeTab(tab))
			for(int i = 0; i < LibItemNames.RESOURCE_NAMES.length; i++) 
				stacks.add(new ItemStack(this, 1, i));  
			
	}
	
	@Nonnull
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + LibItemNames.RESOURCE_NAMES[Math.min(LibItemNames.RESOURCE_NAMES.length, stack.getItemDamage())];
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		for(int i = 0; i < LibItemNames.RESOURCE_NAMES.length; i++)
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(AdvancedBotany.MODID + ":" + LibItemNames.RESOURCE_NAMES[i], "inventory"));  
	}

	public int getParticleColor(ItemStack stack) {
		return 10158080;
	}
	
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		if(player.getHeldItem(hand).getItemDamage() != 3)
			return super.onItemRightClick(world, player, hand);
		player.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World world, EntityLivingBase living) {
		if(stack.getItemDamage() != 3)
			return super.onItemUseFinish(stack, world, living);
		else if(!(living instanceof EntityPlayer))
			return super.onItemUseFinish(stack, world, living);
		else if(!world.isRemote)
			living.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 120, 3));
		EntityPlayer player = (EntityPlayer)living;
		ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Items.GLASS_BOTTLE));
		stack.shrink(1);
		return stack;
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
		return stack.getItemDamage() != 3 ? super.getMaxItemUseDuration(stack) : 24;
	}
	  
	public EnumAction getItemUseAction(ItemStack stack) {
		return stack.getItemDamage() != 3 ? super.getItemUseAction(stack) : EnumAction.DRINK;
	}
}