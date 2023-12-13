package com.kimswartz.app.gamePlay;

import com.kimswartz.app.fighters.Player;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.myScanner.MyScanner.scan;

public class SetPlayer {

    public static void setAndGreetPlayer(Player player) {

        System.out.println(YELLOW + "Before it begins, enter your name:" + RESET);
        player.setName(scan.nextLine());
        System.out.println("You are all set, " + GREEN + player.getName() + RESET);
        System.out.println(YELLOW + "Where would you like to begin?" + RESET);

        player.setHealth(70);
        player.setStrength(35);
        player.setAgility(15);
        player.setIntelligence(15);
        player.setExperience(0);
        player.setLevel(1);
        player.setCoins(10);
        player.setDamage(10);

    }

}