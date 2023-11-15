package com.kimswartz.app.menuView;

import com.kimswartz.app.gamePlay.GameLogics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.myScanner.MyScanner.scan;

public class MainMenuView {

    public static void mainMenuView() {

        while (true) {

            System.out.println("Main Menu:");
            System.out.println(GREEN + "[1]" + RESET + " Get into the Game");
            System.out.println(YELLOW + "[2]" + RESET + " Check player status");
            System.out.println(YELLOW + "[3]" + RESET + " About the game");
            System.out.println(RED + "[4]" + RESET + " Exit the Game");

            String userInput = scan.nextLine();

            // Validate user input using regular expression
            if (isValidInput(userInput)) {
                int choice = Integer.parseInt(userInput);

                switch (choice) {
                    case 1 -> GamePlayView.getGameView();
                    case 2 -> GameLogics.checkPlayerStatus();
                    case 3 -> aboutTheGame();
                    case 4 -> GameLogics.exitGame();
                    default -> System.out.println("Invalid input, try again!");
                }

            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    private static void aboutTheGame() {

        System.out.println(YELLOW + "This is the game where you fight against different monsters.\n" +
                "You have to get to level 5, or wipe out all the monsters to pass the game.\n" +
                "You can collect coins by fighting, and for these you can improve your skills by shopping in the shop!"
                + "\n" +
                "Good Luck!" + RESET);

    }


    private static boolean isValidInput(String input) {
        // Use a regular expression to check if the input is a positive integer
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}