package ab.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import ab.api.AdvancedBotanyAPI;
import ab.api.TerraFarmlandList;
import ab.common.lib.register.RecipeListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

public class BlockTerraFarmland extends Block implements ILexiconable {

    private IIcon icon;

    public BlockTerraFarmland() {
        super(Material.ground);
        this.setTickRandomly(true);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.setBlockName("terraFarmland");
        this.setLightOpacity(255);
        this.setStepSound(soundTypeGravel);
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
            IPlantable plantable) {
        Block plant = plantable.getPlant(world, x, y + 1, z);
        return plant != Blocks.cactus && plant != Blocks.reeds;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote) return;
        Block block = world.getBlock(x, y + 1, z);
        int meta = world.getBlockMetadata(x, y + 1, z);
        if (block instanceof BlockCrops) {
            if (meta == 7) {
                refreshSeed(world, x, y, z, block, meta);
                return;
            }
        } else if (block instanceof IPlantable) {
            for (TerraFarmlandList fSeed : AdvancedBotanyAPI.farmlandList) {
                int meta1 = fSeed.getMeta();
                Block block1 = fSeed.getBlock();
                if (block != block1) continue;
                if (meta == meta1) {
                    refreshSeed(world, x, y, z, block1, meta1);
                    return;
                }
            }
        } else {
            world.setBlock(x, y, z, Blocks.dirt);
        }
    }

    private void refreshSeed(World world, int x, int y, int z, Block block, int meta) {
        List<EntityItem> items = world.getEntitiesWithinAABB(
                EntityItem.class,
                AxisAlignedBB.getBoundingBox(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4));
        if (!items.isEmpty() && items.size() > 7) return;
        IPlantable seed = (IPlantable) block;
        List<ItemStack> list = block.getDrops(world, x, y + 1, z, meta, 0);
        for (ItemStack stack : list) {
            if (stack == null) continue;
            else if (stack.getItem() == seed) {
                if (stack.stackSize > 1) stack.stackSize -= 1;
            } else stack.stackSize = Math.min(64, (int) (stack.stackSize * 2.5f));
        }
        for (ItemStack stack : list) {
            if (stack == null) continue;
            EntityItem itemEnt = new EntityItem(world, x + 0.5f, y + 1, z + 0.5f, stack);
            world.spawnEntityInWorld(itemEnt);
        }
        world.setBlock(x, y + 1, z, block, 0, 3);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
            int p_149668_4_) {
        return AxisAlignedBB.getBoundingBox(
                (double) (p_149668_2_ + 0),
                (double) (p_149668_3_ + 0),
                (double) (p_149668_4_ + 0),
                (double) (p_149668_2_ + 1),
                (double) (p_149668_3_ + 1),
                (double) (p_149668_4_ + 1));
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Item.getItemFromBlock(Blocks.dirt);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? icon : Blocks.dirt.getIcon(0, 0);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icon = reg.registerIcon("ab:terraFarmland");
    }

    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        return RecipeListAB.terraHoe;
    }
}
