package com.company.soccershoesstore;

public class ProductCard {
    private String id;
    private String img;
    private String price;
    private  String name;

    public ProductCard(String id, String img, String price, String name) {
        this.id = id;
        this.img = img;
        this.price = price;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



