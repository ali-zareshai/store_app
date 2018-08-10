package model;

public class LoginModel {
    private String msg,id,name;

    public LoginModel(String msg, String id, String name) {
        this.msg = msg;
        this.id = id;
        this.name = name;
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
