package com.integral.forgottenrelics.handlers;

import com.integral.forgottenrelics.Main;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import vazkii.botania.common.core.helper.Vector3;

public class PortalTraceMessage implements IMessage {
    
    private double x;
    private double y;
    private double z;
    
    private double xs;
    private double ys;
    private double zs;
    
    private double distance;

    public PortalTraceMessage() { }

    public PortalTraceMessage(double x, double y, double z, double xs, double ys, double zs, double distance) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.xs = xs;
        this.ys = ys;
        this.zs = zs;
        
        this.distance = distance;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        
        this.xs = buf.readDouble();
        this.ys = buf.readDouble();
        this.zs = buf.readDouble();
        
        this.distance = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        
        buf.writeDouble(this.xs);
        buf.writeDouble(this.ys);
        buf.writeDouble(this.zs);
        
        buf.writeDouble(this.distance);
    }

    public static class Handler implements IMessageHandler<PortalTraceMessage, IMessage> {
        
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PortalTraceMessage message, MessageContext ctx) {
        	
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            
            Vector3 primalVec = new Vector3(message.x, message.y, message.z);
            Vector3 finalVec = new Vector3(message.xs, message.ys, message.zs);
            Vector3 diffVec = finalVec.copy().sub(primalVec);
            Vector3 motionVec = diffVec.copy().multiply(1/message.distance);
            
            for (int counterS = (int) message.distance; counterS >= 0; counterS--) {
				
				for (int ISS = 0; ISS <= 4; ISS++)
				Main.proxy.spawnSuperParticle(player.worldObj, "portalstuff", primalVec.x, primalVec.y, primalVec.z, (Math.random() - 0.5D)*1, (Math.random() - 0.5D)*1, (Math.random() - 0.5D)*1, 1.0F, 64F);
				
				primalVec.add(motionVec);
			}
            
            return null; // no response in this case
        }
    }
    
}
