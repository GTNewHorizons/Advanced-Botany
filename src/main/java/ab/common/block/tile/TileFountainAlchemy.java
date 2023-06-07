package ab.common.block.tile;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.Botania;
import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeFountainAlchemy;

public class TileFountainAlchemy extends TileInventory implements ISparkAttachable, ISidedInventory {

    private int mana;
    public int manaToGet;
    private RecipeFountainAlchemy currentRecipe;
    private int recipeID;
    public boolean requestUpdate;

    public void updateEntity() {
        if (!this.worldObj.isRemote) updateServer();
        else updateClient();
        ISparkEntity spark = getAttachedSpark();
        if (spark != null) {
            List<ISparkEntity> sparkEntities = SparkHelper
                    .getSparksAround(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D);
            for (ISparkEntity otherSpark : sparkEntities) {
                if (spark == otherSpark) continue;
                else if (otherSpark.getAttachedTile() != null && otherSpark.getAttachedTile() instanceof IManaPool)
                    otherSpark.registerTransfer(spark);
            }
        }
    }

    private void updateServer() {
        if (requestUpdate)
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        boolean hasUpdate = false;
        List<EntityItem> items = this.worldObj.getEntitiesWithinAABB(
                EntityItem.class,
                AxisAlignedBB.getBoundingBox(
                        this.xCoord,
                        this.yCoord,
                        this.zCoord,
                        (this.xCoord + 1),
                        (this.yCoord + 1),
                        (this.zCoord + 1)));
        for (EntityItem item : items) {
            if (item.getEntityItem() != null && !item.isDead) {
                ItemStack stack = item.getEntityItem();
                int splitCount = addItemStack(stack);
                stack.stackSize -= splitCount;
                if (stack.stackSize <= 0) item.setDead();
                if (splitCount > 0) hasUpdate = true;
                break;
            }
        }
        int wasManaToGet = this.manaToGet;
        boolean hasCraft = false;
        int recipeID = 0;
        for (RecipeFountainAlchemy recipe : AdvancedBotanyAPI.FountainAlchemyRecipes) {
            if (recipe.matches(this)) {
                this.recipeID = recipeID;
                if (this.mana > 0 && this.isFull()) {
                    ItemStack output = recipe.getOutput().copy();
                    this.recieveMana(-recipe.getManaUsage());
                    this.manaToGet = 0;
                    for (int i = 1; i < this.getSizeInventory(); i++) {
                        if (this.getStackInSlot(i).stackSize > 1) --this.getStackInSlot(i).stackSize;
                        else this.setInventorySlotContents(i, null);
                    }
                    if (this.getStackInSlot(0) != null) ++this.getStackInSlot(0).stackSize;
                    else this.setInventorySlotContents(0, output);
                    hasUpdate = true;
                    worldObj.playSoundEffect(xCoord, yCoord, zCoord, "botania:terrasteelCraft", 1.0F, 2.0F);
                    break;
                } else {
                    if (this.getStackInSlot(0) == null) {
                        this.manaToGet = recipe.getManaUsage();
                        currentRecipe = recipe;
                        hasCraft = true;
                        break;
                    } else if (isItemEqual(recipe.getOutput(), getStackInSlot(0))
                            && getStackInSlot(0).stackSize < recipe.getOutput().getMaxStackSize()) {
                                this.manaToGet = recipe.getManaUsage();
                                currentRecipe = recipe;
                                hasCraft = true;
                                break;
                            }
                }
            }
            recipeID++;
        }
        if (!hasCraft) {
            currentRecipe = null;
            this.mana = 0;
            this.manaToGet = 0;
        }
        if (this.manaToGet != wasManaToGet) hasUpdate = true;
        requestUpdate = hasUpdate;
    }

    private void updateClient() {
        if (this.mana > 0) {
            double worldTime = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
            worldTime += (new Random((this.xCoord ^ this.yCoord ^ this.zCoord))).nextInt(360);
            float indetY = (float) (Math.sin(worldTime / 18.0D) / 24.0D);
            float ticks = 100.0F * getCurrentMana() / this.manaToGet;
            int totalSpiritCount = 3;
            double tickIncrement = 360.0D / totalSpiritCount;
            int speed = 5;
            double wticks = (ticks * speed) - tickIncrement;
            double r = Math.sin((ticks - 100) / 10.0D) * 0.5D;
            double g = Math.sin(wticks * Math.PI / 180.0D * 0.55D);
            float size = 0.4f;
            for (int i = 0; i < totalSpiritCount; i++) {
                double x = this.xCoord + Math.sin(wticks * Math.PI / 180.0D) * r + 0.5D;
                double y = this.yCoord - indetY + 0.85D + Math.abs(r) * 0.7D;
                double z = this.zCoord + Math.cos(wticks * Math.PI / 180.0D) * r + 0.5D;
                wticks += tickIncrement;
                int color = 0x24cce0;
                if (currentRecipe != null) color = currentRecipe.getColor();
                float hsb[] = Color.RGBtoHSB(color & 255, color >> 8 & 255, color >> 16 & 255, null);
                int color1 = Color.HSBtoRGB(hsb[0], hsb[1], ticks / 100.0F);
                float[] colorsfx = { (color1 & 255) / 255.0f, (color1 >> 8 & 255) / 255.0f,
                        (color1 >> 16 & 255) / 255.0f };
                Botania.proxy.wispFX(
                        this.worldObj,
                        x,
                        y,
                        z,
                        colorsfx[0],
                        colorsfx[1],
                        colorsfx[2],
                        0.85F * size,
                        (float) g * 0.05F,
                        0.25F);
                Botania.proxy.wispFX(
                        this.worldObj,
                        x,
                        y,
                        z,
                        colorsfx[0],
                        colorsfx[1],
                        colorsfx[2],
                        (float) Math.random() * 0.1F + 0.1F * size,
                        (float) (Math.random() - 0.5D) * 0.05F,
                        (float) (Math.random() - 0.5D) * 0.05F,
                        (float) (Math.random() - 0.5D) * 0.05F,
                        0.9F);
                if (ticks == 100) for (int j = 0; j < 12; j++) Botania.proxy.wispFX(
                        this.worldObj,
                        this.xCoord + 0.5D,
                        this.yCoord + 1.1D - indetY,
                        this.zCoord + 0.5D,
                        colorsfx[0],
                        colorsfx[1],
                        colorsfx[2],
                        (float) Math.random() * 0.15F + 0.15F * size,
                        (float) (Math.random() - 0.5D) * 0.125F * size,
                        (float) (Math.random() - 0.5D) * 0.125F * size,
                        (float) (Math.random() - 0.5D) * 0.125F * size,
                        0.8f);
            }
        }
    }

    public static boolean isItemEqual(ItemStack stack, ItemStack stack1) {
        return stack.isItemEqual(stack1) && ItemStack.areItemStackTagsEqual(stack, stack1);
    }

    private int addItemStack(ItemStack stack) {
        for (int i = 1; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) == null) {
                ItemStack stackToAdd = stack.copy();
                setInventorySlotContents(i, stackToAdd);
                return stack.stackSize;
            } else if (isItemEqual(stack, getStackInSlot(i))) {
                if (getStackInSlot(i).stackSize < stack.getMaxStackSize()) {
                    int count = Math.min(stack.stackSize, stack.getMaxStackSize() - getStackInSlot(i).stackSize);
                    getStackInSlot(i).stackSize += count;
                    return count;
                }
            }
        }
        return 0;
    }

    public void writeCustomNBT(NBTTagCompound cmp) {
        super.writeCustomNBT(cmp);
        cmp.setInteger("mana", mana);
        cmp.setInteger("manaToGet", manaToGet);
        cmp.setBoolean("requestUpdate", requestUpdate);
        cmp.setInteger("recipeID", currentRecipe == null ? -1 : recipeID);
    }

    public void readCustomNBT(NBTTagCompound cmp) {
        super.readCustomNBT(cmp);
        this.mana = cmp.getInteger("mana");
        this.manaToGet = cmp.getInteger("manaToGet");
        this.requestUpdate = cmp.getBoolean("requestUpdate");
        int recipeID = cmp.getInteger("recipeID");
        if (recipeID == -1) this.currentRecipe = null;
        else this.currentRecipe = AdvancedBotanyAPI.FountainAlchemyRecipes.get(recipeID);
    }

    public int getSizeInventory() {
        // return 4;
        return 2;
    }

    public String getInventoryName() {
        return "tileFountainAlchemy";
    }

    public int getCurrentMana() {
        return this.mana;
    }

    public boolean isFull() {
        return this.mana >= this.manaToGet;
    }

    public void recieveMana(final int mana) {
        this.mana = Math.min(this.mana + mana, this.manaToGet);
    }

    public boolean canRecieveManaFromBursts() {
        return !this.isFull();
    }

    public boolean canAttachSpark(ItemStack stack) {
        return true;
    }

    public void attachSpark(ISparkEntity entity) {}

    public ISparkEntity getAttachedSpark() {
        List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(
                ISparkEntity.class,
                AxisAlignedBB.getBoundingBox(
                        this.xCoord,
                        (this.yCoord + 1),
                        this.zCoord,
                        (this.xCoord + 1),
                        (this.yCoord + 2),
                        (this.zCoord + 1)));
        if (sparks.size() == 1) {
            Entity e = (Entity) sparks.get(0);
            return (ISparkEntity) e;
        }
        return null;
    }

    public boolean areIncomingTranfersDone() {
        return this.isFull();
    }

    public int getAvailableSpaceForMana() {
        return Math.max(0, this.manaToGet - getCurrentMana());
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1 };
    }

    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        for (int i = 1; i < getSizeInventory(); i++) {
            ItemStack slotStack = getStackInSlot(i);
            if (slotStack != null && slotStack.stackSize == slotStack.getMaxStackSize()
                    && isItemEqual(stack, slotStack))
                return false;
        }
        return side == 1 && slot != 0;
    }

    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return (side == 0 && slot == 0) || (side != 0 && side != 1 && slot != 0);
    }
}
