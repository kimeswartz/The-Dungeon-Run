package com.kimswartz.app.gamePlay;

import com.kimswartz.app.DungeonGameDatabase;
import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;

import java.util.Random;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.gamePlay.GameLogics.monsterList;

public class Flee {

    public static void playerFleeTheFight(Player player, Monster monster) {

        DungeonGameDatabase autoSaveMonsters = new DungeonGameDatabase();
        autoSaveMonsters.monsterToDatabase(monster, player);

        Random random = new Random();

        while (true) {
            int min = 50; // Minimum value of range
            int max = 100; // Maximum value of range

            // Calculates the player's chances to escape in method
            int randomFleePoints = (int) Math.floor(Math.random() * (max - min + 1) + min);

            // Rewards the player if he escapes
            if (GameLogics.playerFlee() + randomFleePoints > (monster.getStrength() + randomFleePoints)) {
                System.out.println("You successfully managed to escape and were rewarded with more "
                        + GREEN + "agility skills!" + RESET);

                player.setAgility(player.getAgility() + 2);
                player.setIntelligence(player.getIntelligence() + 2);
                monsterList.remove(monster);

            } else {

                // The player loses in health if he fails to escape
                System.out.println("You tried to flee, but the monster caught up and attacks you.");
                int monsterDamage = random.nextInt(monster.getDamage()) + 30;
                player.setHealth(player.getHealth() - monsterDamage);
                System.out.println("The monster "
                        + PURPLE + monster.getName() + RESET
                        + " caused -"
                        + PURPLE + monsterDamage + RESET
                        + " damage on you!");
            }

            // Game over if players health is <= 0
            if (player.getHealth() <= 0) {
                System.out.println("\nYou were defeated by " + monster + " Game over!");
                GameOver.gameOver(player);
            }
            break;
        }
    }
}