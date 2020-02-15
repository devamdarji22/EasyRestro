package com.jadd.easyrestro;

import java.util.List;

public class Table {
    int tableNumber;
    boolean isEmpty;
    Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Table(int tableNumber, boolean isEmpty, Cart cart) {
        this.tableNumber = tableNumber;
        this.isEmpty = isEmpty;
        this.cart = cart;
    }
    public Table(int tableNumber, boolean isEmpty) {
        this.tableNumber = tableNumber;
        this.isEmpty = isEmpty;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }


}
