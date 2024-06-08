package com.company.soccershoesstore;

public class ItemProductStaticAdmin {
    String mid;
    long num;

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

    public ItemProductStaticAdmin(String mid, long num) {
        this.mid = mid;
        this.num = num;
    }
}
