package ab.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileLebethronCore;
import ab.common.lib.register.RecipeListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

public class BlockLebethronWood extends Block implements ILexiconable, ITileEntityProvider {

    private static IIcon[] icon = new IIcon[5];
    public static IIcon iconPortal;

    public BlockLebethronWood() {
        super(Material.wood);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setHardness(6.0F);
        this.setStepSound(soundTypeWood);
        this.setBlockName("lebethronWood");
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 4) {
            TileLebethronCore core = (TileLebethronCore) world.getTileEntity(x, y, z);
            ItemStack heldItem = player.getCurrentEquippedItem();
            if (heldItem == null) return false;
            Block block = Block.getBlockFromItem(heldItem.getItem());
            if (block.getMaterial() == Material.leaves && !heldItem.hasTagCompound()) {
                if (!world.isRemote) {
                    core.updateStructure();
                    if (core.getValidTree()) {
                        if (core.setBlock(player, block, heldItem.getItemDamage())) {
                            heldItem.stackSize--;
                            if (heldItem.stackSize == 0)
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote && meta == 4) {
            TileLebethronCore tile = (TileLebethronCore) world.getTileEntity(x, y, z);
            if (tile != null) {
                ItemStack stack = new ItemStack(tile.getBlock(), 1, tile.getMeta());
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
        super.breakBlock(world, x, y, z, block, meta);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < icon.length; i++) {
            list.add(new ItemStack(par1, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        for (int i = 0; i < icon.length; i++) icon[i] = ir.registerIcon("ab:lebethronWood_" + i);
        iconPortal = ir.registerIcon("ab:portalAlphirine");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icon[meta];
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return (world.getBlockMetadata(x, y, z) == 3) ? 12 : 0;
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return (world.getBlockMetadata(x, y, z) == 0);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return new ItemStack(this, 1, meta);
    }

    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        return RecipeListAB.lebethronWood;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return meta == 4 ? new TileLebethronCore() : null;
    }
}
