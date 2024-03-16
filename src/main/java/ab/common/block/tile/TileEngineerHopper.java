package ab.common.block.tile;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import ab.api.IBoundRender;
import ab.client.core.ClientHelper;
import vazkii.botania.common.item.ModItems;

public class TileEngineerHopper extends TileInventory implements IHopper, IBoundRender {

    private int cooldown;
    private int[] invPosX = new int[] { 0, 0 };
    private int[] invPosY = new int[] { -1, -1 };
    private int[] invPosZ = new int[] { 0, 0 };
    private int[] invSide = new int[] { -1, -1 };
    private boolean bindType;
    public int redstoneSignal = 0;

    public void updateEntity() {
        this.redstoneSignal = 0;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            int redstoneSide = getWorldObj().getIndirectPowerLevelTo(
                    xCoord + dir.offsetX,
                    yCoord + dir.offsetY,
                    zCoord + dir.offsetZ,
                    dir.ordinal());
            this.redstoneSignal = Math.max(this.redstoneSignal, redstoneSide);
        }
        if (redstoneSignal > 0) return;
        else if (this.worldObj != null) {
            if (!this.worldObj.isRemote) {
                if (cooldown > 0) --cooldown;
                else if (cooldown == 0) {
                    if (worldObj != null && !worldObj.isRemote) {
                        boolean hasUpdate = false;
                        ItemStack stack = getStackInSlot(0);
                        if (stack != null) hasUpdate = canExtractStack();
                        if (stack == null || stack.stackSize != stack.getMaxStackSize())
                            hasUpdate = canInsertStack() || hasUpdate;
                        if (hasUpdate) this.cooldown = 8;
                        this.markDirty();
                    }
                }
            }
        }
    }

    public boolean bindTo(EntityPlayer player, ItemStack wand, int x, int y, int z, int side) {
        TileEntity tile = player.getEntityWorld().getTileEntity(x, y, z);
        boolean isFar = Math.abs(this.xCoord - x) >= 10 || Math.abs(this.yCoord - y) >= 10
                || Math.abs(this.zCoord - z) >= 10;
        if (isFar) return false;
        int invCount = bindType ? 0 : 1;
        if (tile instanceof TileEngineerHopper) return false;
        else if (tile != null && tile instanceof IInventory) {
            setDistantInventory(invCount, x, y, z);
            invSide[invCount] = side;
            return true;
        } else {
            setDistantInventory(invCount, 0, -1, 0);
            invSide[invCount] = -1;
            return false;
        }
    }

    public void changeBindType() {
        bindType = !bindType;
    }

    public void setDistantInventory(int count, int posX, int posY, int posZ) {
        invPosX[count] = posX;
        invPosY[count] = posY;
        invPosZ[count] = posZ;
    }

    public IInventory getDistantInventory(int count) {
        IInventory inv = TileEntityHopper
                .func_145893_b(this.getWorldObj(), invPosX[count], invPosY[count], invPosZ[count]);
        if (inv == null) setDistantInventory(count, 0, -1, 0);
        return inv;
    }

    public boolean canSelect(EntityPlayer player, ItemStack wand, int x, int y, int z, int side) {
        return true;
    }

    public ChunkCoordinates[] getBlocksCoord() {
        return new ChunkCoordinates[] { new ChunkCoordinates(invPosX[0], invPosY[0], invPosZ[0]),
                new ChunkCoordinates(invPosX[1], invPosY[1], invPosZ[1]) };
    }

    public ChunkCoordinates getBinding() {
        return null;
    }

    public void renderHUD(Minecraft mc, ScaledResolution res) {
        int x = res.getScaledWidth() / 2 - 7;
        int y = res.getScaledHeight() / 2 + 16;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        ClientHelper.drawArrow(x - 2, y, false);
        for (int i = 0; i < 2; i++) {
            ItemStack stack = null;
            boolean hasInv = false;
            int posX = x + (i == 0 ? 32 : -32);
            if (getDistantInventory(i) != null && (TileEntity) getDistantInventory(i) != null) {
                TileEntity tile = (TileEntity) getDistantInventory(i);
                Block block = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
                int meta = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
                stack = new ItemStack(block, 1, meta);
                hasInv = true;
            }
            Gui.drawRect(posX - 4, y - 4, posX + 20, y + 20, 1140850688);
            Gui.drawRect(posX - 2, y - 2, posX + 18, y + 18, 1140850688);
            if (stack != null) {
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glEnable(32826);
                RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, posX, y);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable(2929);
            }
            int invCount = bindType ? 0 : 1;
            if (invCount == i) {
                GL11.glTranslatef(0.0f, 0.0f, 300.0f);
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glEnable(32826);
                RenderItem.getInstance().renderItemIntoGUI(
                        mc.fontRenderer,
                        mc.renderEngine,
                        new ItemStack(ModItems.twigWand),
                        posX + 10,
                        y + 7);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable(2929);
                GL11.glTranslatef(0.0f, 0.0f, -300.0f);
            }
            boolean unicode = mc.fontRenderer.getUnicodeFlag();
            mc.fontRenderer.setUnicodeFlag(true);
            if (!hasInv) {
                mc.fontRenderer.drawStringWithShadow("\u2717", posX + 5, y + 6, 4980736);
                mc.fontRenderer.drawStringWithShadow("\u2717", posX + 5, y + 5, 13764621);
            }
            mc.fontRenderer.setUnicodeFlag(unicode);
            GL11.glEnable(2929);
        }
        GL11.glDisable(3042);
    }

    public void writeCustomNBT(NBTTagCompound nbtt) {
        super.writeCustomNBT(nbtt);
        nbtt.setInteger("cooldown", this.cooldown);
        nbtt.setBoolean("bindType", this.bindType);
        nbtt.setIntArray("bindingX", this.invPosX);
        nbtt.setIntArray("bindingY", this.invPosY);
        nbtt.setIntArray("bindingZ", this.invPosZ);
        nbtt.setIntArray("bindingSide", this.invSide);
    }

    public void readCustomNBT(NBTTagCompound nbtt) {
        super.readCustomNBT(nbtt);
        this.cooldown = nbtt.getInteger("cooldown");
        this.bindType = nbtt.getBoolean("bindType");
        this.invPosX = nbtt.getIntArray("bindingX");
        this.invPosY = nbtt.getIntArray("bindingY");
        this.invPosZ = nbtt.getIntArray("bindingZ");
        this.invSide = nbtt.getIntArray("bindingSide");
    }

    private boolean canExtractStack() {
        IInventory inv = getDistantInventory(0);
        if (inv == null) return false;
        else {
            int side = invSide[0];
            int pullCount = getPullCount(inv, side);
            System.out.println("canExtractStack " + pullCount);
            if (pullCount <= 0) return false;
            else {
                if (getStackInSlot(0) != null) {
                    ItemStack itemstack = getStackInSlot(0).copy();
                    ItemStack itemstack1 = TileEntityHopper.func_145889_a(inv, decrStackSize(0, pullCount), side);
                    if (itemstack1 == null || itemstack1.stackSize == 0) {
                        inv.markDirty();
                        return true;
                    }
                    setInventorySlotContents(0, itemstack);
                }
                return false;
            }
        }
    }

    private int getPullCount(IInventory inv, int side) {
        if (inv instanceof ISidedInventory && side > -1) {
            ISidedInventory sideInv = (ISidedInventory) inv;
            int[] slots = sideInv.getAccessibleSlotsFromSide(side);
            for (int i = 0; i < slots.length; ++i) {
                ItemStack stack = sideInv.getStackInSlot(slots[i]);
                if (stack == null) return inv.getInventoryStackLimit();
                else if (inv.getInventoryStackLimit() != stack.stackSize && stack.stackSize != stack.getMaxStackSize()
                        && TileNidavellirForge.isItemEqual(stack, getStackInSlot(0)))
                    return inv.getInventoryStackLimit() - stack.stackSize;
            }
        } else {
            int size = inv.getSizeInventory();
            for (int i = 0; i < size; ++i) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack == null) return inv.getInventoryStackLimit();
                else if (inv.getInventoryStackLimit() != stack.stackSize && stack.stackSize != stack.getMaxStackSize()
                        && TileNidavellirForge.isItemEqual(stack, getStackInSlot(0)))
                    return inv.getInventoryStackLimit() - stack.stackSize;
            }
        }
        return 0;
    }

    private boolean canInsertStack() {
        IInventory inv = getDistantInventory(1);
        if (inv != null) {
            int side = invSide[1];
            int takeCount = getTakeCount(inv, side);
            System.out.println("canInsertStack " + takeCount);
            if (takeCount <= 0) return false;
            if (inv instanceof ISidedInventory && side > -1) {
                ISidedInventory sidedInv = (ISidedInventory) inv;
                int[] slots = sidedInv.getAccessibleSlotsFromSide(side);
                for (int k = 0; k < slots.length; ++k) {
                    if (func_145892_a(inv, slots[k], side, takeCount)) return true;
                }
            } else {
                int i = inv.getSizeInventory();
                for (int j = 0; j < i; ++j) {
                    if (func_145892_a(inv, j, side, takeCount)) return true;
                }
            }
        }
        return false;
    }

    private int getTakeCount(IInventory inv, int side) {
        if (inv instanceof ISidedInventory && side > -1) {
            ISidedInventory sideInv = (ISidedInventory) inv;
            int[] slots = sideInv.getAccessibleSlotsFromSide(side);
            for (int i = 0; i < slots.length; ++i) {
                ItemStack stack = sideInv.getStackInSlot(slots[i]);
                if (stack != null)
                    if (getStackInSlot(0) == null) return Math.min(stack.getMaxStackSize(), getInventoryStackLimit());
                    else if (getInventoryStackLimit() != getStackInSlot(0).stackSize
                            && getStackInSlot(0).stackSize != getStackInSlot(0).getMaxStackSize()
                            && TileNidavellirForge.isItemEqual(stack, getStackInSlot(0)))
                        return getInventoryStackLimit() - getStackInSlot(0).stackSize;
            }
        } else {
            for (int i = 0; i < inv.getSizeInventory(); ++i) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack != null)
                    if (getStackInSlot(0) == null) return Math.min(stack.getMaxStackSize(), getInventoryStackLimit());
                    else if (getInventoryStackLimit() != getStackInSlot(0).stackSize
                            && getStackInSlot(0).stackSize != getStackInSlot(0).getMaxStackSize()
                            && TileNidavellirForge.isItemEqual(stack, getStackInSlot(0)))
                        return getInventoryStackLimit() - getStackInSlot(0).stackSize;
            }
        }
        return 0;
    }

    private boolean func_145892_a(IInventory inv, int slot, int side, int takeCount) {
        ItemStack itemstack = inv.getStackInSlot(slot);
        if (itemstack != null && func_145890_b(inv, itemstack, slot, side)) {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = TileEntityHopper.func_145889_a(this, inv.decrStackSize(slot, takeCount), side);
            if (itemstack2 == null || itemstack2.stackSize == 0) {
                inv.markDirty();
                return true;
            }
            inv.setInventorySlotContents(slot, itemstack1);
        }
        return false;
    }

    private static boolean func_145890_b(IInventory inv, ItemStack stack, int slot, int side) {
        return !(inv instanceof ISidedInventory) || ((ISidedInventory) inv).canExtractItem(slot, stack, side);
    }

    public int getSizeInventory() {
        return 1;
    }

    public String getInventoryName() {
        return "engineerHopper";
    }

    public double getXPos() {
        return this.xCoord;
    }

    public double getYPos() {
        return this.yCoord;
    }

    public double getZPos() {
        return this.zCoord;
    }
}
