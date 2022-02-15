package ab.common.lib.register;

import ab.common.block.subtile.ABSubTileSignature;
import ab.common.block.subtile.SubTileAncientAlphirine;
import ab.common.block.subtile.SubTileAngryMallos;
import ab.common.block.subtile.SubTileAspecolus;
import ab.common.block.subtile.SubTileDictarius;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.Botania;

public class FlowerRegister {

	public static void init() {
		registerFlower(SubTileAncientAlphirine.class, "ancientAlphirine");
		registerFlower(SubTileDictarius.class, "dictarius");
	//	registerFlower(SubTileAngryMallos.class, "angryMallos");
		if(Botania.thaumcraftLoaded) {
			registerFlower(SubTileAspecolus.class, "aspecolus");
		}
	}
	
	static void registerFlower(Class <? extends SubTileEntity> subTile, String name) {
		BotaniaAPI.registerSubTile(name, subTile);
		BotaniaAPI.registerSubTileSignature(subTile, new ABSubTileSignature(name));
		BotaniaAPI.addSubTileToCreativeMenu(name);
	}
}
