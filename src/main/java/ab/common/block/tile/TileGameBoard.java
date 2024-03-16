package ab.common.block.tile;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import ab.api.IRenderHud;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.block.tile.TileMod;

public class TileGameBoard extends TileMod implements IRenderHud {

    public String[] playersName = new String[] { "", "" };
    public byte[] slotChance = new byte[] { 0, 0, 0, 0 };
    protected int botTick = -1;
    public int endGameTick = -1;
    protected boolean requestUpdate;
    public boolean isSingleGame = true;
    public boolean isCustomGame = false;
    public int clientTick[] = new int[] { 0, 0, 0, 0 };
    public static final ItemStack headRender = new ItemStack(Items.skull, 1, 3);
    private ItemStack customStack;

    public void updateEntity() {
        if (botTick > 0) botTick--;
        if (endGameTick > 0) endGameTick--;
        if (!worldObj.isRemote) updateServer();
        else updateAnimationTicks();
    }

    public void updateAnimationTicks() {
        for (int i = 0; i < slotChance.length; i++) if (slotChance[i] > 0) clientTick[i]++;
        else clientTick[i] = 0;
    }

    protected void updateServer() {
        if (hasGame() && endGameTick == 0 && !isCustomGame) finishGame();
        if (requestUpdate)
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        if (worldObj.getTotalWorldTime() % 20 == 0 && hasFullDice() && endGameTick == -1) endGameTick = 28;
        boolean hasUpdate = false;
        if (isSingleGame) {
            if (botTick == 0 && hasGame()) {
                for (int i = 2; i < 4; i++) {
                    if (slotChance[i] == 0) {
                        slotChance[i] = (byte) (worldObj.rand.nextInt(6) + 1);
                        botTick = -1;
                        hasUpdate = true;
                        worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "ab:boardCube", 0.6F, 1.0F);
                        break;
                    }
                }
            }
        } else {
            if (botTick == 0 && hasGame()) endGameTick = 0;
        }
        requestUpdate = hasUpdate;
    }

    public void setPlayer(String name, boolean isCustomGame) {
        this.isCustomGame = isCustomGame;
        if (isSingleGame) {
            playersName[0] = name;
            playersName[1] = "";
            requestUpdate = true;
            botTick = 8;
        } else {
            if (playersName[0].isEmpty()) playersName[0] = name;
            else if (!playersName[0].equals(name)) playersName[1] = name;
            requestUpdate = true;
        }
    }

    public void setPlayer(EntityPlayer player) {
        setPlayer(player.getCommandSenderName(), false);
    }

    public boolean dropDice(String name) {
        if (isSingleGame) {
            if (name.equals(playersName[0]) && botTick == -1) {
                boolean hasDrop = false;
                for (int i = 0; i < 2; i++) {
                    if (slotChance[i] == 0) {
                        hasDrop = true;
                        if (!worldObj.isRemote) {
                            slotChance[i] = (byte) (worldObj.rand.nextInt(6) + 1);
                            botTick = 18;
                            worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "ab:boardCube", 0.6F, 1.0F);
                            requestUpdate = true;
                        }
                        break;
                    }
                }
                return hasDrop;
            }
        } else {
            for (int i = 0; i < playersName.length; i++) {
                if (name.equals(playersName[i])) {
                    boolean hasDrop = false;
                    for (int j = i * 2; j < (i + 1) * 2; j++) {
                        if (slotChance[j] == 0) {
                            hasDrop = true;
                            if (!worldObj.isRemote) {
                                slotChance[j] = (byte) (worldObj.rand.nextInt(6) + 1);
                                botTick = playersName[1].isEmpty() ? 240 : 1200;
                                worldObj.playSoundEffect(
                                        this.xCoord,
                                        this.yCoord,
                                        this.zCoord,
                                        "ab:boardCube",
                                        0.6F,
                                        1.0F);
                                requestUpdate = true;
                            }
                            break;
                        }
                    }
                    return hasDrop;
                }
            }
        }
        return false;
    }

    public boolean dropDice(EntityPlayer player) {
        return dropDice(player.getCommandSenderName());
    }

    public boolean hasFullDice() {
        boolean hasFull = false;
        for (int i = 0; i < slotChance.length; i++) {
            if (!(slotChance[i] > 0)) return false;
            hasFull = slotChance[i] > 0;
        }
        return hasFull;
    }

    public boolean hasGame() {
        if (isSingleGame) return !playersName[0].isEmpty();
        else for (int i = 0; i < playersName.length; i++) if (!playersName[i].isEmpty()) return true;
        return false;
    }

    public void finishGame(boolean hasChatMessage) {
        if (worldObj.isRemote) return;
        else if (!hasChatMessage) {
            resetGame();
            return;
        } else if (!hasFullDice()) {
            sendNearMessage("ab.gameBoard.misc.notPlayer", new Object[] {});
            resetGame();
            return;
        }
        String str = isSingleGame ? "" : ".mult";
        if ((slotChance[0] + slotChance[1]) > (slotChance[2] + slotChance[3]))
            sendNearMessage("ab.gameBoard.misc.0" + str, new Object[] { playersName[0] });
        else if ((slotChance[0] + slotChance[1]) == (slotChance[2] + slotChance[3]))
            sendNearMessage("ab.gameBoard.misc.1" + str, new Object[] {});
        else sendNearMessage("ab.gameBoard.misc.2" + str, new Object[] { playersName[isSingleGame ? 0 : 1] });
        resetGame();
    }

    public void finishGame() {
        finishGame(true);
    }

    public void sendNearMessage(String text, Object[] obj) {
        List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(
                EntityPlayer.class,
                AxisAlignedBB.getBoundingBox(
                        this.xCoord,
                        this.yCoord,
                        this.zCoord,
                        (this.xCoord + 1),
                        (this.yCoord + 1),
                        (this.zCoord + 1)).expand(3.5f, 3.5f, 3.5f));
        for (EntityPlayer player : players) {
            if (player != null) player.addChatMessage(
                    (new ChatComponentTranslation(text, obj))
                            .setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.DARK_GREEN)));
        }
    }

    public boolean changeCustomStack(ItemStack stack) {
        if (!isCustomGame) return false;
        else if (customStack == null || !ItemStack.areItemStacksEqual(customStack, stack)) {
            customStack = stack;
            requestUpdate = true;
            return true;
        } else return false;
    }

    public void resetGame() {
        playersName[0] = "";
        playersName[1] = "";
        for (int i = 0; i < slotChance.length; i++) slotChance[i] = 0;
        botTick = -1;
        endGameTick = -1;
        isCustomGame = false;
        requestUpdate = true;
    }

    public void renderHud(Minecraft mc, ScaledResolution res) {
        int x = res.getScaledWidth() / 2 - 7;
        int y = res.getScaledHeight() / 2 + 12;
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem.getInstance().renderItemAndEffectIntoGUI(
                mc.fontRenderer,
                mc.renderEngine,
                isCustomGame ? (customStack == null ? headRender : customStack) : headRender,
                x - (!isSingleGame ? 1 : 0),
                y - (!isSingleGame ? 1 : 0));
        if (!isSingleGame) RenderItem.getInstance()
                .renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, headRender, x + 3, y + 3);
        RenderHelper.disableStandardItemLighting();
    }

    public void writeCustomNBT(NBTTagCompound cmp) {
        super.writeCustomNBT(cmp);
        for (int i = 0; i < playersName.length; i++) cmp.setString("playerName" + i, playersName[i]);
        cmp.setByteArray("slotChance", slotChance);
        cmp.setInteger("botTick", botTick);
        cmp.setInteger("endGameTick", endGameTick);
        cmp.setBoolean("requestUpdate", requestUpdate);
        cmp.setBoolean("isSingleGame", isSingleGame);
        cmp.setBoolean("isAnonimGame", isCustomGame);
        if (customStack != null) customStack.writeToNBT(cmp);
    }

    public void readCustomNBT(NBTTagCompound cmp) {
        super.readCustomNBT(cmp);
        for (int i = 0; i < playersName.length; i++) playersName[i] = cmp.getString("playerName" + i);
        botTick = cmp.getInteger("botTick");
        endGameTick = cmp.getInteger("endGameTick");
        slotChance = cmp.getByteArray("slotChance");
        requestUpdate = cmp.getBoolean("requestUpdate");
        isSingleGame = cmp.getBoolean("isSingleGame");
        isCustomGame = cmp.getBoolean("isAnonimGame");
        customStack = ItemStack.loadItemStackFromNBT(cmp);
    }
}
