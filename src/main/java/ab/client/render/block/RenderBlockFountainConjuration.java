package ab.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import ab.client.render.tile.RenderTileManaContainer;
import ab.common.block.tile.TileFountainConjuration;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockFountainConjuration implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.375F, -0.5F);
        GL11.glScalef(1.25f, 1.25f, 1.25f);
        TileFountainConjuration conjuration = new TileFountainConjuration();
        RenderTileManaContainer.metadata = metadata;
        TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity) conjuration, 0.0D, 0.0D, 0.0D, 0.0F);
        GL11.glPopMatrix();
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        return false;
    }

    public int getRenderId() {
        return BlockListAB.blockABConjurationRI;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
