package ab.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import vazkii.botania.api.item.IRelic;

public class AchievementAB extends Achievement {
	
	public AchievementAB(String name, int x, int y, ItemStack icon, Achievement parent) {
		super("achievement.ab:" + name, "ab:" + name, x, y, icon, parent);
		AdvancedBotanyAPI.achievements.add(this);
		registerStat();
		if(icon.getItem() instanceof IRelic)
			((IRelic)icon.getItem()).setBindAchievement(this);
	}
}
