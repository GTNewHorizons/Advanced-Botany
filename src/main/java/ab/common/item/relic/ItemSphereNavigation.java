package ab.common.item.relic;

import java.awt.Color;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import ab.client.core.handler.ItemsRemainingRender;
import ab.common.core.handler.ConfigABHandler;
import ab.common.core.handler.NetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemSphereNavigation extends ItemModRelic {

    public static IIcon[] icons;
    public static final int rangeSearch = 16;
    public static final int maxCooldown = 158;

    public ItemSphereNavigation() {
        super("sphereNavigation");
    }

    public void registerIcons(IIconRegister ir) {
        icons = new IIcon[5];
        for (int i = 0; i < icons.length; i++) icons[i] = icons[i] = ir.registerIcon("ab:" + "sphereNavigation_" + i);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.icons[pass == 0 ? 0 : (pass == 1 ? 1 : 4)];
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public String getItemStackDisplayName(ItemStack stack) {
        Block block = getFindBlock(stack);
        int meta = getFindMeta(stack);
        ItemStack rStack = new ItemStack(block, 1, meta);
        return super.getItemStackDisplayName(stack) + ((rStack == null || rStack.getItem() == null) ? ""
                : (EnumChatFormatting.RESET + " ("
                        + EnumChatFormatting.GREEN
                        + rStack.getDisplayName()
                        + EnumChatFormatting.RESET
                        + ")"));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        super.addInformation(stack, player, list, par4);
        addStringToTooltip(
                StatCollector
                        .translateToLocal(stack.getItemDamage() == 0 ? "botaniamisc.active" : "botaniamisc.inactive"),
                list);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (getFindBlock(stack) != null && player.isSneaking()) {
            int dmg = stack.getItemDamage();
            stack.setItemDamage((dmg ^ 0xFFFFFFFF) & 0x1);
            world.playSoundAtEntity(player, "random.orb", 0.3F, 0.1F);
        }
        return stack;
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int pos, boolean equipped) {
        super.onUpdate(stack, world, entity, pos, equipped);
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (!world.isRemote && stack.getItemDamage() == 0
                    && getFindBlock(stack) != null
                    && canWork(stack)
                    && ManaItemHandler
                            .requestManaExactForTool(stack, player, ConfigABHandler.sphereNavigationManaCost, true)) {
                setMaxTick(stack);
                NetworkHandler.sendPacketToFindBlocks((EntityPlayerMP) player, getFindBlock(stack), getFindMeta(stack));
            }
        }
    }

    public static void findBlocks(World world, Block findBlock, int findMeta, EntityPlayer player) {
        if (world.isRemote) {
            ItemStack renderStack = null;
            int maxFindedBlocks = 32;
            int findedBlocks = 0;
            label1703: for (int y = -rangeSearch * 2; y < rangeSearch; y++) {
                for (int x = -rangeSearch; x < rangeSearch; x++) {
                    if (!(world.rand.nextInt(maxFindedBlocks) < (maxFindedBlocks - findedBlocks))
                            || world.rand.nextBoolean())
                        continue;
                    for (int z = -rangeSearch; z < rangeSearch; z++) {
                        if (!(world.rand.nextInt(maxFindedBlocks) < (maxFindedBlocks - findedBlocks))
                                || world.rand.nextBoolean())
                            continue;
                        else if (findedBlocks >= maxFindedBlocks) break label1703;
                        int posX = MathHelper.floor_double(player.posX) + x;
                        int posY = MathHelper.floor_double(player.posY) + y;
                        int posZ = MathHelper.floor_double(player.posZ) + z;
                        if (posY < 0) continue;
                        Block block = world.getBlock(posX, posY, posZ);
                        if (!Block.isEqualTo(block, findBlock)) continue;
                        int meta = world.getBlockMetadata(posX, posY, posZ);
                        if (meta == findMeta) {
                            if (renderStack == null)
                                renderStack = getRenderStackForBlock(world, block, meta, posX, posY, posZ);
                            findedBlocks++;
                            float maxAge = 2.7f + (0.5f * (float) (Math.random()));
                            Botania.proxy.setWispFXDepthTest(false);
                            Botania.proxy.setWispFXDistanceLimit(false);
                            float far = 120.0f - (((Math.abs(x) + Math.min(rangeSearch, Math.abs(y)) + Math.abs(z))
                                    / (rangeSearch * 4.0f)) * 120.0f);
                            if (far <= 70) far *= 0.1f;
                            Color color = new Color(
                                    Color.HSBtoRGB(far / 360.0f, 0.9f + (float) (Math.random() * 0.1f), 1.0f));
                            for (int i = 0; i < 11; i++) Botania.proxy.wispFX(
                                    world,
                                    posX + 0.5f + (Math.random() - 0.5f),
                                    posY + 0.5f + (Math.random() - 0.5f),
                                    posZ + 0.5f + (Math.random() - 0.5f),
                                    color.getRed() / 100.0f,
                                    color.getGreen() / 100.0f,
                                    color.getBlue() / 100.0f,
                                    0.3f + (float) (Math.random() * 0.25f),
                                    0.0f,
                                    maxAge);
                            Botania.proxy.setWispFXDistanceLimit(true);
                            Botania.proxy.setWispFXDepthTest(true);
                        }
                    }
                }
            }
            if (renderStack != null) ItemsRemainingRender.set(
                    renderStack,
                    StatCollector.translateToLocal("ab.sphereNavigation.founded") + " " + findedBlocks);
        }
    }

    public static ItemStack getRenderStackForBlock(World world, Block block, int meta, int x, int y, int z) {
        if (block != null) {
            Item item = Item.getItemFromBlock(block);
            if (item != null) {
                ItemStack findStack = new ItemStack(block, 1, meta);
                if (findStack != null) return findStack;
            } else {
                Item item1 = block.getItem(world, x, y, z);
                if (item1 != null) {
                    ItemStack findStack = new ItemStack(item1, 1, meta);
                    if (findStack != null) return findStack;
                }
            }
        }
        return null;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer p, World world, int x, int y, int z, int p_77648_7_,
            float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (p.isSneaking()) {
            Block block = world.getBlock(x, y, z);
            if (block != null) {
                int meta = world.getBlockMetadata(x, y, z);
                ItemStack findStack = getRenderStackForBlock(world, block, meta, x, y, z);
                if (findStack != null) {
                    setFindBlock(stack, block, meta);
                    if (world.isRemote) ItemsRemainingRender.set(findStack, findStack.getDisplayName());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canWork(ItemStack stack) {
        int tick = ItemNBTHelper.getInt(stack, "cooldown", 0);
        if (tick == 0) return true;
        else if (tick > 0) ItemNBTHelper.setInt(stack, "cooldown", tick - 1);
        return false;
    }

    public void setMaxTick(ItemStack stack) {
        ItemNBTHelper.setInt(stack, "cooldown", maxCooldown);
    }

    public static void setFindBlock(ItemStack stack, Block block, int meta) {
        ItemNBTHelper.setInt(stack, "findBlockID", Block.getIdFromBlock(block));
        ItemNBTHelper.setInt(stack, "findBlockMeta", meta);
    }

    public static Block getFindBlock(ItemStack stack) {
        int blockID = ItemNBTHelper.getInt(stack, "findBlockID", 0);
        if (blockID == 0) return null;
        else return Block.getBlockById(blockID);
    }

    public static int getFindMeta(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "findBlockMeta", -1);
    }
}
