package ab.common.entity;

import java.awt.Color;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import ab.client.core.ClientHelper;
import ab.common.core.CommonHelper;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.lib.LibObfuscation;

public class EntityManaVine extends EntityThrowable {

    public EntityManaVine(World world) {
        super(world);
    }

    public EntityManaVine(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(28, "");
        this.dataWatcher.setObjectWatched(28);
    }

    public String getAttacker() {
        return this.dataWatcher.getWatchableObjectString(28);
    }

    public void setAttacker(String str) {
        this.dataWatcher.updateObject(28, str);
    }

    public void onUpdate() {
        super.onUpdate();
        ticksExisted++;
        if (ticksExisted >= 240) setDead();
        if (this.worldObj.isRemote) {
            float m = 0.02F;
            float f1 = 6.0f;
            for (int i = 0; i < 4; i++) {
                double posX = this.posX + (Math.random() / f1 - (0.5f / f1));
                double posY = this.posY + (Math.random() / f1 - (0.5f / f1));
                double posZ = this.posZ + (Math.random() / f1 - (0.5f / f1));
                float mx = (float) (Math.random() - 0.5D) * m;
                float my = (float) (Math.random() - 0.5D) * m;
                float mz = (float) (Math.random() - 0.5D) * m;
                Color color = ClientHelper.getCorporeaRuneColor((int) posX, (int) posY, (int) posZ, 3);
                Botania.proxy.wispFX(
                        worldObj,
                        posX,
                        posY,
                        posZ,
                        color.getRed() / 255.0f,
                        color.getGreen() / 255.0f,
                        color.getBlue() / 255.0f,
                        (float) (0.15f + (Math.random() * 0.12f)),
                        mx,
                        my,
                        mz,
                        0.7f);
            }
        }
    }

    protected void onImpact(MovingObjectPosition pos) {
        EntityPlayer player = worldObj.getPlayerEntityByName(getAttacker());
        if (pos != null && pos.entityHit == null && player != null) {
            World world = this.worldObj;
            int x = pos.blockX;
            int y = pos.blockY;
            int z = pos.blockZ;
            List<EntityLivingBase> list = (List<EntityLivingBase>) (world.getEntitiesWithinAABB(
                    EntityLivingBase.class,
                    AxisAlignedBB.getBoundingBox(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10)));
            for (EntityLivingBase target : list) {
                if (target instanceof EntityAnimal
                        && target.attackEntityFrom(DamageSource.causePlayerDamage(player), 0.0f)) {
                    EntityAnimal animal = (EntityAnimal) target;
                    ReflectionHelper.setPrivateValue(EntityAnimal.class, animal, 1200, LibObfuscation.IN_LOVE);
                    animal.setTarget(null);
                    this.worldObj.setEntityState(animal, (byte) 18);
                }
            }
            for (int k = 0; k < 7; k++) for (int k1 = 0; k1 < 7; k1++) for (int k2 = 0; k2 < 7; k2++) {
                int xCoord = x + k - 3;
                int yCoord = y + k1 - 1;
                int zCoord = z + k2 - 3;
                Block block = this.worldObj.getBlock(xCoord, yCoord, zCoord);
                if (block instanceof IGrowable && !(block instanceof BlockGrass)) {
                    CommonHelper.fertilizer(world, block, xCoord, yCoord, zCoord, 12, player);
                    if (ConfigHandler.blockBreakParticles) {
                        worldObj.playAuxSFX(2005, xCoord, yCoord, zCoord, 6 + this.worldObj.rand.nextInt(4));
                        worldObj.playSoundEffect(
                                x,
                                y,
                                z,
                                "botania:agricarnation",
                                0.01F,
                                0.5F + (float) Math.random() * 0.5F);
                    }
                } else if (!world.isRemote
                        && BlockListAB.blockFreyrLiana.canBlockStay(world, xCoord, yCoord - 1, zCoord)) {
                            Vec3 vec = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
                            int distant = (int) vec.squareDistanceTo(Vec3.createVectorHelper(x, y, z));
                            yCoord--;
                            if (this.worldObj.rand.nextInt(distant + 1) == 0) while (yCoord > 0) {
                                block = worldObj.getBlock(xCoord, yCoord, zCoord);
                                if (block.isAir(worldObj, xCoord, yCoord, zCoord)) {
                                    if (world.rand.nextInt(4) < 3) CommonHelper.setBlock(
                                            world,
                                            BlockListAB.blockFreyrLiana,
                                            0,
                                            xCoord,
                                            yCoord,
                                            zCoord,
                                            player,
                                            false);
                                    else CommonHelper.setBlock(
                                            world,
                                            BlockListAB.blockLuminousFreyrLiana,
                                            0,
                                            xCoord,
                                            yCoord,
                                            zCoord,
                                            player,
                                            false);
                                    worldObj.playAuxSFX(
                                            2001,
                                            xCoord,
                                            yCoord,
                                            zCoord,
                                            Block.getIdFromBlock(BlockListAB.blockFreyrLiana));
                                    yCoord--;
                                } else break;
                            }
                        }
            }
            this.setDead();
        }
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_,
            float p_70056_8_, int p_70056_9_) {}

    protected float getGravityVelocity() {
        return 0.0f;
    }

    public void setDead() {
        if (this.worldObj.isRemote) {
            float m = 0.175F;
            for (int i = 0; i < 32; i++) {
                float mx = (float) (Math.random() - 0.5D) * m;
                float my = (float) (Math.random() - 0.5D) * m;
                float mz = (float) (Math.random() - 0.5D) * m;
                Color color = ClientHelper.getCorporeaRuneColor((int) posX, (int) posY, (int) posZ, 3);
                Botania.proxy.wispFX(
                        worldObj,
                        posX,
                        posY,
                        posZ,
                        color.getRed() / 255.0f,
                        color.getGreen() / 255.0f,
                        color.getBlue() / 255.0f,
                        (float) (0.2f + (Math.random() * 0.12f)),
                        mx,
                        my,
                        mz,
                        2.0f);
            }
        }
        super.setDead();
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
}
