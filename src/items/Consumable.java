package items;

import database.SQL_Db;

public class Consumable extends Item{
	private String consumableName;
	private String consumableDesc;
	private SQL_Db database = new SQL_Db();
	@Override
	public String getItemName() {
		return consumableName;
	}

	@Override
	public String getItemDesc() {
		return consumableDesc;
	}

	@Override
	public String getItemImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
