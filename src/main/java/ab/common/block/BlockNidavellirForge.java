package ab.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileInventory;
import ab.common.block.tile.TileNidavellirForge;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.block.ModBlocks;

public class BlockNidavellirForge extends BlockContainer implements ILexiconable {

    public BlockNidavellirForge() {
        super(Material.iron);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setHardness(3.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeMetal);
        this.setBlockName("ABPlate");
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        float f = 0.1875F;
        float f1 = 0.0625F;
        switch (world.getBlockMetadata(x, y, z)) {
            case 0:
                this.setBlockBounds(f, 0.0F, f1, 1.0F - f, 0.75F, 1.0F);
                break;
            case 1:
                this.setBlockBounds(0.0F, 0.0F, f, 1.0F - f1, 0.75F, 1.0F - f);
                break;
            case 2:
                this.setBlockBounds(f, 0.0F, 0.0F, 1.0F - f, 0.75F, 1.0F - f1);
                break;
            case 3:
                this.setBlockBounds(f1, 0.0F, f, 1.0F, 0.75F, 1.0F - f);
                break;
            default:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase liv, ItemStack stack) {
        int meta = MathHelper.floor_double((double) (liv.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, meta, 3);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        TileNidavellirForge tile = (TileNidavellirForge) world.getTileEntity(x, y, z);
        if (player.isSneaking()) {
            if (tile.getStackInSlot(0) != null) {
                ItemStack copy = tile.getStackInSlot(0).copy();
                if (!world.isRemote) {
                    Vec3 vec3 = player.getLook(1.0F).normalize();
                    EntityItem entityitem = new EntityItem(
                            world,
                            player.posX + vec3.xCoord,
                            player.posY + 1.2f,
                            player.posZ + vec3.zCoord,
                            copy);
                    world.spawnEntityInWorld(entityitem);
                    tile.requestUpdate = true;
                }
                tile.setInventorySlotContents(0, null);
                world.func_147453_f(x, y, z, (Block) this);
                return true;
            }
            for (int i = tile.getSizeInventory() - 1; i > 0; i--) {
                ItemStack stack = tile.getStackInSlot(i);
                if (stack != null) {
                    ItemStack copy = stack.copy();
                    if (!world.isRemote) {
                        Vec3 vec3 = player.getLook(1.0F).normalize();
                        EntityItem entityitem = new EntityItem(
                                world,
                                player.posX + vec3.xCoord,
                                player.posY + 1.2f,
                                player.posZ + vec3.zCoord,
                                copy);
                        world.spawnEntityInWorld(entityitem);
                        tile.requestUpdate = true;
                    }
                    tile.setInventorySlotContents(i, null);
                    world.func_147453_f(x, y, z, (Block) this);
                    return true;
                }
            }
        }
        return false;
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

    public int getRenderType() {
        return BlockListAB.blockABPlateRI;
    }

    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.storage.getIcon(0, 0);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
        return false;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileNidavellirForge();
    }

    public LexiconEntry getEntry(World arg0, int arg1, int arg2, int arg3, EntityPlayer arg4, ItemStack arg5) {
        return RecipeListAB.advandedAgglomerationPlate;
    }
}
