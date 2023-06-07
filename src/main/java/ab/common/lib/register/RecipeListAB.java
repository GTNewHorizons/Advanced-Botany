package ab.common.lib.register;

import static net.fuzzycraft.botanichorizons.util.Constants.*;

import java.util.ArrayList;
import java.util.List;

import net.fuzzycraft.botanichorizons.util.OreDict;
import net.minecraft.block.Block;
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
import ab.api.recipe.RecipeFountainAlchemy;
import ab.api.recipe.RecipeFountainConjuration;
import ab.api.recipe.RecipeFountainMana;
import ab.api.recipe.lexicon.AdvancedPlateCraftPage;
import ab.api.recipe.lexicon.AlphirineCraftPage;
import ab.common.block.tile.TileLebethronCore;
import ab.common.core.handler.ConfigABHandler;
import ab.common.item.ItemCraftingPattern;
import ab.utils.IModHelper;
import ab.utils.LocalizationManager;
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
    public static RecipeAdvancedPlate EnderAirRecipe;

    public static RecipeFountainMana manasteelRecipes;
    public static RecipeFountainMana manaPowderRecipes;
    public static RecipeFountainMana manaDiamondRecipes;
    public static RecipeFountainMana grassSeedsRecipe;
    public static RecipeFountainMana podzolSeedsRecipe;
    public static RecipeFountainMana mycelSeedsRecipes;
    public static RecipeFountainMana manaQuartzRecipe;
    public static RecipeFountainMana managlassRecipe;
    public static RecipeFountainMana manaStringRecipe;
    public static RecipeFountainMana manaBottleRecipe;
    public static RecipeFountainMana manaPearlRecipe;
    public static RecipeFountainMana pistonRelayRecipe;
    public static RecipeFountainMana manaCookieRecipe;
    public static RecipeFountainMana tinyPotatoRecipe;
    public static RecipeFountainMana manaweaveClothRecipe;
    public static RecipeFountainMana wandCoresRecipes;
    public static RecipeFountainMana wandCapRecipe;
    // public static RecipeFountainMana Bee1Recipe;

    public static RecipeFountainAlchemy leatherRecipe;
    public static RecipeFountainAlchemy woodRecipes;
    public static RecipeFountainAlchemy saplingRecipes;
    public static RecipeFountainAlchemy glowstoneDustRecipe;
    public static RecipeFountainAlchemy quartzRecipes;
    public static RecipeFountainAlchemy chiseledBrickRecipe;
    public static RecipeFountainAlchemy iceRecipe;
    public static RecipeFountainAlchemy swampFolliageRecipes;
    public static RecipeFountainAlchemy fishRecipes;
    public static RecipeFountainAlchemy cropRecipes;
    public static RecipeFountainAlchemy potatoRecipe;
    public static RecipeFountainAlchemy netherWartRecipe;
    public static RecipeFountainAlchemy gunpowderAndFlintRecipes;
    public static RecipeFountainAlchemy nameTagRecipe;
    public static RecipeFountainAlchemy stringRecipes;
    public static RecipeFountainAlchemy slimeballCactusRecipes;
    public static RecipeFountainAlchemy enderPearlRecipe;
    public static RecipeFountainAlchemy redstoneToGlowstoneRecipes;
    public static RecipeFountainAlchemy sandRecipe;
    public static RecipeFountainAlchemy redSandRecipe;
    public static RecipeFountainAlchemy clayBreakdownRecipes;
    public static RecipeFountainAlchemy coarseDirtRecipe;
    public static RecipeFountainAlchemy prismarineRecipe;
    public static RecipeFountainAlchemy stoneRecipes;
    public static RecipeFountainAlchemy tallgrassRecipes;
    public static RecipeFountainAlchemy flowersRecipes;
    public static RecipeFountainAlchemy petiteRecipes;
    // public static RecipeFountainAlchemy Bee2Recipe;

    public static RecipeFountainConjuration redstoneRecipe;
    public static RecipeFountainConjuration glowstoneRecipe;
    public static RecipeFountainConjuration quartzRecipe;
    public static RecipeFountainConjuration coalRecipe;
    public static RecipeFountainConjuration snowballRecipe;
    public static RecipeFountainConjuration netherrackRecipe;
    public static RecipeFountainConjuration soulSandRecipe;
    public static RecipeFountainConjuration gravelRecipe;
    public static RecipeFountainConjuration leavesRecipes;
    public static RecipeFountainConjuration grassRecipe;

    public static RecipePetals alphirineRecipe;
    public static RecipePetals PrimalRecipe;
    public static RecipePetals dictariusRecipe;
    public static RecipePetals aspecolusRecipe;
    public static RecipePetals pureGladRecipe;
    public static RecipePetals azartFlowerRecipe;

    public static LexiconEntry advandedAgglomerationPlate;
    public static LexiconEntry FountainMana;
    public static LexiconEntry FountainAlchemy;
    public static LexiconEntry FountainConjuration;
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
    public static ResearchPage FountainManaPages;
    public static ResearchPage FountainManaPages2;
    public static ResearchPage FountainAlchemyPages;
    public static ResearchPage FountainConjurationPages;

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
    public static InfusionRecipe FountainManaB;
    public static InfusionRecipe FountainAlchemyB;
    public static InfusionRecipe FountainConjurationB;

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
        ///////////////////////////////////////////////////////////////////////////////////////// and fountains
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
        EnderAirRecipe = AdvancedBotanyAPI.registerAdvancedPlateRecipe(
                new ItemStack(ModItems.manaResource, 16, 15),
                new ItemStack(ModBlocks.endStoneBrick, 1, 2),
                new ItemStack(ModItems.manaBottle),
                new ItemStack((Item) Item.itemRegistry.getObject("ThaumicTinkerer:fireAir")),
                500,
                0x29de20);
        ////////////////////////////////////////////////////////////////////////////////////////

        // Stone age -- diluted pool, max 10K

        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANASTEEL),
                OreDictionary.getOres("ingotSteel").get(0),
                3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANASTEEL),
                OreDictionary.getOres("ingotSteel").get(1),
                3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANASTEEL),
                OreDictionary.getOres("ingotSteel").get(2),
                3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANASTEEL),
                OreDictionary.getOres("ingotSteel").get(3),
                3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANASTEEL),
                OreDictionary.getOres("ingotThaumium").get(0),
                1500,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANASTEEL),
                OreDictionary.getOres("ingotThaumium").get(1),
                1500,
                0x25d6b7));

        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.storage, 1, STORAGE_META_MANASTEELBLOCK),
                OreDictionary.getOres("blockSteel").get(0),
                9 * 3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.storage, 1, STORAGE_META_MANASTEELBLOCK),
                OreDictionary.getOres("blockSteel").get(1),
                9 * 3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.storage, 1, STORAGE_META_MANASTEELBLOCK),
                OreDictionary.getOres("blockSteel").get(2),
                9 * 3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.storage, 1, STORAGE_META_MANASTEELBLOCK),
                OreDictionary.getOres("blockSteel").get(3),
                9 * 3000,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.storage, 1, STORAGE_META_MANASTEELBLOCK),
                OreDictionary.getOres("blockThaumium").get(0),
                9 * 1500,
                0x25d6b7));
        manasteelRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.storage, 1, STORAGE_META_MANASTEELBLOCK),
                OreDictionary.getOres("blockThaumium").get(1),
                9 * 1500,
                0x25d6b7));

        manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                new ItemStack(Items.gunpowder),
                1000,
                0x25d6b7));
        manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                new ItemStack(Items.redstone),
                1000,
                0x25d6b7));
        manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                new ItemStack(Items.glowstone_dust),
                750,
                0x25d6b7));
        manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                new ItemStack(Items.sugar),
                2000,
                0x25d6b7));
        manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                OreDictionary.getOres("dustThaumium").get(0),
                250,
                0x25d6b7));
        manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                OreDictionary.getOres("dustVinteum").get(0),
                150,
                0x25d6b7));
        manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                OreDictionary.getOres("dustDraconium").get(0),
                50,
                0x25d6b7));
        for (int i = 0; i < 16; i++) manaPowderRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_MANAPOWDER),
                new ItemStack(ModItems.dye, 1, i),
                1500,
                0x25d6b7));

        // only the exquisite diamond is good enough for a diluted pool

        manaDiamondRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_DIAMOND),
                new ItemStack((Item) Item.itemRegistry.getObject("miscutils:IndustrialDiamondExquisite")),
                10000 * 1,
                0x25d6b7));
        manaDiamondRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_DIAMOND),
                OreDictionary.getOres("gemExquisiteDiamond").get(0),
                10000 * 1,
                0x25d6b7));
        manaDiamondRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_DIAMOND),
                OreDictionary.getOres("gemFlawlessDiamond").get(0),
                10000 * 2,
                0x25d6b7));
        manaDiamondRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_DIAMOND),
                OreDictionary.getOres("craftingIndustrialDiamond").get(0),
                10000 * 2,
                0x25d6b7));
        manaDiamondRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_DIAMOND),
                new ItemStack(Items.diamond),
                10000 * 4,
                0x25d6b7));
        manaDiamondRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.storage, 1, STORAGE_META_DIAMONDBLOCK),
                new ItemStack(Blocks.diamond_block),
                10000 * 4 * 9,
                0x25d6b7));

        grassSeedsRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.grassSeeds, 1, SEEDS_META_GRASS),
                new ItemStack(Blocks.tallgrass, 1, 1),
                2500,
                0x25d6b7);
        podzolSeedsRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.grassSeeds, 1, SEEDS_META_PODZOL),
                new ItemStack(Blocks.deadbush),
                2500,
                0x25d6b7);
        mycelSeedsRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.grassSeeds, 1, SEEDS_META_MYCELIUM),
                new ItemStack(ModBlocks.mushroom, 1, Short.MAX_VALUE),
                6500,
                0x25d6b7));

        mycelSeedsRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.grassSeeds, 1, SEEDS_META_MYCELIUM),
                new ItemStack(Blocks.red_mushroom),
                6500,
                0x25d6b7));
        mycelSeedsRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.grassSeeds, 1, SEEDS_META_MYCELIUM),
                new ItemStack(Blocks.brown_mushroom),
                6500,
                0x25d6b7));
        mycelSeedsRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.grassSeeds, 1, SEEDS_META_MYCELIUM),
                new ItemStack((Item) Item.itemRegistry.getObject("BiomesOPlenty:mushrooms"), 1, Short.MAX_VALUE),
                6500,
                0x25d6b7));
        mycelSeedsRecipes = (AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.grassSeeds, 1, SEEDS_META_MYCELIUM),
                new ItemStack((Item) Item.itemRegistry.getObject("harvestcraft:whitemushroomItem")),
                6500,
                0x25d6b7));

        manaQuartzRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.quartz, 1, QUARTZ_META_MANA),
                new ItemStack(Items.quartz),
                1000,
                0x25d6b7);

        managlassRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.manaGlass),
                new ItemStack((Item) Item.itemRegistry.getObject("minecraft:stained_glass"), 1, Short.MAX_VALUE),
                250,
                0x25d6b7);
        managlassRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.manaGlass),
                new ItemStack(Blocks.glass),
                250,
                0x25d6b7);
        manaStringRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, 16),
                new ItemStack(Items.string),
                5000,
                0x25d6b7);

        manaBottleRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaBottle),
                new ItemStack(Items.glass_bottle),
                10000,
                0x25d6b7);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // MV -- regular pool, max 1M
        manaPearlRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_PEARL),
                new ItemStack(Items.ender_pearl),
                10000 * 3 / 2,
                0x25d6b7);

        pistonRelayRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.pistonRelay),
                new ItemStack(Blocks.piston),
                15000,
                0x25d6b7);
        manaCookieRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaCookie),
                new ItemStack(Items.cookie),
                20000,
                0x25d6b7);

        tinyPotatoRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModBlocks.tinyPotato),
                new ItemStack(Items.potato),
                31337,
                0x25d6b7);

        // Manaweave Cloth Recipe
        manaweaveClothRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_CLOTH),
                OreDictionary.getOres("materialCloth").get(0),
                15000,
                0x25d6b7);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        wandCapRecipe = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack((Item) Item.itemRegistry.getObject("ForbiddenMagic:WandCaps"), 1, 3),
                new ItemStack((Item) Item.itemRegistry.getObject("ForbiddenMagic:WandCaps"), 1, 4),
                200,
                0x25d6b7);

        wandCoresRecipes = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack((Item) Item.itemRegistry.getObject("ForbiddenMagic:WandCores"), 1, 11),
                new ItemStack((Item) Item.itemRegistry.getObject("ForbiddenMagic:WandCores"), 1, 12),
                2000,
                0x25d6b7);

        wandCoresRecipes = AdvancedBotanyAPI.registerFountainManaRecipe(
                new ItemStack((Item) Item.itemRegistry.getObject("ForbiddenMagic:WandCores"), 1, 7),
                new ItemStack((Item) Item.itemRegistry.getObject("ForbiddenMagic:WandCores"), 1, 8),
                2000,
                0x25d6b7);

        // Bee1Recipe = AdvancedBotanyAPI.registerFountainManaRecipe(
        // new ItemStack((Item) Item.itemRegistry.getObject("magicbees.speciesBotBotanic")),
        // new ItemStack((Item) Item.itemRegistry.getObject("magicbees.speciesBotRooted")),
        // 450000,
        // 0x25d6b7);
        // Alchemy crafting
        // Bee2Recipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
        // new ItemStack((Item) Item.itemRegistry.getObject("magicbees.speciesBotVazbee")),
        // new ItemStack((Item) Item.itemRegistry.getObject("magicbees.speciesBotFloral")),
        // 450000,
        // 0x25d6b7);

        leatherRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.leather),
                new ItemStack(Items.rotten_flesh),
                600,
                0x25d6b7);

        woodRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.log, 1, 0),
                new ItemStack(Blocks.log2, 1, 1),
                40,
                0x25d6b7));
        woodRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.log, 1, 1),
                new ItemStack(Blocks.log, 1, 0),
                40,
                0x25d6b7));
        woodRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.log, 1, 2),
                new ItemStack(Blocks.log, 1, 1),
                40,
                0x25d6b7));
        woodRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.log, 1, 3),
                new ItemStack(Blocks.log, 1, 2),
                40,
                0x25d6b7));
        woodRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.log2, 1, 0),
                new ItemStack(Blocks.log, 1, 3),
                40,
                0x25d6b7));
        woodRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.log2, 1, 1),
                new ItemStack(Blocks.log2, 1, 0),
                40,
                0x25d6b7));

        for (int i = 0; i < 6; i++) {
            saplingRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                    new ItemStack(Blocks.sapling, 1, i == 5 ? 0 : i + 1),
                    new ItemStack(Blocks.sapling, 1, i),
                    120,
                    0x25d6b7));
        }

        glowstoneDustRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.glowstone_dust, 4),
                new ItemStack(Blocks.glowstone),
                25,
                0x25d6b7);

        quartzRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.quartz, 4),
                new ItemStack(Blocks.quartz_block, 1, Short.MAX_VALUE),
                25,
                0x25d6b7));
        quartzRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModItems.quartz, 4, 0),
                new ItemStack(ModFluffBlocks.darkQuartz, 1, Short.MAX_VALUE),
                25,
                0x25d6b7));
        quartzRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModItems.quartz, 4, 1),
                new ItemStack(ModFluffBlocks.manaQuartz, 1, Short.MAX_VALUE),
                25,
                0x25d6b7));
        quartzRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModItems.quartz, 4, 2),
                new ItemStack(ModFluffBlocks.blazeQuartz, 1, Short.MAX_VALUE),
                25,
                0x25d6b7));
        quartzRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModItems.quartz, 4, 3),
                new ItemStack(ModFluffBlocks.lavenderQuartz, 1, Short.MAX_VALUE),
                25,
                0x25d6b7));
        quartzRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModItems.quartz, 4, 4),
                new ItemStack(ModFluffBlocks.redQuartz, 1, Short.MAX_VALUE),
                25,
                0x25d6b7));
        quartzRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModItems.quartz, 4, 5),
                new ItemStack(ModFluffBlocks.elfQuartz, 1, Short.MAX_VALUE),
                25,
                0x25d6b7));

        chiseledBrickRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.stonebrick, 1, 3),
                new ItemStack(Blocks.stonebrick),
                150,
                0x25d6b7);
        iceRecipe = AdvancedBotanyAPI
                .registerFountainAlchemyRecipe(new ItemStack(Blocks.ice), new ItemStack(Blocks.snow), 2250, 0x25d6b7);

        swampFolliageRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.waterlily),
                new ItemStack(Blocks.vine),
                320,
                0x25d6b7));
        swampFolliageRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.vine),
                new ItemStack(Blocks.waterlily),
                320,
                0x25d6b7));

        for (int i = 0; i < 4; i++) {
            fishRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                    new ItemStack(Items.fish, 1, i == 3 ? 0 : i + 1),
                    new ItemStack(Items.fish, 1, i),
                    200,
                    0x25d6b7));
        }

        cropRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.wheat_seeds),
                new ItemStack(Items.dye, 1, 3),
                6000,
                0x25d6b7));
        cropRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.potato),
                new ItemStack(Items.wheat),
                6000,
                0x25d6b7));
        cropRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.carrot),
                new ItemStack(Items.potato),
                6000,
                0x25d6b7));
        cropRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.melon_seeds),
                new ItemStack(Items.carrot),
                6000,
                0x25d6b7));
        cropRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.pumpkin_seeds),
                new ItemStack(Items.melon_seeds),
                6000,
                0x25d6b7));
        cropRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.dye, 1, 3),
                new ItemStack(Items.pumpkin_seeds),
                6000,
                0x25d6b7));

        potatoRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.potato),
                new ItemStack(Items.poisonous_potato),
                1200,
                0x25d6b7);
        netherWartRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.nether_wart),
                new ItemStack(Items.blaze_rod),
                4000,
                0x25d6b7);

        gunpowderAndFlintRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.flint),
                new ItemStack(Items.gunpowder),
                200,
                0x25d6b7));
        gunpowderAndFlintRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.gunpowder),
                new ItemStack(Items.flint),
                4000,
                0x25d6b7));

        nameTagRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.name_tag),
                new ItemStack(Items.writable_book),
                16000,
                0x25d6b7);

        for (int i = 0; i < 16; i++) {
            stringRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                    new ItemStack(Items.string, 3),
                    new ItemStack(Blocks.wool, 1, i),
                    100,
                    0x25d6b7));
        }

        slimeballCactusRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.slime_ball),
                new ItemStack(Blocks.cactus),
                1200,
                0x25d6b7));
        slimeballCactusRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.cactus),
                new ItemStack(Items.slime_ball),
                1200,
                0x25d6b7));

        enderPearlRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.ender_pearl),
                new ItemStack(Items.ghast_tear),
                28000,
                0x25d6b7);

        redstoneToGlowstoneRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.redstone),
                new ItemStack(Items.glowstone_dust),
                300,
                0x25d6b7));
        redstoneToGlowstoneRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.glowstone_dust),
                new ItemStack(Items.redstone),
                300,
                0x25d6b7));

        sandRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Block.getBlockFromName("sand")),
                new ItemStack(Blocks.cobblestone),
                50,
                0x25d6b7);
        redSandRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Block.getBlockFromName("sand"), 1, 1),
                new ItemStack(Blocks.hardened_clay),
                50,
                0x25d6b7);

        clayBreakdownRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.clay_ball, 4),
                new ItemStack(Blocks.clay),
                25,
                0x25d6b7));
        clayBreakdownRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Items.brick, 4),
                new ItemStack(Blocks.brick_block),
                25,
                0x25d6b7));

        coarseDirtRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.dirt, 1, 1),
                new ItemStack(Blocks.dirt),
                120,
                0x25d6b7);

        prismarineRecipe = AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModItems.manaResource, 1, MANARESOURCE_META_PRISMARINE),
                new ItemStack(Items.quartz),
                200,
                0x25d6b7);

        stoneRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(ModFluffBlocks.stone),
                OreDictionary.getOres("stone").get(0),
                200,
                0x25d6b7));
        for (int i = 0; i < 4; i++) {
            stoneRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                    new ItemStack(ModFluffBlocks.stone, 1, i),
                    new ItemStack(ModFluffBlocks.stone, 1, i == 0 ? 3 : i - 1),
                    200,
                    0x25d6b7));
        }

        tallgrassRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.deadbush),
                new ItemStack(Blocks.tallgrass, 1, 2),
                500,
                0x25d6b7));
        tallgrassRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.tallgrass, 1, 1),
                new ItemStack(Blocks.deadbush),
                500,
                0x25d6b7));
        tallgrassRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.tallgrass, 1, 2),
                new ItemStack(Blocks.tallgrass, 1, 1),
                500,
                0x25d6b7));

        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower),
                new ItemStack(Blocks.yellow_flower),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 1),
                new ItemStack(Blocks.red_flower),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 2),
                new ItemStack(Blocks.red_flower, 1, 1),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 3),
                new ItemStack(Blocks.red_flower, 1, 2),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 4),
                new ItemStack(Blocks.red_flower, 1, 3),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 5),
                new ItemStack(Blocks.red_flower, 1, 4),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 6),
                new ItemStack(Blocks.red_flower, 1, 5),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 7),
                new ItemStack(Blocks.red_flower, 1, 6),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.red_flower, 1, 8),
                new ItemStack(Blocks.red_flower, 1, 7),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.double_plant),
                new ItemStack(Blocks.red_flower, 1, 8),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.double_plant, 1, 1),
                new ItemStack(Blocks.double_plant),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.double_plant, 1, 4),
                new ItemStack(Blocks.double_plant, 1, 1),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.double_plant, 1, 5),
                new ItemStack(Blocks.double_plant, 1, 4),
                400,
                0x25d6b7));
        flowersRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                new ItemStack(Blocks.yellow_flower),
                new ItemStack(Blocks.double_plant, 1, 5),
                400,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("solegnoliaChibi"),
                ItemBlockSpecialFlower.ofType("solegnolia"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("bubbellChibi"),
                ItemBlockSpecialFlower.ofType("bubbell"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("marimorphosisChibi"),
                ItemBlockSpecialFlower.ofType("marimorphosis"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("rannuncarpusChibi"),
                ItemBlockSpecialFlower.ofType("rannuncarpus"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("clayconiaChibi"),
                ItemBlockSpecialFlower.ofType("clayconia"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("agricarnationChibi"),
                ItemBlockSpecialFlower.ofType("agricarnation"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("hopperhockChibi"),
                ItemBlockSpecialFlower.ofType("hopperhock"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("nightshadeDecor"),
                ItemBlockSpecialFlower.ofType("nightshade"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("bellethornChibi"),
                ItemBlockSpecialFlower.ofType("bellethorn"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("daybloomDecor"),
                ItemBlockSpecialFlower.ofType("daybloom"),
                30,
                0x25d6b7));
        petiteRecipes = (AdvancedBotanyAPI.registerFountainAlchemyRecipe(
                ItemBlockSpecialFlower.ofType("hydroangeasDecor"),
                ItemBlockSpecialFlower.ofType("hydroangeas"),
                30,
                0x25d6b7));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Conjuration catalyst

        redstoneRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Items.redstone, 2),
                new ItemStack(Items.redstone),
                5000,
                0x25d6b7);
        glowstoneRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Items.glowstone_dust, 2),
                new ItemStack(Items.glowstone_dust),
                5000,
                0x25d6b7);
        quartzRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Items.quartz, 2),
                new ItemStack(Items.quartz),
                7500,
                0x25d6b7);
        coalRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Items.coal, 2),
                new ItemStack(Items.coal),
                7500,
                0x25d6b7);
        snowballRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Items.snowball, 2),
                new ItemStack(Items.snowball),
                200,
                0x25d6b7);
        netherrackRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Blocks.netherrack, 2),
                new ItemStack(Blocks.netherrack),
                200,
                0x25d6b7);
        soulSandRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Blocks.soul_sand, 2),
                new ItemStack(Blocks.soul_sand),
                4500,
                0x25d6b7);
        gravelRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Block.getBlockFromName("gravel"), 2),
                new ItemStack(Block.getBlockFromName("gravel")),
                720,
                0x25d6b7);

        for (int i = 0; i < 4; i++) leavesRecipes = (AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Blocks.leaves, 2, i),
                new ItemStack(Blocks.leaves, 1, i),
                2000,
                0x25d6b7));
        for (int i = 0; i < 2; i++) leavesRecipes = (AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Blocks.leaves2, 2, i),
                new ItemStack(Blocks.leaves2, 1, i),
                2000,
                0x25d6b7));

        grassRecipe = AdvancedBotanyAPI.registerFountainConjurationRecipe(
                new ItemStack(Blocks.tallgrass, 2, 1),
                new ItemStack(Blocks.tallgrass, 1, 1),
                2000,
                0x25d6b7);

        ///////////////////////////////////////////////////////////////////////////////////////// Shaped Recipes
        PrimalRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("daybloomPrime"),
                ItemBlockSpecialFlower.ofType("daybloomDecor"),
                new ItemStack(ModItems.overgrowthSeed),
                new ItemStack(ItemListAB.itemManaFlower),
                new ItemStack(ModItems.petal, 1, 1),
                new ItemStack(ModItems.petal, 1, 4),
                new ItemStack(ModItems.petal, 1, 3));

        PrimalRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("nightshadePrime"),
                ItemBlockSpecialFlower.ofType("nightshadeDecor"),
                new ItemStack(ModItems.overgrowthSeed),
                new ItemStack(ItemListAB.itemManaFlower),
                new ItemStack(ModItems.petal, 1, 10),
                new ItemStack(ModItems.petal, 1, 7),
                new ItemStack(ModItems.petal, 1, 15)

        );
        // Ancient alphirine recipe
        ancientAlphirine = new BLexiconEntry("ancientAlphirine", categoryForgotten);
        alphirineRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("ancientAlphirine"),
                new ItemStack(ModBlocks.storage, 1, 1),
                new ItemStack(ModItems.quartz, 1, 2),
                new ItemStack(ModItems.quartz, 1, 0),
                new ItemStack(ModItems.quartz, 1, 3),
                new ItemStack(ModItems.quartz, 1, 1),
                new ItemStack(ModItems.quartz, 1, 4),
                new ItemStack(ModItems.quartz, 1, 6),
                new ItemStack(ModItems.quartz, 1, 5),
                new ItemStack(ModBlocks.storage, 1, 1),
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
                        new ItemStack(BlockListAB.blockManaContainer, 1, 1),
                        "M5M",
                        "LPL",
                        "MAM",
                        'M',
                        new ItemStack((Item) Item.itemRegistry.getObject("ForbiddenMagic:WandCaps"), 1, 3),
                        '5',
                        new ItemStack(ModItems.rune, 1, 15),
                        'L',
                        new ItemStack(ModBlocks.pool, 1, 2),
                        'P',
                        new ItemStack(ModBlocks.pylon),
                        'A',
                        new ItemStack(ItemListAB.itemAntigravityCharm)));

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

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemAdvancedSpark),
                        "PDP",
                        "PSP",
                        "PLP",
                        'D',
                        new ItemStack(ModItems.sparkUpgrade, 1, 1),
                        'S',
                        new ItemStack(ModItems.spark),
                        'L',
                        new ItemStack(BlockListAB.blockLebethron, 1, 3),
                        'P',
                        "plateTerrasteel"));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(BlockListAB.blockEngineerHopper),
                        "DEO",
                        "SRS",
                        "BHB",
                        'E',
                        "pipeLargeElectrum",
                        'D',
                        new ItemStack(Blocks.dispenser),
                        'O',
                        new ItemStack(Blocks.dropper),
                        'S',
                        "springRoseGold",
                        'R',
                        new ItemStack(ModItems.rainbowRod),
                        'B',
                        new ItemStack(ModBlocks.bifrostPerm),
                        'H',
                        new ItemStack(Blocks.hopper)

                ));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ItemListAB.itemABResource, 1, 3),
                        "BLB",
                        "EAN",
                        "BMB",
                        'B',
                        new ItemStack(ModBlocks.bifrostPerm),
                        'L',
                        new ItemStack(BlockListAB.blockLebethron, 1, 3),
                        'E',
                        new ItemStack(Blocks.end_stone),
                        'N',
                        new ItemStack(Blocks.netherrack),
                        'A',
                        new ItemStack(ModItems.manaResource, 1, 15),
                        'M',
                        new ItemStack(ModBlocks.storage, 1, 2)

                ));

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
                        OreDictionary.getOres("gemExquisiteManaDiamond").get(0),
                        new ItemStack(ItemListAB.itemABResource, 1, 0), new ItemStack(ItemListAB.itemNebulaRod),
                        new ItemStack(ModBlocks.dreamwood, 1, 5), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ModBlocks.livingwood, 1, 5),
                        new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:HandMirror")),
                        new ItemStack(ModBlocks.livingwood, 1, 5), new ItemStack(ItemListAB.itemABResource, 1, 5),
                        new ItemStack(ModBlocks.dreamwood, 1, 5), new ItemStack(ItemListAB.itemNebulaRod),
                        new ItemStack(ItemListAB.itemABResource, 1, 0),
                        OreDictionary.getOres("gemExquisiteAmethyst").get(0),
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

        FountainManaB = ThaumcraftApi.addInfusionCraftingRecipe(
                "FountainMana",

                new ItemStack(BlockListAB.blockABFountain),
                12,
                new AspectList().add(Aspect.MAGIC, 180).add(Aspect.WATER, 100).add(Aspect.EARTH, 100)
                        .add(Aspect.CRYSTAL, 80).add(Aspect.ORDER, 40).add(Aspect.EXCHANGE, 20),
                new ItemStack(ModBlocks.pool),
                new ItemStack[] { new ItemStack(BlockListAB.blockManaCharger), new ItemStack(ModBlocks.pylon),
                        new ItemStack(ModItems.lens, 1, 2), new ItemStack(ModBlocks.pool, 1, 2),
                        new ItemStack(ModBlocks.pump), new ItemStack(ModBlocks.pool, 1, 2),
                        new ItemStack(ModItems.lens, 1, 4), new ItemStack(ModItems.rune, 1, 8),
                        new ItemStack(BlockListAB.blockABPlate), new ItemStack(ModItems.rune, 1, 8),
                        new ItemStack(ModItems.lens, 1, 3), new ItemStack(ModBlocks.pool, 1, 2),
                        new ItemStack(ModBlocks.pump), new ItemStack(ModBlocks.pool, 1, 2),
                        new ItemStack(ModItems.lens, 1, 1), new ItemStack(ModBlocks.pylon),

                });

        FountainAlchemyB = ThaumcraftApi.addInfusionCraftingRecipe(
                "FountainAlchemy",

                new ItemStack(BlockListAB.blockABAlchemy),
                12,
                new AspectList().add(Aspect.MAGIC, 180).add(Aspect.WATER, 100).add(Aspect.EARTH, 100)
                        .add(Aspect.VOID, 80).add(Aspect.ORDER, 40).add(Aspect.EXCHANGE, 20),
                new ItemStack(BlockListAB.blockABFountain),
                new ItemStack[] { new ItemStack(ModBlocks.alchemyCatalyst), new ItemStack(ModBlocks.livingwood, 1, 5),
                        new ItemStack(ModBlocks.livingwood, 1, 5),
                        OreDictionary.getOres("gemExquisiteManaDiamond").get(0), new ItemStack(ModBlocks.brewery),
                        OreDictionary.getOres("gemExquisiteManaDiamond").get(0),
                        new ItemStack(ModBlocks.livingwood, 1, 5), new ItemStack(ModBlocks.livingwood, 1, 5),

                });

        FountainConjurationB = ThaumcraftApi.addInfusionCraftingRecipe(
                "FountainConjuration",

                new ItemStack(BlockListAB.blockABConjuration),
                12,
                new AspectList().add(Aspect.MAGIC, 380).add(Aspect.FIRE, 200).add(Aspect.EARTH, 127)
                        .add(Aspect.CRYSTAL, 80).add(Aspect.ORDER, 40).add(Aspect.EXCHANGE, 200),
                new ItemStack(BlockListAB.blockABAlchemy),
                new ItemStack[] { new ItemStack(ModBlocks.conjurationCatalyst),
                        new ItemStack(ModBlocks.dreamwood, 1, 5), new ItemStack(ModBlocks.dreamwood, 1, 5),
                        OreDictionary.getOres("gemExquisiteBotaniaDragonstone").get(0),
                        new ItemStack(ModBlocks.pylon, 1, 1),
                        OreDictionary.getOres("gemExquisiteBotaniaDragonstone").get(0),
                        new ItemStack(ModBlocks.dreamwood, 1, 5), new ItemStack(ModBlocks.dreamwood, 1, 5),

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

        ResearchItem FountainManaPage;
        ResearchPage FountainManaPages;
        ResearchPage FountainManaPages2;
        ResearchPage infFountainManaPages;

        ResearchItem FountainAlchemyPage;
        ResearchPage FountainAlchemyPages;
        ResearchPage infFountainAlchemyPages;

        ResearchItem FountainConjurationPage;
        ResearchPage FountainConjurationPages;
        ResearchPage infFountainConjurationPages;

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

        FountainManaPage = new ResearchItem(
                "FountainMana",
                category,
                new AspectList().add(Aspect.MAGIC, 18).add(Aspect.WATER, 10).add(Aspect.EARTH, 10)
                        .add(Aspect.CRYSTAL, 8).add(Aspect.ORDER, 4).add(Aspect.EXCHANGE, 2),
                8,
                9,
                0,
                new ItemStack(BlockListAB.blockABFountain));

        FountainManaPages = new ResearchPage("FountainManaPages");
        FountainManaPages2 = new ResearchPage("FountainManaPages2");
        infFountainManaPages = new ResearchPage(FountainManaB);

        FountainAlchemyPage = new ResearchItem(
                "FountainAlchemy",
                category,
                new AspectList().add(Aspect.MAGIC, 18).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.VOID, 8)
                        .add(Aspect.ORDER, 4).add(Aspect.EXCHANGE, 2),
                8,
                10,
                0,
                new ItemStack(BlockListAB.blockABAlchemy));

        FountainAlchemyPages = new ResearchPage("FountainAlchemyPages");
        infFountainAlchemyPages = new ResearchPage(FountainAlchemyB);

        FountainConjurationPage = new ResearchItem(
                "FountainConjuration",
                category,
                new AspectList().add(Aspect.MAGIC, 18).add(Aspect.FIRE, 10).add(Aspect.EARTH, 10).add(Aspect.CRYSTAL, 8)
                        .add(Aspect.ORDER, 4).add(Aspect.EXCHANGE, 2),
                8,
                11,
                0,
                new ItemStack(BlockListAB.blockABConjuration));

        FountainConjurationPages = new ResearchPage("FountainConjurationPages");
        infFountainConjurationPages = new ResearchPage(FountainConjurationB);

        TerraHoePage.setPages(TerraHoePages, infTerraHoePages);
        AquaSwordPage.setPages(AquaSwordPages, infAquaSwordPages);
        ManaChargerPage.setPages(ManaChargerPages, infManaChargerPages);
        MithrillSwordPage.setPages(MithrillSwordPages, infMithrillSwordPages);
        ForgePage.setPages(ForgePages, infForgePages);
        DestroyerPage.setPages(DestroyerPages, infDestroyerPages);
        NebulaPage.setPages(NebulaPages, infNebulaHelm, infNebulaChest, infNebulaLegs, infNebulaBoots);
        FountainManaPage.setPages(FountainManaPages, FountainManaPages2, infFountainManaPages);
        FountainAlchemyPage.setPages(FountainAlchemyPages, infFountainAlchemyPages);
        FountainConjurationPage.setPages(FountainConjurationPages, infFountainConjurationPages);

        TerraHoePage.setParents("ELEMENTALHOE");
        AquaSwordPage.setParents("Nebula");
        ManaChargerPage.setParents("MIRROR");
        MithrillSwordPage.setParents("AquaSword");
        ForgePage.setParents("ManaCharger");
        DestroyerPage.setParents("TerraHoe", "Nebula");
        NebulaPage.setParents("Forge");
        FountainManaPage.setParents("Nebula");
        FountainAlchemyPage.setParents("FountainMana");
        FountainConjurationPage.setParents("FountainAlchemy");

        ThaumcraftApi.addWarpToResearch("Nebula", 8);
        ThaumcraftApi.addWarpToResearch("FountainMana", 8);

        ResearchCategories.addResearch(TerraHoePage);
        ResearchCategories.addResearch(AquaSwordPage);
        ResearchCategories.addResearch(ManaChargerPage);
        ResearchCategories.addResearch(MithrillSwordPage);
        ResearchCategories.addResearch(ForgePage);
        ResearchCategories.addResearch(DestroyerPage);
        ResearchCategories.addResearch(NebulaPage);
        ResearchCategories.addResearch(FountainManaPage);
        ResearchCategories.addResearch(FountainAlchemyPage);
        ResearchCategories.addResearch(FountainConjurationPage);
    }

    public static ResearchPage getResearchPage(String ident) {
        return new ResearchPage(LocalizationManager.getLocalizedString("tc.research_page." + ident));
    }

    public static void setupItemAspects() {}
}
