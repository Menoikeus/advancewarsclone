import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public enum TextSheet {
	WAIT(257,0), FIRE(0,0);
	
	int texX, texY;
	static Texture spritesheet;
	
	static int spriteWidth;
	static int spriteHeight;
	
	static
	{
		try {
			spritesheet = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/text_spritesheet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spriteWidth = 256;
		spriteHeight = 128;
	}
	
	private TextSheet(int tX, int tY)
	{	
		texX = tX;
		texY = tY;
	}
	
	public static int getSpriteWidth()
	{
		return spriteWidth;
	}
	
	public static int getSpriteHeight()
	{
		return spriteHeight;
	}
	
	public int[] getTextureLocation()
	{
		int[] info = new int[2];
		info[0] = texX;
		info[1] = texY;
		
		return info;
	}
	
	public static Texture getSpritesheet()
	{
		return spritesheet;
	}
	
	
}
