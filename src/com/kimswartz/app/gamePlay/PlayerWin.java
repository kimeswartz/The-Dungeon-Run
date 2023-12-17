package com.kimswartz.app.gamePlay;

import com.kimswartz.app.DungeonGameDatabase;
import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;

import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.colors.ChooseColors.YELLOW;
import static com.kimswartz.app.myScanner.MyScanner.scan;

public class PlayerWin {


    public static void winTheGame(Player player) {

        System.out.println(YELLOW + "Congratulations! You have defeated all the monsters and have thus won the game! Good work!" + RESET);
        DungeonGameDatabase registerWinner = new DungeonGameDatabase();
        registerWinner.registerWinnerPlayer(player);
        scan.close();
        System.exit(0);

    }

    public static void winTheGameByLevel(Player player) {
        System.out.println(YELLOW + "Congratulations! You made it to the final level '8', You have completed the game!" + YELLOW);
        DungeonGameDatabase registerWinner = new DungeonGameDatabase();
        registerWinner.registerWinnerPlayer(player);
        scan.close();
        System.exit(0);
    }

}