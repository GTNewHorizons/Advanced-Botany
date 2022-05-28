package pulxes.ab.common.item.equipment;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import pulxes.ab.AdvancedBotany;
import pulxes.ab.common.item.ItemMod;
import pulxes.ab.common.lib.LibItemNames;
import vazkii.botania.api.item.IBlockProvider;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.PlayerHelper;
import vazkii.botania.common.item.ItemBlackHoleTalisman;
import vazkii.botania.common.item.ItemCraftingHalo;

public class ItemBlackHoleBox extends ItemMod implements IBlockProvider {
	
	public static final ResourceLocation GLOW_TEXTURE = new ResourceLocation(AdvancedBotany.MODID + ":textures/misc/glow.png");	
	private static final int MAX_SEGMENTS = 12;

	public ItemBlackHoleBox() {
		super(LibItemNames.BLACK_HOLE_BOX);
		this.setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Nonnull
	public void onUpdate(@Nonnull ItemStack stack, World world, @Nonnull Entity entity, int pos, boolean equipped) {
		if(!(entity instanceof EntityLivingBase))
		      return; 
		EntityLivingBase living = (EntityLivingBase)entity;
		boolean eqLastTick = wasEquipped(stack);
		if(!equipped && living.getHeldItemOffhand() == stack)
		      equipped = true; 
		if(!equipped && eqLastTick)
			setEquipped(stack, equipped);
		if(!eqLastTick && equipped) {
			setEquipped(stack, equipped);
			int angles = 360;
			int segAngles = angles / MAX_SEGMENTS;
			float shift = (segAngles / 2);
			setRotationBase(stack, getCheckingAngle((EntityLivingBase)entity) - shift);
		} 
		if(!world.isRemote && entity.ticksExisted % 10 == 0) {
			for(int i = 0; i < MAX_SEGMENTS; i++) {
				ItemStack blackHalo = getItemForSlot(stack, i).copy();
				if(!blackHalo.isEmpty()) {
					((ItemBlackHoleTalisman)blackHalo.getItem()).onUpdate(blackHalo, world, entity, pos, equipped);
					setItemSlot(stack, blackHalo, i);
				}
			}	
		}
	}
	
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		int segment = getSegmentLookedAt(stack, player);
		if(!getItemForSlot(stack, segment).isEmpty()) {
			ItemStack blackHole = getItemForSlot(stack, segment).copy();
			if(player.isSneaking()) {
				ItemHandlerHelper.giveItemToPlayer(player, blackHole);
				setItemSlot(stack, ItemStack.EMPTY, segment);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			} else {
				int dmg = blackHole.getItemDamage();
				blackHole.setItemDamage((dmg ^ 0xFFFFFFFF) & 0x1);
				player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.3F, 0.1F);
				setItemSlot(stack, blackHole, segment);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
		} else {
			for(int i = 0; i < 9; i++) {
				ItemStack hotBarStack = player.inventory.getStackInSlot(i);
				if(!hotBarStack.isEmpty() && hotBarStack.getItem() instanceof ItemBlackHoleTalisman) {
					Block block = ((ItemBlackHoleTalisman)hotBarStack.getItem()).getBlock(hotBarStack);
					if(block != null && Item.getItemFromBlock(block) != Items.AIR) {
						setItemSlot(stack, hotBarStack, segment);
						hotBarStack.shrink(1);
						return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
					}
				}
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack box = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
			if(!world.isRemote) {
				for(int i = 0; i < MAX_SEGMENTS; i++) {
					ItemStack blackHole = getItemForSlot(box, i).copy();
					if(blackHole.isEmpty())
						continue;
					ItemBlackHoleTalisman blackHoleItem = (ItemBlackHoleTalisman)blackHole.getItem();
					Block bBlock = blackHoleItem.getBlock(blackHole);
					int bmeta = blackHoleItem.getBlockMeta(blackHole);
					IItemHandler inv = (IItemHandler)tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
					ItemStack toAdd = new ItemStack(bBlock, 1, bmeta);
			        toAdd.setCount(blackHoleItem.remove(blackHole, toAdd.getMaxStackSize()));
			        ItemStack remainder = ItemHandlerHelper.insertItemStacked(inv, toAdd, false);
			        if(!remainder.isEmpty())
			        	ItemNBTHelper.setInt(blackHole, "blockCount", blackHoleItem.getBlockCount(blackHole) + remainder.getCount());
			        setItemSlot(box, blackHole, i);
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack box, World world, List<String> list, ITooltipFlag flags) {
		if(GuiScreen.isShiftKeyDown()) {
			for(int i = 0; i < MAX_SEGMENTS; i++) {
				ItemStack blackHole = getItemForSlot(box, i).copy();
				if(blackHole.isEmpty())
					continue;
				ItemBlackHoleTalisman talisman = (ItemBlackHoleTalisman)blackHole.getItem();
				Block block = talisman.getBlock(blackHole);
				int count = talisman.getBlockCount(blackHole);
				String active = "" + (blackHole.getItemDamage() == 1 ? TextFormatting.GREEN : TextFormatting.RED);
				list.add(active + count + TextFormatting.RESET + " " + I18n.format((new ItemStack(block, 1, talisman.getBlockMeta(blackHole))).getUnlocalizedName() + ".name", new Object[0]));
			}
		} else
			list.add(I18n.format("botaniamisc.shiftinfo", new Object[0]).replaceAll("&", "\u00A7")); 
	}		
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
	    ItemStack stack = PlayerHelper.getFirstHeldItemClass((EntityPlayer)entityPlayerSP, ItemBlackHoleBox.class);
	    boolean hasCraftingHalo = (entityPlayerSP.getHeldItemMainhand().getItem() instanceof ItemCraftingHalo) || (entityPlayerSP.getHeldItemOffhand().getItem() instanceof ItemCraftingHalo);
	    if(!stack.isEmpty() && !hasCraftingHalo)
	    	render(stack, (EntityPlayer)entityPlayerSP, event.getPartialTicks()); 
	}
	
	@SideOnly(Side.CLIENT)
	public void render(ItemStack stack, EntityPlayer player, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
	    Tessellator tess = Tessellator.getInstance();
	    double renderPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
	    double renderPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
	    double renderPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
	    GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(770, 771);
	    float alpha = ((float)Math.sin(((ClientTickHandler.ticksInGame + partialTicks) * 0.2F)) * 0.5F + 0.5F) * 0.4F + 0.3F;
	    double posX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
	    double posY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
	    double posZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
	    GlStateManager.translate(posX - renderPosX, posY - renderPosY + player.getDefaultEyeHeight(), posZ - renderPosZ);
	    float base = getRotationBase(stack);
	    int angles = 360;
	    int segAngles = angles / MAX_SEGMENTS;
	    float shift = base - (segAngles / 2);
	    float u = 1.0F;
	    float v = 0.25F;
	    float s = 3.2F;
	    float m = 0.8F;
	    float y = v * s * 2.0F;
	    float y0 = 0.0F;
	    int segmentLookedAt = getSegmentLookedAt(stack, (EntityLivingBase)player);
	    for(int seg = 0; seg < MAX_SEGMENTS; seg++) {
	    	boolean inside = false;
	    	float rotationAngle = (seg + 0.5F) * segAngles + shift;
	    	GlStateManager.pushMatrix();
	    	GlStateManager.rotate(rotationAngle, 0.0F, 1.0F, 0.0F);
	    	GlStateManager.translate(s * m, -0.75F, 0.0F);
	    	if(segmentLookedAt == seg)
	    		inside = true; 
	    	ItemStack slotStack = getItemForSlot(stack, seg);
	    	if(!slotStack.isEmpty()) {
	    		ItemBlackHoleTalisman blackHole = (ItemBlackHoleTalisman)slotStack.getItem();
	    		ItemStack renderStack = new ItemStack(blackHole.getBlock(slotStack));
	    		int meta = 0;
	    		if(renderStack.getItem().getHasSubtypes())
	    			meta = blackHole.getBlockMeta(slotStack);
	    		renderStack.setItemDamage(meta);
	    		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	    		RenderHelper.enableStandardItemLighting();
	    		float scale = 0.75F;
	    		double worldTime = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks + seg * 2.75f;
	    		GlStateManager.scale(scale, scale, scale);
	    		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
	    		GlStateManager.translate(0.0F, 0.4F + Math.sin(worldTime / 8.0D) / 20.0D, 0.0F);
	    		GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
	    		Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, ItemCameraTransforms.TransformType.GUI);
	    		RenderHelper.disableStandardItemLighting();
	    	} 
	    	GlStateManager.popMatrix();
	    	GlStateManager.pushMatrix();
	    	GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
	    	float a = alpha;
	    	if(inside) {
	    		a += 0.3F;
	    		y0 = -y;
	    	} 
	    	GlStateManager.enableBlend();
	    	GlStateManager.blendFunc(770, 771);
	    	if(seg % 2 == 0)
	    		GlStateManager.color(0.6F, 0.6F, 0.6F, a);
	    	else
	    		GlStateManager.color(1.0F, 1.0F, 1.0F, a);
	    	GlStateManager.disableCull();
	    	mc.renderEngine.bindTexture(GLOW_TEXTURE);
	    	tess.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
	    	for(int i = 0; i < segAngles; i++) {
	    		float ang = (i + seg * segAngles) + shift;
	    		double xp = Math.cos(ang * Math.PI / 180.0D) * s;
	    		double zp = Math.sin(ang * Math.PI / 180.0D) * s;
	    		tess.getBuffer().pos(xp * m, y, zp * m).tex(u, v).endVertex();
	    		tess.getBuffer().pos(xp, y0, zp).tex(u, 0.0D).endVertex();
	    		xp = Math.cos((ang + 1.0F) * Math.PI / 180.0D) * s;
	    		zp = Math.sin((ang + 1.0F) * Math.PI / 180.0D) * s;
	    		tess.getBuffer().pos(xp, y0, zp).tex(0.0D, 0.0D).endVertex();
	    		tess.getBuffer().pos(xp * m, y, zp * m).tex(0.0D, v).endVertex();
	    	} 
	    	y0 = 0.0F;
	    	tess.draw();
	    	GlStateManager.enableCull();
	    	GlStateManager.popMatrix();
	    } 
	    GlStateManager.popMatrix();
	}
	
	@SideOnly(Side.CLIENT)
	public static void renderHUD(ScaledResolution resolution, EntityPlayer player, ItemStack box) {
		Minecraft mc = Minecraft.getMinecraft();
	    int slot = getSegmentLookedAt(box, player);
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(770, 771);
	    ItemStack blackHole = getItemForSlot(box, slot);
	    if(!blackHole.isEmpty()) {
	    	String name = blackHole.getDisplayName();
	    	String count = "" + ((ItemBlackHoleTalisman)blackHole.getItem()).getBlockCount(blackHole);	
	    	int l = mc.fontRenderer.getStringWidth(name);
	    	int x = resolution.getScaledWidth() / 2 - l / 2;
	    	int y = resolution.getScaledHeight() / 2 - 65;
	    	Gui.drawRect(x - 6, y - 6, x + l + 6, y + 43, 570425344);
	    	Gui.drawRect(x - 4, y - 4, x + l + 4, y + 41, 570425344);
	    	RenderHelper.enableGUIStandardItemLighting();
	    	GlStateManager.enableRescaleNormal();
	    	mc.getRenderItem().renderItemAndEffectIntoGUI(blackHole, resolution.getScaledWidth() / 2 - 8, resolution.getScaledHeight() / 2 - 52);
	    	RenderHelper.disableStandardItemLighting();
	    	mc.fontRenderer.drawStringWithShadow(name, x, y, 16777215);
	    	mc.fontRenderer.drawStringWithShadow(count, resolution.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(count) / 2, y + 32, 16777215);
	    }
	}
	
	public void setItemSlot(ItemStack box, ItemStack stack, int slot) {
		NBTTagCompound cmp = new NBTTagCompound();
		if(!stack.isEmpty())
			stack.copy().writeToNBT(cmp);
		ItemNBTHelper.setCompound(box, "blackHoleSlot" + slot, cmp);
	}
	
	public static ItemStack getItemForSlot(ItemStack box, int slot) {
		if(slot >= MAX_SEGMENTS)
			return ItemStack.EMPTY;
		NBTTagCompound cmp = ItemNBTHelper.getCompound(box, "blackHoleSlot" + slot, true);
		if(cmp != null)
			return new ItemStack(cmp);
		return ItemStack.EMPTY;
	}
	
	private static int getSegmentLookedAt(ItemStack stack, EntityLivingBase player) {
		getRotationBase(stack);
		float yaw = getCheckingAngle(player, getRotationBase(stack));
		int angles = 360;
		int segAngles = angles / MAX_SEGMENTS;
		for (int seg = 0; seg < MAX_SEGMENTS; seg++) {
			float calcAngle = seg * segAngles;
			if (yaw >= calcAngle && yaw < calcAngle + segAngles)
				return seg; 
		} 
		return -1;
	}
	
	public static void setRotationBase(ItemStack stack, float rotation) {
		ItemNBTHelper.setFloat(stack, "rotationBase", rotation);
	}
		  
	public static float getRotationBase(ItemStack stack) {
		return ItemNBTHelper.getFloat(stack, "rotationBase", 0.0F);
	}
	
	private static float getCheckingAngle(EntityLivingBase player, float base) {
		float yaw = MathHelper.wrapDegrees(player.rotationYaw) + 90.0F;
		int angles = 360;
		int segAngles = angles / MAX_SEGMENTS;
		float shift = (segAngles / 2);
		if (yaw < 0.0F)
			yaw = 180.0F + 180.0F + yaw; 
		    yaw -= 360.0F - base;
		    float angle = 360.0F - yaw + shift;
		    if (angle < 0.0F)
		    	angle = 360.0F + angle; 
		    return angle;
	}
	
	private static float getCheckingAngle(EntityLivingBase player) {
		return getCheckingAngle(player, 0.0F);
	}
	
	public static void setEquipped(ItemStack stack, boolean equipped) {
		ItemNBTHelper.setBoolean(stack, "equipped", equipped);
	}
	
	public static boolean wasEquipped(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, "equipped", false);
	}

	public int getBlockCount(EntityPlayer player, ItemStack requestor, ItemStack box, Block block, int meta) {
		for(int i = 0; i < MAX_SEGMENTS; i++) {
			ItemStack blackHole = getItemForSlot(box, i).copy();
			if(blackHole.isEmpty())
				continue;
			ItemBlackHoleTalisman talisman = (ItemBlackHoleTalisman)blackHole.getItem();
			Block stored = talisman.getBlock(blackHole);
		    int storedMeta = talisman.getBlockMeta(blackHole);
		    if(stored == block && storedMeta == meta)
		    	return talisman.getBlockCount(blackHole);
		} 
	    return 0;
	}

	public boolean provideBlock(EntityPlayer player, ItemStack requestor, ItemStack box, Block block, int meta, boolean doit) {
		for(int i = 0; i < MAX_SEGMENTS; i++) {
			ItemStack blackHole = getItemForSlot(box, i).copy();
			if(blackHole.isEmpty())
				continue;
			ItemBlackHoleTalisman talisman = (ItemBlackHoleTalisman)blackHole.getItem();
			Block stored = talisman.getBlock(blackHole);
		    int storedMeta = talisman.getBlockMeta(blackHole);
		    if(stored == block && storedMeta == meta) {
		    	int count = talisman.getBlockCount(blackHole);
		    	if(count > 0) {
		    		if(doit) {
		    			talisman.remove(blackHole, 1);
		    			setItemSlot(box, blackHole, i);
		    		}
		    		return true;
		    	} 
		    } 
		}
		return false;
	}
}