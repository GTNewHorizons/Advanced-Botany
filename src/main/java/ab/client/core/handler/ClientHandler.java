package ab.client.core.handler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import ab.api.IRankItem;
import ab.client.core.handler.PlayerItemUsingSound.ClientSoundHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.lib.LibObfuscation;

public class ClientHandler {

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) renderTooltip();
    }

    @SubscribeEvent
    public void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ItemsRemainingRender.tick();
            ClientSoundHandler.tick();
        }
    }

    public static void renderTooltip() {
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen gui = mc.currentScreen;
        if (gui != null && gui instanceof GuiContainer
                && mc.thePlayer != null
                && mc.thePlayer.inventory.getItemStack() == null) {
            GuiContainer container = (GuiContainer) gui;
            Slot slot = (Slot) ReflectionHelper.getPrivateValue(GuiContainer.class, container, LibObfuscation.THE_SLOT);
            if (slot != null && slot.getHasStack()) {
                ItemStack stack = slot.getStack();
                if (stack != null) {
                    List<String> tooltip;
                    ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                    FontRenderer font = mc.fontRenderer;
                    int mouseX = Mouse.getX() * res.getScaledWidth() / mc.displayWidth;
                    int mouseY = res.getScaledHeight() - Mouse.getY() * res.getScaledHeight() / mc.displayHeight;
                    try {
                        tooltip = stack.getTooltip((EntityPlayer) mc.thePlayer, mc.gameSettings.advancedItemTooltips);
                    } catch (Exception e) {
                        tooltip = new ArrayList<String>();
                    }
                    int width = 0;
                    for (String s : tooltip) width = Math.max(width, font.getStringWidth(s) + 2);
                    int tooltipHeight = (tooltip.size() - 1) * 10 + 5;
                    int height = 3;
                    int offx = 11;
                    int offy = 17;
                    boolean offscreen = (mouseX + width + 19 >= res.getScaledWidth());
                    int fixY = res.getScaledHeight() - mouseY + tooltipHeight;
                    if (fixY < 0) offy -= fixY;
                    if (offscreen) offx = -13 - width;
                    if (stack.getItem() instanceof IRankItem) {
                        drawRankItemBar(stack, mouseX, mouseY, offx, offy, width, height, font);
                    }
                }
            }
        }
    }

    private static void drawRankItemBar(ItemStack stack, int mouseX, int mouseY, int offx, int offy, int width,
            int height, FontRenderer font) {
        IRankItem item = (IRankItem) stack.getItem();
        int level = item.getLevel(stack);
        int max = item.getLevels()[Math.min(item.getLevels().length - 1, level + 1)];
        boolean ss = (level >= item.getLevels().length - 1);
        int curr = item.getMana(stack);
        float percent = (level == 0) ? 0.0F : ((float) curr / (float) max);
        int rainbowWidth = Math.min(width - (ss ? 0 : 1), (int) (width * percent));
        float huePer = (width == 0) ? 0.0F : (1.0F / width);
        float hueOff = (ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks) * 0.01F;
        GL11.glDisable(2929);
        Gui.drawRect(
                mouseX + offx - 1,
                mouseY - offy - height - 1,
                mouseX + offx + width + 1,
                mouseY - offy,
                -16777216);
        for (int i = 0; i < rainbowWidth; i++) Gui.drawRect(
                mouseX + offx + i,
                mouseY - offy - height,
                mouseX + offx + i + 1,
                mouseY - offy,
                Color.HSBtoRGB(hueOff + huePer * i, 1.0F, 1.0F));
        Gui.drawRect(
                mouseX + offx + rainbowWidth,
                mouseY - offy - height,
                mouseX + offx + width,
                mouseY - offy,
                -11184811);
        String rank = StatCollector.translateToLocal("botania.rank" + level).replaceAll("&", "\u00A7");
        GL11.glPushAttrib(2896);
        GL11.glDisable(2896);
        font.drawStringWithShadow(rank, mouseX + offx, mouseY - offy - 12, 16777215);
        if (!ss) {
            rank = StatCollector.translateToLocal("botania.rank" + (level + 1)).replaceAll("&", "\u00A7");
            font.drawStringWithShadow(
                    rank,
                    mouseX + offx + width - font.getStringWidth(rank),
                    mouseY - offy - 12,
                    16777215);
        }
        GL11.glEnable(2929);
        GL11.glPopAttrib();
    }
}
