import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import de.matthiasmann.twl.utils.PNGDecoder;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
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
	
	public static void main(String[] args)
	{
		new AdvanceWarsCloneProject().start();
	}
	public void start()
	{
		initGL(width, height);
		getDelta();
		isRunning = true;
		
		map = new Map(4);
		uMap = new UnitMap(4);
		
		
		
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
				map.moveSelector(0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				map.moveSelector(1);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				map.moveSelector(2);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				map.moveSelector(3);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				//if(uMap[y][x])
			}
		}
	}
	
	public void render()
	{
		glClear(GL11.GL_COLOR_BUFFER_BIT);
		map.draw();
		uMap.draw();
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
