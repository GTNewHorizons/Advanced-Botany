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

public class ModelArmorNebula extends ModelBiped {

    private final ModelRenderer head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r12;
    private final ModelRenderer cube_r13;
    private final ModelRenderer cube_r14;
    private final ModelRenderer cube_r15;
    private final ModelRenderer chestplate;
    private final ModelRenderer cube_r16;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r4_r1;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r5_r1;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r7_r1;
    private final ModelRenderer cube_r7_r2;
    private final ModelRenderer lArm;
    private final ModelRenderer cube_r17;
    private final ModelRenderer cube_r8;
    private final ModelRenderer cube_r9;
    private final ModelRenderer rArm;
    private final ModelRenderer cube_r18;
    private final ModelRenderer cube_r10;
    private final ModelRenderer cube_r11;
    private final ModelRenderer lLeg;
    private final ModelRenderer cube_r19;
    private final ModelRenderer cube_r20;
    private final ModelRenderer cube_r21;
    private final ModelRenderer rLeg;
    private final ModelRenderer cube_r22;
    private final ModelRenderer cube_r23;
    private final ModelRenderer cube_r24;
    private final ModelRenderer lBoot;
    private final ModelRenderer cube_r25;
    private final ModelRenderer cube_r26;
    private final ModelRenderer rBoot;
    private final ModelRenderer cube_r27;
    private final ModelRenderer cube_r28;

    int slot;

    public ModelArmorNebula(int slot) {
        this.slot = slot;

        textureWidth = 64;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 66, -4.0F, -8.0F, -4.0F, 8, 2, 8, 0.21F));
        head.cubeList.add(new ModelBox(head, 36, 90, -3.0F, -8.75F, -3.0F, 6, 1, 4, 0.21F));

        head.cubeList.add(new ModelBox(head, 0, 90, -4.0F, -6.0F, -4.0F, 1, 3, 7, 0.2085F));
        head.cubeList.add(new ModelBox(head, 19, 86, 3.0F, -6.0F, -4.0F, 1, 3, 7, 0.2085F));
        head.cubeList.add(new ModelBox(head, 28, 80, -3.0F, -6.0F, -4.0F, 6, 1, 1, 0.2075F));
        head.cubeList.add(new ModelBox(head, 23, 81, -4.0F, -6.0F, 3.0F, 1, 1, 1, 0.2075F));
        head.cubeList.add(new ModelBox(head, 23, 78, 3.0F, -6.0F, 3.0F, 1, 1, 1, 0.2075F));

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 0.0F, 0.3F);
        head.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.1745F, -0.3491F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 16, 78, 0.6F, -8.4F, -6.8F, 2, 3, 1, 0.21F));

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 0.0F, 0.3F);
        head.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.1745F, 0.3491F, 0.0F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 9, 78, -2.6F, -8.4F, -6.8F, 2, 3, 1, 0.21F));

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.2618F, 0.0F, 0.0F);
        cube_r3.cubeList.add(new ModelBox(cube_r3, 5, 83, -1.0F, -9.0F, -2.5F, 2, 1, 5, 0.21F));

        cube_r12 = new ModelRenderer(this);
        cube_r12.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.7418F, 0.0F, -0.0873F);
        cube_r12.cubeList.add(new ModelBox(cube_r12, 47, 83, -4.6F, -7.0F, 3.0F, 2, 2, 4, 0.21F));

        cube_r13 = new ModelRenderer(this);
        cube_r13.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(cube_r13);
        setRotationAngle(cube_r13, 0.7418F, 0.0F, 0.0873F);
        cube_r13.cubeList.add(new ModelBox(cube_r13, 45, 76, 2.6F, -7.0F, 3.0F, 2, 2, 4, 0.21F));

        cube_r14 = new ModelRenderer(this);
        cube_r14.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(cube_r14);
        setRotationAngle(cube_r14, -0.0873F, 0.0F, 0.0F);
        cube_r14.cubeList.add(new ModelBox(cube_r14, 31, 84, -1.0F, -5.0F, -4.6F, 2, 1, 1, 0.21F));

        cube_r15 = new ModelRenderer(this);
        cube_r15.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(cube_r15);
        setRotationAngle(cube_r15, -0.48F, 0.0F, 0.0F);
        cube_r15.cubeList.add(new ModelBox(cube_r15, 0, 78, -1.0F, -7.9F, -8.0F, 2, 4, 2, 0.21F));

        chestplate = new ModelRenderer(this);
        chestplate.setRotationPoint(0.0F, 0.0F, 0.0F);
        chestplate.cubeList.add(new ModelBox(chestplate, 0, 0, -4.5F, -0.2F, -3.0F, 9, 11, 6, 0.0F));
        chestplate.cubeList.add(new ModelBox(chestplate, 31, 10, -2.5F, 5.7F, 1.9F, 5, 4, 2, 0.0F));
        chestplate.cubeList.add(new ModelBox(chestplate, 31, 0, -3.5F, -0.6F, 2.6F, 7, 7, 2, 0.0F));

        cube_r16 = new ModelRenderer(this);
        cube_r16.setRotationPoint(0.0F, 24.0F, -1.0F);
        chestplate.addChild(cube_r16);
        setRotationAngle(cube_r16, 0.1309F, 0.0F, 0.0F);
        cube_r16.cubeList.add(new ModelBox(cube_r16, 57, 16, -1.0F, -20.7F, 7.7F, 2, 6, 1, 0.0F));

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(-0.1F, 25.2F, 3.35F);
        chestplate.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.2618F, 0.0F, 0.1745F);

        cube_r4_r1 = new ModelRenderer(this);
        cube_r4_r1.setRotationPoint(0.1F, -1.2F, -3.35F);
        cube_r4.addChild(cube_r4_r1);
        setRotationAngle(cube_r4_r1, -0.5236F, 0.0F, 0.0F);
        cube_r4_r1.cubeList.add(new ModelBox(cube_r4_r1, 50, 8, -2.0F, -25.0F, -3.0F, 2, 5, 2, 0.0F));

        cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(-0.1F, 25.2F, 3.35F);
        chestplate.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.2618F, 0.0F, -0.1745F);

        cube_r5_r1 = new ModelRenderer(this);
        cube_r5_r1.setRotationPoint(0.1F, -1.2F, -3.35F);
        cube_r5.addChild(cube_r5_r1);
        setRotationAngle(cube_r5_r1, -0.5236F, 0.0F, 0.0F);
        cube_r5_r1.cubeList.add(new ModelBox(cube_r5_r1, 50, 0, 0.0F, -25.0F, -3.0F, 2, 5, 2, 0.0F));

        cube_r6 = new ModelRenderer(this);
        cube_r6.setRotationPoint(0.0F, 24.0F, 0.0F);
        chestplate.addChild(cube_r6);
        setRotationAngle(cube_r6, -0.1745F, 0.0F, 0.0F);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setRotationPoint(0.0F, 23.75F, 0.0F);
        chestplate.addChild(cube_r7);
        setRotationAngle(cube_r7, -0.0873F, 0.0F, 0.0F);

        cube_r7_r1 = new ModelRenderer(this);
        cube_r7_r1.setRotationPoint(0.0F, -0.3F, 0.0F);
        cube_r7.addChild(cube_r7_r1);
        setRotationAngle(cube_r7_r1, 0.2182F, 0.0F, 0.0F);
        cube_r7_r1.cubeList.add(new ModelBox(cube_r7_r1, 34, 68, -1.5F, -23.1842F, -1.1615F, 3, 8, 2, 0.0F));

        cube_r7_r2 = new ModelRenderer(this);
        cube_r7_r2.setRotationPoint(0.0F, -0.3F, 0.0F);
        cube_r7.addChild(cube_r7_r2);
        setRotationAngle(cube_r7_r2, 0.2618F, 0.0F, 0.0F);
        cube_r7_r2.cubeList.add(new ModelBox(cube_r7_r2, 34, 17, 1.5F, -21.1842F, -0.8615F, 2, 7, 2, 0.0F));
        cube_r7_r2.cubeList.add(new ModelBox(cube_r7_r2, 54, 69, -3.5F, -21.1842F, -0.8615F, 2, 7, 2, 0.0F));

        lArm = new ModelRenderer(this);
        lArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        lArm.cubeList.add(new ModelBox(lArm, 0, 27, -4.0F, -2.4F, -2.5F, 5, 5, 5, 0.05F));
        lArm.cubeList.add(new ModelBox(lArm, 21, 27, -3.0F, 6.0F, -2.0F, 4, 4, 4, 0.2F));

        cube_r17 = new ModelRenderer(this);
        cube_r17.setRotationPoint(0.0F, -3.5F, 0.0F);
        lArm.addChild(cube_r17);
        setRotationAngle(cube_r17, 0.8727F, 0.0F, 0.0F);
        cube_r17.cubeList.add(new ModelBox(cube_r17, 45, 63, -1.7F, 1.3F, -2.3F, 2, 1, 4, 0.0F));

        cube_r8 = new ModelRenderer(this);
        cube_r8.setRotationPoint(5.2F, 22.3F, 0.0F);
        lArm.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.0F, 0.0F, 0.2182F);
        cube_r8.cubeList.add(new ModelBox(cube_r8, 54, 25, -14.0918F, -24.4119F, -1.0F, 2, 3, 2, 0.0F));

        cube_r9 = new ModelRenderer(this);
        cube_r9.setRotationPoint(5.2F, 22.3F, 0.0F);
        lArm.addChild(cube_r9);
        setRotationAngle(cube_r9, 0.0F, 0.0F, 0.0873F);
        cube_r9.cubeList.add(new ModelBox(cube_r9, 16, 19, -11.8564F, -24.7019F, -2.0F, 4, 3, 4, 0.0F));

        rArm = new ModelRenderer(this);
        rArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        rArm.cubeList.add(new ModelBox(rArm, 38, 26, -1.0F, -2.4F, -2.5F, 5, 5, 5, 0.05F));
        rArm.cubeList.add(new ModelBox(rArm, 0, 38, -1.0F, 6.0F, -2.0F, 4, 4, 4, 0.2F));

        cube_r18 = new ModelRenderer(this);
        cube_r18.setRotationPoint(-10.0F, -3.5F, 0.0F);
        rArm.addChild(cube_r18);
        setRotationAngle(cube_r18, 0.8727F, 0.0F, 0.0F);
        cube_r18.cubeList.add(new ModelBox(cube_r18, 45, 63, 9.7F, 1.3F, -2.3F, 2, 1, 4, 0.0F));

        cube_r10 = new ModelRenderer(this);
        cube_r10.setRotationPoint(-4.8F, 22.3F, 0.0F);
        rArm.addChild(cube_r10);
        setRotationAngle(cube_r10, 0.0F, 0.0F, -0.2182F);
        cube_r10.cubeList.add(new ModelBox(cube_r10, 19, 36, 11.6918F, -24.4119F, -1.0F, 2, 3, 2, 0.0F));

        cube_r11 = new ModelRenderer(this);
        cube_r11.setRotationPoint(-4.8F, 22.3F, 0.0F);
        rArm.addChild(cube_r11);
        setRotationAngle(cube_r11, 0.0F, 0.0F, -0.0873F);
        cube_r11.cubeList.add(new ModelBox(cube_r11, 24, 38, 7.4564F, -24.7019F, -2.0F, 4, 3, 4, 0.0F));

        lLeg = new ModelRenderer(this);
        lLeg.setRotationPoint(0.0F, 12.0F, 0.0F);
        lLeg.cubeList.add(new ModelBox(lLeg, 0, 50, -4.0F, 0.0F, -2.0F, 4, 8, 4, 0.2F));

        cube_r19 = new ModelRenderer(this);
        cube_r19.setRotationPoint(0.0F, 0.0F, 0.0F);
        lLeg.addChild(cube_r19);
        setRotationAngle(cube_r19, 0.2618F, 0.0F, 0.0F);
        cube_r19.cubeList.add(new ModelBox(cube_r19, 46, 17, -3.0F, 0.6F, -3.8F, 2, 4, 1, 0.2F));

        cube_r20 = new ModelRenderer(this);
        cube_r20.setRotationPoint(0.0F, 0.0F, 0.0F);
        lLeg.addChild(cube_r20);
        setRotationAngle(cube_r20, 0.3054F, 0.0F, 0.0F);
        cube_r20.cubeList.add(new ModelBox(cube_r20, 7, 20, -3.0F, 2.0F, 0.7F, 2, 4, 1, 0.2F));

        cube_r21 = new ModelRenderer(this);
        cube_r21.setRotationPoint(0.0F, 12.0F, 1.0F);
        lLeg.addChild(cube_r21);
        setRotationAngle(cube_r21, 0.1745F, 0.0F, -0.1745F);
        cube_r21.cubeList.add(new ModelBox(cube_r21, 17, 48, -3.0F, -10.7F, -1.9F, 2, 4, 2, 0.2F));

        rLeg = new ModelRenderer(this);
        rLeg.setRotationPoint(0.0F, 12.0F, 0.0F);
        rLeg.cubeList.add(new ModelBox(rLeg, 42, 38, 0.0F, 0.0F, -2.0F, 4, 8, 4, 0.2F));

        cube_r22 = new ModelRenderer(this);
        cube_r22.setRotationPoint(0.0F, 0.0F, 0.0F);
        rLeg.addChild(cube_r22);
        setRotationAngle(cube_r22, 0.2618F, 0.0F, 0.0F);
        cube_r22.cubeList.add(new ModelBox(cube_r22, 35, 46, 1.0F, 0.6F, -3.8F, 2, 4, 1, 0.2F));

        cube_r23 = new ModelRenderer(this);
        cube_r23.setRotationPoint(0.0F, 0.0F, 0.0F);
        rLeg.addChild(cube_r23);
        setRotationAngle(cube_r23, 0.3054F, 0.0F, 0.0F);
        cube_r23.cubeList.add(new ModelBox(cube_r23, 0, 20, 1.0F, 2.0F, 0.7F, 2, 4, 1, 0.2F));

        cube_r24 = new ModelRenderer(this);
        cube_r24.setRotationPoint(0.0F, 12.0F, 1.0F);
        rLeg.addChild(cube_r24);
        setRotationAngle(cube_r24, 0.1745F, 0.0F, 0.1745F);
        cube_r24.cubeList.add(new ModelBox(cube_r24, 26, 48, 1.0F, -10.7F, -1.9F, 2, 4, 2, 0.2F));

        lBoot = new ModelRenderer(this);
        lBoot.setRotationPoint(0.0F, 12.0F, 0.0F);
        lBoot.cubeList.add(new ModelBox(lBoot, 18, 56, 0.0F, 9.0F, -2.8F, 4, 3, 5, 0.2F));

        cube_r25 = new ModelRenderer(this);
        cube_r25.setRotationPoint(0.0F, 12.0F, -1.0F);
        lBoot.addChild(cube_r25);
        setRotationAngle(cube_r25, -0.3054F, 0.0F, 0.0F);
        cube_r25.cubeList.add(new ModelBox(cube_r25, 40, 52, 1.0F, -5.7F, 1.9F, 2, 3, 1, 0.2F));

        cube_r26 = new ModelRenderer(this);
        cube_r26.setRotationPoint(0.0F, 12.0F, -1.0F);
        lBoot.addChild(cube_r26);
        setRotationAngle(cube_r26, 0.7418F, 0.0F, 0.1745F);
        cube_r26.cubeList.add(new ModelBox(cube_r26, 33, 54, 3.2F, -1.8F, 2.2F, 1, 2, 3, 0.2F));

        rBoot = new ModelRenderer(this);
        rBoot.setRotationPoint(0.0F, 12.0F, 0.0F);
        rBoot.cubeList.add(new ModelBox(rBoot, 44, 53, -4.0F, 9.0F, -2.8F, 4, 3, 5, 0.2F));

        cube_r27 = new ModelRenderer(this);
        cube_r27.setRotationPoint(0.0F, 12.0F, -1.0F);
        rBoot.addChild(cube_r27);
        setRotationAngle(cube_r27, -0.3054F, 0.0F, 0.0F);
        cube_r27.cubeList.add(new ModelBox(cube_r27, 27, 65, -3.0F, -5.7F, 1.9F, 2, 3, 1, 0.2F));

        cube_r28 = new ModelRenderer(this);
        cube_r28.setRotationPoint(0.0F, 12.0F, -1.0F);
        rBoot.addChild(cube_r28);
        setRotationAngle(cube_r28, 0.7418F, 0.0F, -0.1745F);
        cube_r28.cubeList.add(new ModelBox(cube_r28, 37, 61, -4.2F, -1.8F, 2.2F, 1, 2, 3, 0.2F));
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
        this.chestplate.showModel = (this.slot == 1);
        this.lArm.showModel = (this.slot == 1);
        this.rArm.showModel = (this.slot == 1);
        this.rLeg.showModel = (this.slot == 2);
        this.lLeg.showModel = (this.slot == 2);
        this.lBoot.showModel = (this.slot == 3);
        this.rBoot.showModel = (this.slot == 3);
        this.bipedHeadwear.showModel = false;
        this.bipedHead = this.head;
        this.bipedBody = this.chestplate;
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
