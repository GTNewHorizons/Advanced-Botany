package ab.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ab.common.core.CommonHelper;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemAntigravityCharm extends ItemMod {

    public static IIcon[] icons;

    public ItemAntigravityCharm() {
        super("antigravityCharm");
        this.setMaxStackSize(1);
        this.setNoRepair();
    }

    public void registerIcons(IIconRegister ir) {
        icons = new IIcon[2];
        for (int i = 0; i < icons.length; i++)
            icons[i] = icons[i] = ir.registerIcon("ab:" + "itemAntigravityCharm_" + i);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        int iconID = Math.min(1, pass);
        return this.icons[iconID];
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking())
            ItemNBTHelper.setBoolean(stack, "isActive", !ItemNBTHelper.getBoolean(stack, "isActive", true));
        return stack;
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean inHand) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (ItemNBTHelper.getBoolean(stack, "isActive", true)) {
                double posX = player.posX;
                double posY = player.posY;
                double posZ = player.posZ;
                final AxisAlignedBB asis = AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1, posY + 1, posZ + 1)
                        .expand(8, 8, 8);
                final List<EntityFallingBlock> listEntity = world.getEntitiesWithinAABB(EntityFallingBlock.class, asis);
                for (EntityFallingBlock fallBlock : listEntity) {
                    int x = (int) (fallBlock.posX >= 0 ? fallBlock.posX : fallBlock.posX - 1);
                    int y = (int) fallBlock.posY;
                    int z = (int) (fallBlock.posZ >= 0 ? fallBlock.posZ : fallBlock.posZ - 1);
                    Block block = fallBlock.func_145805_f();
                    fallBlock.setInvisible(true);
                    if (!world.isRemote) {
                        if (CommonHelper
                                .setBlock(world, BlockListAB.blockAntigravitation, 0, x, y - 1, z, player, true)) {
                            CommonHelper.setBlock(world, block, fallBlock.field_145814_a, x, y, z, player, true);
                            fallBlock.setDead();
                        }
                    }
                }
            }
        }
    }

    private void setBlock(World world, int x, int y, int z, Block block, int meta) {
        if (world.getBlock(x, y, z).getMaterial() == Material.air && y < 256) {
            world.setBlock(x, y, z, block, meta, 3);
        }
    }
}
