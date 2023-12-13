package com.kimswartz.app.fighters;

import static com.kimswartz.app.colors.ChooseColors.GREEN;
import static com.kimswartz.app.colors.ChooseColors.RESET;

public class Player extends Combat {

    private String name;
    private int intelligence;
    private int experience;
    private int agility;
    private int level;
    private int coins;

    public Player(int strength, int health, int damage, String name, int intelligence, int experience, int agility, int level, int coins) {
        super(strength, health, damage);
        this.name = name;
        this.intelligence = intelligence;
        this.experience = experience;
        this.agility = agility;
        this.level = level;
        this.coins = coins;
    }

    public Player() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void getPlayerInfo() {

        System.out.println(GREEN + "Player name: " + getName());
        System.out.println("Level: " + getLevel());
        System.out.println("Health: " + getHealth());
        System.out.println("Agility: " + getAgility());
        System.out.println("Strength: " + getStrength());
        System.out.println("Coins: " + getCoins() + RESET);

    }

    public void increaseLevel(int amountOfExp) {
        experience = amountOfExp;

        // Player levels up at '50 points.
        if (amountOfExp == 50) {
            setLevel(getLevel() + 1);
            setExperience(0);
            setStrength(getStrength() + 2);
            setIntelligence(getIntelligence() + 2);
            setAgility(getAgility() + 2);

        }
        System.out.println("Player level: ");
        System.out.println(getLevel());
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String toString() {
        return name + ", you have " + getStrength() + "* strength and health " + getHealth() + "*";
    }
}