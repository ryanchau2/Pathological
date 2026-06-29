package events;

import entity.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Rest extends Event {
	private Player player;
	private BorderPane window;
	private int pathFloor;
	
//	Top Path Level Bar
	private Text pathProgressText = new Text("");
	private HBox pathProgressBox = new HBox();
	
	private HBox mainDisplayContainer = new HBox();
	private HBox imageContainerPlayer = new HBox();
	private HBox imageContainerFire = new HBox();
	private HBox buttonContainer = new HBox(15);
	
	private Button btRest = new Button("Rest (+20HP)");
	private Button btUpgradeHP = new Button("Upgrade (+10 Max HP)");
	private Button btUpgradeAtk = new Button("Upgrade (+5 Atk)");
	private Button btUpgradeDef = new Button("Upgrade (+5 Def)");
	
	public Rest(Player player, BorderPane window, int pathFloor) {
		this.player = player;
		this.window = window;
		this.pathFloor = pathFloor;
		updatePathFloor();
		displayRestScreen();
		createButtonListeners();
		styleRestButtons();
	}
	private void updatePathFloor() {
		pathProgressText.setText("Path "+pathFloor+": Rest");
		pathProgressBox.getChildren().addAll(pathProgressText);
		window.setTop(pathProgressBox);
	}
	private void displayRestScreen() {
		mainDisplayContainer.getChildren().addAll(imageContainerPlayer, imageContainerFire);
		buttonContainer.getChildren().addAll(btRest, btUpgradeHP, btUpgradeAtk, btUpgradeDef);
		window.setCenter(mainDisplayContainer);
		window.setBottom(buttonContainer);
	}
	private void createButtonListeners() {
		btRest.setOnAction(e->{
			if(player.getCurrentHP()+20>player.getMaxHP())
				player.setCurrentHP(player.getMaxHP());
			else
				player.setCurrentHP(player.getCurrentHP()+20);
			window.setBottom(null);
			new ChoosePath(window, pathFloor, player);
		});
		btUpgradeHP.setOnAction(e->{
			player.setMaxHP(player.getMaxHP()+10);
			player.setCurrentHP(player.getCurrentHP()+10);
			window.setBottom(null);
			new ChoosePath(window, pathFloor, player);
		});
		btUpgradeAtk.setOnAction(e->{
			player.setAtk(player.getAtk()+5);
			window.setBottom(null);
			new ChoosePath(window, pathFloor, player);
		});
		btUpgradeDef.setOnAction(e->{
			player.setDef(player.getAtk()+5);
			window.setBottom(null);
			new ChoosePath(window, pathFloor, player);
		});
	}
	private void styleRestButtons(){
		String btStyle = "-fx-font-size:20";
		int buttonWidth = 250;
		mainDisplayContainer.setAlignment(Pos.CENTER);
		buttonContainer.setAlignment(Pos.CENTER);
		btRest.setStyle(btStyle);
		btUpgradeHP.setStyle(btStyle);
		btUpgradeAtk.setStyle(btStyle);
		btUpgradeDef.setStyle(btStyle);
		btRest.setPrefWidth(buttonWidth);
		btUpgradeHP.setPrefWidth(buttonWidth);
		btUpgradeAtk.setPrefWidth(buttonWidth);
		btUpgradeDef.setPrefWidth(buttonWidth);
		
		buttonContainer.setPadding(new Insets(0,20,40,20));
		String pathProgression = "-fx-font-size:28";
		pathProgressText.setStyle(pathProgression);
		pathProgressBox.setAlignment(Pos.CENTER);
	}
}
