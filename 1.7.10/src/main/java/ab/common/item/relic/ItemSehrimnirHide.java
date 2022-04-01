package ab.common.item.relic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ab.common.core.handler.ConfigABHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class ItemSehrimnirHide extends ItemModRelic {
	
	public static final String[] dropFewItems = new String[] { "dropFewItems", "func_70628_a" };
	
	public ItemSehrimnirHide() {
		super("sehrimnirHide");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerAttack(LivingDropsEvent event) {
		if(event != null && event.source != null && event.source.getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.source.getSourceOfDamage();
			if(player.inventory.hasItem(this) && !player.worldObj.isRemote) {
				if(player.worldObj.rand.nextInt(100) < 20 && event.entityLiving != null && !(event.entityLiving instanceof IBossDisplayData) && isVallidEntity(event.entityLiving)) {
					try {
						EntityLivingBase liv = event.entityLiving;
						ReflectionHelper.findMethod(EntityLivingBase.class, liv, dropFewItems,  new Class[] {boolean.class, int.class} ).invoke(liv, new Object[] {true, 7});
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static boolean isVallidEntity(EntityLivingBase liv) {
		for(String entityName : ConfigABHandler.lockEntityListToHide)
			if(liv.getClass().getSimpleName().equals(entityName))
				return false;
		return true;
	}
}