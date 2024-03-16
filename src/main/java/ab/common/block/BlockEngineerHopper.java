package ab.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileEngineerHopper;
import ab.common.block.tile.TileInventory;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.block.ModBlocks;

public class BlockEngineerHopper extends BlockContainer implements IWandable, IWandHUD, ILexiconable {

    private static IIcon[] icons = new IIcon[3];

    public BlockEngineerHopper() {
        super(Material.iron);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setBlockName("engineerHopper");
        float offset = 0.125F;
        this.setBlockBounds(offset, offset / 2.0f, offset, 1.0F - offset, 1.0F - (offset / 2.0f), 1.0F - offset);
    }

    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        return RecipeListAB.engineerHopper;
    }

    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.blazeBlock.getIcon(0, 0);
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote) {
            TileInventory inv = (TileInventory) world.getTileEntity(x, y, z);
            if (inv != null) {
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack != null) {
                        float f = world.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
                        EntityItem entityitem = new EntityItem(world, (x + f), (y + f1), (z + f2), stack.copy());
                        float f3 = 0.05F;
                        entityitem.motionX = ((float) world.rand.nextGaussian() * f3);
                        entityitem.motionY = ((float) world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = ((float) world.rand.nextGaussian() * f3);
                        if (stack.hasTagCompound())
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEngineerHopper();
    }

    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        ((TileEngineerHopper) world.getTileEntity(x, y, z)).renderHUD(mc, res);
    }

    public int getRenderType() {
        return BlockListAB.blockEngineerHopperRI;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean onUsedByWand(EntityPlayer player, ItemStack wand, World world, int x, int y, int z, int meta) {
        if (!player.isSneaking()) {
            TileEngineerHopper tile = (TileEngineerHopper) world.getTileEntity(x, y, z);
            tile.changeBindType();
            if (!world.isRemote) world.playSoundAtEntity((Entity) player, "botania:ding", 0.11F, 1.0F);
        }
        return true;
    }
}
