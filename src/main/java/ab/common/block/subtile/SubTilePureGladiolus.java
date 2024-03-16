package ab.common.block.subtile;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import ab.common.lib.register.RecipeListAB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.entities.EntityAspectOrb;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.ModBlocks;

public class SubTilePureGladiolus extends SubTileFunctional {

    private static final int MAX_MANA = 10000;
    private static final String COOLDOWN_TAG = "Cooldown";
    private static final Aspect[] PRIMALS = new Aspect[] { Aspect.AIR, Aspect.WATER, Aspect.EARTH, Aspect.ENTROPY,
            Aspect.ORDER, Aspect.FIRE };

    private int cooldown = 180;
    protected static int manaRequare = 1000;

    public void onUpdate() {
        super.onUpdate();
        int tick = 1;
        World world = this.supertile.getWorldObj();
        int x = this.supertile.xCoord;
        int y = this.supertile.yCoord;
        int z = this.supertile.zCoord;
        List<EntityPlayer> players = world.getEntitiesWithinAABB(
                EntityPlayer.class,
                AxisAlignedBB.getBoundingBox(x, y, z, (x + 1), (y + 1), (z + 1)).expand(1.9f, 1.2f, 1.9f));
        if (world.getBlock(x, y + 1, z).getMaterial() != Material.air) return;
        else if (world.getBlock(x, y - 1, z) == ModBlocks.enchantedSoil) tick = 2;
        if (players.isEmpty()) return;
        if (cooldown > 0) cooldown -= tick;
        else if (mana >= manaRequare && cooldown <= 0) {
            mana -= manaRequare;
            cooldown = 180;
            spawnAspectOrbs(world, x + 0.5, y + 1.0, z + 0.5);
        }
    }

    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toChunkCoordinates(), 1);
    }

    public LexiconEntry getEntry() {
        return RecipeListAB.gladious;
    }

    public void spawnAspectOrbs(World world, double x, double y, double z) {
        if (!world.isRemote) {
            for (int i = 0; i < 3 + world.rand.nextInt(3); i++) {
                EntityAspectOrb orb = new EntityAspectOrb(
                        world,
                        x,
                        y,
                        z,
                        PRIMALS[world.rand.nextInt(6)],
                        2 + world.rand.nextInt(3));
                orb.motionX = 0.05 - Math.random() * 0.1;
                orb.motionY = 0.07;
                orb.motionZ = 0.05 - Math.random() * 0.1;
                world.spawnEntityInWorld(orb);
            }
            this.sync();
        }
    }

    public int getColor() {
        return 0xFF00FF;
    }

    public int getMaxMana() {
        return MAX_MANA;
    }

    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setInteger(COOLDOWN_TAG, this.cooldown);
    }

    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        this.cooldown = cmp.getInteger(COOLDOWN_TAG);
    }
}
