package ab.common.item.relic;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class ItemSpiritJar extends ItemModRelic {

	public ItemSpiritJar() {
		super("spiritJar");
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int pos, boolean equipped) {
		if(world.isRemote && world.getTotalWorldTime() % 32L == 0L) {
			int posX = (int)entity.posX;
			int posY = (int)entity.posY;
			int posZ = (int)entity.posZ;
			
		}
	}
}