package ab.common.entity;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import ab.common.item.equipment.ItemSprawlRod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.ItemGrassSeeds;

public class EntitySeed extends EntityThrowable {

    public EntitySeed(World world) {
        super(world);
    }

    public EntitySeed(World world, EntityPlayer e) {
        super(world, e);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(27, new ItemStack(Blocks.air));
        this.dataWatcher.addObject(28, Integer.valueOf(1));
        this.dataWatcher.addObject(29, "");
        this.dataWatcher.setObjectWatched(27);
        this.dataWatcher.setObjectWatched(28);
        this.dataWatcher.setObjectWatched(29);
    }

    public void onUpdate() {
        super.onUpdate();
        if (ticksExisted >= 240) setDead();
        if (this.worldObj.isRemote) {
            float m = 0.02F;
            float f1 = 4.0f / (getRadius() / 20.0f);
            float size = 1.0f + (getRadius() / 12.0f);
            for (int i = 0; i < 5; i++) {
                double posX = this.posX + (Math.random() - 0.5f) / f1;
                double posY = this.posY + (Math.random() - 0.5f) / f1;
                double posZ = this.posZ + (Math.random() - 0.5f) / f1;
                float mx = (float) (Math.random() - 0.5D) * m;
                float my = (float) (Math.random() - 0.5D) * m;
                float mz = (float) (Math.random() - 0.5D) * m;
                Color color = ItemSprawlRod.getSeedColor(getSeed());
                Botania.proxy.wispFX(
                        worldObj,
                        posX,
                        posY,
                        posZ,
                        color.getRed() / 255.0f,
                        color.getGreen() / 255.0f,
                        color.getBlue() / 255.0f,
                        (float) (0.0625f * size + (Math.random() * 0.12f)),
                        mx,
                        my,
                        mz,
                        0.5f);
            }
        }
    }

    public int getRadius() {
        return this.dataWatcher.getWatchableObjectInt(28);
    }

    public void setRadius(int rad) {
        this.dataWatcher.updateObject(28, rad);
    }

    public ItemStack getSeed() {
        return this.dataWatcher.getWatchableObjectItemStack(27);
    }

    public void setSeed(ItemStack stack) {
        stack.stackSize = 1;
        this.dataWatcher.updateObject(27, stack == null ? new ItemStack(Blocks.air) : stack);
    }

    public String getAttacker() {
        return this.dataWatcher.getWatchableObjectString(29);
    }

    public void setAttacker(String str) {
        this.dataWatcher.updateObject(29, str);
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float help, float me, int please) {}

    protected float getGravityVelocity() {
        return 0.025F;
    }

    protected void onImpact(MovingObjectPosition mov) {
        Block block = worldObj.getBlock(mov.blockX, mov.blockY, mov.blockZ);
        EntityPlayer player = worldObj.getPlayerEntityByName(getAttacker());
        if (block != null && mov.entityHit == null) {
            if (block instanceof BlockBush || block instanceof BlockLeaves || player == null) return;
            ItemStack seed = getSeed();
            if (seed != null && seed.getItem() instanceof ItemGrassSeeds) {
                Item itemSeed = (ItemGrassSeeds) seed.getItem();
                for (int i = 0; i < getRadius(); i++) {
                    for (int k = 0; k < getRadius(); k++) {
                        int posX = mov.blockX + i - getRadius() / 2;
                        int posY = mov.blockY;
                        int posZ = mov.blockZ + k - getRadius() / 2;
                        int j = posY;
                        if (isTopBlock(posX, posY - 1, posZ)) j = Math.max(0, j - 20);
                        for (; !isTopBlock(posX, j, posZ); j++) {
                            if (Math.abs(j - posY) > 40) break;
                        }
                        posY = j;
                        if (!worldObj.isRemote && isDirt(posX, posY, posZ)) {
                            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(
                                    posX,
                                    posY,
                                    posZ,
                                    worldObj,
                                    worldObj.getBlock(posX, posY, posZ),
                                    worldObj.getBlockMetadata(posX, posY, posZ),
                                    player);
                            MinecraftForge.EVENT_BUS.post(event);
                            if (event.isCanceled()) continue;
                            itemSeed.onItemUse(seed.copy(), player, worldObj, posX, posY, posZ, mov.sideHit, 0, 0, 0);
                        } else if ((Math.random() < 0.15f || getRadius() < 3) && isDirt(posX, posY, posZ))
                            spawnGrowParticle(posX, posY, posZ);
                    }
                }
            }
            setDead();
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbtt) {
        super.readEntityFromNBT(nbtt);
        ticksExisted = nbtt.getInteger("ticks");
        setAttacker(nbtt.getString("attacker"));
        NBTTagCompound stackCmp = nbtt.getCompoundTag("seedStack");
        setSeed(ItemStack.loadItemStackFromNBT(stackCmp));
        setRadius(nbtt.getInteger("radius"));
    }

    public void writeEntityToNBT(NBTTagCompound nbtt) {
        super.writeEntityToNBT(nbtt);
        nbtt.setInteger("ticks", ticksExisted);
        nbtt.setString("attacker", getAttacker());
        ItemStack stack = getSeed();
        NBTTagCompound stackNbt = new NBTTagCompound();
        if (stack != null) stack.writeToNBT(stackNbt);
        nbtt.setInteger("radius", getRadius());
        nbtt.setTag("seedStack", stackNbt);
    }

    private void spawnGrowParticle(int posX, int posY, int posZ) {
        for (int i = 0; i < 50; i++) {
            double x = (Math.random() - 0.5D) * 3.0D;
            double y = Math.random() - 0.5D + 1.0D;
            double z = (Math.random() - 0.5D) * 3.0D;
            float velMul = 0.025F;
            Color color = ItemSprawlRod.getSeedColor(getSeed());
            Botania.proxy.wispFX(
                    worldObj,
                    posX + 0.5D + x,
                    posY + 0.5D + y,
                    posZ + 0.5D + z,
                    color.getRed() / 255.0f,
                    color.getGreen() / 255.0f,
                    color.getBlue() / 255.0f,
                    (float) Math.random() * 0.15F + 0.15F,
                    (float) -x * velMul,
                    (float) -y * velMul,
                    (float) -z * velMul);
        }
    }

    private boolean isDirt(int posX, int posY, int posZ) {
        return worldObj.getBlock(posX, posY, posZ) == Blocks.dirt
                || worldObj.getBlock(posX, posY, posZ) == Blocks.grass;
    }

    private boolean isTopBlock(int posX, int posY, int posZ) {
        return worldObj.getBlock(posX, posY + 1, posZ).getMaterial() == Material.air
                || worldObj.getBlock(posX, posY + 1, posZ) instanceof BlockBush;
    }
}
