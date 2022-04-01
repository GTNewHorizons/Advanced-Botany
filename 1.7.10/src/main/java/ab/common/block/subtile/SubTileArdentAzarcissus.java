package ab.common.block.subtile;

import ab.common.block.tile.TileGameBoard;
import ab.common.lib.register.RecipeListAB;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileArdentAzarcissus extends SubTileGenerating {
	
	public static final String playerName = "ArdentAzarcissus#21sda2gaj91*21df#111sfq3jrns@#";
	
	int cooldown;
	int workMana = 320;

	public void onUpdate() {
		super.onUpdate();
		World world = this.supertile.getWorldObj();
		int posX = this.supertile.xCoord;
		int posY = this.supertile.yCoord;
		int posZ = this.supertile.zCoord;
		if(world.isRemote)
			return;
		if(cooldown > 0) {
			cooldown--;
			return;
		} else if(this.mana == this.getMaxMana())
			return;
		boolean needSync = false;
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++) {
				int x = posX + i;
				int z = posZ + j;
				TileEntity tile = world.getTileEntity(x, posY, z);
				if(tile != null && tile instanceof TileGameBoard) {
					TileGameBoard board = (TileGameBoard)tile;
					if(board.isSingleGame) {
						if(!board.hasGame()) {
							board.setPlayer(playerName, true);
							cooldown = 120;
						} else {
							if(!board.playersName[0].equals(playerName))
								board.playersName[0] = playerName;
							if(!board.isCustomGame)
								board.isCustomGame = true;
							if(board.endGameTick == 0) {
								int winCount = (board.slotChance[0] + board.slotChance[1]) - (board.slotChance[2] + board.slotChance[3]);
								if(winCount > 0) {
									mana = Math.min(this.getMaxMana(), this.mana + (workMana * winCount));
									needSync = true;
								}
								board.finishGame(false);
							} else
								board.dropDice(playerName);
							cooldown = 120;
						}
					}
				}
			}
		if(needSync)
			sync();
	}
	
	public int getMaxMana() {
		return 16000;
	}
	
	public int getColor() {
		return 0xdf3596;
	}
	
	public LexiconEntry getEntry() {
		return RecipeListAB.azartFlower;
	}
	
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setInteger("cooldown", this.cooldown);
	}
		  
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		this.cooldown = cmp.getInteger("cooldown");
	}
}