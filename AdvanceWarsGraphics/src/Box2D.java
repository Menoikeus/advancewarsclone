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


public class Box2D extends AbstractEntity2D{
	protected float size;
	
	public Box2D(float x, float y, float size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public Box2D()
	{
		this.x = this.y = this.size = 0;
	}
	
	@Override
	public void setUp() {
	}

	@Override
	public void destroy() {	
	}

	@Override
	public void draw() {
		glBegin(GL_TRIANGLES);
		glVertex2f(x + size/2, y + size/2);
		glVertex2f(x + size/2, y - size/2);
		glVertex2f(x - size/2, y - size/2);
		glVertex2f(x - size/2, y - size/2);
		glVertex2f(x - size/2, y + size/2);
		glVertex2f(x + size/2, y + size/2);
		glEnd();
	}
	
}
