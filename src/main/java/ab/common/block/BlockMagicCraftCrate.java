package ab.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ab.AdvancedBotany;
import ab.common.block.tile.TileMagicCraftCrate;
import ab.common.item.ItemCraftingPattern;
import ab.common.lib.register.RecipeListAB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.block.BlockModContainer;

public class BlockMagicCraftCrate extends BlockModContainer implements IWandable, IWandHUD, ILexiconable {

    public static final Aspect[] primals = new Aspect[] { Aspect.AIR, Aspect.WATER, Aspect.EARTH, Aspect.FIRE,
            Aspect.ORDER, Aspect.ENTROPY };

    IIcon iconSide;
    IIcon iconBottom;
    IIcon iconTop;
    public IIcon nothing;

    public BlockMagicCraftCrate() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
        this.setBlockName("magicThaumCrafter");
        this.setCreativeTab(AdvancedBotany.tabAB);
    }

    public LexiconEntry getEntry(World arg0, int arg1, int arg2, int arg3, EntityPlayer arg4, ItemStack arg5) {
        return RecipeListAB.thaumAutoCraft;
    }

    public void registerBlockIcons(IIconRegister ir) {
        iconSide = ir.registerIcon("ab:magicCrateSide");
        iconTop = ir.registerIcon("ab:magicCrateTop");
        iconBottom = ir.registerIcon("ab:magicCrateBottom");
        nothing = ir.registerIcon("ab:nothing");
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
        TileMagicCraftCrate crate = (TileMagicCraftCrate) par1World.getTileEntity(par2, par3, par4);
        return crate.getSignal();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
            float hitY, float hitZ) {
        TileMagicCraftCrate crate = (TileMagicCraftCrate) world.getTileEntity(x, y, z);
        ItemStack stack = player.getHeldItem();
        for (int i = 10; i < 12; i++) {
            ItemStack item = crate.getStackInSlot(i);
            if (player.isSneaking()) {
                if (item == null) continue;
                if (!world.isRemote) {
                    Vec3 vec3 = player.getLook(1.0F).normalize();
                    EntityItem entityitem = new EntityItem(
                            world,
                            player.posX + vec3.xCoord,
                            player.posY + vec3.yCoord + 0.5f,
                            player.posZ + vec3.zCoord,
                            item.copy());
                    world.spawnEntityInWorld(entityitem);
                }
                crate.setInventorySlotContents(i, null);
                world.playSoundEffect(
                        (double) x,
                        (double) y,
                        (double) z,
                        "random.pop",
                        0.2f,
                        ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7f + 1.0f) * 1.5f);
                crate.markDirty();
                return true;
            } else if (stack != null && crate.isItemValidForSlot(i, stack)) {
                if (item != null) {
                    if (!world.isRemote) {
                        Vec3 vec3 = player.getLook(1.0F).normalize();
                        EntityItem entityitem = new EntityItem(
                                world,
                                player.posX + vec3.xCoord,
                                player.posY + vec3.yCoord + 0.5f,
                                player.posZ + vec3.zCoord,
                                item.copy());
                        world.spawnEntityInWorld(entityitem);
                    }
                }
                crate.setInventorySlotContents(i, stack.copy());
                if (i == 11) crate.setPlayer(player);
                stack.splitStack(1);
                world.playSoundEffect(
                        (double) x,
                        (double) y,
                        (double) z,
                        "random.pop",
                        0.2f,
                        ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7f + 1.0f) * 1.5f);
                crate.markDirty();
                return true;
            }
        }
        if (stack != null && stack.getItem() instanceof ItemCraftingPattern && stack.getItemDamage() != 0) {
            boolean[][] pattern = ((ItemCraftingPattern) stack.getItem()).getPattern(stack.getItemDamage());
            crate.setPattern(pattern);
            stack.splitStack(1);
            return true;
        }
        return false;
    }

    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case 0:
                return iconBottom;
            case 1:
                return iconTop;
            default:
                return iconSide;
        }
    }

    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        switch (side) {
            case 0:
                return iconBottom;
            case 1:
                return iconTop;
            default:
                return iconSide;
        }
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMagicCraftCrate();
    }

    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMagicCraftCrate) {
            TileMagicCraftCrate craft = (TileMagicCraftCrate) tile;
            int width = 52;
            int height = 52;
            int xc = res.getScaledWidth() / 2 + 20;
            int yc = res.getScaledHeight() / 2 - height / 2;

            Gui.drawRect(xc - 6 - 3, yc - 6, xc + width + 3, yc + height + 6, 0x2294178a);
            Gui.drawRect(xc - 4 - 3, yc - 4, xc + width + 1, yc + height + 4, 0x2294178a);

            Gui.drawRect(xc + 60 - 3, yc + 18 - 3, xc + 60 + 16 + 3, yc + 18 + 16 + 3, 0x2294178a);
            Gui.drawRect(xc + 60 - 2, yc + 18 - 2, xc + 60 + 16 + 2, yc + 18 + 16 + 2, 0x2294178a);
            Gui.drawRect(xc + 60, yc + 18, xc + 60 + 16, yc + 18 + 16, 0x22efd3f4);

            AspectList waitingAspects = null;

            if (craft.getWaitingStack() != null && craft.getPlayer() != null) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.7f);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                RenderItem.getInstance().renderItemAndEffectIntoGUI(
                        mc.fontRenderer,
                        mc.renderEngine,
                        craft.getWaitingStack(),
                        xc + 60,
                        yc + 18);
                net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                waitingAspects = ThaumcraftCraftingManager
                        .findMatchingArcaneRecipeAspects((IInventory) craft, craft.getPlayer());
            }
            if (craft.getStackInSlot(10) != null) {
                GL11.glEnable(GL11.GL_BLEND);
                ItemStack wand = craft.getStackInSlot(10);
                ItemWandCasting itemWand = ((ItemWandCasting) wand.getItem());
                AspectList aspects = itemWand.getAllVis(wand);
                int n = 0;
                for (Aspect a : primals) {
                    float alpha = 1.0f;
                    if (waitingAspects != null) {
                        float amt = (float) waitingAspects.getAmount(a);
                        if (amt > 0) {
                            amt *= itemWand.getConsumptionModifier(wand, craft.getPlayer(), a, true);
                            if (amt * 100.0f > itemWand.getVis(wand, a)) {
                                alpha = 0.5f + (MathHelper.sin((craft.getPlayer().ticksExisted + n * 10) / 2.0f) * 0.2f
                                        - 0.2f);
                            }
                        }
                    }
                    int xa = (int) (xc - 45 + Math.cos(0.2f * Math.PI * n + Math.PI / 2) * 45);
                    int ya = (int) (yc + 16 + Math.sin(0.2f * Math.PI * n + Math.PI / 2) * 45);
                    float amout = aspects.getAmount(a) + 0.000001f;
                    UtilsFX.drawTag(xa, ya, a, amout / 100f, 0, 0, 771, alpha, false);
                    n++;
                }
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            }

            Gui.drawRect(xc - 28 - 20 - 3, yc + 7 - 3, xc - 28 - 20 + 16 + 3, yc + 7 + 16 + 3, 0x2294178a);
            Gui.drawRect(xc - 28 - 20 - 2, yc + 7 - 2, xc - 28 - 20 + 16 + 2, yc + 7 + 16 + 2, 0x2294178a);
            Gui.drawRect(xc - 28 - 20, yc + 7, xc - 28 - 20 + 16, yc + 7 + 16, 0x22efd3f4);
            net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderItem.getInstance().renderItemAndEffectIntoGUI(
                    mc.fontRenderer,
                    mc.renderEngine,
                    craft.getStackInSlot(10),
                    xc - 28 - 20,
                    yc + 8);
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            Gui.drawRect(xc - 28 - 20 - 3, yc + 29 - 3, xc - 28 - 20 + 16 - 3, yc + 29 + 16 + 3, 0x2294178a);
            Gui.drawRect(xc - 28 - 20 - 2, yc + 29 - 2, xc - 28 - 20 + 16 + 2, yc + 29 + 16 + 2, 0x2294178a);
            Gui.drawRect(xc - 28 - 20, yc + 29, xc - 28 - 20 + 16, yc + 29 + 16, 0x22efd3f4);
            net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderItem.getInstance().renderItemAndEffectIntoGUI(
                    mc.fontRenderer,
                    mc.renderEngine,
                    craft.getStackInSlot(11),
                    xc - 28 - 20,
                    yc + 29);
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                int xp = xc + j * 18 - 3;
                int yp = yc + i * 18;

                boolean enabled = craft.getPattern()[i][j];

                Gui.drawRect(xp, yp, xp + 16, yp + 16, enabled ? 0x22efd3f4 : 0x22fb02a6);

                ItemStack item = craft.getStackInSlot(index);
                net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, item, xp, yp);
                net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            }
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote) {
            TileMagicCraftCrate inv = (TileMagicCraftCrate) world.getTileEntity(x, y, z);
            if (inv != null) {
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack != null) {
                        float f = world.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
                        EntityItem entityitem = new EntityItem(world, (x + f), (y + f1), (z + f2), stack.copy());
                        float f3 = 0.05F;
                        entityitem.motionX = ((float) world.rand.nextGaussian() * f3);
                        entityitem.motionY = ((float) world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = ((float) world.rand.nextGaussian() * f3);
                        if (stack.hasTagCompound())
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
        TileMagicCraftCrate crate = (TileMagicCraftCrate) world.getTileEntity(x, y, z);
        crate.ejectAll();
        return true;
    }
}
