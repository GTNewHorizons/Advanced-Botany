package ab.common.item.equipment;

import java.awt.Color;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import ab.AdvancedBotany;
import ab.api.AdvancedBotanyAPI;
import ab.api.IRankItem;
import ab.common.core.handler.*;
import ab.common.entity.EntitySword;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemSpaceBlade extends ItemSword implements IRankItem, IManaUsingItem {

    private static final IIcon[] icons = new IIcon[3];
    private static final int recharge = 36;

    public static final int[] LEVELS = new int[] { 0, 10000, 1000000, 10000000, 100000000, 1000000000 };
    private static final int[] CREATIVE_MANA = new int[] { 9999, 999999, 9999999, 99999999, 999999999, 2147483646 };

    public ItemSpaceBlade() {
        super(AdvancedBotanyAPI.mithrilToolMaterial);
        this.setCreativeTab(AdvancedBotany.tabAB);
        this.setUnlocalizedName("spaceBlade");
    }

    public boolean isItemTool(ItemStack par1ItemStack) {
        return true;
    }

    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int mana : CREATIVE_MANA) {
            ItemStack stack = new ItemStack(item);
            setMana(stack, mana);
            list.add(stack);
        }
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!player.worldObj.isRemote) {
            ItemNBTHelper.setInt(stack, "postAttackTick", 3);
            if (this.getLevel(stack) >= 3 && this.isEnabledMode(stack)) {
                float size = this.getLevel(stack) >= 4 ? (this.getLevel(stack) >= 5 ? 3.5f : 2.5f) : 1.5f;
                AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(
                        entity.posX,
                        entity.posY,
                        entity.posZ,
                        entity.lastTickPosX,
                        entity.lastTickPosY,
                        entity.lastTickPosZ).expand(size, 1.7f, size);
                List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
                for (EntityLivingBase living : entities) {
                    if (living instanceof EntityPlayer && (((EntityPlayer) living).getCommandSenderName()
                            .equals(player.getCommandSenderName())
                            || (MinecraftServer.getServer() != null && !MinecraftServer.getServer().isPVPEnabled())))
                        continue;
                    if (living.hurtTime == 0) {
                        float damage = getSwordDamage(stack);
                        living.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                    }
                }
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity;
        int tick = ItemNBTHelper.getInt(stack, "tick", 0);
        if (!world.isRemote) {
            int postAttackTick = ItemNBTHelper.getInt(stack, "postAttackTick", 0);
            if (postAttackTick > 0 && !player.isUsingItem())
                ItemNBTHelper.setInt(stack, "postAttackTick", postAttackTick - 1);
            if (tick > 0) ItemNBTHelper.setInt(stack, "tick", tick - 1);
            PotionEffect haste = player.getActivePotionEffect(Potion.digSpeed);
            float check = (haste == null) ? 0.16666667F : ((haste.getAmplifier() == 1) ? 0.5F : 0.4F);
            if (player.getCurrentEquippedItem() == stack && player.swingProgress == check
                    && this.getLevel(stack) >= 1
                    && postAttackTick == 0
                    && ManaItemHandler.requestManaExactForTool(stack, player, 120, true)) {
                EntitySword sword = new EntitySword(world, player);
                sword.setDamage(getSwordDamage(stack));
                sword.setAttacker(player.getCommandSenderName());
                sword.motionX *= 0.2f;
                sword.motionY *= 0.2f;
                sword.motionZ *= 0.2f;
                world.spawnEntityInWorld(sword);
                player.worldObj.playSoundAtEntity(player, "ab:bladeSpace", 0.5F, 3.6F);
            }
        } else {
            if (tick > 26 && par5) {
                for (int i = 0; i < 14; i++) {
                    float r = world.rand.nextBoolean() ? (225.0f / 255.0f) : (101.0f / 255.0f);
                    float g = world.rand.nextBoolean() ? (67.0f / 255.0f) : (209.0f / 255.0f);
                    float b = world.rand.nextBoolean() ? (240.0f / 255.0f) : (225.0f / 255.0f);
                    Botania.proxy.sparkleFX(
                            world,
                            entity.posX + (Math.random() - 0.5D),
                            entity.posY + ((Math.random() - 0.5D) * 2) - 0.5f,
                            entity.posZ + (Math.random() - 0.5D),
                            r + (float) (Math.random() / 4 - 0.125D),
                            g + (float) (Math.random() / 4 - 0.125D),
                            b + (float) (Math.random() / 4 - 0.125D),
                            1.8F * (float) (Math.random() - 0.5D),
                            3);
                }
            }
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (ItemNBTHelper.getInt(stack, "tick", 0) != 0) return stack;
        return super.onItemRightClick(stack, world, player);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int lastTime) {
        int useTime = getMaxItemUseDuration(stack) - lastTime;
        if (useTime < 4) {
            if (!world.isRemote && !player.isSneaking()
                    && ItemNBTHelper.getInt(stack, "tick", 0) == 0
                    && this.getLevel(stack) >= 2) {
                NetworkHandler.sendPacketToSpaceDash((EntityPlayerMP) player);
                onPlayerSpaceDash(player);
                ItemNBTHelper.setInt(stack, "tick", recharge);
                player.worldObj.playSoundAtEntity(player, "ab:bladeSpace", 2.3F, 1.2F);
                return;
            }
            if (player.isSneaking() && this.getLevel(stack) >= 3) {
                ItemNBTHelper.setBoolean(stack, "isEnabledMode", !this.isEnabledMode(stack));
                return;
            }
        }
    }

    public static void onPlayerSpaceDash(EntityPlayer player) {
        Vec3 vec3 = player.getLook(1.0F).normalize();
        player.motionX += vec3.xCoord * 3.25f;
        player.motionY += (vec3.yCoord / 1.6f);
        player.motionZ += vec3.zCoord * 3.25f;
    }

    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        String rankFormat = StatCollector.translateToLocal("botaniamisc.toolRank");
        String rank = StatCollector.translateToLocal("botania.rank" + getLevel(stack));
        list.add(String.format(rankFormat, new Object[] { rank }).replaceAll("&", "\u00A7"));
        if (getMana(stack) == Integer.MAX_VALUE)
            list.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("abmisc.swordFull"));
        if (GuiScreen.isShiftKeyDown()) {
            int level = (getLevel(stack));
            list.add(
                    (level >= 1 ? EnumChatFormatting.GREEN : "")
                            + StatCollector.translateToLocal("abmisc.swordInfo.1"));
            list.add(
                    (level >= 2 ? EnumChatFormatting.GREEN : "")
                            + StatCollector.translateToLocal("abmisc.swordInfo.2"));
            list.add(
                    (level >= 3 ? EnumChatFormatting.GREEN : "") + StatCollector.translateToLocal(
                            ("abmisc.swordInfo.LEVEL").replaceAll("LEVEL", "" + (level >= 3 ? level : 3))));
        } else {
            if (this.getLevel(stack) != 0)
                list.add(StatCollector.translateToLocal("botaniamisc.shiftinfo").replaceAll("&", "\u00A7"));
        }
    }

    public EnumRarity getRarity(ItemStack stack) {
        return AdvancedBotanyAPI.rarityNebula;
    }

    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int par2) {
        return (par2 == 1) ? Color.HSBtoRGB(0.836F, 1.0f - (float) getDurabilityForDisplay(stack), 1.0F) : 16777215;
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean showDurabilityBar(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "tick", 0) != 0;
    }

    @SideOnly(Side.CLIENT)
    public double getDurabilityForDisplay(ItemStack stack) {
        int tick = ItemNBTHelper.getInt(stack, "tick", 0);
        return (double) (tick) / (double) recharge;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icons[0] = ir.registerIcon("ab:itemMithrillSword_mode_0");
        this.icons[1] = ir.registerIcon("ab:itemMithrillSword_gem");
        this.icons[2] = ir.registerIcon("ab:itemMithrillSword_mode_1");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        int iconID = Math.min(1, pass);
        if (isEnabledMode(stack) && iconID == 0) {
            iconID = 2;
        }
        return this.icons[iconID];
    }

    private boolean isEnabledMode(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "isEnabledMode", false);
    }

    public int[] getLevels() {
        return LEVELS;
    }

    public int getLevel(ItemStack stack) {
        int mana = getMana_(stack);
        for (int i = LEVELS.length - 1; i > 0; i--) {
            if (mana >= LEVELS[i]) return i;
        }
        return 0;
    }

    public static int getMana_(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "mana", 0);
    }

    public static void setMana(ItemStack stack, int mana) {
        ItemNBTHelper.setInt(stack, "mana", mana);
    }

    public void addMana(ItemStack stack, int mana) {
        setMana(stack, Math.min(getMana(stack) + mana, 2147483647));
    }

    public boolean canExportManaToItem(ItemStack stack, ItemStack stack1) {
        return false;
    }

    public boolean canExportManaToPool(ItemStack stack, TileEntity tile) {
        return false;
    }

    public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
        return !(otherStack.getItem() instanceof vazkii.botania.api.mana.IManaGivingItem);
    }

    public boolean canReceiveManaFromPool(ItemStack stack, TileEntity tile) {
        return true;
    }

    public int getMana(ItemStack stack) {
        return getMana_(stack);
    }

    public int getMaxMana(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    public boolean isNoExport(ItemStack stack) {
        return true;
    }

    public boolean usesMana(ItemStack stack) {
        return true;
    }

    private float getSwordDamage(ItemStack stack) {
        int level = this.getLevel(stack);
        return 4.0F + (float) Math.round(
                (AdvancedBotanyAPI.mithrilToolMaterial.getDamageVsEntity() + (level * level / 1.5f))
                        * ConfigABHandler.damageFactorSpaceSword);
    }

    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = this.getItemAttributeModifiers();
        multimap.clear();
        UUID uuid = new UUID(getUnlocalizedName().hashCode(), 0L);
        multimap.put(
                SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Weapon modifier", getSwordDamage(stack), 0));
        multimap.put(
                SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(),
                new AttributeModifier(uuid, "Weapon speed", 0.25D, 1));
        return multimap;
    }
}
