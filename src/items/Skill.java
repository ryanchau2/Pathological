package items;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.SQL_Db;

public class Skill {
	private int skill_id;
	private String skill_name;
	private String skill_desc;
	private double skill_dmg_mod;
	private int mp_cost;
	
	private SQL_Db database;
	private ResultSet skillSet;
	
	public Skill(int id) {
		database = new SQL_Db();
		skillSet = database.createSkill(id);
		try {
			while(skillSet.next()) {
				skill_id = skillSet.getInt("skill_id");
				skill_name = skillSet.getString("skill_name");
				skill_desc = skillSet.getString("skill_description");
				skill_dmg_mod = skillSet.getDouble("skill_modifier");
				mp_cost = skillSet.getInt("mp_cost");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong creating the Skill");
		}
	}
	public int getSkill_id() {
		return skill_id;
	}

	public String getSkill_name() {
		return skill_name;
	}

	public String getSkill_desc() {
		return skill_desc;
	}

	public double getSkill_dmg_mod() {
		return skill_dmg_mod;
	}

	public int getMp_cost() {
		return mp_cost;
	}
}
