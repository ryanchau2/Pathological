package events;

import database.SQL_Db;
import entity.Player;
import items.Consumable;
import items.Equipment;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChoosePath {
	private BorderPane window;
	private int pathFloor;
	private Player newPlayer;
	
//	Top Path Level Bar
	private Text pathProgressText = new Text("");
	private HBox pathProgressBox = new HBox();
	
//	Choices Box
	private VBox centerPathBox = new VBox(20);
	private HBox caveViewContainer = new HBox();
	private HBox pathChoicesHBox = new HBox(20);
	ImageView caveView = new ImageView("file:images/sprites/cave.png");
	private SQL_Db database;
//	SaveQuit
	private VBox quitOptions = new VBox(20);
	private Button btSaveQuit = new Button("Save & Quit");
	private Button btQuitNoSave = new Button("Quit Without Saving");
	
	public ChoosePath(BorderPane window, int pathFloor, Player player) {
		this.pathFloor = pathFloor++;
		this.window=window;
		newPlayer = player;
		window.setBottom(null);
		window.setLeft(null);
		if(pathFloor != 1)
			window.setRight(quitOptions);
		displayPaths();
	}
	private void displayPaths() {
//		Paths randomly decided and generated
		int path1 = (int)(Math.random()*3)+1;								// s/b *3)+1
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
		caveViewContainer.getChildren().addAll(caveView);
		pathChoicesHBox.getChildren().addAll(btPath1, btPath2);
		centerPathBox.getChildren().addAll(caveViewContainer, pathChoicesHBox);
		window.setCenter(centerPathBox);
		pathChoicesHBox.setAlignment(Pos.CENTER);
		setPathButtonStyles(btPath1,btPath2);
//		Path Button Listeners
		pathButtonListeners(btPath1, btPath2, path1, path2);
		
		pathProgressText.setText("Path "+pathFloor+" Decision: Choose a Path to Traverse");
		pathProgressBox.getChildren().add(pathProgressText);
		window.setTop(pathProgressBox);
		
		quitOptions.getChildren().addAll(btSaveQuit, btQuitNoSave);
 	}
	private void pathButtonListeners(Button btPath1, Button btPath2, int path1, int path2) {
		btPath1.setOnAction(e->{
			eventCaller(path1);
		});
		btPath2.setOnAction(e->{
			eventCaller(path2);
		});
		btSaveQuit.setOnAction(e->{
			database = new SQL_Db();
			database.saveTempRun(newPlayer.getRunID(), newPlayer.getAtk(), newPlayer.getDef(), newPlayer.getMaxHP(), newPlayer.getMaxMP(), pathFloor, newPlayer.getCurrentHP(), newPlayer.getCurrentMP(), newPlayer.getEquipmentList(), newPlayer.getConsumableList(), newPlayer.getSkills());
			database.close();
			Platform.exit();
		});
		btQuitNoSave.setOnAction(e->{
			Platform.exit();
		});
	}
//	1 - Controls the flow of the Battle Event
	private void battleEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		new Battle(newPlayer, window, pathFloor);
	}
//	2 - Controls the Options of Rest Event
	private void restEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		new Rest(newPlayer, window, pathFloor);
	}
//	3 - Controls anything Treasure Event related
	private void treasureEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		new Treasure(newPlayer, window, pathFloor);
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
		String buttonStyle = "-fx-font-size:18";
		int buttonWidth = 225;
		caveViewContainer.setAlignment(Pos.CENTER);
		b1.setStyle(buttonStyle);
		b1.setPrefWidth(buttonWidth);
		b2.setStyle(buttonStyle);
		b2.setPrefWidth(buttonWidth);
		
		btSaveQuit.setStyle(buttonStyle);
		btSaveQuit.setPrefWidth(buttonWidth-100);
		quitOptions.setPadding(new Insets(0,20,0,0));
		
		String pathProgression = "-fx-font-size:28";
		pathProgressText.setStyle(pathProgression);
		pathProgressBox.setAlignment(Pos.CENTER);
		pathProgressBox.setPadding(new Insets(40, 0, 80, 0));
	}
}
