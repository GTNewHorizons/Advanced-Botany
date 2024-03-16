package ab.common.block.subtile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import ab.common.lib.register.RecipeListAB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketAspectPool;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;

public class SubTileAspecolus extends SubTileFunctional {

    protected static int manaRequare = 1250;
    int cooldown = 0;

    public void onUpdate() {
        super.onUpdate();
        World world = this.supertile.getWorldObj();
        if (world.isRemote) return;
        if (this.cooldown > 0) this.cooldown -= 1;
        int posX = this.supertile.xCoord;
        int posY = this.supertile.yCoord;
        int posZ = this.supertile.zCoord;
        List<EntityPlayer> players = world.getEntitiesWithinAABB(
                EntityPlayer.class,
                AxisAlignedBB.getBoundingBox(posX, posY, posZ, (posX + 1), (posY + 1), (posZ + 1)));
        int countPlayer = 0;
        for (EntityPlayer p : players) {
            if (!(this.mana >= this.manaRequare)) break;
            else if (this.cooldown > 0) break;
            AspectList aspList = Thaumcraft.proxy.playerKnowledge.getAspectsDiscovered(p.getDisplayName());
            ArrayList<Aspect> aspects = new ArrayList<Aspect>();
            for (Aspect a : aspList.getAspects()) {
                if (!Aspect.getPrimalAspects().contains(a)
                        && Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(p.getCommandSenderName(), a) > 0)
                    aspects.add(a);
            }
            if (aspects.isEmpty()) continue;
            for (int i = 0; i < 8; i++) {
                Aspect a = aspects.get(world.rand.nextInt(aspects.size()));
                if (!(Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(p.getCommandSenderName(), a) > 0)) continue;
                Aspect[] components = a.getComponents();
                aspList.reduce(a, 1);
                for (int r = 0; r < components.length; r++) {
                    if (world.rand.nextInt(100) < 70) continue;
                    Thaumcraft.proxy.playerKnowledge.addAspectPool(p.getDisplayName(), components[r], (short) 1);
                    PacketHandler.INSTANCE.sendTo(
                            new PacketAspectPool(
                                    components[r].getTag(),
                                    (short) 1,
                                    Thaumcraft.proxy.playerKnowledge
                                            .getAspectPoolFor(p.getCommandSenderName(), components[r])),
                            (EntityPlayerMP) p);
                }
            }
            this.mana -= this.manaRequare;
            this.sync();
            ++countPlayer;
        }
        if (countPlayer > 0) this.cooldown = 75 * countPlayer;
    }

    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toChunkCoordinates(), 1);
    }

    public int getMaxMana() {
        return 15000;
    }

    public int getColor() {
        return 0x8a20dc;
    }

    public LexiconEntry getEntry() {
        return RecipeListAB.aspecolus;
    }

    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setInteger("cooldown", this.cooldown);
    }

    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        this.cooldown = cmp.getInteger("cooldown");
    }
}
