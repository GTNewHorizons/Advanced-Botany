package com.integral.forgottenrelics.handlers;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RelicsConfigHandler {

    public static float damageApotheosisDirect;
    public static float damageApotheosisImpact;
    public static float damageLunarFlareDirect;
    public static float damageLunarFlareImpact;
    public static float paradoxDamageCap;
    public static float telekinesisTomeDamageMIN;
    public static float telekinesisTomeDamageMAX;
    public static float nuclearFuryDamageMIN;
    public static float nuclearFuryDamageMAX;
    public static float crimsonSpellDamageMIN;
    public static float crimsonSpellDamageMAX;
    public static float weatherStoneVisMult;
    public static float chaosTomeDamageCap;
    public static float eldritchSpellDamage;
    public static float eldritchSpellDamageEx;

    public static float discordTomeVisMult;
    public static float telekinesisTomeVisMult;
    public static float chaosTomeVisMult;
    public static float eldritchSpellVisMult;
    public static float crimsonSpellVisMult;
    public static float soulTomeVisMult;
    public static float nuclearFuryVisMult;
    public static float lunarFlaresVisMult;
    public static float apotheosisVisMult;
    public static float fateTomeVisMult;
    public static float obeliskDrainerVisMult;
    public static float oblivionAmuletVisMult;
    public static float deificAmuletVisMult;
    public static float dormantArcanumVisMult;

    public static float arcanumGenRate;
    public static float soulTomeDivisor;

    public static boolean falseJusticeEnabled;

    // Brand new options! Hate implementing this stuff.

    public static int shinyStoneCheckrate;

    public static boolean deificAmuletInvincibility;
    public static boolean deificAmuletEffectImmunity;

    public static float darkSunRingDamageCap;
    public static float darkSunRingDeflectChance;
    public static boolean darkSunRingHealLimit;

    public static boolean interdimensionalMirror;

    public static float ancientAegisDamageReduction;
    public static float nebulousCoreDodgeChance;

    public static float miningCharmBoost;
    public static float miningCharmReach;

    public static float advancedMiningCharmBoost;
    public static float advancedMiningCharmReach;

    public void configDisposition(FMLPreInitializationEvent event) {

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        this.advancedMiningCharmReach = config.getFloat(
                "advancedMiningCharmReach",
                "Generic Config",
                4.0F,
                0.0F,
                32.0F,
                "Block reach increase for Ethereal Mining Charm.");

        this.miningCharmReach = config.getFloat(
                "miningCharmReach",
                "Generic Config",
                2.0F,
                0.0F,
                32.0F,
                "Block reach increase for Mining Charm.");

        this.advancedMiningCharmBoost = config.getFloat(
                "advancedMiningCharmBoost",
                "Generic Config",
                3.0F,
                0.0F,
                32000.0F,
                "Mining speed boost for Ethereal Mining Charm. 3.0 means that it is boosted by 300%.");

        this.miningCharmBoost = config.getFloat(
                "miningCharmBoost",
                "Generic Config",
                1.0F,
                0.0F,
                32000.0F,
                "Mining speed boost for Mining Charm. 1.0 means that it is boosted by 100%.");

        this.nebulousCoreDodgeChance = config.getFloat(
                "nebulousCoreDodgeChance",
                "Generic Config",
                0.4F,
                0.0F,
                1.0F,
                "Chance to dodge attack by teleporting player from it for Nebulous Core. 1.0 equals 100% chance, 0.0 - 0%.");

        this.ancientAegisDamageReduction = config.getFloat(
                "ancientAegisDamageReduction",
                "Generic Config",
                0.25F,
                0.0F,
                1.0F,
                "Damage Reduction for Ancient Aegis. 1.0 equals 100% reduction, 0.0 - 0%.");

        this.deificAmuletEffectImmunity = config.getBoolean(
                "deificAmuletEffectImmunity",
                "Generic Config",
                true,
                "Whether or not Deific Amulet should provide immunity to status effects. Note, that it includes buffs as well as debuffs.");

        this.deificAmuletInvincibility = config.getBoolean(
                "deificAmuletInvincibility",
                "Generic Config",
                true,
                "Whether or not Deific Amulet should provide prolonged invincibility frames.");

        this.darkSunRingDeflectChance = config.getFloat(
                "darkSunRingDeflectChance",
                "Generic Config",
                0.2F,
                0.0F,
                1.0F,
                "Chance to deflect enemy's attack back to it, while wearing Ring of The Seven Suns. 1.0 equals 100% chance, 0.0 - 0%.");

        this.darkSunRingDamageCap = config.getFloat(
                "darkSunRingDamageCap",
                "Generic Config",
                100.0F,
                0.0F,
                32768.0F,
                "Damage cap for Ring of The Seven Suns. Any attacks that exceed this amount of damage will be completely negated while wearing it.");

        this.darkSunRingHealLimit = config.getBoolean(
                "darkSunRingHealLimit",
                "Generic Config",
                false,
                "Enables the cooldown on Ring of The Seven Sun's healing effect, so standing in fire or lava wouldn't replenish your health insanely fast. WARNING: This config option is experimental, only touch it if you really want this.");

        this.interdimensionalMirror = config.getBoolean(
                "interdimensionalMirror",
                "Generic Config",
                true,
                "Whether or not Dimensional Mirror should be capable of teleporting player across dimensions. If this is set to false, player must reside in the dimension of saved location in order to teleport to it.");

        this.shinyStoneCheckrate = config.getInt(
                "shinyStoneCheckrate",
                "Generic Config",
                4,
                1,
                2048,
                "Checkrate for Shiny Stone effects. The greater it is, the less frequently health regen would happen, and the more time acceleration would take. WARNING: This config option is experimental, only touch it if you really want this.");

        this.obeliskDrainerVisMult = config.getFloat(
                "obeliskDrainerVisGen",
                "Generic Config",
                1.0F,
                0F,
                32000.0F,
                "Vis production multiplier for Devourer of The Void.");

        this.arcanumGenRate = config.getFloat(
                "arcanumGenRate",
                "Generic Config",
                1.0F,
                0F,
                32000.0F,
                "Multiplier for Vis generation rate of Nebulous Core.");

        this.soulTomeDivisor = config.getFloat(
                "soulTomeDivisor",
                "Generic Config",
                10.0F,
                0F,
                Float.POSITIVE_INFINITY,
                "Divisor, used during damage calculations by Edict of a Thousand Damned Souls. Setting this value to 10 basically means that most of the time it will drain 1/10 of entity's max health per attack.");

        this.falseJusticeEnabled = config.getBoolean(
                "falseJusticeEnabled",
                "Generic Config",
                true,
                "Whether or not False Justice should be enabled. Note that it will only remove respective research, so it would be impossible to create this relic legally - it won't remove existing copies from world or prevent it's spawning from Creative Mode.");

        this.damageApotheosisDirect = config.getFloat(
                "damageApotheosisDirect",
                "Damage Values",
                100.0F,
                0F,
                32000.0F,
                "How much damage inflicts direct hit of Babylon Weapons, summoned by Apotheosis.");

        this.damageApotheosisImpact = config.getFloat(
                "damageApotheosisImpact",
                "Damage Values",
                75.0F,
                0F,
                32000.0F,
                "How much damage receive entities within impact zone of Babylon Weapons, summoned by Apotheosis.");

        this.damageLunarFlareDirect = config.getFloat(
                "damageLunarFlareDirect",
                "Damage Values",
                72.0F,
                0F,
                32000.0F,
                "How much damage inflicts direct hit of Lunar Flare.");

        this.damageLunarFlareImpact = config.getFloat(
                "damageLunarFlareImpact",
                "Damage Values",
                40.0F,
                0F,
                32000.0F,
                "How much damage receive entities within impact zone of Lunar Flare.");

        this.paradoxDamageCap = config.getFloat(
                "paradoxDamageCap",
                "Damage Values",
                200.0F,
                0F,
                32000.0F,
                "Maximum damage The Paradox can deal. Damage dealt varies between 1 and this value for each hit.");

        this.telekinesisTomeDamageMIN = config.getFloat(
                "telekinesisTomeDamageMIN",
                "Damage Values",
                16.0F,
                0F,
                32000.0F,
                "Minimal damage that can be dealt by lightning attack of Tome of Predestiny.");

        this.telekinesisTomeDamageMAX = config.getFloat(
                "telekinesisTomeDamageMAX",
                "Damage Values",
                40.0F,
                0F,
                32000.0F,
                "Maximal damage that can be dealt by lightning attack of Tome of Predestiny.");

        this.nuclearFuryDamageMIN = config.getFloat(
                "nuclearFuryDamageMIN",
                "Damage Values",
                24.0F,
                0F,
                32000.0F,
                "Minimal damage that can be dealt by charges of Nuclear Fury.");

        this.nuclearFuryDamageMAX = config.getFloat(
                "nuclearFuryDamageMAX",
                "Damage Values",
                32.0F,
                0F,
                32000.0F,
                "Maximal damage that can be dealt by charges of Nuclear Fury.");

        this.crimsonSpellDamageMIN = config.getFloat(
                "crimsonSpellDamageMIN",
                "Damage Values",
                42.0F,
                0F,
                32000.0F,
                "Minimal damage that can be dealt by projectiles of Crimson Spell.");

        this.crimsonSpellDamageMAX = config.getFloat(
                "crimsonSpellDamageMAX",
                "Damage Values",
                100.0F,
                0F,
                32000.0F,
                "Maximal damage that can be dealt by projectiles of Crimson Spell.");

        this.chaosTomeDamageCap = config.getFloat(
                "chaosTomeDamageCap",
                "Damage Values",
                100.0F,
                0F,
                32000.0F,
                "Maximum damage that projectile of Tome of Primal Chaos can deal. Damage dealt varies between 1 and this value for each hit.");

        this.eldritchSpellDamage = config.getFloat(
                "eldritchSpellDamage",
                "Damage Values",
                32.5F,
                0F,
                32000.0F,
                "Default damage dealt by projectiles of Eldritch Spell.");

        this.eldritchSpellDamageEx = config.getFloat(
                "eldritchSpellDamageEx",
                "Damage Values",
                100.0F,
                0F,
                32000.0F,
                "Damage dealt by projectiles of Eldritch Spell, while it is used in Outer Lands.");

        this.apotheosisVisMult = config
                .getFloat("apotheosisVisMult", "Vis Costs", 1.0F, 0F, 1024.0F, "Vis cost multiplier for Apotheosis.");

        this.chaosTomeVisMult = config.getFloat(
                "chaosTomeVisMult",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Tome of Primal Chaos.");

        this.crimsonSpellVisMult = config.getFloat(
                "crimsonSpellVisMult",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Crimson Spell.");

        this.deificAmuletVisMult = config.getFloat(
                "deificAmuletVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Deific Amulet.");

        this.discordTomeVisMult = config.getFloat(
                "discordTomeVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Tome of Discord.");

        this.dormantArcanumVisMult = config.getFloat(
                "dormantArcanumVisMult",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Dormant Nebulous Core (applies in the moment of transormation; final amount of Vis required for re-awakening depends on this.)");

        this.eldritchSpellVisMult = config.getFloat(
                "eldritchSpellVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Eldritch Spell.");

        this.fateTomeVisMult = config.getFloat(
                "fateTomeVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Tome of Broken Fates.");

        this.lunarFlaresVisMult = config.getFloat(
                "lunarFlaresVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Tome of Lunar Flares.");

        this.nuclearFuryVisMult = config.getFloat(
                "nuclearFuryVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Nuclear Fury.");

        this.oblivionAmuletVisMult = config.getFloat(
                "oblivionAmuletVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Amulet of The Oblivion.");

        this.soulTomeVisMult = config.getFloat(
                "soulTomeVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Edict of a Thousand Damned Souls.");

        this.telekinesisTomeVisMult = config.getFloat(
                "telekinesisTomeVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Tome of Predestiny.");

        this.weatherStoneVisMult = config.getFloat(
                "weatherStoneVisCost",
                "Vis Costs",
                1.0F,
                0F,
                1024.0F,
                "Vis cost multiplier for Runic Stone.");

        config.save();
    }
}
