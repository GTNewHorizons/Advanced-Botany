package ab.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelEngineerHopper extends ModelBase {
	
	private final ModelRenderer hopperBase;
	private final ModelRenderer hopperTop;
	private final ModelRenderer hopperBottom;

	public ModelEngineerHopper() {
		textureWidth = 64;
		textureHeight = 64;

		hopperBase = new ModelRenderer(this);
		hopperBase.setRotationPoint(0.0F, 24.0F, 0.0F);
		hopperBase.cubeList.add(new ModelBox(hopperBase, 0, 45, -7.0F, -16.0F, -7.0F, 2, 5, 14, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 12, 27, -5.0F, -11.2F, -5.0F, 10, 3, 10, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 0, 38, -6.0F, -11.0F, -2.0F, 2, 5, 4, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 52, 38, 4.0F, -11.0F, -2.0F, 2, 5, 4, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 0, 49, -2.0F, -11.0F, -6.0F, 4, 5, 2, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 52, 49, -2.0F, -11.0F, 4.0F, 4, 5, 2, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 32, 45, 5.0F, -16.0F, -7.0F, 2, 5, 14, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 20, 50, -5.0F, -16.0F, 5.0F, 10, 5, 2, 0.0F));
		hopperBase.cubeList.add(new ModelBox(hopperBase, 20, 42, -5.0F, -16.0F, -7.0F, 10, 5, 2, 0.0F));
		
		hopperTop = new ModelRenderer(this);
		hopperTop.setRotationPoint(0.0F, 24.0F, 0.0F);
		hopperTop.cubeList.add(new ModelBox(hopperTop, 20, 13, -3.0F, -20.0F, -3.0F, 6, 6, 6, 0.0F));
		
		hopperBottom = new ModelRenderer(this);
		hopperBottom.setRotationPoint(0.0F, 24.0F, 0.0F);
		hopperBottom.cubeList.add(new ModelBox(hopperBottom, 24, 3, -2.0F, -5.0F, -2.0F, 4, 4, 4, 0.0F));
	}
	
	public void renderHoper(double time) {
		float offset = (float)Math.sin(time / 10.0D) * 0.05F + 0.01F;
		hopperBase.render(0.0625f);
		
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glPushMatrix();	
		GL11.glTranslatef(0.0f, offset, 0.0f);		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
		hopperTop.render(0.0625f);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -offset, 0.0f);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
		hopperBottom.render(0.0625f);
		GL11.glPopMatrix();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
