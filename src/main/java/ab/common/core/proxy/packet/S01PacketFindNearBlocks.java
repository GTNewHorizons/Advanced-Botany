package ab.common.core.proxy.packet;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

import ab.common.item.relic.ItemSphereNavigation;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class S01PacketFindNearBlocks implements IMessage, IMessageHandler<S01PacketFindNearBlocks, IMessage> {

    private int blockID;
    private int meta;

    public S01PacketFindNearBlocks() {};

    public S01PacketFindNearBlocks(Block block, int meta) {
        this.blockID = Block.getIdFromBlock(block);
        this.meta = meta;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.blockID);
        buf.writeInt(this.meta);
    }

    public void fromBytes(ByteBuf buf) {
        this.blockID = buf.readInt();
        this.meta = buf.readInt();
    }

    @SideOnly(Side.CLIENT)
    public IMessage onMessage(S01PacketFindNearBlocks message, MessageContext ctx) {
        ItemSphereNavigation.findBlocks(
                Minecraft.getMinecraft().theWorld,
                Block.getBlockById(message.blockID),
                message.meta,
                Minecraft.getMinecraft().thePlayer);
        return null;
    }
}
