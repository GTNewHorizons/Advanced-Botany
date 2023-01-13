package ab.api;

import net.minecraft.util.ChunkCoordinates;
import vazkii.botania.api.wand.IWandBindable;

public interface IBoundRender extends IWandBindable {
	
	public ChunkCoordinates[] getBlocksCoord();
}
