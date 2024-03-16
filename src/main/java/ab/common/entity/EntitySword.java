package ab.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;

public class EntitySword extends EntityThrowable {

    public EntitySword(World world) {
        super(world);
    }

    public EntitySword(World world, EntityPlayer e) {
        super(world, e);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(27, Float.valueOf(0.0f));
        this.dataWatcher.addObject(28, "");
        this.dataWatcher.setObjectWatched(27);
        this.dataWatcher.setObjectWatched(28);
    }

    public void onUpdate() {
        super.onUpdate();
        update();
        if (ticksExisted < 20) {
            this.motionX *= 1.115f;
            this.motionY *= 1.115f;
            this.motionZ *= 1.115f;
        } else if (ticksExisted > 160) setDead();
        if (worldObj.isRemote) {
            for (int i = 0; i < 12; i++) {
                float r = worldObj.rand.nextBoolean() ? (225.0f / 255.0f) : (101.0f / 255.0f);
                float g = worldObj.rand.nextBoolean() ? (67.0f / 255.0f) : (209.0f / 255.0f);
                float b = worldObj.rand.nextBoolean() ? (240.0f / 255.0f) : (225.0f / 255.0f);
                Botania.proxy.sparkleFX(
                        worldObj,
                        posX + ((Math.random() - 0.5D)) * 0.25f,
                        posY + ((Math.random() - 0.5D) * 0.25f),
                        (posZ + (Math.random() - 0.5D) * 0.25f),
                        r + (float) (Math.random() / 4 - 0.125D),
                        g + (float) (Math.random() / 4 - 0.125D),
                        b + (float) (Math.random() / 4 - 0.125D),
                        1.6F * (float) (Math.random() - 0.5D),
                        2);
            }
        }
    }

    public void update() {
        String attacker = getAttacker();
        AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(posX, posY, posZ, lastTickPosX, lastTickPosY, lastTickPosZ)
                .expand(1.0D, 1.0D, 1.0D);
        List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
        for (EntityLivingBase living : entities) {
            if (living instanceof EntityPlayer && (((EntityPlayer) living).getCommandSenderName().equals(attacker)
                    || (MinecraftServer.getServer() != null && !MinecraftServer.getServer().isPVPEnabled())))
                continue;
            if (living.hurtTime == 0) {
                float damage = getDamage();
                if (!worldObj.isRemote) {
                    EntityPlayer player = living.worldObj.getPlayerEntityByName(attacker);
                    living.attackEntityFrom(
                            (player == null) ? DamageSource.magic : DamageSource.causePlayerDamage(player),
                            damage);
                    setDead();
                    break;
                }
            }
        }
    }

    public String getAttacker() {
        return this.dataWatcher.getWatchableObjectString(28);
    }

    public void setAttacker(String str) {
        this.dataWatcher.updateObject(28, str);
    }

    public float getDamage() {
        return this.dataWatcher.getWatchableObjectFloat(27);
    }

    public void setDamage(float damage) {
        this.dataWatcher.updateObject(27, damage);
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float help, float me, int please) {}

    protected void onImpact(MovingObjectPosition mov) {
        Block block = this.worldObj.getBlock(mov.blockX, mov.blockY, mov.blockZ);
        if (block != null && mov.entityHit == null) {
            if (block instanceof net.minecraft.block.BlockBush || block instanceof net.minecraft.block.BlockLeaves)
                return;
            setDead();
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbtt) {
        super.readEntityFromNBT(nbtt);
        ticksExisted = nbtt.getInteger("ticks");
        setDamage(nbtt.getInteger("disDamage"));
        setAttacker(nbtt.getString("attacker"));
    }

    public void writeEntityToNBT(NBTTagCompound nbtt) {
        super.writeEntityToNBT(nbtt);
        nbtt.setInteger("ticks", ticksExisted);
        nbtt.setFloat("disDamage", getDamage());
        nbtt.setString("attacker", getAttacker());
    }

    protected float getGravityVelocity() {
        return 0.0F;
    }
}
