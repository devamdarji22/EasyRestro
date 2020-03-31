package com.jadd.easyrestro.classes;

public class Employee extends User {

    String restaurantName,ownerName;

    public Employee(String restaurantName, String ownerName) {
        this.restaurantName = restaurantName;
        this.ownerName = ownerName;
    }

    public Employee() {
    }

    public Employee(String name, String email, String phone, String restaurantName, String ownerName) {
        super(name, email, phone);
        this.restaurantName = restaurantName;
        this.ownerName = ownerName;
    }
    public Employee(User user, String restaurantName, String ownerName) {
        super(user.getName(), user.getEmail(), user.getPhone());
        this.restaurantName = restaurantName;
        this.ownerName = ownerName;
    }
}
