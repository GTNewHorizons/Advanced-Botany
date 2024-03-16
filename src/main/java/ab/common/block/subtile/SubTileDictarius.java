package ab.common.block.subtile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import ab.common.core.handler.ConfigABHandler;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.tile.TileSpecialFlower;

public class SubTileDictarius extends SubTileGenerating {

    private static final int workMana = 480;
    int cooldown;

    public void onUpdate() {
        super.onUpdate();
        if (this.supertile.getWorldObj().isRemote) return;
        int posX = this.supertile.xCoord;
        int posY = this.supertile.yCoord;
        int posZ = this.supertile.zCoord;
        World world = this.supertile.getWorldObj();
        if (world.getTotalWorldTime() % 1200 == 0) checkNearDictarius();
        if (this.mana != this.getMaxMana() && cooldown == 0) {
            List<EntityLivingBase> livs = world.getEntitiesWithinAABB(
                    EntityLivingBase.class,
                    AxisAlignedBB.getBoundingBox(posX, posY, posZ, (posX + 1), (posY + 1), (posZ + 1)).expand(2, 1, 2));
            int workMana = 0;
            int villagers = 0;
            if (!livs.isEmpty()) {
                for (int i = 0; i < Math.min(livs.size(), 16); i++) {
                    EntityLivingBase liv = livs.get(i);
                    if (liv instanceof EntityPlayer) workMana += SubTileDictarius.workMana;
                    else if (!world.isRemote && liv instanceof EntityVillager) {
                        workMana += (int) (SubTileDictarius.workMana / 6);
                        if (villagers > 15 && world.rand.nextInt(100) <= 4) liv.setDead();
                        villagers++;
                    }
                }
            }
            if (workMana > 0) {
                this.cooldown = 200;
                workMana *= Math.random();
                this.mana = Math.min(this.getMaxMana(), this.mana + workMana);
                sync();
            }
        }
        if (cooldown > 0) cooldown -= 1;
    }

    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toChunkCoordinates(), 2);
    }

    public void checkNearDictarius() {
        int findedFlower = 0;
        for (int z = -4; z < 4; z++) {
            for (int x = -4; x < 4; x++) {
                for (int y = -4; y < 4; y++) {
                    int posX = this.supertile.xCoord + x;
                    int posY = this.supertile.yCoord + y;
                    int posZ = this.supertile.zCoord + z;
                    TileEntity tile = this.supertile.getWorldObj().getTileEntity(posX, posY, posZ);
                    if (tile != null && tile instanceof TileSpecialFlower) {
                        TileSpecialFlower tileFlower = (TileSpecialFlower) tile;
                        if (tileFlower.subTileName.equals("dictarius")) {
                            if (findedFlower >= ConfigABHandler.maxDictariusCount) {
                                this.supertile.getWorldObj().playAuxSFX(
                                        2001,
                                        this.supertile.xCoord,
                                        this.supertile.yCoord,
                                        this.supertile.zCoord,
                                        Block.getIdFromBlock(this.supertile.getBlockType()));
                                if (this.supertile.getWorldObj()
                                        .getBlock(
                                                this.supertile.xCoord,
                                                this.supertile.yCoord - 1,
                                                this.supertile.zCoord)
                                        .isSideSolid(
                                                (IBlockAccess) this.supertile.getWorldObj(),
                                                this.supertile.xCoord,
                                                this.supertile.yCoord - 1,
                                                this.supertile.zCoord,
                                                ForgeDirection.UP))
                                    this.supertile.getWorldObj().setBlock(
                                            this.supertile.xCoord,
                                            this.supertile.yCoord,
                                            this.supertile.zCoord,
                                            (Block) Blocks.deadbush);
                                else this.supertile.getWorldObj().setBlockToAir(
                                        this.supertile.xCoord,
                                        this.supertile.yCoord,
                                        this.supertile.zCoord);
                                return;
                            }
                            findedFlower++;
                        }
                    }
                }
            }
        }
    }

    public int getMaxMana() {
        return 8000;
    }

    public int getColor() {
        return 0xd2cdb2;
    }

    public LexiconEntry getEntry() {
        return RecipeListAB.dictarius;
    }

    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setInteger("cooldown", this.cooldown);
    }

    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        this.cooldown = cmp.getInteger("cooldown");
    }
}
