package ab.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelDiceFate extends ModelBase {

	private final ModelRenderer bb_main;

	public ModelDiceFate() {
		
		textureWidth = 48;
		textureHeight = 48;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 18.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 24, -6.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -6.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F));
	}

	public void render(float rotX, float rotY, float rotZ) {
		setRotationAngle(bb_main, getAngel(rotX), getAngel(rotY), getAngel(rotZ));
		bb_main.render(0.0625f);
	}
	
	private float getAngel(float rot) {
		return rot / 180.0F * (float)Math.PI;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}