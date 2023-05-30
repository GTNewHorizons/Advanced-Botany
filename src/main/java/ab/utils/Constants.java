package ab.utils;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class Constants {

    private Constants() {}

    public static final int POOL_META_DILUTE = 2;
    public static final int POOL_META_REGULAR = 0;
    public static final int POOL_META_REGULAR_FABULOUS = 3;
    public static final int POOL_META_CREATIVE = 1;

    public static final int SPREADER_META_REGULAR = 0;
    public static final int SPREADER_META_ALFHEIM = 2;
    public static final int SPREADER_META_GAIA = 3;
    public static final int SPREADER_META_PULSE = 1;

    public static final int MANARESOURCE_META_MANASTEEL = 0;
    public static final int MANARESOURCE_META_DIAMOND = 2;
    public static final int MANARESOURCE_META_PEARL = 1;
    public static final int MANARESOURCE_META_TWIG_WOOD = 3;
    public static final int MANARESOURCE_META_TERRASTEEL = 4;
    public static final int MANARESOURCE_META_GAIA_SPIRIT = 5;
    public static final int MANARESOURCE_META_ROOT = 6;
    public static final int MANARESOURCE_META_ELEMENTIUM = 7;
    public static final int MANARESOURCE_META_PIXIE_DUST = 8;
    public static final int MANARESOURCE_META_DRAGONSTONE = 9;
    public static final int MANARESOURCE_META_PRISMARINE = 10;
    public static final int MANARESOURCE_META_CRAFT = 11;
    public static final int MANARESOURCE_META_TWIG_DREAM = 13;
    public static final int MANARESOURCE_META_GAIA_INGOT = 14;
    public static final int MANARESOURCE_META_STRING = 16;
    public static final int MANARESOURCE_META_CLOTH = 22;
    public static final int MANARESOURCE_META_MANAPOWDER = 23;

    public static final int STORAGE_META_MANASTEELBLOCK = 0;
    public static final int STORAGE_META_TERRASTEELBLOCK = 1;
    public static final int STORAGE_META_ELEMENTIUMBLOCK = 2;
    public static final int STORAGE_META_DIAMONDBLOCK = 3;
    public static final int STORAGE_META_DRAGONSTONEBLOCK = 4;

    public static final int STORAGE_META_MITHRILL = 0;

    public static final int SEEDS_META_GRASS = 0;
    public static final int SEEDS_META_PODZOL = 1;
    public static final int SEEDS_META_MYCELIUM = 2;
    public static final int SEEDS_META_DRY = 3;
    public static final int SEEDS_META_GOLD = 4;
    public static final int SEEDS_META_VIVID = 5;
    public static final int SEEDS_META_SCORCHED = 6;
    public static final int SEEDS_META_INFUSED = 7;
    public static final int SEEDS_META_MUTATED = 8;

    public static final int LIVINGWOOD_META_BLOCK = 0;
    public static final int LIVINGWOOD_META_PLANK = 1;
    public static final int LIVINGWOOD_META_MOSSY = 2;
    public static final int LIVINGWOOD_META_FRAMED = 3;
    public static final int LIVINGWOOD_META_PATTERNED = 4;
    public static final int LIVINGWOOD_META_GLIMMERING = 5;

    public static final int LIVINGSTONE_META_BLOCK = 0;
    public static final int LIVINGSTONE_META_BRICK = 1;
    public static final int LIVINGSTONE_META_MOSSY = 2;
    public static final int LIVINGSTONE_META_CRACKED = 3;
    public static final int LIVINGSTONE_META_CHISELED = 4;

    public static final int PYLON_META_MANA = 0;
    public static final int PYLON_META_NATURA = 1;
    public static final int PYLON_META_GAIA = 2;

    public static final int VIAL_META_MANAGLASS = 0;
    public static final int VIAL_META_ALFGLASS = 1;

    public static final int QUARTZ_META_DARK = 0;
    public static final int QUARTZ_META_MANA = 1;
    public static final int QUARTZ_META_BLAZE = 2;
    public static final int QUARTZ_META_LAVENDER = 3;
    public static final int QUARTZ_META_RED = 4;
    public static final int QUARTZ_META_ELVEN = 5;
    public static final int QUARTZ_META_SUNNY = 6;

    public static final int VIRUS_METADATA_NECRO = 0;
    public static final int VIRUS_METADATA_NULL = 1;

    public static final int PRISMARINE_META_BLOCK = 0;
    public static final int PRISMARINE_META_BRICK = 1;
    public static final int PRISMARINE_META_DARK = 2;

    public static final int BRICK_META_HELL = 0;
    public static final int BRICK_META_SOUL = 1;
    public static final int BRICK_META_FROST = 2;
    public static final int BRICK_META_ROOF = 3;

    public static final int ENDSTONE_META_BRICK_YELLOW = 0;
    public static final int ENDSTONE_META_BRICK_PURPLE = 2;

    public static final String THAUMCRAFT_METAL_DEVICE = "Thaumcraft:blockMetalDevice";
    public static final String THAUMCRAFT_STONE_DEVICE = "Thaumcraft:blockStoneDevice";
    public static final int THAUMCRAFT_METAL_META_CRUCIBLE = 0;
    public static final int THAUMCRAFT_METAL_META_CONSTRUCT = 9;
    public static final int THAUMCRAFT_STONE_META_PEDESTAL = 1;
    public static final int THAUMCRAFT_STONE_META_RUNIC_MATRIX = 2;

    public static final String GT_CASING_ID = "gregtech:gt.blockcasings4";
    public static final int GT_CASING_META = 1;

    public static final int POOL_MAX_MANA_DILUTED = 10000;
    public static final int POOL_MAX_MANA_REGULAR = 1000000;

    public static final int MANA_TABLET_MAGIC_META = 10000;

    public static ItemStack thaumcraftCrucible() {
        return new ItemStack(Block.getBlockFromName(THAUMCRAFT_METAL_DEVICE), 1, THAUMCRAFT_METAL_META_CRUCIBLE);
    }

    public static ItemStack thaumcraftMatrix() {
        return new ItemStack(Block.getBlockFromName(THAUMCRAFT_STONE_DEVICE), 1, THAUMCRAFT_STONE_META_RUNIC_MATRIX);
    }

    public static ItemStack thaumcraftConstruct() {
        return new ItemStack(Block.getBlockFromName(THAUMCRAFT_METAL_DEVICE), 1, THAUMCRAFT_METAL_META_CONSTRUCT);
    }

    public static ItemStack gtTradeCasing() {
        return new ItemStack(Block.getBlockFromName(GT_CASING_ID), 1, GT_CASING_META);
    }
}
