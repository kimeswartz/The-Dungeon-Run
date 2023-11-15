package com.kimswartz.app.fighters;

public abstract class Combat {

    private int strength;
    private int health;
    private int damage;

    public Combat(int strength, int health, int damage) {
        this.strength = strength;
        this.health = health;
        this.damage = damage;
    }

    public Combat() {
    }

    public int getStrength() {
        return strength;
    }


    public void setStrength(int strength) {
        this.strength = strength;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    public int calculateDamage() {

        int totalDamage = damage + (strength * 2 / 4 + 1);
        return totalDamage;
    }

}