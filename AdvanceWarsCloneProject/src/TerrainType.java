
public enum TerrainType {
	PLAIN(18, 0), FOREST(0, 0), ROAD(36,0);
	//DESERT, MOUNTAIN, , ROAD, SEA;
	int[] mainTexture;
	int[] topTexture;
	
	private TerrainType(int x, int y)
	{
		mainTexture = new int[2];
		mainTexture[0] = x;
		mainTexture[1] = y;
	}
	public int[] getInfo()
	{
		return mainTexture;
	}
}
