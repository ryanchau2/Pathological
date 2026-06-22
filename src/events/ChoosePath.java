package events;

import database.SQL_Db;
import entity.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ChoosePath {
	private BorderPane window;
	private int pathFloor;
	Player newPlayer;
	
	SQL_Db db;
	
//	Top Path Level Bar
	Text pathProgressText = new Text("pathProgressTopLOL");
	HBox pathProgressBox = new HBox();
//	Choices Box
	HBox pathChoicesHBox = new HBox(20);
	
	public ChoosePath(BorderPane window, int pathFloor, Player player) {
		this.pathFloor = pathFloor++;
		this.window=window;
		newPlayer = player;
		displayPaths();
	}
	private void displayPaths() {
//		Paths randomly decided and generated
		int path1 = (int)(Math.random()*3)+1;
		int path2;
//		Prevents both paths from being the same event
		do {
			path2 = (int)(Math.random()*3)+1;
		}while(path2==path1);
//		1 = Battle // 2 = Rest // 3 = Treasure
		String path1Text = pathTextSetter(path1);
		String path2Text = pathTextSetter(path2);
//		Create Paths Buttons
		Button btPath1 = new Button(path1Text);
		Button btPath2 = new Button(path2Text);
		pathChoicesHBox.getChildren().addAll(btPath1, btPath2);
		window.setCenter(pathChoicesHBox);
		pathChoicesHBox.setAlignment(Pos.CENTER);
		setPathButtonStyles(btPath1,btPath2);
//		Path Button Listeners
		pathButtonListeners(btPath1, btPath2, path1, path2);
		pathProgressBox.getChildren().add(pathProgressText);
		window.setTop(pathProgressBox);
 	}
	private void pathButtonListeners(Button btPath1, Button btPath2, int path1, int path2) {
		btPath1.setOnAction(e->{
			System.out.println(path1);
			eventCaller(path1);
		});
		btPath2.setOnAction(e->{
			System.out.println(path2);
			eventCaller(path2);
		});
	}
//	1 - Controls the flow of the Battle Event
	private void battleEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		System.out.println("Battle has been chosen!");
		new Battle(newPlayer, window, pathFloor);
	}
//	2 - Controls the Options of Rest Event
	private void restEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		System.out.println("time to eep");
	}
//	3 - Controls anything Treasure Event related
	private void treasureEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		new Treasure(newPlayer, window, pathFloor);
////		Pull up 3 items to pick from
//		Object[] treasureItems = new Object[3];
////		Pull t
//		for(int x=0; x<treasureItems.length; x++) {
//			treasureItems[x] = selectThreeItems();
//		}
//		System.out.println("fre money!");
	}
	private Object selectThreeItems() {
		int eq_cons_decider = (int)(Math.random()*2)+1;
		int tableSize = -1;
		switch(eq_cons_decider) {
		case 1:
//			pick a random equipment
//			tableSize = db.countRows("equipment");
			int eqID_selector = (int)(Math.random()*tableSize)+1;
//			Create a new object with that item
		case 2:
//			pick a random consumable
//			tableSize = db.countRows("consumable");
			int conID_selector = (int)(Math.random()*tableSize)+1;
		default:
			System.out.println("Out of bounds item selector error");
		}
		return null;
	}
//	Controls which event will show on screen based on player selection
	private void eventCaller(int path) {
		switch(path) {
		case 1:
			battleEvent();
			return;
		case 2:
			restEvent();
			return;
		case 3:
			treasureEvent();
			return;
		default:
			System.out.println("Something went wrong in the eventCaller");
			return;
		}
	}
	private String pathTextSetter(int path) {
		switch(path) {
		case 1:
			return "Battle";
		case 2:
			return "Rest";
		case 3:
			return "Treasure";
		default:
			return "NULL";
		}
	}
	private void setPathButtonStyles(Button b1, Button b2) {
		//Styling
		String buttonStyle = "-fx-font-size:20";
		int buttonWidth = 250;
		b1.setStyle(buttonStyle);
		b1.setPrefWidth(buttonWidth);
		b2.setStyle(buttonStyle);
		b2.setPrefWidth(buttonWidth);
		
		String pathProgression = "-fx-font-size:28";
		pathProgressText.setStyle(pathProgression);
		pathProgressBox.setAlignment(Pos.CENTER);
	}
}
