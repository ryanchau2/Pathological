package screen;

import database.SQL_Db;
import entity.Player;
import events.ChoosePath;
import items.Consumable;
import items.Equipment;
import items.Skill;
import javafx.application.Platform;
import javafx.geometry.Insets;
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
	private Button btContinue = new Button("Continue Previous Run");
	private Button btPrevRuns = new Button("Previous Runs");
	private Button btExit = new Button("Exit");
	
//	Previous Runs UI
	private Button btBack = new Button("Back");			//set left
	private VBox prevRunContainer = new VBox();
	private HBox pRun1 = new HBox();
	private VBox pRun1statContainer = new VBox();
	private HBox pRun1InventoryContainer = new HBox(15);
	private VBox pRun1Equipment = new VBox();
	private VBox pRun1Consumables = new VBox();
	private String[] p1Stats;
	private String[] p1Equips;
	private String[] p1Consumables;
	private Text txtRun1Stats = new Text("");
	private Text txtRun1Equipment = new Text("");
	private Text txtRun1Consumables = new Text("");
	
	private HBox pRun2 = new HBox();
	private VBox pRun2statContainer = new VBox();
	private HBox pRun2InventoryContainer = new HBox(15);
	private VBox pRun2Equipment = new VBox();
	private VBox pRun2Consumables = new VBox();
	private String[] p2Stats;
	private String[] p2Equips;
	private String[] p2Consumables;
	private Text txtRun2Stats = new Text("");
	private Text txtRun2Equipment = new Text("");
	private Text txtRun2Consumables = new Text("");
	
	private HBox pRun3 = new HBox();
	private VBox pRun3statContainer = new VBox();
	private HBox pRun3InventoryContainer = new HBox(15);
	private VBox pRun3Equipment = new VBox();
	private VBox pRun3Consumables = new VBox();
	private String[] p3Stats;
	private String[] p3Equips;
	private String[] p3Consumables;
	private Text txtRun3Stats = new Text("");
	private Text txtRun3Equipment = new Text("");
	private Text txtRun3Consumables = new Text("");
	
	private HBox pRun4 = new HBox();
	private VBox pRun4statContainer = new VBox();
	private HBox pRun4InventoryContainer = new HBox(15);
	private VBox pRun4Equipment = new VBox();
	private VBox pRun4Consumables = new VBox();
	private String[] p4Stats;
	private String[] p4Equips;
	private String[] p4Consumables;
	private Text txtRun4Stats = new Text("");
	private Text txtRun4Equipment = new Text("");
	private Text txtRun4Consumables = new Text("");
	
	private BorderPane buttonContainer = new BorderPane();
	private Button btNewer = new Button("Previous Page");
	private Button btOlder = new Button("Next Page");
	
	
	private int pathFloor;
	private Player newPlayer;
	private SQL_Db database;
	
	private int totalRuns;
	
//	Prev runID if available
	private int prevRunID;
	
//	Credits
	private Text credits = new Text("Credits: Knight Sprite by aamatniekss and Skeleton Sprite by Jesse Munguia both on itch.io");
//	===============================================================
	
	
	public PathologicalWindow() {
		windowText = "Pathological";
		displayMainMenu();
		buildPrevRunMenu();
		checkUnfinishedRun();
		setPreviousMenuBoxStyles();
		
		this.setTop(titleLogo);
		this.setCenter(mainMenuButtons);
		this.setLeft(null);
		this.setBottom(credits);
		setMenuStyles();
		show();
	}
	private void startGame() {
		newPlayer = new Player();
		pathFloor = 0;
		new ChoosePath(this, pathFloor, newPlayer);
	}
	private void displayMainMenu() {
		titleLogo.getChildren().add(ivTitleLogo);
		mainMenuButtons.getChildren().addAll(btStart,btContinue,btPrevRuns,btExit);
		createMenuListeners();
		
		preparePrevRunMenu();
	}
	private void setMainMenu() {
		this.setTop(titleLogo);
		this.setCenter(mainMenuButtons);
		this.setLeft(null);
		this.setRight(null);
		this.setBottom(credits);
	}
	private void checkUnfinishedRun() {
		database = new SQL_Db();
		prevRunID = database.getIncompleteRunsDB();
		if(prevRunID==0)
			btContinue.setDisable(true);
		else {
			btContinue.setDisable(false);
			btStart.setDisable(true);
		}
	}
	private void createMenuListeners() {
		btStart.setOnAction(e->{
			mainMenuButtons.getChildren().clear();
			titleLogo.getChildren().clear();
			startGame();
		});
		btContinue.setOnAction(e->{
			//change players stats to what they were
			database = new SQL_Db();
//			System.out.println("Setting player stats");
			int[] prevStats = database.setPlayerStats(prevRunID);
			newPlayer = new Player(prevStats[0], prevStats[1], prevStats[2], prevStats[3], prevStats[4], prevStats[5], prevStats[6]);
			pathFloor = prevStats[7];
//			System.out.println("Finished Setting player stats and floor");
			
			//equip the player with the gear if there is any
//			System.out.println("Setting current equipment");
			if(database.countRowsbyID("current_Equipment", prevRunID)!=0) {
				int[] prevEquip = database.pullPrevRunEquipment(prevRunID);
				for(int x=0; x<prevEquip.length; x++) {
					newPlayer.addToEquipment(new Equipment(prevEquip[x]), this);
				}
//				System.out.println("Finished Equipment");
			}
		
//			System.out.println("Setting Current Consumables");
			//give player consumables if there are any
			if(database.countRowsbyID("inventory", prevRunID)!=0) {
				int[] prevConsumables = database.pullPrevRunConsumable(prevRunID);
				for(int x=0; x<prevConsumables.length; x++) {
					newPlayer.addToConsumables(new Consumable(prevConsumables[x]), this);
				}
//				System.out.println("Finished Setting Current Consumables");
			}
//			System.out.println("Setting Up Skills");
			if(database.countRowsbyID("skills_list", prevRunID)!=0) {
				int[] prevSkills = database.pullPrevRunSkills(prevRunID);
				for(int x=0; x<prevSkills.length; x++) {
					newPlayer.addToSkills(new Skill(prevSkills[x]));
				}
//				System.out.println("Finished Setting Skills");
			}
//			pathFloor = 0;
			//delete items from SQL
			database.continueRun(prevRunID);
			database.close();
			//continue run
			new ChoosePath(this, pathFloor, newPlayer);
		});
		btPrevRuns.setOnAction(e->{
			this.setTop(null);
			prevRunMenu();
		});
		btExit.setOnAction(e->{
		Platform.exit();
		});
	}
//	------------------------------------------------------------------------------------------------
	private void preparePrevRunMenu() {
		database = new SQL_Db();
		totalRuns = database.countRows("player_run"); //3
		if(totalRuns-4<0)
			btOlder.setDisable(true);
		database.close();
		populateRuns();
		
		buttonContainer.setLeft(btNewer);
		btNewer.setDisable(true);
		
		buttonContainer.setRight(btOlder);
		createPrevRunMenuListeners();
		
		//Creates Run1 Box
		pRun1statContainer.getChildren().add(txtRun1Stats);
		pRun1Equipment.getChildren().add(txtRun1Equipment);
		pRun1Consumables.getChildren().add(txtRun1Consumables);
		pRun1InventoryContainer.getChildren().addAll(pRun1Equipment,pRun1Consumables);
		pRun1.getChildren().addAll(pRun1statContainer,pRun1InventoryContainer);
		prevRunContainer.getChildren().addAll(pRun1);
		//Creates Run2 Box
		pRun2statContainer.getChildren().add(txtRun2Stats);
		pRun2Equipment.getChildren().add(txtRun2Equipment);
		pRun2Consumables.getChildren().add(txtRun2Consumables);
		pRun2InventoryContainer.getChildren().addAll(pRun2Equipment,pRun2Consumables);
		pRun2.getChildren().addAll(pRun2statContainer,pRun2InventoryContainer);
		prevRunContainer.getChildren().addAll(pRun2);
		//Creates Run3 Box
		pRun3statContainer.getChildren().add(txtRun3Stats);
		pRun3Equipment.getChildren().add(txtRun3Equipment);
		pRun3Consumables.getChildren().add(txtRun3Consumables);
		pRun3InventoryContainer.getChildren().addAll(pRun3Equipment,pRun3Consumables);
		pRun3.getChildren().addAll(pRun3statContainer,pRun3InventoryContainer);
		prevRunContainer.getChildren().addAll(pRun3);
		//Creates Run4 Box
		pRun4statContainer.getChildren().add(txtRun4Stats);
		pRun4Equipment.getChildren().add(txtRun4Equipment);
		pRun4Consumables.getChildren().add(txtRun4Consumables);
		pRun4InventoryContainer.getChildren().addAll(pRun4Equipment,pRun4Consumables);
		pRun4.getChildren().addAll(pRun4statContainer,pRun4InventoryContainer);
		prevRunContainer.getChildren().addAll(pRun4);
	}
	private void populateRuns() {
		database = new SQL_Db();
		if(database.countRows("player_run")==0) {
			return;
		}
		else {
			populateRun1(totalRuns);
			populateRun2(totalRuns-1);
			populateRun3(totalRuns-2);
			populateRun4(totalRuns-3);
		}
		database.close();
	}
	private void createPrevRunMenuListeners() {
		btNewer.setOnAction(e->{
			database = new SQL_Db();
			totalRuns=totalRuns+4;
			if(totalRuns+4>database.countRows("player_run")) {
				btNewer.setDisable(true);
			}
			btOlder.setDisable(false);
			populateRuns();
		});
		btOlder.setOnAction(e->{
			totalRuns=totalRuns-4;
			if(totalRuns-4<0) {
				btOlder.setDisable(true);
			}
			btNewer.setDisable(false);
			populateRuns();
		});
	}
	//Run Box1 (Top)
	private void populateRun1(int runID) {
		if(runID <= 0) {
			txtRun1Stats.setText("");
			txtRun1Equipment.setText("");
			txtRun1Consumables.setText("");
			return;
		}
		p1Stats = database.getPlayerRun(runID);
		String p1StatsString = "";
		for(int x = 0; x<p1Stats.length; x++) {
			p1StatsString += p1Stats[x];
			if(x+1!=p1Stats.length)
				p1StatsString += "\n";
		}
		txtRun1Stats.setText(p1StatsString);
		
		//get equipment
		p1Equips = database.getEquipmentHistory(runID);
		String p1EquipString = "";
		p1EquipString+="Equipment\n";
		for(int y = 0; y<p1Equips.length; y++) {
			if(p1Equips[y]==null)
				break;
			p1EquipString += p1Equips[y];
			if(y+1!=p1Equips.length)
				p1EquipString += "\n";
		}
		txtRun1Equipment.setText(p1EquipString);
		
		//get consumables/inventory
		p1Consumables = database.getConsumableHistory(runID);
		String p1ConsumableString = "";
		p1ConsumableString+="Consumables\n";
		for(int z=0; z<p1Consumables.length; z++) {
			if(p1Consumables[z]==null)
				break;
			p1ConsumableString += p1Consumables[z];
			if(z+1!=p1Consumables.length)
				p1ConsumableString += "\n";
		}
		txtRun1Consumables.setText(p1ConsumableString);
	}
	
	//Run Box2
	private void populateRun2(int runID) {
		if(runID <= 0) {
			txtRun2Stats.setText("");
			txtRun2Equipment.setText("");
			txtRun2Consumables.setText("");
			return;
		}
		p2Stats = database.getPlayerRun(runID);
		String p2StatsString = "";
		for(int x = 0; x<p2Stats.length; x++) {
			p2StatsString += p2Stats[x];
			if(x+1!=p2Stats.length)
				p2StatsString += "\n";
		}
		txtRun2Stats.setText(p2StatsString);
		
		//get equipment
		p2Equips = database.getEquipmentHistory(runID);
		String p2EquipString = "";
		p2EquipString+="Equipment\n";
		for(int y = 0; y<p2Equips.length; y++) {
			if(p2Equips[y]==null)
				break;
			p2EquipString += p2Equips[y];
			if(y+1!=p2Equips.length)
				p2EquipString += "\n";
		}
		txtRun2Equipment.setText(p2EquipString);
		
		//get consumables/inventory
		p2Consumables = database.getConsumableHistory(runID);
		String p2ConsumableString = "";
		p2ConsumableString+="Consumables\n";
		for(int z=0; z<p2Consumables.length; z++) {
			if(p2Consumables[z]==null)
				break;
			p2ConsumableString += p2Consumables[z];
			if(z+1!=p2Consumables.length)
				p2ConsumableString += "\n";
		}
		txtRun2Consumables.setText(p2ConsumableString);
	}
	//Run Box 3
	private void populateRun3(int runID) {
		if(runID <= 0) {
			txtRun3Stats.setText("");
			txtRun3Equipment.setText("");
			txtRun3Consumables.setText("");
			return;
		}
		p3Stats = database.getPlayerRun(runID);
		String p3StatsString = "";
		for(int x = 0; x<p3Stats.length; x++) {
			p3StatsString += p3Stats[x];
			if(x+1!=p3Stats.length)
				p3StatsString += "\n";
		}
		txtRun3Stats.setText(p3StatsString);
		
		//get equipment
		p3Equips = database.getEquipmentHistory(runID);
		String p3EquipString = "";
		p3EquipString+="Equipment\n";
		for(int y = 0; y<p3Equips.length; y++) {
			if(p3Equips[y]==null)
				break;
			p3EquipString += p3Equips[y];
			if(y+1!=p3Equips.length)
				p3EquipString += "\n";
		}
		txtRun3Equipment.setText(p3EquipString);
		
		//get consumables/inventory
		p3Consumables = database.getConsumableHistory(runID);
		String p3ConsumableString = "";
		p3ConsumableString+="Consumables\n";
		for(int z=0; z<p3Consumables.length; z++) {
			if(p3Consumables[z]==null)
				break;
			p3ConsumableString += p3Consumables[z];
			if(z+1!=p3Consumables.length)
				p3ConsumableString += "\n";
		}
		txtRun3Consumables.setText(p3ConsumableString);
	}
	private void populateRun4(int runID) {
		if(runID <= 0) {
			txtRun4Stats.setText("");
			txtRun4Equipment.setText("");
			txtRun4Consumables.setText("");
			return;
		}
		p4Stats = database.getPlayerRun(runID);
		String p4StatsString = "";
		for(int x = 0; x<p4Stats.length; x++) {
			p4StatsString += p4Stats[x];
			if(x+1!=p4Stats.length)
				p4StatsString += "\n";
		}
		txtRun4Stats.setText(p4StatsString);
		
		//get equipment
		p4Equips = database.getEquipmentHistory(runID);
		String p4EquipString = "";
		p4EquipString+="Equipment\n";
		for(int y = 0; y<p4Equips.length; y++) {
			if(p4Equips[y]==null)
				break;
			p4EquipString += p4Equips[y];
			if(y+1!=p4Equips.length)
				p4EquipString += "\n";
		}
		txtRun4Equipment.setText(p4EquipString);
		
		//get consumables/inventory
		p4Consumables = database.getConsumableHistory(runID);
		String p4ConsumableString = "";
		p4ConsumableString+="Consumables\n";
		for(int z=0; z<p4Consumables.length; z++) {
			if(p4Consumables[z]==null)
				break;
			p4ConsumableString += p4Consumables[z];
			if(z+1!=p4Consumables.length)
				p4ConsumableString += "\n";
		}
		txtRun4Consumables.setText(p4ConsumableString);
	}
	private void prevRunMenu() {
		this.setLeft(btBack);
		this.setCenter(prevRunContainer);
		this.setRight(buttonContainer);
		this.setBottom(null);
	}
	private void buildPrevRunMenu() {
		createPrevMenuBtListeners();
	}
	private void createPrevMenuBtListeners() {
		btBack.setOnAction(e->{
			setMainMenu();
		});
	}
//	------------------------------------------------------------------------------------------------
	private void setMenuStyles() {
		//Styling
		String buttonStyle = "-fx-font-size:28";
		int buttonWidth = 250;
		btStart.setStyle(buttonStyle);
		btStart.setPrefWidth(buttonWidth);
		btContinue.setStyle(buttonStyle);
		btContinue.setPrefWidth(buttonWidth+100);
		btPrevRuns.setStyle(buttonStyle);
		btPrevRuns.setPrefWidth(buttonWidth);
		btExit.setStyle(buttonStyle);
		btExit.setPrefWidth(buttonWidth);
		mainMenuButtons.setAlignment(Pos.CENTER);
		titleLogo.setAlignment(Pos.CENTER);
	}
	private void setPreviousMenuBoxStyles() {
		String cssLayout = 
				"-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 0 0 5px 0;\n" +
                "-fx-border-style: solid;\n";
		pRun1.setStyle(cssLayout);
		pRun2.setStyle(cssLayout);
		pRun3.setStyle(cssLayout);
		pRun4.setStyle(cssLayout);
		
		String pRunCText = "-fx-font-size:18";
		pRun1statContainer.setStyle(pRunCText);
		pRun2statContainer.setStyle(pRunCText);
		pRun3statContainer.setStyle(pRunCText);
		pRun4statContainer.setStyle(pRunCText);
		String pRunInventoryText = "-fx-font-size:12";
		pRun1InventoryContainer.setStyle(pRunInventoryText);
		pRun2InventoryContainer.setStyle(pRunInventoryText);
		pRun3InventoryContainer.setStyle(pRunInventoryText);
		pRun4InventoryContainer.setStyle(pRunInventoryText);
		
		pRun1statContainer.setPadding(new Insets(0, 0, 0, 20));
		pRun2statContainer.setPadding(new Insets(0, 0, 0, 20));
		pRun3statContainer.setPadding(new Insets(0, 0, 0, 20));
		pRun4statContainer.setPadding(new Insets(0, 0, 0, 20));
		pRun1InventoryContainer.setPadding(new Insets(0, 0, 0, 50));
		pRun2InventoryContainer.setPadding(new Insets(0, 0, 0, 50));
		pRun3InventoryContainer.setPadding(new Insets(0, 0, 0, 50));
		pRun4InventoryContainer.setPadding(new Insets(0, 0, 0, 50));
	}
	private void show() {
		Stage stage = new Stage();
		Scene scene = new Scene(this, 1024, 768);
		stage.setTitle(windowText);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
		stage.setOnCloseRequest(e->{
			e.consume();
		});
	}
}
