package events;

import entity.Player;
import items.Consumable;
import items.Equipment;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ReplaceConsumable {
	private Player player;
	private BorderPane window;
	private Consumable consumable;
	private Consumable[] consumableList;
	private int pathFloor;
	
	VBox replaceMainContainer = new VBox();
	Text replaceMessage = new Text("Choose a Consumable to replace");
	VBox consumableContainer = new VBox(15);
	HBox consumableRow1 = new HBox(20);
	HBox consumableRow2 = new HBox(20);
	HBox consumableRow3 = new HBox(20);
	Button cs1 = new Button();
	Button cs2 = new Button();
	Button cs3 = new Button();
	Button cs4 = new Button();
	Button cs5 = new Button();
	Button cs6 = new Button();
	Button cs7 = new Button();
	Button cs8 = new Button();
	Button cs9 = new Button();
	Button cs10 = new Button();
	public ReplaceConsumable(Consumable consumable, int pathFloor, Player player, BorderPane window) {
		this.player = player;
		this.window = window;
		this.consumable = consumable;
		this.pathFloor = pathFloor;
		consumableList = player.getConsumableList();
		populateButtons();
		setupReplaceUI();
		styleReplaceUI();
	}
	private void populateButtons() {
		cs1.setText(consumableList[0].getItemName());
		cs2.setText(consumableList[1].getItemName());
		cs3.setText(consumableList[2].getItemName());
		cs4.setText(consumableList[3].getItemName());
		cs5.setText(consumableList[4].getItemName());
		cs6.setText(consumableList[5].getItemName());
		cs7.setText(consumableList[6].getItemName());
		cs8.setText(consumableList[7].getItemName());
		cs9.setText(consumableList[8].getItemName());
		cs10.setText(consumableList[9].getItemName());
	}
	private void setupReplaceUI() {
		window.setCenter(null);
		window.setBottom(null);
		consumableRow1.getChildren().addAll(cs1, cs2, cs3, cs4);
		consumableRow2.getChildren().addAll(cs5, cs6, cs7, cs8);
		consumableRow3.getChildren().addAll(cs9,cs10);
		consumableContainer.getChildren().addAll(consumableRow1, consumableRow2, consumableRow3);
		replaceMainContainer.getChildren().addAll(replaceMessage, consumableContainer);
		window.setCenter(replaceMainContainer);
	}
	private void styleReplaceUI() {
		String btReplaceStyle = "-fx-font-size:20";
		int btReplaceWidth = 400;
		replaceMainContainer.setAlignment(Pos.CENTER);
		consumableContainer.setAlignment(Pos.CENTER);
		consumableRow1.setAlignment(Pos.CENTER);
		consumableRow2.setAlignment(Pos.CENTER);
		consumableRow3.setAlignment(Pos.CENTER);
		cs1.setStyle(btReplaceStyle);
		cs2.setStyle(btReplaceStyle);
		cs3.setStyle(btReplaceStyle);
		cs4.setStyle(btReplaceStyle);
		cs5.setStyle(btReplaceStyle);
		cs6.setStyle(btReplaceStyle);
		cs7.setStyle(btReplaceStyle);
		cs8.setStyle(btReplaceStyle);
		cs9.setStyle(btReplaceStyle);
		cs10.setStyle(btReplaceStyle);
		
		cs1.prefWidth(btReplaceWidth);
		cs2.prefWidth(btReplaceWidth);
		cs3.prefWidth(btReplaceWidth);
		cs4.prefWidth(btReplaceWidth);
		cs5.prefWidth(btReplaceWidth);
		cs6.prefWidth(btReplaceWidth);
		cs7.prefWidth(btReplaceWidth);
		cs8.prefWidth(btReplaceWidth);
		cs9.prefWidth(btReplaceWidth);
		cs10.prefWidth(btReplaceWidth);
		
		
	}
}
