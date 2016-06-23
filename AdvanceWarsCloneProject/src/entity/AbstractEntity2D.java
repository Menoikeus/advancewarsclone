package entity;

public abstract class AbstractEntity2D implements Entity2D{
	protected float x;
	protected float y;
	
	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public abstract void setUp();
	public abstract void destroy();
	public abstract void draw();
}
