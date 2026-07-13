package entity;

public class Enemy extends Entity{
	private double diffModifier;
	private int floorLevel;
	public Enemy(int floor) {
		floorLevel = floor;
		setStats();
		setEntity_sprite("file:images/sprites/enemy_test1.png");
		returnCurrentStats();
		
	}
	private void setStats() {
//		diffModifier=floorLevel*0.35;				//floor modifier*.6
		diffModifier=floorLevel*5;
		setAtk((int)((10-5)*diffModifier));
		setMaxHP((int)(20*diffModifier));
		setMaxMP((int)(15*diffModifier));
		setCurrentHP(getMaxHP());
		setCurrentMP(getMaxMP());
	}
}
