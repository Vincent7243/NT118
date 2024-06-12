package com.company.soccershoesstore;

public class BillItem {
    private String id_bill;
    private String quan;
    private String total;

    public BillItem() {}

    public BillItem(String id_bill, String quan, String total) {
        this.id_bill = id_bill;
        this.quan = quan;
        this.total = total;
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
