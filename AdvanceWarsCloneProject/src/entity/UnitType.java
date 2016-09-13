package entity;

public enum UnitType {
	INFANTRY("infantry", 4.0, 1.0, .5, 0.0, 3, false, false, 0, 0, 0, 1), 
	MECH("mech", 5.0, 1.2, 1.5, .05, 2, false, false, 16, 0, 0, 1),
	RECON("recon", 5.0, 1.6, .7, .10, 6, true, true, 32, 0, 0, 1), 
	TANK("tank", 6.0, 1.1, 1.2, .20, 4, true, true, 48, 0, 0, 1),
	ARTILLERY("artillery", 6.0, 1.2, 1.2, .10, 3, true, true, 64, 0, 1, 5);
	
	private String name;
	private double strength;
	private double infModifier, armorModifier;
	private double damageArmor;
	private int moveCount;
	private boolean isVehicle, isArmored;
	private int textureX, textureY;
	private int minAttackRange, maxAttackRange;
	
	private UnitType(String n, double s, double infM, double armorM, double dA, int mC, boolean iV, boolean iA, int tX, int tY, int minAR, int maxAR)
	{
		name = n;
		this.strength = s;
		this.infModifier = infM;
		this.armorModifier = armorM;
		this.damageArmor = dA;
		this.moveCount = mC;
		this.isVehicle = iV;
		this.isArmored = iA;
		this.textureX = tX;
		this.textureY = tY;
		this.minAttackRange = minAR;
		this.maxAttackRange = maxAR;
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
	
	public int[] getInfo()
	{
		int[] texLocation = new int[2];
		texLocation[0] = textureX;
		texLocation[1] = textureY;
		
		return texLocation;
	}
	
	public int getMinAttackRange()
	{
		return minAttackRange;
	}
	public int getMaxAttackRange()
	{
		return maxAttackRange;
	}
}
