package com.jadd.easyrestro;

public class Item {

    String name;
    String category;
    int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Item(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
}
