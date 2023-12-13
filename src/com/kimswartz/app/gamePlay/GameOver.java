package com.kimswartz.app.gamePlay;

import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;

import static com.kimswartz.app.myScanner.MyScanner.scan;

public class GameOver {

    public static void getGameOverLogic(Player player, Monster monster) {

        gameOverIfPlayerDies(player, monster);
        gameOver(player);

    }

    public static void gameOverIfPlayerDies(Player player, Monster monster) {

        if (monster.getHealth() <= 0 && player.getHealth() <= 0) {
            System.out.println("What a messy fight! Both you and " + monster + " died.");
            gameOver(player);
        } else if (player.getHealth() <= 0) {
            System.out.println("\nYou were defeated by " + monster);
            gameOver(player);
        }

    }

    public static void gameOver(Player player) {
        System.out.println("Better luck next time, game over!");
        displayPlayerStatus(player);
        scan.close();
        System.exit(0);
    }

    public static void displayPlayerStatus(Player player){
        System.out.println("Player status: ");
        player.getPlayerInfo();

    }


}
