package ab.common.block.tile;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;

import ab.client.core.ClientHelper;
import ab.common.core.handler.ConfigABHandler;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.tile.TileMod;

public class TileManaContainer extends TileMod implements IManaPool, ISparkAttachable {

    protected int mana = 0;
    int knownMana = -1;

    public boolean canUpdate() {
        return true;
    }

    public void updateEntity() {
        if (this.worldObj.isRemote) {
            double particleChance = 1.0D - getCurrentMana() / getMaxMana() * 0.1D;
            Color color = new Color(50943);
            if (Math.random() > particleChance) Botania.proxy.wispFX(
                    this.worldObj,
                    this.xCoord + 0.5f + (Math.random() - 0.5) * 0.4f,
                    this.yCoord + 0.81D + (Math.random() * 0.05D),
                    this.zCoord + 0.5f + (Math.random() - 0.5) * 0.4f,
                    color.getRed() / 255.0F,
                    color.getGreen() / 255.0F,
                    color.getBlue() / 255.0F,
                    (float) Math.random() / 4.2F,
                    (float) -Math.random() / 50.0F,
                    2.0F);
        }
    }

    public void onWanded(EntityPlayer player, ItemStack wand) {
        if (player == null) return;
        if (!this.worldObj.isRemote) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            writeCustomNBT(nbttagcompound);
            nbttagcompound.setInteger("knownMana", getCurrentMana());
            if (player instanceof EntityPlayerMP) ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(
                    (Packet) new S35PacketUpdateTileEntity(
                            this.xCoord,
                            this.yCoord,
                            this.zCoord,
                            -999,
                            nbttagcompound));
        }
        this.worldObj.playSoundAtEntity((Entity) player, "botania:ding", 0.11F, 1.0F);
    }

    public void renderHUD(Minecraft mc, ScaledResolution res) {
        String name = StatCollector
                .translateToLocal("ab.manaContainer." + worldObj.getBlockMetadata(xCoord, yCoord, zCoord) + ".hud");
        int color = 0x30ead1;
        ClientHelper.drawPoolManaHUD(res, name, this.knownMana, getMaxMana(), color);
    }

    public int getCurrentMana() {
        return this.mana;
    }

    public boolean canRecieveManaFromBursts() {
        return true;
    }

    public int getMaxMana() {
        int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        return ConfigABHandler.maxContainerMana[Math.min(Math.max(meta, 0), ConfigABHandler.maxContainerMana.length)];
    }

    public boolean isFull() {
        return this.mana == getMaxMana();
    }

    public void recieveMana(int mana) {
        this.mana = Math.max(0, Math.min(getCurrentMana() + mana, getMaxMana()));
    }

    public void writeCustomNBT(NBTTagCompound nbtt) {
        nbtt.setInteger("mana", this.mana);
    }

    public void readCustomNBT(NBTTagCompound nbtt) {
        this.mana = nbtt.getInteger("mana");
        if (nbtt.hasKey("knownMana")) this.knownMana = nbtt.getInteger("knownMana");
    }

    public boolean areIncomingTranfersDone() {
        return false;
    }

    public void attachSpark(ISparkEntity entity) {}

    public boolean canAttachSpark(ItemStack arg0) {
        return true;
    }

    public ISparkEntity getAttachedSpark() {
        List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(
                ISparkEntity.class,
                AxisAlignedBB.getBoundingBox(
                        this.xCoord,
                        (this.yCoord + 1),
                        this.zCoord,
                        (this.xCoord + 1),
                        (this.yCoord + 2),
                        (this.zCoord + 1)));
        if (sparks.size() == 1) {
            Entity e = (Entity) sparks.get(0);
            return (ISparkEntity) e;
        }
        return null;
    }

    public int getAvailableSpaceForMana() {
        return Math.max(0, getMaxMana() - getCurrentMana());
    }

    public boolean isOutputtingPower() {
        return false;
    }
}
