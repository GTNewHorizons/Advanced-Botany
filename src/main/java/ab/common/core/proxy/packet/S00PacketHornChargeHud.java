package ab.common.core.proxy.packet;

import net.minecraft.item.ItemStack;

import ab.client.core.handler.ItemsRemainingRender;
import ab.common.lib.register.ItemListAB;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class S00PacketHornChargeHud implements IMessage, IMessageHandler<S00PacketHornChargeHud, IMessage> {

    private static final ItemStack horn = new ItemStack(ItemListAB.itemHornPlenty);
    private short chargeLoot;

    public S00PacketHornChargeHud() {};

    public S00PacketHornChargeHud(short chargeLoot) {
        this.chargeLoot = chargeLoot;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeShort(chargeLoot);
    }

    public void fromBytes(ByteBuf buf) {
        this.chargeLoot = buf.readShort();
    }

    @SideOnly(Side.CLIENT)
    public IMessage onMessage(S00PacketHornChargeHud message, MessageContext ctx) {
        ItemsRemainingRender.set(horn, "" + message.chargeLoot);
        return null;
    }
}
