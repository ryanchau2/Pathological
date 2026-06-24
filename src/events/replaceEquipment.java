package events;

import entity.Player;
import items.Equipment;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class replaceEquipment extends Event {
	private Player player;
	private BorderPane window;
	private Equipment equipment;
	private Equipment[] equipmentList;
	private int pathFloor;
	
	VBox replaceMainContainer = new VBox();
	Text replaceMessage = new Text("Choose a piece of equipment to replace");
	VBox equipmentContainer = new VBox(15);
	HBox equipmentRow1 = new HBox(20);
	HBox equipmentRow2 = new HBox(20);
	Button eq1 = new Button();
	Button eq2 = new Button();
	Button eq3 = new Button();
	Button eq4 = new Button();
	
	public replaceEquipment(Equipment equipment, int pathFloor, Player player, BorderPane window) {
		System.out.println("Time to replace");
		this.player = player;
		this.window = window;
		this.equipment = equipment;
		this.pathFloor = pathFloor;
		equipmentList = player.getEquipmentList();
		setupReplaceUI();
		styleReplaceUI();
		populateButtons();
		createReplaceEListeners();
	}
	private void createReplaceEListeners() {
		eq1.setOnAction(e->{
			player.replaceEquipment(0, equipment);
			new ChoosePath(window, pathFloor, player);
		});
		eq2.setOnAction(e->{
			player.replaceEquipment(1, equipment);
			new ChoosePath(window, pathFloor, player);
		});
		eq3.setOnAction(e->{
			player.replaceEquipment(2, equipment);
			new ChoosePath(window, pathFloor, player);
		});
		eq4.setOnAction(e->{
			player.replaceEquipment(3, equipment);
			new ChoosePath(window, pathFloor, player);
		});
		
		
	}
	private void populateButtons() {
		eq1.setText(equipmentList[0].getItemName());
		eq2.setText(equipmentList[1].getItemName());
		eq3.setText(equipmentList[2].getItemName());
		eq4.setText(equipmentList[3].getItemName());
	}
	private void setupReplaceUI() {
		System.out.println("In the funny one");
		window.setCenter(null);
		window.setBottom(null);
		equipmentRow1.getChildren().addAll(eq1, eq2);
		equipmentRow2.getChildren().addAll(eq3, eq4);
		equipmentContainer.getChildren().addAll(equipmentRow1, equipmentRow2);
		replaceMainContainer.getChildren().addAll(replaceMessage, equipmentContainer);
		window.setCenter(replaceMainContainer);
	}
	private void styleReplaceUI() {
		String btReplaceStyle = "-fx-font-size:20";
		int btReplaceWidth = 400;
		replaceMainContainer.setAlignment(Pos.CENTER);
		equipmentContainer.setAlignment(Pos.CENTER);
		equipmentRow1.setAlignment(Pos.CENTER);
		equipmentRow2.setAlignment(Pos.CENTER);
		eq1.setStyle(btReplaceStyle);
		eq2.setStyle(btReplaceStyle);
		eq3.setStyle(btReplaceStyle);
		eq4.setStyle(btReplaceStyle);
		
		eq1.prefWidth(btReplaceWidth);
		eq2.prefWidth(btReplaceWidth);
		eq3.prefWidth(btReplaceWidth);
		eq4.prefWidth(btReplaceWidth);
		
	}
}
