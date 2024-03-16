package ab.common.item.relic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import ab.AdvancedBotany;
import ab.client.core.ClientHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemTalismanHiddenRiches extends ItemModRelic {

    protected static List<TileEntityChest> chestList = new ArrayList<TileEntityChest>();
    protected static final ResourceLocation glowTexture = new ResourceLocation("ab:textures/misc/glow3.png");
    protected static final int segmentCount = 11;
    protected static final int maxSegmentCount = 16;

    public ItemTalismanHiddenRiches() {
        super("talismanHiddenRiches");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int pos, boolean equipped) {
        super.onUpdate(stack, world, entity, pos, equipped);
        if (entity != null && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            boolean eqLastTick = wasEquipped(stack);
            if (!equipped && eqLastTick) setEquipped(stack, equipped);
            if (!eqLastTick && equipped) {
                setEquipped(stack, equipped);
                int angles = 360;
                int segAngles = angles / maxSegmentCount;
                float shift = segAngles / 2.0f * segmentCount;
                setRotationBase(stack, getCheckingAngle((EntityLivingBase) entity) - shift);
            }
            if (world.isRemote) {
                if (equipped) {
                    for (int i = 0; i < segmentCount; i++) {
                        TileEntityChest chest = getChestForSegment(i);
                        if (chest != null) {
                            chest.prevLidAngle = chest.lidAngle;
                            float lidAngel = chest.lidAngle;
                            if (i == getOpenChest(stack) && chest.lidAngle < 1.0F) {
                                if (lidAngel == 0.0F) Minecraft.getMinecraft().theWorld.playSound(
                                        player.posX,
                                        player.posY - 0.5f,
                                        player.posZ,
                                        "random.chestopen",
                                        0.5f,
                                        world.rand.nextFloat() * 0.1F + 0.9F,
                                        false);
                                chest.lidAngle = Math.min(1.0f, lidAngel + 0.1F);
                            } else if (i != getOpenChest(stack) && lidAngel > 0.0F) {
                                if ((int) (lidAngel * 10) == 5) Minecraft.getMinecraft().theWorld.playSound(
                                        player.posX,
                                        player.posY - 0.5f,
                                        player.posZ,
                                        "random.chestclosed",
                                        0.5f,
                                        world.rand.nextFloat() * 0.1F + 0.9F,
                                        false);
                                chest.lidAngle = Math.max(0.0f, lidAngel - 0.1F);
                            }
                        }
                    }
                }
            }
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int segment = getSegmentLookedAt(stack, player);
        if (segment == -1) return stack;
        setOpenChest(stack, segment);
        player.openGui(AdvancedBotany.instance, 0, world, 0, 0, 0);
        return stack;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        EntityClientPlayerMP entityClientPlayerMP = (Minecraft.getMinecraft()).thePlayer;
        ItemStack stack = entityClientPlayerMP.getCurrentEquippedItem();
        if (stack != null && stack.getItem() instanceof ItemTalismanHiddenRiches)
            render(stack, (EntityPlayer) entityClientPlayerMP, event.partialTicks);
    }

    @SideOnly(Side.CLIENT)
    public void render(ItemStack stack, EntityPlayer player, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tess = Tessellator.instance;
        Tessellator.renderingWorldRenderer = false;
        GL11.glPushMatrix();
        float alpha = ((float) Math.sin(((ClientTickHandler.ticksInGame + partialTicks) * 0.2F)) * 0.5F + 0.5F) * 0.4F
                + 0.3F;
        double posX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
        double posY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
        double posZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
        GL11.glTranslated(
                posX - RenderManager.renderPosX,
                posY - RenderManager.renderPosY,
                posZ - RenderManager.renderPosZ);
        float base = getRotationBase(stack);
        int angles = 360;
        int segAngles = angles / maxSegmentCount;
        float shift = base - ((segAngles / 2.0f) * segmentCount);
        float u = 1.0F;
        float v = 0.25F;
        float s = 3.2F;
        float m = 0.8F;
        float y = v * 6.0F;
        float y0 = 0.0F;
        int segmentLookedAt = getSegmentLookedAt(stack, (EntityLivingBase) player);
        for (int seg = 0; seg < segmentCount; seg++) {
            ClientHelper.setLightmapTextureCoords();
            float rotationAngle = (seg + 0.5F) * segAngles + shift;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(GL11.GL_CULL_FACE);

            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(s * m, -0.75F, 0.0F);
            double worldTime = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks + seg * 2.75f;
            GL11.glTranslated(0.375f, Math.sin(worldTime / 8.0D) / 20.0D, -0.375F);
            float scale = 0.75F;
            GL11.glScalef(scale, scale, scale);

            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            TileEntityChest chest = getChestForSegment(seg);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(chest, 0.0D, 0.0D, 0.0D, partialTicks);

            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            float a = alpha;
            if (segmentLookedAt == seg) {
                a += 0.3F;
                y0 = -y;
            }
            if (seg % 2 == 0) {
                GL11.glColor4f(0.6F, 0.6F, 0.6F, a);
            } else {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, a);
            }
            ItemTalismanHiddenRiches item = (ItemTalismanHiddenRiches) stack.getItem();
            mc.renderEngine.bindTexture(item.getGlowTexture(stack, seg));
            tess.startDrawingQuads();
            for (int i = 0; i < segAngles; i++) {
                float ang = (i + seg * segAngles) + shift;
                double xp = Math.cos(ang * Math.PI / 180.0D) * s;
                double zp = Math.sin(ang * Math.PI / 180.0D) * s;
                tess.addVertexWithUV(xp * m, y, zp * m, u, v);
                tess.addVertexWithUV(xp, y0, zp, u, 0.0D);
                xp = Math.cos((ang + 1.0F) * Math.PI / 180.0D) * s;
                zp = Math.sin((ang + 1.0F) * Math.PI / 180.0D) * s;
                tess.addVertexWithUV(xp, y0, zp, 0.0D, 0.0D);
                tess.addVertexWithUV(xp * m, y, zp * m, 0.0D, v);
            }
            y0 = 0.0F;
            tess.draw();
            GL11.glPopMatrix();

            ClientHelper.setLightmapTextureCoords();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
    }

    private ResourceLocation getGlowTexture(ItemStack stack, int seg) {
        return glowTexture;
    }

    protected static int getSegmentLookedAt(ItemStack stack, EntityLivingBase player) {
        float yaw = getCheckingAngle(player, getRotationBase(stack));
        int angles = 360;
        int segAngles = angles / maxSegmentCount;
        for (int seg = 0; seg < segmentCount; seg++) {
            float calcAngle = seg * segAngles;
            if (yaw >= calcAngle && yaw < calcAngle + segAngles) return seg;
        }
        return -1;
    }

    protected static float getCheckingAngle(EntityLivingBase player, float base) {
        float yaw = MathHelper.wrapAngleTo180_float(player.rotationYaw) + 90.0f;
        int angles = 360;
        int segAngles = angles / maxSegmentCount;
        float shift = segAngles / 2.0f * segmentCount;
        if (yaw < 0.0f) yaw = 360.0F + yaw;
        yaw -= 360.0F - base;
        float angle = 360.0F - yaw + shift;
        if (angle > 360.0F) angle %= 360.0F;
        return angle;
    }

    protected static float getCheckingAngle(EntityLivingBase player) {
        return getCheckingAngle(player, 0.0F);
    }

    public static void setEquipped(ItemStack stack, boolean equipped) {
        ItemNBTHelper.setBoolean(stack, "equipped", equipped);
    }

    public static boolean wasEquipped(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "equipped", false);
    }

    public static void setRotationBase(ItemStack stack, float rotation) {
        ItemNBTHelper.setFloat(stack, "rotationBase", rotation);
    }

    public static float getRotationBase(ItemStack stack) {
        return ItemNBTHelper.getFloat(stack, "rotationBase", 0.0F);
    }

    public static void setOpenChest(ItemStack stack, int segment) {
        ItemNBTHelper.setInt(stack, "openChest", segment);
    }

    public static int getOpenChest(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "openChest", -1);
    }

    public static TileEntityChest getChestForSegment(int segment) {
        if (chestList.isEmpty()) for (int i = 0; i < segmentCount; i++) chestList.add(new TileEntityChest());
        if (segment < 0 || segment > segmentCount) return null;
        return chestList.get(segment);
    }

    public static void setChestLoot(ItemStack stack, ItemStack[] loot, int segment) {
        NBTTagList nbtList = new NBTTagList();
        int i = -1;
        for (ItemStack item : loot) {
            i++;
            if (item == null) continue;
            NBTTagCompound cmp = new NBTTagCompound();
            cmp.setByte("slot", (byte) i);
            item.copy().writeToNBT(cmp);
            nbtList.appendTag(cmp);
        }
        ItemNBTHelper.setList(stack, "chestLoot" + segment, nbtList);
    }

    public static ItemStack[] getChestLoot(ItemStack stack, int segment) {
        if (segment >= segmentCount) return null;
        ItemStack[] loot = new ItemStack[27];
        NBTTagList nbtList = ItemNBTHelper.getList(stack, "chestLoot" + segment, 10, false);
        for (int i = 0; i < nbtList.tagCount(); i++) {
            NBTTagCompound cmp = nbtList.getCompoundTagAt(i);
            byte slotCount = cmp.getByte("slot");
            if (slotCount >= 0) loot[slotCount] = ItemStack.loadItemStackFromNBT(cmp);
        }
        return loot;
    }
}
