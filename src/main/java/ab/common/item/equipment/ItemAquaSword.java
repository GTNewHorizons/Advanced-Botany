package ab.common.item.equipment;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import ab.client.core.handler.PlayerItemUsingSound.ClientSoundHandler;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;

public class ItemAquaSword extends ItemSword {

    public ItemAquaSword() {
        super(AdvancedBotanyAPI.mithrilToolMaterial);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setUnlocalizedName("itemAquaSword");
        this.setTextureName("ab:itemAquaSword");
    }

    public boolean isItemTool(ItemStack par1ItemStack) {
        return true;
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(
                entity.posX,
                entity.posY,
                entity.posZ,
                entity.lastTickPosX,
                entity.lastTickPosY,
                entity.lastTickPosZ).expand(1.7f, 1.7f, 1.7f);
        List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
        double posX = entity.posX;
        double posY = entity.posY + entity.getEyeHeight();
        double posZ = entity.posZ;
        if (!player.worldObj.isRemote) {
            boolean hasWaterSplash = false;
            for (EntityLivingBase living : entities) {
                if (living instanceof EntityPlayer && (((EntityPlayer) living).getCommandSenderName()
                        .equals(player.getCommandSenderName())
                        || (MinecraftServer.getServer() != null && !MinecraftServer.getServer().isPVPEnabled())))
                    continue;
                else if (ManaItemHandler.requestManaExactForTool(stack, player, 10, false) && living.attackEntityFrom(
                        DamageSource.causePlayerDamage(player),
                        AdvancedBotanyAPI.mithrilToolMaterial.getDamageVsEntity() / 2.0f)) {
                            ManaItemHandler.requestManaExactForTool(stack, player, 10, true);
                            if (!hasWaterSplash) hasWaterSplash = true;
                            Vec3 vec3 = player.getLook(1.0F).normalize();
                            living.motionX += vec3.xCoord * 1.35f;
                            living.motionY += (vec3.yCoord / 1.8f);
                            living.motionZ += vec3.zCoord * 1.35f;
                        }
            }
            if (hasWaterSplash) player.worldObj.playSoundAtEntity(player, "ab:aquaSword", 1.2F, 1.2F);
        } else {
            if (ManaItemHandler.requestManaExactForTool(stack, player, 10, false)) {
                for (int i = 0; i < 24; i++) {
                    float mtX = (float) ((Math.random() - 0.5f) * 0.12f);
                    float mtY = (float) ((Math.random() - 0.5f) * 0.12f);
                    float mtZ = (float) ((Math.random() - 0.5f) * 0.12f);
                    Botania.proxy.wispFX(
                            player.worldObj,
                            posX,
                            posY,
                            posZ,
                            0.0f,
                            (float) (Math.random() * 0.35f),
                            1.0f - (float) (Math.random() * 0.4f),
                            0.17f + (float) (Math.random() * 0.3f),
                            mtX,
                            mtY,
                            mtZ,
                            0.512f);
                }
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer p, int time) {
        if (p.worldObj.isRemote) {
            if (!ManaItemHandler.requestManaExactForTool(stack, p, 15, false)) return;
            ClientSoundHandler.playSound(p, "liquid.water", 0.8F, 1.7F, 13, false);
            time = (this.getMaxItemUseDuration(stack) - time) % 120 + 1;
            if (time > 50) time = 120 - time;
            float phase = time / 120.0f;
            int wispCount = 8;
            double tickIncrement = 360.0D / wispCount;
            int speed = 10;
            double wticks = (time * speed) - tickIncrement;
            double r = Math.sin(1.4f);
            for (int i = 0; i < wispCount; i++) {
                double posX = p.posX + Math.sin(wticks * Math.PI / 180.0D) * r;
                double posY = p.posY + (wticks * 0.001f) + 0.1f;
                double posZ = p.posZ + Math.cos(wticks * Math.PI / 180.0D) * r;
                Botania.proxy.wispFX(
                        p.worldObj,
                        posX,
                        posY,
                        posZ,
                        0.0f,
                        (float) (Math.random() * 0.35f),
                        1.0f - (float) (Math.random() * 0.4f),
                        0.3F,
                        0.0f,
                        -0.1f + (float) (Math.random() * 0.05f),
                        0.0f,
                        0.7F);
                wticks += tickIncrement;
            }
        } else {
            AxisAlignedBB axis = AxisAlignedBB
                    .getBoundingBox(p.posX, p.posY, p.posZ, p.lastTickPosX, p.lastTickPosY, p.lastTickPosZ)
                    .expand(2.75f, 2.75f, 2.75f);
            List<EntityLivingBase> entities = p.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
            for (EntityLivingBase living : entities) {
                if (living instanceof EntityPlayer && (((EntityPlayer) living).getCommandSenderName()
                        .equals(p.getCommandSenderName())
                        || (MinecraftServer.getServer() != null && !MinecraftServer.getServer().isPVPEnabled())))
                    continue;
                double dist = living.getDistance(p.posX, p.posY, p.posZ) / 2.5f;
                if (ManaItemHandler.requestManaExactForTool(stack, p, 15, false)
                        && living.attackEntityFrom(DamageSource.causePlayerDamage(p), 1.0f)) {
                    ManaItemHandler.requestManaExactForTool(stack, p, 15, true);
                    if (dist <= 1.0D) {
                        double d5 = living.posX - p.posX;
                        double d7 = living.posZ - p.posZ;
                        double d9 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7);
                        if (d9 != 0.0D) {
                            d5 /= d9;
                            d7 /= d9;
                            living.motionX += d5 * 1.2f;
                            living.motionZ += d7 * 1.2f;
                        }
                    }
                }
            }
        }
    }
}
