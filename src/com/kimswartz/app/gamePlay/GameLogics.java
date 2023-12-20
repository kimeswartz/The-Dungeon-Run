package com.kimswartz.app.gamePlay;

import static com.kimswartz.app.myScanner.MyScanner.*;

import com.kimswartz.app.DungeonGameDatabase;
import com.kimswartz.app.menuView.FightOrFleeMenu;
import com.kimswartz.app.menuView.GamePlayView;
import com.kimswartz.app.fighters.Player;
import com.kimswartz.app.fighters.Monster;

import java.rmi.dgc.DGC;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogics {

    static Player player = new Player();
    static List<Monster> monsterList = new ArrayList<>();


    public static void createAndSetPlayer() {

        SetPlayer.setAndGreetPlayer(player);
        DungeonGameDatabase DGD = new DungeonGameDatabase();
        DGD.savePlayer(player);

        // Set Monsters and put them into monsterList;
        int[] monsterStrength = {25, 30, 35, 40, 45, 55, 66, 80, 20, 15, 10};
        int[] monsterHealth = {55, 60, 65, 70, 75, 85, 90, 95, 40, 30, 20};
        int[] monsterDamage = {5, 15, 20, 25, 30, 30, 20, 10, 8, 7, 6};
        String[] monsterNames = {"Dusty", "Muddy", "Clay", "Ashy", "Bloody", "Winy", "Inky", "Grassy", "Scratchy", "Foggy", "Musty"};

        for (int i = 0; i < 11; i++) {
            monsterList.add(new Monster(monsterStrength[i], monsterHealth[i], monsterDamage[i], monsterNames[i]));

        }

    }


    public static void startTheGame() {

        while (true) {

            // Auto Save to database
            DungeonGameDatabase autoSave = new DungeonGameDatabase();
            autoSave.autoSaveAndUpdatePlayer(player);


            // Check if player wins the game
            if (monsterList.isEmpty()) PlayerWin.winTheGame(player);
            if (player.getLevel() >= 8) PlayerWin.winTheGameByLevel(player);

            // Generate random monsters
            Random random = new Random();
            int randomIndex = random.nextInt(monsterList.size());
            Monster randomMonster = monsterList.get(randomIndex);

            // Samla Infrmation om Monstret:



            // Display game status
            GameStatus.displayGameStatus(player, randomMonster);

            // In-Game menu
            FightOrFleeMenu.displayGameMenu();


            // UserInput controlled by regex. (Checker is at the bottom of the file).
            String userInput = scan.nextLine();
            if (isValidInput(userInput)) {
                int choice = Integer.parseInt(userInput);
                switch (choice) {


                    case 1 -> Fight.playerAndMonsterFight(player, randomMonster);
                    case 2 -> Flee.playerFleeTheFight(player, randomMonster);
                    case 3 -> PlayerStatus.displayPlayerStatus(player);
                    case 4 -> GamePlayView.getGameView();

                    default -> System.out.println("Invalid input, please try again [ 1 - 4 ]");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number [ 1 - 4 ]");
            }
        }
    }


    public static void checkPlayerStatus() {
        player.getPlayerInfo();
    }


    public static int playerOutsmartedMonster() {
        int min = 1; // Minimum value of range
        int max = 100; // Maximum value of range
        int random_outsmart = (int) Math.floor(Math.random() * (max - min + 1) + min);
        int playerSmartPoints = player.getAgility() * (random_outsmart / player.getIntelligence());
        return playerSmartPoints;
    }


    public static int playerDidDodge() {
        int min = 1; // Minimum value of range
        int max = 100; // Maximum value of range
        int random_dodge = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return random_dodge;
    }


    public static int playerGetCoins() {
        int min = 5; // Minimum value of range
        int max = 100; // Maximum value of range
        int random_coin = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return random_coin;
    }


    public static int playerFlee() {
        int min = 1; // Minimum value of range
        int max = 100; // Maximum value of range

        int random_outsmart = (int) Math.floor(Math.random() * (max - min + 1) + min);
        int playerFleePoints = player.getIntelligence() + (random_outsmart / player.getAgility());
        return playerFleePoints;
    }


    // Below represents the shop
    public static void playerOpenStore() {
        Store.displayStore(player);
    }


    public static void exitGame() {
        System.out.println("Exit the game");
        scan.close();
        System.exit(0);
    }


    // Regex for fight menu
    private static boolean isValidInput(String input) {
        // Use a regular expression to check if the input is a positive integer
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}