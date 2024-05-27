package com.company.soccershoesstore;

public class Sale {
    private String mcode;
    private String mprice;
    private String mquantity;

    private String mid;

    public Sale(String mid,String mcode, String mprice, String mquantity) {
        this.mcode = mcode;
        this.mprice = mprice;
        this.mquantity = mquantity;
        this.mid = mid;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public String getMquantity() {
        return mquantity;
    }

    public void setMquantity(String mquantity) {
        this.mquantity = mquantity;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
