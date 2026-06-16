package screen;

import entity.Enemy;
import entity.Player;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PathologicalWindow extends BorderPane{
	String windowText;
	
	ImageView ivTitleLogo = new ImageView("file:images/pathological_logo.png");
	
	VBox titleLogo = new VBox();
	VBox mainMenuButtons = new VBox(10);
	
	private Button btStart = new Button("Start");
	private Button btPrevRuns = new Button("Previous Runs");
	private Button btExit = new Button("Exit");
	
	
	private int pathFloor = 0;
//	Top Path Level Bar
	Text pathProgressText = new Text("pathProgressTopLOL");
	HBox pathProgressBox = new HBox();
//	Choices Box
	HBox pathChoicesHBox = new HBox(20);
	
	Player newPlayer;
//	===============================================================
//	Battle
//	Battle UI elements (Contains the player and enemy container)
	HBox battleBoxContainer = new HBox(20);
//	Player Elements (Contains player visual attributes)
	VBox playerContainer = new VBox(5);
	HBox playerImageContainer = new HBox();
	HBox playerHPContainer = new HBox();
	HBox playerMPContainer = new HBox();
//	Enemy Elements (Contains enemy visual attributes)
	VBox enemyContainer = new VBox(5);
	HBox enemyImageContainer = new HBox();
	HBox enemyHPContainer = new HBox();
	HBox enemyMPContainer = new HBox();
//	===============================================================
	
	public PathologicalWindow() {
		windowText = "Pathological";
		
		titleLogo.getChildren().add(ivTitleLogo);
		
		displayMainMenu();
		
		this.setTop(titleLogo);
		this.setCenter(mainMenuButtons);
		this.setLeft(null);
		this.setBottom(null);
		setMenuStyles();
		show();
	}
	private void startGame() {
		newPlayer = new Player();
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
		this.setCenter(pathChoicesHBox);
		pathChoicesHBox.setAlignment(Pos.CENTER);
		setPathButtonStyles(btPath1,btPath2);
//		Path Button Listeners
		pathButtonListeners(btPath1, btPath2, path1, path2);
		pathProgressBox.getChildren().add(pathProgressText);
		this.setTop(pathProgressBox);
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
//	1
	private void battleEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		System.out.println("Battle has been chosen!");
		
		playerImageContainer.getChildren().add(new ImageView(newPlayer.getEntity_sprite()));
		playerContainer.getChildren().addAll(playerImageContainer);
		
		Enemy enemy = new Enemy();
		enemyImageContainer.getChildren().add(new ImageView(enemy.getEntity_sprite()));
		enemyContainer.getChildren().addAll(enemyImageContainer);
		battleBoxContainer.getChildren().addAll(playerContainer, enemyContainer);
		this.setCenter(battleBoxContainer);
	}
//	2
	private void restEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		System.out.println("time to eep");
	}
//	3
	private void treasureEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		System.out.println("fre money!");
	}
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
	private void displayMainMenu() {
		mainMenuButtons.getChildren().addAll(btStart,btPrevRuns,btExit);
		createMenuListeners();
	}
	private void createMenuListeners() {
		btStart.setOnAction(e->{
			mainMenuButtons.getChildren().clear();
			titleLogo.getChildren().clear();
			startGame();
		});
		
//		btSeePastRuns.setOnAction(e->{
//		});
		
		btExit.setOnAction(e->{
		Platform.exit();
		});
	}
	private void setMenuStyles() {
		//Styling
		String buttonStyle = "-fx-font-size:28";
		int buttonWidth = 250;
		btStart.setStyle(buttonStyle);
		btStart.setPrefWidth(buttonWidth);
		btPrevRuns.setStyle(buttonStyle);
		btPrevRuns.setPrefWidth(buttonWidth);
		btExit.setStyle(buttonStyle);
		btExit.setPrefWidth(buttonWidth);
		mainMenuButtons.setAlignment(Pos.CENTER);
		titleLogo.setAlignment(Pos.CENTER);
	}
	private void show() {
		Stage stage = new Stage();
		Scene scene = new Scene(this, 1024, 768);
		stage.setTitle(windowText);
		stage.setScene(scene);
		stage.show();
	}
}
