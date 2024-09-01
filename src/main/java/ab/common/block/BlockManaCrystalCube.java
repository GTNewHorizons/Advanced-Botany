package ab.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileManaCrystalCube;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ItemTwigWand;

public class BlockManaCrystalCube extends BlockContainer implements ILexiconable {

    public BlockManaCrystalCube() {
        super(Material.iron);
        this.setCreativeTab(AdvancedBotany.tabAB);
        float f = 0.1875F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, 1.0F, 1.0F - f);
        this.setHardness(5.5F);
        this.setStepSound(soundTypeMetal);
        this.setBlockName("ABManaCrystalCube");
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7,
            float par8, float par9) {
        boolean isWand = player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTwigWand;
        if (isWand) return false;
        TileManaCrystalCube tile = (TileManaCrystalCube) world.getTileEntity(x, y, z);
        if (tile != null && !world.isRemote) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            tile.writeCustomNBT(nbttagcompound);
            int[] mana = tile.getManaAround();
            nbttagcompound.setInteger("knownMana", mana[0]);
            nbttagcompound.setInteger("knownMaxMana", mana[1]);
            if (player instanceof EntityPlayerMP) ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(
                    new S35PacketUpdateTileEntity(tile.xCoord, tile.yCoord, tile.zCoord, -999, nbttagcompound));
        }
        world.playSoundAtEntity((Entity) player, "botania:ding", 0.11F, 1.0F);
        return true;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileManaCrystalCube();
    }

    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.dreamwood.getIcon(0, 0);
    }

    public int getRenderType() {
        return BlockListAB.blockManaCrystalCubeRI;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        return RecipeListAB.manaCrystalCube;
    }
}
