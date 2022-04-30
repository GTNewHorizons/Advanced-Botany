package pulxes.ab.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pulxes.ab.common.item.equipment.ItemSpaceBlade;

public class S02PacketSpaceBladeDash implements IMessage, IMessageHandler<S02PacketSpaceBladeDash, IMessage> {

	public void fromBytes(ByteBuf buf) {}

	public void toBytes(ByteBuf buf) {}

	@SideOnly(Side.CLIENT)
	public IMessage onMessage(S02PacketSpaceBladeDash message, MessageContext ctx) {
		ItemSpaceBlade.playerSpaceDash(Minecraft.getMinecraft().player);
		return null;
	}
}