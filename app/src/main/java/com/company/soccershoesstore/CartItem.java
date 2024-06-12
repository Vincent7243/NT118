package com.company.soccershoesstore;

public class CartItem {
    String idProduct;
    String quan;
    String total;

    public CartItem(String idProduct, String quan, String total) {
        this.idProduct = idProduct;
        this.quan = quan;
        this.total = total;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
