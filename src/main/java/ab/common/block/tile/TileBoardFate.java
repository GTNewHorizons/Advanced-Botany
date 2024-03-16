package ab.common.block.tile;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;

import ab.api.AdvancedBotanyAPI;
import ab.common.core.handler.ConfigABHandler;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.item.relic.ItemRelic;

public class TileBoardFate extends TileInventory {

    public byte[] slotChance = new byte[getSizeInventory()];
    public int clientTick[] = new int[] { 0, 0, 0, 0 };
    public boolean requestUpdate;

    public void updateEntity() {
        if (!worldObj.isRemote) updateServer();
        else updateAnimationTicks();
    }

    public void updateAnimationTicks() {
        for (int i = 0; i < getSizeInventory(); i++) if (getStackInSlot(i) != null) clientTick[i]++;
        else clientTick[i] = 0;
    }

    protected void updateServer() {
        if (requestUpdate)
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        boolean hasUpdate = false;
        if (hasFreeSlot()) hasUpdate = setDiceFate();
        requestUpdate = hasUpdate;
    }

    public boolean spawnRelic(EntityPlayer player) {
        int relicCount = 0;
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) == null) slotChance[i] = 0;
            else if (!ItemRelic.isRightPlayer(player, getStackInSlot(i))) {
                if (!worldObj.isRemote) dropRelic(player, i);
                return true;
            } else setInventorySlotContents(i, null);
            relicCount += slotChance[i];
        }
        if (relicCount < 1) return false;
        else if (!worldObj.isRemote) {
            ItemStack relic = AdvancedBotanyAPI.relicList
                    .get(Math.min(relicCount - 1, AdvancedBotanyAPI.relicList.size() - 1)).copy();
            worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (worldObj.rand.nextFloat() * 0.4F + 0.8F));
            if (hasRelicAchievement(player, relic) || !ConfigABHandler.fateBoardRelicEnables[relicCount - 1]) {
                player.addChatMessage(
                        (new ChatComponentTranslation(
                                "botaniamisc.dudDiceRoll",
                                new Object[] { Integer.valueOf(relicCount) }))
                                        .setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.DARK_GREEN)));
            } else {
                ItemRelic.updateRelic(relic, player);
                EntityItem entityItem = new EntityItem(worldObj, xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, relic);
                player.addChatMessage(
                        (new ChatComponentTranslation(
                                "botaniamisc.diceRoll",
                                new Object[] { Integer.valueOf(relicCount) }))
                                        .setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.DARK_GREEN)));
                worldObj.spawnEntityInWorld(entityItem);
            }
            requestUpdate = true;
        }
        return true;
    }

    private void dropRelic(EntityPlayer player, int slot) {
        EntityItem entityItem = new EntityItem(
                worldObj,
                xCoord + 0.5f,
                yCoord + 0.8f,
                zCoord + 0.5f,
                getStackInSlot(slot).copy());
        float f3 = 0.15F;
        Vec3 vec = player.getLookVec();
        entityItem.motionX = vec.xCoord * f3;
        entityItem.motionY = 0.25F;
        entityItem.motionZ = vec.zCoord * f3;
        setInventorySlotContents(slot, null);
        worldObj.spawnEntityInWorld(entityItem);
    }

    public static boolean hasRelicAchievement(EntityPlayer player, ItemStack rStack) {
        EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
        IRelic irelic = (IRelic) rStack.getItem();
        if (irelic.getBindAchievement() == null) return false;
        return mpPlayer.func_147099_x().hasAchievementUnlocked(irelic.getBindAchievement());
    }

    protected boolean hasFreeSlot() {
        for (int i = 0; i < getSizeInventory(); i++) if (getStackInSlot(i) == null) return true;
        return false;
    }

    protected boolean setDiceFate() {
        boolean hasUpdate = false;
        List<EntityItem> items = this.worldObj.getEntitiesWithinAABB(
                EntityItem.class,
                AxisAlignedBB.getBoundingBox(
                        this.xCoord,
                        this.yCoord,
                        this.zCoord,
                        (this.xCoord + 1),
                        (this.yCoord + 0.7f),
                        (this.zCoord + 1)));
        label666: for (EntityItem item : items) {
            if (!item.isDead && item.getEntityItem() != null) {
                ItemStack stack = item.getEntityItem();
                if (!isDice(stack)) continue;
                for (int s = 0; s < getSizeInventory(); s++) {
                    ItemStack slotStack = getStackInSlot(s);
                    if (slotStack != null) continue;
                    ItemStack copy = stack.copy();
                    copy.stackSize = 1;
                    setInventorySlotContents(s, copy);
                    slotChance[s] = (byte) (worldObj.rand.nextInt(6) + 1);
                    stack.stackSize--;
                    if (stack.stackSize == 0) item.setDead();
                    hasUpdate = true;
                    worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "ab:boardCube", 0.6F, 1.0F);
                    break label666;
                }
            }
        }
        return hasUpdate;
    }

    public static boolean isDice(ItemStack stack) {
        for (ItemStack dice : AdvancedBotanyAPI.diceList) {
            if (dice.getItem() == stack.getItem()
                    && (dice.getItemDamage() == stack.getItemDamage() || dice.getItemDamage() == 32767))
                return true;
        }
        return false;
    }

    public void writeCustomNBT(NBTTagCompound cmp) {
        super.writeCustomNBT(cmp);
        cmp.setByteArray("slotChance", slotChance);
        cmp.setBoolean("requestUpdate", requestUpdate);
    }

    public void readCustomNBT(NBTTagCompound cmp) {
        super.readCustomNBT(cmp);
        slotChance = cmp.getByteArray("slotChance");
        requestUpdate = cmp.getBoolean("requestUpdate");
    }

    public int getInventoryStackLimit() {
        return 1;
    }

    public String getInventoryName() {
        return "inv.boardFate";
    }

    public int getSizeInventory() {
        return 2;
    }
}
