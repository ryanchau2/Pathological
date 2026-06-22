package events;

import entity.Enemy;
import entity.Entity;
import entity.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import screen.PathologicalWindow;

public class Battle extends Event {
	private Player player;
	private BorderPane window;
	private int floorLevel;
//Battle
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
//Action Bar UI
	private VBox actionBarUI = new VBox();
	private HBox action1Bar = new HBox(10);		//contains Attack | Skills
	private HBox action2Bar = new HBox(10);		//contains Defend | Items
//Action Buttons
	private Button btAttack = new Button("Attack");
	private Button btSkills = new Button("Skills");
	private Button btDefend = new Button("Defend");
	private Button btItem = new Button("Items");

	public Battle(Player player, BorderPane window, int floorLevel) {
		this.player = player;
		this.window = window;
		this.floorLevel = floorLevel;
		setPlayerInfo();
		createEnemy();
		compileBattleWindow();
		battle();
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
	private void battle() {
			createActionBarListeners();	
	}
	private void enemyTurn() {
		if(enemy.getCurrentHP()>0)
			attack(enemy, player);
		else {
			actionBarUI.getChildren().clear();
			playerContainer.getChildren().clear();
			enemyContainer.getChildren().clear();
			new ChoosePath(window, floorLevel, player);
		}
		if(player.getCurrentHP()<=0) {
			gameOverScren();
		}
	}
	private void gameOverScren() {
		System.out.println("Player Lost");
		actionBarUI.getChildren().clear();
		battleBoxContainer.getChildren().clear();
		window.setTop(null);
		System.out.println("You have lasted " + floorLevel + " paths, but unfortunately passed a tragic death");
		//get player attributes and display and save
	}
	private void createActionBarListeners() {
		btAttack.setOnAction(e->{
			attack(player, enemy);
			enemyTurn();
		});
//		btSkill.setOnAction();
		btDefend.setOnAction(e->{
			defend(player);
			enemyTurn();
		});
//		btItem.setOnAction();
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
	private void createEnemy() {
//		Retrieves Enemy Information
		enemy = new Enemy(floorLevel);							//generate new energy object
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
		int buttonWidth = 400;
		btAttack.setStyle(btStyle);
		btSkills.setStyle(btStyle);
		btDefend.setStyle(btStyle);
		btItem.setStyle(btStyle);
		
		btAttack.setPrefWidth(buttonWidth);
		btSkills.setPrefWidth(buttonWidth);
		btDefend.setPrefWidth(buttonWidth);
		btItem.setPrefWidth(buttonWidth);
		action1Bar.setAlignment(Pos.CENTER);
		action2Bar.setAlignment(Pos.CENTER);
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
}
