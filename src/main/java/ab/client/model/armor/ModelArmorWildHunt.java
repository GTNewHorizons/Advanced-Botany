package ab.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class ModelArmorWildHunt extends ModelBiped {

    private final ModelRenderer head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer cube_r9;
    private final ModelRenderer cube_r10;
    private final ModelRenderer cube_r11;
    private final ModelRenderer cube_r12;
    private final ModelRenderer cube_r13;
    private final ModelRenderer cube_r14;
    private final ModelRenderer cube_r15;
    private final ModelRenderer cube_r16;
    private final ModelRenderer chest;
    private final ModelRenderer cube_r17;
    private final ModelRenderer cube_r18;
    private final ModelRenderer cube_r19;
    private final ModelRenderer cloak;
    private final ModelRenderer cube_r20;
    private final ModelRenderer lArm;
    private final ModelRenderer cube_r21;
    private final ModelRenderer cube_r22;
    private final ModelRenderer cube_r23;
    private final ModelRenderer cube_r24;
    private final ModelRenderer cube_r25;
    private final ModelRenderer cube_r26;
    private final ModelRenderer rArm;
    private final ModelRenderer cube_r27;
    private final ModelRenderer cube_r28;
    private final ModelRenderer cube_r29;
    private final ModelRenderer cube_r30;
    private final ModelRenderer cube_r31;
    private final ModelRenderer cube_r32;
    private final ModelRenderer lLeg;
    private final ModelRenderer cube_r33;
    private final ModelRenderer rLeg;
    private final ModelRenderer cube_r34;
    private final ModelRenderer rBoot;
    private final ModelRenderer lBoot;

    int slot;

    public ModelArmorWildHunt(int slot) {
        this.slot = slot;

        textureWidth = 64;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 32, 7, -4.0F, -8.0F, -4.0F, 8, 2, 8, 0.25F));
        head.cubeList.add(new ModelBox(head, 38, 18, -4.0F, -8.6F, -4.0F, 8, 1, 5, 0.0F));
        head.cubeList.add(new ModelBox(head, 21, 20, -3.0F, -8.6F, 1.0F, 6, 1, 2, 0.0F));
        head.cubeList.add(new ModelBox(head, 46, 86, -3.5F, -6.0F, 3.0F, 7, 1, 1, 0.1F));
        head.cubeList.add(new ModelBox(head, 40, 25, 3.0F, -6.2F, -1.8F, 1, 4, 5, 0.1F));
        head.cubeList.add(new ModelBox(head, 52, 25, -4.0F, -6.2F, -1.8F, 1, 4, 5, 0.1F));
        head.cubeList.add(new ModelBox(head, 28, 0, -4.0F, -6.3F, -4.0F, 1, 5, 2, 0.1F));
        head.cubeList.add(new ModelBox(head, 33, 7, 3.0F, -6.3F, -4.0F, 1, 5, 2, 0.1F));

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 3.8F, -2.7F);
        head.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.1745F, 0.0F, 0.1309F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 31, 25, 1.7F, -6.7F, -3.0F, 2, 3, 2, -0.2F));

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 3.8F, -2.7F);
        head.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.1745F, 0.0F, -0.1309F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 22, 25, -3.7F, -6.7F, -3.0F, 2, 3, 2, -0.2F));

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(0.0F, 4.0F, -1.0F);
        head.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.1309F, 0.0F, 0.0F);
        cube_r3.cubeList.add(new ModelBox(cube_r3, 21, 12, -1.0F, -13.1F, -2.8F, 2, 1, 6, 0.0F));

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(0.3F, 4.0F, -1.2F);
        head.addChild(cube_r4);
        setRotationAngle(cube_r4, -0.1309F, 0.0F, 0.0F);
        cube_r4.cubeList.add(new ModelBox(cube_r4, 47, 61, 1.0F, -15.2F, -3.4F, 2, 3, 2, -0.3F));
        cube_r4.cubeList.add(new ModelBox(cube_r4, 47, 55, -3.6F, -15.2F, -3.4F, 2, 3, 2, -0.3F));

        cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(-0.3F, 4.0F, -1.2F);
        head.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.2182F, 0.0F, 0.0F);
        cube_r5.cubeList.add(new ModelBox(cube_r5, 47, 71, -3.0F, -13.0F, 1.2F, 2, 1, 2, -0.1F));
        cube_r5.cubeList.add(new ModelBox(cube_r5, 47, 67, 1.6F, -13.0F, 1.2F, 2, 1, 2, -0.1F));

        cube_r6 = new ModelRenderer(this);
        cube_r6.setRotationPoint(0.0F, 4.0F, -1.0F);
        head.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.1309F, 0.0F, 0.0F);
        cube_r6.cubeList.add(new ModelBox(cube_r6, 24, 8, -1.0F, -11.2F, -2.0F, 2, 2, 1, 0.1F));

        cube_r7 = new ModelRenderer(this);
        cube_r7.setRotationPoint(-0.8F, 5.5F, -0.1F);
        head.addChild(cube_r7);
        setRotationAngle(cube_r7, -0.1309F, 1.5708F, 0.0F);
        cube_r7.cubeList.add(new ModelBox(cube_r7, 56, 43, -1.0F, -15.7F, -6.4F, 2, 3, 2, -0.35F));

        cube_r8 = new ModelRenderer(this);
        cube_r8.setRotationPoint(0.8F, 4.5F, -0.1F);
        head.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.2182F, -1.5708F, 0.0F);
        cube_r8.cubeList.add(new ModelBox(cube_r8, 56, 68, -1.0F, -13.7F, -1.7F, 2, 3, 2, -0.2F));

        cube_r9 = new ModelRenderer(this);
        cube_r9.setRotationPoint(0.8F, 5.5F, -0.1F);
        head.addChild(cube_r9);
        setRotationAngle(cube_r9, -0.1309F, -1.5708F, 0.0F);
        cube_r9.cubeList.add(new ModelBox(cube_r9, 56, 49, -1.0F, -15.7F, -6.4F, 2, 3, 2, -0.35F));

        cube_r10 = new ModelRenderer(this);
        cube_r10.setRotationPoint(-0.8F, 4.5F, -0.1F);
        head.addChild(cube_r10);
        setRotationAngle(cube_r10, 0.2182F, 1.5708F, 0.0F);
        cube_r10.cubeList.add(new ModelBox(cube_r10, 56, 74, -1.0F, -13.7F, -1.7F, 2, 3, 2, -0.2F));

        cube_r11 = new ModelRenderer(this);
        cube_r11.setRotationPoint(0.0F, 3.9F, -0.7F);
        head.addChild(cube_r11);
        setRotationAngle(cube_r11, -0.1309F, 0.0F, 0.0F);
        cube_r11.cubeList.add(new ModelBox(cube_r11, 56, 62, -1.0F, -15.7F, -6.6F, 2, 3, 2, -0.35F));

        cube_r12 = new ModelRenderer(this);
        cube_r12.setRotationPoint(0.0F, 3.9F, -0.7F);
        head.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.2182F, 0.0F, 0.0F);
        cube_r12.cubeList.add(new ModelBox(cube_r12, 56, 55, -1.0F, -14.7F, -1.7F, 2, 4, 2, -0.2F));

        cube_r13 = new ModelRenderer(this);
        cube_r13.setRotationPoint(-1.6F, 3.8F, -1.6F);
        head.addChild(cube_r13);
        setRotationAngle(cube_r13, 0.2182F, 0.7854F, 0.0F);
        cube_r13.cubeList.add(new ModelBox(cube_r13, 47, 43, -1.0F, -13.7F, -1.7F, 2, 3, 2, -0.2F));

        cube_r14 = new ModelRenderer(this);
        cube_r14.setRotationPoint(1.6F, 3.8F, -1.6F);
        head.addChild(cube_r14);
        setRotationAngle(cube_r14, -0.1309F, -0.7854F, 0.0F);
        cube_r14.cubeList.add(new ModelBox(cube_r14, 0, 0, -1.0F, -14.7F, -6.3F, 2, 3, 2, -0.35F));

        cube_r15 = new ModelRenderer(this);
        cube_r15.setRotationPoint(1.6F, 3.8F, -1.6F);
        head.addChild(cube_r15);
        setRotationAngle(cube_r15, 0.2182F, -0.7854F, 0.0F);
        cube_r15.cubeList.add(new ModelBox(cube_r15, 47, 49, -1.0F, -13.7F, -1.7F, 2, 3, 2, -0.2F));

        cube_r16 = new ModelRenderer(this);
        cube_r16.setRotationPoint(-1.6F, 3.8F, -1.6F);
        head.addChild(cube_r16);
        setRotationAngle(cube_r16, -0.1309F, 0.7854F, 0.0F);
        cube_r16.cubeList.add(new ModelBox(cube_r16, 0, 6, -1.0F, -14.7F, -6.3F, 2, 3, 2, -0.35F));

        chest = new ModelRenderer(this);
        chest.setRotationPoint(0.0F, 0.0F, 0.0F);
        chest.cubeList.add(new ModelBox(chest, 49, 25, -1.0F, 7.85F, -2.9F, 2, 2, 1, 0.0F));
        chest.cubeList.add(new ModelBox(chest, 0, 84, -4.0F, 0.9F, -3.1F, 8, 7, 1, 0.0F));
        chest.cubeList.add(new ModelBox(chest, 19, 85, -2.5F, 4.7F, 1.9F, 5, 6, 1, 0.0F));
        chest.cubeList.add(new ModelBox(chest, 44, 89, -3.5F, 0.0F, 1.1F, 7, 7, 2, 0.0F));
        chest.cubeList.add(new ModelBox(chest, 0, 93, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.2F));

        cube_r17 = new ModelRenderer(this);
        cube_r17.setRotationPoint(0.0F, -3.0F, 1.2F);
        chest.addChild(cube_r17);
        setRotationAngle(cube_r17, 0.0873F, 0.0F, 0.0F);
        cube_r17.cubeList.add(new ModelBox(cube_r17, 36, 1, -4.0F, 10.1F, -5.1F, 8, 4, 1, -0.1F));

        cube_r18 = new ModelRenderer(this);
        cube_r18.setRotationPoint(0.0F, 20.5F, 0.8F);
        chest.addChild(cube_r18);
        setRotationAngle(cube_r18, 0.0F, 0.0F, 0.0436F);
        cube_r18.cubeList.add(new ModelBox(cube_r18, 51, 35, 0.9F, -14.0F, -3.9F, 2, 6, 1, -0.1F));

        cube_r19 = new ModelRenderer(this);
        cube_r19.setRotationPoint(0.0F, 20.5F, 0.8F);
        chest.addChild(cube_r19);
        setRotationAngle(cube_r19, 0.0F, 0.0F, -0.0436F);
        cube_r19.cubeList.add(new ModelBox(cube_r19, 58, 35, -2.9F, -14.0F, -3.9F, 2, 6, 1, -0.1F));

        cloak = new ModelRenderer(this);
        cloak.setRotationPoint(0.0F, 2.0F, 0.0F);

        cube_r20 = new ModelRenderer(this);
        cube_r20.setRotationPoint(0.0F, -6.0F, 1.0F);
        cloak.addChild(cube_r20);
        setRotationAngle(cube_r20, 0.2182F, 0.0F, 0.0F);
        cube_r20.cubeList.add(new ModelBox(cube_r20, 0, 71, -3.0F, 4.6F, 1.4F, 6, 10, 0, 0.0F));

        lArm = new ModelRenderer(this);
        lArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        lArm.cubeList.add(new ModelBox(lArm, 31, 95, -3.0F, -2.0F, -2.0F, 4, 4, 4, 0.3F));
        lArm.cubeList.add(new ModelBox(lArm, 19, 59, -3.0F, 2.0F, -2.0F, 4, 8, 4, 0.1F));
        lArm.cubeList.add(new ModelBox(lArm, 19, 51, -3.9F, -2.7F, -2.0F, 4, 3, 4, 0.0F));

        cube_r21 = new ModelRenderer(this);
        cube_r21.setRotationPoint(-1.1F, 10.3F, 0.5F);
        lArm.addChild(cube_r21);
        setRotationAngle(cube_r21, -0.1309F, 0.7854F, 0.0F);
        cube_r21.cubeList.add(new ModelBox(cube_r21, 0, 29, -1.0F, -13.7F, -6.3F, 2, 2, 2, -0.45F));

        cube_r22 = new ModelRenderer(this);
        cube_r22.setRotationPoint(0.8F, 9.9F, 1.0F);
        lArm.addChild(cube_r22);
        setRotationAngle(cube_r22, -0.1309F, 0.0F, 0.0F);
        cube_r22.cubeList.add(new ModelBox(cube_r22, 9, 51, -3.0F, -14.7F, -3.4F, 2, 3, 2, -0.35F));

        cube_r23 = new ModelRenderer(this);
        cube_r23.setRotationPoint(0.8F, 9.9F, 1.0F);
        lArm.addChild(cube_r23);
        setRotationAngle(cube_r23, 0.2182F, 0.0F, 0.0F);
        cube_r23.cubeList.add(new ModelBox(cube_r23, 9, 63, -3.0F, -13.0F, 1.2F, 2, 1, 2, -0.1F));

        cube_r24 = new ModelRenderer(this);
        cube_r24.setRotationPoint(-1.1F, 9.7F, -0.8F);
        lArm.addChild(cube_r24);
        setRotationAngle(cube_r24, 0.2182F, 2.3562F, 0.0F);
        cube_r24.cubeList.add(new ModelBox(cube_r24, 0, 47, -1.0F, -13.7F, -1.7F, 2, 2, 2, -0.3F));

        cube_r25 = new ModelRenderer(this);
        cube_r25.setRotationPoint(-1.1F, 9.9F, -0.8F);
        lArm.addChild(cube_r25);
        setRotationAngle(cube_r25, -0.1309F, 2.3562F, 0.0F);
        cube_r25.cubeList.add(new ModelBox(cube_r25, 0, 23, -1.0F, -13.7F, -6.3F, 2, 2, 2, -0.45F));

        cube_r26 = new ModelRenderer(this);
        cube_r26.setRotationPoint(-1.1F, 10.1F, 0.5F);
        lArm.addChild(cube_r26);
        setRotationAngle(cube_r26, 0.2182F, 0.7854F, 0.0F);
        cube_r26.cubeList.add(new ModelBox(cube_r26, 0, 53, -1.0F, -13.7F, -1.7F, 2, 2, 2, -0.3F));

        rArm = new ModelRenderer(this);
        rArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        rArm.cubeList.add(new ModelBox(rArm, 19, 72, -1.0F, 2.0F, -2.0F, 4, 8, 4, 0.1F));
        rArm.cubeList.add(new ModelBox(rArm, 31, 105, -1.0F, -2.0F, -2.0F, 4, 4, 4, 0.3F));
        rArm.cubeList.add(new ModelBox(rArm, 19, 43, -0.1F, -2.7F, -2.0F, 4, 3, 4, 0.0F));

        cube_r27 = new ModelRenderer(this);
        cube_r27.setRotationPoint(1.1F, 10.1F, 0.5F);
        rArm.addChild(cube_r27);
        setRotationAngle(cube_r27, 0.2182F, -0.7854F, 0.0F);
        cube_r27.cubeList.add(new ModelBox(cube_r27, 0, 59, -1.0F, -13.7F, -1.7F, 2, 2, 2, -0.3F));

        cube_r28 = new ModelRenderer(this);
        cube_r28.setRotationPoint(1.1F, 10.3F, 0.5F);
        rArm.addChild(cube_r28);
        setRotationAngle(cube_r28, -0.1309F, -0.7854F, 0.0F);
        cube_r28.cubeList.add(new ModelBox(cube_r28, 0, 35, -1.0F, -13.7F, -6.3F, 2, 2, 2, -0.45F));

        cube_r29 = new ModelRenderer(this);
        cube_r29.setRotationPoint(1.2F, 10.0F, -0.8F);
        rArm.addChild(cube_r29);
        setRotationAngle(cube_r29, -0.1309F, -2.3562F, 0.0F);
        cube_r29.cubeList.add(new ModelBox(cube_r29, 0, 41, -1.0F, -13.7F, -6.3F, 2, 2, 2, -0.45F));

        cube_r30 = new ModelRenderer(this);
        cube_r30.setRotationPoint(1.2F, 9.8F, -0.8F);
        rArm.addChild(cube_r30);
        setRotationAngle(cube_r30, 0.2182F, -2.3562F, 0.0F);
        cube_r30.cubeList.add(new ModelBox(cube_r30, 0, 65, -1.0F, -13.7F, -1.7F, 2, 2, 2, -0.3F));

        cube_r31 = new ModelRenderer(this);
        cube_r31.setRotationPoint(3.3F, 10.0F, 1.0F);
        rArm.addChild(cube_r31);
        setRotationAngle(cube_r31, -0.1309F, 0.0F, 0.0F);
        cube_r31.cubeList.add(new ModelBox(cube_r31, 9, 57, -3.0F, -14.7F, -3.4F, 2, 3, 2, -0.35F));

        cube_r32 = new ModelRenderer(this);
        cube_r32.setRotationPoint(3.3F, 10.0F, 1.0F);
        rArm.addChild(cube_r32);
        setRotationAngle(cube_r32, 0.2182F, 0.0F, 0.0F);
        cube_r32.cubeList.add(new ModelBox(cube_r32, 9, 67, -3.0F, -13.0F, 1.2F, 2, 1, 2, -0.1F));

        lLeg = new ModelRenderer(this);
        lLeg.setRotationPoint(0.0F, 12.0F, 0.0F);
        lLeg.cubeList.add(new ModelBox(lLeg, 48, 103, -4.0F, 0.4F, -2.0F, 4, 8, 4, 0.15F));

        cube_r33 = new ModelRenderer(this);
        cube_r33.setRotationPoint(0.0F, 11.9F, 1.0F);
        lLeg.addChild(cube_r33);
        setRotationAngle(cube_r33, 0.0873F, 0.0F, 0.0F);
        cube_r33.cubeList.add(new ModelBox(cube_r33, 37, 122, -3.5F, -10.1F, -3.1F, 3, 4, 2, -0.3F));

        rLeg = new ModelRenderer(this);
        rLeg.setRotationPoint(0.0F, 12.0F, 0.0F);
        rLeg.cubeList.add(new ModelBox(rLeg, 48, 116, 0.0F, 0.4F, -2.0F, 4, 8, 4, 0.15F));

        cube_r34 = new ModelRenderer(this);
        cube_r34.setRotationPoint(0.0F, 11.9F, 1.0F);
        rLeg.addChild(cube_r34);
        setRotationAngle(cube_r34, 0.0873F, 0.0F, 0.0F);
        cube_r34.cubeList.add(new ModelBox(cube_r34, 37, 115, 0.5F, -10.1F, -3.1F, 3, 4, 2, -0.3F));

        rBoot = new ModelRenderer(this);
        rBoot.setRotationPoint(0.0F, 12.0F, 0.0F);
        rBoot.cubeList.add(new ModelBox(rBoot, 0, 120, -4.0F, 9.0F, -2.8F, 4, 3, 5, 0.25F));

        lBoot = new ModelRenderer(this);
        lBoot.setRotationPoint(0.0F, 12.0F, 0.0F);
        lBoot.cubeList.add(new ModelBox(lBoot, 0, 111, 0.0F, 9.0F, -2.8F, 4, 3, 5, 0.25F));
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity instanceof net.minecraft.entity.monster.EntitySkeleton
                || entity instanceof net.minecraft.entity.monster.EntityZombie)
            setRotationAnglesMonster(f, f1, f2, f3, f4, f5, entity);
        else {
            prepareForRender(entity);
            setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        }
        this.head.showModel = (this.slot == 0);
        this.chest.showModel = (this.slot == 1);
        this.lArm.showModel = (this.slot == 1);
        this.rArm.showModel = (this.slot == 1);
        this.rLeg.showModel = (this.slot == 2);
        this.lLeg.showModel = (this.slot == 2);
        this.lBoot.showModel = (this.slot == 3);
        this.rBoot.showModel = (this.slot == 3);
        this.bipedHeadwear.showModel = false;
        this.bipedHead = this.head;
        this.bipedBody = this.chest;
        this.bipedRightArm = this.lArm;
        this.bipedLeftArm = this.rArm;
        if (this.slot == 2) {
            this.bipedRightLeg = this.lLeg;
            this.bipedLeftLeg = this.rLeg;
        } else {
            this.bipedRightLeg = this.rBoot;
            this.bipedLeftLeg = this.lBoot;
        }
        if (this.isChild) {
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
            GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
            this.bipedHead.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
            this.bipedHeadwear.render(f5);
            GL11.glPopMatrix();
        } else {
            this.bipedHead.render(f5);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
            this.bipedHeadwear.render(f5);
        }
    }

    public void prepareForRender(Entity entity) {
        EntityLivingBase living = (EntityLivingBase) entity;
        this.isSneak = (living != null) ? living.isSneaking() : false;
        if (living != null && living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;
            ItemStack itemstack = player.inventory.getCurrentItem();
            this.heldItemRight = (itemstack != null) ? 1 : 0;
            this.aimedBow = false;
            if (itemstack != null && player.getItemInUseCount() > 0) {
                EnumAction enumaction = itemstack.getItemUseAction();
                if (enumaction == EnumAction.block) {
                    this.heldItemRight = 3;
                } else if (enumaction == EnumAction.bow) {
                    this.aimedBow = true;
                }
            }
        }
    }

    public void setRotationAnglesMonster(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
            float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        float f6 = MathHelper.sin(this.onGround * 3.1415927F);
        float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * 3.1415927F);
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
        this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
        this.bipedRightArm.rotateAngleX = -1.5707964F;
        this.bipedLeftArm.rotateAngleX = -1.5707964F;
        this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
