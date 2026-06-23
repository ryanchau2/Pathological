package events;

import java.util.concurrent.TimeUnit;

import entity.Enemy;
import entity.Entity;
import entity.Player;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import screen.PathologicalWindow;

public class Battle extends Event {
	private Player player;
	private BorderPane window;
	private int pathFloor;
	
//	Top Path Level Bar
	Text pathProgressText = new Text("");
	HBox pathProgressBox = new HBox();
//Battle Elements
//Battle UI elements (Contains the player and enemy container)
	private HBox battleBoxContainer = new HBox(200);
//Player Elements (Contains player visual attributes)
	private VBox playerContainer = new VBox(5);
	private HBox playerImageContainer = new HBox();
	private HBox playerHPContainer = new HBox();
	private HBox playerMPContainer = new HBox();
//Enemy Elements (Contains enemy visual attributes)
	private VBox enemyContainer = new VBox(5);
	private HBox enemyImageContainer = new HBox();
	private HBox enemyHPContainer = new HBox();
	private HBox enemyMPContainer = new HBox();

	private VBox playerStats = new VBox();
	private Text player_HP;
	private Text player_MP;

	private Enemy enemy;
	private Text enemy_HP;
	private Text enemy_MP;

	private VBox enemyStats = new VBox();
	
	PauseTransition e_pause5 = new PauseTransition(Duration.seconds(5));
	PauseTransition p_pause5 = new PauseTransition(Duration.seconds(5));
//Action Bar UI
	private VBox actionBarUI = new VBox();
	private HBox action1Bar = new HBox(10);		//contains Attack | Skills
	private HBox action2Bar = new HBox(10);		//contains Defend | Items
//Action Buttons
	private Button btAttack = new Button("Attack");
	private Button btSkills = new Button("Skills");
	private Button btDefend = new Button("Defend");
	private Button btItem = new Button("Items");
//	Skills
	private VBox skillsContainer = new VBox();
	private HBox skillBar1 = new HBox();
	private HBox skillBar2 = new HBox();
	private Button btsBack = new Button("Back");
	private Button skill1 = new Button();
	private Button skill2 = new Button();
	private Button skill3 = new Button();
	private Button skill4 = new Button();
	private Button skill5 = new Button();
	private Button skill6 = new Button();
//	Item 
	private VBox itemsContainer = new VBox();
	private HBox itemsBar1 = new HBox();
	private HBox itemsBar2 = new HBox();
	private Button btiBack = new Button("Back");
	private Button item1 = new Button();
	private Button item2 = new Button();
	private Button item3 = new Button();
	private Button item4 = new Button();
	private Button item5 = new Button();
	private Button item6 = new Button();
	private Button item7 = new Button();
	private Button item8 = new Button();
	private Button item9 = new Button();
	private Button item10 = new Button();
	
//	Turn Order Elements
	VBox turnOrderContainer = new VBox();
	Text turnNumText = new Text("");
	Text turnEntity = new Text("");
	int turnNum;
	
//	Gameover Elements
	HBox gameOverContainer = new HBox();
	Text gameOverTxt = new Text();

	public Battle(Player player, BorderPane window, int pathFloor) {
		this.player = player;
		this.window = window;
		this.pathFloor = pathFloor;
		
		pathProgressText.setText("Path "+pathFloor+": Battle");
		pathProgressBox.getChildren().addAll(pathProgressText);
		window.setTop(pathProgressBox);
		setPlayerInfo();
		createEnemy();
		compileBattleWindow();
		createSkillInstance();
		createItemsInstance();
		createTurnOrderUI();
		battle();
	}
	private void createItemsInstance() {
		itemsBar1.getChildren().addAll(btiBack, item1, item2, item3, item4, item5);
		itemsBar2.getChildren().addAll(item6, item7, item8, item9, item10);
		itemsContainer.getChildren().addAll(itemsBar1, itemsBar2);
	}
	private void showItems() {
		window.setBottom(null);
		window.setBottom(itemsContainer);
		createItemsListener();
	}
	private void createSkillInstance() {
		skillBar1.getChildren().addAll(btsBack, skill1, skill2, skill3);
		skillBar2.getChildren().addAll(skill4, skill5, skill6);
		skillsContainer.getChildren().addAll(skillBar1,skillBar2);
	}
	private void createItemsListener() {
		btiBack.setOnAction(e->{
			window.setBottom(actionBarUI);
		});
	}
	private void showSkills() {
		window.setBottom(null);
		window.setBottom(skillsContainer);
		createSkillsListener();
	}
	private void createSkillsListener() {
		btsBack.setOnAction(e->{
			window.setBottom(actionBarUI);
		});
	}
	private void compileBattleWindow() {
		battleBoxContainer.getChildren().addAll(playerContainer, enemyContainer);
		window.setCenter(battleBoxContainer);
		
//		Container Styles
		styleBattleEntityContainers(playerStats, enemyStats, player_HP, player_MP, enemy_HP, enemy_MP);
		
//		Action Bar
		action1Bar.getChildren().addAll(btAttack, btSkills);
		action2Bar.getChildren().addAll(btDefend, btItem);
		actionBarUI.getChildren().addAll(action1Bar, action2Bar);
		window.setBottom(actionBarUI);
		
		styleBattleActionUI();
	}
	private void createTurnOrderUI() {
		turnEntity.setText("Player's turn");
		turnNum = 1;
		turnNumText.setText("Turn "+turnNum);
		turnOrderContainer.getChildren().addAll(turnEntity, turnNumText);
		window.setLeft(turnOrderContainer);
	}
	private void battle() {
			createActionBarListeners();
	}
	private void gameOverScreen() {
		System.out.println("Player Lost");
		actionBarUI.getChildren().clear();
		battleBoxContainer.getChildren().clear();
		turnOrderContainer.getChildren().clear();
		window.setTop(null);
		gameOverTxt.setText("You have lasted " + pathFloor + " paths, but unfortunately passed a tragic death");
		gameOverContainer.getChildren().addAll(gameOverTxt);
		window.setCenter(gameOverContainer);
		//get player attributes and display and save
	}
	private void enableButtons() {
		btAttack.setDisable(false);
		btSkills.setDisable(false);
		btDefend.setDisable(false);
		btItem.setDisable(false);
	}
	private void disableButtons() {
		btAttack.setDisable(true);
		btSkills.setDisable(true);
		btDefend.setDisable(true);
		btItem.setDisable(true);
	}
	private void enemyTurn() {
		if(enemy.getCurrentHP()>0) {
//			enemyTurnDelay();
			attack(enemy, player);
		}
		else {
			actionBarUI.getChildren().clear();
			playerContainer.getChildren().clear();
			enemyContainer.getChildren().clear();
			new ChoosePath(window, pathFloor, player);
		}
		turnEntity.setText("Player's turn");
		if(player.getCurrentHP()<=0) {
			gameOverScreen();
		}
		
	}
	private void enemyTurnDelay() {
		turnEntity.setText("Enemy's turn");
		disableButtons();
		e_pause5.setOnFinished(f->{
			enemyTurn();
//			attack(enemy, player);
			enableButtons();
			turnNum++;
			turnNumText.setText("Turn "+turnNum);
		});
		e_pause5.play();
	}
	
	private void defend(Entity e1) {
		int tempDefense = (int)(player.getDef()*.5);
		player.setDef(player.getDef()+tempDefense);
		enemyTurn();
		player.setDef(player.getDef()-tempDefense);
	}
	private void attack(Entity e1, Entity e2) {
		int damage = e1.getAtk()-e2.getDef();
		if(damage < 0) {
			return;
		}
		e2.setCurrentHP(e2.getCurrentHP()-damage);
		if(e2 instanceof Player) {
			player_HP.setText(String.valueOf(e2.getCurrentHP()+"/"+e2.getMaxHP()));
		}
		else {
			enemy_HP.setText(String.valueOf(e2.getCurrentHP()+"/"+e2.getMaxHP()));	
		}
	}
//	Action Bar Listeners
	private void createActionBarListeners() {
		btAttack.setOnAction(e->{
			attack(player, enemy);
			enemyTurnDelay();
		});
		btSkills.setOnAction(e->{
			showSkills();
		});
		btDefend.setOnAction(e->{
			defend(player);
			enemyTurnDelay();
		});
		btItem.setOnAction(e->{
			showItems();
		});
	}
	private void createEnemy() {
//		Retrieves Enemy Information
		enemy = new Enemy(pathFloor);							//generate new energy object
		setEnemyInfo();
	}
	private void setEnemyInfo() {
		enemy_HP = enemy.display_HPStat();
		enemy_MP = enemy.display_MPStat();
		enemyStats.getChildren().addAll(enemy_HP, enemy_MP);
		enemyImageContainer.getChildren().add(new ImageView(enemy.getEntity_sprite()));
		enemyContainer.getChildren().addAll(enemyImageContainer,enemyStats);
	}
	private void setPlayerInfo() {
//		Retrieves Player Information
		player_HP = player.display_HPStat();
		player_MP = player.display_MPStat();
		playerStats.getChildren().addAll(player_HP, player_MP);
		playerImageContainer.getChildren().add(new ImageView(player.getEntity_sprite()));
		playerContainer.getChildren().addAll(playerImageContainer,playerStats);
	}
	private void styleBattleActionUI() {
		String btStyle = "-fx-font-size:40";
		String btStyle20 = "-fx-font-size:20";
		int buttonWidth = 400;
		btAttack.setStyle(btStyle);
		btSkills.setStyle(btStyle);
		btDefend.setStyle(btStyle);
		btItem.setStyle(btStyle);
		turnNumText.setStyle(btStyle20);
		turnEntity.setStyle(btStyle20);
		
//		Skill Buttons
		String btSkillStyle = "-fx-font-size:25";
		int btSkillWidth = 300;
		btsBack.setStyle(btSkillStyle);
		skill1.setStyle(btSkillStyle);
		skill2.setStyle(btSkillStyle);
		skill3.setStyle(btSkillStyle);
		skill4.setStyle(btSkillStyle);
		skill5.setStyle(btSkillStyle);
		skill6.setStyle(btSkillStyle);
		skill1.setPrefWidth(btSkillWidth);
		skill2.setPrefWidth(btSkillWidth);
		skill3.setPrefWidth(btSkillWidth);
		skill4.setPrefWidth(btSkillWidth);
		skill5.setPrefWidth(btSkillWidth);
		skill6.setPrefWidth(btSkillWidth);
		skillBar1.setAlignment(Pos.CENTER);
		skillBar2.setAlignment(Pos.CENTER);
		
//		Standard ActionBar UI
		btAttack.setPrefWidth(buttonWidth);
		btSkills.setPrefWidth(buttonWidth);
		btDefend.setPrefWidth(buttonWidth);
		btItem.setPrefWidth(buttonWidth);
		action1Bar.setAlignment(Pos.CENTER);
		action2Bar.setAlignment(Pos.CENTER);
		
//		Items
		String btItemStyle = "-fx-font-size:20";
		int btItemWidth = 175;
		btiBack.setStyle(btItemStyle);
		item1.setStyle(btItemStyle);
		item2.setStyle(btItemStyle);
		item3.setStyle(btItemStyle);
		item4.setStyle(btItemStyle);
		item5.setStyle(btItemStyle);
		item6.setStyle(btItemStyle);
		item7.setStyle(btItemStyle);
		item8.setStyle(btItemStyle);
		item9.setStyle(btItemStyle);
		item10.setStyle(btItemStyle);
		item1.setPrefWidth(btItemWidth);
		item2.setPrefWidth(btItemWidth);
		item3.setPrefWidth(btItemWidth);
		item4.setPrefWidth(btItemWidth);
		item5.setPrefWidth(btItemWidth);
		item6.setPrefWidth(btItemWidth);
		item7.setPrefWidth(btItemWidth);
		item8.setPrefWidth(btItemWidth);
		item9.setPrefWidth(btItemWidth);
		item10.setPrefWidth(btItemWidth);
		itemsBar1.setAlignment(Pos.CENTER);
		itemsBar2.setAlignment(Pos.CENTER);
//		===
	}
	private void styleBattleEntityContainers(VBox entity1, VBox entity2, Text e1_hpStat, Text e1_mpStat, Text e2_hpStat, Text e2_mpStat){
		battleBoxContainer.setAlignment(Pos.CENTER);
		entity1.setAlignment(Pos.CENTER);
		entity2.setAlignment(Pos.CENTER);
		e1_hpStat.setStyle("-fx-font-size:20");
		e1_mpStat.setStyle("-fx-font-size:20");
		e2_hpStat.setStyle("-fx-font-size:20");
		e2_mpStat.setStyle("-fx-font-size:20");
		
//		Gameover Styling
		gameOverContainer.setAlignment(Pos.CENTER);
		gameOverTxt.setStyle("-fx-font-size:40");
		gameOverTxt.setWrappingWidth(800);
		gameOverTxt.setTextAlignment(TextAlignment.CENTER);
//		Top Progression
		String pathProgression = "-fx-font-size:28";
		pathProgressText.setStyle(pathProgression);
		pathProgressBox.setAlignment(Pos.CENTER);
	}
}
