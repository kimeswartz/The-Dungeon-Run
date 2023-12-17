package com.kimswartz.app.menuView;

import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.colors.ChooseColors.YELLOW;

public class FightOrFleeMenu {

    public static void displayGameMenu(){
        // Menu options
        System.out.println("\nWhat should you do?");
        System.out.println(YELLOW + "[1] Fight!");
        System.out.println("[2] Flee");
        System.out.println("[3] Player Status");
        System.out.println("[4] Back to main menu");

    }

}
