package com.kimswartz.app.menuView;

import com.kimswartz.app.gamePlay.GameLogics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.myScanner.MyScanner.scan;

public class StoreView {

    public static void getStoreMenu() {

        while (true) {

            System.out.println("Store menu: ");
            System.out.println(BLUE + "In the shop you can find items that can help you defeat monsters. " +
                    "To buy, you need coins, which the monsters obtain.");

            System.out.println(BLUE + "[1]" + RESET + " Buy items");
            System.out.println(BLUE + "[2]" + RESET + " Back to 'Gameplay' menu");

            String userInput = scan.nextLine();

            // Validate user input using regex
            if (isValidInput(userInput)) {
                int choice = Integer.parseInt(userInput);

                switch (choice) {
                    case 1 -> GameLogics.playerOpenStore();
                    case 2 -> GamePlayView.getGameView();
                    default -> System.out.println("Invalid input, please try again!");
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