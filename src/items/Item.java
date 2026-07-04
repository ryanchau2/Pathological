package items;

public class Item {
	private int item_id;
	protected String item_name;
	protected String item_description;

	private String item_sprite;
	public Item() {
		
	}
	public String getItemName() {
		return item_name;
	}
	public String getItemDesc() {
		return item_description;
	}
	public String getItem_sprite() {
		return item_sprite;
	}
	public void setItem_sprite(String item_sprite) {
		this.item_sprite = item_sprite;
	}
}
