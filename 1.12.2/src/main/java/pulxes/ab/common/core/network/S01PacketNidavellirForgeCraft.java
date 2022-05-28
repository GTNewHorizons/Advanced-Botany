package pulxes.ab.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pulxes.ab.common.item.equipment.ItemSpaceBlade;

public class S01PacketNidavellirForgeCraft implements IMessage, IMessageHandler<S01PacketNidavellirForgeCraft, IMessage> {
	
	private int posX;
	private int posY;
	private int posZ;
	
	private int recipeID;
	private float craftProcess;

	public void fromBytes(ByteBuf buf) {}

	public void toBytes(ByteBuf buf) {}

	@SideOnly(Side.CLIENT)
	public IMessage onMessage(S01PacketNidavellirForgeCraft message, MessageContext ctx) {
		ItemSpaceBlade.playerSpaceDash(Minecraft.getMinecraft().player);
		ItemSpaceBlade.doParticleDash();
		return null;
	}
}