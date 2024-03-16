package ab.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.common.entity.EntityAdvancedSpark;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;

public class ItemAdvancedSpark extends ItemMod implements IManaGivingItem {

    public static IIcon invIcon;
    public static IIcon worldIcon;

    public ItemAdvancedSpark() {
        super("advancedSpark");
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xv,
            float yv, float zv) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof ISparkAttachable) {
            ISparkAttachable attach = (ISparkAttachable) tile;
            if (attach.canAttachSpark(stack) && attach.getAttachedSpark() == null) {
                stack.stackSize--;
                if (!world.isRemote) {
                    EntityAdvancedSpark spark = new EntityAdvancedSpark(world);
                    spark.setPosition(x + 0.5D, y + 1.5D, z + 0.5D);
                    world.spawnEntityInWorld((Entity) spark);
                    attach.attachSpark((ISparkEntity) spark);
                    VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
                }
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        invIcon = ir.registerIcon("ab:itemAB.spark_0");
        worldIcon = ir.registerIcon("ab:itemAB.spark_1");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return invIcon;
    }
}
