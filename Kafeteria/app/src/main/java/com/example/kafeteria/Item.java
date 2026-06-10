package com.example.kafeteria;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String name;
    private String details1;
    private String details2;
    private String imageUrl;

    public Item(int id, String name, String details1, String details2, String imageUrl) {
        this.id = id;
        this.name = name;
        this.details1 = details1;
        this.details2 = details2;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDetails1() { return details1; }
    public String getDetails2() { return details2; }
    public String getImageUrl() { return imageUrl; }

    @Override
    public String toString() {
        return name;
    }
}
