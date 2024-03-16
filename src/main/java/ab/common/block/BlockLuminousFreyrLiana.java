package ab.common.block;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;

public class BlockLuminousFreyrLiana extends BlockFreyrLiana {

    public BlockLuminousFreyrLiana() {
        this.setBlockName("BlockLuminousFreyrLiana");
        this.setBlockTextureName("ab:freyrLuminousLiana");
        this.setTickRandomly(true);
    }

    public int getLightValue() {
        return 11;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        if (rand.nextInt(11) == 0) world.setBlockMetadataWithNotify(x, y, z, 1, 3);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.getBlockMetadata(x, y, z) == 1) if (rand.nextInt(3) == 0) Botania.proxy.wispFX(
                world,
                x + 0.1 + Math.random() * 0.8,
                y + Math.random() * 0.5,
                z + 0.1 + Math.random() * 0.8,
                249 / 255f,
                230 / 255f,
                3 / 255f,
                0.14f,
                -0.04f,
                2);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
            float hitY, float hitZ) {
        Random rand = new Random();
        if (world.getBlockMetadata(x, y, z) == 1) {
            if (!world.isRemote) (new EntityItem(world, x + 0.5, y, z + 0.5))
                    .entityDropItem(new ItemStack(Items.gold_nugget, 1 + rand.nextInt(3)), 0.75f);
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            return true;
        }
        return false;
    }
}
