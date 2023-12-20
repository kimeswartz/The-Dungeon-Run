package com.kimswartz.app.gamePlay;

import com.kimswartz.app.DungeonGameDatabase;
import com.kimswartz.app.fighters.Player;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.myScanner.MyScanner.scan;
import java.util.Random;

public class SetPlayer {

    public static int setAndGreetPlayer(Player player) {

        System.out.println(YELLOW + "Before it begins, enter your name:" + RESET);
        player.setName(scan.nextLine());
        System.out.println("You are all set, " + GREEN + player.getName() + RESET);

        player.setHealth(70);
        player.setStrength(40);
        player.setAgility(15);
        player.setIntelligence(15);
        player.setExperience(0);
        player.setLevel(1);
        player.setCoins(10);
        player.setDamage(10);

        // Create ID fot Database- handling
        Random random = new Random();

        // Generate a random number between 1 and 500
        int randomNumber = random.nextInt(1000) + 1;

        player.setNumber(randomNumber);
        return randomNumber;
    }

}

