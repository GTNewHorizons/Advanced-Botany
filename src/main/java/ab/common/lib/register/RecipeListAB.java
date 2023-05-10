package ab.common.lib.register;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
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
import vazkii.botania.common.lib.LibOreDict;
import ab.api.AdvancedBotanyAPI;
import ab.api.recipe.RecipeAdvancedPlate;
import ab.api.recipe.RecipeAncientAlphirine;
import ab.api.recipe.lexicon.AdvancedPlateCraftPage;
import ab.api.recipe.lexicon.AlphirineCraftPage;
import ab.common.block.tile.TileLebethronCore;
import ab.common.core.handler.ConfigABHandler;
import ab.common.item.ItemCraftingPattern;
import ab.utils.IModHelper;
import ab.utils.LocalizationManager;
import ab.utils.OreDict;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeListAB implements IModHelper {

    @SuppressWarnings("unused")

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
    public static RecipeAdvancedPlate FlawlessManaDiamondRecipe;
    public static RecipeAdvancedPlate ExquisiteManaDiamondRecipe;
    public static RecipeAdvancedPlate FlawlessDragonstoneRecipe;
    public static RecipeAdvancedPlate ExquisiteDragonstoneRecipe;

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

    public static ResearchPage TerraHoePages;
    public static ResearchPage DestroyerPages;
    public static ResearchPage MithrillSwordPages;
    public static ResearchPage AquaSwordPages;
    public static ResearchPage ForgePages;
    public static ResearchPage ManaChargerPages;
    public static ResearchPage NebulaPages;

    public static KnowledgeType forgotten;

    public static final String Name = "Thaumcraft";

    public void preInit() {}

    public void init() {}

    public void postInit() {
        setupItemAspects();
        setupCrafting();
        setupResearch();
    }

    public static InfusionRecipe TerraHoe;
    public static InfusionRecipe AquaSword;
    public static InfusionRecipe ManaCharger;
    public static InfusionRecipe MithrillSword;
    public static InfusionRecipe Forge;
    public static InfusionRecipe Destroyer;
    public static InfusionRecipe Nebula;
    public static InfusionRecipe NebulaHelm;
    public static InfusionRecipe NebulaChest;
    public static InfusionRecipe NebulaLegs;
    public static InfusionRecipe NebulaBoots;

    public static void setupCrafting() {

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
                OreDictionary.getOres("blockManasteel").get(0),
                OreDictionary.getOres("blockTerrasteel").get(0),
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
                OreDictionary.getOres("blockBotaniaDragonstone").get(0),
                new ItemStack(BlockListAB.blockABStorage, 1, 0),
                25000000,
                0x8d16e0);
        terrasteelBlockRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                OreDictionary.getOres("blockTerrasteel").get(0),
                new ItemStack(ModItems.rune, 1, 8),
                OreDictionary.getOres("blockManasteel").get(0),
                OreDictionary.getOres("blockManaDiamond").get(0),
                4500000,
                0x29de21);
        FlawlessManaDiamondRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                OreDictionary.getOres("gemFlawlessManaDiamond").get(0),
                new ItemStack(ModItems.manaResource, 1, 2),
                new ItemStack(ModItems.manaResource, 1, 2),
                new ItemStack(ModItems.manaResource, 1, 2),
                250000,
                0x1dab92);
        ExquisiteManaDiamondRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                OreDictionary.getOres("gemExquisiteManaDiamond").get(0),
                OreDictionary.getOres("gemFlawlessManaDiamond").get(0),
                OreDictionary.getOres("gemFlawlessManaDiamond").get(0),
                new ItemStack(ModItems.manaResource, 1, 2),
                250000,
                0x1dab92);
        FlawlessDragonstoneRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                OreDictionary.getOres("gemFlawlessBotaniaDragonstone").get(0),
                new ItemStack(ModItems.manaResource, 1, 9),
                new ItemStack(ModItems.manaResource, 1, 9),
                new ItemStack(ModItems.manaResource, 1, 9),
                300000,
                0xd6259d);
        ExquisiteDragonstoneRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                OreDictionary.getOres("gemExquisiteBotaniaDragonstone").get(0),
                OreDictionary.getOres("gemFlawlessBotaniaDragonstone").get(0),
                OreDictionary.getOres("gemFlawlessBotaniaDragonstone").get(0),
                new ItemStack(ModItems.manaResource, 1, 9),
                300000,
                0xd6259d);
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

        advandedAgglomerationPlate = new BLexiconEntry("advancedPlate", categoryForgotten);
        advandedAgglomerationPlate.setPriority().setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        new AdvancedPlateCraftPage(
                                advandedAgglomerationPlate,
                                terrasteelRecipe.getOutput(),
                                ".abCraft0"),
                        new AdvancedPlateCraftPage(advandedAgglomerationPlate, manaStarRecipe.getOutput(), ".abCraft0"),
                        new AdvancedPlateCraftPage(
                                advandedAgglomerationPlate,
                                terrasteelBlockRecipe.getOutput(),
                                ".abCraft1"),
                        new AdvancedPlateCraftPage(advandedAgglomerationPlate, mithrillRecipe.getOutput(), ".abCraft0"),
                        new AdvancedPlateCraftPage(advandedAgglomerationPlate, nebulaRecipe.getOutput(), ".abCraft0"),
                        new AdvancedPlateCraftPage(
                                advandedAgglomerationPlate,
                                FlawlessManaDiamondRecipe.getOutput(),
                                ".abCraft0"),
                        new AdvancedPlateCraftPage(
                                advandedAgglomerationPlate,
                                FlawlessDragonstoneRecipe.getOutput(),
                                ".abCraft0"),
                        new AdvancedPlateCraftPage(
                                advandedAgglomerationPlate,
                                ExquisiteManaDiamondRecipe.getOutput(),
                                ".abCraft0"),
                        new AdvancedPlateCraftPage(
                                advandedAgglomerationPlate,
                                ExquisiteDragonstoneRecipe.getOutput(),
                                ".abCraft0") })
                .setIcon(new ItemStack(BlockListAB.blockABPlate));

        // Natural mana spreader recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockABSpreader),
                        "WMW",
                        "PSP",
                        "WMW",
                        'W',
                        new ItemStack(BlockListAB.blockLebethron, 1, 4),
                        'M',
                        new ItemStack(ItemListAB.itemABResource, 1, 2),
                        'P',
                        new ItemStack(ModBlocks.pylon),
                        'S',
                        new ItemStack(ModBlocks.spreader, 1, 3)));

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

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockLebethron, 4, 3),
                        "SLS",
                        "LEL",
                        "SLS",
                        'S',
                        "ingotSteeleaf",
                        'L',
                        new ItemStack(BlockListAB.blockLebethron, 1, 0),
                        'E',
                        new ItemStack(ModBlocks.pylon)));

        IRecipe leb3 = getLastRecipe();

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockLebethron, 1, 4),
                        "4W5",
                        "WPW",
                        "6W7",
                        'W',
                        new ItemStack(BlockListAB.blockLebethron, 1, 3),
                        'P',
                        new ItemStack(ModBlocks.pylon, 1, 1),
                        '4',
                        new ItemStack(ModItems.rune, 1, 4),
                        '5',
                        new ItemStack(ModItems.rune, 1, 5),
                        '6',
                        new ItemStack(ModItems.rune, 1, 6),
                        '7',
                        new ItemStack(ModItems.rune, 1, 7)));

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

        mithrill = new BLexiconEntry("mithrill", categoryForgotten);
        mithrill.setKnowledgeType(forgotten)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                new AdvancedPlateCraftPage(mithrill, mithrillRecipe.getOutput(), ".abCraft"),
                                new PageText("1"), })
                .setIcon(new ItemStack(ItemListAB.itemMihrillMultiTool));

        // Advanced mana containers recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockManaContainer),
                        "MGM",
                        "LPL",
                        "M3M",
                        'M',
                        new ItemStack(ItemListAB.itemABResource),
                        'G',
                        new ItemStack(ModItems.rune, 1, 15),
                        'L',
                        new ItemStack(ModBlocks.pool),
                        'P',
                        new ItemStack(ModBlocks.pylon),
                        '3',
                        new ItemStack(ModItems.rune, 1, 3)

                ));

        IRecipe cont1 = getLastRecipe();

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockManaContainer, 1, 1),
                        "MGM",
                        "LPL",
                        "M3M",
                        'M',
                        new ItemStack(ItemListAB.itemABResource, 1, 1),
                        'G',
                        new ItemStack(ModItems.rune, 1, 15),
                        'L',
                        new ItemStack(ModBlocks.pool, 1, 2),
                        'P',
                        new ItemStack(ModBlocks.pylon),
                        '3',
                        new ItemStack(ModItems.rune, 1, 3)));
        IRecipe cont2 = getLastRecipe();

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockManaContainer, 1, 2),
                        "MGM",
                        "LPL",
                        "M3M",
                        'M',
                        new ItemStack(ItemListAB.itemABResource),
                        'G',
                        new ItemStack(ModItems.rune, 1, 15),
                        'L',
                        new ItemStack(ModBlocks.pool, 1, 3),
                        'P',
                        new ItemStack(ModBlocks.pylon),
                        '3',
                        new ItemStack(ModItems.rune, 1, 3)));

        IRecipe cont3 = getLastRecipe();
        manaContainer = new BLexiconEntry("manaContainer", categoryForgotten);
        manaContainer.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), BotaniaAPI.internalHandler.craftingRecipePage(".craft0", cont1),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft1", cont2),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft2", cont3), new PageText("1"),
                        new AlphirineCraftPage(manaContainer, advancedSparkRecipe.getOutput(), ".alphirineCraft") })
                .setIcon(new ItemStack(BlockListAB.blockManaContainer));

        // Mana crystal cube recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockManaCrystalCube),
                        "TST",
                        "GWG",
                        "DMD",
                        'T',
                        new ItemStack(ModItems.manaResource, 1, 18),
                        'S',
                        new ItemStack(ModItems.spark),
                        'G',
                        new ItemStack(ModBlocks.manaGlass),
                        'W',
                        new ItemStack(ModItems.twigWand, 1, 32767),
                        'D',
                        new ItemStack(ModBlocks.dreamwood),
                        'M',
                        "blockManasteel"

                ));

        manaCrystalCube = new BLexiconEntry("manaCrystalCube", BotaniaAPI.categoryMana);
        manaCrystalCube.setKnowledgeType(BotaniaAPI.elvenKnowledge)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(BlockListAB.blockManaCrystalCube));

        // Terrahoe recipe

        terraHoe = new BLexiconEntry("terraHoe", BotaniaAPI.categoryTools);
        terraHoe.setLexiconPages(new LexiconPage[] { new PageText("0"), })
                .setIcon(new ItemStack(ItemListAB.itemTerraHoe));

        // Mithrill ring recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemMithrillRing),
                        "LML",
                        "MRM",
                        "LML",
                        'L',
                        new ItemStack(ItemListAB.itemABResource, 1, 1),
                        'M',
                        new ItemStack(ItemListAB.itemABResource),
                        'R',
                        new ItemStack(ModItems.manaRingGreater)

                ));

        IRecipe ring1 = getLastRecipe();

        // Nebula ring recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemNebulaRing),
                        "LML",
                        "MRM",
                        "LML",
                        'L',
                        new ItemStack(ModItems.manaResource, 1, 5),
                        'M',
                        new ItemStack(ItemListAB.itemABResource, 1, 6),
                        'R',
                        new ItemStack(ItemListAB.itemMithrillRing)

                ));

        IRecipe ring2 = getLastRecipe();
        manaRings = new BLexiconEntry("manaRings", categoryForgotten);
        manaRings.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), BotaniaAPI.internalHandler.craftingRecipePage(".craft0", ring1),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft1", ring2) })
                .setIcon(new ItemStack(ItemListAB.itemMithrillRing));
        AdvancedBotanyAPI.registerFarmlandSeed(Blocks.nether_wart, 3);

        // Blade of space recipe

        mithrillSword = new BLexiconEntry("mithrillSword", categoryForgotten);
        mithrillSword.setKnowledgeType(forgotten)
                .setLexiconPages(new LexiconPage[] { new PageText("0"), new PageText("1"), })
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

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemBlackHalo),
                        " E ",
                        "DHD",
                        " E ",
                        'E',
                        "plateElvenElementium",
                        'D',
                        new ItemStack(ModItems.blackHoleTalisman),
                        'H',
                        new ItemStack(ModItems.autocraftingHalo)

                ));

        blackHalo = new BLexiconEntry("blackHalo", BotaniaAPI.categoryTools);
        blackHalo.setKnowledgeType(BotaniaAPI.elvenKnowledge).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        // Sphere of Attraction recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemAntigravityCharm),
                        " G ",
                        "GDG",
                        "ERE",
                        'G',
                        new ItemStack(ModBlocks.elfGlass),
                        'D',
                        new ItemStack(ModBlocks.floatingFlower, 1, 32767),
                        'E',
                        "plateElvenElementium",
                        'R',
                        new ItemStack(ModItems.rune, 1, 3)

                ));

        antigravityCharm = new BLexiconEntry("antigravityCharm", BotaniaAPI.categoryTools);
        antigravityCharm.setKnowledgeType(BotaniaAPI.elvenKnowledge)
                .setLexiconPages(
                        new LexiconPage[] { new PageText("0"),
                                BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) })
                .setIcon(new ItemStack(ItemListAB.itemAntigravityCharm));

        // Nebula blaze recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemNebulaBlaze),
                        " N ",
                        "MMN",
                        "AMN",
                        'N',
                        new ItemStack(ItemListAB.itemABResource, 1, 6),
                        'M',
                        "blockManasteel",
                        'A',
                        new ItemStack(ItemListAB.itemABResource, 1, 3)

                ));

        nebulaBlaze = new BLexiconEntry("nebulaBlaze", categoryForgotten);
        nebulaBlaze.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        // Mana charger recipe
        if (ConfigABHandler.hasManaCharger) {

            manaCharger = new BLexiconEntry("manaCharger", categoryForgotten);
            manaCharger.setKnowledgeType(forgotten).setLexiconPages(new LexiconPage[] { new PageText("0"), });
        }

        ///////////////////////////////////////////////////////////////////////////////////////// Nebula set recipes

        nebulaArmor = new BLexiconEntry("nebulaArmor", categoryForgotten);
        nebulaArmor.setKnowledgeType(forgotten)
                .setLexiconPages(new LexiconPage[] { new PageText("0"), new PageText("1"), });

        // Enginerr hopper recipe
        engineerHopper = new BLexiconEntry("engineerHopper", categoryForgotten);
        engineerHopper.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"), new PageImage("1", "ab:textures/misc/engineerHopper.png"),
                        new PageText("2"), new PageText("3"), new PageText("4"),
                        new AlphirineCraftPage(engineerHopper, hopperRecipe.getOutput(), ".alphirineCraft") });

        // Rod of nebula recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemNebulaRod),
                        " WN",
                        " RW",
                        "E  ",
                        'W',
                        new ItemStack(ModItems.manaResource, 1, 13),
                        'N',
                        new ItemStack(ItemListAB.itemABResource, 1, 6),
                        'R',
                        new ItemStack(ModItems.rune, 1, 8),
                        'E',
                        "itemEnderCrystal"

                ));

        nebulaRod = new BLexiconEntry("nebulaRod", categoryForgotten);
        nebulaRod.setKnowledgeType(forgotten).setLexiconPages(
                new LexiconPage[] { new PageText("0"),
                        BotaniaAPI.internalHandler.craftingRecipePage(".craft", getLastRecipe()) });

        // Playing board recipe

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockBoardFate),
                        "G G",
                        "QDQ",
                        "MPM",
                        'G',
                        "gearGtSmallInfusedGold",
                        'Q',
                        new ItemStack(ModFluffBlocks.manaQuartz),
                        'D',
                        new ItemStack(ModItems.manaResource, 1, 2),
                        'M',
                        "plateTerrasteel",
                        'P',
                        new ItemStack(ModItems.manaResource, 1, 23)

                ));

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

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemSprawlRod),
                        " SC",
                        " WS",
                        "W  ",
                        'S',
                        new ItemStack(ModItems.grassSeeds, 1, 32767),
                        'C',
                        new ItemStack(BlockListAB.blockLebethron, 1, 4),
                        'W',
                        new ItemStack(ModItems.manaResource, 1, 3)

                ));

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

        aquaSword = new BLexiconEntry("aquaSword", categoryForgotten);
        aquaSword.setKnowledgeType(forgotten).setLexiconPages(new LexiconPage[] { new PageText("0"), });

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

        TerraHoe = ThaumcraftApi.addInfusionCraftingRecipe(
                "TerraHoe",

                new ItemStack(ItemListAB.itemTerraHoe),
                5,
                new AspectList().add(Aspect.EARTH, 32).add(Aspect.MAGIC, 16).add(Aspect.HARVEST, 48)
                        .add(Aspect.CROP, 16),
                new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemHoeElemental")),
                new ItemStack[] { OreDictionary.getOres("gemFlawlessGreenSapphire").get(0),
                        new ItemStack(ModItems.manaResource, 1, 3),
                        OreDict.preference("plateTerrasteel", LibOreDict.TERRA_STEEL),
                        new ItemStack(ModItems.rune, 1, 2), new ItemStack(ModItems.fertilizer),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:blockCrystal"), 1, 3),
                        OreDict.preference("plateTerrasteel", LibOreDict.TERRA_STEEL),
                        new ItemStack(ModItems.manaResource, 1, 3), OreDictionary.getOres("gemFlawlessOlivine").get(0),
                        new ItemStack(ModItems.manaResource, 1, 3),
                        OreDict.preference("plateTerrasteel", LibOreDict.TERRA_STEEL),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:blockCrystal"), 1, 3),
                        new ItemStack(ModItems.fertilizer), new ItemStack(ModItems.rune, 1, 2),
                        OreDict.preference("plateTerrasteel", LibOreDict.TERRA_STEEL),
                        new ItemStack(ModItems.manaResource, 1, 3) });

        AquaSword = ThaumcraftApi.addInfusionCraftingRecipe(
                "AquaSword",

                new ItemStack(ItemListAB.itemAquaSword),
                8,
                new AspectList().add(Aspect.WEAPON, 64).add(Aspect.EARTH, 64).add(Aspect.WATER, 64)
                        .add(Aspect.MAGIC, 32).add(Aspect.SLIME, 32).add(Aspect.BEAST, 16),
                new ItemStack(ModItems.starSword),
                new ItemStack[] { new ItemStack(ModItems.waterRod), new ItemStack(ItemListAB.itemABResource, 1, 0),
                        new ItemStack(ItemListAB.itemABResource, 1, 2), new ItemStack(ModItems.rune, 1, 0),
                        new ItemStack(ItemListAB.itemABResource, 1, 6), new ItemStack(ModItems.rune, 1, 3),
                        new ItemStack(ModBlocks.dreamwood, 1, 5), new ItemStack(ModItems.rune, 1, 3),
                        new ItemStack(ItemListAB.itemABResource, 1, 6), new ItemStack(ModItems.rune, 1, 0),
                        new ItemStack(ItemListAB.itemABResource, 1, 2),
                        new ItemStack(ItemListAB.itemABResource, 1, 0) });

        ManaCharger = ThaumcraftApi.addInfusionCraftingRecipe(
                "ManaCharger",

                new ItemStack(BlockListAB.blockManaCharger),
                6,
                new AspectList().add(Aspect.AIR, 64).add(Aspect.EARTH, 64).add(Aspect.FLIGHT, 64).add(Aspect.WATER, 32)
                        .add(Aspect.EXCHANGE, 32).add(Aspect.SENSES, 16),
                new ItemStack(ModBlocks.pylon, 1, 0),
                new ItemStack[] { new ItemStack(ModFluffBlocks.livingrockSlab), new ItemStack(ModItems.manaTablet),
                        new ItemStack(ModItems.rune, 1, 4), new ItemStack(ModItems.rune, 1, 8),
                        new ItemStack(ModBlocks.bellows), new ItemStack(ModFluffBlocks.livingrockSlab),
                        new ItemStack(ModItems.manaTablet), new ItemStack(ModItems.rune, 1, 7),
                        new ItemStack(ModItems.rune, 1, 8), new ItemStack(ModBlocks.bellows),
                        new ItemStack(ModFluffBlocks.livingrockSlab), new ItemStack(ModItems.manaTablet),
                        new ItemStack(ModItems.rune, 1, 6), new ItemStack(ModItems.rune, 1, 8),
                        new ItemStack(ModBlocks.bellows), new ItemStack(ModFluffBlocks.livingrockSlab),
                        new ItemStack(ModItems.manaTablet), new ItemStack(ModItems.rune, 1, 5),
                        new ItemStack(ModItems.rune, 1, 8), new ItemStack(ModBlocks.bellows) });

        MithrillSword = ThaumcraftApi.addInfusionCraftingRecipe(
                "MithrillSword",

                new ItemStack(ItemListAB.itemMithrillSword),
                10,
                new AspectList().add(Aspect.WEAPON, 64).add(Aspect.EARTH, 64).add(Aspect.MOTION, 64)
                        .add(Aspect.MAGIC, 32).add(Aspect.ELDRITCH, 32).add(Aspect.BEAST, 16).add(Aspect.HUNGER, 16),
                new ItemStack(ItemListAB.itemAquaSword),
                new ItemStack[] { new ItemStack(ModItems.rainbowRod), new ItemStack(ItemListAB.itemABResource, 1, 0),
                        new ItemStack(
                                (Item) Item.itemRegistry.getObject("dreamcraft:item.MysteriousCrystalGemFlawless")),
                        new ItemStack(ItemListAB.itemABResource, 1, 0), new ItemStack(ItemListAB.itemNebulaRod),
                        new ItemStack(ModBlocks.dreamwood, 1, 5), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ModBlocks.livingwood, 1, 5),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:HandMirror")),
                        new ItemStack(ModBlocks.livingwood, 1, 5), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ModBlocks.dreamwood, 1, 5), new ItemStack(ItemListAB.itemNebulaRod),
                        new ItemStack(ItemListAB.itemABResource, 1, 0),
                        OreDictionary.getOres("gemFlawlessAmethyst").get(0),
                        new ItemStack(ItemListAB.itemABResource, 1, 0) });

        Forge = ThaumcraftApi.addInfusionCraftingRecipe(
                "Forge",

                new ItemStack(BlockListAB.blockABPlate),
                8,
                new AspectList().add(Aspect.AIR, 80).add(Aspect.EARTH, 60).add(Aspect.ORDER, 40).add(Aspect.MAGIC, 32)
                        .add(Aspect.CRAFT, 32).add(Aspect.CRYSTAL, 20).add(Aspect.METAL, 20),
                new ItemStack((Item) Item.itemRegistry.getObject("thaumicbases:voidAnvil")),
                new ItemStack[] { new ItemStack(ModBlocks.terraPlate), new ItemStack(ModItems.rune, 1, 2),
                        OreDictionary.getOres("blockTerrasteel").get(0),
                        new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.DiamondCoreChip")),
                        new ItemStack(ModBlocks.terraPlate), new ItemStack(ModItems.rune, 1, 3),
                        OreDictionary.getOres("blockManasteel").get(0),
                        new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.EssentiaCircuit")),
                        new ItemStack(ModBlocks.terraPlate), new ItemStack(ModItems.rune, 1, 2),
                        OreDictionary.getOres("blockTerrasteel").get(0),
                        new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.DiamondCoreChip")),
                        new ItemStack(ModBlocks.terraPlate), new ItemStack(ModItems.rune, 1, 3),
                        OreDictionary.getOres("blockManasteel").get(0),
                        new ItemStack((Item) Item.itemRegistry.getObject("dreamcraft:item.EssentiaCircuit")), });

        Destroyer = ThaumcraftApi.addInfusionCraftingRecipe(
                "Destroyer",

                new ItemStack(ItemListAB.itemMihrillMultiTool),
                12,
                new AspectList().add(Aspect.EARTH, 128).add(Aspect.MAGIC, 64).add(Aspect.TOOL, 64).add(Aspect.MINE, 64)
                        .add(Aspect.CROP, 32).add(Aspect.HARVEST, 32).add(Aspect.TREE, 32),
                new ItemStack(ItemListAB.itemABResource, 1, 2),
                new ItemStack[] { new ItemStack(ModItems.terraAxe), new ItemStack(BlockListAB.blockABStorage, 1, 0),
                        OreDictionary.getOres("gemExquisiteManaDiamond").get(0),
                        new ItemStack(BlockListAB.blockABStorage, 1, 0), new ItemStack(ModItems.terraPick, 1, 0), // nbt(2147483646),
                        new ItemStack(ModBlocks.livingwood, 1, 5), OreDictionary.getOres("gemFlawlessAmber").get(0),
                        new ItemStack(BlockListAB.blockLebethron, 1, 4), new ItemStack(ModItems.temperanceStone, 1, 0),
                        new ItemStack(BlockListAB.blockLebethron, 1, 4),
                        OreDictionary.getOres("gemFlawlessAmber").get(0), new ItemStack(ModBlocks.livingwood, 1, 5),
                        new ItemStack(ItemListAB.itemTerraHoe), new ItemStack(BlockListAB.blockABStorage, 1, 0),
                        OreDictionary.getOres("gemExquisiteManaDiamond").get(0),
                        new ItemStack(BlockListAB.blockABStorage, 1, 0) });

        NebulaHelm = ThaumcraftApi.addInfusionCraftingRecipe(
                "Nebula",

                new ItemStack(ItemListAB.itemNebulaHelm),
                12,
                new AspectList().add(Aspect.EARTH, 128).add(Aspect.MAGIC, 64).add(Aspect.CRYSTAL, 20)
                        .add(Aspect.METAL, 20).add(Aspect.SENSES, 20).add(Aspect.ELDRITCH, 20),
                new ItemStack(ModItems.gaiaHead),
                new ItemStack[] { new ItemStack(ModItems.elementiumHelm), new ItemStack(ModItems.laputaShard, 1, 15),
                        OreDictionary.getOres("gemExquisiteBotaniaDragonstone").get(0),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), new ItemStack(ItemListAB.itemNebulaRing),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), new ItemStack(ItemListAB.itemABResource, 1, 3),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(ModItems.terrasteelHelm),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(ItemListAB.itemABResource, 1, 3), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ItemListAB.itemNebulaRing), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        OreDictionary.getOres("gemExquisiteBotaniaDragonstone").get(0),
                        new ItemStack(ModItems.laputaShard, 1, 15), });

        NebulaChest = ThaumcraftApi.addInfusionCraftingRecipe(
                "Nebula",

                new ItemStack(ItemListAB.itemNebulaChest),
                12,
                new AspectList().add(Aspect.EARTH, 128).add(Aspect.MAGIC, 64).add(Aspect.CRYSTAL, 20)
                        .add(Aspect.METAL, 20).add(Aspect.SENSES, 20).add(Aspect.FLIGHT, 20),
                new ItemStack(ModItems.flightTiara),
                new ItemStack[] { new ItemStack(ModItems.elementiumChest), new ItemStack(ModItems.laputaShard, 1, 15),
                        OreDictionary.getOres("blockElvenElementium").get(0),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), new ItemStack(ItemListAB.itemNebulaRing),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), new ItemStack(BlockListAB.blockABStorage, 1, 0),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(ModItems.terrasteelChest),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(BlockListAB.blockABStorage, 1, 0), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ItemListAB.itemNebulaRing), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        OreDictionary.getOres("blockElvenElementium").get(0),
                        new ItemStack(ModItems.laputaShard, 1, 15),

                });

        NebulaLegs = ThaumcraftApi.addInfusionCraftingRecipe(
                "Nebula",

                new ItemStack(ItemListAB.itemNebulaLegs),
                12,
                new AspectList().add(Aspect.EARTH, 128).add(Aspect.MAGIC, 64).add(Aspect.CRYSTAL, 20)
                        .add(Aspect.METAL, 20).add(Aspect.SENSES, 20).add(Aspect.BEAST, 20),
                new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemGirdleRunic"), 1, 1),
                new ItemStack[] { new ItemStack(ModItems.elementiumLegs), new ItemStack(ModItems.laputaShard, 1, 15),
                        OreDictionary.getOres("gemExquisiteBotaniaDragonstone").get(0),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), new ItemStack(ItemListAB.itemNebulaRing),
                        new ItemStack(ItemListAB.itemABResource, 1, 5), new ItemStack(ModBlocks.manaBeacon, 1, 5),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(ModItems.terrasteelLegs),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(ModBlocks.manaBeacon, 1, 5), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ItemListAB.itemNebulaRing), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        OreDictionary.getOres("gemExquisiteBotaniaDragonstone").get(0),
                        new ItemStack(ModItems.laputaShard, 1, 15),

                });

        NebulaBoots = ThaumcraftApi.addInfusionCraftingRecipe(
                "Nebula",

                new ItemStack(ItemListAB.itemNebulaBoots),
                12,
                new AspectList().add(Aspect.EARTH, 128).add(Aspect.MAGIC, 64).add(Aspect.CRYSTAL, 20)
                        .add(Aspect.METAL, 20).add(Aspect.SENSES, 20).add(Aspect.SLIME, 20),
                new ItemStack(ModItems.speedUpBelt),
                new ItemStack[] { new ItemStack(ModItems.elementiumBoots), new ItemStack(ModItems.laputaShard, 1, 15),
                        new ItemStack(ItemListAB.itemABResource, 1, 2), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ItemListAB.itemNebulaRing), new ItemStack(ItemListAB.itemABResource, 1, 3),
                        new ItemStack(ModBlocks.starfield),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(ModItems.terrasteelBoots),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemAmuletRunic"), 1, 1),
                        new ItemStack(ModBlocks.starfield), new ItemStack(ItemListAB.itemABResource, 1, 3),
                        new ItemStack(ItemListAB.itemNebulaRing), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ItemListAB.itemABResource, 1, 2), new ItemStack(ModItems.laputaShard, 1, 15),

                });

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
                new ItemStack(ItemListAB.itemManaFlower),
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
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(ItemListAB.itemCraftingPattern, 1, i),
                                str.get(0),
                                str.get(1),
                                str.get(2),
                                'T',
                                new ItemStack(ItemListAB.itemCraftingPattern),
                                'H',
                                new ItemStack(ConfigItems.itemNugget, 1, 6)));

                recipes.add(getLastRecipe());
            }

            // Thaumic crafty crate recipe

            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(BlockListAB.blockMagicCraftCrate),
                            "TST",
                            "WCW",
                            "WEW",
                            'T',
                            new ItemStack(ConfigItems.itemResource, 1, 7),
                            'S',
                            new ItemStack(ConfigItems.itemResource, 1, 14),
                            'W',
                            new ItemStack(ConfigBlocks.blockMagicalLog),
                            'C',
                            new ItemStack(ModBlocks.openCrate, 1, 1),
                            'E',
                            new ItemStack(ModBlocks.openCrate)));

            addShapelessOreDictRecipe(
                    new ItemStack(ItemListAB.itemCraftingPattern, 48),
                    new Object[] { new ItemStack(Blocks.crafting_table), new ItemStack(ModBlocks.livingrock),
                            new ItemStack(ConfigItems.itemResource, 1, 14) });
            thaumAutoCraft = new BLexiconEntry("thaumAutoCraft", BotaniaAPI.categoryDevices);
            thaumAutoCraft.setKnowledgeType(BotaniaAPI.elvenKnowledge).setLexiconPages(
                    new LexiconPage[] { new PageText("0"),
                            BotaniaAPI.internalHandler.craftingRecipePage(".craft0", getLastRecipe()),
                            BotaniaAPI.internalHandler.craftingRecipesPage(".craft1", recipes) });

            // Mana flower recipe

            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ItemListAB.itemManaFlower),
                            "NFN",
                            "PMP",
                            "NRN",
                            'N',
                            new ItemStack(ItemListAB.itemABResource, 1, 1),
                            'F',
                            new ItemStack(ItemListAB.itemABResource, 1, 4),
                            'P',
                            new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                            'M',
                            new ItemStack(ModItems.manaBottle),
                            'R',
                            new ItemStack(ModItems.rune, 1, 8)));

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
                new ItemStack(ItemListAB.itemManaFlower),
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

    public static void setupResearch() {
        String category = "AB_botania";
        ResearchCategories.registerCategory(
                category,
                new ResourceLocation("botania", "textures/gui/categories/forgotten.png"),
                new ResourceLocation("botania", "textures/gui/categories/ABstars.png"));

        ResearchItem TerraHoePage;
        ResearchPage TerraHoePages;
        ResearchPage infTerraHoePages;

        ResearchItem AquaSwordPage;
        ResearchPage AquaSwordPages;
        ResearchPage infAquaSwordPages;

        ResearchItem ManaChargerPage;
        ResearchPage ManaChargerPages;
        ResearchPage infManaChargerPages;

        ResearchItem MithrillSwordPage;
        ResearchPage MithrillSwordPages;
        ResearchPage infMithrillSwordPages;

        ResearchItem ForgePage;
        ResearchPage ForgePages;
        ResearchPage infForgePages;

        ResearchItem DestroyerPage;
        ResearchPage DestroyerPages;
        ResearchPage infDestroyerPages;

        ResearchItem NebulaPage;
        ResearchPage NebulaPages;
        ResearchPage infNebulaHelm;
        ResearchPage infNebulaChest;
        ResearchPage infNebulaLegs;
        ResearchPage infNebulaBoots;

        TerraHoePage = new ResearchItem(
                "TerraHoe",
                category,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.LIFE, 1).add(Aspect.TOOL, 1).add(Aspect.EARTH, 8)
                        .add(Aspect.CROP, 4).add(Aspect.HARVEST, 2),
                0,
                2,
                0,
                new ItemStack(ItemListAB.itemTerraHoe));

        TerraHoePages = new ResearchPage("TerraHoePages");
        infTerraHoePages = new ResearchPage(TerraHoe);

        AquaSwordPage = new ResearchItem(
                "AquaSword",
                category,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.LIFE, 1).add(Aspect.TOOL, 1).add(Aspect.EARTH, 8)
                        .add(Aspect.CROP, 4).add(Aspect.HARVEST, 2),
                2,
                2,
                0,
                new ItemStack(ItemListAB.itemAquaSword));

        AquaSwordPages = new ResearchPage("AquaSwordPages");
        infAquaSwordPages = new ResearchPage(AquaSword);

        ManaChargerPage = new ResearchItem(
                "ManaCharger",
                category,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.LIFE, 1).add(Aspect.TOOL, 1).add(Aspect.EARTH, 8)
                        .add(Aspect.CROP, 4).add(Aspect.HARVEST, 2),
                4,
                6,
                0,
                new ItemStack(BlockListAB.blockManaCharger));

        ManaChargerPages = new ResearchPage("ManaChargerPages");
        infManaChargerPages = new ResearchPage(ManaCharger);

        MithrillSwordPage = new ResearchItem(
                "MithrillSword",
                category,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.LIFE, 1).add(Aspect.TOOL, 1).add(Aspect.EARTH, 8)
                        .add(Aspect.CROP, 4).add(Aspect.HARVEST, 2),
                2,
                0,
                0,
                new ItemStack(ItemListAB.itemMithrillSword));

        MithrillSwordPages = new ResearchPage("MithrillSwordPages");
        infMithrillSwordPages = new ResearchPage(MithrillSword);

        ForgePage = new ResearchItem(
                "Forge",
                category,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.LIFE, 1).add(Aspect.TOOL, 1).add(Aspect.EARTH, 8)
                        .add(Aspect.CROP, 4).add(Aspect.HARVEST, 2),
                4,
                5,
                0,
                new ItemStack(BlockListAB.blockABPlate));

        ForgePages = new ResearchPage("ForgePages");
        infForgePages = new ResearchPage(Forge);

        DestroyerPage = new ResearchItem(
                "Destroyer",
                category,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.LIFE, 1).add(Aspect.TOOL, 1).add(Aspect.EARTH, 8)
                        .add(Aspect.CROP, 4).add(Aspect.HARVEST, 2),
                0,
                0,
                0,
                new ItemStack(ItemListAB.itemMihrillMultiTool));

        DestroyerPages = new ResearchPage("DestroyerPages");
        infDestroyerPages = new ResearchPage(Destroyer);

        NebulaPage = new ResearchItem(
                "Nebula",
                category,
                new AspectList().add(Aspect.MAGIC, 18).add(Aspect.ELDRITCH, 10).add(Aspect.EARTH, 10)
                        .add(Aspect.CRYSTAL, 8).add(Aspect.SENSES, 4).add(Aspect.EXCHANGE, 2),
                8,
                8,
                0,
                new ItemStack(ItemListAB.itemABResource, 1, 5));

        NebulaPages = new ResearchPage("NebulaPages");
        infNebulaHelm = new ResearchPage(NebulaHelm);
        infNebulaChest = new ResearchPage(NebulaChest);
        infNebulaLegs = new ResearchPage(NebulaLegs);
        infNebulaBoots = new ResearchPage(NebulaBoots);

        TerraHoePage.setPages(TerraHoePages, infTerraHoePages);
        AquaSwordPage.setPages(AquaSwordPages, infAquaSwordPages);
        ManaChargerPage.setPages(ManaChargerPages, infManaChargerPages);
        MithrillSwordPage.setPages(MithrillSwordPages, infMithrillSwordPages);
        ForgePage.setPages(ForgePages, infForgePages);
        DestroyerPage.setPages(DestroyerPages, infDestroyerPages);
        NebulaPage.setPages(NebulaPages, infNebulaHelm, infNebulaChest, infNebulaLegs, infNebulaBoots);

        TerraHoePage.setParents("ELEMENTALHOE");
        AquaSwordPage.setParents("Nebula");
        ManaChargerPage.setParents("MIRROR");
        MithrillSwordPage.setParents("AquaSword");
        ForgePage.setParents("ManaCharger");
        DestroyerPage.setParents("TerraHoe", "Nebula");
        NebulaPage.setParents("Forge");

        ThaumcraftApi.addWarpToResearch("Nebula", 8);

        ResearchCategories.addResearch(TerraHoePage);
        ResearchCategories.addResearch(AquaSwordPage);
        ResearchCategories.addResearch(ManaChargerPage);
        ResearchCategories.addResearch(MithrillSwordPage);
        ResearchCategories.addResearch(ForgePage);
        ResearchCategories.addResearch(DestroyerPage);
        ResearchCategories.addResearch(NebulaPage);
    }

    public static ResearchPage getResearchPage(String ident) {
        return new ResearchPage(LocalizationManager.getLocalizedString("tc.research_page." + ident));
    }

    public static void setupItemAspects() {}
}
