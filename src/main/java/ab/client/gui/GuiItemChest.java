package ab.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiItemChest extends GuiContainer {

    private static final ResourceLocation field_147017_u = new ResourceLocation(
            "textures/gui/container/generic_54.png");
    private int inventoryRows;

    public GuiItemChest(EntityPlayer player) {
        super(new ContainerItemChest(player));
        short short1 = 222;
        int i = short1 - 108;
        this.inventoryRows = ((ContainerItemChest) this.inventorySlots).itemChestInv.getSizeInventory() / 9;
        this.ySize = i + this.inventoryRows * 18;
    }

    protected boolean checkHotbarKeys(int p_146983_1_) {
        return false;
    }

    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(I18n.format("container.chest", new Object[0]), 8, 6, 4210752);
        this.fontRendererObj
                .drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_147017_u);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
