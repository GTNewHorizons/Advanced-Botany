package com.integral.forgottenrelics.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.bolt.FXLightningBolt;
import thaumcraft.client.renderers.entity.RenderEldritchOrb;
import thaumcraft.client.renderers.entity.RenderPrimalOrb;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.entities.EntityBabylonWeaponSS;
import com.integral.forgottenrelics.entities.EntityChaoticOrb;
import com.integral.forgottenrelics.entities.EntityCrimsonOrb;
import com.integral.forgottenrelics.entities.EntityDarkMatterOrb;
import com.integral.forgottenrelics.entities.EntityRageousMissile;
import com.integral.forgottenrelics.entities.FXBurst;
import com.integral.forgottenrelics.entities.FXWisp;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers(Main ins) {

    }

    public void registerDisplayInformation() {
        RenderingRegistry
                .registerEntityRenderingHandler((Class) EntityRageousMissile.class, (Render) new RenderPrimalOrb());
        RenderingRegistry
                .registerEntityRenderingHandler((Class) EntityChaoticOrb.class, (Render) new RenderPrimalOrb());
        RenderingRegistry
                .registerEntityRenderingHandler((Class) EntityCrimsonOrb.class, (Render) new RenderCrimsonOrb());
        RenderingRegistry
                .registerEntityRenderingHandler((Class) EntityDarkMatterOrb.class, (Render) new RenderEldritchOrb());
        RenderingRegistry.registerEntityRenderingHandler(
                (Class) EntityBabylonWeaponSS.class,
                (Render) new RenderBabylonWeaponSS());
    }

    @Override
    public void lunarBurst(World world, double x, double y, double z, float size) {
        final FXBurst ef = new FXBurst(world, x, y, z, size);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect((EntityFX) ef);
    }

    @Override
    public void lightning(World world, double sx, double sy, double sz, double ex, double ey, double ez, int dur,
            float curve, int speed, int type, float width) {
        FXLightningBolt bolt = new FXLightningBolt(
                world,
                sx,
                sy,
                sz,
                ex,
                ey,
                ez,
                world.rand.nextLong(),
                dur,
                curve,
                speed);

        bolt.defaultFractal();
        bolt.setType(type);
        bolt.setWidth(width);
        bolt.finalizeBolt();
    }

    public void wispFX4(final World worldObj, final double posX, final double posY, final double posZ,
            final Entity target, final int type, final boolean shrink, final float gravity) {
        final FXWisp ef = new FXWisp(worldObj, posX, posY, posZ, target, type);
        ef.setGravity(gravity);
        ef.shrink = shrink;
        ParticleEngine.instance.addEffect(worldObj, (EntityFX) ef);
    }

    @Override
    public void spawnSuperParticle(World world, String particleType, double x, double y, double z, double velX,
            double velY, double velZ, float particleSize, double renderDistance) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null && mc.theWorld == world) {
            double distX = mc.renderViewEntity.posX - x;
            double distY = mc.renderViewEntity.posY - y;
            double distZ = mc.renderViewEntity.posZ - z;

            EntityFX particle = null;

            double maxDist = renderDistance; // normally 16.0D

            // check for particle max distance
            if (distX * distX + distY * distY + distZ * distZ < maxDist * maxDist) {

                if (particleType.equals("portalstuff")) {
                    particle = new EntityPortalFX(world, x, y, z, velX, velY, velZ);
                }

                // if we made a partcle, go ahead and add it
                if (particle != null) {
                    particle.prevPosX = particle.posX;
                    particle.prevPosY = particle.posY;
                    particle.prevPosZ = particle.posZ;

                    // we keep having a non-threadsafe crash adding particles directly here, so let's pass them to a
                    // buffer
                    // clientTicker.addParticle(particle);
                    mc.effectRenderer.addEffect(particle); // maybe it's fixed?
                }
            }
        }
    }

}
