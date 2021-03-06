import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Map {
	private int width, height;
	private Tile[][] tile_map;
	
	private int texture_size = 16;
	private int scaleFactor;
	private int aTexSize;
	
	Texture spritesheet;
	Texture selector;
	
	int[] selectorLocation;
	
	public Map(int width, int height, int aTS, int tS, int sF)
	{	
		this.width = width;
		this.height = height;
		tile_map = new Tile[this.height][this.width];
		aTexSize = aTS;
		texture_size = tS;
		scaleFactor = sF;
		
		try {
			spritesheet = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/map_spritesheet.png"));
			selector = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/selector.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(height + " " + width);
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				tile_map[i][j] = new Tile(TerrainType.PLAIN);
		
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
			{
				int chance = 1 + (int)(Math.random() * 100);
				if(chance < 5)
					tile_map[i][j] = new Tile(TerrainType.FOREST);
				else if(chance < 7)
					tile_map[i][j] = new Tile(TerrainType.ROAD);
			}
		
		int pass = 7;
		for(int z = 0; z < pass; z++)
			for(int i = 0; i < height; i++)
				for(int j = 0; j < width; j++)
				{
					int adRoads = 0;
					if(i - 1 > 0 && tile_map[i-1][j].getType() == TerrainType.ROAD)
					{
						adRoads++;
						if(i - 2 > 0 && tile_map[i-2][j].getType() == TerrainType.ROAD)
						{
							if(i + 1 < height && (Math.random() * 100 + 1) <= 80)
								tile_map[i][j].setType(TerrainType.ROAD);
								
						}
					}
					else if(i + 1 < height && tile_map[i+1][j].getType() == TerrainType.ROAD)
					{
						adRoads++;
						if(i + 2 < height && tile_map[i+2][j].getType() == TerrainType.ROAD)
						{
							if(i - 1 > 0&& (Math.random() * 100 + 1) <= 80)
								tile_map[i][j].setType(TerrainType.ROAD);
						}
					}
					else if(j - 1 > 0 && tile_map[i][j-1].getType() == TerrainType.ROAD)
					{
						adRoads++;
						if(j - 2 > 0 && tile_map[i][j-2].getType() == TerrainType.ROAD)
						{
							if(j + 1 < width && (Math.random() * 100 + 1) <= 80)
								tile_map[i][j].setType(TerrainType.ROAD);
						}
					}
					else if(j + 1 < width && tile_map[i][j+1].getType() == TerrainType.ROAD)
					{
						adRoads++;
						if(j + 2 < width && tile_map[i][j+2].getType() == TerrainType.ROAD)
						{
							if(j - 1 > 0 && (Math.random() * 100 + 1) <= 80)
								tile_map[i][j].setType(TerrainType.ROAD);
						}
					}
					else if((Math.random() * 100 + 1) <= 20 && tile_map[i][j].getType() == TerrainType.ROAD)
					{
						int side = (int)(Math.random() * 4);
						switch(side)
						{
						case 0:
							if(i - 1 > 0)
								tile_map[i-1][j].setType(TerrainType.ROAD);
							break;
						case 1:
							if(i + 1 < height)
								tile_map[i+1][j].setType(TerrainType.ROAD);
							break;
						case 2:
							if(j - 1 > 0)
								tile_map[i][j-1].setType(TerrainType.ROAD);
							break;
						case 3:
							if(j + 1 < width)
								tile_map[i][j+1].setType(TerrainType.ROAD);
							break;
						}
					}
					else if(adRoads >= 2)
						tile_map[i][j].setType(TerrainType.ROAD);
					
						
				}
	}
	public void draw()
	{
		spritesheet.bind();
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glBegin(GL11.GL_QUADS);
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
			{ 
				
				int tileInfo[] = tile_map[i][j].getSpriteLocation();
				
				//System.out.println((tileInfo[0])/((float)spritesheet.getWidth()));
				//System.out.println((float)spritesheet.getTextureHeight());
				//System.out.println((tileInfo[0] +16)/((float)spritesheet.getTextureWidth()));
				
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
		
		
				/*
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(100,100);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(100+64,100);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(100+64,100+64);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(100,100+64);*/
		
		
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
	public Tile getTile(int x, int y)
	{
		return tile_map[y][x];
	}
}

 // or GL11.glBind(texture.getTextureID());
