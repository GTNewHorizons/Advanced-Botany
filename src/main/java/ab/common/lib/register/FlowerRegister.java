package ab.common.lib.register;

import ab.common.block.subtile.ABSubTileSignature;
import ab.common.block.subtile.SubTileAncientAlphirine;
import ab.common.block.subtile.SubTileArdentAzarcissus;
import ab.common.block.subtile.SubTileAspecolus;
import ab.common.block.subtile.SubTileDictarius;
import ab.common.block.subtile.SubTilePureGladiolus;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.Botania;

public class FlowerRegister {

    public static void init() {
        registerFlower(SubTileAncientAlphirine.class, "ancientAlphirine");
        registerFlower(SubTileDictarius.class, "dictarius");
        registerFlower(SubTileArdentAzarcissus.class, "ardentAzarcissus");
        if (Botania.thaumcraftLoaded) {
            registerFlower(SubTileAspecolus.class, "aspecolus");
            registerFlower(SubTilePureGladiolus.class, "pureGladiolus");
        }
    }

    static void registerFlower(Class<? extends SubTileEntity> subTile, String name) {
        BotaniaAPI.registerSubTile(name, subTile);
        BotaniaAPI.registerSubTileSignature(subTile, new ABSubTileSignature(name));
        BotaniaAPI.addSubTileToCreativeMenu(name);
    }
}
