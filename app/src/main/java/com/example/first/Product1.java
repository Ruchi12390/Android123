package com.example.first;

public class Product1 {
    private int id; // Add an ID field to uniquely identify each product
    private String name;
    private String description;
    private String price;
    private String imageUri;

    // Constructor
    public Product1(int id, String name, String description, String price, String imageUri) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUri = imageUri;
    }

    // Getters
    public int getId() {
        return id; // Get the product ID
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUri() {
        return imageUri;
    }
}
