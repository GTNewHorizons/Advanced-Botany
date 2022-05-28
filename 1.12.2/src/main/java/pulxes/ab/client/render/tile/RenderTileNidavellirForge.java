package pulxes.ab.client.render.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.client.model.ModelNidavellirForge;
import pulxes.ab.common.block.tile.TileNidavellirForge;
import pulxes.ab.common.lib.register.BlockListAB;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class RenderTileNidavellirForge extends TileEntitySpecialRenderer<TileNidavellirForge> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(AdvancedBotany.MODID + ":textures/model/nidavellir_forge.png");
	private static final ModelNidavellirForge MODEL = new ModelNidavellirForge();
	private static final float[] ROTATIONS = new float[] { 180.0F, 0.0F, 90.0F, 270.0F };
	private List<EntityItem> entityList = null;
	private RenderEntityItem itemRenderer = null;
	
	public void render(@Nullable TileNidavellirForge forge, double x, double y, double z, float pticks, int digProgress, float unused) {
		if(forge != null && (!forge.getWorld().isBlockLoaded(forge.getPos(), false) || forge.getWorld().getBlockState(forge.getPos()).getBlock() != BlockListAB.nidavellirForge))
			return;
		
		int meta = 0;
		double worldTime = 0.0D;
		float indetY = 0.0f;
		
		if(forge != null && forge.getWorld() != null) {
			meta = forge.getBlockMetadata();
			worldTime = ClientTickHandler.ticksInGame + pticks + (new Random((forge.getPos().getX() ^ forge.getPos().getY() ^ forge.getPos().getZ()))).nextInt(360);
			indetY = (float)(Math.sin(worldTime / 18.0D) / 24.0D);
			if(entityList == null) {
				List<EntityItem> list = new ArrayList();
				for(int i = 0; i < forge.getSizeInventory(); i++)
					list.add(new EntityItem(forge.getWorld()));
				entityList = list;
			}
			if(itemRenderer == null) {
				itemRenderer = new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()) {
					public boolean shouldBob() {
						return false;
					}
				}; 
			}
		}
		
		GlStateManager.pushMatrix();
		
	    GlStateManager.enableRescaleNormal();
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    (Minecraft.getMinecraft()).renderEngine.bindTexture(TEXTURE);
	    GlStateManager.translate(x, y, z);
	    GlStateManager.translate(0.5f, 1.5f, 0.5f);
	    GlStateManager.rotate(180.0f, 1.0f, 0.0f, 1.0f);
	    GlStateManager.rotate(ROTATIONS[Math.max(Math.min(ROTATIONS.length - 1, meta - 2), 0)], 0.0F, 1.0F, 0.0F);
	    MODEL.renderBottom();
	    GlStateManager.translate(0.0f, indetY, 0.0f);
	    MODEL.renderTop();
	    GlStateManager.color(1.0F, 1.0F, 1.0F);
	    GlStateManager.enableRescaleNormal();
	    
	    GlStateManager.popMatrix();
	    
	    if(forge != null && forge.getWorld() != null) {
	    	int items = 0;
	 	    for(int i = 1; i < forge.getSizeInventory() && !forge.getItemHandler().getStackInSlot(i).isEmpty(); i++)
	 	    	items++; 
	 	    float[] angles = new float[forge.getSizeInventory() - 1];
	 	    float anglePer = 360.0F / items;
	 	    float totalAngle = 0.0F;
	 	    for(int i = 0; i < angles.length; i++)
	 	    	angles[i] = totalAngle += anglePer; 
	 	    
	 	    GlStateManager.pushMatrix();
	 	    
	 	    GlStateManager.translate(x + 0.5F, y + 0.75F, z + 0.5F);
	 	    GlStateManager.scale(0.4F, 0.4F, 0.4F);
	 	    
	 	    if(entityList != null) {
	 	    	
	 	    	ItemStack resultStack = forge.getItemHandler().getStackInSlot(0);
	 	    	if(!resultStack.isEmpty()) {
	 	    		
	 	    		entityList.get(0).setItem(resultStack);
	 	    		entityList.get(0).hoverStart = 0;
	 	    		
	 	    		float yPos = MathHelper.sin((ClientTickHandler.ticksInGame + pticks) / 8.0F) * 0.05F + 0.1F;
	 	    		float angelRotate = ((ClientTickHandler.ticksInGame + pticks) / 20.0F) * 57.295776F;
	 	    		
	 	    		GlStateManager.pushMatrix();
	 	    		
	 	    		GlStateManager.scale(1.5F, 1.5F, 1.5F);
	 	    		GlStateManager.translate(0.0f, yPos - indetY, 0.0f);
	 	    		GlStateManager.rotate(angelRotate, 0.0f, 1.0f, 0.0f);
	 	    		itemRenderer.doRender(entityList.get(0), 0.0D, 0.0D, 0.0D, 1.0F, 0.0f);
	 	    		
	 	 	        GlStateManager.popMatrix();
	 	    	}
	 	    	
	 	    	for(int i = 1; i < entityList.size(); i++) {
	 	    		ItemStack slotStack = forge.getItemHandler().getStackInSlot(i);
	 	    		
	 	    		if(slotStack.isEmpty())
	 	    			continue;
	 	    		
	 	    		entityList.get(i).setItem(slotStack);
	 	    		entityList.get(i).hoverStart = 0;
	 	    		
	 	    		float yPos = MathHelper.sin((ClientTickHandler.ticksInGame + pticks + i * 7) / 8.0F) * 0.05F + 0.1F;
	 	    		float angelRotate = ((ClientTickHandler.ticksInGame + pticks + i * 7) / 20.0F) * 57.295776F;
	 	    		
	 	    		GlStateManager.pushMatrix();
	 	    		
	 	    		GlStateManager.rotate(angles[i - 1], 0.0F, 1.0F, 0.0F);
	 	    		GlStateManager.translate(1.35F, 0.0F, 0.25F);
	 	    		GlStateManager.translate(0.0f, yPos, 0.0f);
	 	    		GlStateManager.rotate(angelRotate, 0.0f, 1.0f, 0.0f);
	 	    		itemRenderer.doRender(entityList.get(i), 0.0D, 0.0D, 0.0D, 1.0F, 0.0f);
	 	    		
	 	 	        GlStateManager.popMatrix();
	 	    	}
	 	    }
	 	    
	 	    GlStateManager.popMatrix();
	    }
	}
}