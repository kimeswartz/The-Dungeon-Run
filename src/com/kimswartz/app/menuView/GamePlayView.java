package com.kimswartz.app.menuView;

import com.kimswartz.app.gamePlay.GameLogics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.myScanner.MyScanner.scan;

public class GamePlayView {

    public static void getGameView() {

        while (true) {

            System.out.println("Gameplay Menu:");
            System.out.println(YELLOW + "[1]" + RESET + " Fight a monster");
            System.out.println(YELLOW + "[2]" + RESET + " Get player status");
            System.out.println(BLUE + "[3]" + RESET + " Go to store");
            System.out.println(RED + "[4]" + RESET + " Exit the game");

            String userInput = scan.nextLine();

            // Validate user input using regular expression
            if (isValidInput(userInput)) {
                int choice = Integer.parseInt(userInput);

                switch (choice) {
                    case 1 -> GameLogics.startTheGame();
                    case 2 -> GameLogics.checkPlayerStatus();
                    case 3 -> StoreView.getStoreMenu();
                    case 4 -> GameLogics.exitGame();
                    default -> System.out.println("Invalid input, try again!");
                }

            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }

        }
    }

    private static boolean isValidInput(String input) {
        // Use a regular expression to check if the input is a positive integer
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}