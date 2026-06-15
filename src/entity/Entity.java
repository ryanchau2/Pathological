package entity;

public class Entity {
	private int atk;
	private int def;
	private int maxHP;
	private int maxMP;
	
	private int currentHP;
	private int currentMP;
	public Entity() {
		setStats();
	}
	private void setStats() {
		atk=10;
		def=5;
		maxHP=20;
		maxMP=15;
		currentHP=maxHP;
		currentMP=maxMP;
	}
//	=======================================================================
//	getters and setters
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getMaxMP() {
		return maxMP;
	}
	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	public int getCurrentMP() {
		return currentMP;
	}
	public void setCurrentMP(int currentMP) {
		this.currentMP = currentMP;
	}
	
}
