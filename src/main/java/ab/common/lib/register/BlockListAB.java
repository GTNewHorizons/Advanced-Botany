package ab.common.lib.register;

import net.minecraft.block.Block;

import vazkii.botania.common.Botania;
import ab.common.block.BlockABSpreader;
import ab.common.block.BlockABStorage;
import ab.common.block.BlockAntigravitation;
import ab.common.block.BlockBoardFate;
import ab.common.block.BlockEngineerHopper;
import ab.common.block.BlockFountainAlchemy;
import ab.common.block.BlockFountainConjuration;
import ab.common.block.BlockFountainMana;
import ab.common.block.BlockFreyrLiana;
import ab.common.block.BlockLebethronWood;
import ab.common.block.BlockLuminousFreyrLiana;
import ab.common.block.BlockMagicCraftCrate;
import ab.common.block.BlockManaCharger;
import ab.common.block.BlockManaContainer;
import ab.common.block.BlockManaCrystalCube;
import ab.common.block.BlockNidavellirForge;
import ab.common.block.BlockTerraFarmland;
import ab.common.block.tile.TileABSpreader;
import ab.common.block.tile.TileBoardFate;
import ab.common.block.tile.TileEngineerHopper;
import ab.common.block.tile.TileFountainAlchemy;
import ab.common.block.tile.TileFountainConjuration;
import ab.common.block.tile.TileFountainMana;
import ab.common.block.tile.TileGameBoard;
import ab.common.block.tile.TileLebethronCore;
import ab.common.block.tile.TileMagicCraftCrate;
import ab.common.block.tile.TileManaCharger;
import ab.common.block.tile.TileManaContainer;
import ab.common.block.tile.TileManaCrystalCube;
import ab.common.block.tile.TileNidavellirForge;
import ab.common.core.handler.ConfigABHandler;
import ab.common.item.block.ItemBlockBase;
import ab.common.item.block.ItemBlockBoard;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockListAB {

    public static Block blockABSpreader;
    public static Block blockABPlate;
    public static Block blockABFountain;
    public static Block blockABAlchemy;
    public static Block blockABConjuration;
    public static Block blockLebethron;
    public static Block blockManaContainer;
    public static Block blockABStorage;
    public static Block blockManaCrystalCube;
    public static Block blockTerraFarmland;
    public static Block blockAntigravitation;
    public static Block blockManaCharger;
    public static Block blockEngineerHopper;
    public static Block blockFreyrLiana;
    public static Block blockLuminousFreyrLiana;
    public static Block blockMagicCraftCrate;
    public static Block blockBoardFate;

    public static int blockABSpreaderRI;
    public static int blockManaContainerRI;
    public static int blockManaCrystalCubeRI;
    public static int blockManaChargerRI;
    public static int blockEngineerHopperRI;
    public static int blockABPlateRI;
    public static int blockABFountainRI;
    public static int blockABAlchemyRI;
    public static int blockABConjurationRI;

    public static void init() {
        initializeBlocks();
        registerBlocks();
        registerTileEntities();
    }

    private static void initializeBlocks() {
        BlockListAB.blockABSpreader = new BlockABSpreader();
        BlockListAB.blockABPlate = new BlockNidavellirForge();
        BlockListAB.blockABFountain = new BlockFountainMana();
        BlockListAB.blockABAlchemy = new BlockFountainAlchemy();
        BlockListAB.blockABConjuration = new BlockFountainConjuration();
        BlockListAB.blockLebethron = new BlockLebethronWood();
        BlockListAB.blockManaContainer = new BlockManaContainer();
        BlockListAB.blockABStorage = new BlockABStorage();
        BlockListAB.blockManaCrystalCube = new BlockManaCrystalCube();
        BlockListAB.blockTerraFarmland = new BlockTerraFarmland();
        BlockListAB.blockAntigravitation = new BlockAntigravitation();
        BlockListAB.blockManaCharger = new BlockManaCharger();
        BlockListAB.blockEngineerHopper = new BlockEngineerHopper();
        BlockListAB.blockLuminousFreyrLiana = new BlockLuminousFreyrLiana();
        BlockListAB.blockFreyrLiana = new BlockFreyrLiana();
        BlockListAB.blockBoardFate = new BlockBoardFate();
        if (Botania.thaumcraftLoaded && ConfigABHandler.hasAutoThaum)
            BlockListAB.blockMagicCraftCrate = new BlockMagicCraftCrate();
    }

    private static void registerBlocks() {
        GameRegistry.registerBlock(BlockListAB.blockABSpreader, "blockABSpreader");
        GameRegistry.registerBlock(BlockListAB.blockABPlate, "blockABPlate");
        GameRegistry.registerBlock(BlockListAB.blockABFountain, "blockABFountain");
        GameRegistry.registerBlock(BlockListAB.blockABAlchemy, "blockABAlchemy");
        GameRegistry.registerBlock(BlockListAB.blockABConjuration, "blockABConjuration");
        GameRegistry.registerBlock(BlockListAB.blockManaContainer, ItemBlockBase.class, "blockManaContainer");
        GameRegistry.registerBlock(BlockListAB.blockLebethron, ItemBlockBase.class, "blockLebethron");
        GameRegistry.registerBlock(BlockListAB.blockABStorage, ItemBlockBase.class, "blockABStorage");
        GameRegistry.registerBlock(BlockListAB.blockManaCrystalCube, "blockManaCrystalCube");
        GameRegistry.registerBlock(BlockListAB.blockTerraFarmland, "blockTerraFarmland");
        GameRegistry.registerBlock(BlockListAB.blockAntigravitation, "blockAntigravitation");
        GameRegistry.registerBlock(BlockListAB.blockManaCharger, "blockManaCharger");
        GameRegistry.registerBlock(BlockListAB.blockEngineerHopper, "blockEngineerHopper");
        GameRegistry.registerBlock(BlockListAB.blockFreyrLiana, "blockFreyrLiana");
        GameRegistry.registerBlock(BlockListAB.blockLuminousFreyrLiana, "blockLuminousFreyrLiana");
        GameRegistry.registerBlock(BlockListAB.blockBoardFate, ItemBlockBoard.class, "blockBoardFate");
    }

    private static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileABSpreader.class, "tileABSpreader");
        GameRegistry.registerTileEntity(TileNidavellirForge.class, "tileAgglomerationPlate");
        GameRegistry.registerTileEntity(TileFountainMana.class, "tileFountainMana");
        GameRegistry.registerTileEntity(TileFountainAlchemy.class, "tileFountainAlchemy");
        GameRegistry.registerTileEntity(TileFountainConjuration.class, "tileFountainConjuration");
        GameRegistry.registerTileEntity(TileLebethronCore.class, "tileLebethronCore");
        GameRegistry.registerTileEntity(TileManaContainer.class, "tileManaContainer");
        GameRegistry.registerTileEntity(TileManaCrystalCube.class, "tileManaCrystalCube");
        GameRegistry.registerTileEntity(TileEngineerHopper.class, "tileEngineerHopper");
        if (ConfigABHandler.hasManaCharger) GameRegistry.registerTileEntity(TileManaCharger.class, "tileManaCharger");
        GameRegistry.registerTileEntity(TileBoardFate.class, "tileBoardFate");
        GameRegistry.registerTileEntity(TileGameBoard.class, "tileGameBoard");
        if (Botania.thaumcraftLoaded && ConfigABHandler.hasAutoThaum)
            GameRegistry.registerTileEntity(TileMagicCraftCrate.class, "tileMagicCraftCrate");
    }

    static {
        BlockListAB.blockABSpreaderRI = -1;
        BlockListAB.blockManaContainerRI = -1;
        BlockListAB.blockManaCrystalCubeRI = -1;
        BlockListAB.blockManaChargerRI = -1;
        BlockListAB.blockEngineerHopperRI = -1;
        BlockListAB.blockABPlateRI = -1;
        BlockListAB.blockABFountainRI = -1;
        BlockListAB.blockABAlchemyRI = -1;
        BlockListAB.blockABConjurationRI = -1;
    }
}
