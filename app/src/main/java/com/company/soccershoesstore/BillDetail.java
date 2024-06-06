package com.company.soccershoesstore;

public class BillDetail {

    String idItem;
    String idProduct;
    String quantity;
    String total;

    public BillDetail(String idItem, String idProduct, String quantity, String total) {
        this.idItem = idItem;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.total = total;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
