package com.kimswartz.app.gamePlay;

import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.colors.ChooseColors.YELLOW;
import static com.kimswartz.app.myScanner.MyScanner.scan;

public class PlayerWin {

    // Metod som hämtar om player vinner med att slå alla monsyer eller klarar till nivå 8.

    public static void winTheGame() {
        System.out.println(YELLOW + "Congratulations! You have defeated all the monsters and have thus won the game! Good work!" + RESET);
        scan.close();
        System.exit(0);

    }


    public static void winTheGameByLevel() {
        System.out.println(YELLOW + "Congratulations! You made it to the final level '8', You have completed the game!" + YELLOW);
        scan.close();
        System.exit(0);
    }


}