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

public class EntityNebulaBlaze extends EntityThrowable {

    public EntityNebulaBlaze(World world) {
        super(world);
    }

    public EntityNebulaBlaze(World world, EntityPlayer e) {
        super(world, e);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(28, "");
        this.dataWatcher.setObjectWatched(28);
    }

    public void onUpdate() {
        if (this.ticksExisted >= 240) setDead();
        double posX = this.posX;
        double posY = this.posY;
        double posZ = this.posZ;
        update();
        super.onUpdate();
        AxisAlignedBB axis = AxisAlignedBB
                .getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1, this.posY + 1, this.posZ + 1)
                .expand(3.75f, 3.75f, 3.75f);
        List<EntityLivingBase> livs = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
        for (EntityLivingBase liv : livs) {
            if (liv instanceof EntityPlayer) continue;
            else if (liv.getHealth() <= 0.0f) continue;
            double d1 = (this.posX - liv.posX);
            double d2 = (this.posY - liv.posY - liv.getEyeHeight());
            double d3 = (this.posZ - liv.posZ);
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            if (d4 < 3.1D) d4 = 3.1D;
            double d5 = 1.0D - d4;
            this.setPosition(posX, posY, posZ);
            this.motionX = d1 / d4 * d5 * 0.325D + (liv.motionX * 0.85f);
            this.motionY = d2 / d4 * d5 * 0.325D + (liv.motionY * 0.85f);
            this.motionZ = d3 / d4 * d5 * 0.325D + (liv.motionZ * 0.85f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            break;
        }
        if (this.worldObj.isRemote) for (int i = 0; i < 12; i++) Botania.proxy.sparkleFX(
                worldObj,
                posX + ((Math.random() - 0.5D)) * 0.15f,
                posY + ((Math.random() - 0.5D) * 0.15f),
                (posZ + (Math.random() - 0.5D) * 0.15f),
                1.0f - (float) (Math.random() / 5.0f),
                0.0f + (float) (Math.random() / 5.0f),
                1.0f - (float) (Math.random() / 5.0f),
                1.2F * (float) (Math.random() - 0.5D),
                3);
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
                float damage = 18.0f;
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

    public void readEntityFromNBT(NBTTagCompound nbtt) {
        super.readEntityFromNBT(nbtt);
        ticksExisted = nbtt.getInteger("ticks");
        setAttacker(nbtt.getString("attacker"));
    }

    public void writeEntityToNBT(NBTTagCompound nbtt) {
        super.writeEntityToNBT(nbtt);
        nbtt.setInteger("ticks", ticksExisted);
        nbtt.setString("attacker", getAttacker());
    }

    public String getAttacker() {
        return this.dataWatcher.getWatchableObjectString(28);
    }

    public void setAttacker(String str) {
        this.dataWatcher.updateObject(28, str);
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float help, float me, int please) {}

    protected float getGravityVelocity() {
        return 0.0F;
    }

    protected void onImpact(MovingObjectPosition mov) {
        Block block = this.worldObj.getBlock(mov.blockX, mov.blockY, mov.blockZ);
        if (block != null && mov.entityHit == null) {
            if (block instanceof net.minecraft.block.BlockBush || block instanceof net.minecraft.block.BlockLeaves)
                return;
            setDead();
        }
    }
}
