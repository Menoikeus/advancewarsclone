import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.*;

public class UnitMap {
	private int width, height;
	private Unit[][] unit_map;
	
	private int texture_size;
	private int scaleFactor;
	private int aTexSize;
	
	Texture spritesheet;
	Texture numbersheet;
	
	public UnitMap(int width, int height, int aTS, int tS, int sF)
	{	
		this.width = width;
		this.height = height;
		unit_map = new Unit[this.height][this.width];
		aTexSize = aTS;
		texture_size = tS;
		scaleFactor = sF;
		
		try {
			spritesheet = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/unit_spritesheet.png"));
			numbersheet = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/number_sheet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				unit_map[i][j] = null;
		
		unit_map[10][5] = new Unit(UnitType.INFANTRY);
		unit_map[10][9] = new Unit(UnitType.INFANTRY);
		unit_map[9][8] = new Unit(UnitType.MECH);
		unit_map[5][9] = new Unit(UnitType.RECON);
		unit_map[4][3] = new Unit(UnitType.TANK);
		unit_map[8][8] = new Unit(UnitType.ARTILLERY);
	}
	
	public void draw()
	{
		spritesheet.bind();
		
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
		
		numbersheet.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glBegin(GL11.GL_QUADS);
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
			{
				if(unit_map[i][j] != null)
				{
					double health = unit_map[i][j].getHealth();
					int[] tileInfo = new int[2];
					if(health < 9)
					{
						tileInfo = Number.getTextureLocationOfNumber((int)(unit_map[i][j].getHealth()+1));
					}
					else if(health < 10)
					{
						tileInfo = Number.getTextureLocationOfNumber(9);
					}
					if(health < 10)
					{
						GL11.glTexCoord2f((tileInfo[0])/((float)numbersheet.getTextureWidth()),((tileInfo[1])+16)/((float)numbersheet.getTextureHeight()));
						GL11.glVertex2f(0 + j * aTexSize,i * aTexSize);
						GL11.glTexCoord2f((tileInfo[0] +16)/((float)numbersheet.getTextureWidth()),((tileInfo[1])+16)/((float)numbersheet.getTextureHeight()));
						GL11.glVertex2f(aTexSize + j * aTexSize,i * aTexSize);
						GL11.glTexCoord2f((tileInfo[0] +16)/((float)numbersheet.getTextureWidth()),(tileInfo[1])/((float)numbersheet.getTextureHeight()));
						GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize);
						GL11.glTexCoord2f((tileInfo[0])/((float)numbersheet.getTextureWidth()),(tileInfo[1])/((float)numbersheet.getTextureHeight()));
						GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize);
					}
				}
			}
		GL11.glEnd();
	}
	
	public Unit getUnit(int x, int y)
	{
		return unit_map[y][x];
	}
	
	public void destroyUnit(int x, int y)
	{
		unit_map[y][x] = null;
	}
	
	public int[][] generateMovementMap(Map map, int x, int y)
	{
		int[][] objectMap = new int[map.getHeight()][map.getWidth()];
		float count = (float)unit_map[y][x].getUnitType().getMoveCount();
		objectMap[y][x] = 1;
		
		UnitType unType = unit_map[y][x].getUnitType();
		
		if(x + 1 < map.getWidth())
			recurseFind(map, objectMap, "right", count, x + 1, y, unType);
		if(x - 1 >= 0)
			recurseFind(map, objectMap, "left", count, x - 1, y, unType);
		if(y - 1 >= 0)
			recurseFind(map, objectMap, "bottom", count, x, y - 1, unType);
		if(y + 1 < map.getHeight())
			recurseFind(map, objectMap, "top", count, x, y + 1, unType);
		/*for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
				System.out.print(objectMap[i][j]);
			System.out.println();
		}*/
		return objectMap;
		
	}
	
	public void recurseFind(Map map, int[][] objectMap, String side, float count, int x, int y, UnitType uT)
	{
		if(map.getTile(x, y).getType() == TerrainType.PLAIN)
			count -= 1.0;
		else if(map.getTile(x, y).getType() == TerrainType.FOREST)
		{
			if(uT.isVehicle())
				count -= 2.0;
			else if(!uT.isVehicle())
				count -= 1.0;
		}
		else if(map.getTile(x, y).getType() == TerrainType.ROAD)
		{
			if(uT.isVehicle())
				count -= .5;
			else if(!uT.isVehicle())
				count -= 1.0;
		}
		
		if(!(count < -.3))
		{	
			objectMap[y][x] = 1;
			if(side != "top" && x + 1 < map.getWidth())
				recurseFind(map, objectMap, side, count, x + 1, y, uT);
			if(side != "bottom" && x - 1 >= 0)
				recurseFind(map, objectMap, side, count, x - 1, y, uT);
			if(side != "right" && y - 1 >= 0)
				recurseFind(map, objectMap, side, count, x, y - 1, uT);
			if(side != "left" && y + 1 < map.getHeight())
				recurseFind(map, objectMap, side, count, x, y + 1, uT);
		}
			
	}
	
	public int[][] generateAttackMap(int x, int y, int origX, int origY)
	{
		int[][] objectMap = new int[height][width];
		Unit attackingUnit = unit_map[origY][origX];
		int min = attackingUnit.getUnitType().getMinAttackRange();
		int max = attackingUnit.getUnitType().getMaxAttackRange();
		
		if(x + 1 < width)
			recurseAttack(objectMap, "right", min, max, x + 1, y, attackingUnit);
		if(x - 1 >= 0)
			recurseAttack(objectMap, "left", min, max, x - 1, y, attackingUnit);
		if(y - 1 >= 0)
			recurseAttack(objectMap, "bottom", min, max, x, y - 1, attackingUnit);
		if(y + 1 < height)
			recurseAttack(objectMap, "top", min, max, x, y + 1, attackingUnit);
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
				System.out.print(objectMap[i][j]);
			System.out.println();
		}
		return objectMap;
		
	}
	public void recurseAttack(int[][] objectMap, String side, int min, int max, int x, int y, Unit attackingUnit)
	{

		System.out.println("min: " + min + " max: " + max);
		max -= 1;
		min -= 1;
		
		if(max >= 0)
		{	
			if(min < -1 && unit_map[y][x] != null && unit_map[y][x] != attackingUnit)
			{
				objectMap[y][x] = 1;
				System.out.println("ENEMY SPOTTED");
			}
			if(side != "top" && x + 1 < width)
				recurseAttack(objectMap, side, min, max, x + 1, y, attackingUnit);
			if(side != "bottom" && x - 1 >= 0)
				recurseAttack(objectMap, side, min, max, x - 1, y, attackingUnit);
			if(side != "right" && y - 1 >= 0)
				recurseAttack(objectMap, side, min, max, x, y - 1, attackingUnit);
			if(side != "left" && y + 1 < height)
				recurseAttack(objectMap, side, min, max, x, y + 1, attackingUnit);
		}	
	}
	
	//move unit at uX, uY, to target block tX, tY
	public void moveUnit(int uX, int uY, int tX, int tY)
	{
		System.out.print(uX + " " + uY + " " + tX + " " + tY);
		unit_map[tY][tX] = unit_map[uY][uX];
		if(uX != tX || uY != tY)
			unit_map[uY][uX] = null;
	}
	
}
