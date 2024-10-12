package com.example.first;

public class Product {
    private String name;
    private String description;
    private String price;
    private String imageUri;

    public Product(String name, String description, String price, String imageUri) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUri = imageUri;
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
