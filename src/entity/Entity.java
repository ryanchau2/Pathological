package entity;

import javafx.scene.text.Text;

public class Entity {
	private int atk;
	private int def;
	private int maxHP;
	private int maxMP;
	
	private int currentHP;
	private int currentMP;
	
	private String entity_sprite;
	
	public Entity() {
		
	}
	public void returnCurrentStats() {
		System.out.printf("%-12s", "Current HP");
		System.out.printf("%-12s", "Current MP");
		System.out.printf("%-12s", "Max HP");
		System.out.printf("%-12s", "Max MP");
		System.out.printf("%-12s", "Atk");
		System.out.printf("%-12s", "Def");
		System.out.println();
		System.out.printf("%-12s", currentHP);
		System.out.printf("%-12s", currentMP);
		System.out.printf("%-12s", maxHP);
		System.out.printf("%-12s", maxMP);
		System.out.printf("%-12s", atk);
		System.out.printf("%-12s", def);
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
	public Text display_HPStat() {
		return new Text(String.valueOf(this.getCurrentHP())+"/"+String.valueOf(this.getMaxHP()));
	}
	public Text display_MPStat() {
		return new Text(String.valueOf(this.getCurrentMP())+"/"+String.valueOf(this.getMaxMP()));
	}
	public String getEntity_sprite() {
		return entity_sprite;
	}
	public void setEntity_sprite(String entity_sprite) {
		this.entity_sprite = entity_sprite;
	}
	
}
