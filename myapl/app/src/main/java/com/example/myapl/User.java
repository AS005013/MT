package com.example.myapl;

public class User {

    private final int id;
    private final String name;
    private final String imageUrl;

    public User(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return name;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public int getId() {
        return  id;
    }
}
