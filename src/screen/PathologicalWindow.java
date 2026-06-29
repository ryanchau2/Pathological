package screen;

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
	
	
	private int pathFloor;
	Player newPlayer;
//	===============================================================
	
	
	public PathologicalWindow() {
		windowText = "Pathological";
		displayMainMenu();
		
		this.setTop(titleLogo);
		this.setCenter(mainMenuButtons);
		this.setLeft(null);
		this.setBottom(null);
		setMenuStyles();
		show();
	}
	public PathologicalWindow(String x) {
		displayMainMenu();
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
	private void createMenuListeners() {
		btStart.setOnAction(e->{
			mainMenuButtons.getChildren().clear();
			titleLogo.getChildren().clear();
			startGame();
		});
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
