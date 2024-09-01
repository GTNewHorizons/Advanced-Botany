package ab.common.item.equipment;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import ab.AdvancedBotany;
import ab.common.lib.register.BlockListAB;
import cpw.mods.fml.common.eventhandler.Event.Result;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

public class ItemTerraHoe extends ItemHoe implements IManaUsingItem {

    public ItemTerraHoe() {
        super(BotaniaAPI.terrasteelToolMaterial);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setUnlocalizedName("terraHoe");
        this.setTextureName("ab:terraHoe");
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer p, World world, int x, int y, int z, int p_77648_7_,
            float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (!p.canPlayerEdit(x, y, z, p_77648_7_, stack)) {
            return false;
        } else {
            UseHoeEvent event = new UseHoeEvent(p, stack, world, x, y, z);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            }
            if (event.getResult() == Result.ALLOW) {
                stack.damageItem(1, p);
                return true;
            }
            Block block = world.getBlock(x, y, z);
            if (p_77648_7_ != 0 && world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z)
                    && (block == Blocks.grass || block == Blocks.dirt)) {
                Block block1 = BlockListAB.blockTerraFarmland;
                world.playSoundEffect(
                        (double) ((float) x + 0.5F),
                        (double) ((float) y + 0.5F),
                        (double) ((float) z + 0.5F),
                        block1.stepSound.getStepResourcePath(),
                        (block1.stepSound.getVolume() + 1.0F) / 2.0F,
                        block1.stepSound.getPitch() * 0.8F);
                if (world.isRemote) {
                    float velMul = 0.025F;
                    for (int i = 0; i < 48; i++) {
                        double px = (Math.random() - 0.5D) * 3.0D;
                        double py = Math.random() - 0.5D + 1.0D;
                        double pz = (Math.random() - 0.5D) * 3.0D;
                        Botania.proxy.wispFX(
                                world,
                                x + 0.5D + px,
                                y + 0.5D + py,
                                z + 0.5D + pz,
                                0.0f,
                                0.4f,
                                0.0f,
                                (float) Math.random() * 0.15F + 0.15F,
                                (float) -px * velMul,
                                (float) -py * velMul,
                                (float) -pz * velMul);
                    }
                    return true;
                } else {
                    world.setBlock(x, y, z, block1);
                    stack.damageItem(1, p);
                    return true;
                }
            } else if (p.isSneaking() && p_77648_7_ != 0
                    && world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z)
                    && (block == ModBlocks.enchantedSoil)) {
                        if (world.isRemote) return true;
                        world.setBlock(x, y, z, Blocks.dirt);
                        stack.damageItem(1, p);
                        EntityItem entity = new EntityItem(
                                world,
                                x + 0.5f,
                                y + 1,
                                z + 0.5f,
                                new ItemStack(ModItems.overgrowthSeed));
                        world.spawnEntityInWorld(entity);
                        return true;
                    } else {
                        return false;
                    }
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if (!world.isRemote && player instanceof EntityPlayer
                && stack.getItemDamage() > 0
                && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, 1760, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
    }

    public boolean usesMana(ItemStack stack) {
        return true;
    }
}
