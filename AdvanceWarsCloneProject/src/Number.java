
public class Number 
{
	static int[][] textureLocations;
	
	static
	{
		textureLocations = new int[9][2];
		textureLocations[0][0] = 0;
		textureLocations[1][0] = 16;
		textureLocations[2][0] = 32;
		textureLocations[3][0] = 48;
		textureLocations[4][0] = 64;
		textureLocations[5][0] = 80;
		textureLocations[6][0] = 96;
		textureLocations[7][0] = 112;
		textureLocations[8][0] = 128;
	}
	
	public static int[] getTextureLocationOfNumber(int n)
	{
		return textureLocations[n-1];
	}
}
