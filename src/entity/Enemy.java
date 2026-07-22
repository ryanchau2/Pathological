package entity;

public class Enemy extends Entity{
	private double diffModifier;
	private int floorLevel;
	public Enemy(int floor) {
		floorLevel = floor;
		setStats();
		setEntity_sprite("file:images/sprites/enemy_idle.gif");
		returnCurrentStats();
		
	}
	private void setStats() {
		diffModifier=floorLevel*0.35;				//floor modifier*.6
		setAtk((int)((5)*diffModifier+5));
		setDef((int)(2*diffModifier+2));
		setMaxHP((int)(20*diffModifier+4));
		setMaxMP((int)(15*diffModifier));
		setCurrentHP(getMaxHP());
		setCurrentMP(getMaxMP());
	}
	public String changeAttackSprite() {
		return "file:images/sprites/enemy_attack.gif";
	}
	public String changeIdleSprite() {
		return "file:images/sprites/enemy_idle.gif";
	}
	public String changeDeadSprite() {
		return "file:images/sprites/enemy_dead.gif";
	}
}
