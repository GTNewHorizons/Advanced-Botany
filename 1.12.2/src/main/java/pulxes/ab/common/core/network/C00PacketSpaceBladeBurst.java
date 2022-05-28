package pulxes.ab.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pulxes.ab.common.item.equipment.ItemSpaceBlade;

public class C00PacketSpaceBladeBurst implements IMessage, IMessageHandler<C00PacketSpaceBladeBurst, IMessage> {

	public void fromBytes(ByteBuf buf) {}

	public void toBytes(ByteBuf buf) {}

	public IMessage onMessage(C00PacketSpaceBladeBurst message, MessageContext ctx) {
		EntityPlayerMP player = (ctx.getServerHandler()).player;
		player.mcServer.addScheduledTask(() -> ItemSpaceBlade.spaceBurstAttack(player));
		return null;
	}
}
