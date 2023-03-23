package com.integral.forgottenrelics.handlers;

import net.minecraft.entity.player.EntityPlayer;

import thaumcraft.common.Thaumcraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class ArcLightningMessage implements IMessage {

    private double x;
    private double y;
    private double z;

    private double destx;
    private double desty;
    private double destz;

    private float r;
    private float g;
    private float b;
    private float h;

    public ArcLightningMessage() {}

    public ArcLightningMessage(double x, double y, double z, double destx, double desty, double destz, float r, float g,
            float b, float h) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.destx = destx;
        this.desty = desty;
        this.destz = destz;

        this.r = r;
        this.g = g;
        this.b = b;

        this.h = h;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();

        this.destx = buf.readDouble();
        this.desty = buf.readDouble();
        this.destz = buf.readDouble();

        this.r = buf.readFloat();
        this.g = buf.readFloat();
        this.b = buf.readFloat();

        this.h = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);

        buf.writeDouble(this.destx);
        buf.writeDouble(this.desty);
        buf.writeDouble(this.destz);

        buf.writeFloat(this.r);
        buf.writeFloat(this.g);
        buf.writeFloat(this.b);

        buf.writeFloat(this.h);
    }

    public static class Handler implements IMessageHandler<ArcLightningMessage, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(ArcLightningMessage message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

            Thaumcraft.proxy.arcLightning(
                    player.worldObj,
                    message.x,
                    message.y,
                    message.z,
                    message.destx,
                    message.desty,
                    message.destz,
                    message.r,
                    message.g,
                    message.b,
                    message.h);
            return null;
        }
    }

}
