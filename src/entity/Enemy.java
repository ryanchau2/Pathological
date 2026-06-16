package entity;

public class Enemy extends Entity{
	private double diffModifier;
	public Enemy() {
		setStats();
		setEntity_sprite("file:images/sprites/enemy_test.png");
		returnCurrentStats();
		
	}
	private void setStats() {
		diffModifier=1*0.6;				//floor modifier*.6
		setAtk((int)(10*diffModifier));
		setDef((int)(5*diffModifier));
		setMaxHP((int)(20*diffModifier));
		setMaxMP((int)(15*diffModifier));
		setCurrentHP(getMaxHP());
		setCurrentMP(getMaxMP());
	}
}
