package com.kimswartz.app.fighters;

public class Monster extends Combat {

    private String name;

    public Monster(int strength, int health, int damage, String name) {
        super(strength, health, damage);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Monster " + name + " , has " + getStrength() + "* strength and health " + getHealth() + "*";
    }
}