package ab.common.core.handler;

import ab.common.core.proxy.packet.S00PacketHornChargeHud;
import ab.common.core.proxy.packet.S01PacketFindNearBlocks;
import ab.common.core.proxy.packet.S02PacketSpaceBladeDash;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;

public class NetworkHandler {
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("advancedBotanyChanel");

	public static void registerPackets() {
		int id = 0;
		NETWORK.registerMessage(S00PacketHornChargeHud.class, S00PacketHornChargeHud.class, id++, Side.CLIENT);
		NETWORK.registerMessage(S01PacketFindNearBlocks.class, S01PacketFindNearBlocks.class, id++, Side.CLIENT);
		NETWORK.registerMessage(S02PacketSpaceBladeDash.class, S02PacketSpaceBladeDash.class, id++, Side.CLIENT);
	}
	
	public static void sendPacketToSpaceDash(EntityPlayerMP playerMP) {
		NETWORK.sendTo(new S02PacketSpaceBladeDash(), playerMP);
	}
	
	public static void sendPacketToFindBlocks(EntityPlayerMP playerMP, Block block, int meta) {
		NETWORK.sendTo(new S01PacketFindNearBlocks(block, meta), playerMP);
	}
	
	public static void sendPacketToHornHud(EntityPlayerMP playerMP, short chargeLoot) {
		NETWORK.sendTo(new S00PacketHornChargeHud(chargeLoot), playerMP);
	}
}