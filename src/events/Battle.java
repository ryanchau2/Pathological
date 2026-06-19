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

public class Battle extends Event {
Player player;
BorderPane window;
//Battle
//Battle UI elements (Contains the player and enemy container)
HBox battleBoxContainer = new HBox(200);
//Player Elements (Contains player visual attributes)
VBox playerContainer = new VBox(5);
HBox playerImageContainer = new HBox();
HBox playerHPContainer = new HBox();
HBox playerMPContainer = new HBox();
//Enemy Elements (Contains enemy visual attributes)
VBox enemyContainer = new VBox(5);
HBox enemyImageContainer = new HBox();
HBox enemyHPContainer = new HBox();
HBox enemyMPContainer = new HBox();
//
VBox playerStats = new VBox();
Text player_HP;
Text player_MP;

Enemy enemy;
Text enemy_HP;
Text enemy_MP;
int floorLevel;

VBox enemyStats = new VBox();
//Action Bar UI
VBox actionBarUI = new VBox();
HBox action1Bar = new HBox(10);		//contains Attack | Skills
HBox action2Bar = new HBox(10);		//contains Defend | Items
//Action Buttons
Button btAttack = new Button("Attack");
Button btSkills = new Button("Skills");
Button btDefend = new Button("Defend");
Button btItem = new Button("Items");

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
	}
	private void createActionBarListeners() {
		btAttack.setOnAction(e->{
			attack(player, enemy);
			enemyTurn();
		});
//		btSkills.setOnAction(e->{
//			
//		});
		btDefend.setOnAction(e->{
			defend(player);
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
