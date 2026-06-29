package events;

import entity.Player;
import items.Consumable;
import items.Equipment;
import items.Item;
import javafx.application.Platform;
import javafx.geometry.Insets;
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
	private Text pathProgressText = new Text("");
	private HBox pathProgressBox = new HBox();
	
	private HBox treasureItemDisplay = new HBox(20);
	private VBox vb_item1 = new VBox();
	private VBox vb_item2 = new VBox();
	private VBox vb_item3 = new VBox();
	
	private HBox treasureSelectUIBar = new HBox(30);
	private Button btItem1 = new Button();
	private Button btItem2 = new Button();
	private Button btItem3 = new Button();
	private Text txtItem1 = new Text("");
	private Text txtItem2 = new Text("");
	private Text txtItem3 = new Text("");
	
	private Item treasure_i1;
	private Item treasure_i2;
	private Item treasure_i3;
	
	public Treasure(Player player, BorderPane window, int pathFloor) {
		this.player = player;
		this.window = window;
		this.pathFloor = pathFloor;
		updatePathFloor();
		enableButtons();
		compileTreasureWindow();
		createTreasureButtonListeners();
	}
	private void updatePathFloor() {
		pathProgressText.setText("Path "+pathFloor+": Treasure");
		pathProgressBox.getChildren().addAll(pathProgressText);
		window.setTop(pathProgressBox);
	}
	private void createTreasureButtonListeners() {
		btItem1.setOnAction(e->{
			addToInventory(treasure_i1);
			btItem2.setDisable(true);
			btItem3.setDisable(true);
			window.setBottom(null);
//			new ChoosePath(window, pathFloor, player);
		});
		btItem2.setOnAction(e->{
			addToInventory(treasure_i2);
			btItem1.setDisable(true);
			btItem3.setDisable(true);
			window.setBottom(null);
//			new ChoosePath(window, pathFloor, player);
		});
		btItem3.setOnAction(e->{
			addToInventory(treasure_i3);
			btItem1.setDisable(true);
			btItem2.setDisable(true);
			window.setBottom(null);
//			new ChoosePath(window, pathFloor, player);
		});
	}
	private void compileTreasureWindow() {
//		get 3 items randomly
		populateChest();
//		Shows the items at the top
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
	private void addToInventory(Item item) {
		if(item instanceof Equipment) {
			if(player.getEquipmentTotal() != player.getEquipmentList().length) {
				player.addToEquipment((Equipment)item, window);
				new ChoosePath(window, pathFloor, player);
			}
			else {
				new ReplaceEquipment((Equipment)item, pathFloor, player, window);
			}
		}
		else if(item instanceof Consumable) {
			if(player.getConsumableTotal() != player.getConsumableList().length) {
				player.addToConsumables((Consumable)item, window);
				new ChoosePath(window, pathFloor, player);
			}
			else {
				new ReplaceConsumable((Consumable)item, pathFloor, player, window);
			}
		}
		else
			System.out.println();
	}
	private void enableButtons() {
		btItem1.setDisable(false);
		btItem2.setDisable(false);
		btItem3.setDisable(false);
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
		treasureSelectUIBar.setPadding(new Insets(0,0,40,0));
		String pathProgression = "-fx-font-size:28";
		pathProgressText.setStyle(pathProgression);
		pathProgressBox.setAlignment(Pos.CENTER);
	}
}
