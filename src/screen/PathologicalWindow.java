package screen;

import database.SQL_Db;
import entity.Player;
import events.ChoosePath;
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
	
//	Previous Runs UI
	Button btBack = new Button("Back");			//set left
	VBox prevRunContainer = new VBox();
	HBox pRun1 = new HBox();
	VBox run1Floor = new VBox();
	VBox run1Atk = new VBox();
	VBox run1Def = new VBox();
	VBox run1HP = new VBox();
	VBox run1MP = new VBox();
	VBox run1Equipment = new VBox();
	VBox run1Consumables = new VBox();
	
	VBox pRun2 = new VBox();
	VBox pRun3 = new VBox();
	VBox pRun4 = new VBox();
	Button btNewer = new Button();
	Button btOlder = new Button();
	
	
	private int pathFloor;
	Player newPlayer;
	SQL_Db database;
//	===============================================================
	
	
	public PathologicalWindow() {
		windowText = "Pathological";
		displayMainMenu();
		buildPrevRunMenu();
		
		this.setTop(titleLogo);
		this.setCenter(mainMenuButtons);
		this.setLeft(null);
		this.setBottom(null);
		setMenuStyles();
		show();
	}
	public PathologicalWindow(String x) {
		displayMainMenu();
		setMainMenu();
		this.setTop(titleLogo);
		this.setCenter(mainMenuButtons);
		this.setLeft(null);
		this.setRight(null);
		this.setBottom(null);
		setMenuStyles();
	}
	private void startGame() {
		newPlayer = new Player();
		pathFloor = 0;
		new ChoosePath(this, pathFloor, newPlayer);
	}
	private void displayMainMenu() {
		titleLogo.getChildren().add(ivTitleLogo);
		mainMenuButtons.getChildren().addAll(btStart,btPrevRuns,btExit);
		createMenuListeners();
	}
	private void setMainMenu() {
		this.setTop(titleLogo);
		this.setCenter(mainMenuButtons);
		this.setLeft(null);
		this.setRight(null);
		this.setBottom(null);
	}
	private void createMenuListeners() {
		btStart.setOnAction(e->{
			mainMenuButtons.getChildren().clear();
			titleLogo.getChildren().clear();
			startGame();
		});
		btPrevRuns.setOnAction(e->{
			this.setTop(null);
			populateRuns();
			prevRunMenu();
		});
		btExit.setOnAction(e->{
		Platform.exit();
		});
	}
	private void populateRuns() {
		database = new SQL_Db();
		String[] x = database.getPlayerRun(1);
		database.close();
	}
	private void prevRunMenu() {
		this.setLeft(btBack);
		this.setCenter(prevRunContainer);
	}
	private void buildPrevRunMenu() {
		createPrevMenuBtListeners();
		//create way to retrieve runs
		prevRunContainer.getChildren().addAll(pRun1, pRun2, pRun3, pRun4);
	}
	private void createPrevMenuBtListeners() {
		btBack.setOnAction(e->{
			setMainMenu();
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
