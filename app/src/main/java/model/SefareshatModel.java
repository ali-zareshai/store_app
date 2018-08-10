package model;

public class SefareshatModel {
    private String id,refrence,price,method_price,status,date_sefaresh;

    public SefareshatModel() {
    }

    public SefareshatModel(String id, String refrence, String price, String method_price, String status, String date_sefaresh) {
        this.id = id;
        this.refrence = refrence;
        this.price = price;
        this.method_price = method_price;
        this.status = status;
        this.date_sefaresh = date_sefaresh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMethod_price() {
        return method_price;
    }

    public void setMethod_price(String method_price) {
        this.method_price = method_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_sefaresh() {
        return date_sefaresh;
    }

    public void setDate_sefaresh(String date_sefaresh) {
        this.date_sefaresh = date_sefaresh;
    }
}
