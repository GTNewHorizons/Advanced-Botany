package com.integral.forgottenrelics.research;

import net.minecraft.item.ItemStack;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import com.integral.forgottenrelics.Main;
import com.integral.forgottenrelics.handlers.SuperpositionHandler;

public class RelicsAspectRegistry {

    public static void registerItemAspectsFirst() {

        ItemStack mysticFlowers = new ItemStack(ModBlocks.flower, 1, 0);
        ItemStack mysticDoubleFlowers1 = new ItemStack(ModBlocks.doubleFlower1, 1, 0);
        ItemStack mysticDoubleFlowers2 = new ItemStack(ModBlocks.doubleFlower2, 1, 0);
        ItemStack livingwoodPlanks = new ItemStack(ModBlocks.livingwood, 1, 1);
        ItemStack dreamwoodPlanks = new ItemStack(ModBlocks.dreamwood, 1, 0);
        ItemStack buriedPetals = new ItemStack(ModBlocks.buriedPetals, 1, 0);
        ItemStack altGrasses = new ItemStack(ModBlocks.altGrass, 1, 0);
        ItemStack mushrooms = new ItemStack(ModBlocks.mushroom, 1, 0);

        ItemStack specialFlowers = new ItemStack(ModBlocks.specialFlower, 1);
        ItemStack floatingSpecialFlowers = new ItemStack(ModBlocks.floatingSpecialFlower, 1);
        ItemStack floatingFlowers = new ItemStack(ModBlocks.floatingFlower, 1);

        ItemStack enchantedSoil = new ItemStack(ModBlocks.enchantedSoil, 1, 0);
        ItemStack enchanter = new ItemStack(ModBlocks.enchanter, 1, 0);
        ItemStack pistonRelay = new ItemStack(ModBlocks.pistonRelay, 1, 0);
        ItemStack livingwood = new ItemStack(ModBlocks.livingwood, 1, 0);
        ItemStack livingrock = new ItemStack(ModBlocks.livingrock, 1, 0);
        ItemStack bifrostPerm = new ItemStack(ModBlocks.bifrostPerm, 1, 0);
        ItemStack bifrost = new ItemStack(ModBlocks.bifrost, 1, 0);
        ItemStack dreamwood = new ItemStack(ModBlocks.dreamwood, 1, 0);
        ItemStack manaGlass = new ItemStack(ModBlocks.manaGlass, 1, 0);
        ItemStack elfGlass = new ItemStack(ModBlocks.elfGlass, 1, 0);
        ItemStack gaiaHead = new ItemStack(ModBlocks.gaiaHead, 1, 0);

        ItemStack grassSeeds = new ItemStack(ModItems.grassSeeds, 1, 0);
        ItemStack petals = new ItemStack(ModItems.petal, 1, 0);
        ItemStack dyes = new ItemStack(ModItems.dye, 1, 0);
        ItemStack quartzStuff = new ItemStack(ModItems.quartz, 1, 0);
        ItemStack ancientWills = new ItemStack(ModItems.ancientWill, 1, 0);

        ItemStack fateDice = new ItemStack(ModItems.dice, 1, 0);
        ItemStack kingKey = new ItemStack(ModItems.kingKey, 1, 0);
        ItemStack infiniteFruit = new ItemStack(ModItems.infiniteFruit, 1, 0);
        ItemStack flugelEye = new ItemStack(ModItems.flugelEye, 1, 0);
        ItemStack odinRing = new ItemStack(ModItems.odinRing, 1, 0);
        ItemStack thorRing = new ItemStack(ModItems.thorRing, 1, 0);
        ItemStack lokiRing = new ItemStack(ModItems.lokiRing, 1, 0);
        ItemStack aesirRing = new ItemStack(ModItems.aesirRing, 1, 0);

        ItemStack manasteelIngot = new ItemStack(ModItems.manaResource, 1, 0);
        ItemStack manaPearl = new ItemStack(ModItems.manaResource, 1, 1);
        ItemStack manaDiamond = new ItemStack(ModItems.manaResource, 1, 2);
        ItemStack livingwoodTwig = new ItemStack(ModItems.manaResource, 1, 3);
        ItemStack terrasteelIngot = new ItemStack(ModItems.manaResource, 1, 4);
        ItemStack gaiaSpirit = new ItemStack(ModItems.manaResource, 1, 5);
        ItemStack redstoneRoot = new ItemStack(ModItems.manaResource, 1, 6);
        ItemStack elementiumIngot = new ItemStack(ModItems.manaResource, 1, 7);
        ItemStack pixieDust = new ItemStack(ModItems.manaResource, 1, 8);
        ItemStack dragonStone = new ItemStack(ModItems.manaResource, 1, 9);
        ItemStack prismarine = new ItemStack(ModItems.manaResource, 1, 10);
        ItemStack craftBlank = new ItemStack(ModItems.manaResource, 1, 11);
        ItemStack redString = new ItemStack(ModItems.manaResource, 1, 12);
        ItemStack dreamwoodTwig = new ItemStack(ModItems.manaResource, 1, 13);
        ItemStack gaiaSpiritIngot = new ItemStack(ModItems.manaResource, 1, 14);
        ItemStack enderAir = new ItemStack(ModItems.manaResource, 1, 15);
        ItemStack manaString = new ItemStack(ModItems.manaResource, 1, 16);
        ItemStack manasteelNugget = new ItemStack(ModItems.manaResource, 1, 17);
        ItemStack terrasteelNugget = new ItemStack(ModItems.manaResource, 1, 18);
        ItemStack elementiumNugget = new ItemStack(ModItems.manaResource, 1, 19);
        ItemStack manaweaveCloth = new ItemStack(ModItems.manaResource, 1, 22);
        ItemStack manaPowder = new ItemStack(ModItems.manaResource, 1, 23);
        ItemStack manaInkwell = new ItemStack(ModItems.manaInkwell, 1, 0);
        ItemStack manaBottle = new ItemStack(ModItems.manaBottle, 1, 0);
        ItemStack manaCookie = new ItemStack(ModItems.manaCookie, 1, 0);
        ItemStack blackLotus = new ItemStack(ModItems.blackLotus, 1, 0);
        ItemStack greatBlackLotus = new ItemStack(ModItems.blackLotus, 1, 1);
        ItemStack lexicon = new ItemStack(ModItems.lexicon, 1, 0);
        ItemStack waterRune = new ItemStack(ModItems.rune, 1, 0);
        ItemStack fireRune = new ItemStack(ModItems.rune, 1, 1);
        ItemStack earthRune = new ItemStack(ModItems.rune, 1, 2);
        ItemStack airRune = new ItemStack(ModItems.rune, 1, 3);
        ItemStack springRune = new ItemStack(ModItems.rune, 1, 4);
        ItemStack summerRune = new ItemStack(ModItems.rune, 1, 5);
        ItemStack autumnRune = new ItemStack(ModItems.rune, 1, 6);
        ItemStack winterRune = new ItemStack(ModItems.rune, 1, 7);
        ItemStack manaRune = new ItemStack(ModItems.rune, 1, 8);
        ItemStack lustRune = new ItemStack(ModItems.rune, 1, 9);
        ItemStack gluttonyRune = new ItemStack(ModItems.rune, 1, 10);
        ItemStack greedRune = new ItemStack(ModItems.rune, 1, 11);
        ItemStack slothRune = new ItemStack(ModItems.rune, 1, 12);
        ItemStack wrathRune = new ItemStack(ModItems.rune, 1, 13);
        ItemStack envyRune = new ItemStack(ModItems.rune, 1, 14);
        ItemStack prideRune = new ItemStack(ModItems.rune, 1, 15);
        ItemStack overgrowthSeed = new ItemStack(ModItems.overgrowthSeed, 1, 0);
        ItemStack pinkinator = new ItemStack(ModItems.pinkinator, 1, 0);
        ItemStack waterBowl = new ItemStack(ModItems.waterBowl, 1, 0);
        ItemStack brewVial = new ItemStack(ModItems.brewVial, 1, 0);
        ItemStack brewFlask = new ItemStack(ModItems.brewFlask, 1, 0);

        ItemStack goldenLaurel = new ItemStack(ModItems.goldLaurel, 1, 0);
        // ThaumcraftApi.registerObjectTag(goldenLaurel, new AspectList().add(Aspect.VOID, 4).add(Aspect.DARKNESS, 4));

        /*
         * for (int counter = 0; counter <= 15; counter++) { .setItemDamage(counter); ThaumcraftApi.registerObjectTag(,
         * new AspectList()); } SuperpositionHandler.setItemAspectsForMetaRange(grassSeeds, , 15);
         */

        SuperpositionHandler.setItemAspectsForMetaRange(
                mysticFlowers,
                new AspectList().add(Aspect.SENSES, 6).add(Aspect.PLANT, 4).add(Aspect.LIFE, 3).add(Aspect.MAGIC, 3),
                15,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                mysticDoubleFlowers1,
                new AspectList().add(Aspect.SENSES, 10).add(Aspect.PLANT, 7).add(Aspect.LIFE, 5).add(Aspect.MAGIC, 5),
                15,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                mysticDoubleFlowers2,
                new AspectList().add(Aspect.SENSES, 10).add(Aspect.PLANT, 7).add(Aspect.LIFE, 5).add(Aspect.MAGIC, 5),
                15,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                grassSeeds,
                new AspectList().add(Aspect.PLANT, 4).add(Aspect.EXCHANGE, 2).add(Aspect.MAGIC, 2),
                8,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                petals,
                new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 1).add(Aspect.MAGIC, 1),
                15,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                dyes,
                new AspectList().add(Aspect.SENSES, 1).add(Aspect.ENTROPY, 1).add(Aspect.MAGIC, 0),
                15,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(livingwoodPlanks, new AspectList().add(Aspect.TREE, 1), 4, 1);
        SuperpositionHandler.setItemAspectsForMetaRange(dreamwoodPlanks, new AspectList().add(Aspect.TREE, 1), 4, 1);
        SuperpositionHandler.setItemAspectsForMetaRange(
                buriedPetals,
                new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 1).add(Aspect.MAGIC, 1),
                15,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                altGrasses,
                new AspectList().add(Aspect.PLANT, 3).add(Aspect.EXCHANGE, 1),
                8,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                quartzStuff,
                new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.ENERGY, 1).add(Aspect.EXCHANGE, 1),
                6,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                ancientWills,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.EXCHANGE, 4).add(Aspect.DARKNESS, 4)
                        .add(Aspect.ENERGY, 4).add(Aspect.MAGIC, 4),
                5,
                0);
        SuperpositionHandler.setItemAspectsForMetaRange(
                mushrooms,
                new AspectList().add(Aspect.PLANT, 4).add(Aspect.SENSES, 3).add(Aspect.LIGHT, 3).add(Aspect.MAGIC, 2),
                15,
                0);

        ThaumcraftApi.registerObjectTag(
                floatingFlowers,
                new AspectList().add(Aspect.PLANT, 4).add(Aspect.SENSES, 3).add(Aspect.LIFE, 3).add(Aspect.FLIGHT, 2)
                        .add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                floatingSpecialFlowers,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.PLANT, 4).add(Aspect.SENSES, 4).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerObjectTag(
                specialFlowers,
                new AspectList().add(Aspect.MAGIC, 4).add(Aspect.PLANT, 4).add(Aspect.SENSES, 4));

        ThaumcraftApi.registerObjectTag(
                fateDice,
                new AspectList().add(Aspect.EXCHANGE, 32).add(Aspect.SOUL, 8).add(Aspect.MAGIC, 8));
        ThaumcraftApi.registerObjectTag(
                kingKey,
                new AspectList().add(Aspect.WEAPON, 16).add(Aspect.LIGHT, 8).add(Aspect.ENERGY, 4)
                        .add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                infiniteFruit,
                new AspectList().add(Aspect.HUNGER, 16).add(Aspect.CROP, 8).add(Aspect.EXCHANGE, 4)
                        .add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                flugelEye,
                new AspectList().add(Aspect.SENSES, 16).add(Aspect.TRAVEL, 8).add(Aspect.FLIGHT, 4)
                        .add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                odinRing,
                new AspectList().add(Aspect.LIFE, 12).add(Aspect.FIRE, 8).add(Aspect.ARMOR, 8).add(Aspect.EXCHANGE, 4)
                        .add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                thorRing,
                new AspectList().add(Aspect.ENERGY, 12).add(Aspect.EARTH, 8).add(Aspect.TOOL, 8).add(Aspect.EXCHANGE, 4)
                        .add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                lokiRing,
                new AspectList().add(Aspect.CRAFT, 12).add(Aspect.TRAVEL, 8).add(Aspect.SENSES, 8)
                        .add(Aspect.EXCHANGE, 4).add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                aesirRing,
                new AspectList().add(Aspect.LIFE, 16).add(Aspect.ENERGY, 16).add(Aspect.CRAFT, 16)
                        .add(Aspect.EXCHANGE, 12).add(Aspect.MAGIC, 12));

        ThaumcraftApi.registerObjectTag(
                livingwood,
                new AspectList().add(Aspect.TREE, 4).add(Aspect.LIFE, 2).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                dreamwood,
                new AspectList().add(Aspect.TREE, 4).add(Aspect.FLIGHT, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                livingrock,
                new AspectList().add(Aspect.EARTH, 4).add(Aspect.LIFE, 2).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                bifrostPerm,
                new AspectList().add(Aspect.SENSES, 2).add(Aspect.MAGIC, 1).add(Aspect.EXCHANGE, 1));
        ThaumcraftApi.registerObjectTag(
                bifrost,
                new AspectList().add(Aspect.SENSES, 2).add(Aspect.MAGIC, 1).add(Aspect.EXCHANGE, 1));
        ThaumcraftApi.registerObjectTag(manaGlass, new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                elfGlass,
                new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.FLIGHT, 1).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                pistonRelay,
                new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.MOTION, 4).add(Aspect.TRAVEL, 4)
                        .add(Aspect.EXCHANGE, 2).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                enchanter,
                new AspectList().add(Aspect.MIND, 8).add(Aspect.ENERGY, 6).add(Aspect.MAGIC, 6).add(Aspect.AURA, 4)
                        .add(Aspect.EXCHANGE, 4).add(Aspect.SENSES, 2));
        ThaumcraftApi.registerObjectTag(
                enchantedSoil,
                new AspectList().add(Aspect.PLANT, 4).add(Aspect.EXCHANGE, 3).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                gaiaHead,
                new AspectList().add(Aspect.DEATH, 4).add(Aspect.SOUL, 4).add(Aspect.MAGIC, 4));

        ThaumcraftApi.registerObjectTag(
                manasteelIngot,
                new AspectList().add(Aspect.METAL, 3).add(Aspect.LIFE, 1).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                manaPearl,
                new AspectList().add(Aspect.ELDRITCH, 3).add(Aspect.TRAVEL, 3).add(Aspect.MAGIC, 2)
                        .add(Aspect.LIFE, 1));
        ThaumcraftApi.registerObjectTag(
                manaDiamond,
                new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.GREED, 3).add(Aspect.LIFE, 1).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                livingwoodTwig,
                new AspectList().add(Aspect.TREE, 4).add(Aspect.LIFE, 4).add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                terrasteelIngot,
                new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 2).add(Aspect.LIFE, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                gaiaSpirit,
                new AspectList().add(Aspect.LIFE, 16).add(Aspect.EARTH, 8).add(Aspect.SOUL, 8).add(Aspect.EXCHANGE, 5)
                        .add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                elementiumIngot,
                new AspectList().add(Aspect.METAL, 3).add(Aspect.FLIGHT, 1).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(
                pixieDust,
                new AspectList().add(Aspect.LIFE, 4).add(Aspect.LIGHT, 4).add(Aspect.FLIGHT, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                dragonStone,
                new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.GREED, 2).add(Aspect.FIRE, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                prismarine,
                new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.WATER, 1).add(Aspect.MAGIC, 1));
        ThaumcraftApi.registerObjectTag(craftBlank, new AspectList().add(Aspect.CRAFT, 1));
        ThaumcraftApi.registerObjectTag(
                dreamwoodTwig,
                new AspectList().add(Aspect.TREE, 4).add(Aspect.FLIGHT, 4).add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(enderAir, new AspectList().add(Aspect.AIR, 3).add(Aspect.ELDRITCH, 2));
        ThaumcraftApi.registerObjectTag(
                manaString,
                new AspectList().add(Aspect.CLOTH, 1).add(Aspect.LIFE, 0).add(Aspect.MAGIC, 0));
        ThaumcraftApi.registerObjectTag(manasteelNugget, new AspectList().add(Aspect.METAL, 1));
        ThaumcraftApi.registerObjectTag(terrasteelNugget, new AspectList().add(Aspect.METAL, 1));
        ThaumcraftApi.registerObjectTag(elementiumNugget, new AspectList().add(Aspect.METAL, 1));
        ThaumcraftApi.registerObjectTag(
                manaweaveCloth,
                new AspectList().add(Aspect.CLOTH, 4).add(Aspect.LIFE, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                manaPowder,
                new AspectList().add(Aspect.LIFE, 1).add(Aspect.MAGIC, 1).add(Aspect.ENTROPY, 0));
        ThaumcraftApi.registerObjectTag(
                waterRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.WATER, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                earthRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.EARTH, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                airRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.AIR, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                fireRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.FIRE, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                springRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.WATER, 2).add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                summerRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.LIGHT, 2).add(Aspect.MOTION, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                autumnRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.HARVEST, 2).add(Aspect.TRAP, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                winterRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.COLD, 2).add(Aspect.DEATH, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                manaRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.LIFE, 4).add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(
                lustRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.FLESH, 4).add(Aspect.HUNGER, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                gluttonyRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.HUNGER, 8).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                greedRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.GREED, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                slothRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.TRAP, 4).add(Aspect.SOUL, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                wrathRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.WEAPON, 4).add(Aspect.FIRE, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                envyRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.SENSES, 4).add(Aspect.HUNGER, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                prideRune,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.VOID, 4).add(Aspect.FLIGHT, 4).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                manaInkwell,
                new AspectList().add(Aspect.MIND, 4).add(Aspect.SENSES, 3).add(Aspect.LIFE, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                manaCookie,
                new AspectList().add(Aspect.HUNGER, 16).add(Aspect.LIFE, 4).add(Aspect.MAGIC, 8));
        ThaumcraftApi.registerObjectTag(
                blackLotus,
                new AspectList().add(Aspect.MAGIC, 8).add(Aspect.PLANT, 4).add(Aspect.LIFE, 4));
        ThaumcraftApi.registerObjectTag(
                greatBlackLotus,
                new AspectList().add(Aspect.MAGIC, 32).add(Aspect.PLANT, 8).add(Aspect.LIFE, 8));
        ThaumcraftApi.registerObjectTag(
                lexicon,
                new AspectList().add(Aspect.MIND, 6).add(Aspect.PLANT, 4).add(Aspect.LIFE, 2).add(Aspect.MAGIC, 2));
        ThaumcraftApi.registerObjectTag(
                overgrowthSeed,
                new AspectList().add(Aspect.PLANT, 8).add(Aspect.ENERGY, 4).add(Aspect.EXCHANGE, 4)
                        .add(Aspect.MAGIC, 3));
        ThaumcraftApi.registerObjectTag(
                manaBottle,
                new AspectList().add(Aspect.LIFE, 8).add(Aspect.EXCHANGE, 4).add(Aspect.MAGIC, 4).add(Aspect.WATER, 1));
        ThaumcraftApi.registerObjectTag(
                pinkinator,
                new AspectList().add(Aspect.SENSES, 8).add(Aspect.TOOL, 5).add(Aspect.EXCHANGE, 4)
                        .add(Aspect.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(waterBowl, new AspectList().add(Aspect.WATER, 1));
        ThaumcraftApi.registerObjectTag(
                brewVial,
                new AspectList().add(Aspect.WATER, 4).add(Aspect.MAGIC, 4).add(Aspect.EXCHANGE, 2).add(Aspect.LIFE, 2));
        ThaumcraftApi.registerObjectTag(
                brewFlask,
                new AspectList().add(Aspect.WATER, 8).add(Aspect.MAGIC, 6).add(Aspect.EXCHANGE, 4).add(Aspect.LIFE, 4));

    }

    public static void registerItemAspectsLast() {

        ItemStack superpositionRing = new ItemStack(Main.itemSuperpositionRing, 1, 0);
        ItemStack weatherStone = new ItemStack(Main.itemWeatherStone, 1, 0);
        ItemStack miningCharm = new ItemStack(Main.itemMiningCharm, 1, 0);
        ItemStack advancedMiningCharm = new ItemStack(Main.itemAdvancedMiningCharm, 1, 0);
        ItemStack ancientAegis = new ItemStack(Main.itemAncientAegis, 1, 0);
        ItemStack apotheosis = new ItemStack(Main.itemApotheosis, 1, 0);
        ItemStack nebulousCore = new ItemStack(Main.itemArcanum, 1, 0);
        ItemStack chaosCore = new ItemStack(Main.itemChaosCore, 1, 0);
        ItemStack chaosTome = new ItemStack(Main.itemChaosTome, 1, 0);
        ItemStack crimsonSpell = new ItemStack(Main.itemCrimsonSpell, 1, 0);
        ItemStack darkSunRing = new ItemStack(Main.itemDarkSunRing, 1, 0);
        ItemStack deificAmulet = new ItemStack(Main.itemDeificAmulet, 1, 0);
        ItemStack dimensionalMirror = new ItemStack(Main.itemDimensionalMirror, 1, 0);
        ItemStack eldritchSpell = new ItemStack(Main.itemEldritchSpell, 1, 0);
        ItemStack falseJustice = new ItemStack(Main.itemFalseJustice, 1, 0);
        ItemStack fateTome = new ItemStack(Main.itemFateTome, 1, 0);
        ItemStack lunarFlares = new ItemStack(Main.itemLunarFlares, 1, 0);
        ItemStack nuclearFury = new ItemStack(Main.itemMissileTome, 1, 0);
        ItemStack obeliskDrainer = new ItemStack(Main.itemObeliskDrainer, 1, 0);
        ItemStack theParadox = new ItemStack(Main.itemParadox, 1, 0);
        ItemStack shinyStone = new ItemStack(Main.itemShinyStone, 1, 0);
        ItemStack soulTome = new ItemStack(Main.itemSoulTome, 1, 0);
        ItemStack telekinesisTome = new ItemStack(Main.itemTelekinesisTome, 1, 0);
        ItemStack discordTome = new ItemStack(Main.itemTeleportationTome, 1, 0);
        ItemStack XPTome = new ItemStack(Main.itemXPTome, 1, 0);
        ItemStack oblivionAmulet = new ItemStack(Main.itemOblivionAmulet, 1, 0);
        ItemStack ghastlySkull = new ItemStack(Main.itemGhastlySkull, 1, 0);
        ItemStack omegaCore = new ItemStack(Main.itemOmegaCore, 1, 0);
        ItemStack dormantNebulousCore = new ItemStack(Main.itemDormantArcanum);

        ThaumcraftApi.registerObjectTag(
                weatherStone,
                new AspectList().add(Aspect.ENERGY, 12).add(Aspect.WEATHER, 10).add(Aspect.EXCHANGE, 8)
                        .add(Aspect.AIR, 7).add(Aspect.WATER, 7).add(Aspect.MAGIC, 5));
        ThaumcraftApi.registerObjectTag(
                miningCharm,
                new AspectList().add(Aspect.MINE, 15).add(Aspect.TOOL, 10).add(Aspect.MOTION, 8).add(Aspect.MAGIC, 8)
                        .add(Aspect.METAL, 6));
        ThaumcraftApi.registerObjectTag(
                advancedMiningCharm,
                new AspectList().add(Aspect.MINE, 32).add(Aspect.TOOL, 24).add(Aspect.CRYSTAL, 20).add(Aspect.AURA, 16)
                        .add(Aspect.MAGIC, 12));
        ThaumcraftApi.registerObjectTag(
                ancientAegis,
                new AspectList().add(Aspect.ARMOR, 25).add(Aspect.METAL, 22).add(Aspect.HEAL, 16)
                        .add(Aspect.EXCHANGE, 12).add(Aspect.MAGIC, 10).add(Aspect.ENERGY, 8));
        ThaumcraftApi.registerObjectTag(
                apotheosis,
                new AspectList().add(Aspect.WEAPON, 45).add(Aspect.LIGHT, 36).add(Aspect.ORDER, 30)
                        .add(Aspect.EXCHANGE, 24).add(Aspect.MIND, 24).add(Aspect.MAGIC, 22));
        ThaumcraftApi.registerObjectTag(
                nebulousCore,
                new AspectList().add(Aspect.MAGIC, 54).add(Aspect.AURA, 48).add(Aspect.VOID, 40)
                        .add(Aspect.EXCHANGE, 32).add(Aspect.DARKNESS, 30).add(Aspect.ELDRITCH, 25));
        ThaumcraftApi.registerObjectTag(
                chaosCore,
                new AspectList().add(Aspect.MAGIC, 17).add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 12)
                        .add(Aspect.EXCHANGE, 10).add(Aspect.VOID, 7).add(Aspect.DARKNESS, 5));
        ThaumcraftApi.registerObjectTag(
                chaosTome,
                new AspectList().add(Aspect.AIR, 36).add(Aspect.FIRE, 36).add(Aspect.WATER, 36).add(Aspect.EARTH, 36)
                        .add(Aspect.ORDER, 36).add(Aspect.ENTROPY, 36));
        ThaumcraftApi.registerObjectTag(
                crimsonSpell,
                new AspectList().add(Aspect.MAGIC, 26).add(Aspect.FIRE, 20).add(Aspect.ENTROPY, 19)
                        .add(Aspect.ELDRITCH, 16).add(Aspect.MIND, 14).add(Aspect.DARKNESS, 11));
        ThaumcraftApi.registerObjectTag(
                darkSunRing,
                new AspectList().add(Aspect.FIRE, 24).add(Aspect.DARKNESS, 20).add(Aspect.MAGIC, 15)
                        .add(Aspect.EXCHANGE, 12).add(Aspect.ARMOR, 9).add(Aspect.VOID, 5));
        ThaumcraftApi.registerObjectTag(
                deificAmulet,
                new AspectList().add(Aspect.SOUL, 30).add(Aspect.LIGHT, 23).add(Aspect.MAN, 19).add(Aspect.EXCHANGE, 16)
                        .add(Aspect.MAGIC, 12).add(Aspect.HEAL, 8));
        ThaumcraftApi.registerObjectTag(
                dimensionalMirror,
                new AspectList().add(Aspect.METAL, 19).add(Aspect.TRAVEL, 16).add(Aspect.DARKNESS, 12)
                        .add(Aspect.VOID, 12).add(Aspect.MAGIC, 10).add(Aspect.EXCHANGE, 8));
        ThaumcraftApi.registerObjectTag(
                eldritchSpell,
                new AspectList().add(Aspect.ELDRITCH, 27).add(Aspect.DARKNESS, 20).add(Aspect.VOID, 20)
                        .add(Aspect.WEAPON, 16).add(Aspect.MIND, 16).add(Aspect.MAGIC, 12));
        ThaumcraftApi.registerObjectTag(
                falseJustice,
                new AspectList().add(Aspect.LIGHT, 40).add(Aspect.TOOL, 32).add(Aspect.MAN, 24).add(Aspect.EXCHANGE, 24)
                        .add(Aspect.SOUL, 20).add(Aspect.MAGIC, 16));
        ThaumcraftApi.registerObjectTag(
                fateTome,
                new AspectList().add(Aspect.MAN, 24).add(Aspect.EXCHANGE, 20).add(Aspect.MIND, 20).add(Aspect.LIFE, 16)
                        .add(Aspect.HEAL, 16).add(Aspect.MAGIC, 12));
        ThaumcraftApi.registerObjectTag(
                lunarFlares,
                new AspectList().add(Aspect.AIR, 36).add(Aspect.ENERGY, 30).add(Aspect.LIGHT, 24).add(Aspect.WEAPON, 24)
                        .add(Aspect.MIND, 24).add(Aspect.MAGIC, 20));
        ThaumcraftApi.registerObjectTag(
                nuclearFury,
                new AspectList().add(Aspect.ENERGY, 32).add(Aspect.LIGHT, 28).add(Aspect.AURA, 20).add(Aspect.VOID, 16)
                        .add(Aspect.SENSES, 16).add(Aspect.MAGIC, 12));
        ThaumcraftApi.registerObjectTag(
                obeliskDrainer,
                new AspectList().add(Aspect.VOID, 40).add(Aspect.ELDRITCH, 32).add(Aspect.DARKNESS, 28)
                        .add(Aspect.MAGIC, 24).add(Aspect.MIND, 16).add(Aspect.ENERGY, 12));
        ThaumcraftApi.registerObjectTag(
                shinyStone,
                new AspectList().add(Aspect.HEAL, 20).add(Aspect.LIFE, 16).add(Aspect.CRYSTAL, 12)
                        .add(Aspect.EXCHANGE, 10).add(Aspect.MAGIC, 8).add(Aspect.TRAP, 4));
        ThaumcraftApi.registerObjectTag(
                soulTome,
                new AspectList().add(Aspect.SOUL, 30).add(Aspect.DEATH, 25).add(Aspect.VOID, 21)
                        .add(Aspect.DARKNESS, 16).add(Aspect.EXCHANGE, 16).add(Aspect.MAGIC, 12));
        ThaumcraftApi.registerObjectTag(
                telekinesisTome,
                new AspectList().add(Aspect.MAGIC, 23).add(Aspect.ENERGY, 20).add(Aspect.MOTION, 16)
                        .add(Aspect.TOOL, 15).add(Aspect.MIND, 12).add(Aspect.AIR, 7));
        ThaumcraftApi.registerObjectTag(
                discordTome,
                new AspectList().add(Aspect.TRAVEL, 25).add(Aspect.MAGIC, 20).add(Aspect.DARKNESS, 16)
                        .add(Aspect.VOID, 16).add(Aspect.MIND, 12).add(Aspect.ORDER, 7));
        ThaumcraftApi.registerObjectTag(
                XPTome,
                new AspectList().add(Aspect.MIND, 28).add(Aspect.MAGIC, 21).add(Aspect.SOUL, 16)
                        .add(Aspect.EXCHANGE, 12).add(Aspect.VOID, 8));
        ThaumcraftApi.registerObjectTag(
                oblivionAmulet,
                new AspectList().add(Aspect.DARKNESS, 52).add(Aspect.DEATH, 48).add(Aspect.VOID, 45)
                        .add(Aspect.EXCHANGE, 32).add(Aspect.MAGIC, 25).add(Aspect.ELDRITCH, 12));
        ThaumcraftApi
                .registerObjectTag(dormantNebulousCore, new AspectList().add(Aspect.VOID, 8).add(Aspect.DARKNESS, 8));
        ThaumcraftApi.registerObjectTag(
                ghastlySkull,
                new AspectList().add(Aspect.DEATH, 16).add(Aspect.SOUL, 14).add(Aspect.DARKNESS, 14)
                        .add(Aspect.ENTROPY, 10).add(Aspect.VOID, 8).add(Aspect.MAGIC, 8));
        ThaumcraftApi.registerObjectTag(
                omegaCore,
                new AspectList().add(Aspect.MAGIC, 32).add(Aspect.AURA, 32).add(Aspect.ENERGY, 32).add(Aspect.LIGHT, 32)
                        .add(Aspect.EXCHANGE, 32).add(Aspect.VOID, 32));

    }

}
