package events;

import java.util.concurrent.TimeUnit;

import entity.Enemy;
import entity.Entity;
import entity.Player;
import items.Consumable;
import items.Equipment;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
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
	
	private Equipment[] equipmentList;
	private Consumable[] consumableList;
	
//	Top Path Level Bar
	Text pathProgressText = new Text("");
	HBox pathProgressBox = new HBox();
//Battle Elements
//Battle UI elements (Contains the player and enemy container)
	private HBox battleBoxContainer = new HBox(200);
//Player Elements (Contains player visual attributes)
	private VBox playerContainer = new VBox(5);
	private HBox playerImageContainer = new HBox();
	private ImageView playerImage;
	
	private HBox playerHPContainer = new HBox();
	private HBox playerMPContainer = new HBox();
//Enemy Elements (Contains enemy visual attributes)
	private VBox enemyContainer = new VBox(5);
	private HBox enemyImageContainer = new HBox();
	private HBox enemyHPContainer = new HBox();
	private HBox enemyMPContainer = new HBox();
	private ImageView enemyImage;

	private VBox playerStats = new VBox();
	private Text player_HP;
	private Text player_MP;

	private Enemy enemy;
	private Text enemy_HP;
	private Text enemy_MP;

	private VBox enemyStats = new VBox();
	
	PauseTransition e_pause = new PauseTransition(Duration.seconds(2));
	PauseTransition p_pause = new PauseTransition(Duration.seconds(0.5));
//Action Bar UI
	private VBox actionBarUI = new VBox(10);
	private HBox action1Bar = new HBox(10);		//contains Attack | Skills
	private HBox action2Bar = new HBox(10);		//contains Defend | Items
//Action Buttons
	private Button btAttack = new Button("Attack");
	private Button btSkills = new Button("Skills");
	private Button btDefend = new Button("Defend");
	private Button btItem = new Button("Items");
//	Skills
	private VBox skillsContainer = new VBox(10);
	private HBox skillBar1 = new HBox(10);
	private HBox skillBar2 = new HBox(10);
	private Button btsBack = new Button("Back");
	private Button skill1 = new Button();
	private Button skill2 = new Button();
	private Button skill3 = new Button();
	private Button skill4 = new Button();
//	Item 
	
//	Item Containers and Buttons
	private ScrollPane itemsContainer2 = new ScrollPane();
	private VBox itemsContainer = new VBox(10);
	private HBox itemsBar1 = new HBox(10);
	private HBox itemsBar2 = new HBox(10);
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
	private VBox turnOrderContainer = new VBox();
	private Text turnNumText = new Text("");
	private Text turnEntity = new Text("");
	private int turnNum;
	
//	Gameover Elements
	private HBox gameOverContainer = new HBox();
	private Text gameOverTxt = new Text();
	private HBox restartContainer = new HBox();
	private Button btRestart = new Button("Exit");

	public Battle(Player player, BorderPane window, int pathFloor) {
		this.player = player;
		this.window = window;
		this.pathFloor = pathFloor;
		window.setRight(null);
//		equipmentList = player.getEquipmentList();
		consumableList = player.getConsumableList();
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
		presetGameOverScreen();
	}
//	Sets all the button's text to the item they correspond to
	private void setButtonsItemsNames() {
		if (consumableList[0] != null) {
			item1.setText(consumableList[0].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[0].getItemDesc());
			item1.setTooltip(tooltip);
		}
		else
			item1.setDisable(true);
		if (consumableList[1] != null) {
			item2.setText(consumableList[1].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[1].getItemDesc());
			item2.setTooltip(tooltip);
		}
		else
			item2.setDisable(true);
		if (consumableList[2] != null) {
			item3.setText(consumableList[2].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[2].getItemDesc());
			item3.setTooltip(tooltip);
		}
		else
			item3.setDisable(true);
		if (consumableList[3] != null) {
			item4.setText(consumableList[3].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[3].getItemDesc());
			item4.setTooltip(tooltip);
		}
		else
			item4.setDisable(true);
		if (consumableList[4] != null) {
			item5.setText(consumableList[4].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[4].getItemDesc());
			item5.setTooltip(tooltip);
		}
		else
			item5.setDisable(true);
		if (consumableList[5] != null) {
			item6.setText(consumableList[5].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[5].getItemDesc());
			item6.setTooltip(tooltip);
		}
		else
			item6.setDisable(true);
		if (consumableList[6] != null) {
			item7.setText(consumableList[6].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[6].getItemDesc());
			item7.setTooltip(tooltip);
		}
		else
			item7.setDisable(true);
		if (consumableList[7] != null) {
			item8.setText(consumableList[7].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[7].getItemDesc());
			item8.setTooltip(tooltip);
		}
		else
			item8.setDisable(true);
		if (consumableList[8] != null) {
			item9.setText(consumableList[8].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[8].getItemDesc());
			item9.setTooltip(tooltip);
		}
		else
			item9.setDisable(true);
		if (consumableList[9] != null) {
			item10.setText(consumableList[9].getItemName());
			Tooltip tooltip = new Tooltip(consumableList[9].getItemDesc());
			item10.setTooltip(tooltip);
		}
		else
			item10.setDisable(true);
	}
//	Sets non-null items to be enabled
	private void resetDisables() {
		item1.setDisable(false);
		item2.setDisable(false);
		item3.setDisable(false);
		item4.setDisable(false);
		item5.setDisable(false);
		item6.setDisable(false);
		item7.setDisable(false);
		item8.setDisable(false);
		item9.setDisable(false);
		item10.setDisable(false);
	}
//	Creates the user interface for items container and the buttons for items
	private void createItemsInstance() {
		resetDisables();
		setButtonsItemsNames();
		itemsBar1.getChildren().addAll(item1, item2, item3, item4, item5);
		itemsBar2.getChildren().addAll(item6, item7, item8, item9, item10);
		itemsContainer.getChildren().addAll(btiBack, itemsBar1, itemsBar2);
		itemsContainer2.setContent(itemsContainer);
	}
//	Change bottom UI to items when the player wishes to look at
	private void showItems() {
		window.setBottom(null);
		window.setBottom(itemsContainer);
		createItemsListener();
	}
//	Creates the UI for skills container
	private void createSkillInstance() {
		skillBar1.getChildren().addAll(skill1, skill2);
		skillBar2.getChildren().addAll(skill3, skill4);
		skillsContainer.getChildren().addAll(btsBack, skillBar1,skillBar2);
	}
	//Creates the Listener for each itemm in the inventory
	private void createItemsListener() {
		btiBack.setOnAction(e->{
			window.setBottom(actionBarUI);
		});
		item1.setOnAction(e->{
			if(consumableList[0].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[0]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item1.setDisable(true);
					consumableList[0] = null;
					item1.setText("");
				}
			}
			else if(consumableList[0].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[0]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item1.setDisable(true);
					consumableList[0] = null;
					item1.setText("");
				}
			}
		});
		item2.setOnAction(e->{
			if(consumableList[1].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[1]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					item2.setDisable(true);
					consumableList[1] = null;
					item2.setText("");
				}
			}
			else if(consumableList[1].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[1]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item2.setDisable(true);
					consumableList[1] = null;
					item2.setText("");
				}
			}
		});
		item3.setOnAction(e->{
			if(consumableList[2].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[2]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item3.setDisable(true);
					consumableList[2] = null;
					item3.setText("");
				}
			}
			else if(consumableList[2].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[2]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item3.setDisable(true);
					consumableList[2] = null;
					item3.setText("");
				}
			}
		});
		item4.setOnAction(e->{
			if(consumableList[3].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[3]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item4.setDisable(true);
					consumableList[3] = null;
					item4.setText("");
				}
			}
			else if(consumableList[3].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[3]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item4.setDisable(true);
					consumableList[3] = null;
					item4.setText("");
				}
			}
		});
		item5.setOnAction(e->{
			if(consumableList[4].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[4]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item5.setDisable(true);
					consumableList[4] = null;
					item5.setText("");
				}
			}
			else if(consumableList[4].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[4]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item5.setDisable(true);
					consumableList[4] = null;
					item5.setText("");
				}
			}
		});
		item6.setOnAction(e->{
			if(consumableList[5].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[5]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item6.setDisable(true);
					consumableList[5] = null;
					item6.setText("");
				}
			}
			else if(consumableList[5].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[5]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item6.setDisable(true);
					consumableList[5] = null;
					item6.setText("");
				}
			}
		});
		item7.setOnAction(e->{
			if(consumableList[6].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[6]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item7.setDisable(true);
					consumableList[6] = null;
					item7.setText("");
				}
			}
			else if(consumableList[6].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[6]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item7.setDisable(true);
					consumableList[6] = null;
					item7.setText("");
				}
			}
		});
		item8.setOnAction(e->{
			if(consumableList[7].getConsumable_HP()>0) {
				if(consumableList[7].getConsumable_HP()>0) {
					if(player.getCurrentHP()!=player.getMaxHP()) {
						player.useConsumable(consumableList[7]);
						player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
						player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
						item8.setDisable(true);
						consumableList[7] = null;
						item8.setText("");
					}
				}
			}
			else if(consumableList[7].getConsumable_MP()>0) {
				if(consumableList[7].getConsumable_MP()>0) {
					if(player.getCurrentMP()!=player.getMaxMP()) {
							player.useConsumable(consumableList[7]);
							player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
							item8.setDisable(true);
							consumableList[7] = null;
							item8.setText("");
					}
				}
			}
		});
		item9.setOnAction(e->{
			if(consumableList[8].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[8]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item9.setDisable(true);
					consumableList[8] = null;
					item9.setText("");
				}
			}
			else if(consumableList[8].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[8]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item9.setDisable(true);
					consumableList[8] = null;
					item9.setText("");
				}
			}
		});
		item10.setOnAction(e->{
			if(consumableList[9].getConsumable_HP()>0) {
				if(player.getCurrentHP()!=player.getMaxHP()) {
					player.useConsumable(consumableList[9]);
					player_HP.setText(String.valueOf(player.getCurrentHP()+"/"+player.getMaxHP()));
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item10.setDisable(true);
					consumableList[9] = null;
					item10.setText("");
				}
			}
			else if(consumableList[9].getConsumable_MP()>0) {
				if(player.getCurrentMP()!=player.getMaxMP()) {
					player.useConsumable(consumableList[9]);
					player_MP.setText(String.valueOf(player.getCurrentMP()+"/"+player.getMaxMP()));
					item10.setDisable(true);
					consumableList[9] = null;
					item10.setText("");
				}
			}
		});
	}
//	Creates the Skill Menu
	private void showSkills() {
		window.setBottom(null);
		window.setBottom(skillsContainer);
		createSkillsListener();
	}
//	Skills Menu Listener
	private void createSkillsListener() {
		btsBack.setOnAction(e->{
			window.setBottom(actionBarUI);
		});
		skill1.setOnAction(e->{
		});
	}
//	Compiles the Creation of the Battle Window, containing all the player's information and enemy information (including sprites)
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
//	Manages the User Interface when the turn alternates
	private void createTurnOrderUI() {
		turnEntity.setText("Player's turn");
		turnNum = 1;
		turnNumText.setText("Turn "+turnNum);
		turnOrderContainer.getChildren().addAll(turnEntity, turnNumText);
		window.setLeft(turnOrderContainer);
	}
//	Creates the Listeners for the Player's Actions
	private void battle() {
			createActionBarListeners();
	}
//	This section controls the actions when the player hits a GameOver
	private void gameOverScreen() {
		System.out.println("Player Lost");
		window.setCenter(null);
		window.setTop(null);
		window.setBottom(null);
		window.setLeft(null);
		gameOverTxt.setText("You have lasted " + pathFloor + " paths, but unfortunately passed a tragic death");
		window.setCenter(gameOverContainer);
		window.setBottom(restartContainer);
		setRestartListener();
	}
//	Assigns the GameOver and Restart Container
	private void presetGameOverScreen() {
		gameOverContainer.getChildren().addAll(gameOverTxt);
		restartContainer.getChildren().addAll(btRestart);
	}
//	Exit Listener to save the player's stats and closes the run
	private void setRestartListener() {
		btRestart.setOnAction(e->{
			window.setTop(null);
			window.setCenter(null);
			window.setLeft(null);
			window.setRight(null);
			window.setBottom(null);
			player.saveStats(pathFloor);
			Platform.exit();
		});
	}
//	Enables UI buttons when the player is able to make their turn
	private void enableButtons() {
		btAttack.setDisable(false);
		btSkills.setDisable(false);
		btDefend.setDisable(false);
		btItem.setDisable(false);
	}
//	Disables the UI when it is not the player's turn
	private void disableButtons() {
		btAttack.setDisable(true);
		btSkills.setDisable(true);
		btDefend.setDisable(true);
		btItem.setDisable(true);
	}
//	This method controls the actions taken during the enemy's turn
	private void enemyTurn() {
		if(enemy.getCurrentHP()>0) {
			attack(enemy, player);
		}
		else {
			actionBarUI.getChildren().clear();
			playerContainer.getChildren().clear();
			enemyContainer.getChildren().clear();
//			Upgrade Player's stats when winning battle
			player.battleReward();
			new ChoosePath(window, pathFloor, player);
		}
		turnEntity.setText("Player's turn");
		if(player.getCurrentHP()<=0) {
			gameOverScreen();
		}
		
	}
//	This method controls the delay for the enemy once the player has made up their turn
	private void enemyTurnDelay() {
		turnEntity.setText("Enemy's turn");
		disableButtons();
		enemyImage.setImage(new Image(enemy.changeAttackSprite()));
		e_pause.setOnFinished(f->{
			enemyTurn();
			enableButtons();
			turnNum++;
			turnNumText.setText("Turn "+turnNum);
			enemyImage.setImage(new Image(enemy.changeIdleSprite()));
		});
		e_pause.play();
	}
//	Controls the actions of the Attack Button Functionality for the player
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
//	Controls the Defend Button Functionality for the player
	private void defend(Entity e1) {
		int tempDefense = (int)(player.getDef()*.5);
		player.setDef(player.getDef()+tempDefense);
		enemyTurn();
		player.setDef(player.getDef()-tempDefense);
	}
//	Action Bar Listeners for the Attack, Skill, Defend, and Item Buttons on the User Interface
	private void createActionBarListeners() {
		btAttack.setOnAction(e->{
			p_pause.setOnFinished(f->{
				playerImage.setImage(new Image(player.changeIdleSprite()));
				if(enemy.getCurrentHP()<=0) {
//					new ChoosePath(window, pathFloor, player);
				}
			});
			playerImage.setImage(new Image(player.changeAttackSprite()));
			p_pause.play();
			
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
	//This method creates a new enemy for the player to fight
	private void createEnemy() {
//		Retrieves Enemy Information
		enemy = new Enemy(pathFloor);							//generate new energy object
		setEnemyInfo();
	}
//	Sets Enemy Information Containers
	private void setEnemyInfo() {
		enemy_HP = enemy.display_HPStat();
		enemy_MP = enemy.display_MPStat();
		enemyStats.getChildren().addAll(enemy_HP, enemy_MP);
		enemyImageContainer.getChildren().add(enemyImage = new ImageView(enemy.getEntity_sprite()));
		enemyContainer.getChildren().addAll(enemyImageContainer,enemyStats);
	}
//	Sets Player Information Containers
	private void setPlayerInfo() {
//		Retrieves Player Information
		player_HP = player.display_HPStat();
		player_MP = player.display_MPStat();
		playerStats.getChildren().addAll(player_HP, player_MP);
		playerImageContainer.getChildren().add(playerImage = new ImageView(player.getEntity_sprite()));
		playerContainer.getChildren().addAll(playerImageContainer,playerStats);
	}
//	Customizes the User Interface for the Battle
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
		skillsContainer.setPadding(new Insets(0,0,20,0));
		btsBack.setStyle(btSkillStyle);
		skill1.setStyle(btSkillStyle);
		skill2.setStyle(btSkillStyle);
		skill3.setStyle(btSkillStyle);
		skill4.setStyle(btSkillStyle);
		skill1.setPrefWidth(btSkillWidth);
		skill2.setPrefWidth(btSkillWidth);
		skill3.setPrefWidth(btSkillWidth);
		skill4.setPrefWidth(btSkillWidth);
		skillBar1.setAlignment(Pos.CENTER);
		skillBar2.setAlignment(Pos.CENTER);
		
//		Standard ActionBar UI
		actionBarUI.setPadding(new Insets(0,0,20,0));
		btAttack.setPrefWidth(buttonWidth);
		btSkills.setPrefWidth(buttonWidth);
		btDefend.setPrefWidth(buttonWidth);
		btItem.setPrefWidth(buttonWidth);
		action1Bar.setAlignment(Pos.CENTER);
		action2Bar.setAlignment(Pos.CENTER);
		
		turnOrderContainer.setPadding(new Insets(0, 0, 0, 20));
		
//		Items
		String btItemStyle = "-fx-font-size:20";
		int btItemWidth = 185;
		itemsContainer.setPadding(new Insets(0,0,20,0));
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
		
		
		btRestart.setStyle(btStyle20);
		btRestart.setPrefWidth(buttonWidth);
		restartContainer.setPadding(new Insets(0,0,0,0));
		btRestart.setAlignment(Pos.CENTER);
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
		restartContainer.setAlignment(Pos.CENTER);
		
//		Top Progression
		String pathProgression = "-fx-font-size:28";
		pathProgressText.setStyle(pathProgression);
		pathProgressBox.setAlignment(Pos.CENTER);
	}
}
