package com.company.soccershoesstore;

<<<<<<< HEAD
import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private String imageUrl;

    public Product(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    protected Product(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }
=======
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

>>>>>>> 73e2bf679d5eedadb24347aea0fe4a453dbdfdc3
}
