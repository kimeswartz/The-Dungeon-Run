package com.kimswartz.app.gamePlay;

import static com.kimswartz.app.colors.ChooseColors.*;
import static com.kimswartz.app.myScanner.MyScanner.*;

import com.kimswartz.app.menuView.GamePlayView;
import com.kimswartz.app.storeItems.Book;
import com.kimswartz.app.fighters.Player;
import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.storeItems.Exercise;
import com.kimswartz.app.storeItems.FirstAid;
import com.kimswartz.app.storeItems.Product;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogics {

    static Player player = new Player();
    static List<Monster> monsterList = new ArrayList<>();

    public static void createAndSetPlayer() {
        System.out.println(YELLOW + "Before it begins, enter your name:" + RESET);
        player.setName(scan.nextLine());
        System.out.println("Fantastic " + GREEN + player.getName() + ", you are all set!" + RESET);
        System.out.println(YELLOW + "Where would you like to begin?" + RESET);

        player.setHealth(70);
        player.setStrength(35);
        player.setAgility(15);
        player.setIntelligence(15);
        player.setExperience(0);
        player.setLevel(1);
        player.setCoins(10);
        player.setDamage(10);

        // Set Monsters and put them into monsterList;
        int[] monsterStrength = {25, 30, 35, 40, 45, 55, 66, 80};
        int[] monsterHealth = {55, 60, 65, 70, 75, 85, 90, 95};
        int[] monsterDamage = {5, 15, 20, 25, 30, 30, 20, 10};
        String[] monsterNames = {"Dusty", "Muddy", "Clay", "Ashy", "Bloody", "Winy", "Inky", "Grassy"};

        for (int i = 0; i < 8; i++) {
            monsterList.add(new Monster(monsterStrength[i], monsterHealth[i], monsterDamage[i], monsterNames[i]));
        }
    }

    public static void startTheGame() {
        while (true) {

            // Player passes the game if 'monsterList' is Empty
            if (monsterList.isEmpty()) winTheGame();

            // Player passes the game if 'Level' is >= 5
            if (player.getLevel() >= 5) winTheGameByLevel();

            // Generate random monsters from the 'monsterList' Arraylist.
            Random random = new Random();
            int randomIndex = random.nextInt(monsterList.size());
            Monster randomMonster = monsterList.get(randomIndex);

            // Provides the player with general game status
            System.out.println(YELLOW + "\n--- Level " + player.getLevel() + " ---" + RESET);

            System.out.println("Amount of monsters left to defeat: "
                    + PURPLE + monsterList.size() + RESET
                    + YELLOW + "/8" + RESET);

            System.out.println
                    (PURPLE + "The filthy monster " + randomMonster.getName()
                            + " appears, with health status of: " + randomMonster.getHealth()
                            + ", Strength is: " + randomMonster.getStrength()
                            + RESET);

            // Game starts here
            System.out.println
                    (GREEN + player.getName() + " - Your health is: [" + player.getHealth()
                            + "], and strength is [" + player.getStrength() + "]"
                            + RESET);

            // Menu options
            System.out.println("\nWhat should you do?");
            System.out.println(YELLOW + "[1] Fight!");
            System.out.println("[2] Flee");
            System.out.println("[3] Player Status");
            System.out.println("[4] Back to Menu" + RESET);

            // UserInput controlled by regex. (Checker is at the bottom of the file).
            String userInput = scan.nextLine();
            if (isValidInput(userInput)) {
                int choice = Integer.parseInt(userInput);
                switch (choice) {

                    case 1 -> {

                        // Loop the attack(s) while health is > 0
                        while (player.getHealth() > 0 && randomMonster.getHealth() > 0) {

                            // Set attackSum for monster & player
                            Random randomAttack = new Random();
                            int randomMonsterAttack = randomAttack.nextInt(randomMonster.getStrength()) + randomMonster.getStrength();
                            int randomPlayerAttack = randomAttack.nextInt(player.getStrength()) + player.getStrength();

                            // Set the damageSum for monster & player
                            int playerDamage = player.calculateDamage() + randomPlayerAttack;
                            int monsterDamage = randomMonster.calculateDamage() + randomMonsterAttack;

                            // Player initial attack:
                            randomMonster.setHealth(randomMonster.getHealth() - playerDamage);
                            System.out.println
                                    (GREEN + player.getName() + RESET
                                            + " attacks and deals "
                                            + GREEN + playerDamage + RESET
                                            + " damage on the monster "
                                            + PURPLE + randomMonster.getName() + " [Health status: " + randomMonster.getHealth() + "]" + RESET);

                            // Reward if player outsmart the monster with 'Intelligence' skills.
                            if (playerOutsmartedMonster() <= player.getIntelligence()) {
                                System.out.println
                                        (GREEN + player.getName() + RESET
                                                + " outsmarted the monster - "
                                                + PURPLE + randomMonster.getName() + RESET
                                                + " struck himself! "
                                                + GREEN + player.getName() + RESET
                                                + " earned some new intelligence skills!");
                                randomMonster.setHealth(randomMonster.getHealth() - monsterDamage);
                                player.setIntelligence(player.getIntelligence() + 5);
                            }

                            // Player rewarded with coins
                            if (playerGetCoins() < 30) {
                                System.out.println(PURPLE + randomMonster.getName() + RESET
                                        + " dropped some coins that you managed to catch!");
                                player.setCoins(player.getCoins() + playerGetCoins());
                            }

                            // Reward with 'agility skills' if player dodge the attack.
                            if (playerDidDodge() <= player.getAgility()) {
                                System.out.println
                                        (PURPLE + randomMonster.getName() + RESET
                                                + " struck back and missed its blow - "
                                                + GREEN + player.getName() + RESET
                                                + " got new agility skills!");
                                player.setAgility(player.getAgility() + 8);
                            }

                            // player wins the attack and is rewarded
                            if (randomMonster.getHealth() <= 0 && player.getHealth() >= 1) {
                                System.out.println

                                        ("Good job " + GREEN + player.getName() + RESET
                                                + " - "
                                                + PURPLE + randomMonster.getName() + RESET
                                                + " was defeated, which means he's now gone!"
                                                + GREEN + " You are rewarded with more skills!" + RESET);

                                player.setExperience(player.getExperience() + 25);
                                player.increaseLevel(player.getExperience());
                                monsterList.remove(randomMonster);
                                player.setHealth(player.getHealth() + 10);
                                player.setCoins((player.getCoins() + 2));

                                break;

                            } else {

                                // the monster continues to attack as long as health is > 0
                                player.setHealth(player.getHealth() - monsterDamage);
                                System.out.println
                                        (PURPLE + randomMonster.getName() + RESET
                                                + " strikes back and deals "
                                                + PURPLE + monsterDamage + RESET
                                                + " damage on "
                                                + GREEN + player.getName() + " [Health status: " + player.getHealth() + "]" + RESET);
                            }

                            // Game over if both player and monster Health is <= 0
                            if (randomMonster.getHealth() <= 0 && player.getHealth() <= 0) {
                                System.out.println("What a messy fight! You both died.");
                                gameOver();
                            }

                            // Game over if players health is <= 0
                            if (player.getHealth() <= 0) {
                                System.out.println("\nYou were defeated by the monster!");
                                gameOver();
                            }
                        }
                    }

                    case 2 -> {

                        while (true) {
                            int min = 50; // Minimum value of range
                            int max = 100; // Maximum value of range

                            // Calculates the player's chances to escape in method
                            int randomFleePoints = (int) Math.floor(Math.random() * (max - min + 1) + min);

                            // Rewards the player if he escapes
                            if (playerFlee() + randomFleePoints > (randomMonster.getStrength() + randomFleePoints)) {
                                System.out.println("You successfully managed to escape and were rewarded with more "
                                        + GREEN + "agility skills!" + RESET);

                                player.setAgility(player.getAgility() + 2);
                                player.setIntelligence(player.getIntelligence() + 2);
                                monsterList.remove(randomMonster);

                            } else {

                                // The player loses in health if he fails to escape
                                System.out.println("You tried to flee, but the monster caught up and attacks you.");
                                int monsterDamage = random.nextInt(randomMonster.getDamage()) + 30;
                                player.setHealth(player.getHealth() - monsterDamage);
                                System.out.println("The monster "
                                        + PURPLE + randomMonster.getName() + RESET
                                        + " caused -"
                                        + PURPLE + monsterDamage + RESET
                                        + " damage on you!");
                            }

                            // Game over if players health is <= 0
                            if (player.getHealth() <= 0) {
                                System.out.println("\nYou were defeated by the monster!");
                                gameOver();
                            }
                            break;
                        }
                    }
                    case 3 -> checkPlayerStatus();
                    case 4 -> GamePlayView.getGameView();

                    default -> System.out.println("Invalid input, please try again!");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
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

    public static void exitGame() {
        System.out.println("Exit the game");
        scan.close();
        System.exit(0);
    }

    public static void gameOver() {
        System.out.println("Better luck next time, game over!");
        scan.close();
        System.exit(0);
    }

    public static void winTheGame() {
        System.out.println(YELLOW + "Congratulations! You have defeated all the monsters and have thus won the game! Good work!" + RESET);
        scan.close();
        System.exit(0);
    }

    public static void winTheGameByLevel() {
        System.out.println(YELLOW + "Congratulations! You made it to 'Level 5'. You have completed the game!" + YELLOW);
        scan.close();
        System.exit(0);
    }

    // Below represents the shop
    public static void playerOpenStore() {
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
                    "Skipping rope is said to be excellent for training your agility!");
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
                            System.out.println(GREEN + "Good choice! Intelligence has improved significantly!"
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

    // Regex for fight menu
    private static boolean isValidInput(String input) {
        // Use a regular expression to check if the input is a positive integer
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    // Regex for store
    private static boolean isValidStoreInput(String input) {
        String regex = "^[AaBbRrGg]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}