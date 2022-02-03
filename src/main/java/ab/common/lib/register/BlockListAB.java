package ab.common.lib.register;

import ab.common.block.BlockABSpreader;
import ab.common.block.BlockABStorage;
import ab.common.block.BlockAgglomerationPlate;
import ab.common.block.BlockAntigravitation;
import ab.common.block.BlockCorporeaCatalyst;
import ab.common.block.BlockCorporeaInterrupter;
import ab.common.block.BlockFreyrLiana;
import ab.common.block.BlockLebethronWood;
import ab.common.block.BlockLuminousFreyrLiana;
import ab.common.block.BlockManaCharger;
import ab.common.block.BlockManaContainer;
import ab.common.block.BlockManaCrystalCube;
import ab.common.block.BlockModifiedFunnel;
import ab.common.block.BlockModifiedInterceptor;
import ab.common.block.BlockModifiedRetainer;
import ab.common.block.BlockTerraFarmland;
import ab.common.block.tile.TileABSpreader;
import ab.common.block.tile.TileAgglomerationPlate;
import ab.common.block.tile.TileCorporeaCatalyst;
import ab.common.block.tile.TileCorporeaInterrupter;
import ab.common.block.tile.TileLebethronCore;
import ab.common.block.tile.TileManaCharger;
import ab.common.block.tile.TileManaContainer;
import ab.common.block.tile.TileManaCrystalCube;
import ab.common.block.tile.TileModifiedCorporeaFunnel;
import ab.common.block.tile.TileModifiedInterceptor;
import ab.common.block.tile.TileModifiedRetainer;
import ab.common.item.block.ItemBlockBase;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockListAB {
	
	public static Block blockABSpreader;
	public static Block blockABPlate;
	public static Block blockLebethron;
	public static Block blockManaContainer;
	public static Block blockABStorage;
	public static Block blockManaCrystalCube;
	public static Block blockTerraFarmland;
	public static Block blockAntigravitation;
	public static Block blockManaCharger;
	
	public static Block blockFreyrLiana;
	public static Block blockLuminousFreyrLiana;
	
	public static BlockModifiedRetainer blockModifiedRetainer;
	public static BlockModifiedInterceptor blockModifiedInterceptor;
	public static BlockCorporeaCatalyst blockCorporeaCatalyst;
	public static BlockModifiedFunnel blockModifiedCorporeaFunnel;
	public static BlockCorporeaInterrupter blockCorporeaInterrupter;
	
	public static int blockABSpreaderRI;
	public static int blockManaContainerRI;
	public static int blockManaCrystalCubeRI;
	public static int blockManaChargerRI;
	
	public static int blockCorporeaCatalystRI;
	public static int blockModifiedCorporeaFunnelRI;
	public static int blockModifiedCorporeaInterrupterRI;
	public static int blockModifiedCorporeaInterceptorRI;
	
	public static void init() {
		initializeBlocks();
		registerBlocks();
		registerTileEntities();
	}
	
	private static void initializeBlocks() {
		BlockListAB.blockABSpreader = new BlockABSpreader();
		BlockListAB.blockABPlate = new BlockAgglomerationPlate();
		BlockListAB.blockLebethron = new BlockLebethronWood();
		BlockListAB.blockManaContainer = new BlockManaContainer();
		BlockListAB.blockABStorage = new BlockABStorage();
		BlockListAB.blockManaCrystalCube = new BlockManaCrystalCube();
		BlockListAB.blockTerraFarmland = new BlockTerraFarmland();
		BlockListAB.blockAntigravitation = new BlockAntigravitation();
		BlockListAB.blockManaCharger = new BlockManaCharger();
		
		//BlockListAB.blockLuminousFreyrLiana = new BlockLuminousFreyrLiana();
		//BlockListAB.blockFreyrLiana = new BlockFreyrLiana();
		
		//BlockListAB.blockModifiedCorporeaFunnel = new BlockModifiedFunnel();
		//BlockListAB.blockCorporeaCatalyst = new BlockCorporeaCatalyst();
		//BlockListAB.blockCorporeaInterrupter = new BlockCorporeaInterrupter();
		//BlockListAB.blockModifiedInterceptor = new BlockModifiedInterceptor();
		//BlockListAB.blockModifiedRetainer = new BlockModifiedRetainer();
	}
	
	private static void registerBlocks() {
		GameRegistry.registerBlock(BlockListAB.blockABSpreader, "blockABSpreader");
		GameRegistry.registerBlock(BlockListAB.blockABPlate, "blockABPlate");
		GameRegistry.registerBlock(BlockListAB.blockManaContainer, "blockManaContainer");
		GameRegistry.registerBlock(BlockListAB.blockLebethron, ItemBlockBase.class, "blockLebethron");
		GameRegistry.registerBlock(BlockListAB.blockABStorage, ItemBlockBase.class, "blockABStorage");
		GameRegistry.registerBlock(BlockListAB.blockManaCrystalCube, "blockManaCrystalCube");
		GameRegistry.registerBlock(BlockListAB.blockTerraFarmland, "blockTerraFarmland");
		GameRegistry.registerBlock(BlockListAB.blockAntigravitation, "blockAntigravitation");
		GameRegistry.registerBlock(BlockListAB.blockManaCharger, "blockManaCharger");
		
		//GameRegistry.registerBlock(BlockListAB.blockFreyrLiana, "blockFreyrLiana");
		//GameRegistry.registerBlock(BlockListAB.blockLuminousFreyrLiana, "blockLuminousFreyrLiana");
		
		//GameRegistry.registerBlock(BlockListAB.blockCorporeaCatalyst, "blockCorporeaCatalyst");
		//GameRegistry.registerBlock(BlockListAB.blockModifiedCorporeaFunnel, "blockModifiedCorporeaFunnel");
	}
	
	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileABSpreader.class, "tileABSpreader");
		GameRegistry.registerTileEntity(TileAgglomerationPlate.class, "tileAgglomerationPlate");
		GameRegistry.registerTileEntity(TileLebethronCore.class, "tileLebethronCore");
		GameRegistry.registerTileEntity(TileManaContainer.class, "tileManaContainer");
		GameRegistry.registerTileEntity(TileManaCrystalCube.class, "tileManaCrystalCube");
		GameRegistry.registerTileEntity(TileManaCharger.class, "tileManaCharger");
		
		//GameRegistry.registerTileEntity(TileCorporeaCatalyst.class, "tileCorporeaCatalyst");
		//GameRegistry.registerTileEntity(TileModifiedCorporeaFunnel.class, "tileModifiedCorporeaFunnel");
		//GameRegistry.registerTileEntity(TileCorporeaInterrupter.class, "tileCorporeaInterrupter");
		//GameRegistry.registerTileEntity(TileModifiedInterceptor.class, "tileModifiedInterceptor");
		//GameRegistry.registerTileEntity(TileModifiedRetainer.class, "tileModifiedRetainer");
	}
	
	static {
		BlockListAB.blockABSpreaderRI = -1;
		BlockListAB.blockManaContainerRI = -1;
		BlockListAB.blockManaCrystalCubeRI = -1;
		BlockListAB.blockManaChargerRI = -1;
		
		BlockListAB.blockModifiedCorporeaFunnelRI = -1;
		BlockListAB.blockCorporeaCatalystRI = -1;
		BlockListAB.blockModifiedCorporeaInterceptorRI = -1;
	}
}
