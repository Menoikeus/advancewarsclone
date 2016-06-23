package entity;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

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
	double health;
	
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
	
	public int[] getSpriteLocation()
	{
		return uType.getInfo();
	}
	
	public UnitType getUnitType()
	{
		return uType;
	}

	public double getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
	
	public void setHealth(double h) {
		// TODO Auto-generated method stub
		health = h;
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
