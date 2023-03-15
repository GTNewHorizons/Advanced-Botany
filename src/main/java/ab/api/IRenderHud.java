package ab.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public interface IRenderHud {

    public void renderHud(Minecraft mc, ScaledResolution res);
}
