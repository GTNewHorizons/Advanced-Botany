package pulxes.ab.common.core.handler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.core.network.S02PacketSpaceBladeDash;
import pulxes.ab.common.core.network.S03PacketSpaceBladeBurst;

public class NetworkHandler {

	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(AdvancedBotany.MODID);
	
	public static void registerPackets() {
		int id = 0;
		NETWORK.registerMessage(S02PacketSpaceBladeDash.class, S02PacketSpaceBladeDash.class, id++, Side.CLIENT);
		NETWORK.registerMessage(S03PacketSpaceBladeBurst.class, S03PacketSpaceBladeBurst.class, id++, Side.SERVER);
	}
	
	public static void sendPacketToSpaceDash(EntityPlayerMP playerMP) {
		NETWORK.sendTo(new S02PacketSpaceBladeDash(), playerMP);
	}
	
	public static void sendPacketToSpaceBurst() {
		NETWORK.sendToServer(new S03PacketSpaceBladeBurst());
	}
}
