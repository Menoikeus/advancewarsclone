
public class GridInfo {
	private final int texture_size = 16;
	private int scaleFactor;
	
	private int aTexSize;
	
	private int width, height;
	
	public GridInfo(int sF, int w, int h)
	{
		scaleFactor = sF;
		width = w/(texture_size * scaleFactor);
		height = h/(texture_size * scaleFactor);
		
		aTexSize = scaleFactor * texture_size;
	}

	public int getATexSize() {
		return aTexSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getTextureSize() {
		return texture_size;
	}
}
