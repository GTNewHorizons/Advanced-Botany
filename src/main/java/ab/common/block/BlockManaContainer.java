package ab.common.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.AdvancedBotany;
import ab.common.block.tile.TileManaContainer;
import ab.common.lib.register.BlockListAB;
import ab.common.lib.register.RecipeListAB;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.block.ModBlocks;

public class BlockManaContainer extends BlockContainer implements IWandHUD, IWandable, ILexiconable {

    public BlockManaContainer() {
        super(Material.iron);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setHardness(9.0F);
        this.setBlockName("ABManaContainer");
        float offset = 0.08F;
        this.setBlockBounds(offset, offset / 2.0f, offset, 1.0F - offset, 1.0F - (offset / 2.0f), 1.0F - offset);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2, List par3) {
        par3.add(new ItemStack(par1, 1, 0));
        par3.add(new ItemStack(par1, 1, 1));
        par3.add(new ItemStack(par1, 1, 2));
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileManaContainer();
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return BlockListAB.blockManaContainerRI;
    }

    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.livingrock.getIcon(0, 0);
    }

    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        ((TileManaContainer) world.getTileEntity(x, y, z)).renderHUD(mc, res);
    }

    public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
        ((TileManaContainer) world.getTileEntity(x, y, z)).onWanded(player, stack);
        return true;
    }

    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        return RecipeListAB.manaContainer;
    }
}
