package ab.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import ab.client.render.tile.RenderTileManaCharger;
import ab.common.block.tile.TileManaCharger;
import vazkii.botania.client.core.handler.LightningHandler;
import vazkii.botania.common.core.helper.Vector3;

public class ModelManaCharger extends ModelBase {

    private final ModelRenderer chargerBase;
    private final ModelRenderer chargerPlate;

    public ModelManaCharger() {
        textureWidth = 32;
        textureHeight = 32;

        chargerBase = new ModelRenderer(this);
        chargerBase.setRotationPoint(0.0F, 24.0F, 0.0F);
        chargerBase.cubeList.add(new ModelBox(chargerBase, 0, 9, -2.5F, -9.0F, -2.5F, 5, 4, 5, 0.0F));
        chargerBase.cubeList.add(new ModelBox(chargerBase, 0, 0, -3.5F, -11.0F, -3.5F, 7, 2, 7, 0.0F));
        chargerBase.cubeList.add(new ModelBox(chargerBase, 20, 9, -1.5F, -5.0F, -1.5F, 3, 3, 3, 0.0F));

        chargerPlate = new ModelRenderer(this);
        chargerPlate.setRotationPoint(0.0F, 17.0F, 0.0F);
        chargerPlate.cubeList.add(new ModelBox(chargerPlate, 0, 18, 5.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F));
    }

    public void render(RenderTileManaCharger render, double time) {
        TileManaCharger tile = render.charger;
        float offset = (float) Math.sin(time / 40.0D) * 0.1F + 0.05F;
        float polerot = -((float) time / 16.0F) * 25.0F;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, offset, 0.0F);
        GL11.glRotatef(polerot, 0.0F, 1.0F, 0.0F);
        chargerBase.render(0.0625f);
        if (tile.getStackInSlot(0) != null) {
            float rot = this.chargerPlate.rotateAngleY * 180.0F / (float) Math.PI;
            GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.125F, 0.8125F, 0.125F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            render.renderItemStack(tile.getStackInSlot(0));
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, offset / 1.3f + 0.185f, 0.0F);
        GL11.glScalef(0.85f, 0.85f, 0.85f);
        for (int i = 1; i < 5; i++) {
            switch (i) {
                case 1:
                    chargerPlate.rotateAngleY = 3.1416F;
                    break;
                case 2:
                    chargerPlate.rotateAngleY = 0;
                    break;
                case 3:
                    chargerPlate.rotateAngleY = 1.5708F;
                    break;
                case 4:
                    chargerPlate.rotateAngleY = -1.5708F;
                    break;
            }
            if (tile.getWorldObj() != null) time += i * 36.0f;
            float offset1 = (float) Math.sin(time / 15.0D) * 0.1F - 0.1F;
            if (time == -1.0D) offset1 = 0.0F;
            ItemStack stack = tile.getStackInSlot(i);
            GL11.glTranslatef(0.0F, -offset1, 0.0F);
            if (stack != null) {
                GL11.glPushMatrix();
                float manaPercent = TileManaCharger.getManaPercent(stack);
                float rot = this.chargerPlate.rotateAngleY * 180.0F / (float) Math.PI;
                if (manaPercent < 100.0f) {
                    float chargeY = (offset1 + (offset1 / 2.4f)) * ((100.0f - manaPercent) / 150.0f);
                    GL11.glTranslatef(0.0F, chargeY, 0.0F);
                    if (tile.clientTick[i] > 12) {
                        float posX = 0;
                        float posZ = 0;
                        switch (i) {
                            case 1:
                                posX = 0.0f;
                                posZ = -0.375f;
                                break;
                            case 2:
                                posX = 0.0f;
                                posZ = 0.375f;
                                break;
                            case 3:
                                posX = -0.375f;
                                posZ = 0.0f;
                                break;
                            case 4:
                                posX = 0.375f;
                                posZ = 0.0f;
                                break;
                        }
                        Vector3 itemVec = Vector3.fromTileEntity(render.charger).add(
                                0.5D + posX + ((Math.random() / 8) - 0.0625f),
                                0.67D + offset1,
                                0.5D + posZ + ((Math.random() / 8) - 0.0625f));
                        Vector3 tileVec = Vector3.fromTileEntity(render.charger)
                                .add(0.5D + posX, 0.7425D + offset1 - (chargeY / 2), 0.5D + posZ);
                        LightningHandler.spawnLightningBolt(
                                render.charger.getWorldObj(),
                                itemVec,
                                tileVec,
                                10.0F,
                                render.charger.getWorldObj().rand.nextLong(),
                                1140881820,
                                1140901631);
                        render.charger.clientTick[i] = 0;
                    }
                }
                GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.3125F, 1.06F, 0.1245F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                render.renderItemStack(stack);
                GL11.glPopMatrix();
            }
            chargerPlate.render(0.0625f);
            GL11.glTranslatef(0.0F, offset1, 0.0F);
        }
        GL11.glPopMatrix();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
