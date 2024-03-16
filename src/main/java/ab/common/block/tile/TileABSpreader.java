package ab.common.block.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ab.common.core.handler.ConfigABHandler;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILensEffect;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.entity.EntityManaBurst;

public class TileABSpreader extends TileSpreader {

    protected boolean requestsClientUpdate = false;
    protected int knownMana = -1;
    protected IManaReceiver receiver = null;

    public void readCustomNBT(NBTTagCompound cmp) {
        super.readCustomNBT(cmp);
        this.requestsClientUpdate = cmp.getBoolean("requestUpdate");
        if (cmp.hasKey("knownMana")) this.knownMana = cmp.getInteger("knownMana");
        if (this.requestsClientUpdate && this.worldObj != null) {
            int x = cmp.getInteger("forceClientBindingX");
            int y = cmp.getInteger("forceClientBindingY");
            int z = cmp.getInteger("forceClientBindingZ");
            if (y != -1) {
                TileEntity tile = this.worldObj.getTileEntity(x, y, z);
                if (tile instanceof IManaReceiver) {
                    this.receiver = (IManaReceiver) tile;
                } else {
                    this.receiver = null;
                }
            } else {
                this.receiver = null;
            }
        }
    }

    public EntityManaBurst getBurst(boolean fake) {
        EntityManaBurst burst = new EntityManaBurst(this, fake);
        int maxMana = ConfigABHandler.spreaderBurstMana;
        int color = 0xcdd419;
        int ticksBeforeManaLoss = 35;
        float manaLossPerTick = ConfigABHandler.spreaderBurstMana / 4.5f;
        float motionModifier = 2.5f;
        float gravity = 0.0F;
        BurstProperties props = new BurstProperties(
                maxMana,
                ticksBeforeManaLoss,
                manaLossPerTick,
                gravity,
                motionModifier,
                color);
        ItemStack lens = getStackInSlot(0);
        if (lens != null && lens.getItem() instanceof ILensEffect) ((ILensEffect) lens.getItem()).apply(lens, props);
        burst.setSourceLens(lens);
        if (getCurrentMana() >= props.maxMana || fake) {
            burst.setColor(props.color);
            burst.setMana(props.maxMana);
            burst.setStartingMana(props.maxMana);
            burst.setMinManaLoss(props.ticksBeforeManaLoss);
            burst.setManaLossPerTick(props.manaLossPerTick);
            burst.setGravity(props.gravity);
            burst.setMotion(
                    burst.motionX * props.motionModifier,
                    burst.motionY * props.motionModifier,
                    burst.motionZ * props.motionModifier);
            return burst;
        }
        return null;
    }

    public void renderHUD(Minecraft mc, ScaledResolution res) {
        String name = StatCollector.translateToLocal("tile.advancedSpreader.name");
        int color = 0xcdd419;
        HUDHandler.drawSimpleManaHUD(color, this.knownMana, getMaxMana(), name, res);
        ItemStack lens = getStackInSlot(0);
        if (lens != null) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            String lensName = lens.getDisplayName();
            int width = 16 + mc.fontRenderer.getStringWidth(lensName) / 2;
            int x = res.getScaledWidth() / 2 - width;
            int y = res.getScaledHeight() / 2 + 50;
            mc.fontRenderer.drawStringWithShadow(lensName, x + 20, y + 5, color);
            RenderHelper.enableGUIStandardItemLighting();
            RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, lens, x, y);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(3042);
        }
        if (this.receiver != null) {
            TileEntity receiverTile = (TileEntity) this.receiver;
            ItemStack recieverStack = new ItemStack(
                    this.worldObj.getBlock(receiverTile.xCoord, receiverTile.yCoord, receiverTile.zCoord),
                    1,
                    receiverTile.getBlockMetadata());
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            if (recieverStack != null && recieverStack.getItem() != null) {
                String stackName = recieverStack.getDisplayName();
                int width = 16 + mc.fontRenderer.getStringWidth(stackName) / 2;
                int x = res.getScaledWidth() / 2 - width;
                int y = res.getScaledHeight() / 2 + 30;
                mc.fontRenderer.drawStringWithShadow(stackName, x + 20, y + 5, color);
                RenderHelper.enableGUIStandardItemLighting();
                RenderItem.getInstance()
                        .renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, recieverStack, x, y);
                RenderHelper.disableStandardItemLighting();
            }
            GL11.glDisable(2896);
            GL11.glDisable(3042);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public int getMaxMana() {
        return ConfigABHandler.spreaderMaxMana;
    }
}
