package com.company.soccershoesstore;

public class BillInfo {
    String idBill;
    String idUser;
    String status;
    String total;

    public BillInfo(String idBill, String idUser, String status, String total) {
        this.idBill = idBill;
        this.idUser = idUser;
        this.status = status;
        this.total = total;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
