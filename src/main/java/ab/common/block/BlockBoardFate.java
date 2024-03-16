package ab.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileBoardFate;
import ab.common.block.tile.TileGameBoard;
import ab.common.block.tile.TileInventory;
import ab.common.lib.register.AchievementRegister;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.achievement.IPickupAchievement;

public class BlockBoardFate extends BlockContainer implements IPickupAchievement, ILexiconable {

    protected IIcon[] iconsFate;
    protected IIcon[] iconsBoard;

    public BlockBoardFate() {
        super(Material.iron);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
        this.setHardness(3.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeMetal);
        this.setBlockName("boardFate");
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < 2; i++) list.add(new ItemStack(par1, 1, i));
    }

    public void registerBlockIcons(IIconRegister ir) {
        this.iconsFate = new IIcon[3];
        this.iconsBoard = new IIcon[3];
        for (int i = 0; i < iconsFate.length; i++) this.iconsFate[i] = ir.registerIcon("ab:boardFate_" + i);
        for (int i = 0; i < iconsBoard.length; i++) this.iconsBoard[i] = ir.registerIcon("ab:gameBoard_" + i);
    }

    public IIcon getIcon(int side, int meta) {
        if (meta == 0) return this.iconsBoard[Math.min(2, side)];
        else return this.iconsFate[Math.min(2, side)];
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7,
            float par8, float par9) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0) {
            TileGameBoard tile = (TileGameBoard) world.getTileEntity(x, y, z);
            if (player.isSneaking() && !tile.hasGame()) {
                tile.isSingleGame = !tile.isSingleGame;
                if (!world.isRemote) world.playSoundAtEntity((Entity) player, "botania:ding", 0.11F, 0.8F);
                return true;
            }
            if (!tile.hasGame()) tile.setPlayer(player);
            else if (!tile.isSingleGame && tile.playersName[1].isEmpty()
                    && !tile.playersName[0].equals(player.getCommandSenderName()))
                tile.setPlayer(player);
            else {
                return tile.dropDice(player);
            }
            return false;
        } else {
            if (!player.isSneaking()) {
                ItemStack heldItem = player.getCurrentEquippedItem();
                TileBoardFate tile = (TileBoardFate) world.getTileEntity(x, y, z);
                if (heldItem != null && tile != null && tile.isDice(heldItem)) {
                    for (int i = 0; i < tile.getSizeInventory(); i++) {
                        ItemStack slotStack = tile.getStackInSlot(i);
                        if (slotStack != null) continue;
                        heldItem.stackSize--;
                        if (heldItem.stackSize == 0)
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                        if (!world.isRemote) {
                            ItemStack copy = heldItem.copy();
                            copy.stackSize = 1;
                            tile.setInventorySlotContents(i, copy);
                            tile.slotChance[i] = (byte) (world.rand.nextInt(6) + 1);
                            tile.requestUpdate = true;
                            world.playSoundEffect(x, y, z, "ab:boardCube", 0.6F, 1.0F);
                        }
                        return true;
                    }
                }
                return false;
            } else {
                TileBoardFate tile = (TileBoardFate) world.getTileEntity(x, y, z);
                return tile.spawnRelic(player);
            }
        }
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return meta == 0 ? new TileGameBoard() : new TileBoardFate();
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote && meta == 1) {
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

    public Achievement getAchievementOnPickup(ItemStack stack, EntityPlayer player, EntityItem item) {
        return stack.getItemDamage() == 1 ? AchievementRegister.fateBoard : null;
    }

    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack stack) {
        return world.getBlockMetadata(x, y, z) == 0 ? RecipeListAB.gameBoard : RecipeListAB.fateBoard;
    }
}
