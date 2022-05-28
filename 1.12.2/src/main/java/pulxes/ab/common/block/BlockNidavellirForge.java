package pulxes.ab.common.block;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import pulxes.ab.client.core.helper.ClientHelper;
import pulxes.ab.common.block.tile.TileNidavellirForge;
import pulxes.ab.common.lib.LibBlockNames;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.block.tile.TileSimpleInventory;
import vazkii.botania.common.core.helper.InventoryHelper;

public class BlockNidavellirForge extends BlockMod {
	
	private static final AxisAlignedBB[] AABB;
	
	static {
		double d = 0.1875F;
		double d1 = 0.0625F;
		AABB = new AxisAlignedBB[4];
		AABB[0] = new AxisAlignedBB(d, 0.0D, 0.0D, 1.0D - d, 0.75D, 1.0D - d1);
		AABB[1] = new AxisAlignedBB(d, 0.0D, d1, 1.0D - d, 0.75D, 1.0D);
		AABB[2] = new AxisAlignedBB(0.0D, 0.0D, d, 1.0D - d1, 0.75D, 1.0D - d);
		AABB[3] = new AxisAlignedBB(d1, 0.0D, d, 1.0D, 0.75D, 1.0D - d);
	}

	public BlockNidavellirForge() {
		super(Material.ROCK, LibBlockNames.NIDAVELLIR_FORGE);
		this.setHardness(3.0F);
	    this.setSoundType(SoundType.STONE);
	    this.setDefaultState(blockState.getBaseState().withProperty(BotaniaStateProps.CARDINALS, EnumFacing.SOUTH));
	}
	
	@Nonnull
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BotaniaStateProps.CARDINALS });
	}
	
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(BotaniaStateProps.CARDINALS)).getIndex();
	}
	
	@Nonnull
	public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
		int meta = Math.max(0, Math.min(state.getBlock().getMetaFromState(state) - 2, AABB.length - 1));
		return AABB[meta];
	}
	
	@Nonnull
	public IBlockState getStateFromMeta(int meta) {
		if(meta < 2 || meta > 5)
			meta = 2; 
		return getDefaultState().withProperty(BotaniaStateProps.CARDINALS, EnumFacing.getFront(meta));
	}
	
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(BotaniaStateProps.CARDINALS, placer.getHorizontalFacing()), 2);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing s, float xs, float ys, float zs) {
		if(world.isRemote)
		      return true;
		TileNidavellirForge forge = (TileNidavellirForge)world.getTileEntity(pos);
	    ItemStack stack = player.getHeldItem(hand);
	    if(player.isSneaking()) {
	    	if(!forge.getItemHandler().getStackInSlot(0).isEmpty()) {
	    		ItemStack copy = forge.getItemHandler().getStackInSlot(0).copy();
	            ItemHandlerHelper.giveItemToPlayer(player, copy);
	            forge.getItemHandler().setStackInSlot(0, ItemStack.EMPTY);
	            player.world.updateComparatorOutputLevel(forge.getPos(), null);
	    		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(forge);
		    	return true; 
	    	}
	    	InventoryHelper.withdrawFromInventory(forge, player);
	    	VanillaPacketDispatcher.dispatchTEToNearbyPlayers(forge);
	    	return true; 
	    } else {
	    	if(!stack.isEmpty())
	    		return forge.addItem(player, stack, false);
	    } 
		return false;
	}
	
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileSimpleInventory inv = (TileSimpleInventory)world.getTileEntity(pos);
		InventoryHelper.dropInventory(inv, world, state, pos);
		super.breakBlock(world, pos, state);
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	  
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public boolean hasTileEntity(IBlockState state) {
	    return true;
	}
	
	@Nonnull
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Nonnull
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileNidavellirForge();
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomStateMapper(this, (new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BotaniaStateProps.CARDINALS }).build());
	    ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileNidavellirForge.class);
	    ClientHelper.registerCustomItemblock(this, LibBlockNames.NIDAVELLIR_FORGE);
	}
	
	@Nonnull
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return BlockFaceShape.UNDEFINED;
	}
}