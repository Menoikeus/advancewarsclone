import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Selector {
	Texture selectorTex;
	int[] selectorLocation;
	int aTexSize;
	
	int width, height;
	
	public Selector(int aTS, int w, int h)
	{
		selectorLocation = new int[2];
		selectorLocation[0] = 5;
		selectorLocation[1] = 10;
		aTexSize = aTS;
		
		width = w;
		height = h;
		
		try {
			selectorTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/selector.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void drawSelector()
	{
		selectorTex.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		int i = selectorLocation[1];
		int j = selectorLocation[0];
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0 + j * aTexSize, aTexSize+ i * aTexSize);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(aTexSize + j * aTexSize,aTexSize + i * aTexSize);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(aTexSize + j * aTexSize,i * aTexSize);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(0 + j * aTexSize,i * aTexSize);
		GL11.glEnd();
	}
	
	public void moveSelector(int direction)
	{
		switch(direction)
		{
		case 0: selectorLocation[0]--;
			break;
		case 1: selectorLocation[1]++;
			break;
		case 2: selectorLocation[0]++;
			break;
		case 3: selectorLocation[1]--;
			break;
		}
		
		if(selectorLocation[0] < 0)
			selectorLocation[0] = 0;
		if(selectorLocation[0] > width-1)
			selectorLocation[0] = width-1;
		if(selectorLocation[1] < 0)
			selectorLocation[1] = 0;
		if(selectorLocation[1] > height-1)
			selectorLocation[1] = height-1;
	}
	
	public int getSelectorX()
	{
		return selectorLocation[0];
	}
	public int getSelectorY()
	{
		return selectorLocation[1];
	}
	
}
