package com.jadd.easyrestro;

import java.util.List;
import java.util.Map;

public class Cart {

    String category;
    Item item;
    int quantity;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart(String category, Item item, int quantity) {
        this.category = category;
        this.item = item;
        this.quantity = quantity;
    }

    public Cart() {
    }
}