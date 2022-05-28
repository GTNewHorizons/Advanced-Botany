package pulxes.ab.common.lib.register;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import pulxes.ab.common.block.subtile.SubTileAncientAlphirine;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.api.subtile.signature.BasicSignature;

public class FlowerListAB {
	
	public static final String ANCIENT_ALPHIRINE = "ancient_alphirine";

	public static void init() {
		registerFlower(SubTileAncientAlphirine.class, ANCIENT_ALPHIRINE);
	}
	
	static void registerFlower(Class <? extends SubTileEntity> subTile, String name) {
		BotaniaAPI.registerSubTile(name, subTile);
		BotaniaAPI.registerSubTileSignature(subTile, new BasicSignature(name));
		BotaniaAPI.addSubTileToCreativeMenu(name);
		BotaniaAPIClient.registerSubtileModel(subTile, new ModelResourceLocation("botania:" + name)); 
	}
}
