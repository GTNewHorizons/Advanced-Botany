package ab.common.block.tile;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import ab.api.IRenderHud;
import ab.client.core.ClientHelper;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.common.block.tile.TileMod;

public class TileManaCrystalCube extends TileMod implements IRenderHud {

    int knownMana = -1;
    int knownMaxMana = -1;

    public boolean canUpdate() {
        return false;
    }

    public void updateEntity() {}

    public void renderHud(Minecraft mc, ScaledResolution res) {
        String name = StatCollector.translateToLocal("ab.manaCrystalCube.hud");
        int color = 0x30ead1;
        ClientHelper.drawPoolManaHUD(res, name, this.knownMana, this.knownMaxMana, color);
    }

    public int[] getManaAround() {
        int[] mana = new int[] { 0, 0 };
        List<ISparkEntity> allSparks = SparkHelper.getSparksAround(worldObj, xCoord, yCoord, zCoord);
        for (ISparkEntity spark : allSparks) {
            ISparkAttachable tileMana = spark.getAttachedTile();
            mana[1] += tileMana.getCurrentMana() + tileMana.getAvailableSpaceForMana();
            mana[0] += tileMana.getCurrentMana();
        }
        return mana;
    }

    public void readCustomNBT(NBTTagCompound nbtt) {
        if (nbtt.hasKey("knownMana")) this.knownMana = nbtt.getInteger("knownMana");
        if (nbtt.hasKey("knownMaxMana")) this.knownMaxMana = nbtt.getInteger("knownMaxMana");
    }
}
