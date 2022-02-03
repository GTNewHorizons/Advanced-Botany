package ab.common.item.equipment;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

public class ItemTerraHoe extends ItemHoe implements IManaUsingItem {

	public ItemTerraHoe() {
		super(BotaniaAPI.terrasteelToolMaterial);
		this.setCreativeTab(AdvancedBotany.tabAB);
		this.setUnlocalizedName("terraHoe");
		this.setTextureName("ab:terraHoe");
	}
	
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
			return false;
		} else {
			UseHoeEvent event = new UseHoeEvent(p_77648_2_, p_77648_1_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
			if (MinecraftForge.EVENT_BUS.post(event)) {
				return false;
			}
			if (event.getResult() == Result.ALLOW) {
				p_77648_1_.damageItem(1, p_77648_2_);
				return true;
			}
			Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
			if (p_77648_7_ != 0 && p_77648_3_.getBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_).isAir(p_77648_3_, p_77648_4_, p_77648_5_ + 1, p_77648_6_) && (block == Blocks.grass || block == Blocks.dirt)) {
				Block block1 = BlockListAB.blockTerraFarmland;
				p_77648_3_.playSoundEffect((double)((float)p_77648_4_ + 0.5F), (double)((float)p_77648_5_ + 0.5F), (double)((float)p_77648_6_ + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
				if (p_77648_3_.isRemote) {
			        float velMul = 0.025F;
			        for (int i = 0; i < 48; i++) {
				        double x = (Math.random() - 0.5D) * 3.0D;
				        double y = Math.random() - 0.5D + 1.0D;
				        double z = (Math.random() - 0.5D) * 3.0D;
				        Botania.proxy.wispFX(p_77648_3_, p_77648_4_ + 0.5D + x, p_77648_5_ + 0.5D + y, p_77648_6_ + 0.5D + z, 0.0f, 0.4f, 0.0f, (float)Math.random() * 0.15F + 0.15F, (float)-x * velMul, (float)-y * velMul, (float)-z * velMul);
			        }
					return true;
				} else {
					p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, block1);
					p_77648_1_.damageItem(1, p_77648_2_);
					return true;
				}			
			} 
			else if(p_77648_2_.isSneaking() && p_77648_7_ != 0 && p_77648_3_.getBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_).isAir(p_77648_3_, p_77648_4_, p_77648_5_ + 1, p_77648_6_) && (block == ModBlocks.enchantedSoil)) {
				if (p_77648_3_.isRemote)
					return true;
				p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Blocks.dirt);
				p_77648_1_.damageItem(1, p_77648_2_);
				EntityItem entity = new EntityItem(p_77648_3_, p_77648_4_ + 0.5f, p_77648_5_ + 1, p_77648_6_ + 0.5f, new ItemStack(ModItems.overgrowthSeed));
				p_77648_3_.spawnEntityInWorld(entity);
				return true;
			} else {
				return false;
			}		
		}
	}

	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if (!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer)player, 1760, true))
			stack.setItemDamage(stack.getItemDamage() - 1); 
	}
	
	public boolean usesMana(ItemStack stack) {
		return true;
	}
}
