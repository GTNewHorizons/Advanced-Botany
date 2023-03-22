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
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class LunarFlaresParticleMessage implements IMessage {
    
    private double x;
    private double y;
    private double z;
    
    private int quantity;

    public LunarFlaresParticleMessage() { }

    public LunarFlaresParticleMessage(double x, double y, double z, int quantity) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.quantity = quantity;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        
        this.quantity = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        
        buf.writeInt(this.quantity);
    }

    public static class Handler implements IMessageHandler<LunarFlaresParticleMessage, IMessage> {
        
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(LunarFlaresParticleMessage message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            
            for(int i = 0; i <= message.quantity; i++) {
    			float r = 0.0F;
    			float g = 0.8F + (float) Math.random() * 0.2F;
    			float b = 0.4F + (float) Math.random() * 0.6F;
    			float s = 0.3F + (float) Math.random() * 0.3F;
    			float m = 0.4F;
    			float xm = ((float) Math.random() - 0.5F) * m;
    			float ym = ((float) Math.random() - 0.5F) * m;
    			float zm = ((float) Math.random() - 0.5F) * m;
    			
    			Botania.proxy.setWispFXDistanceLimit(false);
    			Botania.proxy.wispFX(player.worldObj, message.x, message.y, message.z, r, g, b, s, xm, ym, zm, 1.0F);
    			Botania.proxy.setWispFXDistanceLimit(true);
    		}
            
            return null;
        }
    }
    
}
