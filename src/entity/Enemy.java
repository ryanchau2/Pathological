package entity;

public class Enemy extends Entity{
	private double diffModifier;
	public Enemy() {
		diffModifier=getFloorLevel()*0.6;
		adjustEnemyStats();
		returnCurrentStats();
		
	}
	private void adjustEnemyStats() {
		setMaxHP((int)(diffModifier*getMaxHP()));
		setMaxMP((int)(diffModifier*getMaxMP()));
	}
}
