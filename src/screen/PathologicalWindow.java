package screen;

import database.SQL_Db;
import entity.Enemy;
import entity.Entity;
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
	HBox battleBoxContainer = new HBox(200);
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
	SQL_Db db;
	
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
//	1 - Controls the flow of the Battle Event
	private void battleEvent() {
		pathFloor++;
		pathChoicesHBox.getChildren().clear();
		System.out.println("Battle has been chosen!");
		
//		Retrieves Player Information
		VBox playerStats = new VBox();
		Text player_HP = newPlayer.display_HPStat();
		Text player_MP = newPlayer.display_MPStat();
		playerStats.getChildren().addAll(player_HP, player_MP);
		playerImageContainer.getChildren().add(new ImageView(newPlayer.getEntity_sprite()));
		playerContainer.getChildren().addAll(playerImageContainer,playerStats);
		
//		Retrieves Enemy Information
		Enemy enemy = new Enemy();
		VBox enemyStats = new VBox();
		Text enemy_HP = enemy.display_HPStat();
		Text enemy_MP = enemy.display_MPStat();
		enemyStats.getChildren().addAll(enemy_HP, enemy_MP);
		enemyImageContainer.getChildren().add(new ImageView(enemy.getEntity_sprite()));
		enemyContainer.getChildren().addAll(enemyImageContainer,enemyStats);
		
		battleBoxContainer.getChildren().addAll(playerContainer, enemyContainer);
		this.setCenter(battleBoxContainer);
		
//		Container Styles
		styleBattleEntityContainers(playerStats, enemyStats, player_HP, player_MP, enemy_HP, enemy_MP);
		
		while()
		
	}
	private void styleBattleEntityContainers(VBox entity1, VBox entity2, Text e1_hpStat, Text e1_mpStat, Text e2_hpStat, Text e2_mpStat){
		battleBoxContainer.setAlignment(Pos.CENTER);
		entity1.setAlignment(Pos.CENTER);
		entity2.setAlignment(Pos.CENTER);
		e1_hpStat.setStyle("-fx-font-size:20");
		e1_mpStat.setStyle("-fx-font-size:20");
		e2_hpStat.setStyle("-fx-font-size:20");
		e2_mpStat.setStyle("-fx-font-size:20");
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
//		Pull up 3 items to pick from
		Object[] treasureItems = new Object[3];
//		Pull t
		for(int x=0; x<treasureItems.length; x++) {
			treasureItems[x] = selectThreeItems();
		}
		System.out.println("fre money!");
	}
	private Object selectThreeItems() {
		int eq_cons_decider = (int)(Math.random()*2)+1;
		int tableSize = -1;
		switch(eq_cons_decider) {
		case 1:
//			pick a random equipment
			tableSize = db.countRows("equipment");
			int eqID_selector = (int)(Math.random()*tableSize)+1;
//			Create a new object with that item
		case 2:
//			pick a random consumable
			tableSize = db.countRows("consumable");
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
