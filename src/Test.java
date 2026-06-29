import database.SQL_Db;
import entity.Enemy;
import entity.Entity;
import entity.Player;
import items.Equipment;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		String insertStatement = "INSERT INTO player_run ";
		insertStatement += "VALUES("+2+", \""+"lmao"+"\", "+2+", "+3+", "+ 3+");";
		System.out.println(insertStatement);
//		System.out.println("Enemy");
//		Enemy testEnemy= new Enemy(1);
//		System.out.println();
//		System.out.println("Player");
//		Player newPlayer = new Player();
//		System.out.println();
//		
//		Scanner scanner = new Scanner(System.in);
//		while(testEnemy.getCurrentHP()>0) {
//			System.out.println("Please select what you would like to do");
//			System.out.printf("1. Attack\n2. Defend\n3. Use Skill\n4. Use Item\n");
//			int x = scanner.nextInt();
//			switch(x) {
//			case 1:		//Attack
//				System.out.println("You have chosen to attack");
//				attack(newPlayer, testEnemy);
//				if(testEnemy.getCurrentHP()<=0)
//					break;
//				System.out.println("Enemy turn");
//				attack(testEnemy, newPlayer);
//				break;
//			case 2:		//Defend
//				System.out.println("You have chosen to Defend");
//				defend(newPlayer, testEnemy);
//				break;
//			}
//		}
//		SQL_Db test = new SQL_Db();
//		test.close();
//		Object[] test = new Object[5];
//		test[0] = 1;
//		test[1] = 2;
//		test[2] = 2;
//		test[3] = 4;
//		for(int x=0; x<test.length;x++) {
//			System.out.println(test[x]);
//			if(test[x]==null) {
//				test[x]= new Equipment();
//				break;
//			}
//			System.out.println("all filled");
//		}
	}
	private static void attack(Entity player, Entity e2) {
		int damage = player.getAtk()-e2.getDef();
		if(damage < 0) {
			System.out.println("No damage was taken");
			System.out.println(e2.getCurrentHP() + " HP remains on the enemy");
			return;
		}
		e2.setCurrentHP(e2.getCurrentHP()-damage);
		System.out.println(damage + " damage has been done");
		if(e2.getCurrentHP()>0)
			System.out.println(e2.getCurrentHP() + " HP remains on the enemy");
		else
			System.out.println("Enemy has been defeated");
		System.out.println();
	}
	private static void defend(Entity player, Entity e2) {
		int tempDefense = (int)(player.getDef()*.5);
		System.out.println("You defended and gained " + tempDefense + " temporary defense");
		player.setDef(player.getDef()+tempDefense);
		System.out.println();
		System.out.println("Enemy turn");
		attack(e2, player);
		player.setDef(player.getDef()-tempDefense);
		System.out.println();
	}
}
