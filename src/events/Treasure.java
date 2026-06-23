package events;

import entity.Player;
import items.Consumable;
import items.Equipment;
import items.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Treasure extends Event{
	private Player player;
	private BorderPane window;
	private int pathFloor;
	
//	Top Path Level Bar
	Text pathProgressText = new Text("");
	HBox pathProgressBox = new HBox();
	
	HBox treasureItemDisplay = new HBox(20);
	VBox vb_item1 = new VBox();
	VBox vb_item2 = new VBox();
	VBox vb_item3 = new VBox();
	
	HBox treasureSelectUIBar = new HBox(40);
	Button btItem1 = new Button();
	Button btItem2 = new Button();
	Button btItem3 = new Button();
	Text txtItem1 = new Text("");
	Text txtItem2 = new Text("");
	Text txtItem3 = new Text("");
	
	Item treasure_i1;
	Item treasure_i2;
	Item treasure_i3;
	
	public Treasure(Player player, BorderPane window, int pathFloor) {
		this.player = player;
		this.window = window;
		this.pathFloor = pathFloor;
		
		compileTreasureWindow();
		createTreasureButtonListeners();
	}
	private void createTreasureButtonListeners() {
		btItem1.setOnAction(e->{
			
		});
		btItem2.setOnAction(e->{
			
		});
		btItem3.setOnAction(e->{
			
		});
	}
	private void compileTreasureWindow() {
//		get 3 items randomly
		populateChest();
		treasureItemDisplay.getChildren().addAll(vb_item1, vb_item2, vb_item3);
		styleTreasureContainers();
		
		createTreasureBar();
		window.setCenter(treasureItemDisplay);
	}
	private void createTreasureBar() {
		btItem1.setText(treasure_i1.getItemName());
		btItem2.setText(treasure_i2.getItemName());
		btItem3.setText(treasure_i3.getItemName());
		treasureSelectUIBar.getChildren().addAll(btItem1, btItem2, btItem3);
		window.setBottom(treasureSelectUIBar);
	}
	private void populateChest() {
		treasure_i1 = generateItems();
		txtItem1.setText(treasure_i1.getItemName());
		vb_item1.getChildren().add(txtItem1);
		
		treasure_i2 = generateItems();
		txtItem2.setText(treasure_i2.getItemName());
		vb_item2.getChildren().add(txtItem2);
		
		treasure_i3 = generateItems();
		txtItem3.setText(treasure_i3.getItemName());
		vb_item3.getChildren().add(txtItem3);
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
			returnItem = new Consumable(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<Consumable
			break;
		default:
			System.out.println("Generate Items out of bounds");
			break;
		}
		return returnItem;
	}
	private void styleTreasureContainers(){
		String btStyle = "-fx-font-size:30";
		int buttonWidth = 300;
		treasureItemDisplay.setAlignment(Pos.CENTER);
		treasureSelectUIBar.setAlignment(Pos.CENTER);
		btItem1.setStyle(btStyle);
		btItem2.setStyle(btStyle);
		btItem3.setStyle(btStyle);
		btItem1.setPrefWidth(buttonWidth);
		btItem2.setPrefWidth(buttonWidth);
		btItem3.setPrefWidth(buttonWidth);

		String pathProgression = "-fx-font-size:28";
		pathProgressText.setStyle(pathProgression);
		pathProgressBox.setAlignment(Pos.CENTER);
	}
}
