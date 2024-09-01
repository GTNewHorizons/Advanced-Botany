package ab.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileABSpreader;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.api.wand.IWireframeAABBProvider;
import vazkii.botania.common.item.ModItems;

public class BlockABSpreader extends BlockContainer
        implements IWandable, IWandHUD, IWireframeAABBProvider, ILexiconable {

    public BlockABSpreader() {
        super(Material.wood);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setHardness(7.0F);
        this.setStepSound(soundTypeWood);
        this.setBlockName("advancedSpreader");
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase liv, ItemStack stack) {
        int orientation = BlockPistonBase.determineOrientation(world, x, y, z, liv);
        TileABSpreader spreader = (TileABSpreader) world.getTileEntity(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, stack.getItemDamage(), 3);
        switch (orientation) {
            case 0:
                spreader.rotationY = -90.0F;
            case 1:
                spreader.rotationY = 90.0F;
            case 2:
                spreader.rotationX = 270.0F;
            case 3:
                spreader.rotationX = 90.0F;
            case 4:
                return;
        }
        spreader.rotationX = 180.0F;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileABSpreader)) return false;
        TileABSpreader spreader = (TileABSpreader) tile;
        ItemStack lens = spreader.getStackInSlot(0);
        ItemStack heldItem = player.getCurrentEquippedItem();
        boolean isHeldItemLens = (heldItem != null && heldItem.getItem() instanceof vazkii.botania.api.mana.ILens);
        boolean wool = (heldItem != null && heldItem.getItem() == Item.getItemFromBlock(Blocks.wool));
        if (heldItem != null && heldItem.getItem() == ModItems.twigWand) return false;
        if (lens == null && isHeldItemLens) {
            if (!player.capabilities.isCreativeMode)
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            spreader.setInventorySlotContents(0, heldItem.copy());
            spreader.markDirty();
        } else if (lens != null && !wool) {
            ItemStack add = lens.copy();
            if (!player.inventory.addItemStackToInventory(add)) player.dropPlayerItemWithRandomChoice(add, false);
            spreader.setInventorySlotContents(0, null);
            spreader.markDirty();
        }
        if (wool && spreader.paddingColor == -1) {
            spreader.paddingColor = heldItem.getItemDamage();
            heldItem.stackSize--;
            if (heldItem.stackSize == 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
        } else if (heldItem == null && spreader.paddingColor != -1 && lens == null) {
            ItemStack pad = new ItemStack(Blocks.wool, 1, spreader.paddingColor);
            if (!player.inventory.addItemStackToInventory(pad)) player.dropPlayerItemWithRandomChoice(pad, false);
            spreader.paddingColor = -1;
            spreader.markDirty();
        }
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileABSpreader)) return;
        TileABSpreader inv = (TileABSpreader) tile;
        if (inv != null) {
            for (int i = 0; i < inv.getSizeInventory() + 1; i++) {
                ItemStack stack = (i >= inv.getSizeInventory())
                        ? ((inv.paddingColor == -1) ? null : new ItemStack(Blocks.wool, 1, inv.paddingColor))
                        : inv.getStackInSlot(i);
                if (stack != null) {
                    float spawnX = x + world.rand.nextFloat();
                    float spawnY = y + world.rand.nextFloat();
                    float spawnZ = z + world.rand.nextFloat();
                    EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
                    float mult = 0.05F;
                    droppedItem.motionX = ((-0.5F + world.rand.nextFloat()) * mult);
                    droppedItem.motionY = ((4.0F + world.rand.nextFloat()) * mult);
                    droppedItem.motionZ = ((-0.5F + world.rand.nextFloat()) * mult);
                    if (stack.hasTagCompound())
                        droppedItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                    world.spawnEntityInWorld((Entity) droppedItem);
                }
            }
            world.func_147453_f(x, y, z, par5);
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }

    public IIcon getIcon(int par1, int par2) {
        return BlockListAB.blockLebethron.getIcon(par1, 0);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return BlockListAB.blockABSpreaderRI;
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return 12;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileABSpreader();
    }

    public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
        ((TileABSpreader) world.getTileEntity(x, y, z)).onWanded(player, stack);
        return true;
    }

    public AxisAlignedBB getWireframeAABB(World world, int x, int y, int z) {
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox((x + f), (y + f), (z + f), ((x + 1) - f), ((y + 1) - f), ((z + 1) - f));
    }

    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        ((TileABSpreader) world.getTileEntity(x, y, z)).renderHUD(mc, res);
    }

    public LexiconEntry getEntry(World arg0, int arg1, int arg2, int arg3, EntityPlayer arg4, ItemStack arg5) {
        return RecipeListAB.lebethronSpreader;
    }
}
