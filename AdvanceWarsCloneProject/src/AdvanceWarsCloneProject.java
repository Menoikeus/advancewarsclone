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
	
	public static void main(String[] args)
	{
		new AdvanceWarsCloneProject().start();
	}
	public void start()
	{
		initGL(800, 600);
		getDelta();
		isRunning = true;
		
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
    	
	    //	glEnable(GL_BLEND);
	    //	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	    	
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
    	GL11.glLoadIdentity();
    	GL11.glOrtho(0,  Display.getWidth(),  Display.getHeight(),  0,  1,  -1);
    	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void logic()
	{
		
	}
	
	public void render()
	{
		glClear(GL11.GL_COLOR_BUFFER_BIT);
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
