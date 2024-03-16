package ab.common.block.tile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import ab.api.IRenderHud;
import ab.common.lib.register.BlockListAB;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.tile.TileMod;

public class TileLebethronCore extends TileMod implements IRenderHud {

    protected int tick;
    protected Block block;
    protected int meta;
    protected boolean validTree;

    public static MultiblockSet makeMultiblockSet() {
        Multiblock mb = new Multiblock();
        mb.addComponent(0, 1, 0, BlockListAB.blockLebethron, 4);
        mb.addComponent(0, 0, 0, BlockListAB.blockLebethron, 0);
        mb.addComponent(0, 2, 0, BlockListAB.blockLebethron, 0);
        mb.addComponent(0, 3, 0, BlockListAB.blockLebethron, 0);
        mb.addComponent(0, 4, 0, BlockListAB.blockLebethron, 0);
        mb.addComponent(0, 5, 0, BlockListAB.blockLebethron, 0);
        mb.setRenderOffset(0, 1, 0);
        return mb.makeSet();
    }

    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (tick <= 0) {
                updateStructure();
                if (validTree && getBlock() != null) {
                    spawnLeaves();
                    tick = 40;
                }
            } else tick--;
        } else if (worldObj.rand.nextBoolean()) Botania.proxy.sparkleFX(
                this.worldObj,
                this.xCoord + Math.random(),
                this.yCoord + Math.random(),
                this.zCoord + Math.random(),
                0.5F,
                1.0F,
                0.5F,
                (float) Math.random() * 2,
                2);
    }

    public boolean getValidTree() {
        return this.validTree;
    }

    public void updateStructure() {
        boolean oldValidTree = validTree;
        validTree = hasValidTree();
        if (oldValidTree != validTree)
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    public void writeCustomNBT(NBTTagCompound nbtt) {
        nbtt.setInteger("blockID", Block.getIdFromBlock(block));
        nbtt.setInteger("blockMeta", this.meta);
        nbtt.setInteger("tick", this.tick);
        nbtt.setBoolean("validTree", validTree);
    }

    public void readCustomNBT(NBTTagCompound nbtt) {
        this.block = Block.getBlockById(nbtt.getInteger("blockID"));
        this.meta = nbtt.getInteger("blockMeta");
        this.tick = nbtt.getInteger("tick");
        this.validTree = nbtt.getBoolean("validTree");
    }

    public boolean setBlock(EntityPlayer player, Block block, int meta) {
        if (this.block == null) {
            this.block = block;
            this.meta = meta;
            return true;
        } else {
            if (Block.isEqualTo(this.block, block) && this.meta == meta) return false;
            else if (!worldObj.isRemote) {
                Vec3 vec3 = player.getLook(1.0F).normalize();
                EntityItem entityitem = new EntityItem(
                        worldObj,
                        player.posX + vec3.xCoord,
                        player.posY + 1.2f,
                        player.posZ + vec3.zCoord,
                        new ItemStack(this.block, 1, this.meta));
                worldObj.spawnEntityInWorld(entityitem);
            }
            this.block = block;
            this.meta = meta;
            return true;
        }
    }

    public Block getBlock() {
        if (!Block.isEqualTo(block, Blocks.air) && block != null) return this.block;
        return null;
    }

    public int getMeta() {
        return this.meta;
    }

    public boolean hasValidTree() {
        if (!checkBlock(
                this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord),
                this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1, this.zCoord))) {
            return false;
        }
        for (int i = 1; i <= 4; i++) {
            if (!checkBlock(
                    this.worldObj.getBlock(this.xCoord, this.yCoord + i, this.zCoord),
                    this.worldObj.getBlockMetadata(this.xCoord, this.yCoord + i, this.zCoord))) {
                return false;
            }
        }
        return true;
    }

    boolean checkBlock(Block block, int meta) {
        return block == BlockListAB.blockLebethron && meta == 0;
    }

    private void spawnLeaves() {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y < 5; y++) {
                    this.setBlock(worldObj, this.xCoord + x, this.yCoord + y + 2, this.zCoord + z);
                }
            }
        }
        this.setBlock(worldObj, this.xCoord + 1, this.yCoord + 1, this.zCoord);
        this.setBlock(worldObj, this.xCoord - 1, this.yCoord + 1, this.zCoord);
        this.setBlock(worldObj, this.xCoord, this.yCoord + 1, this.zCoord + 1);
        this.setBlock(worldObj, this.xCoord, this.yCoord + 1, this.zCoord - 1);
        this.setBlock(worldObj, this.xCoord + 1, this.yCoord + 7, this.zCoord);
        this.setBlock(worldObj, this.xCoord - 1, this.yCoord + 7, this.zCoord);
        this.setBlock(worldObj, this.xCoord, this.yCoord + 7, this.zCoord + 1);
        this.setBlock(worldObj, this.xCoord, this.yCoord + 7, this.zCoord - 1);
        for (int i = 0; i <= 3; i++) this.setBlock(worldObj, this.xCoord, this.yCoord + 6 + i, this.zCoord);
        this.setBlock(worldObj, this.xCoord, this.yCoord + 2, this.zCoord - 2);
        for (int i = -1; i <= 1; i++) {
            this.setBlock(worldObj, this.xCoord + i, this.yCoord + 3, this.zCoord - 2);
            this.setBlock(worldObj, this.xCoord + i, this.yCoord + 4, this.zCoord - 2);
        }
        this.setBlock(worldObj, this.xCoord, this.yCoord + 5, this.zCoord - 2);
        this.setBlock(worldObj, this.xCoord, this.yCoord + 2, this.zCoord + 2);
        for (int i = -1; i <= 1; i++) {
            this.setBlock(worldObj, this.xCoord + i, this.yCoord + 3, this.zCoord + 2);
            this.setBlock(worldObj, this.xCoord + i, this.yCoord + 4, this.zCoord + 2);
        }
        this.setBlock(worldObj, this.xCoord, this.yCoord + 5, this.zCoord + 2);
        this.setBlock(worldObj, this.xCoord + 2, this.yCoord + 2, this.zCoord);
        for (int i = -1; i <= 1; i++) {
            this.setBlock(worldObj, this.xCoord + 2, this.yCoord + 3, this.zCoord + i);
            this.setBlock(worldObj, this.xCoord + 2, this.yCoord + 4, this.zCoord + i);
        }
        this.setBlock(worldObj, this.xCoord + 2, this.yCoord + 5, this.zCoord);
        this.setBlock(worldObj, this.xCoord - 2, this.yCoord + 2, this.zCoord);
        for (int i = -1; i <= 1; i++) {
            this.setBlock(worldObj, this.xCoord - 2, this.yCoord + 3, this.zCoord + i);
            this.setBlock(worldObj, this.xCoord - 2, this.yCoord + 4, this.zCoord + i);
        }
        this.setBlock(worldObj, this.xCoord - 2, this.yCoord + 5, this.zCoord);
    }

    private void setBlock(World world, int x, int y, int z) {
        if (world.rand.nextInt(10) <= 8) return;
        else if (world.getBlock(x, y, z).getMaterial() == Material.air && y < 256)
            world.setBlock(x, y, z, this.block, this.meta, 3);
    }

    public void renderHud(Minecraft mc, ScaledResolution res) {
        if (!validTree) return;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        ItemStack stack = getBlock() != null ? new ItemStack(block, 1, meta) : new ItemStack(Blocks.leaves);
        int x = res.getScaledWidth() / 2 - 7;
        int y = res.getScaledHeight() / 2 + 12;
        Gui.drawRect(x - 2, y - 2, x + 18, y + 18, 1140850688);
        Gui.drawRect(x, y, x + 16, y + 16, 1140850688);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(32826);
        RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2929);
        boolean unicode = mc.fontRenderer.getUnicodeFlag();
        mc.fontRenderer.setUnicodeFlag(true);
        if (getBlock() != null) {
            mc.fontRenderer.drawStringWithShadow("\u2713", x + 10, y + 9, 19456);
            mc.fontRenderer.drawStringWithShadow("\u2713", x + 10, y + 8, 774669);
        } else {
            mc.fontRenderer.drawStringWithShadow("\u2717", x + 10, y + 9, 4980736);
            mc.fontRenderer.drawStringWithShadow("\u2717", x + 10, y + 8, 13764621);
        }
        mc.fontRenderer.setUnicodeFlag(unicode);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
    }
}
