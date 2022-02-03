package ab.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelManaContainer extends ModelBase {
	
	private final ModelRenderer bone;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer bb_main;

	public ModelManaContainer() {
		textureWidth = 64;
		textureHeight = 64;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -2.0F, 0.0F);
		bone.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.1309F, 0.0F, 0.1309F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 24, 47, -8.5F, -15.0F, -8.5F, 3, 5, 3, 0.0F));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -2.0F, 0.0F);
		bone.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.1309F, 0.0F, 0.1309F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 12, 47, -8.5F, -15.0F, 5.5F, 3, 5, 3, 0.0F));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, -2.0F, 0.0F);
		bone.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.1309F, 0.0F, -0.1309F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 36, 47, 5.5F, -15.0F, -8.5F, 3, 5, 3, 0.0F));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -17.0F, 0.0F);
		bone.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.1309F, 0.0F, 0.1309F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 36, 55, 5.5F, 10.0F, 5.5F, 3, 5, 3, 0.0F));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, -17.0F, 0.0F);
		bone.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.1309F, 0.0F, 0.1309F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 12, 55, 5.5F, 10.0F, -8.5F, 3, 5, 3, 0.0F));

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, -17.0F, 0.0F);
		bone.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.1309F, 0.0F, -0.1309F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 0, 55, -8.5F, 10.0F, 5.5F, 3, 5, 3, 0.0F));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(0.0F, -17.0F, 0.0F);
		bone.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.1309F, 0.0F, -0.1309F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 24, 55, -8.5F, 10.0F, -8.5F, 3, 5, 3, 0.0F));

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(0.0F, -2.0F, 0.0F);
		bone.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.1309F, 0.0F, -0.1309F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 0, 47, 5.5F, -15.0F, 5.5F, 3, 5, 3, 0.0F));

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -6.0F, -16.0F, -6.0F, 12, 13, 12, 0.0F));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 25, -5.0F, -17.0F, -5.0F, 10, 1, 10, 0.0F));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 36, -5.0F, -3.0F, -5.0F, 10, 1, 10, 0.0F));
	}

	public void render() {
		bone.render(0.0625f);
		bb_main.render(0.0625f);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
