package pulxes.ab.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pulxes.ab.AdvancedBotany;
import vazkii.botania.client.core.handler.ModelHandler;
import vazkii.botania.client.render.IModelRegister;

public class BlockMod extends Block implements IModelRegister {
	
	public BlockMod(Material material, String name) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(AdvancedBotany.MODID, name));
		if(registerInCreative())
			this.setCreativeTab(AdvancedBotany.tabAB); 
	}
		  
	protected boolean registerInCreative() {
		return true;
	}
		  
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		if(Item.getItemFromBlock(this) != Items.AIR)
			ModelHandler.registerBlockToState(this, 0, getDefaultState()); 
	}
		  
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		super.eventReceived(state, world, pos, id, param);
		TileEntity tileentity = world.getTileEntity(pos);
		return (tileentity != null && tileentity.receiveClientEvent(id, param));
	}
}