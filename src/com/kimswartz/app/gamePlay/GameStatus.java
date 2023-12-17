package com.kimswartz.app.gamePlay;

import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.gamePlay.GameLogics.monsterList;

public class GameStatus {

    public static void displayGameStatus(Player player, Monster monster) {

        System.out.println(YELLOW + "\n--- Level " + player.getLevel() + " ---" + RESET);

        System.out.println("Amount of monsters left to defeat: "
                + PURPLE + monsterList.size() + RESET
                + YELLOW + "/11" + RESET);

        System.out.println
                (PURPLE_BOLD + "The filthy monster " + monster.getName()
                        + " appears, with health status of: " + monster.getHealth()
                        + ", Strength is: " + monster.getStrength()
                        + RESET);

        recommendPlayerToImproveHealth(player, monster);

        // Game starts here
        System.out.println
                (GREEN + player.getName() + " - Your health is: [" + player.getHealth()
                        + "], and strength is [" + player.getStrength() + "]"
                        + RESET);

    }

    public static void recommendPlayerToImproveHealth(Player player, Monster monster) {

        if (player.getStrength() < monster.getStrength() + 10) {
            System.out.println("The monster is a bit stronger than you, see if you can improve your strength in the shop!");
        } else if (player.getStrength() > monster.getStrength() + 10) {
            System.out.println("You are stronger than the monster, which means you have a better chance of winning the battle!");
        }

    }

}
