
public class Unit {
	double health;
	UnitType type;
	
	public Unit(UnitType t)
	{
		health = 10;
		type = t;
	}
	
	public double getHealth()
	{
		return health;
	}
	public void setHealth(double h)
	{
		health = h;
	}
	
	public UnitType getType()
	{
		return type;
	}
}
