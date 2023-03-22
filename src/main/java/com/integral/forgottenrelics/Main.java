package com.integral.forgottenrelics;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;

import com.integral.forgottenrelics.entities.EntityBabylonWeaponSS;
import com.integral.forgottenrelics.entities.EntityChaoticOrb;
import com.integral.forgottenrelics.entities.EntityCrimsonOrb;
import com.integral.forgottenrelics.entities.EntityDarkMatterOrb;
import com.integral.forgottenrelics.entities.EntityLunarFlare;
import com.integral.forgottenrelics.entities.EntityRageousMissile;
import com.integral.forgottenrelics.entities.EntityShinyEnergy;
import com.integral.forgottenrelics.entities.EntitySoulEnergy;
import com.integral.forgottenrelics.handlers.ApotheosisParticleMessage;
import com.integral.forgottenrelics.handlers.ArcLightningMessage;
import com.integral.forgottenrelics.handlers.BurstMessage;
import com.integral.forgottenrelics.handlers.LightningMessage;
import com.integral.forgottenrelics.handlers.LunarBurstMessage;
import com.integral.forgottenrelics.handlers.LunarFlaresParticleMessage;
import com.integral.forgottenrelics.handlers.PortalTraceMessage;
import com.integral.forgottenrelics.handlers.RelicsConfigHandler;
import com.integral.forgottenrelics.handlers.RelicsEventHandler;
import com.integral.forgottenrelics.handlers.RelicsMaterialHandler;
import com.integral.forgottenrelics.items.ItemAdvancedMiningCharm;
import com.integral.forgottenrelics.items.ItemAncientAegis;
import com.integral.forgottenrelics.items.ItemApotheosis;
import com.integral.forgottenrelics.items.ItemArcanum;
import com.integral.forgottenrelics.items.ItemChaosCore;
import com.integral.forgottenrelics.items.ItemChaosTome;
import com.integral.forgottenrelics.items.ItemCrimsonSpell;
import com.integral.forgottenrelics.items.ItemDarkSunRing;
import com.integral.forgottenrelics.items.ItemDeificAmulet;
import com.integral.forgottenrelics.items.ItemDimensionalMirror;
import com.integral.forgottenrelics.items.ItemDormantArcanum;
import com.integral.forgottenrelics.items.ItemEldritchSpell;
import com.integral.forgottenrelics.items.ItemFalseJustice;
import com.integral.forgottenrelics.items.ItemFateTome;
import com.integral.forgottenrelics.items.ItemGhastlySkull;
import com.integral.forgottenrelics.items.ItemLunarFlares;
import com.integral.forgottenrelics.items.ItemMiningCharm;
import com.integral.forgottenrelics.items.ItemMissileTome;
import com.integral.forgottenrelics.items.ItemObeliskDrainer;
import com.integral.forgottenrelics.items.ItemOblivionAmulet;
import com.integral.forgottenrelics.items.ItemOmegaCore;
import com.integral.forgottenrelics.items.ItemParadox;
import com.integral.forgottenrelics.items.ItemShinyStone;
import com.integral.forgottenrelics.items.ItemSoulTome;
import com.integral.forgottenrelics.items.ItemSuperpositionRing;
import com.integral.forgottenrelics.items.ItemTelekinesisTome;
import com.integral.forgottenrelics.items.ItemTeleportationTome;
import com.integral.forgottenrelics.items.ItemWeatherStone;
import com.integral.forgottenrelics.items.ItemXPTome;
import com.integral.forgottenrelics.proxy.CommonProxy;
import com.integral.forgottenrelics.research.RelicsAspectRegistry;
import com.integral.forgottenrelics.research.RelicsResearchRegistry;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(
        modid = Main.MODID,
        version = Main.VERSION,
        name = Main.NAME,
        dependencies = "required-after:Thaumcraft@[4.2.3.5,);required-after:Botania@[r1.8-237,)")
public class Main {

    public static final String MODID = "ForgottenRelics";
    public static final String VERSION = "1.1.0";
    public static final String NAME = "Forgotten Relics";

    public static SimpleNetworkWrapper packetInstance;

    @SidedProxy(
            clientSide = "com.integral.forgottenrelics.proxy.ClientProxy",
            serverSide = "com.integral.forgottenrelics.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Instance(MODID)
    public static Main instance;
    public static List<String> darkRingDamageNegations = new ArrayList();

    public static Item itemFalseJustice;
    public static Item itemDeificAmulet;
    public static Item itemParadox;
    public static Item itemOblivionAmulet;
    public static Item itemArcanum;
    public static Item itemDormantArcanum;
    public static Item itemFateTome;
    public static Item itemDarkSunRing;
    public static Item itemChaosCore;
    public static Item itemMiningCharm;
    public static Item itemAdvancedMiningCharm;
    public static Item itemTelekinesisTome;
    public static Item itemAncientAegis;
    public static Item itemMissileTome;
    public static Item itemCrimsonSpell;
    public static Item itemGhastlySkull;
    public static Item itemObeliskDrainer;

    public static Item itemEldritchSpell;
    public static Item itemLunarFlares;
    public static Item itemTeleportationTome;
    public static Item itemShinyStone;
    public static Item itemSuperpositionRing;
    public static Item itemSoulTome;

    public static Item itemDimensionalMirror;
    public static Item itemWeatherStone;
    public static Item itemApotheosis;
    public static Item itemChaosTome;
    public static Item itemOmegaCore;
    public static Item itemXPTome;

    public RelicsConfigHandler configHandler = new RelicsConfigHandler();

    public static final int howCoolAmI = Integer.MAX_VALUE;

    @EventHandler
    public void load(FMLInitializationEvent event) {
        Main.proxy.registerDisplayInformation();

        darkRingDamageNegations.add(DamageSource.lava.damageType);
        darkRingDamageNegations.add(DamageSource.inFire.damageType);
        darkRingDamageNegations.add(DamageSource.onFire.damageType);

    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configHandler.configDisposition(event);

        // TODO Throw all this message stuff into induvidual package

        packetInstance = NetworkRegistry.INSTANCE.newSimpleChannel("RelicsChannel");
        packetInstance.registerMessage(PortalTraceMessage.Handler.class, PortalTraceMessage.class, 1, Side.CLIENT);
        packetInstance.registerMessage(BurstMessage.Handler.class, BurstMessage.class, 2, Side.CLIENT);
        packetInstance.registerMessage(
                ApotheosisParticleMessage.Handler.class,
                ApotheosisParticleMessage.class,
                3,
                Side.CLIENT);
        packetInstance.registerMessage(LunarBurstMessage.Handler.class, LunarBurstMessage.class, 4, Side.CLIENT);
        packetInstance.registerMessage(
                LunarFlaresParticleMessage.Handler.class,
                LunarFlaresParticleMessage.class,
                5,
                Side.CLIENT);
        packetInstance.registerMessage(LightningMessage.Handler.class, LightningMessage.class, 6, Side.CLIENT);
        packetInstance.registerMessage(ArcLightningMessage.Handler.class, ArcLightningMessage.class, 7, Side.CLIENT);

        RelicsAspectRegistry.registerItemAspectsFirst();

        itemFalseJustice = new ItemFalseJustice();
        itemDeificAmulet = new ItemDeificAmulet();
        itemParadox = new ItemParadox(RelicsMaterialHandler.materialParadoxicalStuff);
        itemOblivionAmulet = new ItemOblivionAmulet();
        itemArcanum = new ItemArcanum();
        itemDormantArcanum = new ItemDormantArcanum();
        itemFateTome = new ItemFateTome();
        itemDarkSunRing = new ItemDarkSunRing();
        itemChaosCore = new ItemChaosCore();
        itemMiningCharm = new ItemMiningCharm();
        itemAdvancedMiningCharm = new ItemAdvancedMiningCharm();
        itemTelekinesisTome = new ItemTelekinesisTome();
        itemAncientAegis = new ItemAncientAegis();
        itemMissileTome = new ItemMissileTome();
        itemCrimsonSpell = new ItemCrimsonSpell();
        itemGhastlySkull = new ItemGhastlySkull();
        itemObeliskDrainer = new ItemObeliskDrainer();
        itemEldritchSpell = new ItemEldritchSpell();
        itemLunarFlares = new ItemLunarFlares();
        itemTeleportationTome = new ItemTeleportationTome();
        itemShinyStone = new ItemShinyStone();
        itemSuperpositionRing = new ItemSuperpositionRing();
        itemSoulTome = new ItemSoulTome();
        itemDimensionalMirror = new ItemDimensionalMirror();
        itemWeatherStone = new ItemWeatherStone();
        itemApotheosis = new ItemApotheosis();
        itemChaosTome = new ItemChaosTome();
        itemOmegaCore = new ItemOmegaCore();
        itemXPTome = new ItemXPTome();

        GameRegistry.registerItem(itemFalseJustice, "ItemFalseJustice");
        GameRegistry.registerItem(itemDeificAmulet, "ItemDeificAmulet");
        GameRegistry.registerItem(itemParadox, "ItemParadox");
        GameRegistry.registerItem(itemOblivionAmulet, "ItemOblivionAmulet");
        GameRegistry.registerItem(itemArcanum, "ItemArcanum");
        GameRegistry.registerItem(itemDormantArcanum, "ItemDormantArcanum");
        GameRegistry.registerItem(itemFateTome, "ItemFateTome");
        GameRegistry.registerItem(itemDarkSunRing, "ItemDarkSunRing");
        GameRegistry.registerItem(itemChaosCore, "ItemChaosCore");
        GameRegistry.registerItem(itemMiningCharm, "ItemMiningCharm");
        GameRegistry.registerItem(itemAdvancedMiningCharm, "ItemAdvancedMiningCharm");
        GameRegistry.registerItem(itemTelekinesisTome, "ItemTelekinesisTome");
        GameRegistry.registerItem(itemAncientAegis, "ItemAncientAegis");
        GameRegistry.registerItem(itemMissileTome, "ItemMissileTome");
        GameRegistry.registerItem(itemCrimsonSpell, "ItemCrimsonSpell");
        GameRegistry.registerItem(itemGhastlySkull, "itemGhastlySkull");
        GameRegistry.registerItem(itemObeliskDrainer, "ItemObeliskDrainer");
        GameRegistry.registerItem(itemEldritchSpell, "ItemEldritchSpell");
        GameRegistry.registerItem(itemLunarFlares, "ItemLunarFlares");
        GameRegistry.registerItem(itemTeleportationTome, "ItemTeleportationTome");
        GameRegistry.registerItem(itemShinyStone, "ItemShinyStone");
        GameRegistry.registerItem(itemSuperpositionRing, "ItemSuperpositionRing");
        GameRegistry.registerItem(itemSoulTome, "ItemSoulTome");
        GameRegistry.registerItem(itemDimensionalMirror, "ItemDimensionalMirror");
        GameRegistry.registerItem(itemWeatherStone, "ItemWeatherStone");
        GameRegistry.registerItem(itemApotheosis, "ItemApotheosis");
        GameRegistry.registerItem(itemChaosTome, "ItemChaosTome");
        GameRegistry.registerItem(itemOmegaCore, "ItemOmegaCore");
        GameRegistry.registerItem(itemXPTome, "ItemXPTome");

        EntityRegistry.registerModEntity(
                EntityRageousMissile.class,
                "SomeVeryRageousStuff",
                237,
                Main.instance,
                64,
                20,
                true);
        EntityRegistry.registerModEntity(EntityCrimsonOrb.class, "EntityCrimsonOrb", 238, Main.instance, 64, 20, true);
        EntityRegistry
                .registerModEntity(EntityDarkMatterOrb.class, "EntityDarkMatterOrb", 239, Main.instance, 64, 20, true);
        EntityRegistry.registerModEntity(EntitySoulEnergy.class, "EntitySoulEnergy", 240, Main.instance, 64, 20, true);
        EntityRegistry.registerModEntity(EntityLunarFlare.class, "EntityLunarFlare", 241, Main.instance, 196, 20, true);
        EntityRegistry
                .registerModEntity(EntityShinyEnergy.class, "EntityShinyEnergy", 242, Main.instance, 64, 20, true);
        EntityRegistry.registerModEntity(
                EntityBabylonWeaponSS.class,
                "EntityBabylonWeaponSS",
                243,
                Main.instance,
                64,
                20,
                true);
        EntityRegistry.registerModEntity(EntityChaoticOrb.class, "EntityChaoticOrb", 245, Main.instance, 64, 20, true);

        MinecraftForge.EVENT_BUS.register(new RelicsEventHandler());
        proxy.registerRenderers(this);

    }

    @EventHandler
    public static void postLoad(FMLPostInitializationEvent event) {
        RelicsResearchRegistry.integrateInfusion();
        RelicsAspectRegistry.registerItemAspectsLast();
        RelicsResearchRegistry.integrateResearch();
    }

    public static CreativeTabs tabForgottenRelics = new CreativeTabs("tabForgottenRelics") {

        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return itemApotheosis;
        }
    };

}
