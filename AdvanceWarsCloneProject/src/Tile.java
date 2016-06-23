
public class Tile {
	TerrainType tType;
	
	public Tile(TerrainType t)
	{
		tType = t;
	}
	public TerrainType getType()
	{
		return tType;
	}
	public void setType(TerrainType t)
	{
		tType = t;
	}
	public int[] getSpriteLocation()
	{
		return tType.getInfo();
	}
}
