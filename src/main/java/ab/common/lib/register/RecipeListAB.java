package ab.common.lib.register;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import thaumcraft.*;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import vazkii.*;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.ModFluffBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.BLexiconCategory;
import vazkii.botania.common.lexicon.BLexiconEntry;
import vazkii.botania.common.lexicon.RLexiconEntry;
import vazkii.botania.common.lexicon.page.PageImage;
import vazkii.botania.common.lexicon.page.PageText;
import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAdvancedPlate;
import ab.api.recipe.RecipeAncientAlphirine;
import ab.api.recipe.lexicon.AdvancedPlateCraftPage;
import ab.api.recipe.lexicon.AlphirineCraftPage;
import ab.common.block.tile.TileLebethronCore;
import ab.common.core.handler.ConfigABHandler;
import ab.common.item.ItemCraftingPattern;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.ToolDictNames;

public class RecipeListAB {

    public static LexiconCategory categoryForgotten;

    public static RecipeAncientAlphirine lebethroneRecipe;
    public static RecipeAncientAlphirine forgottenLandRecipe;
    public static RecipeAncientAlphirine advancedSparkRecipe;
    public static RecipeAncientAlphirine manaFlowerRecipe;
    public static RecipeAncientAlphirine hopperRecipe;
    public static RecipeAncientAlphirine fateBoardRecipe;

    public static RecipeAdvancedPlate manaStarRecipe;
    public static RecipeAdvancedPlate terrasteelRecipe;
    public static RecipeAdvancedPlate terrasteelBlockRecipe;
    public static RecipeAdvancedPlate mithrillRecipe;
    public static RecipeAdvancedPlate nebulaRecipe;

    public static RecipePetals alphirineRecipe;
    public static RecipePetals dictariusRecipe;
    public static RecipePetals aspecolusRecipe;
    public static RecipePetals pureGladRecipe;
    public static RecipePetals azartFlowerRecipe;

    public static LexiconEntry advandedAgglomerationPlate;
    public static LexiconEntry ancientAlphirine;
    public static LexiconEntry lebethronWood;
    public static LexiconEntry lebethronSpreader;
    public static LexiconEntry mithrill;
    public static LexiconEntry manaContainer;
    public static LexiconEntry manaCrystalCube;
    public static LexiconEntry terraHoe;
    public static LexiconEntry manaRings;
    public static LexiconEntry manaFlower;
    public static LexiconEntry mithrillSword;
    public static LexiconEntry nebula;
    public static LexiconEntry blackHalo;
    public static LexiconEntry antigravityCharm;
    public static LexiconEntry nebulaBlaze;
    public static LexiconEntry aspecolus;
    public static LexiconEntry dictarius;
    public static LexiconEntry manaCharger;
    public static LexiconEntry nebulaArmor;
    public static LexiconEntry freyrSlingshot;
    public static LexiconEntry engineerHopper;
    public static LexiconEntry nebulaRod;
    public static LexiconEntry thaumAutoCraft;
    public static LexiconEntry gladious;
    public static LexiconEntry gameBoard;
    public static LexiconEntry fateBoard;
    public static LexiconEntry richesKey;
    public static LexiconEntry cubeWardrobe;
    public static LexiconEntry sphereNavigation;
    public static LexiconEntry hornPlenty;
    public static LexiconEntry sprawlRod;
    public static LexiconEntry azartFlower;
    public static LexiconEntry aquaSword;

    public static KnowledgeType forgotten;

    public static void init() {
        relicInit();

        BotaniaAPI.addCategory(categoryForgotten = new BLexiconCategory("forgotten", 5));
        forgotten = BotaniaAPI.registerKnowledgeType("ab_forgotten", EnumChatFormatting.BLUE, false);

        ///////////////////////////////////////////////////////////////////////////////////////// Ancient Alphirine
        ///////////////////////////////////////////////////////////////////////////////////////// Recipes

        lebethroneRecipe = AdvancedBotanyAPI.registerAlphirineRecipe(
                new ItemStack(BlockListAB.blockLebethron),
                new ItemStack(ModBlocks.dreamwood, 1, 0),
                15);
        forgottenLandRecipe = AdvancedBotanyAPI.registerAlphirineRecipe(
                new ItemStack(ItemListAB.itemABResource, 1, 3),
                new ItemStack(ModItems.manaResource, 1, 15),
                75);
        advancedSparkRecipe = AdvancedBotanyAPI.registerAlphirineRecipe(
                new ItemStack(ItemListAB.itemAdvancedSpark),
                new ItemStack(ModItems.spark),
                11);
        manaFlowerRecipe = AdvancedBotanyAPI.registerAlphirineRecipe(
                new ItemStack(ItemListAB.itemABResource, 1, 4),
                new ItemStack(ModBlocks.flower, 1, 32767),
                32);
        hopperRecipe = AdvancedBotanyAPI.registerAlphirineRecipe(
                new ItemStack(BlockListAB.blockEngineerHopper),
                new ItemStack(Blocks.hopper),
                23);
        fateBoardRecipe = AdvancedBotanyAPI.registerAlphirineRecipe(
                new ItemStack(BlockListAB.blockBoardFate, 1, 1),
                new ItemStack(BlockListAB.blockBoardFate),
                60);

        ///////////////////////////////////////////////////////////////////////////////////////// Forge of Nidavellir
        ///////////////////////////////////////////////////////////////////////////////////////// Recipes

        mithrillRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                new ItemStack(ItemListAB.itemABResource, 1, 0),
                new ItemStack(ModItems.manaResource, 1, 14),
                new ItemStack(ModBlocks.storage, 1, 0),
                new ItemStack(ModBlocks.storage, 1, 1),
                2500000,
                0x25d6b7);
        terrasteelRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                new ItemStack(ModItems.manaResource, 1, 4),
                new ItemStack(ModItems.manaResource, 1, 2),
                new ItemStack(ModItems.manaResource, 1, 0),
                new ItemStack(ModItems.manaResource, 1, 1),
                500000,
                0x29de20);
        manaStarRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                new ItemStack(ItemListAB.itemABResource, 1, 2),
                new ItemStack(ModItems.manaResource, 1, 23),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(Items.nether_star),
                250000,
                0x6bc9ec);
        nebulaRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                new ItemStack(ItemListAB.itemABResource, 1, 5),
                new ItemStack(ModItems.rainbowRod),
                new ItemStack(ModBlocks.storage, 1, 4),
                new ItemStack(BlockListAB.blockABStorage, 1, 0),
                25000000,
                0x8d16e0);
        terrasteelBlockRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                new ItemStack(ModBlocks.storage, 1, 1),
                new ItemStack(ModItems.rune, 1, 8),
                new ItemStack(ModBlocks.storage, 1, 0),
                new ItemStack(ModBlocks.storage, 1, 3),
                4500000,
                0x29de21);

        ///////////////////////////////////////////////////////////////////////////////////////// Shaped Recipes

        // Ancient alphirine recipe
        ancientAlphirine = new BLexiconEntry("ancientAlphirine", categoryForgotten);
        alphirineRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("ancientAlphirine"),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(ModItems.rune, 1, 8),
                new ItemStack(ModItems.rune, 1, 4),
                new ItemStack(ModItems.rune, 1, 5),
                new ItemStack(ModItems.rune, 1, 6),
                new ItemStack(ModItems.rune, 1, 7),
                new ItemStack(ModItems.petal, 1, 1),
                new ItemStack(ModItems.petal, 1, 4));
        ancientAlphirine.setPriority().setKnowledgeType(BotaniaAPI.elvenKnowledge).setLexiconPages(
                new LexiconPage[] { new PageText("0"), new PageText("1"),
                        new PageImage("2", "ab:textures/misc/ancientAlphirine.png"),
                        BotaniaAPI.internalHandler.petalRecipePage(".petalCraft", alphirineRecipe),
                        new AlphirineCraftPage(ancientAlphirine, forgottenLandRecipe.getOutput(), ".alphirineCraft") })
                .setIcon(ItemBlockSpecialFlower.ofType("ancientAlphirine"));

        // Forge of Nidavellir recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockABPlate),
                new Object[] { "MPM", " F ", "BRB", Character.valueOf('F'), new ItemStack(Blocks.anvil),
                        Character.valueOf('M'), new ItemStack(ModBlocks.storage), Character.valueOf('P'),
                        new ItemStack(ModBlocks.terraPlate), Character.valueOf('B'),
                        new ItemStack(ItemListAB.itemABResource, 1, 3), Character.valueOf('R'),
                        new ItemStack(ModItems.rune, 1, 3) });
        advandedAgglomerationPlate = new BLexiconEntry("advancedPlate", categoryForgotten);
        advandedAgglomerationPlate.setPriority().setKnowledgeType(forgotten)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()),
                                new AdvancedPlateCraftPage(
                                        advandedAgglomerationPlate,
                                        terrasteelRecipe.getOutput(),
                                        ".abCraft0"),
                                new AdvancedPlateCraftPage(
                                        advandedAgglomerationPlate,
                                        manaStarRecipe.getOutput(),
                                        ".abCraft1") })
                .setIcon(new ItemStack(BlockListAB.blockABPlate));

        // Natural mana spreader recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockABSpreader),
                new Object[] { "WMW", "PSP", "WMW", Character.valueOf('M'),
                        new ItemStack(ItemListAB.itemABResource, 1, 2), Character.valueOf('P'),
                        new ItemStack(ModBlocks.pylon), Character.valueOf('S'), new ItemStack(ModBlocks.spreader, 1, 3),
                        Character.valueOf('W'), new ItemStack(BlockListAB.blockLebethron, 1, 4) });
        lebethronSpreader = new BLexiconEntry("lebethronSpreader", categoryForgotten);
        lebethronSpreader.setKnowledgeType(forgotten)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(BlockListAB.blockABSpreader));

        // Lebethorn block variants recipes
        addShapelessOreDictRecipe(
                new ItemStack(BlockListAB.blockLebethron, 4, 1),
                new Object[] { new ItemStack(BlockListAB.blockLebethron) });
        IRecipe leb1 = getLastRecipe();
        addShapelessOreDictRecipe(
                new ItemStack(BlockListAB.blockLebethron, 1, 2),
                new Object[] { new ItemStack(BlockListAB.blockLebethron, 1, 1), new ItemStack(Items.wheat_seeds) });
        IRecipe leb2 = getLastRecipe();
        addShapelessOreDictRecipe(
                new ItemStack(BlockListAB.blockLebethron, 1, 3),
                new Object[] { new ItemStack(BlockListAB.blockLebethron), new ItemStack(Items.glowstone_dust) });
        IRecipe leb3 = getLastRecipe();
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockLebethron, 1, 4),

                // Natural core recipe
                new Object[] { "4W5", "WPW", "6W7", Character.valueOf('P'), new ItemStack(ModBlocks.pylon, 1, 1),
                        Character.valueOf('W'), new ItemStack(BlockListAB.blockLebethron), Character.valueOf('4'),
                        new ItemStack(ModItems.rune, 1, 4), Character.valueOf('5'), new ItemStack(ModItems.rune, 1, 5),
                        Character.valueOf('6'), new ItemStack(ModItems.rune, 1, 6), Character.valueOf('7'),
                        new ItemStack(ModItems.rune, 1, 7) });
        IRecipe leb4 = getLastRecipe();
        lebethronWood = new BLexiconEntry("lebethronWood", categoryForgotten);
        lebethronWood.setKnowledgeType(forgotten)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                new AlphirineCraftPage(lebethronWood, lebethroneRecipe.getOutput(), ".alphirineCraft"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft0", leb1),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft0", leb2),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft0", leb3),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft0", leb4), new PageText("1"),
                                BotaniaAPI.internalHandler
                                        .multiblockPage(".structure", TileLebethronCore.makeMultiblockSet()) })
                .setIcon(new ItemStack(BlockListAB.blockLebethron));

        // AB Mithrill recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemMihrillMultiTool),
                new Object[] { " MB", " FM", "F  ", Character.valueOf('F'), new ItemStack(ModItems.manaResource, 1, 3),
                        Character.valueOf('B'), new ItemStack(ModBlocks.livingwood), Character.valueOf('M'),
                        new ItemStack(ItemListAB.itemABResource) });
        mithrill = new BLexiconEntry("mithrill", categoryForgotten);
        mithrill.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        new AdvancedPlateCraftPage(mithrill, mithrillRecipe.getOutput(), ".abCraft"), new PageText("1"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(ItemListAB.itemMihrillMultiTool));

        // Advanced mana containers recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockManaContainer),
                new Object[] { "MGM", "LPL", "M3M", Character.valueOf('M'), new ItemStack(ItemListAB.itemABResource),
                        Character.valueOf('L'), new ItemStack(ModBlocks.pool), Character.valueOf('P'),
                        new ItemStack(ModBlocks.pylon), Character.valueOf('3'), new ItemStack(ModItems.rune, 1, 3),
                        Character.valueOf('G'), new ItemStack(ModItems.rune, 1, 15) });
        IRecipe cont1 = getLastRecipe();
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockManaContainer, 1, 1),
                new Object[] { "MGM", "LPL", "M3M", Character.valueOf('M'),
                        new ItemStack(ItemListAB.itemABResource, 1, 1), Character.valueOf('L'),
                        new ItemStack(ModBlocks.pool, 1, 2), Character.valueOf('P'), new ItemStack(ModBlocks.pylon),
                        Character.valueOf('3'), new ItemStack(ModItems.rune, 1, 3), Character.valueOf('G'),
                        new ItemStack(ModItems.rune, 1, 15) });
        IRecipe cont2 = getLastRecipe();
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockManaContainer, 1, 2),
                new Object[] { "MGM", "LPL", "M3M", Character.valueOf('M'), new ItemStack(ItemListAB.itemABResource),
                        Character.valueOf('L'), new ItemStack(ModBlocks.pool, 1, 3), Character.valueOf('P'),
                        new ItemStack(ModBlocks.pylon), Character.valueOf('3'), new ItemStack(ModItems.rune, 1, 3),
                        Character.valueOf('G'), new ItemStack(ModItems.rune, 1, 15) });
        IRecipe cont3 = getLastRecipe();
        manaContainer = new BLexiconEntry("manaContainer", categoryForgotten);
        manaContainer.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), BotaniaAPI.internalHandler.craftingRecipePage(".craft0", cont1),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft1", cont2),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft2", cont3), new PageText("1"),
                        new AlphirineCraftPage(manaContainer, advancedSparkRecipe.getOutput(), ".alphirineCraft") })
                .setIcon(new ItemStack(BlockListAB.blockManaContainer));

        // Mana crystal cube recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockManaCrystalCube),
                new Object[] { "TST", "GWG", "DMD", Character.valueOf('D'), new ItemStack(ModBlocks.dreamwood),
                        Character.valueOf('W'), new ItemStack(ModItems.twigWand, 1, 32767), Character.valueOf('G'),
                        new ItemStack(ModBlocks.manaGlass), Character.valueOf('M'),
                        new ItemStack(ModItems.manaResource), Character.valueOf('T'),
                        new ItemStack(ModItems.manaResource, 1, 18), Character.valueOf('S'),
                        new ItemStack(ModItems.spark) });
        manaCrystalCube = new BLexiconEntry("manaCrystalCube", BotaniaAPI.categoryMana);
        manaCrystalCube.setKnowledgeType(BotaniaAPI.elvenKnowledge)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(BlockListAB.blockManaCrystalCube));

        // Terrahoe recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemTerraHoe),
                new Object[] { "TTL", "RW ", " W ", Character.valueOf('T'), new ItemStack(ModItems.manaResource, 1, 4),
                        Character.valueOf('L'), new ItemStack(ModItems.manaResource, 1, 18), Character.valueOf('W'),
                        new ItemStack(ModItems.manaResource, 1, 3), Character.valueOf('R'),
                        new ItemStack(ModItems.rune, 1, 2) });
        terraHoe = new BLexiconEntry("terraHoe", BotaniaAPI.categoryTools);
        terraHoe.setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(ItemListAB.itemTerraHoe));

        // Mithrill recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemMithrillRing),
                new Object[] { "LML", "MRM", "LML", Character.valueOf('M'), new ItemStack(ItemListAB.itemABResource),
                        Character.valueOf('L'), new ItemStack(ItemListAB.itemABResource, 1, 1), Character.valueOf('R'),
                        new ItemStack(ModItems.manaRingGreater) });
        IRecipe ring1 = getLastRecipe();

        // Nebula ring recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemNebulaRing),
                new Object[] { "LML", "MRM", "LML", Character.valueOf('M'),
                        new ItemStack(ItemListAB.itemABResource, 1, 6), Character.valueOf('L'),
                        new ItemStack(ModItems.manaResource, 1, 5), Character.valueOf('R'),
                        new ItemStack(ItemListAB.itemMithrillRing) });
        IRecipe ring2 = getLastRecipe();
        manaRings = new BLexiconEntry("manaRings", categoryForgotten);
        manaRings.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), BotaniaAPI.internalHandler.craftingRecipePage(".craft0", ring1),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft1", ring2) })
                .setIcon(new ItemStack(ItemListAB.itemMithrillRing));
        AdvancedBotanyAPI.registerFarmlandSeed(Blocks.nether_wart, 3);

        // Blade of space recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemMithrillSword),
                new Object[] { "  M", "SM ", "WS ", Character.valueOf('S'),
                        new ItemStack(ItemListAB.itemABResource, 1, 6), Character.valueOf('T'),
                        new ItemStack(ModItems.terraSword), Character.valueOf('M'),
                        new ItemStack(ItemListAB.itemABResource), Character.valueOf('W'),
                        new ItemStack(ModItems.manaResource, 1, 3) });
        mithrillSword = new BLexiconEntry("mithrillSword", categoryForgotten);
        mithrillSword.setKnowledgeType(forgotten)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"), new PageText("1"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(ItemListAB.itemMithrillSword));

        // Dictarius recipe
        dictariusRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("dictarius"),
                new ItemStack(ItemListAB.itemABResource, 1, 4),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(ModItems.rune, 1, 15),
                new ItemStack(ModItems.petal, 1, 4),
                new ItemStack(ModItems.petal, 1, 4),
                new ItemStack(ModItems.petal, 1, 1));
        dictarius = new BLexiconEntry("dictarius", BotaniaAPI.categoryGenerationFlowers);
        dictarius.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), new PageText("1"),
                        BotaniaAPI.internalHandler.petalRecipePage(".petalCraft", dictariusRecipe) });

        nebula = new BLexiconEntry("nebula", categoryForgotten);
        nebula.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        new AdvancedPlateCraftPage(nebula, nebulaRecipe.getOutput(), ".abCraft") });

        // Black hole box recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemBlackHalo),
                new Object[] { " E ", "DHD", " E ", Character.valueOf('E'), new ItemStack(ModItems.manaResource, 1, 7),
                        Character.valueOf('H'), new ItemStack(ModItems.autocraftingHalo), Character.valueOf('D'),
                        new ItemStack(ModItems.blackHoleTalisman) });
        blackHalo = new BLexiconEntry("blackHalo", BotaniaAPI.categoryTools);
        blackHalo.setKnowledgeType(BotaniaAPI.elvenKnowledge).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        // Sphere of Attraction recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemAntigravityCharm),
                new Object[] { " G ", "GDG", "ERE", Character.valueOf('E'), new ItemStack(ModItems.manaResource, 1, 7),
                        Character.valueOf('G'), new ItemStack(ModBlocks.elfGlass), Character.valueOf('D'),
                        new ItemStack(ModBlocks.floatingFlower, 1, 32767), Character.valueOf('R'),
                        new ItemStack(ModItems.rune, 1, 3) });
        antigravityCharm = new BLexiconEntry("antigravityCharm", BotaniaAPI.categoryTools);
        antigravityCharm.setKnowledgeType(BotaniaAPI.elvenKnowledge)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(ItemListAB.itemAntigravityCharm));

        // Nebula blaze recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemNebulaBlaze),
                new Object[] { " N ", "MMN", "AMN", Character.valueOf('N'),
                        new ItemStack(ItemListAB.itemABResource, 1, 6), Character.valueOf('A'),
                        new ItemStack(ItemListAB.itemABResource, 1, 3), Character.valueOf('M'),
                        new ItemStack(ModBlocks.storage) });
        nebulaBlaze = new BLexiconEntry("nebulaBlaze", categoryForgotten);
        nebulaBlaze.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        // Mana charger recipe
        if (ConfigABHandler.hasManaCharger) {
            GameRegistry.addShapedRecipe(
                    new ItemStack(BlockListAB.blockManaCharger),
                    new Object[] { "NLN", "MPM", " R ", Character.valueOf('N'),
                            new ItemStack(ItemListAB.itemABResource, 1, 1), Character.valueOf('L'),
                            new ItemStack(ModBlocks.livingrock), Character.valueOf('M'),
                            new ItemStack(ModBlocks.bellows), Character.valueOf('P'), new ItemStack(ModBlocks.pylon),
                            Character.valueOf('R'), new ItemStack(ModItems.rune, 1, 8) });
            manaCharger = new BLexiconEntry("manaCharger", categoryForgotten);
            manaCharger.setKnowledgeType(forgotten).setLexiconPages(
                    new LexiconPage[] { new PageText("0"),
                            BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });
        }

        ///////////////////////////////////////////////////////////////////////////////////////// Nebula set recipes

        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemNebulaHelm),
                new Object[] { "RNR", "NHN", " E ", Character.valueOf('N'),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), Character.valueOf('R'),
                        new ItemStack(ModItems.manaResource, 1, 7), Character.valueOf('H'),
                        new ItemStack(ModItems.elementiumHelm), Character.valueOf('E'),
                        new ItemStack(ModItems.manaResource, 1, 8) });
        IRecipe nHelm = getLastRecipe();
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemNebulaChest),
                new Object[] { "RTR", "NHN", "ENE", Character.valueOf('N'),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), Character.valueOf('R'),
                        new ItemStack(ModItems.manaResource, 1, 7), Character.valueOf('H'),
                        new ItemStack(ModItems.elementiumChest), Character.valueOf('E'),
                        new ItemStack(ModItems.manaResource, 1, 8), Character.valueOf('T'),
                        new ItemStack(ModItems.flightTiara, 1, 32767) });
        IRecipe nChest = getLastRecipe();
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemNebulaLegs),
                new Object[] { "ENE", "RHR", "NAN", Character.valueOf('N'),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), Character.valueOf('R'),
                        new ItemStack(ModItems.manaResource, 1, 7), Character.valueOf('H'),
                        new ItemStack(ModItems.elementiumLegs), Character.valueOf('E'),
                        new ItemStack(ModItems.manaResource, 1, 8), Character.valueOf('A'),
                        new ItemStack(ItemListAB.itemABResource, 1, 3) });
        IRecipe nLegs = getLastRecipe();
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemNebulaBoots),
                new Object[] { "RBR", "NHN", " E ", Character.valueOf('N'),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), Character.valueOf('R'),
                        new ItemStack(ModItems.manaResource, 1, 7), Character.valueOf('H'),
                        new ItemStack(ModItems.elementiumBoots), Character.valueOf('E'),
                        new ItemStack(ModItems.manaResource, 1, 8), Character.valueOf('B'),
                        new ItemStack(ModItems.speedUpBelt) });
        IRecipe nBoots = getLastRecipe();
        nebulaArmor = new BLexiconEntry("nebulaArmor", categoryForgotten);
        nebulaArmor.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), new PageText("1"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft0", nHelm),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft1", nChest),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft2", nLegs),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft3", nBoots) });

        // Enginerr hopper recipe
        engineerHopper = new BLexiconEntry("engineerHopper", categoryForgotten);
        engineerHopper.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), new PageImage("1", "ab:textures/misc/engineerHopper.png"),
                        new PageText("2"), new PageText("3"), new PageText("4"),
                        new AlphirineCraftPage(engineerHopper, hopperRecipe.getOutput(), ".alphirineCraft") });

        // Rod of nebula recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemNebulaRod),
                new Object[] { " WN", " RW", "W  ", Character.valueOf('W'), new ItemStack(ModItems.manaResource, 1, 13),
                        Character.valueOf('R'), new ItemStack(ModItems.rune, 1, 8), Character.valueOf('N'),
                        new ItemStack(ItemListAB.itemABResource, 1, 6) });
        nebulaRod = new BLexiconEntry("nebulaRod", categoryForgotten);
        nebulaRod.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        // Playing board recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockBoardFate),
                new Object[] { "   ", "QDQ", "MPM", Character.valueOf('Q'), new ItemStack(ModFluffBlocks.manaQuartz),
                        Character.valueOf('D'), new ItemStack(ModItems.manaResource, 1, 2), Character.valueOf('M'),
                        new ItemStack(ModItems.manaResource), Character.valueOf('P'),
                        new ItemStack(ModItems.manaResource, 1, 23) });
        gameBoard = new BLexiconEntry("gameBoard", BotaniaAPI.categoryMisc);
        gameBoard.setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        fateBoard = new BLexiconEntry("fateBoard", categoryForgotten);
        fateBoard.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        new AlphirineCraftPage(fateBoard, fateBoardRecipe.getOutput(), ".alphirineCraft") });

        freyrSlingshot = new RLexiconEntry(
                "freyrSlingshot",
                BotaniaAPI.categoryAlfhomancy,
                AchievementRegister.relicSlingshot);
        freyrSlingshot.setLexiconPages(new LexiconPage[] { new PageText("0"), new PageText("1") });

        richesKey = new RLexiconEntry("richesKey", BotaniaAPI.categoryAlfhomancy, AchievementRegister.relicItemChest);
        richesKey.setLexiconPages(new LexiconPage[] { new PageText("0") });

        cubeWardrobe = new RLexiconEntry(
                "cubeWardrobe",
                BotaniaAPI.categoryAlfhomancy,
                AchievementRegister.relicPocketArmor);
        cubeWardrobe.setLexiconPages(new LexiconPage[] { new PageText("0") });

        sphereNavigation = new RLexiconEntry(
                "sphereNavigation",
                BotaniaAPI.categoryAlfhomancy,
                AchievementRegister.sphereNavigation);
        sphereNavigation.setLexiconPages(new LexiconPage[] { new PageText("0") });

        hornPlenty = new RLexiconEntry("hornPlenty", BotaniaAPI.categoryAlfhomancy, AchievementRegister.hornPlenty);
        hornPlenty.setLexiconPages(new LexiconPage[] { new PageText("0") });

        // Rod of sprawl recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemSprawlRod),
                new Object[] { " SC", " WS", "W  ", Character.valueOf('S'),
                        new ItemStack(ModItems.grassSeeds, 1, 32767), Character.valueOf('C'),
                        new ItemStack(BlockListAB.blockLebethron, 1, 4), Character.valueOf('W'),
                        new ItemStack(ModItems.manaResource, 1, 3) });
        sprawlRod = new BLexiconEntry("sprawlRod", categoryForgotten);
        sprawlRod.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        // Ardent azarcissus recipe
        azartFlowerRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("ardentAzarcissus"),
                new ItemStack(ItemListAB.itemABResource, 1, 4),
                new ItemStack(ModItems.petal, 1, 11),
                new ItemStack(ModItems.petal, 1, 11),
                new ItemStack(ModItems.petal, 1, 14),
                new ItemStack(ModItems.petal, 1, 14),
                new ItemStack(ModItems.petal, 1, 13),
                new ItemStack(ModItems.manaResource, 1, 5));
        azartFlower = new BLexiconEntry("azartFlower", BotaniaAPI.categoryGenerationFlowers);
        azartFlower.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.petalRecipePage(".petalCraft", azartFlowerRecipe) });

        // Aqua sword recipe
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemAquaSword),
                new Object[] { "  N", "RM ", "WR ", Character.valueOf('N'),
                        new ItemStack(ItemListAB.itemABResource, 1, 1), Character.valueOf('M'),
                        new ItemStack(ItemListAB.itemABResource), Character.valueOf('R'), new ItemStack(ModItems.rune),
                        Character.valueOf('W'), new ItemStack(ModItems.manaResource, 1, 13) });
        aquaSword = new BLexiconEntry("aquaSword", categoryForgotten);
        aquaSword.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        ///////////////////////////////////////////////////////////////////////////////////////// Thaumcraft recipes

        if (Botania.thaumcraftLoaded) thaumcraft();

        addShapelessOreDictRecipe(
                new ItemStack(ItemListAB.itemABResource, 9, 1),
                new Object[] { new ItemStack(ItemListAB.itemABResource) });
        addShapelessOreDictRecipe(
                new ItemStack(ItemListAB.itemABResource, 9, 0),
                new Object[] { new ItemStack(BlockListAB.blockABStorage) });
        addShapelessOreDictRecipe(
                new ItemStack(ItemListAB.itemABResource, 9, 6),
                new Object[] { new ItemStack(ItemListAB.itemABResource, 1, 5) });
        GameRegistry.addShapedRecipe(
                new ItemStack(BlockListAB.blockABStorage),
                new Object[] { "III", "III", "III", Character.valueOf('I'), new ItemStack(ItemListAB.itemABResource) });
        GameRegistry.addShapedRecipe(
                new ItemStack(ItemListAB.itemABResource, 1, 5),
                new Object[] { "III", "III", "III", Character.valueOf('I'),
                        new ItemStack(ItemListAB.itemABResource, 1, 6) });
    }

    // Aspecolus recipe
    public static void thaumcraft() {
        aspecolus = new BLexiconEntry("aspecolus", BotaniaAPI.categoryFunctionalFlowers);
        aspecolusRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("aspecolus"),
                new ItemStack(ModItems.manaResource, 1, 6),
                new ItemStack(ModItems.manaResource, 1, 5),
                new ItemStack(ModItems.rune, 1, 1),
                new ItemStack(ModItems.rune),
                new ItemStack(ItemListAB.itemABResource, 1, 2),
                new ItemStack(ModItems.petal, 1, 10),
                new ItemStack(ModItems.petal, 1, 13));
        aspecolus.setKnowledgeType(forgotten)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                BotaniaAPI.internalHandler.petalRecipePage(".petalCraft", aspecolusRecipe) })
                .setIcon(ItemBlockSpecialFlower.ofType("aspecolus"));
        addShapelessOreDictRecipe(
                new ItemStack(ItemListAB.itemNebulaHelmReveal),
                new Object[] { new ItemStack(ItemListAB.itemNebulaHelm), new ItemStack(ConfigItems.itemGoggles) });

        if (ConfigABHandler.hasAutoThaum) {
            List<IRecipe> recipes = new ArrayList<IRecipe>();
            for (int i = 1; i < 10; i++) {
                boolean[][] patterns = ItemCraftingPattern.getPattern(i);
                List<String> str = new ArrayList<String>();
                for (int j = 0; j < patterns.length; j++) {
                    String str1 = "";
                    for (int k = 0; k < patterns[j].length; k++) str1 += patterns[j][k] ? "T" : "H";
                    str.add(str1);
                }
                GameRegistry.addShapedRecipe(
                        new ItemStack(ItemListAB.itemCraftingPattern, 1, i),
                        new Object[] { str.get(0), str.get(1), str.get(2), Character.valueOf('T'),
                                new ItemStack(ItemListAB.itemCraftingPattern), Character.valueOf('H'),
                                new ItemStack(ConfigItems.itemNugget, 1, 6) });
                recipes.add(getLastRecipe());
            }

            // Thaumic crafty crate recipe
            IRecipe crate = GameRegistry.addShapedRecipe(
                    new ItemStack(BlockListAB.blockMagicCraftCrate),
                    new Object[] { "TST", "WCW", "WGW", Character.valueOf('W'),
                            new ItemStack(ConfigBlocks.blockMagicalLog), Character.valueOf('S'),
                            new ItemStack(ConfigItems.itemResource, 1, 14), Character.valueOf('T'),
                            new ItemStack(ConfigItems.itemResource, 1, 7), Character.valueOf('C'),
                            new ItemStack(ModBlocks.openCrate, 1, 1), Character.valueOf('G'),
                            (ToolDictNames.craftingToolScrewdriver.name()) });

            addShapelessOreDictRecipe(
                    new ItemStack(ItemListAB.itemCraftingPattern, 48),
                    new Object[] { new ItemStack(Blocks.crafting_table), new ItemStack(ModBlocks.livingrock),
                            new ItemStack(ConfigItems.itemResource, 1, 14) });
            thaumAutoCraft = new BLexiconEntry("thaumAutoCraft", BotaniaAPI.categoryDevices);
            thaumAutoCraft.setKnowledgeType(BotaniaAPI.elvenKnowledge).setLexiconPages(
                    new LexiconPage[] { new PageText("0"),
                            BotaniaAPI.internalHandler.craftingRecipePage(".craft", crate),
                            BotaniaAPI.internalHandler.craftingRecipePage(".craft0", getLastRecipe()),
                            BotaniaAPI.internalHandler.craftingRecipesPage(".craft1", recipes) });

            // Mana flower recipe
            GameRegistry.addShapedRecipe(
                    new ItemStack(ItemListAB.itemManaFlower),
                    new Object[] { " F ", "PMP", " R ", Character.valueOf('T'),
                            new ItemStack(BlockListAB.blockABStorage, 1, 6), Character.valueOf('F'),
                            new ItemStack(ItemListAB.itemABResource, 1, 4), Character.valueOf('P'),
                            new ItemStack(ConfigItems.itemEldritchObject, 1, 3), Character.valueOf('M'),
                            new ItemStack(ModItems.manaBottle), Character.valueOf('R'),
                            new ItemStack(ModItems.rune, 1, 8) });
            manaFlower = new BLexiconEntry("manaFlower", categoryForgotten);
            manaFlower.setKnowledgeType(forgotten)
                    .setLexiconPages(
                            new LexiconPage[] { new PageText("0"),
                                    new AlphirineCraftPage(manaFlower, manaFlowerRecipe.getOutput(), ".alphirineCraft"),
                                    BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                    .setIcon(new ItemStack(ItemListAB.itemManaFlower));
        }

        // Pure Gladiolus recipe
        pureGladRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("pureGladiolus"),
                new ItemStack(ItemListAB.itemABResource, 1, 4),
                new ItemStack(ModItems.rune, 1, 8),
                new ItemStack(ModItems.petal, 1, 8),
                new ItemStack(ModItems.petal, 1, 4),
                new ItemStack(ModItems.petal, 1, 14),
                new ItemStack(ModItems.petal, 1, 5),
                new ItemStack(ModItems.petal, 1, 13),
                new ItemStack(ModItems.petal, 1, 11));
        gladious = new BLexiconEntry("gladious", BotaniaAPI.categoryFunctionalFlowers);
        gladious.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.petalRecipePage(".petalCraft", pureGladRecipe) });
    }

    ///////////////////////////////////////////////////////////////////////////////////////// Itemlist

    public static void relicInit() {
        AdvancedBotanyAPI.diceList.add(new ItemStack(ModItems.dice));

        AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.infiniteFruit));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.kingKey));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.flugelEye));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.thorRing));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.odinRing));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ModItems.lokiRing));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemFreyrSlingshot));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemTalismanHiddenRiches));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemPocketWardrobe));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemSphereNavigation));
        AdvancedBotanyAPI.relicList.add(new ItemStack(ItemListAB.itemHornPlenty));
    }

    private static IRecipe getLastRecipe() {
        return (IRecipe) CraftingManager.getInstance().getRecipeList()
                .get(CraftingManager.getInstance().getRecipeList().size() - 1);
    }

    private static void addOreDictRecipe(ItemStack output, Object... recipe) {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
    }

    private static void addShapelessOreDictRecipe(ItemStack output, Object... recipe) {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
    }
}
