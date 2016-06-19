
public class AdvanceWars {

	public static void main(String[] args) {
		Unit first = new Unit(UnitType.MECH);
		Unit second = new Unit(UnitType.TANK);
		
		fight(first, second);
		fight(first, second);
	}
	public static void fight(Unit attacker, Unit defender)
	{
		//int attackerHealth = attacker.getHealth();
		//int defenderHealth = defender.getHealth();
		
		UnitType aType = attacker.getType();
		UnitType dType = defender.getType();
		
		double attackerDamage = aType.getStrength() * (dType.isArmored() ? aType.getArmorModifier() : aType.getInfModifier()) * (1.0 - dType.getDamageArmor()) * 1.1;
		double defenderDamage = dType.getStrength() * (aType.isArmored() ? dType.getArmorModifier() : dType.getInfModifier()) * (1.0 - aType.getDamageArmor()) * .8;
	
		attacker.setHealth(attacker.getHealth() - defenderDamage);
		defender.setHealth(defender.getHealth() - attackerDamage);
		
		System.out.println("Attacker health: " + attacker.getHealth());
		System.out.println("Defender health: " + defender.getHealth());
		
		//yoyo
		//woah
		//taku
	}
}
