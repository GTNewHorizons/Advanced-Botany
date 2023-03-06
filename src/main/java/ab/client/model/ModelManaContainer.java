package ab.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelManaContainer extends ModelBase {

    private final ModelRenderer bb_main;

    public ModelManaContainer() {
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.cubeList.add(new ModelBox(bb_main, 6, 14, -7.0F, -15.0F, -7.0F, 14, 5, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 24, 0, -5.0F, -10.2F, -5.0F, 10, 4, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 49, 37, -6.0F, -10.0F, -2.0F, 2, 6, 4, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 50, 26, 4.0F, -10.0F, -2.0F, 2, 6, 4, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 13, 23, -2.0F, -10.0F, -6.0F, 4, 6, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 23, -2.0F, -10.0F, 4.0F, 4, 6, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 27, 23, -5.0F, -18.0F, -5.0F, 1, 3, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 40, 14, -3.0F, -6.2F, -3.0F, 6, 5, 6, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 55, 0, -6.0F, -17.0F, -6.0F, 2, 2, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 28, 28, 4.0F, -17.0F, -6.0F, 2, 2, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 55, 5, 4.0F, -17.0F, 4.0F, 2, 2, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 28, 23, -6.0F, -17.0F, 4.0F, 2, 2, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 1, 0, 4.0F, -18.0F, -5.0F, 1, 3, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 16, 5, -4.0F, -18.0F, 4.0F, 8, 3, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 16, 0, -4.0F, -18.0F, -5.0F, 8, 3, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 38, -7.0F, -15.0F, 4.0F, 14, 5, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 41, 48, 4.0F, -15.0F, -4.0F, 3, 5, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 18, 48, -7.0F, -15.0F, -4.0F, 3, 5, 8, 0.0F));
    }

    public void render() {
        bb_main.render(0.0625f);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
