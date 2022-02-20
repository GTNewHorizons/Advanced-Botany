package ab.common.item.equipment;

import ab.common.item.ItemMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import vazkii.botania.api.mana.IManaUsingItem;

public class ItemNebulaRod extends ItemMod implements IManaUsingItem {

	private static final String TAG_TICKS_COOLDOWN = "ticksCooldown";
	
	public ItemNebulaRod() {
		super("nebulaRod");
	}

	public boolean usesMana(ItemStack stack) {
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			Vec3 vec3 = player.getLook(1.0F).normalize();
			int posX = (int) (player.posX + (vec3.xCoord * 256));
			int posY = (int) player.posY;
			int posZ = (int) (player.posZ + (vec3.zCoord * 256));  
			ChunkCoordinates chunkcoordinates = new ChunkCoordinates(posX, posY, posZ);
			int topY = getTopSolidOrLiquidBlock(world, posX, posZ);
			chunkcoordinates.posY = topY == -1 ? posY : topY;
			((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation((double)chunkcoordinates.posX + 0.5D, (double)chunkcoordinates.posY + 1.6D, (double)chunkcoordinates.posZ + 0.5D, player.rotationYaw, player.rotationPitch); 
		}
		return stack;
	}
	
	public int getTopSolidOrLiquidBlock(World world, int p_72825_1_, int p_72825_2_) {
        Chunk chunk = world.getChunkFromBlockCoords(p_72825_1_, p_72825_2_);
        int x = p_72825_1_;
        int z = p_72825_2_;
        int k = chunk.getTopFilledSegment() + 15;
        p_72825_1_ &= 15;
        for (p_72825_2_ &= 15; k > 0; --k) {
            Block block = chunk.getBlock(p_72825_1_, k, p_72825_2_);
            if (!block.isAir(world, x, k, z)) {
                return k + 1;
            }
        }
        return -1;
    }
}
