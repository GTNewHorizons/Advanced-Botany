package pulxes.ab.common.lib.register;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import pulxes.ab.AdvancedBotany;

@EventBusSubscriber(modid = AdvancedBotany.MODID)
public class SoundListAB {
	
	public static final SoundEvent nebulaRod = makeSoundEvent("nebularod");
	public static final SoundEvent bladeSpace = makeSoundEvent("bladespace");
	
	private static SoundEvent makeSoundEvent(String name) {
	    ResourceLocation loc = new ResourceLocation(AdvancedBotany.MODID, name);
	    return (new SoundEvent(loc)).setRegistryName(loc);
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		 IForgeRegistry<SoundEvent> r = evt.getRegistry();
		 r.register(nebulaRod);
		 r.register(bladeSpace);
	}
}
