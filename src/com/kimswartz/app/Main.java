package com.kimswartz.app;

import com.kimswartz.app.gamePlay.GameLogics;
import com.kimswartz.app.menuView.MainMenuView;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to the game - You are in a dark dungeon with passages guarded by filthy monsters.\n" +
                "Get out by improving your skills, growing stronger and wiping out those creatures!");

        GameLogics.createAndSetPlayer();
        // GameLogics.getFightMenu();
        MainMenuView.mainMenuView();

    }
}