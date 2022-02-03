package ab.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ModelArmorNebula extends ModelBiped {

	int slot;
	private ModelRenderer head;
	private ModelRenderer cube_r1;
	private ModelRenderer cube_r2;
	private ModelRenderer cube_r3;
	private ModelRenderer cube_r4;
	private ModelRenderer cube_r5;
	private ModelRenderer cube_r6;
	private ModelRenderer cube_r7;
	private ModelRenderer body;
	private ModelRenderer cube_r16;
	private ModelRenderer cloak;
	private ModelRenderer cube_r8;
	private ModelRenderer cube_r9;
	private ModelRenderer cube_r10;
	private ModelRenderer cube_r11;
	private ModelRenderer lArm;
	private ModelRenderer cube_r12;
	private ModelRenderer cube_r13;
	private ModelRenderer rArm;
	private ModelRenderer cube_r14;
	private ModelRenderer cube_r15;
	private ModelRenderer lLeg;
	private ModelRenderer rLeg;
	private ModelRenderer lBoot;
	private ModelRenderer rBoot;
	
	public ModelArmorNebula(int slot) {
		this.slot = slot;
		this.textureWidth = 64;
	    this.textureHeight = 128;
	    
	    head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 10, -4.5F, -8.5F, -4.4F, 9, 3, 9, 0.0F));
		head.cubeList.add(new ModelBox(head, 27, 10, -3.0F, -9.0F, -4.0F, 6, 1, 8, 0.0F));
		head.cubeList.add(new ModelBox(head, 47, 6, -1.0F, -9.4F, -2.8F, 2, 1, 5, 0.0F));
		head.cubeList.add(new ModelBox(head, 18, 0, 3.5F, -5.5F, -4.4F, 1, 1, 4, 0.0F));
		head.cubeList.add(new ModelBox(head, 8, 0, -4.5F, -5.5F, -4.4F, 1, 1, 4, 0.0F));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.0436F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 56, 1, -1.0F, -33.2F, -6.1F, 2, 3, 1, 0.0F));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, -0.0436F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 28, 4, 3.7F, -33.3F, -0.4F, 1, 2, 2, 0.0F));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 8, 7, 4.4F, -31.0F, -4.7F, 1, 1, 2, 0.0F));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, 3.4F, -34.0F, -4.7F, 2, 3, 2, 0.0F));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 12, 3.4F, -35.0F, -4.7F, 1, 1, 1, 0.0F));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, 3.4F, -34.0F, -4.7F, 2, 3, 2, 0.0F));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 34, 0, 3.1F, -33.6F, -2.7F, 2, 3, 3, 0.0F));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, 0.0436F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 28, 0, -4.7F, -33.3F, -0.4F, 1, 2, 2, 0.0F));
		cube_r3.cubeList.add(new ModelBox(cube_r3, 44, 0, -5.1F, -33.6F, -2.7F, 2, 3, 3, 0.0F));
		cube_r3.cubeList.add(new ModelBox(cube_r3, 14, 7, -5.4F, -31.0F, -4.7F, 1, 1, 2, 0.0F));
		cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 5, -5.4F, -34.0F, -4.7F, 2, 3, 2, 0.0F));
		cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 10, -4.4F, -35.0F, -4.7F, 1, 1, 1, 0.0F));
		cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 5, -5.4F, -34.0F, -4.7F, 2, 3, 2, 0.0F));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 22, -4.5F, 0.6F, -3.7F, 9, 5, 7, 0.0F));
		body.cubeList.add(new ModelBox(body, 32, 19, -4.0F, 0.1F, -3.1F, 8, 11, 6, 0.0F));
		body.cubeList.add(new ModelBox(body, 0, 34, -3.0F, 11.1F, -3.1F, 6, 1, 1, 0.0F));
		body.cubeList.add(new ModelBox(body, 31, 89, -5.0F, 0.0F, 2.7F, 10, 3, 1, 0.0F));

		cube_r16 = new ModelRenderer(this);
		cube_r16.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.addChild(cube_r16);
		setRotationAngle(cube_r16, 0.1309F, 0.0F, 0.0F);
		cube_r16.cubeList.add(new ModelBox(cube_r16, 18, 99, -2.0F, -22.3F, -1.5F, 4, 6, 2, 0.0F));

		cloak = new ModelRenderer(this);
		cloak.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 24.0F, 0.0F);
		cloak.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.1309F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 0, 89, -3.5F, -15.7F, 5.7F, 7, 5, 1, 0.0F));
		
		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, 24.0F, 0.0F);
		cloak.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0873F, 0.0F, 0.0F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 0, 81, -3.5F, -22.8F, 5.0F, 7, 7, 1, 0.0F));
		
		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, 24.0F, 0.0F);
		cloak.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.1745F, 0.0F, 0.0F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 0, 95, -3.5F, -10.5F, 6.2F, 7, 5, 1, 0.0F));

		rArm = new ModelRenderer(this);
		rArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		rArm.cubeList.add(new ModelBox(rArm, 0, 41, -11.0F + 10.1f, 2.0F, -2.9F, 2, 1, 6, 0.0F));
		rArm.cubeList.add(new ModelBox(rArm, 18, 36, -14.0F + 10.1f, -3.0F, -2.9F, 5, 5, 6, 0.0F));
		rArm.cubeList.add(new ModelBox(rArm, 42, 38, -13.5F + 10.1f, 6.1F, -2.4F, 5, 4, 5, 0.0F));
		rArm.cubeList.add(new ModelBox(rArm, 40, 77, -13.0F + 10, 0.3F, -2.0F, 4, 3, 4, 0.0F));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(5.0F, 2.0F, 0.0F);
		rArm.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.0F, 0.0873F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 18, 86, -15.1F + 7, -23.9F + 18, -1.4F, 3, 2, 3, 0.0F));

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(5.0F, 2.0F, 0.0F);
		rArm.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.0F, 0.0436F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 0, 115, -15.6F + 6, -23.5F + 18, -2.4F, 4, 5, 5, 0.0F));

		lArm = new ModelRenderer(this);
		lArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		lArm.cubeList.add(new ModelBox(lArm, 0, 48, 9.0F - 10.1f, 2.0F, -2.9F, 2, 1, 6, 0.0F));
		lArm.cubeList.add(new ModelBox(lArm, 18, 47, 9.0F - 10.1f, -3.0F, -2.9F, 5, 5, 6, 0.0F));
		lArm.cubeList.add(new ModelBox(lArm, 42, 47, 8.5F - 10.1f, 6.1F, -2.4F, 5, 4, 5, 0.0F));
		lArm.cubeList.add(new ModelBox(lArm, 24, 77, 9.0F - 10, 0.3F, -2.0F, 4, 3, 4, 0.0F));

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(-5.0F, 2.0F, 0.0F);
		lArm.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, 0.0F, -0.0873F);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 18, 91, 12.1F - 7, -23.9F + 18, -1.4F, 3, 2, 3, 0.0F));

		cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(-5.0F, 2.0F, 0.0F);
		lArm.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 0.0F, -0.0436F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 0, 105, 11.6F - 6, -23.5F + 18, -2.4F, 4, 5, 5, 0.0F));

		rLeg = new ModelRenderer(this);
		rLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		rLeg.cubeList.add(new ModelBox(rLeg, 29, 60, -2.1F, -1.0F, -2.0F, 4, 10, 4, 0.0F));

		lLeg = new ModelRenderer(this);
		lLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		lLeg.cubeList.add(new ModelBox(lLeg, 45, 60, -1.9F, -1.0F, -2.0F, 4, 10, 4, 0.0F));

		rBoot = new ModelRenderer(this);
		rBoot.setRotationPoint(0.0F, 24.0F, 0.0F);
		rBoot.cubeList.add(new ModelBox(rBoot, 0, 58, -4.5F, -5.9F, -2.5F, 5, 6, 5, 0.0F));

		lBoot = new ModelRenderer(this);
		lBoot.setRotationPoint(0.0F, 24.0F, 0.0F);
		lBoot.cubeList.add(new ModelBox(lBoot, 0, 69, -0.5F, -5.9F, -2.5F, 5, 6, 5, 0.0F));		
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.head.showModel = (this.slot == 0);
		this.body.showModel = (this.slot == 1);
		this.cloak.showModel = (this.slot == 1);
		this.lArm.showModel = (this.slot == 1);
	    this.rArm.showModel = (this.slot == 1);
	    this.lLeg.showModel = (this.slot == 2);
	    this.rLeg.showModel = (this.slot == 2);
		this.bipedHeadwear.showModel = false;
		this.bipedHead = this.head;
		this.bipedBody = this.body;
		this.bipedRightArm = this.rArm;
	    this.bipedLeftArm = this.lArm;
	    this.bipedLeftLeg = this.lLeg;
	    this.bipedRightLeg = this.rLeg;	    
	    
	    float a = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
	    float b = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
	    float c = Math.min(a, b);
        this.cloak.rotateAngleX = -c / 2.0F + 0.1396263F;
        this.cloak.rotateAngleY = 0.0F;	  
        
		prepareForRender(entity);	
		
		if (entity instanceof EntitySkeleton || entity instanceof EntityZombie) {
			setRotationAnglesZombie(f, f1, f2, f3, f4, f5, entity);
		} else {
			setRotationAngles(f, f1, f2, f3, f4, f5, entity);
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
    		cloak.render(f5);
            GL11.glPopMatrix();
        } else {
            this.bipedHead.render(f5);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            GL11.glPushMatrix();
            GL11.glScalef(1.1F, 1.1F, 1.1F);
            GL11.glTranslatef(-0.0125F, 0.0F, -0.0125F);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
            GL11.glPopMatrix();
            this.bipedHeadwear.render(f5);
    		cloak.render(f5);
        }
	}
	
	public void prepareForRender(Entity entity) {
		EntityLivingBase living = (EntityLivingBase)entity;
		this.isSneak = (living != null) ? living.isSneaking() : false;
		if (living != null && living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)living;
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
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	public void setRotationAnglesZombie(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
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
}