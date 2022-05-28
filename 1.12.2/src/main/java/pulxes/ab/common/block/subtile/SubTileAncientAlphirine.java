package pulxes.ab.common.block.subtile;

import java.awt.Color;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pulxes.ab.api.AdvancedBotanyAPI;
import pulxes.ab.api.recipe.RecipeAncientAlphirine;
import pulxes.ab.common.entity.EntityAlphirinePortal;
import pulxes.ab.common.lib.register.lexicon.LexiconListAB;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.ItemLexicon;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockModSlab;

public class SubTileAncientAlphirine extends SubTileFunctional {
	
	private static final int MANA_REQUARE = 2500;

	public void onUpdate() {
		super.onUpdate();
		List<EntityItem> items = supertile.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.supertile.getPos().add(-1, -1, -1), this.supertile.getPos().add(2, 1, 2)));
		if(ticksExisted % 10 == 0 && mana >= MANA_REQUARE) {
			label666: for(EntityItem item : items) {
				if(item.isDead || !(item.getItem().getCount() >= 1))
					continue;
				else if(item.getItem().getItem() == ModItems.lexicon && !((ItemLexicon)item.getItem().getItem()).isKnowledgeUnlocked(item.getItem(), LexiconListAB.KNOWLEDGE_FORGOTTEN)) {
					ItemStack unlockedForgottenLexicon = item.getItem().copy();
					((ItemLexicon)item.getItem().getItem()).unlockKnowledge(unlockedForgottenLexicon, LexiconListAB.KNOWLEDGE_FORGOTTEN);
					doAlphirineCraft(item, -1, unlockedForgottenLexicon);
					break label666;
				}
				for(RecipeAncientAlphirine recipe : AdvancedBotanyAPI.alphirineRecipes) {
					if(recipe.matches(item.getItem())) {
						doAlphirineCraft(item, recipe.getChance(), recipe.getOutput().copy());
						break label666;
					}
				}
			}
		}
	}
	
	public void doAlphirineCraft(EntityItem item, int chance, ItemStack result) {
		if(!supertile.getWorld().isRemote) {
			if(chance == -1 || supertile.getWorld().rand.nextInt(110) <= chance) {
				spawnPortal(supertile.getWorld(), result, supertile.getPos().getX() + 0.5f, supertile.getPos().getY(), supertile.getPos().getZ() + 0.5f);
				mana -= MANA_REQUARE;
			} else
				mana -= MANA_REQUARE / 10;
			item.getItem().shrink(1);
			sync();
		} else {
			float m = 0.0375F;
			for(int i = 0; i < 16; i++) {
				float mx = (float)(Math.random() - 0.5D) * m;
				float my = (float)(Math.random() - 0.5D) * m;
				float mz = (float)(Math.random() - 0.5D) * m;
				Color color = new Color(Color.HSBtoRGB(0.1583F, (float)Math.random() * 0.5F + 0.5F, 1.0f));
				Botania.proxy.wispFX(item.posX, item.posY + 0.5f, item.posZ, (float)(color.getRed() / 255.0f), (float)(color.getGreen() / 255.0f), (float)(color.getBlue() / 255.0f), (float)(0.1f + (Math.random() * 0.12f)), mx, my, mz, 0.7f);	
			}
		}
	}
	
	private void spawnPortal(World world, ItemStack stack, float posX, float posY, float posZ) {
		EntityAlphirinePortal portal = new EntityAlphirinePortal(world);
		float itemX = (float)(posX + (Math.random() * 2.0f - 1.0f));
		float itemY = (float)(posY + 1.2f + (Math.random() - 0.5f));
		float itemZ = (float)(posZ + (Math.random() * 2.0f - 1.0f));
		portal.setPosition(itemX, itemY, itemZ);
		portal.setItemStack(stack);
		world.spawnEntity(portal);
	}
	
	public int getMaxMana() {
		return 80000;
	}
	  
	public int getColor() {
		return 0xd0bf58;
	}
}