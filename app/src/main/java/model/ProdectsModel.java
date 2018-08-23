package model;

public class ProdectsModel {
    private String id_,categoty,image,ref,price,available,show_price,name,mojodi,combine,update,combine_id;

    public ProdectsModel() {
    }

    public ProdectsModel(String id, String categoty, String image, String ref, String price, String available, String show_price, String name, String mojodi, String combine, String update, String combine_id) {
        this.id_ = id;
        this.categoty = categoty;
        this.image = image;
        this.ref = ref;
        this.price = price;
        this.available = available;
        this.show_price = show_price;
        this.name = name;
        this.mojodi = mojodi;
        this.combine = combine;
        this.update = update;
        this.combine_id = combine_id;
    }

    public String getId() {
        return id_;
    }

    public void setId(String id) {
        this.id_ = id;
    }

    public String getCategoty() {
        return categoty;
    }

    public void setCategoty(String categoty) {
        this.categoty = categoty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getShow_price() {
        return show_price;
    }

    public void setShow_price(String show_price) {
        this.show_price = show_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMojodi() {
        return mojodi;
    }

    public void setMojodi(String mojodi) {
        this.mojodi = mojodi;
    }

    public String getCombine() {
        return combine;
    }

    public void setCombine(String combine) {
        this.combine = combine;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getCombine_id() {
        return combine_id;
    }

    public void setCombine_id(String combine_id) {
        this.combine_id = combine_id;
    }
}
