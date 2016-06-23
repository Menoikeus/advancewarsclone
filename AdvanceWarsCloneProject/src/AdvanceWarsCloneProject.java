import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AdvanceWarsCloneProject {
	private long lastFrame;
	private boolean isRunning;
	private int width = 1280, height = 1024;
	int scaleFactor = 4;
	Map map;
	UnitMap uMap;
	GridInfo gridInfo;
	
	boolean mode_moving = false;
	int moveUnitX, moveUnitY;
	int[][] move_map;
	boolean mode_menu = false;
	boolean mode_attacking = false;
	int[][] attack_map;
	
	boolean justDid = false;
	
	Texture possibleMoveHighlight;
	Texture possibleAttackHighlight;
	Texture menuTexture;
	
	Menu gameMenu;
	
	public static void main(String[] args)
	{
		new AdvanceWarsCloneProject().start();
	}
	public void start()
	{
		initGL(width, height);
		getDelta();
		isRunning = true;
		
		gridInfo = new GridInfo(scaleFactor);
		map = new Map(gridInfo.getWidth(), gridInfo.getHeight(), gridInfo.getATexSize(), gridInfo.getTextureSize(), scaleFactor);
		uMap = new UnitMap(gridInfo.getWidth(), gridInfo.getHeight(), gridInfo.getATexSize(), gridInfo.getTextureSize(), scaleFactor);
		
		move_map = new int[gridInfo.getHeight()][gridInfo.getWidth()];
				
		try {
			possibleMoveHighlight = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/gold.png"));
			possibleAttackHighlight = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/gold_red.png"));
			menuTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/menu.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(isRunning)
		{
			logic();
			render();
			Display.update();
			Display.sync(60);
			if(Display.isCloseRequested())
				isRunning = false;
		}
		Display.destroy();
	}
	
	public void initGL(int width, int height)
	{
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
		}
		
		glEnable(GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
    	
	    	glEnable(GL_BLEND);
	    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	    	
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
    	GL11.glLoadIdentity();
    	GL11.glOrtho(0,  Display.getWidth(),  0,  Display.getHeight(),  1,  -1);
    	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void logic()
	{
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if(!mode_menu)
					map.moveSelector(0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if(!mode_menu)
					map.moveSelector(1);
				else
					gameMenu.addToSelected(-1);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				if(!mode_menu)
					map.moveSelector(2);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if(!mode_menu)
					map.moveSelector(3);
				else
					gameMenu.addToSelected(1);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				if(uMap.getUnit(map.getSelectorX(),map.getSelectorY()) != null && mode_moving == false && justDid == false && mode_menu == false)
				{
					mode_moving = true;
					moveUnitX = map.getSelectorX();
					moveUnitY = map.getSelectorY();
					System.out.println("MoveUnitX: " + moveUnitX + " MoveUnitY: " + moveUnitY);
					move_map = uMap.generateMovementMap(map, map.getSelectorX(), map.getSelectorY());
					justDid = true;
				}
				if(move_map[map.getSelectorY()][map.getSelectorX()] == 1 && mode_moving == true && justDid == false && mode_menu == false)
				{
					if(map.getSelectorX() - 1 > 0 && uMap.getUnit(map.getSelectorX() - 1,map.getSelectorY()) != null || 
							map.getSelectorX() + 1 > 0 && uMap.getUnit(map.getSelectorX() + 1,map.getSelectorY()) != null ||
							map.getSelectorY() - 1 > 0 && uMap.getUnit(map.getSelectorX(),map.getSelectorY() - 1) != null ||
							map.getSelectorY() + 1 > 0 && uMap.getUnit(map.getSelectorX(),map.getSelectorY() + 1) != null)
						gameMenu = new Menu(MenuType.MOVE_OR_FIRE);
					else
						gameMenu = new Menu(MenuType.MOVE);
					mode_menu = true;
					justDid = true;
				}
				if(mode_menu == true && justDid == false)
				{
					mode_menu = false;
					mode_moving = false;
					if(gameMenu.getOptionSelected().equals("WAIT"))
						uMap.moveUnit(moveUnitX, moveUnitY, map.getSelectorX(), map.getSelectorY());
					else if(gameMenu.getOptionSelected().equals("FIRE"))
					{
						mode_attacking = true;
						attack_map = uMap.generateAttackMap(map.getSelectorX(), map.getSelectorY(), moveUnitX, moveUnitY);
					}
					justDid = true;
				}
			}
		}
		justDid = false;
	}
	
	public void render()
	{
		glClear(GL11.GL_COLOR_BUFFER_BIT);
		map.draw();
		uMap.draw();
		
		int aTexSize = gridInfo.getATexSize();
		
		if(mode_moving == true)
		{
			possibleMoveHighlight.bind();
			GL11.glBegin(GL11.GL_QUADS);
			for(int i = 0; i < move_map.length; i++)
				for(int j = 0; j < move_map[i].length; j++)
				{ 
					if(move_map[i][j] == 1)
					{
						GL11.glTexCoord2f(0,0);
						GL11.glVertex2f(0 + j * aTexSize,i * aTexSize);
						GL11.glTexCoord2f(1,1);
						GL11.glVertex2f(aTexSize + j * aTexSize,i * aTexSize);
						GL11.glTexCoord2f(1, 0);
						GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize);
						GL11.glTexCoord2f(0,1);
						GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize);
					}
				}
			GL11.glEnd();
		}
		
		if(mode_menu == true)
		{
			int i = map.getSelectorY();
			int j = map.getSelectorX();
			menuTexture.bind();
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0);
				GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize);
				GL11.glTexCoord2f(1,1);
				GL11.glVertex2f(aTexSize + j * aTexSize,i * (aTexSize) - (aTexSize * (gameMenu.getOptionsList().size()/2f - 1)));
				GL11.glTexCoord2f(0,1);
				GL11.glVertex2f(0 + j * aTexSize,i * (aTexSize) - (aTexSize * (gameMenu.getOptionsList().size()/2f - 1)));
			glEnd();
			
			System.out.println((aTexSize * (gameMenu.getOptionsList().size()/2f - 1)));
			
			
			float textTextureWidth = TextSheet.getSpritesheet().getTextureWidth();
			float textTextureHeight = TextSheet.getSpritesheet().getTextureHeight();
			float totalHeight = aTexSize + (aTexSize * (gameMenu.getOptionsList().size()/2f - 1));
			
			TextSheet.getSpritesheet().bind();
			GL11.glBegin(GL11.GL_QUADS);
			for(int z = 0; z < gameMenu.getOptionsList().size(); z++)
			{
				int textureX = 8888, textureY = 8888;
				switch(gameMenu.getOptionsList().get(z))
				{
				case "WAIT": textureX = TextSheet.WAIT.getTextureLocation()[0];
							 textureY = TextSheet.WAIT.getTextureLocation()[1];
					break;
				case "FIRE": textureX = TextSheet.FIRE.getTextureLocation()[0];
				 			 textureY = TextSheet.FIRE.getTextureLocation()[1];
				 	break;
				}

				
				GL11.glTexCoord2f(textureX/textTextureWidth,textureY/textTextureHeight);
				GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize - (z) * totalHeight/gameMenu.getOptionsList().size());
				GL11.glTexCoord2f((textureX + TextSheet.getSpriteWidth())/textTextureWidth, textureY/textTextureHeight);
				GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize - (z) * totalHeight/gameMenu.getOptionsList().size());
				GL11.glTexCoord2f((textureX + TextSheet.getSpriteWidth())/textTextureWidth,(textureY + TextSheet.getSpriteHeight())/textTextureHeight);
				GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * (aTexSize) - (z+1) * totalHeight/gameMenu.getOptionsList().size());
				GL11.glTexCoord2f(textureX/textTextureWidth,(textureY + TextSheet.getSpriteHeight())/textTextureHeight);
				GL11.glVertex2f(0 + j * aTexSize,aTexSize + i * (aTexSize) - (z+1) * totalHeight/gameMenu.getOptionsList().size());
			}
			glEnd();
			
			
			try {
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/menu_selector.png")).bind();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			glBegin(GL_QUADS);
				GL11.glTexCoord2f(0,0);
				GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize - (gameMenu.getSelected()) * totalHeight/gameMenu.getOptionsList().size());
				GL11.glTexCoord2f(1,0);
				GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize - (gameMenu.getSelected()) * totalHeight/gameMenu.getOptionsList().size());
				GL11.glTexCoord2f(1,1);
				GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * (aTexSize) - (gameMenu.getSelected()+1) * totalHeight/gameMenu.getOptionsList().size());
				GL11.glTexCoord2f(0,1);
				GL11.glVertex2f(0 + j * aTexSize,aTexSize + i * (aTexSize) - (gameMenu.getSelected()+1) * totalHeight/gameMenu.getOptionsList().size());
			glEnd();
		}
		else if(mode_attacking)
		{
			possibleAttackHighlight.bind();
			GL11.glBegin(GL11.GL_QUADS);
			for(int i = 0; i < attack_map.length; i++)
				for(int j = 0; j < attack_map[i].length; j++)
				{ 
					if(attack_map[i][j] == 1)
					{
						GL11.glTexCoord2f(0,0);
						GL11.glVertex2f(0 + j * aTexSize,i * aTexSize);
						GL11.glTexCoord2f(1,1);
						GL11.glVertex2f(aTexSize + j * aTexSize,i * aTexSize);
						GL11.glTexCoord2f(1, 0);
						GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize);
						GL11.glTexCoord2f(0,1);
						GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize);
					}
				}
			GL11.glEnd();
			map.drawSelector();
		}
		else
			map.drawSelector();
	}
	
	//housekeeping
	public long getTime()
	{
		return System.nanoTime() / 1000000;
	}
	
	public long getDelta()
	{
		long currentTime = getTime();
		int delta = (int)(currentTime - lastFrame);
		lastFrame = currentTime;
		
		return delta;
	}
}
