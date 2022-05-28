package pulxes.ab.common.item.equipment;

import java.util.List;
import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.api.AdvancedBotanyAPI;
import pulxes.ab.api.EffectRender;
import pulxes.ab.api.IRankItem;
import pulxes.ab.client.core.handler.ClientEffectHandler;
import pulxes.ab.common.core.handler.AdvBotanyConfigHandler;
import pulxes.ab.common.core.handler.NetworkHandler;
import pulxes.ab.common.entity.EntityProjectileSpaceBurst;
import pulxes.ab.common.lib.LibItemNames;
import pulxes.ab.common.lib.register.ItemListAB;
import pulxes.ab.common.lib.register.SoundListAB;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.render.IModelRegister;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemSpaceBlade extends ItemSword implements IModelRegister, IRankItem, IManaUsingItem {
	
	public static final int[] RANK_LEVELS = new int[] { 0, 10000, 1000000, 10000000, 100000000, 1000000000 };
	private static final int[] CREATIVE_MANA = new int[] { 9999, 999999, 9999999, 99999999, 999999999, 2147483646 };
	public static final int MAX_RECHARGE_TICK = 36;

	public ItemSpaceBlade() {
		super(AdvancedBotanyAPI.MITHRILL_TOOL_MATERIAL);
		this.setCreativeTab(AdvancedBotany.tabAB);
		this.setRegistryName(new ResourceLocation(AdvancedBotany.MODID, LibItemNames.SPACE_BLADE));
	    this.setUnlocalizedName(LibItemNames.SPACE_BLADE);
	    MinecraftForge.EVENT_BUS.register(this);
	    this.addPropertyOverride(new ResourceLocation(AdvancedBotany.MODID, "enabled"), (stack, world, entity) -> isEnabledMode(stack) ? 1.0F : 0.0F);
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, @Nonnull EntityLivingBase attacker) {
		if(!attacker.world.isRemote && entity != null && attacker instanceof EntityPlayer) {
			if(getLevel(stack) >= 3 && isEnabledMode(stack)) {
				float size = getLevel(stack) >= 4 ? (getLevel(stack) >= 5 ? 3.5f : 2.5f) : 1.5f;
				AxisAlignedBB axis = (new AxisAlignedBB(entity.posX - size, entity.posY - 1.7F, entity.posZ - size, entity.posX + size, entity.posY + 1.7F, entity.posZ + size));
				List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
				for(EntityLivingBase living : entities) {
					if(living instanceof EntityPlayer && (((EntityPlayer)living).getName().equals(attacker.getName()) || (FMLCommonHandler.instance().getMinecraftServerInstance() != null && !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled())))
						continue; 
					else if(living.hurtTime == 0) {
						float damage = getSwordDamage(stack);
						living.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), damage);
					}
				}
			}
		}	
		return super.hitEntity(stack, entity, attacker);
	}
	
	@Nonnull
	public void onUpdate(@Nonnull ItemStack stack, World world, @Nonnull Entity entity, int par4, boolean par5) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			int rechargeTick = getRechargeTick(stack);
			if(!world.isRemote) {
				if(rechargeTick > 0)
					setRechargeTick(stack, --rechargeTick);
			}
		}
	}
	
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		if(!world.isRemote) {
			if(!player.isSneaking()) {
				if(getRechargeTick(player.getHeldItem(hand)) == 0)
					startPlayerDash((EntityPlayerMP)player, player.getHeldItem(hand));
				else if(!player.getHeldItemOffhand().isEmpty() && player.getHeldItemOffhand().getItem() == ItemListAB.itemSpaceBlade && getRechargeTick(player.getHeldItemOffhand()) == 0)
					startPlayerDash((EntityPlayerMP)player, player.getHeldItemOffhand());
			} else {
				ItemNBTHelper.setBoolean(player.getHeldItem(hand), "isEnabledMode", !this.isEnabledMode(player.getHeldItem(hand)));
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}	
	
	private void startPlayerDash(EntityPlayerMP player, ItemStack stack) {
		NetworkHandler.sendPacketToSpaceDash(player);
		playerSpaceDash(player);
		setRechargeTick(stack, MAX_RECHARGE_TICK);
		player.world.playSound(null, player.posX, player.posY, player.posZ, SoundListAB.bladeSpace, SoundCategory.PLAYERS, 2.3F, 1.2F);
	}
	
	@SubscribeEvent
	public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
		if(!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == ItemListAB.itemSpaceBlade && ItemSpaceBlade.getLevel_(evt.getItemStack()) >= 1)
			NetworkHandler.sendPacketToSpaceBurst();
	}
	
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return Integer.MAX_VALUE;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flags) {
		String rank = I18n.format("botania.rank" + getLevel(stack), new Object[0]);
	    String rankFormat = I18n.format("botaniamisc.toolRank", new Object[] { rank });
	    list.add(rankFormat.replaceAll("&", "\u00A7"));
	    if(getMana(stack) == Integer.MAX_VALUE)
	    	list.add(TextFormatting.DARK_AQUA + I18n.format("abmisc.spaceBlade.maximumMana", new Object[0]));
		if(GuiScreen.isShiftKeyDown()) {
			int level = getLevel(stack);
			list.add((level >= 1 ? TextFormatting.GREEN : "") + I18n.format("abmisc.spaceBlade.bladeInfo.0", new Object[0]));
			list.add((level >= 2 ? TextFormatting.GREEN : "") + I18n.format("abmisc.spaceBlade.bladeInfo.1", new Object[0]));
			list.add((level >= 3 ? TextFormatting.GREEN : "") + I18n.format("abmisc.spaceBlade.bladeInfo.LEVEL".replaceAll("LEVEL", Integer.toString(level < 3 ? 2 : level - 1)), new Object[0]));
		} else
			list.add(I18n.format("botaniamisc.shiftinfo", new Object[0]).replaceAll("&", "\u00A7")); 
	}
	
	public static boolean spaceBurstAttack(EntityPlayer player) {
		if(!player.world.isRemote && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == ItemListAB.itemSpaceBlade && player.getCooledAttackStrength(0.0F) == 1.0F && ManaItemHandler.requestManaExactForTool(player.getHeldItemMainhand(), player, 120, true)) {
			EntityProjectileSpaceBurst spaceBurst = new EntityProjectileSpaceBurst(player);
			spaceBurst.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			spaceBurst.motionX *= 0.27f;
			spaceBurst.motionY *= 0.27f;
			spaceBurst.motionZ *= 0.27f;
			spaceBurst.setAttacker(player.getName());
			spaceBurst.setDamage(ItemSpaceBlade.getSwordDamage(player.getHeldItemMainhand()));
			player.world.spawnEntity(spaceBurst);
			player.world.playSound(null, player.posX, player.posY, player.posZ, SoundListAB.bladeSpace, SoundCategory.PLAYERS, 0.5F, 3.6F);
			return true;
		}
		return false;
	}
	
	public static void doParticleDash() {
		EffectRender effectRender = new EffectRender(7) {
			public void doEffect() {
				Minecraft mc = Minecraft.getMinecraft();
				for(int i = 0; i < 16; i++) {
					float r = mc.world.rand.nextBoolean() ? (225.0f / 255.0f) : (101.0f / 255.0f);
					float g = mc.world.rand.nextBoolean() ? (67.0f / 255.0f) : (209.0f / 255.0f);
					float b = mc.world.rand.nextBoolean() ? (240.0f / 255.0f) : (225.0f / 255.0f);
					Botania.proxy.sparkleFX(mc.player.posX + (Math.random() - 0.5D), mc.player.posY + ((Math.random() - 0.5D) * 2) + 1.0f, mc.player.posZ + (Math.random() - 0.5D), r + (float)(Math.random() / 4 - 0.125D), g + (float)(Math.random() / 4 - 0.125D), b + (float)(Math.random() / 4 - 0.125D), 1.8F * (float)(Math.random() - 0.5D), 4);
				}
			}
		};
		ClientEffectHandler.addEffectRender(effectRender);
	}
	
	@Nonnull
	public static void playerSpaceDash(@Nonnull EntityPlayer player) {				  
		Vec3d vec3 = player.getLook(1.0F).normalize();
		player.motionX += vec3.x * 3.25f;
		player.motionY += (vec3.y / 1.6f);
		player.motionZ += vec3.z * 3.25f;
	}
	
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if(isInCreativeTab(tab))
			for(int mana : CREATIVE_MANA) {
				ItemStack stack = new ItemStack(this);
				setMana(stack, mana);
				stacks.add(stack);
			}
	}
	
	private static float getSwordDamage(ItemStack stack) {
		int level = ItemSpaceBlade.getLevel_(stack);
		return (float)Math.round((3.0F + AdvancedBotanyAPI.MITHRILL_TOOL_MATERIAL.getAttackDamage() + (level * level / 1.5f)) * AdvBotanyConfigHandler.damageFactorSpaceSword);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
		 Multimap<String, AttributeModifier> multimap = getItemAttributeModifiers(equipmentSlot);
		 multimap.clear();
		 if(equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			 multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)getSwordDamage(stack), 0));
			 multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.5000000953674316D, 0));
		 }
		 return multimap;
    }
	
	@Nonnull
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack stack) {
		return super.getUnlocalizedNameInefficiently(stack).replaceAll("item\\.", "item." + AdvancedBotany.MODID + ":");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	public static void setRechargeTick(ItemStack stack, int value) {
		ItemNBTHelper.setInt(stack, "rechargeTick", value);
	}
	
	public static int getRechargeTick(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, "rechargeTick", 0);
	}
	
	private boolean isEnabledMode(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, "isEnabledMode", false);
	}
	
	private void changeEnableMode(ItemStack stack) {
		ItemNBTHelper.setBoolean(stack, "isEnabledMode", !isEnabledMode(stack));
	}
	
	public int[] getLevels() {
		return ItemSpaceBlade.RANK_LEVELS;
	}
	
	public int getLevel(ItemStack stack) {
		int mana = getMana_(stack);
		for(int i = ItemSpaceBlade.RANK_LEVELS.length - 1; i > 0; i--) 
			if(mana >= ItemSpaceBlade.RANK_LEVELS[i])
				return i;    
		return 0;
	}
	
	public static int getLevel_(ItemStack stack) {
		int mana = getMana_(stack);
		for(int i = ItemSpaceBlade.RANK_LEVELS.length - 1; i > 0; i--) 
			if(mana >= ItemSpaceBlade.RANK_LEVELS[i])
				return i;    
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
	  
	public boolean showDurabilityBar(ItemStack stack) {
		return getRechargeTick(stack) != 0;
	}
	
	public double getDurabilityForDisplay(ItemStack stack) {
		return (float)getRechargeTick(stack) / (float)MAX_RECHARGE_TICK;
	}
}