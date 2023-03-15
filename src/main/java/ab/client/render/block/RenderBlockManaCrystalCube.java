package ab.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import ab.common.block.tile.TileManaCrystalCube;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockManaCrystalCube implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glDisable(3008);
        TileManaCrystalCube cube = new TileManaCrystalCube();
        TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity) cube, 0.0D, 0.0D, 0.0D, 0.0F);
        GL11.glEnable(3008);
        GL11.glPopMatrix();
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        return false;
    }

    public int getRenderId() {
        return BlockListAB.blockManaCrystalCubeRI;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
