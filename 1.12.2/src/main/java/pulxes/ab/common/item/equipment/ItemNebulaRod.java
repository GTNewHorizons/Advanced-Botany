package pulxes.ab.common.item.equipment;

import java.awt.Color;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import pulxes.ab.client.core.handler.PlayerItemUsingSound.ClientSoundHandler;
import pulxes.ab.client.core.helper.ClientHelper;
import pulxes.ab.common.core.handler.AdvBotanyConfigHandler;
import pulxes.ab.common.item.ItemMod;
import pulxes.ab.common.lib.LibItemNames;
import pulxes.ab.common.lib.register.SoundListAB;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class ItemNebulaRod extends ItemMod implements IManaUsingItem {

	public ItemNebulaRod() {
		super(LibItemNames.NEBULA_ROD);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setMaxDamage(100);
	}
	
	@Nonnull
	public void onUpdate(@Nonnull ItemStack stack, World world, @Nonnull Entity player, int par4, boolean par5) {
	    if(!world.isRemote && player instanceof EntityPlayer && player.ticksExisted % 120 == 0 && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer)player, 140, true))
	    	stack.setItemDamage(stack.getItemDamage() - 1); 
	}
	
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		if(player.getHeldItem(hand).getItemDamage() == 0 && checkWorld(world.provider.getDimensionType().getName())) {
			player.setActiveHand(hand);
			return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		} else
			return super.onItemRightClick(world, player, hand);
	}
	
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int time) {
		if(!(living instanceof EntityPlayer))
			return;
		EntityPlayer p = (EntityPlayer)living;
		time = this.getMaxItemUseDuration(stack) - time;
		if(time > 110 && !p.isSneaking()) {
			if(!p.world.isRemote) {
				BlockPos blockPos = getTopBlock(p.world, p);
				if(blockPos == null) {
					p.stopActiveHand();
					p.sendMessage((new TextComponentTranslation("ab.nebulaRod.notTeleporting", new Object[] {})).setStyle((new Style()).setColor(TextFormatting.DARK_PURPLE)));
					return;
				}
				EnderTeleportEvent event = new EnderTeleportEvent((EntityPlayerMP)p, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, 0.0F);
				MinecraftForge.EVENT_BUS.post(event);
				if(event.isCanceled()) {
					p.stopActiveHand();
					p.sendMessage((new TextComponentTranslation("abmisc.nebulaRod.notTeleportingEvent", new Object[] {})).setStyle((new Style()).setColor(TextFormatting.DARK_PURPLE)));
					return;
				}
				((EntityPlayerMP)p).connection.setPlayerLocation(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, p.rotationYaw, p.rotationPitch); 
				p.world.playSound(null, p.posX, p.posY, p.posZ, SoundListAB.nebulaRod, SoundCategory.PLAYERS, 1.2F, 1.2F);
			} 
			if(!p.capabilities.isCreativeMode)
				stack.setItemDamage(100);
			p.stopActiveHand();
		}
		spawnPortalParticle(p.world, p, time, p.world.rand.nextBoolean() ? 0x931fec : 0x4b1682);
	}
	
	public void spawnPortalParticle(World world, EntityPlayer p, int time, int color) {
		if(world.isRemote) {
			ClientSoundHandler.playSound(p, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.PLAYERS, 1.2F, 1.0F, 40);
			boolean isFinish = time > 80;
			int ticks = Math.min(100, time);
			int totalSpiritCount = (int)Math.max(3, ticks / 100.0f * 26);
			double tickIncrement = 360.0D / totalSpiritCount;
			int speed = 9;
			double wticks = (ticks * speed) - tickIncrement;
			double r = Math.sin((ticks) / 100.0D) * Math.max(0.75f, 1.4D * ticks / 100.0f);
			Vec3d look = p.getLookVec();
			float yawOffset = 1.62f - (p.isSneaking() ? 0.1f : 0.0f);
			Vector3 l = new Vector3(look.x, look.y + yawOffset, look.z);
			Vector3 player = Vector3.fromEntity(p);
			for(int i = 0; i < totalSpiritCount; i++) {
				float size = Math.max(0.215f, ticks / 100.0f);
				Vector3 v3 = new Vector3(Math.sin(wticks * Math.PI / 180.0D) / 1.825f * r, Math.cos(wticks * Math.PI / 180.0D) * r, 0.6f);
				v3 = ClientHelper.setRotation(p.rotationPitch, 1.0f, 0.0f, 0.0f, v3);
				v3 = ClientHelper.setRotation(-p.rotationYaw, 0.0f, 1.0f, 0.0f, v3);
				v3 = v3.add(l.multiply(1.0f)).add(player);
				wticks += tickIncrement;
				float hsb[] = Color.RGBtoHSB(color & 255, color >> 8 & 255, color >> 16 & 255, null);
				int color1 = Color.HSBtoRGB(hsb[0], hsb[1], ticks / 100.0f);
				float[] colorsfx = { (color1 & 255) / 255.0f, (color1 >> 8 & 255) / 255.0f, (color1 >> 16 & 255) / 255.0f };
				float motionSpeed = 0.25f * Math.min(1.0f, (time - 80) / 30.0f);
				Botania.proxy.wispFX(v3.x, v3.y, v3.z, colorsfx[0], colorsfx[1], colorsfx[2], 0.3F * size, isFinish ? (float)(look.x * -1) * motionSpeed : 0.0f, isFinish ? (float)(look.y * -1) * motionSpeed : 0.0f, isFinish ? (float)(look.z * -1) * motionSpeed : 0.0f, 0.3F);
				Botania.proxy.wispFX(v3.x, v3.y, v3.z, colorsfx[0], colorsfx[1], colorsfx[2], (float)(Math.random() * 0.1F + 0.05F) * size, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, (float)(Math.random() - 0.5D) * 0.05F, 0.4F);
			}
		}
	}
	
	public BlockPos getTopBlock(World world, EntityPlayer player) {
		Vec3d vec3 = player.getLook(1.0F).normalize();
		int limitXZ = AdvBotanyConfigHandler.limitXZCoords;
		for(int nextPos = 256; nextPos > 0 && nextPos > 8; nextPos--) {
			int nPosX = MathHelper.floor(player.posX + vec3.x * nextPos);
			int nPosZ = MathHelper.floor(player.posZ + vec3.z * nextPos);
			nPosX = Math.min(Math.max(nPosX, -(limitXZ - 1)), limitXZ - 1);
			nPosZ = Math.min(Math.max(nPosZ, -(limitXZ - 1)), limitXZ - 1);
			Chunk chunk = world.getChunkFromChunkCoords(nPosX, nPosZ);
	        for(int k = chunk.getTopFilledSegment() + 15; k > 0; --k) {
	        	IBlockState state = world.getBlockState(new BlockPos(nPosX, k, nPosZ));
	            boolean hasTopAir = world.isAirBlock(new BlockPos(nPosX, k + 1, nPosZ)) && world.isAirBlock(new BlockPos(nPosX, k + 2, nPosZ));
	            if(state.getMaterial() != Material.AIR && state.getBlock() != Blocks.BEDROCK && hasTopAir)
	            	return new BlockPos(nPosX, k + 1, nPosZ);
	        }
		}
        return null;
    }
	
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	private boolean checkWorld(String name) {
		for(String str : AdvBotanyConfigHandler.lockWorldNameNebulaRod)
			if(str.equals(name))
				return false;
		return true;
	}
	
	public boolean usesMana(ItemStack stack) {
		return true;
	}
}
