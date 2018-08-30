package model;

public class LoginModel {
    private String msg,id,name,sefareshat,prodect,customer,fani,bazagani,forosh,add_channel1,remove_chat;

    public LoginModel(String msg, String id, String name, String sefareshat, String prodect, String customer, String fani, String bazagani, String forosh, String add_channel1, String remove_chat) {
        this.msg = msg;
        this.id = id;
        this.name = name;
        this.sefareshat = sefareshat;
        this.prodect = prodect;
        this.customer = customer;
        this.fani = fani;
        this.bazagani = bazagani;
        this.forosh = forosh;
        this.add_channel1 = add_channel1;
        this.remove_chat = remove_chat;
    }

    public String getSefareshat() {
        return sefareshat;
    }

    public void setSefareshat(String sefareshat) {
        this.sefareshat = sefareshat;
    }

    public String getProdect() {
        return prodect;
    }

    public void setProdect(String prodect) {
        this.prodect = prodect;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getFani() {
        return fani;
    }

    public void setFani(String fani) {
        this.fani = fani;
    }

    public String getBazagani() {
        return bazagani;
    }

    public void setBazagani(String bazagani) {
        this.bazagani = bazagani;
    }

    public String getForosh() {
        return forosh;
    }

    public void setForosh(String forosh) {
        this.forosh = forosh;
    }

    public String getAdd_channel1() {
        return add_channel1;
    }

    public void setAdd_channel1(String add_channel1) {
        this.add_channel1 = add_channel1;
    }

    public String getRemove_chat() {
        return remove_chat;
    }

    public void setRemove_chat(String remove_chat) {
        this.remove_chat = remove_chat;
    }

    public LoginModel() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

