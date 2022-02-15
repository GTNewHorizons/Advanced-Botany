package ab.common.entity;

import java.util.List;

import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.lib.LibObfuscation;

public class EntityManaVine extends EntityThrowable {

	public EntityManaVine(World world) {
		super(world);
	}

	public EntityManaVine(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.worldObj.isRemote)
			for (int i = 0; i < 9; i++)
				Botania.proxy.wispFX(this.worldObj, this.posX, this.posY, this.posZ, (18 + rand.nextInt(60)) / 255f,
						(198 + rand.nextInt(60)) / 255f, (140 + rand.nextInt(40)) / 255f, 0.09f,
						(float) (Math.random() - 0.5D) * 0.03F, (float) (Math.random() - 0.5D) * 0.03F,
						(float) (Math.random() - 0.5D) * 0.03F, 2);

	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (pos != null) {
			World world = this.worldObj;
			double x = 0;
			double y = 0;
			double z = 0;
			if (pos.entityHit != null) {
				world = pos.entityHit.worldObj;
				x = pos.entityHit.posX;
				y = pos.entityHit.posY;
				z = pos.entityHit.posZ;
				((EntityLivingBase) pos.entityHit).addPotionEffect(new PotionEffect(19, 180, 8));
				((EntityLivingBase) pos.entityHit).addPotionEffect(new PotionEffect(15, 30, 1));
				((EntityLivingBase) pos.entityHit).addPotionEffect(new PotionEffect(2, 260, 2));
			} else {
				x = pos.blockX;
				y = pos.blockY;
				z = pos.blockZ;
			}
			List<EntityLivingBase> list = (List<EntityLivingBase>) (world.getEntitiesWithinAABB(EntityLivingBase.class,
					AxisAlignedBB.getBoundingBox(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10)));
			for (EntityLivingBase target : list) {
				if (target != this.getThrower())
					if (target instanceof EntityAnimal) {
						EntityAnimal animal = (EntityAnimal) target;
						ReflectionHelper.setPrivateValue(EntityAnimal.class, animal, 1200, LibObfuscation.IN_LOVE);
						animal.setTarget(null);
						this.worldObj.setEntityState(animal, (byte) 18);
					} else
						target.addPotionEffect(new PotionEffect(19, 110, 4));
			}
			for (int k = 0; k < 13; k++)
				for (int k1 = 0; k1 < 7; k1++)
					for (int k2 = 0; k2 < 13; k2++) {
						int xCoord = MathHelper.floor_double(x) + k - 6;
						int yCoord = MathHelper.floor_double(y) + k1 - 3;
						int zCoord = MathHelper.floor_double(z) + k2 - 6;
						if (isPlant(xCoord, yCoord, zCoord)) {
							Block block = this.worldObj.getBlock(xCoord, yCoord, zCoord);
							for (int i = 0; i < 10; i++)
								block.updateTick(world, xCoord, yCoord, zCoord, world.rand);
							if (ConfigHandler.blockBreakParticles)
								this.worldObj.playAuxSFX(2005, xCoord, yCoord, zCoord,
										6 + this.worldObj.rand.nextInt(4));
							this.worldObj.playSoundEffect(x, y, z, "botania:agricarnation", 0.01F,
									0.5F + (float) Math.random() * 0.5F);
						}
					}
			if (!world.isRemote)
				for (int k = 0; k < 7; k++)
					for (int k1 = 0; k1 < 3; k1++)
						for (int k2 = 0; k2 < 7; k2++) {
							int xCoord = MathHelper.floor_double(x) + k - 3;
							int yCoord = MathHelper.floor_double(y) + k1 - 1;
							int zCoord = MathHelper.floor_double(z) + k2 - 3;
							Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
							if (!BlockListAB.blockFreyrLiana.canBlockStay(world, xCoord, yCoord - 1, zCoord))
								continue;
							Vec3 vec = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
							int distant = (int) vec.squareDistanceTo(Vec3.createVectorHelper(x, y, z));
							yCoord--;
							if (this.worldObj.rand.nextInt(distant + 1) == 0)
								while (yCoord > 0) {
									block = worldObj.getBlock(xCoord, yCoord, zCoord);
									if (block.isAir(worldObj, xCoord, yCoord, zCoord)) {
										if (world.rand.nextInt(4) < 3)
											worldObj.setBlock(xCoord, yCoord, zCoord, BlockListAB.blockFreyrLiana);
										else
											worldObj.setBlock(xCoord, yCoord, zCoord,
													BlockListAB.blockLuminousFreyrLiana);
										worldObj.playAuxSFX(2001, xCoord, yCoord, zCoord, Block.getIdFromBlock(BlockListAB.blockFreyrLiana));
										yCoord--;
									} else
										break;
								}
						}
			if (!world.isRemote)
				for (int i = 0; i < 100; i++) {
					Botania.proxy.wispFX(world, x + Math.random() * 5 - Math.random() * 5,
							y + Math.random() * 5 - Math.random() * 5, z + Math.random() * 5 - Math.random() * 5,
							(18 + rand.nextInt(50)) / 255f, (198 + rand.nextInt(40)) / 255f,
							(140 + rand.nextInt(20)) / 255f, 0.08f, 0, 3);
				}
			this.setDead();
		}
	}

	boolean isPlant(int x, int y, int z) {
		Block block = this.worldObj.getBlock(x, y, z);
		if (block == Blocks.grass || block == Blocks.leaves || block == Blocks.leaves2
				|| block instanceof BlockBush && !(block instanceof BlockCrops) && !(block instanceof BlockSapling))
			return false;

		Material mat = block.getMaterial();
		return mat != null
				&& (mat == Material.plants || mat == Material.cactus || mat == Material.grass || mat == Material.leaves
						|| mat == Material.gourd)
				&& block instanceof IGrowable
				&& ((IGrowable) block).func_149851_a(this.worldObj, x, y, z, this.worldObj.isRemote);
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_,
			float p_70056_8_, int p_70056_9_) {
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0f;
	}

	@Override
	public void setDead() {
		for (int i = 0; i < 200; i++) {
			Botania.proxy.wispFX(this.worldObj, posX, posY, posZ, (18 + rand.nextInt(50)) / 255f,
					(198 + rand.nextInt(40)) / 255f, (140 + rand.nextInt(20)) / 255f, 0.08f,
					(float) (-0.4 + Math.random() * 0.8), (float) (-0.4 + Math.random() * 0.8),
					(float) (-0.4 + Math.random() * 0.8));
		}
		super.setDead();
	}
}
