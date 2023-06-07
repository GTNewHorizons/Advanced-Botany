package ab.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelFountainConjuration extends ModelBase {

    private final ModelRenderer bottomAnvil;
    private final ModelRenderer topAnvil;

    public ModelFountainConjuration() {
        textureWidth = 48;
        textureHeight = 48;

        bottomAnvil = new ModelRenderer(this);
        bottomAnvil.setRotationPoint(0.0F, 24.0F, 0.0F);
        bottomAnvil.cubeList.add(new ModelBox(bottomAnvil, 32, 26, -3.0F, -1.0F, -4.0F, 6, 1, 1, 0.0F));
        bottomAnvil.cubeList.add(new ModelBox(bottomAnvil, 0, 31, -5.0F, -1.0F, -3.0F, 12, 1, 6, 0.0F));
        bottomAnvil.cubeList.add(new ModelBox(bottomAnvil, 32, 17, -2.0F, -3.0F, -2.0F, 4, 1, 4, 0.0F));
        bottomAnvil.cubeList.add(new ModelBox(bottomAnvil, 0, 8, -4.0F, -2.0F, -3.0F, 8, 1, 6, 0.0F));
        bottomAnvil.cubeList.add(new ModelBox(bottomAnvil, 32, 23, -3.0F, -1.0F, 3.0F, 6, 1, 1, 0.0F));

        topAnvil = new ModelRenderer(this);
        topAnvil.setRotationPoint(0.0F, 26.0F, 0.0F);
        topAnvil.cubeList.add(new ModelBox(topAnvil, 0, 23, -6.5F, -11.0F, -3.0F, 12, 2, 6, 0.0F));
        topAnvil.cubeList.add(new ModelBox(topAnvil, 0, 38, -5.5F, -12.0F, -4.0F, 13, 2, 8, 0.0F));
        topAnvil.cubeList.add(new ModelBox(topAnvil, 0, 15, -5.5F, -9.0F, -3.0F, 9, 2, 6, 0.0F));
        topAnvil.cubeList.add(new ModelBox(topAnvil, 17, 0, -4.5F, -11.0F, 2.5F, 7, 3, 1, 0.0F));
        topAnvil.cubeList.add(new ModelBox(topAnvil, 0, 0, -4.5F, -11.0F, -3.5F, 7, 3, 1, 0.0F));
        topAnvil.cubeList.add(new ModelBox(topAnvil, 30, 12, -2.5F, -7.0F, -2.0F, 5, 1, 4, 0.0F));
    }

    public void renderBottom() {
        bottomAnvil.render(0.0625f);
    }

    public void renderTop() {
        topAnvil.render(0.0625f);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
