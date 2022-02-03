package ab.api;

import ab.common.block.tile.TileCorporeaCatalyst;

public interface ICorporeaModification {
	
	public void setCatalystCoords(int x, int y, int z);
	
	public TileCorporeaCatalyst getCatalystTile();
	
	public boolean isModified();
	
	public void removeModification();
	
}
