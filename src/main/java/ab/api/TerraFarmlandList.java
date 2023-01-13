package ab.api;

import net.minecraft.block.Block;

public class TerraFarmlandList {

	Block block;
	int meta;
	
	public TerraFarmlandList(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public int getMeta() {
		return meta;
	}
}
