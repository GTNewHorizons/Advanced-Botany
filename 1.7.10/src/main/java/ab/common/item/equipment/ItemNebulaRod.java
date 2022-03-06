package ab.common.item.equipment;

import java.awt.Color;

import ab.common.core.ConfigABHandler;
import ab.common.item.ItemMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class ItemNebulaRod extends ItemMod implements IManaUsingItem {
	
	public ItemNebulaRod() {
		super("nebulaRod");
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setMaxDamage(100);
	}

	public boolean usesMana(ItemStack stack) {
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.getItemDamage() == 0)
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
	    if(!world.isRemote && player instanceof EntityPlayer && player.ticksExisted % ConfigABHandler.nebulaWandCooldownTick == 0 && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer)player, 140, true))
	    	stack.setItemDamage(stack.getItemDamage() - 1); 
	}
	
	public void onUsingTick(ItemStack stack, EntityPlayer p, int time) {
		time = this.getMaxItemUseDuration(stack) - time;
		boolean isFinish = time > 80;
		if(time > 110 && !p.isSneaking()) {
			if(!p.capabilities.isCreativeMode)
				stack.setItemDamage(100);
			p.stopUsingItem();
			if(!p.worldObj.isRemote) {
				Vec3 vec3 = p.getLook(1.0F).normalize();
				MathHelper.floor_double(time);
				int posX = MathHelper.floor_double(p.posX + vec3.xCoord * 256);
				int posY = MathHelper.floor_double(p.posY);
				int posZ = MathHelper.floor_double(p.posZ + vec3.zCoord * 256);  
				ChunkCoordinates chunkcoordinates = new ChunkCoordinates(posX, posY, posZ);
				int topY = getTopBlock(p.worldObj, posX, posZ);
				chunkcoordinates.posY = topY == -1 ? posY : topY;
				((EntityPlayerMP)p).playerNetServerHandler.setPlayerLocation(chunkcoordinates.posX + 0.5D, chunkcoordinates.posY + 1.6D, chunkcoordinates.posZ + 0.5D, p.rotationYaw, p.rotationPitch); 
			}
		}
		if(p.worldObj.isRemote) {
			int ticks = Math.min(100, time);
			int totalSpiritCount = (int)Math.max(3, ticks / 100.0f * 18);
			double tickIncrement = 360.0D / totalSpiritCount;
			int speed = 8;
			double wticks = (ticks * speed) - tickIncrement;
			double r = Math.sin((ticks) / 100.0D) * Math.max(0.75f, 1.4D * ticks / 100.0f);
			Vec3 look = p.getLookVec();
			float yawOffset = Minecraft.getMinecraft().thePlayer.getDisplayName() == p.getDisplayName() ? 0.0f : 1.62f;
			Vector3 l = new Vector3(look.xCoord, look.yCoord + yawOffset, look.zCoord);
			Vector3 player = Vector3.fromEntity(p).add(0.0f, 0.0f, 0.0f);
			Vector3 v3 = new Vector3();	
			for(int i = 0; i < totalSpiritCount; i++) {	
				float size = Math.max(0.215f, ticks / 100.0f);
				v3.set(Math.sin(wticks * Math.PI / 180.0D) / 1.825f * r, Math.cos(wticks * Math.PI / 180.0D) * r, 0.8f);
				setRotation(p.rotationPitch, 1.0f, 0.0f, 0.0f, v3);
				setRotation(-p.rotationYaw, 0.0f, 1.0f, 0.0f, v3);
				v3.add(l.multiply(1.0f)).add(player);
				wticks += tickIncrement;
				int color = p.worldObj.rand.nextBoolean() ? 0x931fec : 0x4b1682;
				float hsb[] = Color.RGBtoHSB(color & 255, color >> 8 & 255, color >> 16 & 255, null);
				int color1 = Color.HSBtoRGB(hsb[0], hsb[1], ticks / 100.0f);
				float[] colorsfx = { (color1 & 255) / 255.0f, (color1 >> 8 & 255) / 255.0f, (color1 >> 16 & 255) / 255.0f };
				float motionSpeed = 0.25f * Math.min(1.0f, (time - 80) / 30.0f);
				Botania.proxy.wispFX(p.worldObj, v3.x, v3.y, v3.z, colorsfx[0], colorsfx[1], colorsfx[2], 0.3F * size, isFinish ? (float)(look.xCoord * -1) * motionSpeed : 0.0f, isFinish ? (float)(look.yCoord * -1) * motionSpeed : 0.0f, isFinish ? (float)(look.zCoord * -1) * motionSpeed : 0.0f, 0.3F);
				Botania.proxy.wispFX(p.worldObj, v3.x, v3.y, v3.z, colorsfx[0], colorsfx[1], colorsfx[2], (float)(Math.random() * 0.1F + 0.05F) * size, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, 0.4F);
			}
		}
	}
	
	public Vector3 setRotation(float angel, float vX, float vY, float vZ, Vector3 v3) {
		Vector3 rVec = new Vector3(vX, vY, vZ);
		Vector3 rVec1 = v3.copy().normalize();
		double rAngel = Math.toRadians(angel) * 0.5f;
		double sin = Math.sin(rAngel);
		double x = rVec.x * sin;
		double y = rVec.y * sin;
		double z = rVec.z * sin;
		rAngel = Math.cos(rAngel);
		double d = -x * rVec1.x - y * rVec1.y - z * rVec1.z;
		double d1 = rAngel * rVec1.x + y * rVec1.z - z * rVec1.y;
		double d2 = rAngel * rVec1.y - x * rVec1.z + z * rVec1.x;
		double d3 = rAngel * rVec1.z + x * rVec1.y - y * rVec1.x;
		v3.x = d1 * rAngel - d * x - d2 * z + d3 * y;
		v3.y = d2 * rAngel - d * y + d1 * z - d3 * x;
		v3.z = d3 * rAngel - d * z - d1 * y + d2 * x;
		return v3;
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	public boolean isFull3D() {
		return true;
	}
	
	public int getTopBlock(World world, int posX, int posZ) {
        Chunk chunk = world.getChunkFromBlockCoords(posX, posZ);
        int x = posX;
        int z = posZ;
        int k = chunk.getTopFilledSegment() + 15;
        posX &= 15;
        for(posZ &= 15; k > 0; --k) {
            Block block = chunk.getBlock(posX, k, posZ);
            if(!block.isAir(world, x, k, z))
                return k + 1;
        }
        return -1;
    }
}