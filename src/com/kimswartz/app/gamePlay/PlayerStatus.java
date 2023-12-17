package com.kimswartz.app.gamePlay;

import com.kimswartz.app.DungeonGameDatabase;
import com.kimswartz.app.fighters.Player;
import com.kimswartz.app.gamePlay.DefeatedMonsters.*;

public class PlayerStatus {

    public static void displayPlayerStatus(Player player) {

        System.out.println(player.getName() + ", Here is your status: ");

        player.getPlayerInfo();
        DefeatedMonsters.printAllDefeatedMonsters();
    }

}
