package com.company.soccershoesstore;

public class Shooes {
    public static final int Type_nam = 1;
    public static final int Type_nu = 2;
    private int image;
    private String name;
    private int type;

    public Shooes(int image, String name, int type) {
        this.image = image;
        this.name = name;
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }
}