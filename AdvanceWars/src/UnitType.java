
public enum UnitType {
	INFANTRY("infantry", 4.0, 1.0, .5, 0.0, 3, false, false), 
	MECH("mech", 5.0, 1.2, 1.1, .05, 2, false, false),
	RECON("recon", 5.0, 1.6, .7, .10, 6, true, true), 
	TANK("tank", 6.0, 1.1, 1.2, .20, 4, true, true);
	
	private String name;
	private double strength;
	private double infModifier, armorModifier;
	private double damageArmor;
	private int moveCount;
	private boolean isVehicle, isArmored;
	
	private UnitType(String n, double s, double infM, double armorM, double dA, int mC, boolean iV, boolean iA)
	{
		name = n;
		this.strength = s;
		this.infModifier = infM;
		this.armorModifier = armorM;
		this.damageArmor = dA;
		this.moveCount = mC;
		this.isVehicle = iV;
		this.isArmored = iA;
	}
	
	public String getName()
	{
		return name;
	}
	public double getStrength()
	{
		return strength;
	}
	public double getInfModifier()
	{
		return infModifier;
	}
	public double getArmorModifier()
	{
		return armorModifier;
	}
	public double getDamageArmor()
	{
		return damageArmor;
	}
	public double getMoveCount()
	{
		return moveCount;
	}
	public boolean isVehicle()
	{
		return isVehicle;
	}
	public boolean isArmored()
	{
		return isArmored;
	}
}
