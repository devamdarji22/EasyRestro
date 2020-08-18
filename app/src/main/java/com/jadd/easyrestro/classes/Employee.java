package com.jadd.easyrestro.classes;

public class Employee extends User {

    String restaurantName, ownerUid;
    long id;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Employee(String restaurantName, String ownerUid,long id) {
        this.restaurantName = restaurantName;
        this.ownerUid = ownerUid;
        this.id = id;
    }

    public Employee(User user,long id) {
        super(user.getName(), user.getEmail(), user.getPhone());
        this.id = id;
    }

    public Employee(String name, String email, String phone, String restaurantName, String ownerUid) {
        super(name, email, phone);
        this.restaurantName = restaurantName;
        this.ownerUid = ownerUid;
    }
    public Employee(User user, String restaurantName, String ownerUid) {
        super(user.getName(), user.getEmail(), user.getPhone());
        this.restaurantName = restaurantName;
        this.ownerUid = ownerUid;
    }
}
