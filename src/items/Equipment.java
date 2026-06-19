package items;

public class Equipment extends Item {
	private String equipmentName;
	private String equipmentDesc;
	
	private int eq_HP;
	private int eq_atk;
	private int eq_def;
	
	public Equipment() {
		//pull name and desc from SQL based on 
	}
	
	public int getEq_HP() {
		return eq_HP;
	}

	public void setEq_HP(int eq_HP) {
		this.eq_HP = eq_HP;
	}

	public int getEq_atk() {
		return eq_atk;
	}

	public void setEq_atk(int eq_atk) {
		this.eq_atk = eq_atk;
	}

	public int getEq_def() {
		return eq_def;
	}

	public void setEq_def(int eq_def) {
		this.eq_def = eq_def;
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getItemDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getItemImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
