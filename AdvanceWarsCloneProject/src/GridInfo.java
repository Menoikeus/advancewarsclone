
public class GridInfo {
	private final int texture_size = 16;
	private int scaleFactor;
	
	private int aTexSize;
	
	private int width, height;
	
	public GridInfo(int sF)
	{
		scaleFactor = sF;
		width = 1280/(texture_size * scaleFactor);
		height = 1024/(texture_size * scaleFactor);
		
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
