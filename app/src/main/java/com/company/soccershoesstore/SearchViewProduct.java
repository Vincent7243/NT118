package com.company.soccershoesstore;

public class SearchViewProduct {
    private String brand;
    private String description;
    private String image;
    private String name;
    private String price;

    public SearchViewProduct() {
        // Empty constructor needed for Firestore
    }

    public SearchViewProduct(String brand, String description, String image, String name, String price) {
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    // Getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
