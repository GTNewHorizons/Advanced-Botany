package ab.client.render.block;

import org.lwjgl.opengl.GL11;

import ab.common.block.tile.TileManaCharger;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderBlockManaCharger implements ISimpleBlockRenderingHandler {

	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	    GL11.glPushMatrix();
	    GL11.glTranslatef(-0.5F, -0.625F, -0.5F);
	    GL11.glScalef(1.35f, 1.35f, 1.35f);
	    TileManaCharger charger = new TileManaCharger();
	    TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)charger, 0.0D, 0.0D, 0.0D, 0.0F);
	    GL11.glPopMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}
	  
	public int getRenderId() {
		return BlockListAB.blockManaChargerRI;
	}
	  
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
}