package ab.common.item.equipment;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import ab.api.AdvancedBotanyAPI;
import ab.client.core.ClientHelper;
import ab.client.core.handler.PlayerItemUsingSound.ClientSoundHandler;
import ab.common.core.handler.ConfigABHandler;
import ab.common.item.ItemMod;
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
        if (stack.getItemDamage() == 0 && checkWorld(world.provider.getDimensionName()))
            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }

    private boolean checkWorld(String name) {
        for (String str : ConfigABHandler.lockWorldNameNebulaRod) if (str.equals(name)) return false;
        return true;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if (!world.isRemote && player instanceof EntityPlayer
                && player.ticksExisted % ConfigABHandler.nebulaWandCooldownTick == 0
                && stack.getItemDamage() > 0
                && ManaItemHandler
                        .requestManaExactForTool(stack, (EntityPlayer) player, ConfigABHandler.nebulaRodManaCost, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return AdvancedBotanyAPI.rarityNebula;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer p, int time) {
        time = this.getMaxItemUseDuration(stack) - time;
        if (time > 110 && !p.isSneaking()) {
            if (!p.worldObj.isRemote) {
                ChunkCoordinates chunkcoordinates = getTopBlock(p.worldObj, p);
                if (chunkcoordinates == null) {
                    p.stopUsingItem();
                    p.addChatMessage(
                            (new ChatComponentTranslation("ab.nebulaRod.notTeleporting", new Object[0]))
                                    .setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.DARK_PURPLE)));
                    return;
                }
                EnderTeleportEvent event = new EnderTeleportEvent(
                        (EntityPlayerMP) p,
                        chunkcoordinates.posX + 0.5D,
                        chunkcoordinates.posY + 0.5D,
                        chunkcoordinates.posZ + 0.5D,
                        0.0F);
                MinecraftForge.EVENT_BUS.post(event);
                if (event.isCanceled()) {
                    p.stopUsingItem();
                    p.addChatMessage(
                            (new ChatComponentTranslation("ab.nebulaRod.notTeleportingEvent", new Object[0]))
                                    .setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.DARK_PURPLE)));
                    return;
                }
                ((EntityPlayerMP) p).playerNetServerHandler.setPlayerLocation(
                        chunkcoordinates.posX + 0.5D,
                        chunkcoordinates.posY + 0.5D,
                        chunkcoordinates.posZ + 0.5D,
                        p.rotationYaw,
                        p.rotationPitch);
                p.worldObj.playSoundAtEntity(p, "ab:nebulaRod", 1.2F, 1.2F);
            }
            if (!p.capabilities.isCreativeMode) stack.setItemDamage(100);
            p.stopUsingItem();
        }
        spawnPortalParticle(p.worldObj, p, time, p.worldObj.rand.nextBoolean() ? 0x931fec : 0x4b1682, 1.0f);
    }

    public void spawnPortalParticle(World world, EntityPlayer p, int time, int color, float particleTime) {
        if (world.isRemote) {
            ClientSoundHandler.playSound(p, "portal.portal", 1.2F, 1.0F, 40);
            boolean isFinish = time > 80;
            int ticks = Math.min(100, time);
            int totalSpiritCount = (int) Math.max(3, ticks / 100.0f * 18);
            double tickIncrement = 360.0D / totalSpiritCount;
            int speed = 8;
            double wticks = (ticks * speed) - tickIncrement;
            double r = Math.sin((ticks) / 100.0D) * Math.max(0.75f, 1.4D * ticks / 100.0f);
            Vec3 look = p.getLookVec();
            float yawOffset = Minecraft.getMinecraft().thePlayer.getDisplayName() == p.getDisplayName() ? 0.0f : 1.62f;
            Vector3 l = new Vector3(look.xCoord, look.yCoord + yawOffset, look.zCoord);
            Vector3 player = Vector3.fromEntity(p).add(0.0f, 0.0f, 0.0f);
            Vector3 v3 = new Vector3();
            for (int i = 0; i < totalSpiritCount; i++) {
                float size = Math.max(0.215f, ticks / 100.0f);
                v3.set(Math.sin(wticks * Math.PI / 180.0D) / 1.825f * r, Math.cos(wticks * Math.PI / 180.0D) * r, 0.8f);
                ClientHelper.setRotation(p.rotationPitch, 1.0f, 0.0f, 0.0f, v3);
                ClientHelper.setRotation(-p.rotationYaw, 0.0f, 1.0f, 0.0f, v3);
                v3.add(l.multiply(1.0f)).add(player);
                wticks += tickIncrement;
                float hsb[] = Color.RGBtoHSB(color & 255, color >> 8 & 255, color >> 16 & 255, null);
                int color1 = Color.HSBtoRGB(hsb[0], hsb[1], ticks / 100.0f);
                float[] colorsfx = { (color1 & 255) / 255.0f, (color1 >> 8 & 255) / 255.0f,
                        (color1 >> 16 & 255) / 255.0f };
                float motionSpeed = 0.25f * Math.min(1.0f, (time - 80) / 30.0f);
                Botania.proxy.wispFX(
                        world,
                        v3.x,
                        v3.y,
                        v3.z,
                        colorsfx[0],
                        colorsfx[1],
                        colorsfx[2],
                        0.3F * size,
                        isFinish ? (float) (look.xCoord * -1) * motionSpeed : 0.0f,
                        isFinish ? (float) (look.yCoord * -1) * motionSpeed : 0.0f,
                        isFinish ? (float) (look.zCoord * -1) * motionSpeed : 0.0f,
                        0.3F * particleTime);
                Botania.proxy.wispFX(
                        world,
                        v3.x,
                        v3.y,
                        v3.z,
                        colorsfx[0],
                        colorsfx[1],
                        colorsfx[2],
                        (float) (Math.random() * 0.1F + 0.05F) * size,
                        (float) (Math.random() - 0.5D) * 0.05F,
                        (float) (Math.random() - 0.5D) * 0.05F,
                        (float) (Math.random() - 0.5D) * 0.05F,
                        0.4F * particleTime);
            }
        }
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    public boolean isFull3D() {
        return true;
    }

    public ChunkCoordinates getTopBlock(World world, EntityPlayer player) {
        Vec3 vec3 = player.getLook(1.0F).normalize();
        int limitXZ = ConfigABHandler.limitXZCoords;
        for (int nextPos = 256; nextPos > 0 && nextPos > 8; nextPos--) {
            int nPosX = MathHelper.floor_double(player.posX + vec3.xCoord * nextPos);
            int nPosZ = MathHelper.floor_double(player.posZ + vec3.zCoord * nextPos);
            nPosX = Math.min(Math.max(nPosX, -(limitXZ - 1)), limitXZ - 1);
            nPosZ = Math.min(Math.max(nPosZ, -(limitXZ - 1)), limitXZ - 1);
            Chunk chunk = world.getChunkFromBlockCoords(nPosX, nPosZ);
            int x = nPosX & 15;
            int z = nPosZ & 15;
            for (int k = chunk.getTopFilledSegment() + 15; k > 0; --k) {
                Block block = chunk.getBlock(x, k, z);
                boolean hasTopAir = chunk.getBlock(x, k + 1, z).getMaterial() == Material.air
                        && chunk.getBlock(x, k + 2, z).getMaterial() == Material.air;
                if (!block.isAir(world, x, k, z) && block != Blocks.bedrock && hasTopAir)
                    return new ChunkCoordinates(nPosX, k + 1, nPosZ);
            }
        }
        return null;
    }
}
