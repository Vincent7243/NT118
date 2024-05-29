package com.company.soccershoesstore;

public class Product {
    private String mid;
    private  String mname;
    private  String mprice;
    private String mimage;
    private String mdescription;
    private String mbrand;

    public Product(String mid, String mname, String mprice, String mimage, String mdescription, String mbrand) {
        this.mid = mid;
        this.mname = mname;
        this.mprice = mprice;
        this.mimage = mimage;
        this.mdescription = mdescription;
        this.mbrand = mbrand;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public String getMimage() {
        return mimage;
    }

    public void setMimage(String mimage) {
        this.mimage = mimage;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    public String getMbrand() {
        return mbrand;
    }

    public void setMbrand(String mbrand) {
        this.mbrand = mbrand;
    }


}
