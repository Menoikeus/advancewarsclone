package entity;

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


public class Unit extends AbstractEntity2D{
	UnitType uType;
	int health;
	
	public Unit(UnitType u) {
		uType = u;
		health = 10;
	}
	
	public Unit()
	{
		this.x = this.y = 0;
		health = 10;
		uType = UnitType.INFANTRY;
	}
	
	@Override
	public void setUp() {
	}

	@Override
	public void destroy() {	
	}

	@Override
	public void draw() {
	}
	
	public int[] getSpriteLocation()
	{
		return uType.getInfo();
	}
	
}
