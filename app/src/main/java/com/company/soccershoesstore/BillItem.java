package com.company.soccershoesstore;

public class BillItem {
    private String id_bill;
    private String quan;
    private String total;
    private String id_product;

    public BillItem() {}

    public BillItem(String id_bill, String quan, String total,String id_product) {
        this.id_bill = id_bill;
        this.quan = quan;
        this.total = total;
        this.id_product = id_product;
    }

    public void setId_bill(String id_bill) {
        this.id_bill = id_bill;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_product() {
        return id_product;
    }

    public String getId_bill() {
        return id_bill;
    }

    public String getQuan() {
        return quan;
    }

    public String getTotal() {
        return total;
    }
}
