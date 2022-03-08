package ab.common.entity;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import ab.client.core.ClientHelper;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
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

	int maxAge = 240;
	int age;

	public EntityManaVine(World world) {
		super(world);
	}

	public EntityManaVine(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	public void onUpdate() {
		super.onUpdate();
		age++;
		if (age >= maxAge)
			setDead();
		if(this.worldObj.isRemote) {
			float m = 0.02F;
			float f1 = 6.0f;
			for(int i = 0; i < 4; i++) {
				double posX = this.posX + (Math.random() / f1 - (0.5f / f1));
				double posY = this.posY + (Math.random() / f1 - (0.5f / f1));
				double posZ = this.posZ + (Math.random() / f1 - (0.5f / f1));
				float mx = (float)(Math.random() - 0.5D) * m;
				float my = (float)(Math.random() - 0.5D) * m;
				float mz = (float)(Math.random() - 0.5D) * m;
				Color color = ClientHelper.getCorporeaRuneColor((int)posX, (int)posY, (int)posZ, 3);
				Botania.proxy.wispFX(worldObj, posX, posY, posZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, (float) (0.15f + (Math.random() * 0.12f)), mx, my, mz, 0.7f);	
			}
		}
	}

	protected void onImpact(MovingObjectPosition pos) {
		if(pos != null && pos.entityHit == null) {
			World world = this.worldObj;
			int x = pos.blockX;
			int y = pos.blockY;
			int z = pos.blockZ;
			List<EntityLivingBase> list = (List<EntityLivingBase>) (world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10)));
			for(EntityLivingBase target : list) {
				if(target instanceof EntityAnimal) {
					EntityAnimal animal = (EntityAnimal) target;
					ReflectionHelper.setPrivateValue(EntityAnimal.class, animal, 1200, LibObfuscation.IN_LOVE);
					animal.setTarget(null);
					this.worldObj.setEntityState(animal, (byte) 18);
				}
			}
			for (int k = 0; k < 7; k++)
				for (int k1 = 0; k1 < 7; k1++)
					for (int k2 = 0; k2 < 7; k2++) {
						int xCoord = x + k - 3;
						int yCoord = y + k1 - 1;
						int zCoord = z + k2 - 3;
						Block block = this.worldObj.getBlock(xCoord, yCoord, zCoord);
						if(isPlant(xCoord, yCoord, zCoord)) {
							for(int i = 0; i < 10; i++)
								block.updateTick(world, xCoord, yCoord, zCoord, world.rand);
							if(ConfigHandler.blockBreakParticles)
								this.worldObj.playAuxSFX(2005, xCoord, yCoord, zCoord, 6 + this.worldObj.rand.nextInt(4));
								this.worldObj.playSoundEffect(x, y, z, "botania:agricarnation", 0.01F, 0.5F + (float) Math.random() * 0.5F);
						} else if(block instanceof BlockGrass && world.rand.nextInt(2) == 0) {
							if (!world.isRemote) {
								this.worldObj.playAuxSFX(2005, xCoord, yCoord + 1, zCoord, 6 + this.worldObj.rand.nextInt(4));
								((BlockGrass)block).func_149853_b(world, world.rand, xCoord, yCoord, zCoord);
							}
						} else if(!world.isRemote && BlockListAB.blockFreyrLiana.canBlockStay(world, xCoord, yCoord - 1, zCoord)) {
							Vec3 vec = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
							int distant = (int)vec.squareDistanceTo(Vec3.createVectorHelper(x, y, z));
							yCoord--;
							if (this.worldObj.rand.nextInt(distant + 1) == 0)
								while (yCoord > 0) {
									block = worldObj.getBlock(xCoord, yCoord, zCoord);
									if(block.isAir(worldObj, xCoord, yCoord, zCoord)) {
										if(world.rand.nextInt(4) < 3)
											worldObj.setBlock(xCoord, yCoord, zCoord, BlockListAB.blockFreyrLiana);
										else
											worldObj.setBlock(xCoord, yCoord, zCoord,BlockListAB.blockLuminousFreyrLiana);
										worldObj.playAuxSFX(2001, xCoord, yCoord, zCoord, Block.getIdFromBlock(BlockListAB.blockFreyrLiana));
										yCoord--;
									} else
										break;
								}
						}
					}
			this.setDead();
		}
	}

	boolean isPlant(int x, int y, int z) {
		Block block = this.worldObj.getBlock(x, y, z);
		if (block == Blocks.grass || block == Blocks.leaves || block == Blocks.leaves2 || block instanceof BlockBush && !(block instanceof BlockCrops) && !(block instanceof BlockSapling))
			return false;
		Material mat = block.getMaterial();
		return mat != null
				&& (mat == Material.plants || mat == Material.cactus || mat == Material.grass 
				|| mat == Material.leaves || mat == Material.gourd) && block instanceof IGrowable
				&& ((IGrowable) block).func_149851_a(this.worldObj, x, y, z, this.worldObj.isRemote);
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {}

	protected float getGravityVelocity() {
		return 0.0f;
	}

	public void setDead() {
		if(this.worldObj.isRemote) {
			float m = 0.175F;
			for(int i = 0; i < 32; i++) {
				float mx = (float)(Math.random() - 0.5D) * m;
				float my = (float)(Math.random() - 0.5D) * m;
				float mz = (float)(Math.random() - 0.5D) * m;
				Color color = ClientHelper.getCorporeaRuneColor((int)posX, (int)posY, (int)posZ, 3);
				Botania.proxy.wispFX(worldObj, posX, posY, posZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, (float) (0.2f + (Math.random() * 0.12f)), mx, my, mz, 2.0f);	
			}
		}
		super.setDead();
	}

	public void readFromNBT(NBTTagCompound nbtt) {
		super.readFromNBT(nbtt);
		maxAge = nbtt.getInteger("maxAge");
		age = nbtt.getInteger("age");
	}

	public void writeToNBT(NBTTagCompound nbtt) {
		super.writeToNBT(nbtt);
		nbtt.setInteger("maxAge", maxAge);
		nbtt.setInteger("age", age);
	}
}