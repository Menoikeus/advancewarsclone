
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import de.matthiasmann.twl.utils.PNGDecoder;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class AdvanceWarsGraphics {

	private int x = 200, y = 200;
	
	private float rotation;
	
	private long lastFrame;
	
	private long lastFPS;
	private int fps;
	
    public static void main(String[] args) throws LWJGLException {
    	new AdvanceWarsGraphics().start();
    }

    // Start our game
    public void start() throws LWJGLException {
    	Display.setDisplayMode(new DisplayMode(1280,800));
    	Display.setFullscreen(true);;
    	Display.create();
    	
    	//OPENGL
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
    	GL11.glLoadIdentity();
    	GL11.glOrtho(0,  Display.getWidth(),  0,  Display.getHeight(),  1,  -1);
    	GL11.glMatrixMode(GL11.GL_MODELVIEW);
    	glEnable(GL_BLEND);
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    	
    	getDelta();
    	lastFPS = getTime();
    	
    	//EXPERIMENTAL
    	Texture tex = null;
    	try {
			tex = new Texture();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	while(!Display.isCloseRequested())
    	{
    		update(getDelta());
    		renderGL();
    		debugTexture(tex, 300, 300, 400, 400);
    		
    		Display.update();
    		Display.sync(60);
    	}
    }
    
    public void update(int delta)
    {
    	rotation += 0.15f * delta;
    	
    	/*
    	while (Keyboard.next()) {
		    if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() == Keyboard.KEY_A) {
		        	x -= 0.35f * delta;
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_D) {
		        	x += 0.35f * delta;
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_W) {
		        	y += 0.35f * delta;
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_S) {
		        	y -= 0.35f * delta;
		        }
		    }
		}*/
    	if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * delta;
         
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y -= 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y += 0.35f * delta;
    	
    	if (x < 0) x = 0;
        if (x > 800) x = 800;
        if (y < 0) y = 0;
        if (y > 600) y = 600;
        
        updateFPS();
    }
    
    public void updateFPS()
    {
    	if(getTime() - lastFPS > 1000)
		{
			System.out.println(fps);
			fps = 0;
			lastFPS = getTime();
		}
		fps++;
    }
    
    public long getTime()
    {
    	return System.nanoTime() / 1000000;
    }
    
    public int getDelta()
    {
    	long currentTime = getTime();
    	int delta = (int)(currentTime - lastFrame);
    	lastFrame = currentTime;
    	
    	return delta;
    }
    
    public void renderGL()
    {
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glColor3f(0.5f,0.5f,1.0f);
		
		GL11.glPushMatrix();
			GL11.glTranslatef(x,  y, 0);
			GL11.glRotatef(rotation,  0f,  0f,  1f);
			GL11.glTranslatef(-x,  -y,  0);
		
			GL11.glBegin(GL11.GL_TRIANGLES);
				GL11.glVertex2f(x - 50, y - 50);
			    GL11.glVertex2f(x + 50, y + 50);
			    GL11.glVertex2f(x + 50, y - 50);
			GL11.glEnd();
		GL11.glPopMatrix();
    }
    
    public static void debugTexture(Texture tex, float x, float y, float width, float height) {
        //usually glOrtho would not be included in our game loop
        //however, since it's deprecated, let's keep it inside of this debug function which we will remove later
        //likely redundant; will be removed upon migration to "modern GL"

        //bind the texture before rendering it
        tex.bind();

        //setup our texture coordinates
        //(u,v) is another common way of writing (s,t)
        float u = 0f;
        float v = 0f;
        float u2 = 1f;
        float v2 = 1f;

        //immediate mode is deprecated -- we are only using it for quick debugging
        glColor4f(1f, 1f, 1f, 1f);
        glBegin(GL_QUADS);
            glTexCoord2f(u, v);
            glVertex2f(x, y);
            glTexCoord2f(u, v2);
            glVertex2f(x, y + height);
            glTexCoord2f(u2, v2);
            glVertex2f(x + width, y + height);
            glTexCoord2f(u2, v);
            glVertex2f(x + width, y);
        glEnd();
    }
}