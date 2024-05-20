package com.company.soccershoesstore;

public class ProductCard {
    private String id;
    private int img;
    private String price;
    private  String title;

    public ProductCard(String id, int img, String price, String title) {
        this.id = id;
        this.img = img;
        this.price = price;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
