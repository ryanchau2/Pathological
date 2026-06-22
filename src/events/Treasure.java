package events;

import entity.Player;
import items.Equipment;
import items.Item;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Treasure extends Event{
	private Player player;
	private BorderPane window;
	private int floorLevel;
	
	HBox treasureItemDisplay = new HBox(20);
	VBox vb_item1 = new VBox();
	VBox vb_item2 = new VBox();
	VBox vb_item3 = new VBox();
	
	HBox treasureSelectUIBar = new HBox();
	Button btItem1 = new Button();
	Button btItem2 = new Button();
	Button btItem3 = new Button();
	Text txtItem1 = new Text("");
	Text txtItem2 = new Text("");
	Text txtItem3 = new Text("");
	
	Item treasure_i1;
	Item treasure_i2;
	Item treasure_i3;
	
	public Treasure(Player player, BorderPane window, int floorLevel) {
		this.player = player;
		this.window = window;
		this.floorLevel = floorLevel;
		
		compileTreasureWindow();
	}
	private void compileTreasureWindow() {
//		get 3 items randomly
		populateChest();
		treasureItemDisplay.getChildren().addAll(vb_item1, vb_item2, vb_item3);
		window.setCenter(treasureItemDisplay);
	}
	private void populateChest() {
		treasure_i1 = generateItems();
//		treasure_i2 = generateItems();
//		treasure_i3 = generateItems();
		System.out.println("Job done");
	}
	private Item generateItems() {
		int item_type = (int)(Math.random()*2)+1;
		Item returnItem = null;
		switch(item_type) {
		case 1:			//Equipment
//			Generate a random item from the Equipment table
			returnItem = new Equipment();
			break;
		case 2:			//Consumable
//			Generate a random item from the Item table
			returnItem = new Equipment(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<
			break;
		default:
			System.out.println("Generate Items out of bounds");
			break;
		}
		return returnItem;
	}
}
