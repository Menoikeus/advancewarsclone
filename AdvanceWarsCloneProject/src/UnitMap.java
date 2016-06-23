import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.*;

public class UnitMap {
	private int width, height;
	private Unit[][] unit_map;
	
	private final int texture_size = 16;
	private int scaleFactor;
	
	Texture spritesheet;
	
	public UnitMap(int sF)
	{
		scaleFactor = sF;
		width = 1280/(texture_size * scaleFactor);
		height = 1024/(texture_size * scaleFactor);
		
		unit_map = new Unit[height][width];
		
		try {
			spritesheet = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/unit_spritesheet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				unit_map[i][j] = null;
		
		unit_map[10][5] = new Unit(UnitType.INFANTRY);
		
		
	}
	
	public void draw()
	{
		spritesheet.bind();
		int aTexSize = texture_size*scaleFactor;
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glBegin(GL11.GL_QUADS);
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				if(unit_map[i][j] != null)
				{
					int tileInfo[] = unit_map[i][j].getSpriteLocation();
					
					//System.out.println((tileInfo[0])/((float)spritesheet.getWidth()));
					//System.out.println((float)spritesheet.getTextureHeight());
					System.out.println((tileInfo[0] +16)/((float)spritesheet.getTextureWidth()));
					
					GL11.glTexCoord2f((tileInfo[0])/((float)spritesheet.getTextureWidth()),((tileInfo[1])+16)/((float)spritesheet.getTextureHeight()));
					GL11.glVertex2f(0 + j * aTexSize,i * aTexSize);
					GL11.glTexCoord2f((tileInfo[0] +16)/((float)spritesheet.getTextureWidth()),((tileInfo[1])+16)/((float)spritesheet.getTextureHeight()));
					GL11.glVertex2f(aTexSize + j * aTexSize,i * aTexSize);
					GL11.glTexCoord2f((tileInfo[0] +16)/((float)spritesheet.getTextureWidth()),(tileInfo[1])/((float)spritesheet.getTextureHeight()));
					GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize);
					GL11.glTexCoord2f((tileInfo[0])/((float)spritesheet.getTextureWidth()),(tileInfo[1])/((float)spritesheet.getTextureHeight()));
					GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize);
				}
		GL11.glEnd();
	}
}
