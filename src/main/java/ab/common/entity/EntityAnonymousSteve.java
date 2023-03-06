package ab.common.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityAnonymousSteve extends AbstractClientPlayer {

    public EntityAnonymousSteve(World world) {
        super(world, new GameProfile(null, "abSteveForRenderer"));
    }

    public boolean canCommandSenderUseCommand(int i, String s) {
        return false;
    }

    public ChunkCoordinates getPlayerCoordinates() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {
        return 15728880;
    }

    public boolean isInvisible() {
        return true;
    }

    public void addChatMessage(IChatComponent var1) {}
}
