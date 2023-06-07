package ab.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import ab.client.render.tile.RenderTileManaContainer;
import ab.common.block.tile.TileFountainAlchemy;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockFountainAlchemy implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.375F, -0.5F);
        GL11.glScalef(1.25f, 1.25f, 1.25f);
        TileFountainAlchemy alchemy = new TileFountainAlchemy();
        RenderTileManaContainer.metadata = metadata;
        TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity) alchemy, 0.0D, 0.0D, 0.0D, 0.0F);
        GL11.glPopMatrix();
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        return false;
    }

    public int getRenderId() {
        return BlockListAB.blockABAlchemyRI;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
