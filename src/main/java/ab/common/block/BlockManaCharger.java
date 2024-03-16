package ab.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileInventory;
import ab.common.block.tile.TileManaCharger;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.block.ModBlocks;

public class BlockManaCharger extends BlockContainer implements IWandHUD, IWandable, ILexiconable {

    public BlockManaCharger() {
        super(Material.iron);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setHardness(6.0F);
        float f = 0.0625f;
        this.setBlockBounds(f * 3, f * 3, f * 3, 1.0f - f * 3, 1.0f - f * 4 + f * 3, 1.0f - f * 3);
        this.setBlockName("ABManaCharger");
    }

    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.livingrock.getIcon(0, 0);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7,
            float par8, float par9) {
        TileManaCharger tile = (TileManaCharger) world.getTileEntity(x, y, z);
        if (tile != null) {
            int slotSide = side - 1;
            if (slotSide < 0) return false;
            ItemStack heldItem = player.getCurrentEquippedItem();
            ItemStack stackInSlot = tile.getStackInSlot(slotSide);
            if (player.isSneaking()) {
                if (stackInSlot != null) {
                    ItemStack copy = stackInSlot.copy();
                    tile.setInventorySlotContents(slotSide, null);
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
                    world.func_147453_f(x, y, z, (Block) this);
                    return true;
                }
            } else {
                if (heldItem != null && heldItem.getItem() instanceof IManaItem
                        && stackInSlot == null
                        && heldItem.getMaxStackSize() == 1) {
                    ItemStack copy = heldItem.copy();
                    copy.stackSize = 1;
                    heldItem.stackSize--;
                    if (heldItem.stackSize == 0)
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    player.inventory.markDirty();
                    if (!world.isRemote) {
                        tile.setInventorySlotContents(slotSide, copy);
                        tile.requestUpdate = true;
                    }
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

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return BlockListAB.blockManaChargerRI;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileManaCharger();
    }

    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        ((TileManaCharger) world.getTileEntity(x, y, z)).renderHUD(mc, res);
    }

    public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
        ((TileManaCharger) world.getTileEntity(x, y, z)).onWanded(player, stack);
        return true;
    }

    public LexiconEntry getEntry(World arg0, int arg1, int arg2, int arg3, EntityPlayer arg4, ItemStack arg5) {
        return RecipeListAB.manaCharger;
    }
}
