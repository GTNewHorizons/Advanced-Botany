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
import thaumcraft.common.Thaumcraft;
import vazkii.botania.common.core.helper.Vector3;

public class LightningMessage implements IMessage {
    
    private double x;
    private double y;
    private double z;
    
    private double destx;
    private double desty;
    private double destz;
    
    private int duration;
    private float curve;
    private int speed;
    private int type;
    private float width;

    public LightningMessage() { }

    public LightningMessage(double x, double y, double z, double destx, double desty, double destz, int duration, float curve, int speed, int type, float width) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.destx = destx;
        this.desty = desty;
        this.destz = destz;
        
        this.duration = duration;
        this.curve = curve;
        this.speed = speed;
        this.type = type;
        this.width = width;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        
        this.destx = buf.readDouble();
        this.desty = buf.readDouble();
        this.destz = buf.readDouble();
        
        this.duration = buf.readInt();
        this.curve = buf.readFloat();
        this.speed = buf.readInt();
        this.type = buf.readInt();
        this.width = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        
        buf.writeDouble(this.destx);
        buf.writeDouble(this.desty);
        buf.writeDouble(this.destz);
        
        buf.writeInt(this.duration);
        buf.writeFloat(this.curve);
        buf.writeInt(this.speed);
        buf.writeInt(this.type);
        buf.writeFloat(this.width);
    }

    public static class Handler implements IMessageHandler<LightningMessage, IMessage> {
        
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(LightningMessage message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            
            Main.proxy.lightning(player.worldObj, message.x, message.y, message.z, message.destx, message.desty, message.destz, message.duration, message.curve, message.speed, message.type, message.width);
            return null;
        }
    }
    
}
