package items;

public class Consumable extends Item{
	private String consumableName;
	private String consumableDesc;
	
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
