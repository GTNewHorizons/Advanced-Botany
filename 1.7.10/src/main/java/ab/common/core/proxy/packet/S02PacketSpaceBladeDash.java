package ab.common.core.proxy.packet;

import ab.common.item.equipment.ItemSpaceBlade;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class S02PacketSpaceBladeDash implements IMessage, IMessageHandler<S02PacketSpaceBladeDash, IMessage> {

	public void fromBytes(ByteBuf buf) {}

	public void toBytes(ByteBuf buf) {}

	@SideOnly(Side.CLIENT)
	public IMessage onMessage(S02PacketSpaceBladeDash message, MessageContext ctx) {
		ItemSpaceBlade.onPlayerSpaceDash(Minecraft.getMinecraft().thePlayer);
		return null;
	}
}