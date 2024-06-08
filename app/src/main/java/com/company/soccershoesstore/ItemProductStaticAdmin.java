package com.company.soccershoesstore;

public class ItemProductStaticAdmin {
    String mid;
    long num;

    String name;

    public ItemProductStaticAdmin(String mid, long num, String name) {
        this.mid = mid;
        this.num = num;
        this.name = name;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
