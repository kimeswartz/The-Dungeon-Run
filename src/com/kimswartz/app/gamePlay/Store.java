package com.kimswartz.app.gamePlay;

import com.kimswartz.app.fighters.Player;
import com.kimswartz.app.menuView.GamePlayView;
import com.kimswartz.app.storeItems.Book;
import com.kimswartz.app.storeItems.Exercise;
import com.kimswartz.app.storeItems.FirstAid;
import com.kimswartz.app.storeItems.Product;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.colors.ChooseColors.RESET;
import static com.kimswartz.app.myScanner.MyScanner.scan;

public class Store {

    public static void displayStore(Player player) {
        while (true) {

            Product addHealth = new FirstAid("Healthy little pill", 15, 80,
                    "This little red pill will improve your health... Probably!");
            System.out.println(addHealth.toString());
            System.out.println(YELLOW
                    + "Enter [A] to buy item\n"
                    + RESET);

            Product addIntelligence = new Book("'Think Faster' - Book", 20, 15,
                    "It is said that clever writings in this book train your intelligence!");
            System.out.println(addIntelligence.toString());
            System.out.println(YELLOW
                    + "Enter [B] to buy item\n"
                    + RESET);

            Product addAgility = new Exercise("A pink skipping rope", 10, 15,
                    "Skipping ropes are said to be good for improving agility!");
            System.out.println(addAgility.toString());
            System.out.println(YELLOW
                    + "Enter [R] to buy item\n"
                    + RESET);

            System.out.println(GREEN + "You have: " + player.getCoins() + " coins on your pocket!"
                    + RESET);

            if (player.getCoins() < 9) {
                System.out.println
                        (YELLOW + "Pockets almost empty! Looks like you need to go out among monsters to find more coins!");
            } else {
                System.out.println("Looks like you have enough coins to buy something that can power you up!"
                        + RESET);
            }

            System.out.print("Enter " + GREEN + "[G]"
                    + RESET + " to get back to 'Gameplay'\n");

            String input = scan.nextLine().toUpperCase(); // Convert to uppercase for case-insensitivity

            if (isValidStoreInput(input)) {

                // Handle 'Y' or 'N' as needed
                switch (input) {

                    case "A" -> {
                        if (player.getCoins() >= addHealth.getPrice()) {
                            player.setHealth(player.getHealth() + addHealth.getLevelUpSkill());
                            player.setCoins(player.getCoins() - addHealth.getPrice());
                            System.out.println(GREEN + "Good choice! Your health has improved significantly!"
                                    + "[ +" + addHealth.getLevelUpSkill() + " ]" + RESET);
                        } else {
                            System.out.println(YELLOW + "You need " + (addHealth.getPrice() - player.getCoins())
                                    + " more coins to be able to buy this item!" + RESET);
                        }
                    }
                    case "B" -> {
                        if (player.getCoins() >= addIntelligence.getPrice()) {
                            player.setIntelligence(player.getIntelligence() + addIntelligence.getLevelUpSkill());
                            player.setCoins(player.getCoins() - addIntelligence.getPrice());
                            System.out.println(GREEN + "Good choice! Intelligence has improved significantly!"
                                    + "[ +" + addIntelligence.getLevelUpSkill() + " ]" + RESET);
                        } else {
                            System.out.println(YELLOW + "You need " + (addIntelligence.getPrice() - player.getCoins())
                                    + " more coins to be able to buy this item!" + RESET);
                        }
                    }
                    case "R" -> {
                        if (player.getCoins() >= addAgility.getPrice()) {
                            player.setAgility(player.getAgility() + addAgility.getLevelUpSkill());
                            player.setCoins(player.getCoins() - addAgility.getPrice());
                            System.out.println(GREEN + "Good choice! Your agility skills are improved significantly!"
                                    + "[ +" + addAgility.getLevelUpSkill() + " ]" + RESET);
                        } else {
                            System.out.println(YELLOW + "You need " + (addAgility.getPrice() - player.getCoins())
                                    + " more coins to be able to buy this item!" + RESET);
                        }
                    }
                    case "G" -> GamePlayView.getGameView();
                }
                break; // Exit the loop when valid input is received
            } else {
                System.out.println(RED + "Invalid input. Please enter what is indicated to purchase item or leave." + RESET);
            }

        }

    }

    // Regex for store
    private static boolean isValidStoreInput(String input) {
        String regex = "^[AaBbRrGg]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
