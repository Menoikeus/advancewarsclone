import entity.*;

public class Calculator {
	public static void fight(Unit attacker, Unit defender)
	{
		//int attackerHealth = attacker.getHealth();
		//int defenderHealth = defender.getHealth();
		
		UnitType aType = attacker.getUnitType();
		UnitType dType = defender.getUnitType();
		
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
