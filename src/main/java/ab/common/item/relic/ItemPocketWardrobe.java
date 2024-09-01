package ab.common.item.relic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import org.lwjgl.opengl.GL11;

import ab.client.core.ClientHelper;
import ab.common.entity.EntityAnonymousSteve;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemPocketWardrobe extends ItemModRelic {

    protected static final ResourceLocation glowPriorityTexture = new ResourceLocation("ab:textures/misc/glow2.png");
    protected static final ResourceLocation glowTexture = new ResourceLocation("ab:textures/misc/glow1.png");
    protected static final int segmentCount = 5;
    protected static final int maxSegmentCount = 12;

    public ItemPocketWardrobe() {
        super("autoPocketWardrobe");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onUpdate(ItemStack pocketWardrobe, World world, Entity entity, int pos, boolean equipped) {
        super.onUpdate(pocketWardrobe, world, entity, pos, equipped);
        boolean eqLastTick = wasEquipped(pocketWardrobe);
        if (!equipped && eqLastTick) setEquipped(pocketWardrobe, equipped);
        if (!eqLastTick && equipped && entity instanceof EntityLivingBase) {
            setEquipped(pocketWardrobe, equipped);
            int angles = 360;
            int segAngles = angles / maxSegmentCount;
            float shift = segAngles / 2.0f * segmentCount;
            setRotationBase(pocketWardrobe, getCheckingAngle((EntityLivingBase) entity) - shift);
        }
        int tick = getFightingTick(pocketWardrobe);
        if (tick > 0) setFightingTick(pocketWardrobe, tick - 1);
        else if (!world.isRemote && tick == 0
                && getFightingMode(pocketWardrobe)
                && entity != null
                && entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    setFightingMode(pocketWardrobe, false);
                    swapArmorSet(pocketWardrobe, player, getPrioritySet(pocketWardrobe));
                }
    }

    public void setArmorSet(ItemStack pocketWardrobe, ItemStack[] armorSet, int segment) {
        NBTTagList nbtList = new NBTTagList();
        int i = -1;
        for (ItemStack armor : armorSet) {
            i++;
            if (armor == null) continue;
            NBTTagCompound cmp = new NBTTagCompound();
            cmp.setByte("slot", (byte) i);
            armor.copy().writeToNBT(cmp);
            nbtList.appendTag(cmp);
        }
        ItemNBTHelper.setList(pocketWardrobe, "armorSet" + segment, nbtList);
    }

    public static ItemStack[] getArmorSet(ItemStack pocketWardrobe, int segment) {
        if (segment >= segmentCount) return null;
        ItemStack[] armorSet = new ItemStack[4];
        NBTTagList nbtList = ItemNBTHelper.getList(pocketWardrobe, "armorSet" + segment, 10, false);
        for (int i = 0; i < nbtList.tagCount(); i++) {
            NBTTagCompound cmp = nbtList.getCompoundTagAt(i);
            byte slotCount = cmp.getByte("slot");
            if (slotCount >= 0 && slotCount <= segmentCount) armorSet[slotCount] = ItemStack.loadItemStackFromNBT(cmp);
        }
        return armorSet;
    }

    public ItemStack onItemRightClick(ItemStack pocketWardrobe, World world, EntityPlayer player) {
        int segment = getSegmentLookedAt(pocketWardrobe, player);
        if (segment == -1) return pocketWardrobe;
        if (player.isSneaking()) {
            setPrioritySet(pocketWardrobe, segment);
        } else {
            swapArmorSet(pocketWardrobe, player, segment);
        }
        return pocketWardrobe;
    }

    public void swapArmorSet(ItemStack stack, EntityPlayer player, int segment) {
        ItemStack[] playerSet = player.inventory.armorInventory;
        player.inventory.armorInventory = getArmorSet(stack, segment);
        setArmorSet(stack, playerSet, segment);
        if (!player.worldObj.isRemote) player.worldObj.playSoundAtEntity(player, "ab:lokiCubeArmor", 0.3f, 0.86f);
    }

    @SubscribeEvent
    public void onPlayerAttack(LivingAttackEvent event) {
        if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (player.capabilities.isCreativeMode) return;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof ItemPocketWardrobe) {
                    ItemPocketWardrobe item = (ItemPocketWardrobe) stack.getItem();
                    if (!item.getFightingMode(stack)) {
                        int armorPriorirySlot = item.getPrioritySet(stack);
                        ItemStack[] armorSet = item.getArmorSet(stack, armorPriorirySlot);
                        boolean hasArmor = false;
                        for (int j = 0; j < armorSet.length; j++) if (armorSet[j] != null) {
                            hasArmor = true;
                            break;
                        }
                        if (hasArmor) {
                            item.setFightingTick(stack, 32);
                            item.setFightingMode(stack, true);
                            item.swapArmorSet(stack, player, armorPriorirySlot);
                            return;
                        }
                    } else {
                        item.setFightingTick(stack, 32);
                        return;
                    }
                }
            }
        }
    }

    public ResourceLocation getGlowTexture(ItemStack stack, int segment) {
        return getPrioritySet(stack) == segment ? glowPriorityTexture : glowTexture;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        EntityClientPlayerMP entityClientPlayerMP = (Minecraft.getMinecraft()).thePlayer;
        ItemStack stack = entityClientPlayerMP.getCurrentEquippedItem();
        if (stack != null && stack.getItem() instanceof ItemPocketWardrobe)
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
        float s = 3.6F;
        float m = 0.8F;
        float y = v * 6.0F;
        float y0 = 0.0F;
        int segmentLookedAt = getSegmentLookedAt(stack, (EntityLivingBase) player);
        for (int seg = 0; seg < segmentCount; seg++) {
            ClientHelper.setLightmapTextureCoords();
            float rotationAngle = (seg + 0.5F) * segAngles + shift;
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glDisable(GL11.GL_CULL_FACE);
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
            ItemPocketWardrobe item = (ItemPocketWardrobe) stack.getItem();
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
            AbstractClientPlayer steve = new EntityAnonymousSteve(player.worldObj);
            steve.inventory.armorInventory = getArmorSet(stack, seg);
            boolean hasArmor = false;
            for (int l = 0; l < steve.inventory.armorInventory.length; l++)
                if (steve.inventory.armorItemInSlot(l) != null) {
                    hasArmor = true;
                    break;
                }
            if (hasArmor) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
                double worldTime = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks + seg * 2.75f;
                GL11.glTranslated(s * m, -0.75F + Math.sin(worldTime / 12.0D) / 26.0D, 0.0F);
                float scale = 0.6F;
                GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(0.0f, 0.8125f, 0.0f);
                GL11.glScalef(scale, scale, scale);
                steve.setPositionAndRotation(player.posX, player.posY, player.posZ, 0.0f, 10.0f);
                RenderManager.instance.renderEntityWithPosYaw(steve, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
                Minecraft.getMinecraft().entityRenderer.disableLightmap(partialTicks);
                GL11.glPopMatrix();
            }
            ClientHelper.setLightmapTextureCoords();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
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

    public static void setFightingMode(ItemStack stack, boolean mode) {
        ItemNBTHelper.setBoolean(stack, "fightingMode", mode);
    }

    public static boolean getFightingMode(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "fightingMode", false);
    }

    public static void setFightingTick(ItemStack stack, int tick) {
        ItemNBTHelper.setInt(stack, "fightingTick", tick);
    }

    public static int getFightingTick(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "fightingTick", 0);
    }

    public static void setPrioritySet(ItemStack stack, int segment) {
        ItemNBTHelper.setInt(stack, "prioritySet", segment);
    }

    public static int getPrioritySet(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "prioritySet", 2);
    }
}
