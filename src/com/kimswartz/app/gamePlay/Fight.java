package com.kimswartz.app.gamePlay;

import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;

import java.util.Random;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.gamePlay.GameLogics.monsterList;

public class Fight {

    public static void playerAndMonsterFight(Player player, Monster monster) {

        // Loop the attack(s) while health is > 0
        while (player.getHealth() > 0 && monster.getHealth() > 0) {

            // Set attackSum for monster & player
            Random randomAttack = new Random();
            int randomMonsterAttack = randomAttack.nextInt(monster.getStrength()) + monster.getStrength();
            int randomPlayerAttack = randomAttack.nextInt(player.getStrength()) + player.getStrength();

            // Set the damageSum for monster & player
            int playerDamage = player.calculateDamage() + randomPlayerAttack;
            int monsterDamage = monster.calculateDamage() + randomMonsterAttack;

            // Player initial attack:
            monster.setHealth(monster.getHealth() - playerDamage);
            System.out.println
                    (GREEN + player.getName() + RESET
                            + " attacks and deals "
                            + GREEN + playerDamage + RESET
                            + " damage on the monster "
                            + PURPLE + monster.getName() + " [Health status: " + monster.getHealth() + "]" + RESET);

            // Reward if player outsmart the monster with 'Intelligence' skills.
            if (GameLogics.playerOutsmartedMonster() <= player.getIntelligence()) {
                System.out.println
                        (GREEN + player.getName() + RESET
                                + " outsmarted the monster - "
                                + PURPLE + monster.getName() + RESET
                                + " struck himself! "
                                + GREEN + player.getName() + RESET
                                + " earned some new intelligence skills!");
                monster.setHealth(monster.getHealth() - monsterDamage);
                player.setIntelligence(player.getIntelligence() + 5);
            }

            // Player rewarded with coins
            if (GameLogics.playerGetCoins() < 30) {
                System.out.println(PURPLE + monster.getName() + RESET
                        + " dropped some coins that you managed to catch!");
                player.setCoins(player.getCoins() + GameLogics.playerGetCoins());
            }

            // Reward with 'agility skills' if player dodge the attack.
            if (GameLogics.playerDidDodge() <= player.getAgility()) {
                System.out.println
                        (PURPLE + monster.getName() + RESET
                                + " struck back and missed its blow - "
                                + GREEN + player.getName() + RESET
                                + " got new agility skills!");
                player.setAgility(player.getAgility() + 8);
            }

            // player wins the attack and is rewarded
            if (monster.getHealth() <= 0 && player.getHealth() >= 1) {
                System.out.println

                        ("Good job " + GREEN + player.getName() + RESET
                                + " - "
                                + PURPLE + monster.getName() + RESET
                                + " was defeated, which means he's now gone!"
                                + GREEN + " You are rewarded with more skills!" + RESET);

                player.setExperience(player.getExperience() + 25);
                player.increaseLevel(player.getExperience());
                monsterList.remove(monster);
                player.setHealth(player.getHealth() + 10);
                player.setCoins((player.getCoins() + 2));

                break;

            } else {

                // the monster continues to attack as long as health is > 0
                player.setHealth(player.getHealth() - monsterDamage);
                System.out.println
                        (PURPLE + monster.getName() + RESET
                                + " strikes back and deals "
                                + PURPLE + monsterDamage + RESET
                                + " damage on "
                                + GREEN + player.getName() + " [Health status: " + player.getHealth() + "]" + RESET);
            }

            GameOver.gameOverIfPlayerDies(player, monster);

        }
    }

}