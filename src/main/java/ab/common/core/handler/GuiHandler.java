package ab.common.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import ab.client.gui.ContainerItemChest;
import ab.client.gui.GuiItemChest;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new ContainerItemChest(player);
        }
        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new GuiItemChest(player);
        }
        return null;
    }
}
