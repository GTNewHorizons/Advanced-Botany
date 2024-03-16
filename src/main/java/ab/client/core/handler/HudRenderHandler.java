package ab.client.core.handler;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import ab.api.IRenderHud;
import ab.common.item.equipment.ItemBlackHalo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.common.item.ItemLexicon;
import vazkii.botania.common.item.ItemTwigWand;

public class HudRenderHandler {

    @SubscribeEvent
    public void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        Profiler profiler = mc.mcProfiler;
        ItemStack equippedStack = mc.thePlayer.getCurrentEquippedItem();
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            profiler.startSection("advabcedBotany-hud");
            MovingObjectPosition pos = mc.objectMouseOver;
            if (pos != null) {
                Block block = mc.theWorld.getBlock(pos.blockX, pos.blockY, pos.blockZ);
                TileEntity tile = mc.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
                boolean canRender = equippedStack != null && (equippedStack.getItem() instanceof ItemTwigWand
                        || equippedStack.getItem() instanceof ItemLexicon);
                if (tile != null && tile instanceof IRenderHud && !canRender) {
                    ((IRenderHud) tile).renderHud(mc, event.resolution);
                }
            }
            if (equippedStack != null && equippedStack.getItem() instanceof ItemBlackHalo) {
                profiler.startSection("blackHalo");
                ItemBlackHalo.renderHUD(event.resolution, mc.thePlayer, equippedStack);
                profiler.endSection();
            }
            profiler.endStartSection("itemsRemainingAB");
            ItemsRemainingRender.render(event.resolution, event.partialTicks);
            profiler.endSection();
        }
    }
}
