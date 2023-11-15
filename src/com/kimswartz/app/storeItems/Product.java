package com.kimswartz.app.storeItems;

public abstract class Product {

    private String name;
    private int price;
    private int levelUpSkill;
    private String productDescription;

    public Product(String name, int price, int levelUpSkill, String productDescription) {
        this.name = name;
        this.price = price;
        this.levelUpSkill = levelUpSkill;
        this.productDescription = productDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLevelUpSkill() {
        return levelUpSkill;
    }

    public void setLevelUpSkill(int levelUpSkill) {
        this.levelUpSkill = levelUpSkill;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String toString() {
        return name + "\nPrice: " + getPrice() + "\n" + productDescription;
    }
}