package ab.common.item.equipment;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import ab.common.item.ItemMod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IBlockProvider;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.core.helper.InventoryHelper;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ItemBlackHoleTalisman;

public class ItemBlackHalo extends ItemMod implements IBlockProvider {

    public static final ResourceLocation glowTexture = new ResourceLocation("ab:textures/misc/glow.png");

    public ItemBlackHalo() {
        super("blackHalo");
        this.setMaxStackSize(1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public ItemStack onItemRightClick(ItemStack halo, World world, EntityPlayer player) {
        int segment = getSegmentLookedAt(halo, player);
        if (this.getItemForSlot(halo, segment) != null) {
            ItemStack stack = this.getItemForSlot(halo, segment).copy();
            if (player.isSneaking()) {
                if (!player.inventory.addItemStackToInventory(stack))
                    player.dropPlayerItemWithRandomChoice(stack, false);
                this.setItemSlot(halo, null, segment);
                return halo;
            }
            stack.setItemDamage(stack.getItemDamage() == 0 ? 1 : 0);
            if (!world.isRemote) world.playSoundAtEntity(player, "random.orb", 0.3F, 0.1F);
            this.setItemSlot(halo, stack, segment);
            return halo;
        }
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBlackHoleTalisman) {
                boolean hasBlock = !Block
                        .isEqualTo(((ItemBlackHoleTalisman) stack.getItem()).getBlock(stack), Blocks.air)
                        && ((ItemBlackHoleTalisman) stack.getItem()).getBlock(stack) != null;
                if (hasBlock) {
                    if (this.getItemForSlot(halo, segment) != null) return halo;
                    this.setItemSlot(halo, stack, segment);
                    if (player.inventory.getStackInSlot(i).stackSize > 1)
                        player.inventory.getStackInSlot(i).stackSize = -1;
                    else player.inventory.setInventorySlotContents(i, null);
                    return halo;
                }
            }
        }
        return halo;
    }

    public void onUpdate(ItemStack halo, World world, Entity entity, int pos, boolean equipped) {
        boolean eqLastTick = wasEquipped(halo);
        if (!equipped && eqLastTick) setEquipped(halo, equipped);
        if (!eqLastTick && equipped && entity instanceof EntityLivingBase) {
            setEquipped(halo, equipped);
            int angles = 360;
            int segAngles = angles / 12;
            float shift = (segAngles / 2);
            setRotationBase(halo, getCheckingAngle((EntityLivingBase) entity) - shift);
        }
        if (world.isRemote) return;
        else if (!(entity.ticksExisted % 10 == 0)) return;
        for (int i = 0; i < 12; i++) {
            ItemStack stack = this.getItemForSlot(halo, i);
            if (stack != null) {
                ((ItemBlackHoleTalisman) stack.getItem()).onUpdate(stack, world, entity, pos, equipped);
            }
        }
    }

    public boolean onItemUse(ItemStack halo, EntityPlayer player, World world, int par4, int par5, int par6, int par7,
            float par8, float par9, float par10) {
        for (int i = 0; i < 12; i++) {
            ItemStack tal = this.getItemForSlot(halo, i);
            if (tal == null) continue;
            ItemBlackHoleTalisman talisman = (ItemBlackHoleTalisman) tal.getItem();
            Block bBlock = talisman.getBlock(tal);
            int bmeta = talisman.getBlockMeta(tal);
            TileEntity tile = world.getTileEntity(par4, par5, par6);
            if (tile != null && tile instanceof IInventory) {
                IInventory inv = (IInventory) tile;
                int[] slots = (inv instanceof ISidedInventory)
                        ? ((ISidedInventory) inv).getAccessibleSlotsFromSide(par7)
                        : InventoryHelper.buildSlotsForLinearInventory(inv);
                for (int slot : slots) {
                    ItemStack stackInSlot = inv.getStackInSlot(slot);
                    if (stackInSlot == null) {
                        ItemStack stack = new ItemStack(bBlock, 1, bmeta);
                        int maxSize = stack.getMaxStackSize();
                        stack.stackSize = talisman.remove(tal, maxSize);
                        if (stack.stackSize != 0 && inv.isItemValidForSlot(slot, stack)
                                && (!(inv instanceof ISidedInventory)
                                        || ((ISidedInventory) inv).canInsertItem(slot, stack, par7))) {
                            inv.setInventorySlotContents(slot, stack);
                            inv.markDirty();
                        }
                    } else if (stackInSlot.getItem() == Item.getItemFromBlock(bBlock)
                            && stackInSlot.getItemDamage() == bmeta) {
                                int maxSize = stackInSlot.getMaxStackSize();
                                int missing = maxSize - stackInSlot.stackSize;
                                if (inv.isItemValidForSlot(slot, stackInSlot) && (!(inv instanceof ISidedInventory)
                                        || ((ISidedInventory) inv).canInsertItem(slot, stackInSlot, par7))) {
                                    stackInSlot.stackSize += talisman.remove(tal, missing);
                                    inv.markDirty();
                                }
                            }
                }
            }
        }
        return true;
    }

    public void setItemSlot(ItemStack halo, ItemStack stack, int slot) {
        NBTTagCompound cmp = new NBTTagCompound();
        if (stack != null) stack.copy().writeToNBT(cmp);
        ItemNBTHelper.setCompound(halo, "itemSlot" + slot, cmp);
    }

    public static ItemStack getItemForSlot(ItemStack halo, int slot) {
        if (slot >= 12) return null;
        NBTTagCompound cmp = ItemNBTHelper.getCompound(halo, "itemSlot" + slot, true);
        if (cmp != null) return ItemStack.loadItemStackFromNBT(cmp);
        return null;
    }

    @SideOnly(Side.CLIENT)
    public static void renderHUD(ScaledResolution resolution, EntityPlayer player, ItemStack halo) {
        Minecraft mc = Minecraft.getMinecraft();
        int slot = getSegmentLookedAt(halo, (EntityLivingBase) player);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        ItemStack stack = getItemForSlot(halo, slot);
        if (stack != null) {
            String name = stack.getDisplayName();
            String count = "" + ((ItemBlackHoleTalisman) stack.getItem()).getBlockCount(stack);
            int l = mc.fontRenderer.getStringWidth(name);
            int x = resolution.getScaledWidth() / 2 - l / 2;
            int y = resolution.getScaledHeight() / 2 - 65;
            Gui.drawRect(x - 6, y - 6, x + l + 6, y + 43, 570425344);
            Gui.drawRect(x - 4, y - 4, x + l + 4, y + 41, 570425344);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(32826);
            RenderItem.getInstance().renderItemAndEffectIntoGUI(
                    mc.fontRenderer,
                    mc.renderEngine,
                    stack,
                    resolution.getScaledWidth() / 2 - 8,
                    resolution.getScaledHeight() / 2 - 52);
            RenderHelper.disableStandardItemLighting();
            mc.fontRenderer.drawStringWithShadow(name, x, y, 16777215);
            mc.fontRenderer.drawStringWithShadow(
                    count,
                    resolution.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(count) / 2,
                    y + 32,
                    16777215);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        EntityClientPlayerMP entityClientPlayerMP = (Minecraft.getMinecraft()).thePlayer;
        ItemStack stack = entityClientPlayerMP.getCurrentEquippedItem();
        if (stack != null && stack.getItem() instanceof ItemBlackHalo)
            render(stack, (EntityPlayer) entityClientPlayerMP, event.partialTicks);
    }

    @SideOnly(Side.CLIENT)
    public void render(ItemStack stack, EntityPlayer player, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tess = Tessellator.instance;
        Tessellator.renderingWorldRenderer = false;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
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
        int segAngles = angles / 12;
        float shift = base - (segAngles / 2);
        float u = 1.0F;
        float v = 0.25F;
        float s = 3.0F;
        float m = 0.8F;
        float y = v * s * 2.0F;
        float y0 = 0.0F;
        int segmentLookedAt = getSegmentLookedAt(stack, (EntityLivingBase) player);
        for (int seg = 0; seg < 12; seg++) {
            boolean inside = false;
            float rotationAngle = (seg + 0.5F) * segAngles + shift;
            GL11.glPushMatrix();
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
            double worldTime = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks + seg * 2.75f;
            GL11.glTranslated(s * m, -0.75F + Math.sin(worldTime / 12.0D) / 30.0D, 0.0F);
            if (segmentLookedAt == seg) inside = true;
            ItemStack slotStack = getItemForSlot(stack, seg);
            if (slotStack != null) {
                ItemBlackHoleTalisman talisman = (ItemBlackHoleTalisman) slotStack.getItem();
                mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                float scale = 0.6F;
                GL11.glScalef(scale, scale, scale);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.6F, 0.0F);
                RenderBlocks.getInstance()
                        .renderBlockAsItem(talisman.getBlock(slotStack), talisman.getBlockMeta(slotStack), 1.0F);
            }
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            float a = alpha;
            if (inside) {
                a += 0.3F;
                y0 = -y;
            }
            if (seg % 2 == 0) {
                GL11.glColor4f(0.6F, 0.6F, 0.6F, a);
            } else {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, a);
            }
            GL11.glDisable(2884);
            mc.renderEngine.bindTexture(glowTexture);
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
            GL11.glEnable(2884);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    private static int getSegmentLookedAt(ItemStack stack, EntityLivingBase player) {
        getRotationBase(stack);
        float yaw = getCheckingAngle(player, getRotationBase(stack));
        int angles = 360;
        int segAngles = angles / 12;
        for (int seg = 0; seg < 12; seg++) {
            float calcAngle = seg * segAngles;
            if (yaw >= calcAngle && yaw < calcAngle + segAngles) return seg;
        }
        return -1;
    }

    public static void setRotationBase(ItemStack stack, float rotation) {
        ItemNBTHelper.setFloat(stack, "rotationBase", rotation);
    }

    public static float getRotationBase(ItemStack stack) {
        return ItemNBTHelper.getFloat(stack, "rotationBase", 0.0F);
    }

    private static float getCheckingAngle(EntityLivingBase player, float base) {
        float yaw = MathHelper.wrapAngleTo180_float(player.rotationYaw) + 90.0F;
        int angles = 360;
        int segAngles = angles / 12;
        float shift = (segAngles / 2);
        if (yaw < 0.0F) yaw = 180.0F + 180.0F + yaw;
        yaw -= 360.0F - base;
        float angle = 360.0F - yaw + shift;
        if (angle < 0.0F) angle = 360.0F + angle;
        return angle;
    }

    private static float getCheckingAngle(EntityLivingBase player) {
        return getCheckingAngle(player, 0.0F);
    }

    public static void setEquipped(ItemStack stack, boolean equipped) {
        ItemNBTHelper.setBoolean(stack, "equipped", equipped);
    }

    public static boolean wasEquipped(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "equipped", false);
    }

    public boolean provideBlock(EntityPlayer player, ItemStack requestor, ItemStack halo, Block block, int meta,
            boolean doit) {
        for (int i = 0; i < 12; i++) {
            ItemStack tal = getItemForSlot(halo, i);
            if (tal == null) continue;
            ItemBlackHoleTalisman talisman = (ItemBlackHoleTalisman) tal.getItem();
            Block stored = talisman.getBlock(tal);
            int storedMeta = talisman.getBlockMeta(tal);
            if (stored == block && storedMeta == meta) {
                int count = talisman.getBlockCount(tal);
                if (count > 0) {
                    if (doit) ItemNBTHelper.setInt(tal, "blockCount", count - 1);
                    return true;
                }
            }
        }
        return false;
    }

    public int getBlockCount(EntityPlayer player, ItemStack requestor, ItemStack halo, Block block, int meta) {
        for (int i = 0; i < 12; i++) {
            ItemStack tal = getItemForSlot(halo, i);
            if (tal == null) continue;
            ItemBlackHoleTalisman talisman = (ItemBlackHoleTalisman) tal.getItem();
            Block stored = talisman.getBlock(tal);
            int storedMeta = talisman.getBlockMeta(tal);
            if (stored == block && storedMeta == meta) return talisman.getBlockCount(tal);
        }
        return 0;
    }
}
