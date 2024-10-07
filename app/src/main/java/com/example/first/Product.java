package com.example.first;

public class Product {
    private String productId;
    private String name;
    private String description;
    private String category;

    // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    public Product() {}

    public Product(String productId, String name, String description, String category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
